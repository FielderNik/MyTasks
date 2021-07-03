package com.example.mytasks.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var taskBody: String?,
    var deadLine: String?,
    var priority: Int,
    var isComplete: Boolean,

    ) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(taskBody)
        parcel.writeString(deadLine)
        parcel.writeInt(priority)
        parcel.writeByte(if (isComplete) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TaskEntity> {
        override fun createFromParcel(parcel: Parcel): TaskEntity {
            return TaskEntity(parcel)
        }

        override fun newArray(size: Int): Array<TaskEntity?> {
            return arrayOfNulls(size)
        }
    }
}
