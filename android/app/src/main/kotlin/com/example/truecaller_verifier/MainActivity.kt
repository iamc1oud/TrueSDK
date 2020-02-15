package com.example.truecaller_verifier

import androidx.annotation.NonNull;
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugins.GeneratedPluginRegistrant
import io.flutter.plugin.common.MethodChannel
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle


class MainActivity: FlutterActivity() {
    private val CHANNEL = "samples.flutter.dev/battery"
    private var user_email : String? = null

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        GeneratedPluginRegistrant.registerWith(flutterEngine)

        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler {
            // Note: this method is invoked on the main thread.
            call, result ->
            if(call.method.equals("startActivity")){
                 val intent = Intent(this, TrueCallerActivity::class.java)
                 startActivity(intent)

                 result.success("Activity started")
             }
            else {
                result.notImplemented()
            }
        }
    }


}