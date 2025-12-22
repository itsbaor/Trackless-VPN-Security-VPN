package com.rbs.studio.trackless.vpn.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rbs.studio.trackless.vpn.R
import com.rbs.studio.trackless.vpn.model.Language


class LanguageAdapter(
    private val context: Context,
    private val languages: List<Language>,
    private val onLanguageSelected: (Language, Int) -> Unit):
    RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LanguageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_language, parent, false)
        return LanguageViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: LanguageViewHolder,
        position: Int
    ) {
        holder.bind(languages[position], position)
    }

    override fun getItemCount() = languages.size

    inner class LanguageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val imageFlag = itemView.findViewById<ImageView>(R.id.img_flag)
        private val tvLanguageNameSelected = itemView.findViewById<TextView>(R.id.tv_language_name_selected)
        private val tvLanguageNameUnSelected = itemView.findViewById<TextView>(R.id.tv_language_name_unselected)
        private val radioButton = itemView.findViewById<ImageButton>(R.id.radio_button)
        fun bind(language: Language, position: Int) {
            imageFlag.setImageResource(language.flag)
            tvLanguageNameSelected.setText(language.name)
            tvLanguageNameUnSelected.setText(language.name)

            if (language.isSelected && type == Type.LANGUAGE_SELECTION) {
                radioButton.setImageResource(R.drawable.ic_radio_button_checked)
                tvLanguageNameSelected.visibility = View.VISIBLE
                tvLanguageNameUnSelected.visibility = View.GONE
                itemView.isSelected = true
            } else {
                radioButton.setImageResource(R.drawable.ic_radio_button_unchecked)
                tvLanguageNameSelected.visibility = View.GONE
                tvLanguageNameUnSelected.visibility = View.VISIBLE
                itemView.isSelected = false
            }

            itemView.setOnClickListener {
                handleLanguageSelection(language, position)
            }
            radioButton.setOnClickListener {
                itemView.performClick()
            }
        }

        @SuppressLint("NotifyDataSetChanged")
        private fun handleLanguageSelection(language: Language, position: Int) {
            if(type == Type.LANGUAGE_SELECTION) {
                languages.forEach { it.isSelected = false }
                language.isSelected = true
                notifyDataSetChanged()
            }
            onLanguageSelected(language, position)
        }
    }

    var type = Type.LANGUAGE_SELECTION

    enum class Type{
        LANGUAGE_SELECTION,
        LANGUAGE_ONBOARDING
    }
}