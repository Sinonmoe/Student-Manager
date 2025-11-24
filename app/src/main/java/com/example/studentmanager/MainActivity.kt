package com.example.studentmanager

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studentmanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var studentAdapter: StudentAdapter

    private val studentList = mutableListOf<Student>()

    private var selectedPosition: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupRecyclerView()
        binding.btnAdd.setOnClickListener {
            addStudent()
        }

        binding.btnUpdate.setOnClickListener {
            updateStudent()
        }
    }

    private fun setupRecyclerView() {
        studentAdapter = StudentAdapter(
            studentList,
            onEditClick = { student ->
                binding.etMSSV.setText(student.mssv)
                binding.etHoTen.setText(student.hoTen)
                selectedPosition = studentList.indexOf(student)
                binding.btnUpdate.isEnabled = true
            },
            onDeleteClick = { student ->
                deleteStudent(student)
            }
        )

        binding.recyclerView.adapter = studentAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun addStudent() {
        val mssv = binding.etMSSV.text.toString().trim()
        val hoTen = binding.etHoTen.text.toString().trim()

        if (mssv.isEmpty() || hoTen.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show()
            return
        }

        if (studentList.any { it.mssv == mssv }) {
            Toast.makeText(this, "MSSV đã tồn tại!", Toast.LENGTH_SHORT).show()
            return
        }

        val newStudent = Student(mssv, hoTen)
        studentList.add(newStudent)

        studentAdapter.notifyItemInserted(studentList.size - 1)

        clearInput()
    }

    private fun updateStudent() {
        if (selectedPosition == -1) {
            Toast.makeText(this, "Chưa chọn sinh viên để sửa", Toast.LENGTH_SHORT).show()
            return
        }

        val mssv = binding.etMSSV.text.toString().trim()
        val hoTen = binding.etHoTen.text.toString().trim()

        if (mssv.isEmpty() || hoTen.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show()
            return
        }

        val student = studentList[selectedPosition]
        student.mssv = mssv
        student.hoTen = hoTen

        studentAdapter.notifyItemChanged(selectedPosition)

        Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show()

        clearInput()
        selectedPosition = -1
        binding.btnUpdate.isEnabled = false
    }

    private fun deleteStudent(student: Student) {
        val position = studentList.indexOf(student)
        if (position != -1) {
            studentList.removeAt(position)
            studentAdapter.notifyItemRemoved(position)
            if (position == selectedPosition) {
                clearInput()
                selectedPosition = -1
                binding.btnUpdate.isEnabled = false
            } else if (position < selectedPosition) {
                selectedPosition--
            }
        }
    }

    private fun clearInput() {
        binding.etMSSV.text.clear()
        binding.etHoTen.text.clear()
        binding.etMSSV.requestFocus()
    }
}