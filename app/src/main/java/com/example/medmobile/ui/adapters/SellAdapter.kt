package com.example.medmobile.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.medmobile.R
import com.example.medmobile.inflate
import com.example.medmobile.mvvm.model.Sell
import kotlinx.android.synthetic.main.item_sell.view.*

class SellAdapter : RecyclerView.Adapter<SellAdapter.SellViewHolder>() {
    val sells: MutableList<Sell> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SellViewHolder(parent.inflate(R.layout.item_sell))

    override fun getItemCount() = sells.size

    override fun onBindViewHolder(holder: SellViewHolder, position: Int) =
        holder.bind(sells[position])

    class SellViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(sell: Sell) {
            val context = itemView.context

            itemView.sell_id.text = sell.id.toString()
            itemView.sell_medicine_name.text = sell.medicine.title
            itemView.sell_date.text = context.getString(R.string.sell_date, sell.date.split("T")[0])
            itemView.sell_production_date.text =
                context.getString(R.string.sell_production_date, sell.producedAt.split("T")[0])
            itemView.sell_invoice_id.text =
                context.getString(R.string.sell_invoice_id, sell.invoiceId)
            itemView.sell_quantity.text = context.getString(R.string.sell_quantity, sell.quantity)
            itemView.sell_shelf_life.text =
                context.getString(R.string.sell_shelf_life, sell.shelfLife)
        }
    }
}