package com.proyecto
import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Filter
import java.util.*

class CustomLocationAdapter(context: Context, resource: Int, objects: List<String>) :
    ArrayAdapter<String>(context, resource, objects) {

    private val originalList: List<String> = ArrayList(objects)
    private var filteredList: List<String> = ArrayList(objects)

    override fun getCount(): Int {
        return filteredList.size
    }

    override fun getItem(position: Int): String? {
        return filteredList[position]
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if (constraint != null && constraint.isNotEmpty()) {
                    val queryString = constraint.toString().lowercase(Locale.getDefault())

                    val filtered = originalList.filter {
                        it.lowercase(Locale.getDefault()).contains(queryString)
                    }

                    filterResults.values = filtered
                    filterResults.count = filtered.size
                } else {
                    filterResults.values = originalList
                    filterResults.count = originalList.size
                }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as List<String>
                notifyDataSetChanged()
            }
        }
    }
}
