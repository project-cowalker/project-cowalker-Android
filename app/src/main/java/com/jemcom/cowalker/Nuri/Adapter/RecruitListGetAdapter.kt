package com.jemcom.cowalker.Nuri.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jemcom.cowalker.Jemin.Activity.ApplyActivity
import com.jemcom.cowalker.Nuri.Holder.RecruitListGetViewHolder
import com.jemcom.cowalker.Nuri.Holder.RecruitListViewHolder
import com.jemcom.cowalker.Nuri.Item.RecruitListItem
import com.jemcom.cowalker.R

class RecruitListGetAdapter (private var recruitlistItems : ArrayList<RecruitListItem>) : RecyclerView.Adapter<RecruitListGetViewHolder>() {

    private lateinit var onItemClick : View.OnClickListener
    var selectedPosition : Int = 0

    fun setOnItemClickListener(I : View.OnClickListener)
    {
        onItemClick = I
    }

    //내가 쓸 뷰홀더가 뭔지를 적어준다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):RecruitListGetViewHolder {
        val mainView : View = LayoutInflater.from(parent.context)
                .inflate(R.layout.recruit_list_item, parent, false)
        mainView.setOnClickListener(onItemClick)
        return RecruitListGetViewHolder(mainView)
    }

    override fun getItemCount(): Int = recruitlistItems.size

    //데이터클래스와 뷰홀더를 이어준다.
    override fun onBindViewHolder(holder: RecruitListGetViewHolder, position: Int) {
        holder.postion.text = recruitlistItems[position].position
        holder.number.text = recruitlistItems[position].number.toString()
        holder.task.text = recruitlistItems[position].task
        holder.dday.text = "D"+recruitlistItems[position].dday




    }
}