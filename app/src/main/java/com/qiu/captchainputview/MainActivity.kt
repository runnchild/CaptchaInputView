package com.qiu.captchainputview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.qiu.captcha.CaptchaInputView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val inputView = findViewById<CaptchaInputView>(R.id.captchaView)

        inputView.setOnInputCompleteListener {
            Toast.makeText(this@MainActivity, "验证码：$it", Toast.LENGTH_LONG).show()
        }
    }
}
