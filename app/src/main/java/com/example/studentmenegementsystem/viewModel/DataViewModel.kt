package com.example.studentmenegementsystem.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.studentmenegementsystem.model.Data
import com.example.studentmenegementsystem.repository.DataRepository

class DataViewModel:ViewModel() {

    private val dataRepository = DataRepository()
    private var _dataList: MutableLiveData<List<Data>> = dataRepository.fetchData()
    val dataList:LiveData<List<Data>> get() = _dataList

    fun addData(data: Data, onSuccess: () -> Unit, onFailure: () -> Unit){
        dataRepository.addData(data).addOnSuccessListener {
            onSuccess()
        }.addOnFailureListener {
            onFailure()
        }

    }

    fun updateData(data: Data, onSuccess: () -> Unit, onFailure: () -> Unit) {
        dataRepository.updateData(data).addOnSuccessListener {
            onSuccess()
        }.addOnFailureListener {
            onFailure()
        }

    }

    fun deleteData(data: Data, onSuccess: () -> Unit, onFailure: () -> Unit) {
        dataRepository.deleteData(data).addOnSuccessListener {
            onSuccess()
        }.addOnFailureListener {
            onFailure()
        }

    }







}