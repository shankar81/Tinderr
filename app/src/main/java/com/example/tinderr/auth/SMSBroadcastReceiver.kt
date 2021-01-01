package com.example.tinderr.auth

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status

private const val TAG = "SMSBroadcastReceiver"

class SMSBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) {
        if (SmsRetriever.SMS_RETRIEVED_ACTION == intent.action) {
            val extras = intent.extras!!
            val status = extras.get(SmsRetriever.EXTRA_STATUS) as Status

            when (status.statusCode) {
                CommonStatusCodes.SUCCESS -> {
                    val message = extras.get(SmsRetriever.EXTRA_SMS_MESSAGE)
                    Log.d(TAG, "onReceive: $message")
                }
                CommonStatusCodes.TIMEOUT -> {
                    Log.d(TAG, "onReceive: TIMEOUT")
                }
            }
        }
    }
}