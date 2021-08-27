package com.example.mytasks.models

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

}
