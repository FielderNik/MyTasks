package com.example.mytasks.forms

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytasks.R
import com.example.mytasks.adapters.SwipeToCompleteCallback
import com.example.mytasks.adapters.SwipeToDeleteCallback
import com.example.mytasks.adapters.TaskListAdapter
import com.example.mytasks.databinding.FragmentMainScreenBinding
import com.example.mytasks.models.TaskEntity
import com.example.mytasks.viewmodels.MainScreenViewModel


class MainScreenFragment : Fragment(), TaskListAdapter.TaskAdapterCallback {

    lateinit var viewModelMainScreen: MainScreenViewModel

    lateinit var adapterTaskRV: TaskListAdapter
    lateinit var binding: FragmentMainScreenBinding
    private var isCompletedTaskShow = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelMainScreen = ViewModelProvider(this).get(MainScreenViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainScreenBinding.inflate(inflater, container, false)

        adapterTaskRV = TaskListAdapter(this)
        binding.rvMainTasks.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMainTasks.adapter = adapterTaskRV

        viewModelMainScreen.getTaskWithFilterCompleted(false)

        viewModelMainScreen.allTasks.observe(viewLifecycleOwner, Observer { tasks ->
            val qtyCompletedTask = tasks.filter { it.isComplete == true }.count()
            binding.tvSubTitleTasks.text = String.format(getString(R.string.title_completed_task), qtyCompletedTask)
            adapterTaskRV.updateList(tasks)
        })


        val deleteItemTouchHelper = ItemTouchHelper(getDeleteTouchHelper())
        deleteItemTouchHelper.attachToRecyclerView(binding.rvMainTasks)

        val completeItemTouchHelper = ItemTouchHelper(getCompleteTouchHelper())
        completeItemTouchHelper.attachToRecyclerView(binding.rvMainTasks)


        binding.fabAddTask.setOnClickListener{
            openAddTaskForm()
        }

        binding.ibShowTasks.setOnClickListener{
            changeShowCompletedTask()
        }

        binding.tvTitleMainScreen.setOnClickListener {
            (binding.rvMainTasks.layoutManager as LinearLayoutManager).scrollToPosition(0)
        }


        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if (isCompletedTaskShow) {
            binding.ibShowTasks.setImageResource(R.drawable.ic_visibility_off)
            viewModelMainScreen.getAllTasks()
        } else {
            binding.ibShowTasks.setImageResource(R.drawable.ic_btn_visibility)
            viewModelMainScreen.getTaskWithFilterCompleted(false)
        }
    }

    fun openAddTaskForm(){
        val fragmentAddTask = AddEditTaskFragment.newInstance(null)
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.flFragmentContainer, fragmentAddTask, null)
            ?.addToBackStack("backstack")
            ?.commit()
    }

    override fun onTaskItemClicked(v: View, task: TaskEntity, position: Int) {
        val fragmentEditTask = AddEditTaskFragment.newInstance(task)
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.flFragmentContainer, fragmentEditTask, null)
            ?.addToBackStack("backstack")
            ?.commit()
    }

    override fun onCheckItemClicked(task: TaskEntity) {
        task.isComplete = true
        viewModelMainScreen.editTask(task)
    }

    override fun onUnCheckItemClicked(task: TaskEntity) {
        task.isComplete = false
        viewModelMainScreen.editTask(task)
    }

    fun changeShowCompletedTask(){
        if (!isCompletedTaskShow){
            isCompletedTaskShow = true
            binding.ibShowTasks.setImageResource(R.drawable.ic_visibility_off)
            viewModelMainScreen.getAllTasks()
        } else {
            isCompletedTaskShow = false
            binding.ibShowTasks.setImageResource(R.drawable.ic_btn_visibility)
            viewModelMainScreen.getTaskWithFilterCompleted(false)
        }
    }

    private fun getDeleteTouchHelper(): SwipeToDeleteCallback {
        return object : SwipeToDeleteCallback(requireContext()) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val currentTask = adapterTaskRV.tasks[position]
                adapterTaskRV.removeItem(position)
                viewModelMainScreen.deleteTask(currentTask)
            }
        }
    }

    private fun getCompleteTouchHelper(): SwipeToCompleteCallback {
        return object : SwipeToCompleteCallback(requireContext()) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val currentTask = adapterTaskRV.tasks[position]
                currentTask.isComplete = true
                viewModelMainScreen.editTask(currentTask)
                adapterTaskRV.removeItem(position)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainScreenFragment()
    }


}