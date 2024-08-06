package com.xavier_carpentier.realestatemanager.datasource.picture

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.xavier_carpentier.realestatemanager.datasource.property.Property

@Entity(tableName = "picture",
    foreignKeys = [ForeignKey(
        entity = Property::class,
        parentColumns = ["id"],
        childColumns = ["propertyId"],
        onDelete = ForeignKey.CASCADE
    )])
data class Picture(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id :Int = 0,
    @ColumnInfo(name="propertyId", index=true)
    val propertyId :Int,
    val description :String,
    @ColumnInfo(name="imageData", typeAffinity=ColumnInfo.BLOB)
    val imageData : ByteArray
)
