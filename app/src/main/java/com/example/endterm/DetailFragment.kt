package com.example.endterm

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.endterm.network.ApiClient
import com.example.endterm.network.ApiService
import kotlinx.android.synthetic.main.fragment_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailFragment : Fragment() {
    private lateinit var idText: TextView
    private lateinit var title: TextView
    private lateinit var description: TextView
    private lateinit var status: TextView
    private lateinit var item: ToDo

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        idText = view.findViewById(R.id.id)
        title = view.findViewById(R.id.title)
        description = view.findViewById(R.id.description)
        status = view.findViewById(R.id.status)
        val idToDo = arguments?.getInt("ToDoId")!!
        getById(idToDo)
        back.setOnClickListener()
        {
            val action = DetailFragmentDirections.actionDetailToTodo()
            view.findNavController().navigate(action)
        }
    }

    private fun getById(id: Int) {

        ApiClient.getApiService().getTodoById(id).enqueue(object : Callback<ToDo> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<ToDo>?,
                response: Response<ToDo>
            ) {
                if (response.isSuccessful) {
                    item = response.body()!!
                    idText.text = "Task ID: " + item.id.toString()
                    title.text = "Task Title: " + item.title
                    description.text = "User Id: " + item.userId.toString()
                    status.text = "Task Status: " + item.completed.toString()

                }
            }

            override fun onFailure(call: Call<ToDo>?, t: Throwable) {
                Toast.makeText(context, "error", Toast.LENGTH_LONG).show()
            }
        })
    }
}