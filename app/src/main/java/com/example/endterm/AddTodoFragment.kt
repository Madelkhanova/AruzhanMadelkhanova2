package com.example.endterm


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.endterm.network.ApiClient
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.fragment_add_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

/**
 * A simple [Fragment] subclass.
 */
class AddTodoFragment : Fragment() {
    private lateinit var btn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_add_list, container, false)
        btn = rootView.findViewById(R.id.add)
        btn.setOnClickListener {
            try {
                val body = JsonObject().apply {
                    addProperty("title", title.text.toString())
                    addProperty("completed", false)
                }
                addTodo(body)
            } catch (e: Exception) {
                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
            }
        }

        return rootView
    }

    private fun addTodo(item: JsonObject) {
        ApiClient.getApiService().postTodos(item).enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                findNavController().navigate(R.id.toDoListFragment)
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Toast.makeText(context, "Wrong data!", Toast.LENGTH_SHORT).show()
            }
        })
    }

}
