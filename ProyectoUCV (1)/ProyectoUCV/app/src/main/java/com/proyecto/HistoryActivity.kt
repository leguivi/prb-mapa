package com.proyecto

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.proyecto.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {
    private  val PREFS_NAME = "history_prefs"
    private  val KEY_HISTORY_LIST = "history_list"
    private lateinit var adapter: ArrayAdapter<String>

    private lateinit var binding : ActivityHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val listHistory = getHistoryList(this)
        setAdapter(listHistory)

    }

    private fun setAdapter(list : ListHistory) {
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list.list.map { history -> "${history.location} \n ${history.date}" })
        binding.listHistory.adapter = adapter
    }

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun getHistoryList(context: Context): ListHistory {
        val sharedPreferences = getSharedPreferences(context)
        val gson = Gson()
        val json = sharedPreferences.getString(KEY_HISTORY_LIST, null)

        return if (json != null) {
            val type = object : TypeToken<ListHistory>() {}.type
            gson.fromJson(json, type)
        } else {
            ListHistory() // Si no existe la lista, devolver una lista vacía
        }
    }
}