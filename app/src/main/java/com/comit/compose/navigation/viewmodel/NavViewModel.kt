package com.comit.compose.navigation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/*
 * Created by Comit on 2021/12/19.
 */
@HiltViewModel
class NavViewModel @Inject constructor() : ViewModel() {

    override fun onCleared() {
        super.onCleared()
        Log.d("TTT", "onCleared")
    }

}