package com.minimalisticapps.priceconverter.presentation.donate

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.minimalisticapps.priceconverter.common.Event
import com.minimalisticapps.priceconverter.common.utils.PCSharedStorage
import com.minimalisticapps.priceconverter.data.repository.DonationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DonationViewModel @Inject constructor(
    application: Application,
    private val donationRepository: DonationRepository,
) : AndroidViewModel(application) {
    private var _lnUrl: MutableLiveData<String> = MutableLiveData()
    var lnUrl: LiveData<String> = _lnUrl

    private val _error = MutableLiveData<Event<String>>()
    val error: LiveData<Event<String>> = _error

    fun userClickedDonate() {
        val claim = PCSharedStorage.getDonationClaim()

        viewModelScope.launch {
            try {
                _lnUrl.postValue(donationRepository.makeClaim(claim).lnurl)
            } catch (e: Exception) {
                _error.postValue(Event(e.toString()))
            }
        }
    }
}
