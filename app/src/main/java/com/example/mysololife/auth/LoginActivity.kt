package com.example.mysololife.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.mysololife.MainActivity
import com.example.mysololife.R
import com.example.mysololife.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    
    private lateinit var binding : ActivityLoginBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login)

        binding.loginBtn.setOnClickListener {

            val email = binding.emailArea.text.toString()
            val password = binding.passwordArea.text.toString()

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        val intent = Intent(this, MainActivity::class.java)
//회원가입 진행 후 메인 엑티비티로 화면이 전환되고 뒤로가기 버튼을 눌렀을때 화면이 꺼지도록 설정하는 문장
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)

                        Toast.makeText(this,"로그인 성공",Toast.LENGTH_LONG).show()

                    } else {

                        Toast.makeText(this,"로그인 실패",Toast.LENGTH_LONG).show()
                    }
                }
        }


    }
}