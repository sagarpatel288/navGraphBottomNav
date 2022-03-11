package com.srdpatel.bottomnavigationtutorial

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {

    private val mutableLiveDataNumber = MutableLiveData<Int>(0)

    val liveDataTitle: LiveData<Int>
        get() = mutableLiveDataNumber

    fun increaseNumber() {
        mutableLiveDataNumber.value = mutableLiveDataNumber.value?.plus(1)
    }
}