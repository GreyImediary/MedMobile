package com.example.medmobile.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.medmobile.R
import com.example.medmobile.inflate
import com.example.medmobile.mvvm.model.MedicineRequest
import kotlinx.android.synthetic.main.item_medicine_requset.view.*

class MedicineRequestAdapter : RecyclerView.Adapter<MedicineRequestAdapter.MedicineRequestHolder>() {
    val medicineRequests: MutableList<MedicineRequest> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MedicineRequestHolder(parent.inflate(R.layout.item_medicine_requset))

    override fun getItemCount() = medicineRequests.size

    override fun onBindViewHolder(holder: MedicineRequestHolder, position: Int) =
        holder.bind(medicineRequests[position])

    class MedicineRequestHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(medicineRequest: MedicineRequest) {
            itemView.medicine_request_id.text = medicineRequest.id.toString()
            itemView.medicine_request_name.text = medicineRequest.medicine.title
            itemView.medicine_request_count.text = itemView.context.getString(R.string.medicine_request_count, medicineRequest.quantity)
            itemView.medicine_request_status.text = medicineRequest.status
        }
    }
}