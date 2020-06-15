package com.example.medmobile.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.medmobile.R
import com.example.medmobile.inflate
import com.example.medmobile.mvvm.model.Manufacturer
import kotlinx.android.synthetic.main.item_manufacturer.view.*

class ManufacturersAdapter : RecyclerView.Adapter<ManufacturersAdapter.ManufacturerViewHolder>() {
    val manufacturers: MutableList<Manufacturer> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ManufacturerViewHolder(parent.inflate(R.layout.item_manufacturer))

    override fun getItemCount() = manufacturers.size

    override fun onBindViewHolder(holder: ManufacturerViewHolder, position: Int) =
        holder.bind(manufacturers[position])

    class ManufacturerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(manufacturer: Manufacturer) {
            itemView.manufacturer_id.text = manufacturer.id.toString()
            itemView.manufacturer_name.text = manufacturer.title
            itemView.manufacturer_inn.text =
                itemView.context.getString(R.string.manufacturer_inn, manufacturer.inn)
            itemView.manufacturer_phone.text =
                itemView.context.getString(R.string.manufacturer_phone, manufacturer.phone)
            itemView.manufacturer_address.text =
                itemView.context.getString(R.string.manufacturer_address, manufacturer.address)
        }
    }
}