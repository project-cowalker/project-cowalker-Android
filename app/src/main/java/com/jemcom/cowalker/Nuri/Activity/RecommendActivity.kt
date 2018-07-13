package com.jemcom.cowalker.Nuri.Activity

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.jemcom.cowalker.Jemin.Activity.ApplyActivity
import com.jemcom.cowalker.Network.ApplicationController
import com.jemcom.cowalker.Network.Get.Response.GetProjectMineResponse
import com.jemcom.cowalker.Network.Get.Response.GetRecruitListResponse
import com.jemcom.cowalker.Network.NetworkService
import com.jemcom.cowalker.Network.Post.PostRecommend
import com.jemcom.cowalker.Network.Post.Response.PostRecommendResponse
import com.jemcom.cowalker.Nuri.Adapter.RecommendAdapter
import com.jemcom.cowalker.Nuri.Item.RecommendItem
import com.jemcom.cowalker.R
import kotlinx.android.synthetic.main.activity_recommend.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecommendActivity : AppCompatActivity(),View.OnClickListener {

    var recommendItems : ArrayList<RecommendItem> = ArrayList()
    lateinit var recommendAdapter : RecommendAdapter
    lateinit var networkService: NetworkService
    var check = 0
    var position = -1
    var idx :String?= ""


    companion object {
        lateinit var activity: RecommendActivity
        //일종의 스태틱
    }

    override fun onClick(v: View?) {
        when(v)
        {
            recommend_ok_btn -> {
                if(position > -1)
                {
                    post()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommend)
        val view = window.decorView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (view != null) {
                // 23 버전 이상일 때 상태바 하얀 색상에 회색 아이콘 색상을 설정
                view.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                window.statusBarColor = Color.parseColor("#FFFFFF")
            }
        } else if (Build.VERSION.SDK_INT >= 21) {
            // 21 버전 이상일 때
            window.statusBarColor = Color.BLACK
        }
        networkService = ApplicationController.instance.networkSerVice
        activity = this
        recommend_ok_btn.setOnClickListener(this)
        get()
    }

    fun get()
    {
        var project_idx = "5b4755b9d2b973301fe964a7"
        var getProjectResponse = networkService.getRecruitList(project_idx)

        getProjectResponse.enqueue(object : Callback<GetRecruitListResponse>{
            override fun onFailure(call: Call<GetRecruitListResponse>?, t: Throwable?) {
                Toast.makeText(applicationContext,"서버 연결 실패",Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<GetRecruitListResponse>?, response: Response<GetRecruitListResponse>?) {
                if(response!!.isSuccessful)
                {
                    var data = response.body().result
                    recommendItems.add(RecommendItem("전체 프로젝트",""))
                    for(i in 0..data.size-1)
                    {
                        recommendItems.add(RecommendItem(data[i].task,data[i].recruit_idx))
                    }
                    recommendAdapter = RecommendAdapter(recommendItems)
                    recommend_rv.layoutManager = LinearLayoutManager(applicationContext)
                    recommend_rv.adapter = recommendAdapter
                }
                else Toast.makeText(applicationContext,"실패",Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun post()
    {
        var project_idx = "5b4755b9d2b973301fe964a7"
        var reason = recommend_ed.text.toString()
        var data = PostRecommend(project_idx,idx!!,reason)
        System.out.println(reason)
        var postRecommendResponse = networkService.postRecommend("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoxMTcsImlhdCI6MTUzMTM4ODE4NywiZXhwIjoxNTMzOTgwMTg3fQ.9gIoju4t1FIZI1OK9cgwkYE56Z7LS4RUbMBKNZHFf58",data)

        postRecommendResponse.enqueue(object : Callback<PostRecommendResponse>{
            override fun onFailure(call: Call<PostRecommendResponse>?, t: Throwable?) {
                Toast.makeText(applicationContext,"서버 연결 실패",Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<PostRecommendResponse>?, response: Response<PostRecommendResponse>?) {
                if (response!!.isSuccessful)
                {
                    var intent = Intent(applicationContext,PopupActivity::class.java)
                    startActivity(intent)
                }
                else Toast.makeText(applicationContext,"실패",Toast.LENGTH_SHORT).show()
            }
        })
    }
}