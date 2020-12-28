//package com.fsoc.template.common.service
//
//import com.google.android.gms.tasks.OnCompleteListener
//import com.google.firebase.iid.FirebaseInstanceId
//import com.orhanobut.logger.Logger
//
//object FireBaseToken {
//    fun retrieveToken(onCompleted: (token: String) -> Unit) {
//        FirebaseInstanceId.getInstance().instanceId
//            .addOnCompleteListener(OnCompleteListener { task ->
//                if (!task.isSuccessful) {
//                    Logger.w("getInstanceId failed", task.exception)
//                    onCompleted("")
//                    return@OnCompleteListener
//                }
//
//                // Get new Instance ID token
//                val token = task.result?.token
//
//                // Log token
//                Logger.d("Firebase token: $token")
//                onCompleted(token ?: "")
//            })
//    }
//
//    fun unregisterToken() {
//        try {
//            FirebaseInstanceId.getInstance().deleteInstanceId()
//            Logger.d("Firebase unregisterToken: success")
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//}