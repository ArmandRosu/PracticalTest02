//package ro.pub.cs.systems.eim.practialtest02v2
//
//import android.content.Intent
//import android.os.Bundle
//import android.util.Log
//import android.widget.Button
//import android.widget.EditText
//import android.widget.TextView
//import androidx.appcompat.app.AppCompatActivity
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import okhttp3.OkHttpClient
//import okhttp3.Request
//import org.json.JSONArray
//
//class MainActivity : AppCompatActivity() {
//
//    private lateinit var wordEditText: EditText
//    private lateinit var lookupButton: Button
//    private lateinit var resultTextView: TextView
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        wordEditText = findViewById(R.id.wordEditText)
//        lookupButton = findViewById(R.id.lookupButton)
//        resultTextView = findViewById(R.id.resultTextView)
//
//        lookupButton.setOnClickListener {
//            val word = wordEditText.text.toString()
//            if (word.isNotEmpty()) {
//                fetchDefinition(word)
//            }
//        }
//    }
//
//    private fun fetchDefinition(word: String) {
//        CoroutineScope(Dispatchers.IO).launch {
//            try {
//                val client = OkHttpClient()
//                val request = Request.Builder()
//                    .url("https://api.dictionaryapi.dev/api/v2/entries/en/$word")
//                    .build()
//
//                val response = client.newCall(request).execute()
//                val responseBody = response.body?.string()
//
//                responseBody?.let {
//                    val definition = parseDefinition(it)
//                    sendBroadcast(definition)
//                    runOnUiThread {
//                        resultTextView.text = definition
//                    }
//                }
//            } catch (e: Exception) {
//                Log.e("MainActivity", "Error fetching definition", e)
//                runOnUiThread {
//                    resultTextView.text = "Error fetching definition"
//                }
//            }
//        }
//    }
//
//    private fun parseDefinition(jsonResponse: String): String {
//        return try {
//            val jsonArray = JSONArray(jsonResponse)
//            val jsonObject = jsonArray.getJSONObject(0)
//            val meanings = jsonObject.getJSONArray("meanings")
//            val definitions = meanings.getJSONObject(0).getJSONArray("definitions")
//            definitions.getJSONObject(0).getString("definition")
//        } catch (e: Exception) {
//            "No definition found"
//        }
//    }
//
//    private fun sendBroadcast(definition: String) {
//        val intent = Intent("ro.pub.cs.systems.eim.practialtest02v2.DEFINITION_BROADCAST")
//        intent.putExtra("definition", definition)
//        Log.d("MainActivity", "Sending broadcast with definition: $definition") // Added log
//        sendBroadcast(intent)
//    }
//}



package ro.pub.cs.systems.eim.practialtest02v2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray

class MainActivity : AppCompatActivity() {

    private lateinit var wordEditText: EditText
    private lateinit var lookupButton: Button
    private lateinit var newActivityButton: Button
    private lateinit var resultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wordEditText = findViewById(R.id.wordEditText)
        lookupButton = findViewById(R.id.lookupButton)
        resultTextView = findViewById(R.id.resultTextView)
        newActivityButton = findViewById(R.id.newActivityButton)

        lookupButton.setOnClickListener {
            val word = wordEditText.text.toString()
            if (word.isNotEmpty()) {
                fetchDefinition(word)
            }
        }

        newActivityButton.setOnClickListener {
            // Navigate to NewActivity
            val intent = Intent(this, NewActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchDefinition(word: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val client = OkHttpClient()
                val request = Request.Builder()
                    .url("https://api.dictionaryapi.dev/api/v2/entries/en/$word")
                    .build()

                val response = client.newCall(request).execute()
                val responseBody = response.body?.string()

                responseBody?.let {
                    val definition = parseDefinition(it)
                    sendBroadcast(definition)
                    runOnUiThread {
                        resultTextView.text = definition
                    }
                }
            } catch (e: Exception) {
                Log.e("MainActivity", "Error fetching definition", e)
                runOnUiThread {
                    resultTextView.text = "Error fetching definition"
                }
            }
        }
    }

    private fun parseDefinition(jsonResponse: String): String {
        return try {
            val jsonArray = JSONArray(jsonResponse)
            val jsonObject = jsonArray.getJSONObject(0)
            val meanings = jsonObject.getJSONArray("meanings")
            val definitions = meanings.getJSONObject(0).getJSONArray("definitions")
            definitions.getJSONObject(0).getString("definition")
        } catch (e: Exception) {
            "No definition found"
        }
    }

    private fun sendBroadcast(definition: String) {
        val intent = Intent("ro.pub.cs.systems.eim.practialtest02v2.DEFINITION_BROADCAST")
        intent.putExtra("definition", definition)
        Log.d("MainActivity", "Sending broadcast with definition: $definition")
        sendBroadcast(intent)
    }
}
