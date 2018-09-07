# CaptchaInputView
自定义验证码输入框

一个直接继承EditText的自定义输入验证码控件，本质就是一个EditText，没有添加其他View作为输入框，相比其他实现方式更灵活，使用成本更低，直接按照EditText的使用方法来就是了，比如明文密文，输入类型；<br>
本控件也只是提供一个简单的实现，没有更多样式，重要的是实现思路，因为简单所以更好二次加工。相比直接使用他人代码，我更喜欢的是改造，拿来就用永远还是别人的，其次理解代码之后改需求也更快<br>

#用法
implement 'com.qiurc:CaptchaInputView:1.0.0'<br>

```Java
        
        val inputView = findViewById<CaptchaInputView>(R.id.captchaView)

        inputView.setOnInputCompleteListener {
            Toast.makeText(this@MainActivity, "验证码：$it", Toast.LENGTH_LONG).show()
        }

xml中
<com.qiu.captcha.CaptchaInputView
        android:id="@+id/captchaView"
        android:layout_marginStart="38dp"
        android:layout_marginEnd="38dp"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
```

![eg](https://github.com/qrc0403/CaptchaInputView/blob/master/app/E70B2F2F-C746-401e-97E4-0B03590C5640.png)

