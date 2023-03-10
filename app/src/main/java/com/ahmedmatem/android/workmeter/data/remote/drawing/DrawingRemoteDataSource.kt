package com.ahmedmatem.android.workmeter.data.remote.drawing

import com.ahmedmatem.android.workmeter.data.Result
import com.ahmedmatem.android.workmeter.data.model.Drawing
//import com.ahmedmatem.android.workmeter.utils.await
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
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
                val siteId = (siteDocSnapshot.data["siteRef"] as DocumentReference).get().await().id
                val userSiteId = siteDocSnapshot.id
                val drawingsCollectionPath = "$userSitesCollectionPath/$userSiteId/drawings"
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

    fun downloadSiteDrawings(siteId: String, vararg drawings: String) = flow<List<ByteArray>> {
        val byteArrayList = mutableListOf<ByteArray>()
        val storageRef = storage.reference
        val drawingPaths = mutableListOf<String>()
        drawings.map { drawing ->
            // Transform to path to the drawing in the storage
            "site/$siteId/$drawing"
        }.forEach { path ->
            val drawingRef = storageRef.child(path)
            val drawingBytes = drawingRef.getBytes(ONE_MEGABYTE).await()
            byteArrayList.add(drawingBytes)
        }
        emit(byteArrayList)
    }

    companion object {
        const val ONE_MEGABYTE : Long = 1024 * 1024
    }
}