package com.example.studentmenegementsystem.repository


import androidx.lifecycle.MutableLiveData
import com.example.studentmenegementsystem.model.Data
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


class DataRepository {

    private val db = FirebaseFirestore.getInstance()

    private val dataCollection = db.collection("data")


    fun fetchData(): MutableLiveData<List<Data>> {
        val dataList = MutableLiveData<List<Data>>()
        dataCollection.orderBy(
            "timestamp", Query.Direction.DESCENDING
        )
            .get()
            .addOnSuccessListener { documents ->
                val list = mutableListOf<Data>()
                for (document in documents) {
                    val item = document.toObject(Data::class.java)
                    item.id = document.id
                    list.add(item)

                }

                dataList.value = list
            }
        return dataList
    }


    fun addData(data: Data): Task<Void> {
        return dataCollection.document().set(data)
    }


    fun updateData(data: Data): Task<Void> {
        return dataCollection.document(data.id!!).set(data)
    }


    fun deleteData(data: Data): Task<Void> {
        return dataCollection.document(data.id!!).set(data)
    }

}
