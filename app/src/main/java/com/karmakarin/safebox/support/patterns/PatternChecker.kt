package com.karmakarin.safebox.support.patterns

import com.karmakarin.safebox.db.pattern.PatternDot

object PatternChecker {
    fun checkPatternsEqual(pattern1: List<PatternDot>, pattern2: List<PatternDot>): Boolean {
        if (pattern1.isEmpty() || pattern2.isEmpty()) {
            return false
        }

        if (pattern1.size != pattern2.size) {
            return false
        }

        var checkBothSame = true
        for (i in pattern1.indices) {
            if (pattern1[i].row != pattern2[i].row || pattern1[i].column != pattern2[i].column) {
                checkBothSame = false
            }
        }
        return checkBothSame
    }
}