package com.example.truecaller_verification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.NonNull
import com.example.truecaller.R
import com.truecaller.android.sdk.*
import kotlinx.android.synthetic.main.activity_true_caller.*
import kotlinx.android.synthetic.main.activity_true_caller.view.*
import java.util.*

class TrueCallerActivity : AppCompatActivity() {
    var name : String? = null

    private val sdkCallback = object : ITrueCallback {

        override fun onSuccessProfileShared(@NonNull trueProfile : TrueProfile){
            Log.d("onSuccess" , "Verified")
        }

        override fun onFailureProfileShared(@NonNull trueError: TrueError) {
            if(trueError.errorType == 3) {
                Log.d("partnerKey", "Incorrect partner key")
            }
        }

        override fun onVerificationRequired() {
            Log.d("onVerification", "Verification required" )
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_true_caller)
        val trueScope = TrueSdkScope.Builder(this, sdkCallback)
                .consentMode(TrueSdkScope.CONSENT_MODE_POPUP )
                .consentTitleOption( TrueSdkScope.SDK_CONSENT_TITLE_GET_STARTED )
                .footerType( TrueSdkScope.FOOTER_TYPE_SKIP )
                .build()
        TrueSDK.init(trueScope)

        // Change language
        val locale = Locale("en")
        TrueSDK.getInstance().setLocale(locale)
        TrueSDK.getInstance().getUserProfile(this)
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result = TrueSDK.getInstance().onActivityResultObtained( this,resultCode, data);
        Log.d("data", result.toString())

    }

    fun setName(view: View) {
        textView.text = "Some data"
    }
}