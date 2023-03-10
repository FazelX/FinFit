package dev.fazelx.finfit.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*
import dev.fazelx.finfit.R
import dev.fazelx.finfit.models.remote.Resource
import dev.fazelx.finfit.models.remote.Status
import dev.fazelx.finfit.models.remote.requests.LoginRequestModel
import dev.fazelx.finfit.models.remote.responses.LoginResponse
import dev.fazelx.finfit.utils.PreferenceHelper
import dev.fazelx.finfit.viewmodels.MoneyViewModel
import org.jetbrains.anko.longToast

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val REGEX = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    private val viewModel : MoneyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setClicklistenersOnViews()
    }

    private fun setClicklistenersOnViews() {
        btnLoginApply.setOnClickListener {
            if (isEmailValid() && isPasswordValid()){
                val loginRequestModel = LoginRequestModel(etLoginEmail.text.toString(), etLoginPassword.text.toString())
                viewModel.userLogin(loginRequestModel).observe(this, Observer {
                    val response = it
                    when (response.status) {
                        Status.SUCCESS -> {
                            loginUser(response)
                        }
                        Status.ERROR -> {
                            val error = response.message!!
                            longToast("error = $error")
                        }
                        Status.LOADING -> {

                        }
                    }
                })
            }
        }

        tvLoginBack.setOnClickListener{
            onBackPressed()
        }
    }

    private fun loginUser(response: Resource<LoginResponse>?) {
        PreferenceHelper.writeStringToPreference(this@LoginActivity, "token", response!!.data!!.token)
        PreferenceHelper.writeStringToPreference(this@LoginActivity, "email", response!!.data!!.user.email)
        PreferenceHelper.writeIntToPreference(this@LoginActivity, "newlogin", 1)
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun isPasswordValid(): Boolean {
        return if (etLoginPassword.text.toString().length >= 8) {
            true
        } else {
            etLoginPassword.error = "Password should contain at least 8 characters"
            false
        }
    }

    private fun isEmailValid(): Boolean {
        return if (etLoginEmail.text.toString().isNotEmpty() &&
            etLoginEmail.text.toString().matches(Regex(REGEX))
        ) {
            true
        } else {
            etLoginEmail.error = "Enter a valid email"
            false
        }
    }
}