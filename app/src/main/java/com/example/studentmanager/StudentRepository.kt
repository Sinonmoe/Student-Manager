package com.example.studentmanager

object StudentRepository {
    private val students = mutableListOf<Student>()

    // Lấy toàn bộ danh sách
    fun getStudents(): MutableList<Student> = students

    // Thêm sinh viên
    fun addStudent(student: Student) {
        students.add(student)
    }

    // Lấy sinh viên theo MSSV
    fun getStudentByMSSV(mssv: String): Student? {
        return students.find { it.mssv == mssv }
    }

    // Cập nhật thông tin (tìm theo MSSV cũ, vì MSSV là khóa chính giả định)
    fun updateStudent(originalMSSV: String, newInfo: Student) {
        val index = students.indexOfFirst { it.mssv == originalMSSV }
        if (index != -1) {
            students[index] = newInfo
        }
    }

    // Xóa sinh viên
    fun deleteStudent(student: Student) {
        students.remove(student)
    }
}