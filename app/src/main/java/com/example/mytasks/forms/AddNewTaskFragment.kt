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
import com.example.mytasks.databinding.FragmentAddNewTaskBinding
import com.example.mytasks.models.Priority
import com.example.mytasks.models.TaskEntity
import com.example.mytasks.repositories.TaskList
import java.text.SimpleDateFormat
import java.util.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class AddNewTaskFragment : Fragment() {

    lateinit var binding: FragmentAddNewTaskBinding

//    private var priority: Priority = Priority.NONE
    private var priority = 0

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddNewTaskBinding.inflate(inflater, container, false)

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

        binding.btnSaveTask.setOnClickListener {
            val newTask = TaskEntity(
                3,
                binding.etTaskBody.text.toString(),
                binding.tvDeadLineValue.text.toString(),
                priority,
                false
            )

            TaskList.tasks.add(newTask)
            closeFragment()
        }

        binding.btnPriorityMenu.setOnClickListener {
            showPriorityMenu(it)
        }

        binding.ibCloseForm.setOnClickListener {
            closeFragment()
        }




        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddNewTaskFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
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