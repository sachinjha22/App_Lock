package com.karmakarin.safebox.util.extensions

import com.karmakarin.safebox.db.pattern.PatternDot
import com.karmakarin.safebox.support.patterns.PatternLockView

inline fun <reified T> List<PatternLockView.Dot>.convertToPatternDot(): List<T> {
    return map {
        when (T::class) {
            PatternDot::class -> PatternDot(it.column, it.row) as T
            else -> throw IllegalArgumentException("Unsupported type")
        }
    }
}