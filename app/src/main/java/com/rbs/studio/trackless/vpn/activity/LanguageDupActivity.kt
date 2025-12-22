package com.rbs.studio.trackless.vpn.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.orhanobut.hawk.Hawk
import com.rbs.studio.trackless.vpn.adapter.LanguageAdapter
import com.rbs.studio.trackless.vpn.databinding.ActivityLanguageDupBinding
import com.rbs.studio.trackless.vpn.utils.getLanguages
import com.rbs.studio.trackless.vpn.utils.saveLanguage

class LanguageDupActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLanguageDupBinding
    private lateinit var languageAdapter : LanguageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLanguageDupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val languageCode = intent.getStringExtra("language_code")
        val languages = getLanguages().map { language ->
            if (language.snipCode == languageCode) {
                language.copy(isSelected = true)
            } else {
                language.copy(isSelected = false)
            }
        }



        languageAdapter = LanguageAdapter(
            context = this,
            languages = languages,
            onLanguageSelected = { language, position ->
            }
        )
        languageAdapter.type = LanguageAdapter.Type.LANGUAGE_SELECTION
        binding.rvLanguage.apply {
            layoutManager = LinearLayoutManager(this@LanguageDupActivity, RecyclerView.VERTICAL, false)
            adapter = languageAdapter
        }

        binding.ibLanguageCheck.setOnClickListener {
            val selectedLanguage = languages.firstOrNull { it.isSelected }
            saveLanguage(selectedLanguage ?: languages[0])
            Hawk.put(LanguageActivity::class.java.simpleName, true)
            startActivity(Intent(this, FirstBoardingActivity::class.java))
            finish()
        }
    }
}