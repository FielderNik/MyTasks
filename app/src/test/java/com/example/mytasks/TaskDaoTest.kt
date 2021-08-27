package com.example.mytasks

import com.example.mytasks.models.TaskEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test

class TaskDaoTest: AndroidBaseUnitTest() {

    @Test
    fun `when db empty then return 0`() = testDispatcher.runBlockingTest {
        testScope.launch(Dispatchers.IO) {
            val tasks = tasksDao.getAllTask()
            Assert.assertEquals(0, tasks.size)
        }
    }

    @Test
    fun `when db has 3 tasks then return 3` () = testDispatcher.runBlockingTest {
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
                tasksDao.addTask(task)
            }

            val tasks = tasksDao.getAllTask()
            Assert.assertEquals(3, tasks.size)
        }
    }

}