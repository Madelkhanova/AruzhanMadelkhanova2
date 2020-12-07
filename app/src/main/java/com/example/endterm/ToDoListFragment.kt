package com.example.endterm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.endterm.network.ApiClient
import com.example.endterm.network.ApiService
import kotlinx.android.synthetic.main.fragment_to_do_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ToDoListFragment : Fragment(), ToDoListAdapter.ItemClickListener {
    lateinit var toDoList: MutableList<ToDo>
    private lateinit var myRecyclerView: RecyclerView
    private lateinit var viewAdapter: ToDoListAdapter
    private lateinit var viewManager: LinearLayoutManager

    val args = arguments?.let { ToDoListFragmentArgs.fromBundle(it) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_to_do_list, container, false)
        toDoList = ArrayList()

        myRecyclerView = rootView.findViewById(R.id.myRecyclerView)
        viewManager = LinearLayoutManager(context)
        myRecyclerView.layoutManager = viewManager
        val dividerItemDecoration = DividerItemDecoration(
            myRecyclerView.context,
            viewManager.orientation
        )
        myRecyclerView.addItemDecoration(dividerItemDecoration)

        viewAdapter = context?.let {
            ToDoListAdapter(
                toDoList, it,
                this
            )
        }!!
        myRecyclerView.adapter = viewAdapter

        viewAdapter.notifyDataSetChanged()
        getList()
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addItem.setOnClickListener {
            val action = ToDoListFragmentDirections.actionTodoToAddToDo()
            view.findNavController().navigate(action)
            addItem.visibility = View.GONE
        }

    }

    private fun getList() {
        val list = ArrayList<ToDo>()
        ApiClient.getApiService().getTodos().enqueue(object : Callback<List<ToDo>?> {
            override fun onResponse(
                call: Call<List<ToDo>?>?,
                response: Response<List<ToDo>?>
            ) {
                list.addAll(response.body() as ArrayList<ToDo>)
                try {
                    if (args != null) {
                        Toast.makeText(context, args.userId, Toast.LENGTH_LONG).show()
                        list.add(0, ToDo(args.idToDo, args.userId, args.title, args.complete))
                    }
                } catch (e: Exception) {
                }
                viewAdapter.todolist = list
                viewAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<List<ToDo>?>?, t: Throwable) {
                Toast.makeText(context, "error", Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun itemClick(position: Int, item: ToDo?) {
        val bundle = Bundle()
        bundle.putInt("ToDoId", item?.id!!)
        findNavController().navigate(R.id.detailFragment, bundle)
    }
}