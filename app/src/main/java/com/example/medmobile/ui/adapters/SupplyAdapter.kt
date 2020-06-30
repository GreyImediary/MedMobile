package com.example.medmobile.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.medmobile.R
import com.example.medmobile.inflate
import com.example.medmobile.mvvm.model.Supply
import kotlinx.android.synthetic.main.item_supply.view.*

class SupplyAdapter : RecyclerView.Adapter<SupplyAdapter.SupplyViewHolder>() {
    val supplies: MutableList<Supply> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SupplyViewHolder(parent.inflate(R.layout.item_supply))

    override fun getItemCount() = supplies.size

    override fun onBindViewHolder(holder: SupplyViewHolder, position: Int) =
        holder.bind(supplies[position])

    class SupplyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(supply: Supply) {
            itemView.supply_id.text = supply.id.toString()
            itemView.supply_status.text = supply.status
            itemView.supply_positions.text = supply.positions
        }
    }
}