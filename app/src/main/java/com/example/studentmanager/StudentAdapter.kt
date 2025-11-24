package com.example.studentmanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(
    private val studentList: MutableList<Student>,
    private val onEditClick: (Student) -> Unit,
    private val onDeleteClick: (Student) -> Unit
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvHoTen: TextView = itemView.findViewById(R.id.tvHoTen)
        val tvMSSV: TextView = itemView.findViewById(R.id.tvMSSV)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = studentList[position]

        holder.tvHoTen.text = student.hoTen
        holder.tvMSSV.text = student.mssv

        holder.btnDelete.setOnClickListener {
            onDeleteClick(student)
        }

        holder.itemView.setOnClickListener {
            onEditClick(student)
        }
    }

    override fun getItemCount(): Int = studentList.size
}