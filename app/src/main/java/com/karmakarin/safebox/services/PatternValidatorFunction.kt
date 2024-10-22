package com.karmakarin.safebox.services

import com.karmakarin.safebox.db.pattern.PatternDot
import com.karmakarin.safebox.support.patterns.PatternChecker
import java.util.function.BiFunction

class PatternValidatorFunction : BiFunction<List<PatternDot>, List<PatternDot>, Boolean> {
    override fun apply(t1: List<PatternDot>, t2: List<PatternDot>): Boolean {
        return PatternChecker.checkPatternsEqual(t1, t2)
    }
}