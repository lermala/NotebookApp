package com.example.notebookapp.model.note_style

import android.os.Parcelable
import androidx.annotation.StyleRes
import com.example.notebookapp.R
import com.example.notebookapp.model.PantoneColor
import kotlinx.android.parcel.Parcelize

@Parcelize
class NoteStylePantone(
    // styleName: String = KEY_PANTONE_STYLE,
    // todo change pantoneColor by default
    val pantoneColor: PantoneColor = PantoneColor("Lotus", "14-1905", "#e2c0bf"),
) : NoteStyle(KEY_STYLE_PANTONE, R.layout.item_note_pantone), Parcelable {



}