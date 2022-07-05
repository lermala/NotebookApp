package com.example.notebook.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Note (
    var title: String = "",
    var content: String = "", // TODO: change to Content class that contains xml??? how to allow user formatting text ?
    val editDate: String = "04/07/2022", // todo current date time
    val style: String = "pantone") : Parcelable {
}