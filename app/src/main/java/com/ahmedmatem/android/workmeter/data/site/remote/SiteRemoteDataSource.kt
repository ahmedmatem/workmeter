package com.ahmedmatem.android.workmeter.data.site.remote

import com.ahmedmatem.android.workmeter.data.site.local.Site
import com.ahmedmatem.android.workmeter.data.Result
import com.ahmedmatem.android.workmeter.data.model.LoggedInUser
import com.ahmedmatem.android.workmeter.utils.await
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.flow
import org.koin.java.KoinJavaComponent.inject
import java.io.IOException

class SiteRemoteDataSource {
    private val db: FirebaseFirestore by inject(FirebaseFirestore::class.java)

    fun loadSitesForUser(user: LoggedInUser) = flow {
        try{
            val siteRefResponse = db.collection("users/${user.displayName}/sites").get().await()
            val sites = siteRefResponse.mapNotNull { document ->
                val siteResponse = (document.data["siteRef"] as DocumentReference).get().await()
                siteResponse?.let { document ->
                    Site(
                        document.id,
                        user.userId,
                        document["name"].toString(),
                        document["postCode"].toString()
                    )
                }
            }
            emit(Result.Success(data = sites))
        } catch (e: Exception){
            emit(Result.Error(IOException("Error loading sites.", e)))
        }

    }
}