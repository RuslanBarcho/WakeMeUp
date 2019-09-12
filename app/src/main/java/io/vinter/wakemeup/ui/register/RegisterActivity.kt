package io.vinter.wakemeup.ui.register

import androidx.lifecycle.Observer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import io.vinter.wakemeup.R
import io.vinter.wakemeup.network.form.RegisterForm
import kotlinx.android.synthetic.main.activity_register.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {

    private val viewModel: RegisterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        register.setOnClickListener{
            if ((register_login.text.toString() != "") and (register_password.text.toString() != "")) {
                if (register_password.text.toString() == register_confirm_password.text.toString()) {
                    viewModel.register(RegisterForm(register_login.text.toString(), register_password.text.toString(), "https://pbs.twimg.com/profile_images/378800000532546226/dbe5f0727b69487016ffd67a6689e75a_400x400.jpeg"))
                } else {
                    Toast.makeText(this, "Password does not match confirm", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.message.observe(this, Observer {
            if (it != null){
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                finish()
                viewModel.message.postValue(null)
            }
        })

        viewModel.error.observe(this, Observer {
            if (it != null) {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                viewModel.error.postValue(null)
            }
        })
    }
}
