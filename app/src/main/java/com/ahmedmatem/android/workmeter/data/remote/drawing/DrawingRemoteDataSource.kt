package com.ahmedmatem.android.workmeter.data.remote.drawing

import com.ahmedmatem.android.workmeter.data.Result
import com.ahmedmatem.android.workmeter.data.model.Drawing
import com.ahmedmatem.android.workmeter.utils.await
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import org.koin.java.KoinJavaComponent.inject
import java.io.IOException

class DrawingRemoteDataSource {
    private val storage: FirebaseStorage by inject(FirebaseStorage::class.java)
    private val db: FirebaseFirestore by inject(FirebaseFirestore::class.java)

    suspend fun loadDrawings(username: String) : Result<List<Drawing>> {
        return try {
            val userSiteDrawings = mutableListOf<Drawing>()

            val userSitesCollectionPath = "/users/$username/sites"
            val userSitesCollection = db.collection(userSitesCollectionPath)
            val userSitesCollectionQuerySnapshot = userSitesCollection.get().await()

            userSitesCollectionQuerySnapshot.mapNotNull { siteDocSnapshot ->
                val siteId = siteDocSnapshot.id
                val drawingsCollectionPath = "$userSitesCollectionPath/$siteId/drawings"
                val drawingsCollection = db.collection(drawingsCollectionPath)
                val drawingsCollectionQuerySnapshot = drawingsCollection.get().await()

                val siteDrawings = drawingsCollectionQuerySnapshot.mapNotNull { drawingDocSnapshot ->
                    Drawing(
                        drawingDocSnapshot.id,
                        siteId,
                        drawingDocSnapshot.data["name"].toString(),
                        drawingDocSnapshot.data["show"] as Boolean
                    )
                }
                userSiteDrawings.addAll(siteDrawings)
            }

            Result.Success(userSiteDrawings)

        } catch (e: Exception) {
            Result.Error(IOException("Error loading drawings.", e))
        }
    }

}