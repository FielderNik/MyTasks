package com.example.mytasks.forms

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytasks.MainActivity
import com.example.mytasks.R
import com.example.mytasks.adapters.SwipeToDeleteCallback
import com.example.mytasks.adapters.TaskListAdapter
import com.example.mytasks.databinding.FragmentMainScreenBinding
import com.example.mytasks.models.Priority
import com.example.mytasks.models.TaskEntity
import com.example.mytasks.repositories.TaskList
import com.example.mytasks.viewmodels.MainScreenViewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MainScreenFragment : Fragment(), TaskListAdapter.TaskAdapterCallback {

    lateinit var viewModelMainScreen: MainScreenViewModel

    lateinit var adapterTaskRV: TaskListAdapter
    lateinit var binding: FragmentMainScreenBinding
    private var isCompletedTaskShow = false

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
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



        viewModelMainScreen.tasksLiveData.observe(viewLifecycleOwner, Observer { tasks ->
            val qtyCompletedTask = tasks.filter { it.isComplete == true }.count()
            binding.tvSubTitleTasks.text = "Выполнено - $qtyCompletedTask"

            val notCompletedTasks =tasks.filter { it.isComplete == false }
            adapterTaskRV.updateList(notCompletedTasks)
        })




        val itemTouchHelperCallback  =
            object : SwipeToDeleteCallback(requireContext()) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    val item = adapterTaskRV.tasks.get(position)
                    adapterTaskRV.removeItem(position)
                }



            }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvMainTasks)


        binding.fabAddTask.setOnClickListener{
            openAddTaskForm()
        }

        binding.ibShowTasks.setOnClickListener{
            changeShowCompletedTask()
        }

        return binding.root
    }


    fun openAddTaskForm(){
        val fragmentAddTask = AddNewTaskFragment.newInstance("", "")
        activity?.supportFragmentManager?.beginTransaction()
            ?.add(R.id.flFragmentContainer, fragmentAddTask, null)
            ?.addToBackStack("backstack")
            ?.commit()
    }



    override fun onTaskItemClicked(v: View, task: TaskEntity, position: Int) {
        val fragmentEditTask = EditTaskFragment.newInstance(task, position)
        activity?.supportFragmentManager?.beginTransaction()
            ?.add(R.id.flFragmentContainer, fragmentEditTask, null)
            ?.addToBackStack("backstack")
            ?.commit()
    }


    fun changeShowCompletedTask(){
        if (!isCompletedTaskShow){
            isCompletedTaskShow = true
            binding.ibShowTasks.setImageResource(R.drawable.ic_visibility_off)
            adapterTaskRV.updateList(TaskList.tasks)
        } else {
            isCompletedTaskShow = false
            binding.ibShowTasks.setImageResource(R.drawable.ic_btn_visibility)
            adapterTaskRV.updateList(TaskList.tasks.filter { it.isComplete == false })
        }

    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainScreenFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}