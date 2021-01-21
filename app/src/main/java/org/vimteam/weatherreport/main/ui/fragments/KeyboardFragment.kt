package org.vimteam.weatherreport.main.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.GridView
import androidx.fragment.app.Fragment
import org.vimteam.weatherreport.R
import org.vimteam.weatherreport.main.base.vibratePhone
import org.vimteam.weatherreport.main.domain.mappers.CalcButtonMapper
import org.vimteam.weatherreport.main.domain.contracts.KeyboardContract
import org.vimteam.weatherreport.main.ui.adapters.ButtonAdapter

class KeyboardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val rootView: View = inflater.inflate(R.layout.keyboard, container, false)

        val buttons = resources.getStringArray(R.array.buttons)
        val btnAdapter =
            ButtonAdapter(requireContext(), R.layout.keyboard_grid_item, R.id.btnGrid, buttons)
        val btn = rootView.findViewById<View>(R.id.gridViewButtons) as GridView
        btn.adapter = btnAdapter
        btn.onItemClickListener = OnItemClickListener { _, _, position, _ ->
            vibratePhone()
            (activity as KeyboardContract).onKeyPressed(
                button = CalcButtonMapper.valueOf(position) ?: CalcButtonMapper.DIGIT,
                digit = CalcButtonMapper.getDigitValue(position)
            )
        }
        return rootView
    }

}