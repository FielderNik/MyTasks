package com.example.mytasks.models

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "task_table")
data class TaskEntity(
    @PrimaryKey
    @NonNull
    @SerializedName("id")
    var id: String,
    @SerializedName("text")
    var taskBody: String,
    @SerializedName("deadline")
    var deadline: Int,
    @SerializedName("importance")
    var priority: String,
    @SerializedName("done")
    var isComplete: Boolean,
    @SerializedName("created_at")
    var createdAt: Int,
    @SerializedName("updated_at")
    var updatedAt: Int,
    @Expose
    var isSynchronised: Boolean,
    @Expose
    var isDeleted: Boolean,
    ): Parcelable {

    constructor(
        taskBody: String,
        deadline: Int,
        priority: String,
        isComplete: Boolean,
        createdAt: Int,
        updatedAt: Int,
    ) : this(UUID.randomUUID().toString(), taskBody, deadline, priority, isComplete, createdAt, updatedAt, isSynchronised = false, isDeleted = false)

//    constructor(parcel: Parcel) : this(
//        parcel.readString(),
//        parcel.readString(),
//        parcel.readString(),
//        parcel.readInt(),
//        parcel.readByte() != 0.toByte()
//    ) {
//    }
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeString(id)
//        parcel.writeString(taskBody)
//        parcel.writeString(deadLine)
//        parcel.writeInt(priority)
//        parcel.writeByte(if (isComplete) 1 else 0)
//    }

//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<TaskEntity> {
//        override fun createFromParcel(parcel: Parcel): TaskEntity {
//            return TaskEntity(parcel)
//        }
//
//        override fun newArray(size: Int): Array<TaskEntity?> {
//            return arrayOfNulls(size)
//        }
//    }
}
