package com.example.medmobile.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.medmobile.R
import com.example.medmobile.inflate
import com.example.medmobile.mvvm.model.Pharmacy
import kotlinx.android.synthetic.main.item_pharmacy.view.*

class PharmacyAdapter : RecyclerView.Adapter<PharmacyAdapter.PharmacyHolder>() {
    val pharmacies = mutableListOf<Pharmacy>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PharmacyHolder(parent.inflate(R.layout.item_pharmacy))

    override fun getItemCount() = pharmacies.size

    override fun onBindViewHolder(holder: PharmacyHolder, position: Int) =
        holder.bind(pharmacies[position])

    class PharmacyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(pharmacy: Pharmacy) {
            itemView.pharmacy_id.text = pharmacy.id.toString()
            itemView.pharmacy_name.text = pharmacy.title
            itemView.pharmacy_supervisor.text =
                itemView.context.getString(R.string.pharmacy_supervisor, pharmacy.supervisor)
            itemView.pharmacy_address.text =
                itemView.context.getString(R.string.manufacturer_address, pharmacy.address)
            itemView.pharmacy_phone.text =
                itemView.context.getString(R.string.manufacturer_phone, pharmacy.phone)
        }
    }
}