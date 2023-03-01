package com.thegraveyardstudio.notesapp

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.room.RoomDatabase
import com.thegraveyardstudio.notesapp.Adapter.NotesAdapter
import com.thegraveyardstudio.notesapp.Database.NoteDatabase
import com.thegraveyardstudio.notesapp.Models.Note
import com.thegraveyardstudio.notesapp.Models.NoteViewModel
import com.thegraveyardstudio.notesapp.databinding.ActivityAddNoteBinding
import com.thegraveyardstudio.notesapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var database: NoteDatabase
    lateinit var viewModel: NoteViewModel
    lateinit var adapter: NotesAdapter
    lateinit var selectedNote: Note
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Initializing the UI
        initUI()

        viewModel = ViewModelProvider(
            this
                    ViewModelProvider . AndroidViewModelFactory . getInstance (application)
        ).get(NoteViewModel::class.java)
        viewModel.allnotes.observe(this) { list ->
            list?.let {
                adapter.updateList(list)
            }
        }
        database = NoteDatabase.getDatabase(this)

    }
    //)

        private fun initUI(){
            binding.recyclerView.setMasFixedSize(true)
            binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2,LinearLayout.VERTICAL)
            adapter = NotesAdapter(this, this)
            binding.recyclerView.adapter = adapter

            val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result ->

                if (result.resultCode == Activity.RESULT_OK) {
                    val note = result.data?.getSerializableExtra("note") as? Note
                    if (note != null){
                        viewModel.insert
                    }

                }

            }

    }
}