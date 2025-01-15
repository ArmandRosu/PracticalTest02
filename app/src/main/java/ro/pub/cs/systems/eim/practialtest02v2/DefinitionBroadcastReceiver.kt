package ro.pub.cs.systems.eim.practialtest02v2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class DefinitionBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("DefinitionBroadcastReceiver", "onReceive triggered") // Added log
        val definition = intent.getStringExtra("definition")
        Log.d("DefinitionBroadcastReceiver", "Received Definition: $definition") // Added log
        Toast.makeText(context, "Definition: $definition", Toast.LENGTH_LONG).show()
    }
}
