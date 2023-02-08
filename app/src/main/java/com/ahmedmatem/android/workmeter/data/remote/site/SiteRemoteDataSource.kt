package com.ahmedmatem.android.workmeter.data.remote.site

import com.ahmedmatem.android.workmeter.data.model.Site
import com.ahmedmatem.android.workmeter.data.Result
import com.ahmedmatem.android.workmeter.data.model.LoggedInUser
import com.ahmedmatem.android.workmeter.utils.await
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.java.KoinJavaComponent.inject
import java.io.IOException

class SiteRemoteDataSource {
    private val db: FirebaseFirestore by inject(FirebaseFirestore::class.java)

    fun loadSitesForUser(user: LoggedInUser) : Flow<Result<List<Site>>> = flow {
        try{
            val userSitesQuerySnapshot =
                db.collection("users/${user.displayName}/sites").get().await()
            val sites = userSitesQuerySnapshot.mapNotNull { userSiteDocSnapshot ->
                val siteDocSnapshot =
                    (userSiteDocSnapshot.data["siteRef"] as DocumentReference).get().await()
                siteDocSnapshot?.let { doc ->
                    Site(doc.id, doc["name"].toString(),  doc["postCode"].toString())
                }
            }
            emit(Result.Success(data = sites))
        } catch (e: Exception){
            emit(Result.Error(IOException("Error loading sites.", e)))
        }

    }
}