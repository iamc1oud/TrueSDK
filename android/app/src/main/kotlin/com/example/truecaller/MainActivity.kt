package com.example.truecaller

import androidx.annotation.NonNull;
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugins.GeneratedPluginRegistrant
import io.flutter.plugin.common.MethodChannel
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.os.PersistableBundle
import androidx.lifecycle.LifecycleRegistry
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import androidx.lifecycle.Lifecycle
import com.truecaller.android.sdk.*
import java.util.logging.Logger


class MainActivity: FlutterActivity() {
    private val CHANNEL = "samples.flutter.dev/battery"

    private val sdkCallback = object : ITrueCallback {

        override fun onSuccessProfileShared(trueProfile: TrueProfile) {

        }

        override fun onVerificationRequired() {

        }

        override fun onFailureProfileShared(trueError: TrueError) {

        }

    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        val trueScope = TrueSdkScope.Builder(this, sdkCallback)
                .consentMode(TrueSdkScope.CONSENT_MODE_FULLSCREEN )
                .consentTitleOption( TrueSdkScope.SDK_CONSENT_TITLE_VERIFY )
                .footerType( TrueSdkScope.FOOTER_TYPE_SKIP )
                .build()
        TrueSDK.init(trueScope)
    }

    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        GeneratedPluginRegistrant.registerWith(flutterEngine);



        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler {
            // Note: this method is invoked on the main thread.
            call, result ->
             if(call.method == "getTrueCaller") {
                result.success(getTrueCaller())
            }
            else {
                result.notImplemented()
            }
        }
    }

    private fun getTrueCaller()  : Boolean {
        val trueScope = TrueSdkScope.Builder(this, sdkCallback)
                .consentMode(TrueSdkScope.CONSENT_MODE_FULLSCREEN )
                .consentTitleOption( TrueSdkScope.SDK_CONSENT_TITLE_VERIFY )
                .footerType( TrueSdkScope.FOOTER_TYPE_SKIP )
                .build()
           TrueSDK.init(trueScope)
            return TrueSDK.getInstance().isUsable()
    }
}
