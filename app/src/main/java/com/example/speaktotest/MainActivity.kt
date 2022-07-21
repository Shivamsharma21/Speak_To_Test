package com.example.speaktotest

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import java.util.*


class MainActivity : AppCompatActivity() {

    val languagesCodesVoiceRSS = arrayListOf(
        "ar-sa", "bg-bg", "ca-es", "zh-cn", "hr-hr",
        "cs-cz", "da-dk", "nl-nl", "en-us", "fi-fi", "fr-fr", "de-de", "el-gr", "he-il",
        "hi-in", "hu-hu", "id-id", "it-it", "ja-jp", "ko-kr", "ms-my", "nb-no", "pl-pl",
        "pt-pt", "ro-ro", "ru-ru", "sk-sk", "sl-si", "es-es", "sv-se", "ta-in", "th-th",
        "tr-tr", "vi-vn"
    )
    val languagesVoiceRSS = arrayListOf(
        "Arabic", "Bulgarian", "Catalan", "Chinese",
        "Croatian", "Czech", "Danish", "Dutch",
        "English", "Finnish", "French", "German", "Greek",
        "Hebrew", "Hindi", "Hungarian", "Indonesian", "Italian", "Japanese",
        "Korean", "Malay", "Norwegian", "Polish", "Portuguese", "Romanian",
        "Russian", "Slovak", "Slovenian", "Spanish", "Swedish", "Tamil", "Thai", "Turkish",
        "Vietnamese"
    )

    private var langcode =""
    private var langcodeIndex =0
    private lateinit var spinner:Spinner
    private lateinit var iv_mic: ImageView
    private var tv_Speech_to_text: TextView? = null
    private val REQUEST_CODE_SPEECH_INPUT = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spinner = findViewById(R.id.language_spinner)

        var adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,languagesVoiceRSS)
        spinner.onItemSelectedListener  = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Log.d("Selected ->",p0?.adapter?.getItem(p2).toString())
                Log.d("Selected ->", p2.toString())
                langcodeIndex = p2
                 langcode = languagesCodesVoiceRSS.get(langcodeIndex)
                Log.d("Selected ->", langcode)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        spinner.adapter = adapter

        iv_mic = findViewById(R.id.iv_mic);
        tv_Speech_to_text = findViewById(R.id.tv_speech_to_text);
        iv_mic.setOnClickListener {

           var intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            //Specify language
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, langcode)
            // Specify language model
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            // Specify how many results to receive
            intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, REQUEST_CODE_SPEECH_INPUT);
            // Start listening
            try {
                startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
            } catch (e: Exception) {
                Toast
                    .makeText(
                        this@MainActivity, " " + e.message,
                        Toast.LENGTH_SHORT
                    )
                    .show()
            }
        }
    }
    override fun onActivityResult(
        requestCode: Int, resultCode: Int,
        @Nullable data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                val result = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS
                )
                Log.d("RESUULT _>",result!!.get(0))
                tv_Speech_to_text!!.text = Objects.requireNonNull(result)!![0]
            }
        }
    }
}