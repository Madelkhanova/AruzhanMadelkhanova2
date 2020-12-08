package com.example.endterm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.endterm.network.ApiClient
import com.example.endterm.network.ApiService
import kotlinx.android.synthetic.main.fragment_add_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class CommentsFragment : Fragment() {
    lateinit var toDoList: MutableList<Comment>

    private lateinit var myRecyclerView: RecyclerView
    private lateinit var viewAdapter: CommentsAdapter
    private lateinit var viewManager: LinearLayoutManager
    private var listener: PostListAdapter.ItemClickListener? = null
    val args: CommentsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_to_do_list, container, false)
        toDoList = ArrayList()

        //for recyclerView
        myRecyclerView = rootView.findViewById(R.id.myRecyclerView)
        viewManager = LinearLayoutManager(context)
        myRecyclerView.layoutManager = viewManager
        val dividerItemDecoration = DividerItemDecoration(
            myRecyclerView.context,
            viewManager.orientation
        )
        myRecyclerView.addItemDecoration(dividerItemDecoration)

        //create adapter, to recyclerview
        viewAdapter = context?.let {
            CommentsAdapter(
                toDoList, it,
            )
        }!!
        myRecyclerView.adapter = viewAdapter

        viewAdapter.notifyDataSetChanged()
        val idToDo = args.todoId
        getList(idToDo)
        return rootView
    }


    fun getList(idToDo: Int) {
        val apiService: ApiService? = ApiClient.client?.create(ApiService::class.java)
        val call: Call<List<Comment>>? = apiService?.getToIdComment(idToDo)
        val list = ArrayList<Comment>()

        call?.enqueue(object : Callback<List<Comment>?> {
            override fun onResponse(
                call: Call<List<Comment>?>?,
                response: Response<List<Comment>?>
            ) {
                list.addAll(response.body() as ArrayList<Comment>)
                viewAdapter.todolist = list
                viewAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<List<Comment>?>?, t: Throwable) {
                Toast.makeText(context, "error", Toast.LENGTH_LONG).show()
            }
        })
    }
}