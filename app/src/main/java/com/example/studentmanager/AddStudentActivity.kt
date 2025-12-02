package com.example.studentmanager

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.studentmanager.databinding.ActivityAddStudentBinding

class AddStudentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddStudentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_student)
        supportActionBar?.title = "Thêm Sinh Viên Mới"

        binding.btnSave.setOnClickListener {
            val mssv = binding.etMSSV.text.toString()
            val hoTen = binding.etHoTen.text.toString()
            val phone = binding.etPhone.text.toString()
            val address = binding.etAddress.text.toString()

            if (mssv.isEmpty() || hoTen.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập MSSV và Họ tên", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (StudentRepository.getStudentByMSSV(mssv) != null) {
                Toast.makeText(this, "MSSV đã tồn tại!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Thêm vào kho dữ liệu
            val newStudent = Student(mssv, hoTen, phone, address)
            StudentRepository.addStudent(newStudent)

            Toast.makeText(this, "Đã thêm thành công", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}