package com.ahmedmatem.android.workmeter.data.site.remote

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.java.KoinJavaComponent.inject

class SiteRemoteDataSource {
    private val db: FirebaseFirestore by inject(FirebaseFirestore::class.java)

    fun loadSitesForUser(uid: String){
        db.collection("users")
            .whereEqualTo("uid", uid)
            .get()
            .addOnSuccessListener { result ->
                for (document in result){
                    Log.d("FIRESTORE-DEBUG", "loadSitesForUser: ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w("FIRESTORE-DEBUG", "Error getting documents.", exception)
            }
    }
}