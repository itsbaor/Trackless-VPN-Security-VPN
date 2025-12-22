package com.rbs.studio.trackless.vpn.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import java.io.Serializable

data class Language(
    @StringRes val name: Int,
    @DrawableRes val flag: Int,
    val snipCode: String,
    var isSelected: Boolean = false
): Serializable
