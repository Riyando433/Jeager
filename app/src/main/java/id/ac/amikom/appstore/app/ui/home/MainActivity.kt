package id.ac.amikom.appstore.app.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.ac.amikom.appstore.app.R
import id.ac.amikom.appstore.app.databinding.ActivityMainBinding
import androidx.databinding.DataBindingUtil
import id.ac.amikom.appstore.app.ui.auth.AppstoreAuth
import id.ac.amikom.appstore.app.ui.auth.AuthActivity

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.buttonLogout.setOnClickListener {
            AppstoreAuth.logout(this){
                startActivity(Intent(this, AuthActivity::class.java))
                finish()
            }
        }
    }
}