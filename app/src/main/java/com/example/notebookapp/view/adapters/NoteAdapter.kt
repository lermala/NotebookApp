package com.example.notebookapp.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notebook.model.Note
import com.example.notebookapp.R
import com.example.notebookapp.databinding.ItemNotePantoneBinding
import com.example.notebookapp.model.PantoneColor

typealias OnClickItemListener = (Note) -> Unit

class NoteAdapter(private val notes: MutableList<Note>): BaseAdapter(), View.OnClickListener {

    private var colorId = 0
    private var pantones = PantoneColor.getDefaultPantones()

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
        val binding : ItemNotePantoneBinding =
            convertView?.tag as ItemNotePantoneBinding? ?: // if doesn't exist
            createBinding(parent!!.context) // else create new

        // updates UI
        binding.noteNameTextView.text = notes[pos].title
        // change color
        binding.imageView.setBackgroundColor(pantones[colorId].parseColor())
        binding.codeColorTextView.text = pantones[colorId].codePantoneColor
        binding.codeNameTextView.text = pantones[colorId].nameColor
        changeColorId()

        return binding.root
    }

    private fun changeColorId(){
        if (colorId == pantones.size - 1){
            colorId = 0
        }
        else {
            colorId++
        }
    }

    private fun createBinding(context: Context) : ItemNotePantoneBinding {
        val binding = ItemNotePantoneBinding.inflate(LayoutInflater.from(context))
        // binding.btDel.setOnClickListener(this)

        binding.root.tag = binding
        return binding
    }

    override fun onClick(v: View?) {
        // val note = v.tag as Note
        // OnClickItemListener.invoke()
    }


}