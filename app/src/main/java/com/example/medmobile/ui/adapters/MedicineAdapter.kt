package com.example.medmobile.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.medmobile.R
import com.example.medmobile.inflate
import com.example.medmobile.mvvm.model.Medicine
import kotlinx.android.synthetic.main.item_medicine.view.*

class MedicineAdapter : RecyclerView.Adapter<MedicineAdapter.MedicineHolder>() {
    val medicines: MutableList<Medicine> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MedicineHolder(parent.inflate(R.layout.item_medicine))

    override fun getItemCount() = medicines.size

    override fun onBindViewHolder(holder: MedicineHolder, position: Int) =
        holder.bind(medicines[position])

    class MedicineHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(medicine: Medicine) {
            itemView.medicine_id.text = medicine.id.toString()
            itemView.medicine_name.text = medicine.title
            itemView.medicine_type.text = medicine.type
            itemView.medicine_description.text = medicine.description
            itemView.medicine_price.text =
                itemView.context.getString(R.string.medicine_price, medicine.price)
            itemView.medicine_creator.text =
                itemView.context.getString(R.string.medicine_creator, medicine.manufacturer.title)
        }
    }
}