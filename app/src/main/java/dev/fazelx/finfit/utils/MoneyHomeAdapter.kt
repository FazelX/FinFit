package dev.fazelx.finfit.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.fazelx.finfit.R
import dev.fazelx.finfit.models.local.Expense

class MoneyHomeAdapter(val list : ArrayList<Expense>) : RecyclerView.Adapter<MoneyHomeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoneyHomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return MoneyHomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: MoneyHomeViewHolder, position: Int) {
        val expense = list[position]
        holder.setData(expense)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}