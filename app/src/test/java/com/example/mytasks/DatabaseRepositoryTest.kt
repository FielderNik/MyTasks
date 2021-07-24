package com.example.mytasks

import com.example.mytasks.models.TaskEntity
import com.example.mytasks.repositories.DatabaseRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test
import org.koin.core.component.inject


class DatabaseRepositoryTest: AndroidBaseUnitTest() {

    private val databaseRepository: DatabaseRepositoryImpl by inject()

    @Test
    fun `repo when db empty then return 0`() = testDispatcher.runBlockingTest {
        testScope.launch(Dispatchers.IO) {
            val tasks = databaseRepository.getAllTasks()
            Assert.assertEquals(0, tasks.size)
        }
    }


    @Test
    fun `repo when db has 3 tasks then return 3` () = testDispatcher.runBlockingTest {
        testScope.launch(Dispatchers.IO) {
            repeat(3){
                val task = TaskEntity(
                    taskBody = "taskBody",
                    deadline = 0,
                    priority = "low",
                    isComplete = false,
                    createdAt = 0,
                    updatedAt = 0
                )
                databaseRepository.addTask(task)
            }

            val tasks = databaseRepository.getAllTasks()
            Assert.assertEquals(3, tasks.size)
        }
    }

    @Test
    fun `repo when db has 1 task then return this name` () = testDispatcher.runBlockingTest {
        val body = "taskBody"
        testScope.launch(Dispatchers.IO) {
            val task = TaskEntity(
                id = "task1",
                taskBody = body,
                deadline = 0,
                priority = "low",
                isComplete = false,
                createdAt = 0,
                updatedAt = 0,
                isDeleted = false,
                isSynchronised = false
            )
            databaseRepository.addTask(task)

            val tasks = databaseRepository.getAllTasks()
            val taskBody = tasks[0].taskBody
            Assert.assertEquals(body, taskBody)
        }
    }

}