package com.example.mytasks.forms

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import com.example.mytasks.MainActivity
import com.example.mytasks.R
import com.example.mytasks.databinding.FragmentEditTaskBinding
import com.example.mytasks.models.TaskEntity
import com.example.mytasks.repositories.TaskList
import java.text.SimpleDateFormat
import java.util.*

private const val ARG_PARAM1 = "currentTask"
private const val ARG_PARAM2 = "positionInList"


class EditTaskFragment : Fragment() {
    lateinit var binding: FragmentEditTaskBinding
    private var currentTask: TaskEntity? = null
    private var position: Int? = null
    private var priority = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            currentTask = it.getParcelable(ARG_PARAM1)
            position = it.getInt(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditTaskBinding.inflate(inflater, container, false)
        binding.task = currentTask

        binding.btnPriorityMenu.setOnClickListener {
            showPriorityMenu(it)
        }


        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

            val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
            c.set(year, monthOfYear, dayOfMonth)
            val dateString = dateFormat.format(c.time)

            binding.tvDeadLineValue.visibility = View.VISIBLE
//            binding.tvDeadLineValue.text = "$dayOfMonth ${monthOfYear+1} $year"
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


        binding.ibCloseForm.setOnClickListener{
            closeFragment()
        }


        binding.btnSaveTask.setOnClickListener {
            val newTask = TaskEntity(
                3,
                binding.etTaskBody.text.toString(),
                binding.tvDeadLineValue.text.toString(),
                priority,
                false
            )

            position?.let { positionInList -> TaskList.tasks.set(positionInList, newTask) }
            closeFragment()
        }



        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(task: TaskEntity, position: Int) =
            EditTaskFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM1, task)
                    putInt(ARG_PARAM2, position)
                }
            }
    }


    fun closeFragment(){
        activity?.supportFragmentManager?.beginTransaction()
            ?.remove(this)
            ?.commit()
    }

    fun showPriorityMenu(view: View) {
        val priorityMenu = PopupMenu(requireContext(), view)
        priorityMenu.inflate(R.menu.popupmenu_priority)

        priorityMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.itemPriorityNone -> {
                    binding.btnPriorityMenu.text = "Нет"
                    priority = 0
                    true
                }
                R.id.itemPriorityLow -> {
                    binding.btnPriorityMenu.text = "Низкий"
                    priority = 1
                    true
                }
                R.id.itemPriorityHigh -> {
                    binding.btnPriorityMenu.text = "Высокий"
                    priority = 2
                    true
                }

                else -> false
            }
        }
        priorityMenu.setForceShowIcon(true)
        priorityMenu.show()

    }
}