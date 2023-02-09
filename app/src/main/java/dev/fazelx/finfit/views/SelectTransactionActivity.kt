package dev.fazelx.finfit.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_select_transaction.*
import dev.fazelx.finfit.R

class SelectTransactionActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_transaction)
        setClicklistenersOnViews()
    }

    private fun setClicklistenersOnViews() {
        cvSelectTransactionIncome.setOnClickListener {
            val intent = Intent(this@SelectTransactionActivity, AddIncomeActivity::class.java)
            startActivity(intent)
            finish()
        }
        cvSelectTransactionExpense.setOnClickListener {
            val intent = Intent(this@SelectTransactionActivity, AddExpenseActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}