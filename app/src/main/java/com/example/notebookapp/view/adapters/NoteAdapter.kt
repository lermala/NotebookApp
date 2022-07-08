package com.example.notebookapp.view.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.notebook.model.Note
import com.example.notebookapp.R
import com.example.notebookapp.databinding.ItemNotePantoneBinding
import com.example.notebookapp.model.note_style.NoteStylePantone
import com.example.notebookapp.model.note_style.NoteStyleSimple

typealias OnClickItemListener = (Note) -> Unit

interface NoteActionListener {
    fun onNoteEdit(note: Note)
    fun onNoteDelete(note: Note)
    // todo
}

class NoteAdapter(
    private val actionListener: NoteActionListener
): RecyclerView.Adapter<NoteAdapter.NoteViewHolder>(), View.OnClickListener {

    var notes: List<Note> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = notes.size

    // вызывается тогда, когда RecyclerView хочет создать новый элемент списка
    // viewType вызывается когда будет более 1-го типа элемента в списке
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNotePantoneBinding.inflate(inflater, parent, false) //todo

        with (binding) {
            root.setOnClickListener(this@NoteAdapter)
            deleteNoteImageView.setOnClickListener(this@NoteAdapter)
        }

        return NoteViewHolder(binding)
    }

    // обновление элемента списка
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = notes[position]

        // обновляем вид в соответствии со стилем заметки
        when(currentNote.style){
            is NoteStylePantone -> getPantoneView(holder, position)
            is NoteStyleSimple -> getSimpleView(holder, position) // todo
        }

    }

    private fun getPantoneView(holder: NoteViewHolder, position: Int)  {
        // val binding : ItemNotePantoneBinding =
        //     convertView?.tag as ItemNotePantoneBinding? ?: // if doesn't exist
        //     createPantoneBinding(parent!!.context) // else create new
        val holderPantone = holder.binding as ItemNotePantoneBinding

        val currentNote = notes[position]
        val pantone = (currentNote.style as NoteStylePantone).pantoneColor // // change color

        // updates UI
        with (holderPantone) {
            holder.itemView.tag = currentNote
            noteNameTextView.tag = currentNote
            codeColorTextView.tag = currentNote
            codeNameTextView.tag = currentNote
            deleteNoteImageView.tag = currentNote
            imageView.tag = currentNote

            noteNameTextView.text = notes[position].title
            imageView.setBackgroundColor(pantone.parseColor())
            codeColorTextView.text = pantone.codePantoneColor
            codeNameTextView.text = pantone.nameColor
        }
    }

    private fun getSimpleView(holder: NoteViewHolder, position: Int) {
        // val binding : ItemNotePantoneBinding =
        //     convertView?.tag as ItemNotePantoneBinding? ?: // if doesn't exist
        //     createPantoneBinding(parent!!.context) // else create new

        // updates UI
        // todo
    }

    // использовать, если в каждом view будет кнопка, которую нужно обработать.
    // Сейчас она не нужна.
    override fun onClick(v: View) {
        val note = v.tag as Note
        when (v.id) {
            R.id.deleteNoteImageView -> {
                actionListener.onNoteDelete(note)
            }
            else -> { // клик на элемент списка
                actionListener.onNoteEdit(note)
            }
        }
    }

    class NoteViewHolder(
        val binding: ViewBinding
    ) : RecyclerView.ViewHolder(binding.root)
}
