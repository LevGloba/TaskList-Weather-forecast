package com.example.diplom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom.databinding.TaskElementBinding



class TaskAdapter : ListAdapter<Task, TaskAdapter.ItemHolder>(ItemComparator()) {

//класс наследующийся от RecyclerView и принимающий переменные из макета "task_element.xml"
// служит для заполенения компонентов макета "task_element.xml"
    class ItemHolder(private val binding: TaskElementBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) = with(binding) {
            taskTextView.text = task.title
            descriptionTextView.text = task.task
            dataTimeTextView.text = task.dataTime

            deletTrash.setOnClickListener { TaskAdapter().onDeleteTask(task)  }
        }

//передает контекст родителю
        companion object {
            fun create(parent: ViewGroup) : ItemHolder {
                return ItemHolder(TaskElementBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false))
            }
        }
    }

//удаляет выбранный элемент пользователем
    fun onDeleteTask(task: Task) {
        REF_DATABASE_ROOT.child(NODE_USERS).child("${firebaseAuth.uid}")
            .child(NODE_TASK).child("${task.index}").removeValue()
        notifyDataSetChanged()
    }

    //заменяет старые элементы на новые
    class  ItemComparator : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return  oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return  oldItem == newItem
        }

    }

    //создает элемент из списка
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return  ItemHolder.create(parent)
    }

    //отрисовывает элемент из списка по позиции
    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(getItem(position))
    }

}