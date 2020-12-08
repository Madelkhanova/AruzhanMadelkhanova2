package com.example.endterm
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
    private lateinit var title: TextView
    private lateinit var body: TextView
    private lateinit var btn: Button


    private lateinit var item: Post
    val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootview = inflater.inflate(R.layout.fragment_detail, container, false)
        return rootview
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title = view.findViewById(R.id.title)
        body = view.findViewById(R.id.body)
        btn = view.findViewById(R.id.comments)

        val idToDo = args.todoId
        getById(idToDo)

        back.setOnClickListener()
        {
            val action = DetailFragmentDirections.actionDetailToTodo()
            view.findNavController().navigate(action)
        }
        btn.setOnClickListener(){
            val action = DetailFragmentDirections.actionDetailToTodo()
            action.idToDo = item.id
            view.findNavController().navigate(action)
        }
    }

    fun getById(id: Int) {
        val apiService: ApiService? = ApiClient.client?.create(ApiService::class.java)
        val call: Call<Post>? = apiService?.getTodoById(id)

        call?.enqueue(object : Callback<Post> {
            override fun onResponse(
                call: Call<Post>?,
                response: Response<Post>
            ) {
                item = response.body() as Post
                title.text = item.title
                body.text = item.body

            }

            override fun onFailure(call: Call<Post>?, t: Throwable) {
                Toast.makeText(context, "error", Toast.LENGTH_LONG).show()
            }
        })
    }
}