package com.proyecto

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.proyecto.databinding.FragmentSelectLocationBinding

class SelectLocationFragment : BottomSheetDialogFragment()  {
    private lateinit var binding: FragmentSelectLocationBinding

    lateinit var success: ((pab: Int, floor : Int, label : String) -> Unit)
    lateinit var close : () -> Unit

    private lateinit var adapter: CustomLocationAdapter
    lateinit var itemList : List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelectLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setListeners()
    }

    private fun setListeners() {
        binding.listLocations.setOnItemClickListener { parent, view, position, id ->
            val element = parent.getItemAtPosition(position) as String
            success.invoke(getPabellonNumber(element), getPisoNumber(element), element)
        }
        binding.btnClose.setOnClickListener {
            close.invoke()
        }
        binding.edtInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filter.filter(binding.edtInput.text.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setAdapter() {
        adapter = CustomLocationAdapter(requireContext(), android.R.layout.simple_list_item_1, itemList)
        binding.listLocations.adapter = adapter
    }

    fun getPabellonNumber(pabellonPiso: String): Int {
        val pabellonMap = mapOf(
            "Pabellón A" to 1,
            "Pabellón B" to 2,
            "Pabellón C" to 3,
            "Pabellón D" to 4
        )

        val pabellon = pabellonPiso.substringBefore(" Piso")
        return pabellonMap[pabellon]!!.toInt()
    }

    fun getPisoNumber(pabellonPiso: String): Int {
        val regex = Regex("Piso (\\d+)")
        val matchResult = regex.find(pabellonPiso)
        return matchResult?.groups?.get(1)?.value!!.toInt()
    }
}