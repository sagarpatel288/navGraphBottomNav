package com.srdpatel.bottomnavigationtutorial

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.srdpatel.bottomnavigationtutorial.AppConstants.LOG_APP_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SharedViewModel : ViewModel() {

    private val mutableLiveDataNumber = MutableLiveData<Int>(0)
    private val numberStateFlow = MutableStateFlow(0)

    val liveDataTitle: LiveData<Int>
        get() = mutableLiveDataNumber

    val numbers: StateFlow<Int>
        get() = numberStateFlow

    fun increaseNumber() {
        mutableLiveDataNumber.value = mutableLiveDataNumber.value?.plus(1)
        val tryEmit = numberStateFlow.tryEmit(numberStateFlow.value + 1)
        Log.d(" :$LOG_APP_NAME: ", "SharedViewModel: :increaseNumber: tryEmit: $tryEmit")
    }
}