package com.example.notebookapp.model.note_style
import androidx.annotation.LayoutRes

abstract class NoteStyle(
    val styleName: String,
    @LayoutRes val layout: Int
) {
    companion object{
        const val KEY_STYLE_PANTONE = "PANTONE"
        const val KEY_STYLE_SIMPLE = "SIMPLE" // todo
    }
}