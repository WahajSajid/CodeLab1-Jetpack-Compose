package com.application.listcomposeapplication

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ViewModelClass:ViewModel() {
    private val _showListScreen = mutableStateOf(false)
    val showListScreen = _showListScreen


}