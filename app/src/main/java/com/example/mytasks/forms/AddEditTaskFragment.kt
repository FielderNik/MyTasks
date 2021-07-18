package com.example.mytasks.forms

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mytasks.R
import com.example.mytasks.databinding.FragmentAddNewTaskBinding
import com.example.mytasks.models.Priority
import com.example.mytasks.models.TaskEntity
import com.example.mytasks.viewmodels.AddEditTaskViewModel
import java.text.SimpleDateFormat
import java.util.*

private const val PARAM_TASK = "Task"

class AddEditTaskFragment : Fragment() {

    lateinit var binding: FragmentAddNewTaskBinding
    lateinit var addEditTaskViewModel: AddEditTaskViewModel

    private var priority = Priority.NONE.value
    private var deadlineTime: Int = 0
    private var createdTime: Int = 0
    private var updatedTime: Int = 0

    private var currentTask: TaskEntity? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            currentTask = it.getParcelable<TaskEntity>(PARAM_TASK)
        }
        addEditTaskViewModel = ViewModelProvider(this).get(AddEditTaskViewModel::class.java)

        currentTask?.let {
            deadlineTime = it.deadline
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddNewTaskBinding.inflate(inflater, container, false)

        initUI()

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

            val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
            c.set(year, monthOfYear, dayOfMonth)
            val dateString = dateFormat.format(c.time)
            deadlineTime = (c.timeInMillis / 1000L).toInt()

            binding.tvDeadLineValue.visibility = View.VISIBLE
            binding.tvDeadLineValue.text = dateString

        }, year, month, day)


        binding.swDeadLine.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                dpd.show()
            } else {
                binding.tvDeadLineValue.text = ""
                binding.tvDeadLineValue.visibility = View.GONE
            }
        }

        binding.btnSaveTask.setOnClickListener {
            if (binding.etTaskBody.text.toString() != "") {
                saveTask()
                closeFragment()
            } else {
                Toast.makeText(requireContext(), getString(R.string.error_task_cannot_be_empty), Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnPriorityMenu.setOnClickListener {
            showPriorityMenu(it)
        }

        binding.ibCloseForm.setOnClickListener {
            closeFragment()
        }

        binding.btnDeleteTask.setOnClickListener {
            addEditTaskViewModel.deleteTask(currentTask!!)
            closeFragment()
        }


        return binding.root
    }


    private fun saveTask() {
        updatedTime = (System.currentTimeMillis() / 1000L).toInt()
        createdTime = (System.currentTimeMillis() / 1000L).toInt()

        if (currentTask == null) {
            val newTask = TaskEntity(
                taskBody = binding.etTaskBody.text.toString(),
                deadline = deadlineTime,
                priority = priority,
                isComplete = false,
                createdAt = createdTime,
                updatedAt = updatedTime,
            )
            addEditTaskViewModel.addTask(newTask)
        } else {
            currentTask?.let {
                it.taskBody = binding.etTaskBody.text.toString()
                it.deadline = deadlineTime
                it.priority = priority
                it.updatedAt = updatedTime
                addEditTaskViewModel.editTask(it)
            }
        }
    }

    private fun closeFragment(){
        activity?.supportFragmentManager?.popBackStack()
    }

    private fun showPriorityMenu(view: View) {
        val priorityMenu = PopupMenu(requireContext(), view)
        priorityMenu.inflate(R.menu.popupmenu_priority)

        priorityMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.itemPriorityNone -> {
                    binding.btnPriorityMenu.text = getString(R.string.tittle_priority_none)
                    priority = Priority.NONE.value
                    true
                }
                R.id.itemPriorityLow -> {
                    binding.btnPriorityMenu.text = getString(R.string.tittle_priority_low)
                    priority = Priority.LOW.value
                    true
                }
                R.id.itemPriorityHigh -> {
                    binding.btnPriorityMenu.text = getString(R.string.tittle_priority_high)
                    priority = Priority.HIGH.value
                    true
                }

                else -> false
            }
        }
        priorityMenu.setForceShowIcon(true)
        priorityMenu.show()

    }

    fun initUI() {
        if (currentTask != null) {
            binding.apply {
                etTaskBody.hint = ""
                etTaskBody.setText(currentTask!!.taskBody)

                when(currentTask!!.priority){
                    Priority.NONE.value -> btnPriorityMenu.text = getString(R.string.tittle_priority_none)
                    Priority.LOW.value -> btnPriorityMenu.text = getString(R.string.tittle_priority_low)
                    Priority.HIGH.value -> btnPriorityMenu.text = getString(R.string.tittle_priority_high)
                }

                if (currentTask!!.deadline == 0){
                    binding.tvDeadLineValue.text = ""
                } else {
                    val date = Date(currentTask!!.deadline * 1000L)
                    val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                    val dateString = sdf.format(date)
                    tvDeadLineValue.text = dateString
                    swDeadLine.isChecked = true
                }

                btnDeleteTask.apply {
                    isEnabled = true
                    setTextColor(ResourcesCompat.getColor(resources, R.color.red, null))
                    setIconTintResource(R.color.red)
                }

            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(taskEntity: TaskEntity?) =
            AddEditTaskFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(PARAM_TASK, taskEntity)
                }
            }
    }
}