package com.nloyko.interviewtesttask.displaypoints

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nloyko.interviewtesttask.repository.PointsRepository
import com.nloyko.interviewtesttask.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DisplayPointsViewModel @Inject constructor(pointsRepository: PointsRepository) : ViewModel() {

    val points = pointsRepository.observePoints()

    private val _onSaveToFile = MutableLiveData<Event<Unit>>()
    val onSaveToFile: LiveData<Event<Unit>> = _onSaveToFile

    fun saveToFile() {
        _onSaveToFile.value = Event(Unit)
    }
}