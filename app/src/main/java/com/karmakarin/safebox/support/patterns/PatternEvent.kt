package com.karmakarin.safebox.support.patterns

import androidx.annotation.Keep

@Keep
enum class PatternEvent {
    INITIALIZE, FIRST_COMPLETED, SECOND_COMPLETED, ERROR
}