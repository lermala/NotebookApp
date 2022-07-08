package com.example.notebookapp.model

import android.graphics.Color
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class PantoneColor(
    val nameColor: String, // from Pantone
    val codePantoneColor: String, // from Pantone
    val hexCodeColor: String, // #c6aaaf
): Parcelable {

    fun parseColor(): Int {
        return Color.parseColor(hexCodeColor)
    }
}