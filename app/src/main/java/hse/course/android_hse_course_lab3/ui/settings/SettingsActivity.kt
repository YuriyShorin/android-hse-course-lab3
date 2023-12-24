package hse.course.android_hse_course_lab3.ui.settings

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity

import hse.course.android_hse_course_lab3.R
import hse.course.android_hse_course_lab3.databinding.SettingsActivityLayoutBinding
import hse.course.android_hse_course_lab3.utils.Languages
import hse.course.android_hse_course_lab3.ui.MainActivity


class SettingsActivity: AppCompatActivity() {

    private lateinit var binding: SettingsActivityLayoutBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SettingsActivityLayoutBinding.inflate(layoutInflater)
        init()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent = Intent(this, MainActivity::class.java)
        when (item.itemId) {
            android.R.id.home -> {
                saveChanges()
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun init() {
        setContentView(binding.root)
        setSupportActionBar(binding.settingsToolbar)
        supportActionBar?.show()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val intent = Intent(this, MainActivity::class.java)
        onBackPressedDispatcher.addCallback(this) {
            saveChanges()
            startActivity(intent)
            finish()
        }

        sharedPreferences =
            applicationContext.getSharedPreferences(
                getString(R.string.storage_key),
                Context.MODE_PRIVATE
            )


        ArrayAdapter.createFromResource(
            this,
            R.array.languages_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.languageSpinner.adapter = adapter
        }

        val favoriteTopic =
            sharedPreferences.getString(getString(R.string.favorite_topic_key), null)
        if (favoriteTopic != null) {
            binding.favoriteTopicEditText.setText(favoriteTopic)
        }

        val language: String? = sharedPreferences.getString(getString(R.string.language_key), null)
        if (language == null) {
            binding.languageSpinner.setSelection(Languages.getLanguageIndex(Languages.English.name))
        } else {
            binding.languageSpinner.setSelection(Languages.getLanguageIndex(language))
        }
    }

    private fun saveChanges() {
        val favoriteTopic: String = binding.favoriteTopicEditText.text.toString()
        val language: String = binding.languageSpinner.selectedItem.toString()

        sharedPreferences.edit().putString(getString(R.string.favorite_topic_key), favoriteTopic)
            .apply()
        Log.v("INFO", "Favorite topic saved: $favoriteTopic")

        sharedPreferences.edit().putString(getString(R.string.language_key), language).apply()
        Log.v("INFO", "Language saved: $language")
    }
}