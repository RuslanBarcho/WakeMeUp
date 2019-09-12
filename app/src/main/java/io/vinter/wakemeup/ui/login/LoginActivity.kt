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
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
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
