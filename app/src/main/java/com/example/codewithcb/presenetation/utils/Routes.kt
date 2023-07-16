package com.example.codewithcb.presenetation.utils

object Routes {
    const val FIRST_SCREEN = "FirstScreen"
    const val SECOND_SCREEN = "SecondScreen"

    fun getSecondScreenPath(customValue: String?): String =
        if (customValue != null && customValue.isNotBlank()) "SecondScreen/$customValue" else "SecondScreen/Empty"

    object Values {
        const val SECOND_SCREEN_VALUE = "data"
    }
}