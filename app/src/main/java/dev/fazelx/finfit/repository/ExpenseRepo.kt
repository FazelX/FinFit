package dev.fazelx.finfit.repository

import androidx.lifecycle.LiveData
import dev.fazelx.finfit.models.remote.Resource
import dev.fazelx.finfit.models.remote.ResponseHandler
import dev.fazelx.finfit.models.remote.MoneyAPI
import dev.fazelx.finfit.models.remote.requests.CreateExpenseRequestModel
import dev.fazelx.finfit.models.remote.requests.LoginRequestModel
import dev.fazelx.finfit.models.remote.responses.LoginResponse
import dev.fazelx.finfit.models.remote.requests.SignupRequestModel
import dev.fazelx.finfit.models.remote.responses.GetExpenseResponseModel
import dev.fazelx.finfit.models.remote.responses.SignupResponseModel
import kotlinx.coroutines.*
import dev.fazelx.finfit.models.local.Expense
import dev.fazelx.finfit.models.local.ExpenseDAO
import java.lang.Exception
import javax.inject.Inject

class ExpenseRepo @Inject constructor(val expenseDAO: ExpenseDAO, val api: MoneyAPI) {

    private val responseHandler = ResponseHandler()

    suspend fun login(loginRequestModel: LoginRequestModel): Resource<LoginResponse> {
        return try {
            val response = api.login(loginRequestModel)
            responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    suspend fun signup(signupRequestModel: SignupRequestModel): Resource<SignupResponseModel> {
        return try {
            val response = api.signup(signupRequestModel)
            responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    fun getAllTransactionsFromAPI(token : String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = api.getAllTransactionsFromAPI(token)
            saveTransactionsToDB(response)
        }
    }

    private fun saveTransactionsToDB(response: GetExpenseResponseModel) {
        val listOfTransactions = ArrayList<Expense>()
        response.forEach {
            val date = it.createdAt.substring(0, 10)
            val desc = it.description.split("*#*#*")
            val newTransaction = Expense(it.title, desc[0], it.status, date, desc[1], desc[2], it._id)
            listOfTransactions.add(newTransaction)
        }
        expenseDAO.deleteAllTransactionsFromDB()
        expenseDAO.addAllTransactionsToDB(listOfTransactions)
    }

    fun addNewTransaction(token: String, createExpenseRequestModel : CreateExpenseRequestModel){
        CoroutineScope(Dispatchers.IO).launch {
            val response = api.addTransactionToAPI(token, createExpenseRequestModel)
            val date = response.createdAt!!.substring(0, 10)
            val desc = response.description!!.split("*#*#*")
            val newTransaction = Expense(response.title!!, desc[0], response.status!!, date!!, desc[1], desc[2], response.id!!)
            expenseDAO.addTransactionToDB(newTransaction)
        }
    }


    fun getAllTransactions(): LiveData<List<Expense>> {
        return expenseDAO.getAllTransactionsFromDB()
    }


    fun logout(token: String) {
        CoroutineScope(Dispatchers.IO).launch {
            api.logout(token)
            expenseDAO.deleteAllTransactionsFromDB()
        }
    }

    fun deleteUser(token: String) {
        CoroutineScope(Dispatchers.IO).launch {
            api.deleteUserFromAPI(token)
            expenseDAO.deleteAllTransactionsFromDB()
        }
    }

    fun deleteTransaction(token: String, expense: Expense) {
        CoroutineScope(Dispatchers.IO).launch {
            api.deleteTransactionFromAPI(token, expense.id!!)
            expenseDAO.deleteTransactionFromDB(expense)
        }
    }

    fun getTotalIncome(): LiveData<Int> {
        return expenseDAO.getTotalIncome()
    }

    fun getTotalExpenses(): LiveData<Int> {
        return expenseDAO.getTotalExpenses()
    }
}