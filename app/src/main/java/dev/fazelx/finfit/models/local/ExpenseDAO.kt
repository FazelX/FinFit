package dev.fazelx.finfit.models.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ExpenseDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAllTransactionsToDB(expenseList: ArrayList<Expense>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTransactionToDB(expense: Expense)

    @Query("select * from expenses")
    fun getAllTransactionsFromDB() : LiveData<List<Expense>>

    @Delete
    fun deleteTransactionFromDB(expense: Expense)

    @Update
    fun updateTransactionInDB(task: Expense)

    @Query("delete from expenses")
    fun deleteAllTransactionsFromDB()

    @Query("select sum(amount) from expenses where type = 'income'")
    fun getTotalIncome() : LiveData<Int>

    @Query("select sum(amount) from expenses where type = 'expense'")
    fun getTotalExpenses() : LiveData<Int>

}