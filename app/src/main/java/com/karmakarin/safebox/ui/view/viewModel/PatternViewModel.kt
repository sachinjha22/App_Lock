package com.karmakarin.safebox.ui.view.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karmakarin.safebox.db.pattern.PatternDao
import com.karmakarin.safebox.db.pattern.PatternDotMetadata
import com.karmakarin.safebox.db.pattern.PatternEntity
import com.karmakarin.safebox.support.patterns.PatternChecker
import com.karmakarin.safebox.support.patterns.PatternEvent
import com.karmakarin.safebox.support.patterns.PatternLockView
import com.karmakarin.safebox.ui.view.state.PatternViewState
import com.karmakarin.safebox.util.extensions.convertToPatternDot
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatternViewModel @Inject constructor(private val patternDao: PatternDao) : ViewModel() {

    private val _patternEventLiveData = MutableLiveData<PatternViewState>()

    init {
        _patternEventLiveData.value = PatternViewState(PatternEvent.INITIALIZE)
    }

    val getPatternEventLiveDataa: LiveData<PatternViewState> = _patternEventLiveData

    // Pattern data
    private val firstDrawnPattern = mutableListOf<PatternLockView.Dot>()
    private val redrawnPattern = mutableListOf<PatternLockView.Dot>()

    fun setFirstDrawnPattern(pattern: List<PatternLockView.Dot>?) {
        pattern?.let {
            firstDrawnPattern.clear()
            firstDrawnPattern.addAll(it)
            _patternEventLiveData.value = PatternViewState(PatternEvent.FIRST_COMPLETED)
        }
    }

    fun setRedrawnPattern(pattern: List<PatternLockView.Dot>?) {
        pattern?.let {
            redrawnPattern.clear()
            redrawnPattern.addAll(it)

            if (PatternChecker.checkPatternsEqual(
                    firstDrawnPattern.convertToPatternDot(),
                    redrawnPattern.convertToPatternDot()
                )
            ) {
                saveNewCreatedPattern(firstDrawnPattern)
                _patternEventLiveData.value = PatternViewState(PatternEvent.SECOND_COMPLETED)
            } else {
                firstDrawnPattern.clear()
                redrawnPattern.clear()
                _patternEventLiveData.value = PatternViewState(PatternEvent.ERROR)
            }
        }
    }

    fun isFirstPattern(): Boolean = firstDrawnPattern.isEmpty()

    private fun saveNewCreatedPattern(pattern: List<PatternLockView.Dot>) {
        viewModelScope.launch(Dispatchers.IO) {
            val patternMetadata = PatternDotMetadata(pattern.convertToPatternDot())
            val patternEntity = PatternEntity(patternMetadata)
            patternDao.createPattern(patternEntity)
        }
    }
}