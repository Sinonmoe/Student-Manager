package com.example.studentmanager

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.studentmanager.databinding.ActivityStudentDetailBinding

class StudentDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudentDetailBinding
    private var currentMSSV: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_student_detail)
        supportActionBar?.title = "Chi Tiết Sinh Viên"

        // Lấy MSSV được truyền từ MainActivity
        currentMSSV = intent.getStringExtra("MSSV")

        // Load dữ liệu lên màn hình
        loadData()

        binding.btnUpdate.setOnClickListener {
            updateData()
        }

        binding.btnDelete.setOnClickListener {
            deleteData()
        }
    }

    private fun loadData() {
        val student = StudentRepository.getStudentByMSSV(currentMSSV ?: "")
        if (student != null) {
            binding.etMSSV.setText(student.mssv)
            binding.etHoTen.setText(student.hoTen)
            binding.etPhone.setText(student.soDienThoai)
            binding.etAddress.setText(student.diaChi)
        } else {
            Toast.makeText(this, "Không tìm thấy sinh viên", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun updateData() {
        val newHoTen = binding.etHoTen.text.toString()
        val newPhone = binding.etPhone.text.toString()
        val newAddress = binding.etAddress.text.toString()

        if (newHoTen.isEmpty()) {
            Toast.makeText(this, "Họ tên không được để trống", Toast.LENGTH_SHORT).show()
            return
        }

        val updatedStudent = Student(currentMSSV!!, newHoTen, newPhone, newAddress)
        StudentRepository.updateStudent(currentMSSV!!, updatedStudent)

        Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun deleteData() {
        val student = StudentRepository.getStudentByMSSV(currentMSSV ?: "")
        if (student != null) {
            StudentRepository.deleteStudent(student)
            Toast.makeText(this, "Đã xóa sinh viên", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}