package com.rbs.studio.trackless.vpn.activity;

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rbs.studio.trackless.vpn.adapter.LanguageAdapter
import com.rbs.studio.trackless.vpn.databinding.ActivityLanguageBinding
import com.rbs.studio.trackless.vpn.utils.LocaleHelper
import com.rbs.studio.trackless.vpn.utils.getLanguages

class LanguageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLanguageBinding
    private lateinit var languageAdapter : LanguageAdapter

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LocaleHelper.wrapContext(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLanguageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        languageAdapter = LanguageAdapter(
            context = this,
            languages = getLanguages(),
            onLanguageSelected = { language, position ->
                startActivity(Intent(this, LanguageDupActivity::class.java).also {
                    it.putExtra("language_code", language.snipCode)
                    it.putExtra("source_screen", LanguageActivity::class.java)
                })
                getSharedPreferences("app_prefs", MODE_PRIVATE)
                    .edit()
                    .putBoolean("language_set", true)
                    .apply();

                finish()
            }
        )
        languageAdapter.type = LanguageAdapter.Type.LANGUAGE_ONBOARDING
        binding.rvLanguage.apply {
            layoutManager = LinearLayoutManager(this@LanguageActivity, RecyclerView.VERTICAL, false)
            adapter = languageAdapter
        }
    }
}