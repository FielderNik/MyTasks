package com.example.mytasks.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class Priority (val value: String) : Parcelable {
    NONE ("basic"),
    LOW ("low"),
    HIGH ("important")
}