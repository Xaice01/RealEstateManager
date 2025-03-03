package com.xavier_carpentier.realestatemanager.data.content_provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import androidx.room.Room
import com.xavier_carpentier.realestatemanager.datasource.database.DatabaseRealEstateManager

class RealEstateContentProvider : ContentProvider() {

    companion object {
        // Nouvelle autorité unique pour éviter tout conflit avec FileProvider
        private const val AUTHORITY = "com.xavier_carpentier.realestatemanager.customprovider"
        val BASE_CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY")
        @Suppress("unused")  // pour les autres applications
        val PROPERTY_URI: Uri = Uri.withAppendedPath(BASE_CONTENT_URI, "property")
        @Suppress("unused")  // pour les autres applications
        val PICTURE_URI: Uri = Uri.withAppendedPath(BASE_CONTENT_URI, "picture")

        // Codes for the UriMatcher
        private const val CODE_PROPERTY_LIST = 1
        private const val CODE_PROPERTY_ITEM = 2
        private const val CODE_PICTURE_LIST = 3

        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY, "property", CODE_PROPERTY_LIST)
            addURI(AUTHORITY, "property/#", CODE_PROPERTY_ITEM)
            addURI(AUTHORITY, "picture", CODE_PICTURE_LIST)
        }
    }

    private lateinit var database: DatabaseRealEstateManager

    override fun onCreate(): Boolean {
        context?.let { ctx ->
            database = Room.databaseBuilder(
                ctx,
                DatabaseRealEstateManager::class.java,
                "RealEstateManager_database"
            ).fallbackToDestructiveMigration().build()
        }
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor {
        return when (uriMatcher.match(uri)) {
            CODE_PROPERTY_LIST -> {
                database.propertyDao().getAllPropertyCursor()
            }
            CODE_PROPERTY_ITEM -> {
                val id = uri.lastPathSegment?.toIntOrNull()
                    ?: throw IllegalArgumentException("ID invalide dans l'URI: $uri")
                database.propertyDao().getPropertyCursor(id)
            }
            CODE_PICTURE_LIST -> {
                database.pictureDao().getAllPictureCursor()
            }
            else -> throw IllegalArgumentException("URI non supporté: $uri")
        }
    }

    override fun getType(uri: Uri): String {
        return when (uriMatcher.match(uri)) {
            CODE_PROPERTY_LIST -> "vnd.android.cursor.dir/vnd.$AUTHORITY.property"
            CODE_PROPERTY_ITEM -> "vnd.android.cursor.item/vnd.$AUTHORITY.property"
            CODE_PICTURE_LIST -> "vnd.android.cursor.dir/vnd.$AUTHORITY.picture"
            else -> throw IllegalArgumentException("Type MIME non supporté pour l'URI: $uri")
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        throw UnsupportedOperationException("The insert operation is not supported.")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        throw UnsupportedOperationException("The delete operation is not supported.")
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        throw UnsupportedOperationException("The update operation is not supported.")
    }
}