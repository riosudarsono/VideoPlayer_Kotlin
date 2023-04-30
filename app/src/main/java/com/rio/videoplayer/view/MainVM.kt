package com.rio.videoplayer.view

import androidx.lifecycle.ViewModel
import com.rio.videoplayer.data.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {
    fun search(term: String) = mainRepository.search(term)
}