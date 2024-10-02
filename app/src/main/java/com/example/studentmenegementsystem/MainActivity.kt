package com.example.studentmenegementsystem

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studentmenegementsystem.adapter.DataAdapter
import com.example.studentmenegementsystem.databinding.ActivityMainBinding
import com.example.studentmenegementsystem.model.Data
import com.example.studentmenegementsystem.viewModel.DataViewModel
import com.google.firebase.Timestamp

class MainActivity : AppCompatActivity(), DataAdapter.ItemClickListener {
    private lateinit var binding: ActivityMainBinding

    private val dataViewModel: DataViewModel by viewModels()

    private lateinit var adapter: DataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



        adapter = DataAdapter(listOf(), this)
        binding.recyclerViewList.adapter = adapter
        binding.recyclerViewList.layoutManager = LinearLayoutManager(this)

        dataViewModel.dataList.observe(this, Observer {
            adapter.updateData(it)
        })

        binding.button.setOnClickListener {
            val studentId = binding.StudentId.text.toString()
            val name = binding.nameExt.text.toString()
            val email = binding.emailTxt.text.toString()
            val subject = binding.subjectTxt.text.toString()
            val birthDate = binding.studentBirthdayTxt.text.toString()

            if (studentId.isNotEmpty() && name.isNotEmpty() && email.isNotEmpty() && subject.isNotEmpty() && birthDate.isNotEmpty()) {
                val data = Data(
                    id = null,
                    studentId = studentId,
                    name = name,
                    email = email,
                    subject = subject,
                    birthDate = birthDate,
                    Timestamp.now()
                )
                dataViewModel.addData(data, onSuccess = {
                    clearFields()
                    Toast.makeText(this@MainActivity, "Data Added Successfully", Toast.LENGTH_SHORT)
                        .show()
                }, onFailure = {
                    Toast.makeText(this@MainActivity, "Data Added Failed", Toast.LENGTH_SHORT)
                        .show()


                })
            }


        }


    }




        override fun onEditItemClick(data: Data) {

            binding.StudentId.setText(data.studentId)
            binding.nameExt.setText(data.name)
            binding.emailTxt.setText(data.email)
            binding.subjectTxt.setText(data.subject)
            binding.studentBirthdayTxt.setText(data.birthDate)

            binding.button.setOnClickListener {
                val studentId = binding.StudentId.text.toString()
                val name = binding.nameExt.text.toString()
                val email = binding.emailTxt.text.toString()
                val subject = binding.subjectTxt.text.toString()
                val birthDate = binding.studentBirthdayTxt.text.toString()
                val dataName = Data(
                    id = data.id,
                    studentId = studentId,
                    name = name,
                    email = email,
                    subject = subject,
                    birthDate = birthDate,
                    Timestamp.now()
                )

                dataViewModel.updateData(dataName, onSuccess = {
                    clearFields()
                    Toast.makeText(
                        this@MainActivity,
                        "Data Updated Successfully",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }, onFailure = {
                    Toast.makeText(this@MainActivity, "Data Updated Failed", Toast.LENGTH_SHORT)
                        .show()
                })

            }
        }


            override fun onDeleteItemClick(data: Data) {
                AlertDialog.Builder(this@MainActivity).apply {
                    setTitle("Delete Data")
                    setMessage("Are you sure you want to delete this data?")
                    setPositiveButton("Yes") { _, _ ->
                        dataViewModel.deleteData(data, onSuccess = {
                            clearFields()
                            Toast.makeText(
                                this@MainActivity,
                                "Data Deleted Successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                        }, onFailure = {
                            Toast.makeText(
                                this@MainActivity,
                                "Data Deleted Failed",
                                Toast.LENGTH_SHORT
                            ).show()
                        })
                    }
                    setNegativeButton("No", null).show()

                }
            }

    private fun clearFields() {
        binding.StudentId.text.clear()
        binding.nameExt.text.clear()
        binding.emailTxt.text.clear()
        binding.subjectTxt.text.clear()
        binding.studentBirthdayTxt.text.clear()
    }


}