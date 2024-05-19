package com.example.musicPlayer
//
//import android.content.Context
//import android.net.ConnectivityManager
//import android.os.Bundle
//import android.widget.Toast
//import androidx.activity.enableEdgeToEdge
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
//import androidx.recyclerview.widget.RecyclerView.LayoutManager.Properties
//import com.example.musicPlayer.databinding.ActivityFavouriteBinding
//import com.example.musicPlayer.databinding.ActivityFeedbackBinding
//import javax.mail.Authenticator
//import javax.mail.Session
//import javax.mail.Transport
//import javax.mail.internet.InternetAddress
//import javax.mail.internet.MimeMessage
//
//class FeedbackActivity : AppCompatActivity() {
//    lateinit var binding: ActivityFeedbackBinding
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setTheme(R.style.coolPinkNav)
//        binding = ActivityFeedbackBinding.inflate(layoutInflater)
//        enableEdgeToEdge()
//        setContentView(binding.root)
//        supportActionBar?.title = "Feedback"
//        val feedbackMsg = binding.feedbackMsgFA.text.toString() + "\n" + binding.emailFA.text.toString()
//        binding.topicFA.text.toString()
//        binding.sendFA.setOnClickListener {
//            val feedbackMsg = binding.feedbackMsgFA.text.toString() + "\n" + binding.emailFA.text.toString()
//            val subject = binding.topicFA.text.toString()
//            val userName = "phuongtkp7@gmail.com"
//            val pass = "phuongtkp7@gmail"
//            val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//            if(feedbackMsg.isNotEmpty() && subject.isNotEmpty() && (cm.activeNetworkInfo?.isConnectedOrConnecting == true)) {
//                Thread {
//                    try {
//                        val properties = Properties()
//                        properties["mail.smtp.auth"] = "true"
//                        properties["mail.smtp.starttls.enable"] = "true"
//                        properties["mail.smtp.host"] = "smtp.gmail.com"
//                        properties["mail.smtp.port"] = "587"
//                        val session = Session.getInstance(properties, object : Authenticator() {
//                            override fun getPasswordAuthentication(): PasswordAuthentication {
//                                return PasswordAuthentication(userName, pass)
//                            }
//                        })
//                        val mail = MimeMessage(session)
//                        mail.subject = subject
//                        mail.setText(feedbackMsg)
//                        mail.setFrom(InternetAddress(userName))
//                        mail.setRecipients(
//                            Message.RecipientType.TO,
//                            InternetAddress.parse(userName)
//                        )
//                        Transport.send(mail)
//                    } catch (e: Exception) {
//                        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
//                    }
//                }.start()
//                Toast.makeText(this, "Thanks For Feedback!!", Toast.LENGTH_SHORT).show()
//                finish()
//            }
//            else Toast.makeText(this, feedbackMsg, Toast.LENGTH_LONG).show()
//        }
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//    }
//}