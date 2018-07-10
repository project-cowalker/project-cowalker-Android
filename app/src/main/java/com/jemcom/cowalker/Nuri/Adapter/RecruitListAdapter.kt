package com.jemcom.cowalker.Nuri.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jemcom.cowalker.Nuri.Activity.RecruitDeleteActivity
import com.jemcom.cowalker.Nuri.Holder.RecruitListViewHolder
import com.jemcom.cowalker.Nuri.Item.RecruitListItem
import com.jemcom.cowalker.R

class RecruitListAdapter (private var recruitlistItems : ArrayList<RecruitListItem>) : RecyclerView.Adapter<RecruitListViewHolder>() {

    //내가 쓸 뷰홀더가 뭔지를 적어준다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecruitListViewHolder {
        val mainView : View = LayoutInflater.from(parent.context)
                .inflate(R.layout.delete_item, parent, false)
        return RecruitListViewHolder(mainView)
    }

    override fun getItemCount(): Int = recruitlistItems.size

    //데이터클래스와 뷰홀더를 이어준다.
    override fun onBindViewHolder(holder: RecruitListViewHolder, position: Int) {
        holder.postion.text = recruitlistItems[position].position
        holder.number.text = recruitlistItems[position].number.toString()
        holder.task.text = recruitlistItems[position].task
        holder.dday.text = recruitlistItems[position].dday.toString()

        holder.check.setOnClickListener{
            if(holder.check.isSelected != false)
            {
                RecruitDeleteActivity.activity.check = 1
                holder.check.isSelected = true
            }
            else
            {
                RecruitDeleteActivity.activity.check = 0
                holder.check.isSelected = false
            }
        }
    }
}