package com.minimalisticapps.priceconverter.presentation.donate

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.minimalisticapps.priceconverter.common.utils.PCSharedStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DonationViewModel @Inject constructor(
    application: Application,
) : AndroidViewModel(application) {


    fun userClickedDonate() {
        val claim = PCSharedStorage.getDonationClaim()

        viewModelScope.launch {
        }
    }
}
