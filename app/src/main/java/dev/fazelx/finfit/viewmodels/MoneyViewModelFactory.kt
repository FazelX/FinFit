package dev.fazelx.finfit.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.fazelx.finfit.repository.ExpenseRepo

class MoneyViewModelFactory(val repo: ExpenseRepo) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MoneyViewModel(repo) as T
    }

}