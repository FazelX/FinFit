package dev.fazelx.finfit.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dev.fazelx.finfit.models.remote.Resource
import dev.fazelx.finfit.models.remote.requests.CreateExpenseRequestModel
import dev.fazelx.finfit.models.remote.requests.LoginRequestModel
import dev.fazelx.finfit.models.remote.responses.LoginResponse
import dev.fazelx.finfit.models.remote.requests.SignupRequestModel
import dev.fazelx.finfit.models.remote.responses.SignupResponseModel
import dev.fazelx.finfit.repository.ExpenseRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import dev.fazelx.finfit.models.local.Expense
import javax.inject.Inject

@HiltViewModel
class MoneyViewModel @Inject constructor(val repo: ExpenseRepo) : ViewModel() {

    fun userLogin(loginRequestModel: LoginRequestModel): LiveData<Resource<LoginResponse>>{

        return liveData(Dispatchers.IO) {
            val result = repo.login(loginRequestModel)
            emit(result)
        }
    }

    fun userSignup(signupRequestModel: SignupRequestModel): LiveData<Resource<SignupResponseModel>>{

        return liveData(Dispatchers.IO) {
            val result = repo.signup(signupRequestModel)
            emit(result)
        }
    }

    fun addNewTransaction(token : String, createExpenseRequestModel: CreateExpenseRequestModel){
        repo.addNewTransaction(token, createExpenseRequestModel)
    }

    fun logout(token : String){
        repo.logout(token)
    }

    fun deleteUser(token : String){
        repo.deleteUser(token)
    }

    fun getAllTransactions(): LiveData<List<Expense>> {
        return repo.getAllTransactions()
    }

    fun deleteTransaction(token: String, expense: Expense){
        repo.deleteTransaction(token, expense)
    }

    fun refreshTransactionsFromAPI(token: String){
        repo.getAllTransactionsFromAPI(token)
    }


    fun getTotalIncome(): LiveData<Int> {
        return repo.getTotalIncome()
    }

    fun getTotalExpenses(): LiveData<Int> {
        return repo.getTotalExpenses()
    }
}