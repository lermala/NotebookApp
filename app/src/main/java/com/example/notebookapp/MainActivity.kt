package com.example.notebookapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.LifecycleOwner
import com.example.notebook.model.Note
import com.example.notebookapp.databinding.ActivityMainBinding
import com.example.notebookapp.model.NotesService
import com.example.notebookapp.view.contract.*
import com.example.notebookapp.view.screens.AllNotesFragment
import com.example.notebookapp.view.screens.NoteFragment

class MainActivity : AppCompatActivity(), AppContract {

    lateinit var binding: ActivityMainBinding

    override val notesService: NotesService
        get() = (applicationContext as App).notesService

    // вызывается тогда, когда для определенного фргамента создался интерфейс
    // и который готовится быть показанным сейчас
    // всегда занем какой фрагмент и когда обновить toolbar
    private val fragmentListener = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(
            fm: FragmentManager,
            f: Fragment,
            v: View,
            savedInstanceState: Bundle?
        ) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState)
            updateUI()
        }
    }

    private val currentFragment: Fragment
        get() = supportFragmentManager.findFragmentById(R.id.fragment_container)!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        setSupportActionBar(binding.toolbar) // по умолчанию

        // если запускается впервые
        if (savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, AllNotesFragment())
                .commit()
        }

        // обработка интерфейсов hasCustomTitle, hasCustomAction
        // также удалить его в onDestroy() чтобы не было утечек ресурсов и т.д.
        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentListener, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentListener)
    }

    // чтобы работала кнопка назад
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun goBack() {
        onBackPressed()
    }

    // возврат на самый первый фрагмент
    override fun goToStartFragment() {
        // delete all frags from backstack
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    override fun addNote(note: Note) {
        notesService.notes.add(note)
    }

    override fun editNote(id: Int, note: Note) {
        notesService.notes.set(id, note) // todo change
    }

    override fun showAllNotes() {
        launchFragment(AllNotesFragment())
    }

    override fun showEditingNote(noteId: Int) {
        launchFragment(NoteFragment.newInstance(noteId))
    }

    override fun showCreatingNote() {
        launchFragment(NoteFragment())
    }

    // кастомизация верхнего меню
    private fun updateUI() {
        val fragment = currentFragment

        // кастомизация заголовка
        if (fragment is HasCustomTitle){
            binding.toolbar.title = getString(fragment.getTitleRes())
        } else {
            binding.toolbar.title = getString(R.string.notes)
        }

        // показать кнопку назад или нет
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
        } else {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            supportActionBar?.setDisplayShowHomeEnabled(false)
        }

        // кастомизация верхнего меню (добавление кнопки)
        if (fragment is HasCustomAction){
            createCustomToolbarAction(fragment.getCustomAction())
        } else {
            binding.toolbar.menu.clear()
        }
    }

    private fun createCustomToolbarAction(action: CustomAction) {
        val iconDrawable = DrawableCompat.wrap(ContextCompat.getDrawable(this, action.iconRes)!!)
        iconDrawable.setTint(Color.WHITE)

        // add menu
        val menuItem = binding.toolbar.menu.add(action.textRes)
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS) // показывать как действие, а не как меню
        menuItem.icon = iconDrawable
        menuItem.setOnMenuItemClickListener {
            action.onCustomAction.run()
            return@setOnMenuItemClickListener true
        }
    }

    private fun launchFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun <T : Parcelable> publishResult(result: T) {
        supportFragmentManager.setFragmentResult(result.javaClass.name, bundleOf(KEY_RESULT to result))
    }
    override fun <T : Parcelable> listenResult(
        clazz: Class<T>,
        owner: LifecycleOwner,
        listener: ResultListener<T>
    ) {
        supportFragmentManager.setFragmentResultListener(
            clazz.name,
            owner,
            FragmentResultListener { key, bundle ->
                listener.invoke(bundle.getParcelable(KEY_RESULT)!!)
            }
        )
    }

    companion object{
        private const val KEY_RESULT = "KEY_RESULT"
    }
}