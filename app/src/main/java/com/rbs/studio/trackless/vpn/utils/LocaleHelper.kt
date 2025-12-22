package com.rbs.studio.trackless.vpn.utils

import android.content.Context
import android.content.res.Configuration
import com.orhanobut.hawk.Hawk
import java.util.Locale

/**
 * Centralized locale management for multi-language support.
 *
 * This helper class manages the app's language settings by wrapping contexts
 * with the user-selected locale configuration.
 *
 * Usage in Activity:
 * ```
 * override fun attachBaseContext(newBase: Context) {
 *     super.attachBaseContext(LocaleHelper.wrapContext(newBase))
 * }
 * ```
 */
object LocaleHelper {

    private const val LANGUAGE_CODE_KEY = "language_code"
    private const val DEFAULT_LANGUAGE = "en"

    /**
     * Sets the app locale to the specified language code.
     *
     * @param context The base context to wrap
     * @param languageCode The language code (e.g., "en", "hi", "pt", "es", "ko", "id")
     * @return A context with the specified locale configuration
     */
    fun setLocale(context: Context, languageCode: String): Context {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)

        return context.createConfigurationContext(config)
    }

    /**
     * Wraps the provided context with the user's saved locale preference.
     *
     * Retrieves the saved language code from Hawk storage and applies it
     * to the context. If no language is saved, defaults to English.
     *
     * @param context The base context to wrap
     * @return A context wrapped with the saved locale
     */
    fun wrapContext(context: Context): Context {
        val savedLanguage = getPersistedLocale()
        return setLocale(context, savedLanguage)
    }

    /**
     * Retrieves the saved language code from persistent storage.
     *
     * @return The saved language code, or "en" if none is saved
     */
    fun getPersistedLocale(): String {
        return try {
            Hawk.get(LANGUAGE_CODE_KEY, DEFAULT_LANGUAGE)
        } catch (e: Exception) {
            DEFAULT_LANGUAGE
        }
    }
}
