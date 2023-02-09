package dev.fazelx.finfit.utils

import dev.fazelx.finfit.models.local.Expense

interface ItemClickListener {
    fun onItemClicked(expense: Expense)
    fun onItemLonClicked(expense: Expense)
}