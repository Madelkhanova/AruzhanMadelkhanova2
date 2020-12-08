package com.example.endterm
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CommentsAdapter(
    var todolist: MutableList<Comment>,
    val context: Context,
) : RecyclerView.Adapter<CommentsAdapter.MyCommentViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCommentViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.comment_item, parent, false)
        return MyCommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyCommentViewHolder, position: Int) {
        val todo = todolist.get(position)
        holder.idText.text = todo.id.toString()
        holder.postIdText.text = todo.postId.toString()

        holder.nameText.text = todo.name


        holder.emailText.text = todo.email
        holder.bodyText.text = todo.body

    }

    override fun getItemCount(): Int {
        return todolist.size
    }

    class MyCommentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var idText: TextView = view.findViewById(R.id.id)
        var postIdText: TextView = view.findViewById(R.id.postId)

        var nameText: TextView = view.findViewById(R.id.name)

        var emailText: TextView = view.findViewById(R.id.email)
        var bodyText: TextView = view.findViewById(R.id.body)


    }


}