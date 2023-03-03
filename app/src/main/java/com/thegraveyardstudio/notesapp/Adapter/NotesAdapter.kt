package com.thegraveyardstudio.notesapp.Adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.thegraveyardstudio.notesapp.Models.Note
import com.thegraveyardstudio.notesapp.R
import kotlin.random.Random
import java.util.*
import kotlin.collections.ArrayList

class NotesAdapter(private val context : Context,
                   val listener: NotesClickListener) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    private val NotesList = ArrayList<Note>()
    private val fullList = ArrayList<Note>()

    inner class ViewHolder(itemview:View):RecyclerView.ViewHolder(itemview){
        val notes_layout = itemview.findViewById<CardView>(R.id.card_layout)
        val Note_tv = itemView.findViewById<TextView>(R.id.tv_note)
        val title=itemview.findViewById<TextView>(R.id.tv_title)
        val date=itemview.findViewById<TextView>(R.id.tv_date)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return  ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_item,parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentNote = NotesList[position]
        holder.title.text = currentNote.title
        holder.title.isSelected = true
        holder.Note_tv.text = currentNote.note
        holder.date.text = currentNote.date
        holder.date.isSelected = true


        holder.notes_layout.setCardBackgroundColor(holder.itemView.resources.getColor(randomColor(),null))

        holder.notes_layout.setOnClickListener{
            listener.onItemClicked(NotesList[holder.adapterPosition])
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            holder.notes_layout.setOnLongClickListener {
                listener.onLongItemClicked(NotesList[holder.adapterPosition], holder.notes_layout)
                true
            }
        }

    }

    override fun getItemCount(): Int {
        return NotesList.size
    }


    fun updateList(newList : List<Note>){
        fullList.clear()
        fullList.addAll(newList)


        NotesList.clear()
        NotesList.addAll(fullList)
        notifyDataSetChanged()


    }


    fun filterList(search: String){
        NotesList.clear()
        for(item in fullList){
            if (item.title?.lowercase()?.contains(search.lowercase()) == true ||
                    item.note?.lowercase()?.contains(search.lowercase())== true ){
                NotesList.add(item)
            }
        }
        notifyDataSetChanged()
    }


    fun randomColor(): Int {
        val list= java.util.ArrayList<Int>()
        list.add(R.color.blue)
        list.add(R.color.green)
        list.add(R.color.indigo)
        list.add(R.color.rose)
        list.add(R.color.choco)
        list.add(R.color.pink)

        val seed=System.currentTimeMillis().toInt()
        val randomIndex= Random(seed.toLong()).nextInt(list.size)
        return list[randomIndex]
    }


    interface NotesClickListener{
        fun onItemClicked(note:Note)
        fun onLongItemClicked(note:Note, cardView: CardView)
    }

}