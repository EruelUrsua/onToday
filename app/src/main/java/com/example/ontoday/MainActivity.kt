package com.example.ontoday

import android.os.Bundle
import android.text.SpannableString
import android.text.style.StrikethroughSpan
import android.util.SparseBooleanArray
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.util.isEmpty
import com.example.ontoday.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var task:String
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var itemlist = arrayListOf<String>()
        var adapter =ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, itemlist)

        binding.add.setOnClickListener {
            addTask(itemlist, adapter)
        }

        binding.edit.setOnClickListener {
            editTask(adapter, itemlist)
        }

        binding.listView.setOnItemClickListener { adapterView, view, i, l ->
            task = itemlist.get(i)
        }

        binding.delete.setOnClickListener {
            deleteTask(adapter, itemlist)
        }
    }

    private fun deleteTask(
        adapter: ArrayAdapter<String>,
        itemlist: ArrayList<String>
    ) {
        val position: SparseBooleanArray = binding.listView.checkedItemPositions
        val count = binding.listView.count
        var item = count - 1
        if (binding.listView.checkedItemPositions.isEmpty()){
            Toast.makeText(this, "Must select a task", Toast.LENGTH_SHORT).show()
        }
        else{
        while (item >= 0) {
            if (position.get(item)) {
                adapter.remove(itemlist.get(item))
            }
            item--
        }}
        position.clear()
        adapter.notifyDataSetChanged()
    }

    private fun editTask(
        adapter: ArrayAdapter<String>,
        itemlist: ArrayList<String>
    ) {
        val position: SparseBooleanArray = binding.listView.checkedItemPositions
        val count = binding.listView.count
        var item = count - 1
        if (binding.listView.checkedItemPositions.isEmpty()){
            Toast.makeText(this, "Must select a task", Toast.LENGTH_SHORT).show()
        }
        else{
        while (item >= 0) {
            if (position.get(item)) {
                if (binding.editText.text.isBlank()) {
                    Toast.makeText(this, "Must input a text", Toast.LENGTH_SHORT).show()
                } else {
                    adapter.remove(itemlist.get(item))
                    itemlist.add(binding.editText.text.toString())
                    binding.listView.adapter = adapter
                    binding.editText.text.clear()
                }
            }
            item--
        }}
        position.clear()
        adapter.notifyDataSetChanged()
    }

    private fun addTask(
        itemlist: ArrayList<String>,
        adapter: ArrayAdapter<String>
    ) {
        if (binding.editText.text.isBlank()) {
            Toast.makeText(this, "Must input a text", Toast.LENGTH_SHORT).show()
        } else {
            itemlist.add(binding.editText.text.toString())
            binding.listView.adapter = adapter
            adapter.notifyDataSetChanged()
            binding.editText.text.clear()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.chooseTheme){
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}