package com.example.medmobile.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.medmobile.R
import com.example.medmobile.inflate
import com.example.medmobile.mvvm.model.PharmSell
import kotlinx.android.synthetic.main.item_pharm_sell.view.*
import kotlinx.android.synthetic.main.item_sell.view.*

class PharmSellAdapter : RecyclerView.Adapter<PharmSellAdapter.PharmSellViewHolder>() {
    val pharmSells: MutableList<PharmSell> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PharmSellViewHolder(parent.inflate(R.layout.item_pharm_sell))

    override fun getItemCount() = pharmSells.size

    override fun onBindViewHolder(holder: PharmSellViewHolder, position: Int) =
        holder.bind(pharmSells[position])

    class PharmSellViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(pharmSell: PharmSell) {
            val context = itemView.context

            itemView.pharm_sell_id.text = pharmSell.id.toString()
            itemView.pharm_sell_date.text = context.getString(R.string.sell_date, pharmSell.date.split("T")[0])
            itemView.pharm_sell_quantity.text = context.getString(R.string.sell_quantity, pharmSell.quantity)
            itemView.pharm_sell_medicine_name.text = pharmSell.medicine.title
            itemView.pharm_sell_client.text = context.getString(R.string.pharm_sell_client, pharmSell.pharmacy.title)
            itemView.pharm_check_id.text = context.getString(R.string.pharm_sell_check_id, pharmSell.checkId)
        }
    }
}