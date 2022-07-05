package com.example.notebookapp.model

import android.graphics.Color

class PantoneColor(
    val nameColor: String, // from Pantone
    val codePantoneColor: String, // from Pantone
    val hexCodeColor: String, // #c6aaaf
) {

    fun parseColor(): Int {
        return Color.parseColor(hexCodeColor)
    }
}