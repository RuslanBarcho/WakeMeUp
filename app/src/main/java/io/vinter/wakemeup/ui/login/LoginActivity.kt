package io.vinter.wakemeup.ui.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import io.vinter.wakemeup.R
import io.vinter.wakemeup.data.PreferencesRepository
import io.vinter.wakemeup.network.form.LoginForm
import io.vinter.wakemeup.ui.main.MainActivity
import io.vinter.wakemeup.ui.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        val preferencesRepository = PreferencesRepository(this)

        login_button.setOnClickListener {
            viewModel.getToken(LoginForm(login_login.text.toString(), login_password.text.toString()))
        }

        register_button.setOnClickListener{
            val switchToRegister = Intent(this, RegisterActivity::class.java)
            this.startActivityForResult(switchToRegister, 1)
        }

        viewModel.userData.observe(this, Observer {
            if (it != null){
                preferencesRepository.setUserInfo(it)
                this.startActivity(Intent(this, MainActivity::class.java))
                finish()
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
