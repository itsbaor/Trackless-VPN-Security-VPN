package com.rbs.studio.trackless.vpn.utils


import com.orhanobut.hawk.Hawk
import com.rbs.studio.trackless.vpn.R
import com.rbs.studio.trackless.vpn.model.Language


fun getLanguages(): List<Language>{
    val languageList = listOf(
        Language(R.string.english, R.drawable.ic_flag_en, "en", false),
        Language(R.string.hindi, R.drawable.ic_flag_hi, "hi", false),
        Language(R.string.portuguese, R.drawable.ic_flag_pt, "pt", false),
        Language(R.string.spanish, R.drawable.ic_flag_es, "es", false),
        Language(R.string.korean, R.drawable.ic_flag_ko, "ko", false),
        Language(R.string.indonesia, R.drawable.ic_flag_id, "id", false)
    )
    val languageCode = Hawk.get<String>("language_code", "en")
    return languageList.map { language ->
        if (language.snipCode == languageCode) {
            language.copy(isSelected = true)
        } else {
            language
        }
    }
}

fun getLanguage() : Language{
    val languageCode = Hawk.get<String>("language_code", "en")
    return getLanguages().firstOrNull { it.snipCode == languageCode }
        ?: Language(R.string.english, R.drawable.ic_flag_en, "en", true)
}

fun saveLanguage(language: Language) {
    Hawk.put("language_code", language.snipCode)
}