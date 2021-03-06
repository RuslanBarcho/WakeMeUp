package io.vinter.wakemeup.ui.login

import androidx.lifecycle.Observer
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import io.vinter.wakemeup.R
import io.vinter.wakemeup.data.preferences.PreferencesRepository
import io.vinter.wakemeup.network.form.LoginForm
import io.vinter.wakemeup.ui.main.MainActivity
import io.vinter.wakemeup.ui.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModel()
    private val preferences: PreferencesRepository = get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_button.setOnClickListener {
            if (login_login.text.isNotEmpty() && login_password.text.isNotEmpty()) {
                viewModel.getToken(LoginForm(login_login.text.toString(), login_password.text.toString()))
            } else {
                Toast.makeText(this, getString(R.string.error_empty_fields), Toast.LENGTH_SHORT).show()
            }
        }

        register_button.setOnClickListener {
            val switchToRegister = Intent(this, RegisterActivity::class.java)
            this.startActivityForResult(switchToRegister, 1)
        }

        viewModel.state.observe(this, Observer {
            when (it){
                is LoginState.Success -> {
                    preferences.setUserInfo(it.userData)
                    this.startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                is LoginState.Loading -> login_button.isEnabled = false
                is LoginState.Initial -> login_button.isEnabled = true
            }
        })

        viewModel.error.observe(this, Observer {
            if (it != null){
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                viewModel.error.postValue(null)
            }
        })
    }
}
