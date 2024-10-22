package com.karmakarin.safebox.support.finger

data class FingerPrintResultData(
    val fingerPrintResult: FingerPrintResult,
    val availableTimes: Int = 0,
    val errorMessage: String = ""
) {

    fun isSucces() = fingerPrintResult == FingerPrintResult.SUCCESS

    fun isNotSucces() = fingerPrintResult != FingerPrintResult.SUCCESS

    companion object {

        fun matched() = FingerPrintResultData(FingerPrintResult.SUCCESS)

        fun notMatched(availableTimes: Int) = FingerPrintResultData(
            fingerPrintResult = FingerPrintResult.NOT_MATCHED,
            availableTimes = availableTimes
        )

        fun error(errorMessage: String) =
            FingerPrintResultData(
                fingerPrintResult = FingerPrintResult.ERROR,
                errorMessage = errorMessage
            )
    }
}