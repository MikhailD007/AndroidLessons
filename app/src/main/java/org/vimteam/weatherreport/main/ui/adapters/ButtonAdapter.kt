package org.vimteam.weatherreport.main.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.GridView
import kotlinx.android.synthetic.main.keyboard_grid_item.view.*

class ButtonAdapter(
    context: Context,
    private val resource: Int,
    private val textViewResourceId: Int,
    private val buttons: Array<String>
): ArrayAdapter<String>(context, resource, textViewResourceId, buttons) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)
        view.btnGrid.text = buttons[position]
        view.btnGrid.setOnClickListener {
            (parent as GridView).performItemClick(view, position, 0)
        }
        return view
    }
}