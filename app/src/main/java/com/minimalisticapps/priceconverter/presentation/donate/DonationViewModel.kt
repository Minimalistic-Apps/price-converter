package com.minimalisticapps.priceconverter.presentation.donate

import android.app.Application
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.minimalisticapps.priceconverter.common.Event
import com.minimalisticapps.priceconverter.common.utils.PCSharedStorage
import com.minimalisticapps.priceconverter.data.repository.DonationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

const val POLLING_FREQ = 1000
const val TIMEOUT_POLLING = 5 * 60 * 1000
const val MAX_ATTEMPTS = TIMEOUT_POLLING / POLLING_FREQ


@HiltViewModel
class DonationViewModel @Inject constructor(
    application: Application,
    private val donationRepository: DonationRepository,
) : AndroidViewModel(application) {
    private var _lnUrl = mutableStateOf<String?>(null)
    var lnUrl: State<String?> = _lnUrl

    private val _error = MutableLiveData<Event<String>>()
    val error: LiveData<Event<String>> = _error

    private val _keyStatus = mutableStateOf(listOf<String>())
    val keyStatus: State<List<String>> = _keyStatus

    fun userOpenedScreenWithDonate() {
        val claim = PCSharedStorage.getDonationClaim()

        // Check if claim is already paid
        viewModelScope.launch {
            try {
                val lnurl = donationRepository.makeClaim(claim).lnurl
                _lnUrl.value = lnurl

                // Check if claim is already paid
                val response = donationRepository.getClaim(claim)
                if (response.key != null) {
                    PCSharedStorage.saveDonationToken(response.key)
                }
                _keyStatus.value = response.status

            } catch (e: retrofit2.HttpException) {
                if (e.code() == 404) {
                    return@launch
                }

                Log.e("x", e.stackTraceToString())
                _error.postValue(Event(e.toString()))
            }
        }
    }

    fun userClickedPay() {
        val claim = PCSharedStorage.getDonationClaim()

        var attempts = 0

        Timer().schedule(
            object : TimerTask() {
                override fun run() {
                    if (attempts > MAX_ATTEMPTS) {
                        this.cancel()
                        _error.postValue(Event("Waiting for payment timeouts. ($MAX_ATTEMPTS)"))
                    }

                    viewModelScope.launch {
                        try {
                            val response = donationRepository.getClaim(claim)
                            if (response.key != null) {
                                PCSharedStorage.saveDonationToken(response.key)
                            }
                            _keyStatus.value = response.status

                        } catch (e: retrofit2.HttpException) {
                            if (e.code() == 404) {
                                return@launch
                            }

                            Log.e("x", e.stackTraceToString())
                            _error.postValue(Event(e.toString()))
                        }
                    }
                    attempts += 1
                }
            },
            POLLING_FREQ.toLong(),
            POLLING_FREQ.toLong(),
        )
    }
}
