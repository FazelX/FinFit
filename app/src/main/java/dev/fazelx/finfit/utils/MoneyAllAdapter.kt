package dev.fazelx.finfit.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.fazelx.finfit.R
import dev.fazelx.finfit.models.local.Expense

class MoneyAllAdapter(val list : ArrayList<Expense>, val itemClickListener: ItemClickListener) : RecyclerView.Adapter<MoneyAllViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoneyAllViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return MoneyAllViewHolder(view, itemClickListener)
    }

    override fun onBindViewHolder(holder: MoneyAllViewHolder, position: Int) {
        val expense = list[position]
        holder.setData(expense)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}