package com.nloyko.interviewtesttask.obtainpoints

import androidx.lifecycle.*
import com.nloyko.interviewtesttask.repository.PointsRepository
import com.nloyko.interviewtesttask.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ObtainPointsViewModel @Inject constructor(
    private val pointsRepository: PointsRepository
) : ViewModel() {

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _pointsLoadResult = MutableLiveData<Event<Boolean>>()
    val pointsLoadResult: LiveData<Event<Boolean>> = _pointsLoadResult

    var pointsCount = MutableLiveData(10)

    val inputValidation: LiveData<Boolean> = pointsCount.map { it in 1..1000 }

    fun obtainPoints() {
        if (pointsCount.value in 1..1000 && _dataLoading.value != true) {
            _dataLoading.value = true

            viewModelScope.launch {
                pointsCount.value?.let {
                    _pointsLoadResult.value = Event(pointsRepository.obtainPoints(it))
                }

                _dataLoading.value = false
            }
        }
    }
}