package com.example.endterm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.endterm.network.ApiClient
import com.example.endterm.network.ApiService
import kotlinx.android.synthetic.main.fragment_to_do_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostListFragment : Fragment() {
    lateinit var toDoList: MutableList<Post>
    private lateinit var myRecyclerView: RecyclerView
    private lateinit var viewAdapter: PostListAdapter
    private lateinit var viewManager: LinearLayoutManager
    private var listener: PostListAdapter.ItemClickListener? = null

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

        //listener for Details
        //listener = object : PostListAdapter.ItemClickListener {
         //   override fun itemClick(position: Int, item: Post?) {
          //      val action = PostListFragmentDirections.
           //     if (item != null) {
          //          action.todoId = item.id
           //     }
           //     rootView.findNavController().navigate(action)
          //  }
       // }
        //create adapter, to recyclerview
        viewAdapter = context?.let {
            PostListAdapter(
                toDoList, it,
                listener as PostListAdapter.ItemClickListener
            )
        }!!
        myRecyclerView.adapter = viewAdapter

        viewAdapter.notifyDataSetChanged()
        getList()
        return rootView
    }
    fun getList() {
        val apiService: ApiService? = ApiClient.client?.create(ApiService::class.java)
        val call: Call<List<Post>>? = apiService?.getTodos()
        val list = ArrayList<Post>()

        call?.enqueue(object : Callback<List<Post>?> {
            override fun onResponse(
                call: Call<List<Post>?>?,
                response: Response<List<Post>?>
            ) {
                list.addAll(response.body() as ArrayList<Post>)
                viewAdapter.todolist = list
                viewAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<List<Post>?>?, t: Throwable) {
                Toast.makeText(context, "error", Toast.LENGTH_LONG).show()
            }
        })
    }
}
