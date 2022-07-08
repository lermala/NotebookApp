package com.example.notebook.model

import android.os.Parcel
import android.os.Parcelable
import com.example.notebookapp.model.PantoneColor
import com.example.notebookapp.model.note_style.NoteStyle
import com.example.notebookapp.model.note_style.NoteStylePantone
import kotlinx.android.parcel.Parcelize


class Note (
    var id: Int = -1,
    var title: String = "",
    var content: String = "", // TODO: change to Content class that contains xml??? how to allow user formatting text ?
    val editDate: String = "04/07/2022", // todo current date time
    val style: NoteStyle = NoteStylePantone())  {
}