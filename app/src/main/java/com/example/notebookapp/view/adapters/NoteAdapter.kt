package com.example.notebookapp.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.notebook.model.Note
import com.example.notebookapp.databinding.ItemNotePantoneBinding
import com.example.notebookapp.model.PantoneColor
import com.example.notebookapp.model.note_style.NoteStylePantone
import com.example.notebookapp.model.note_style.NoteStyleSimple

typealias OnClickItemListener = (Note) -> Unit

class NoteAdapter(private val notes: MutableList<Note>): BaseAdapter(), View.OnClickListener {

    override fun getCount(): Int {
        return notes.size
    }

    override fun getItem(p0: Int): Any {
        return notes[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(pos: Int, convertView: View?, parent: ViewGroup?): View {
        val currentNote = notes[pos]

        var view: View = getPantoneView(pos, convertView, parent) // change by default
        when(currentNote.style){
            is NoteStylePantone -> view = getPantoneView(pos, convertView, parent)
            is NoteStyleSimple ->  view = getSimpleView(pos, convertView, parent)
        }

        return view
    }

    private fun getPantoneView(pos: Int, convertView: View?, parent: ViewGroup?): View  {
        val binding : ItemNotePantoneBinding =
            convertView?.tag as ItemNotePantoneBinding? ?: // if doesn't exist
            createPantoneBinding(parent!!.context) // else create new

        // updates UI
        binding.noteNameTextView.text = notes[pos].title
        // change color
        val pantone = (notes[pos].style as NoteStylePantone).pantoneColor

        binding.imageView.setBackgroundColor(pantone.parseColor())
        binding.codeColorTextView.text = pantone.codePantoneColor
        binding.codeNameTextView.text = pantone.nameColor

        return binding.root
    }

    private fun getSimpleView(pos: Int, convertView: View?, parent: ViewGroup?): View  {
        val binding : ItemNotePantoneBinding =
            convertView?.tag as ItemNotePantoneBinding? ?: // if doesn't exist
            createPantoneBinding(parent!!.context) // else create new

        // updates UI
        // todo

        return binding.root
    }

    private fun createPantoneBinding(context: Context) : ItemNotePantoneBinding {
        val binding = ItemNotePantoneBinding.inflate(LayoutInflater.from(context))
        // binding.btDel.setOnClickListener(this)

        binding.root.tag = binding
        return binding
    }

    // использовать, если в каждом view будет кнопка, которую нужно обработать.
    // Сейчас она не нужна.
    override fun onClick(v: View?) {
        // val note = v.tag as Note
        // OnClickItemListener.invoke()
    }


}