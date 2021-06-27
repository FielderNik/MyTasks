package com.example.mytasks.repositories

import com.example.mytasks.models.TaskEntity

class TaskList {
    companion object {
        val tasks = mutableListOf(
            TaskEntity(1, "Новая задача, которую надо сделать", "", 1, false),
            TaskEntity(2, "Новая задача, которую", "", 0, false),
            TaskEntity(3, "Новая задача", "", 2, true),
            TaskEntity(
                4,
                "Новая задача, которую надо сделать, которую надо сделать",
                "",
                2,
                false
            ),
            TaskEntity(
                5,
                "Новая задача, которую надо сделать, которую надо сделать, которую надо сделать",
                "",
                1,
                true
            ),
            TaskEntity(6, "Новая задача, которую надо сделать", "", 1, true),
            TaskEntity(7, "Новая задача", "", 0, false)
        )
    }
}