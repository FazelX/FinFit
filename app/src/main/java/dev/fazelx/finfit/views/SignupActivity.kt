package dev.fazelx.finfit.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_signup.*
import dev.fazelx.finfit.R
import dev.fazelx.finfit.models.remote.Resource
import dev.fazelx.finfit.models.remote.Status
import dev.fazelx.finfit.models.remote.requests.SignupRequestModel
import dev.fazelx.finfit.models.remote.responses.SignupResponseModel
import dev.fazelx.finfit.utils.PreferenceHelper
import dev.fazelx.finfit.viewmodels.MoneyViewModel
import org.jetbrains.anko.longToast

@AndroidEntryPoint
class SignupActivity : AppCompatActivity() {

    private val REGEX = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    private val viewModel : MoneyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        setClickListenersOnViews()
    }

    private fun setClickListenersOnViews() {
        btnSignupApply.setOnClickListener {
            if (isEmailValid() && isPasswordValid() && doPasswordsMatch()){
                val signupRequestModel = SignupRequestModel("UserName",etSignupEmail.getText().toString(), etSignupPassword.getText().toString(), 30)
                viewModel.userSignup(signupRequestModel).observe(this, Observer {
                    val response = it
                    when (response.status) {
                        Status.SUCCESS -> {
                            signUpUser(response)
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

        tvSignupBack.setOnClickListener{
            onBackPressed()
        }
    }

    private fun signUpUser(response: Resource<SignupResponseModel>?) {
        PreferenceHelper.writeStringToPreference(this@SignupActivity, "token", response!!.data!!.token)
        PreferenceHelper.writeStringToPreference(this@SignupActivity, "email", response!!.data!!.user!!.email)
        PreferenceHelper.writeIntToPreference(this@SignupActivity, "newlogin", 1)
        val intent = Intent(this@SignupActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }


    private fun isPasswordValid(): Boolean {
        return if (etSignupPassword.text.toString().length >= 8) {
            true
        } else {
            etSignupPassword.error = "Password should contain at least 8 characters"
            false
        }
    }

    private fun doPasswordsMatch(): Boolean {
        return if (etSignupPassword.text.toString().equals(etSignupConfirmPassword.text.toString())) {
            true
        } else {
            etSignupConfirmPassword.error = "Passwords should match"
            false
        }
    }

    private fun isEmailValid(): Boolean {
        return if (etSignupEmail.text.toString().isNotEmpty() &&
            etSignupEmail.text.toString().matches(Regex(REGEX))
        ) {
            true
        } else {
            etSignupEmail.error = "Enter a valid email"
            false
        }
    }
}