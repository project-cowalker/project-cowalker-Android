package com.jemcom.cowalker.Nuri.Activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.jemcom.cowalker.Jemin.Activity.ApplyModify2Activity
import com.jemcom.cowalker.Jemin.Activity.MyYearMonthPickerDialog
import com.jemcom.cowalker.Network.ApplicationController
import com.jemcom.cowalker.Network.Get.GetRecruitDetail
import com.jemcom.cowalker.Network.Get.Response.GetRecruitDetailResponse
import com.jemcom.cowalker.Network.NetworkService
import com.jemcom.cowalker.R
import kotlinx.android.synthetic.main.activity_invite.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApplyModifyActivity : AppCompatActivity(),View.OnClickListener {

    lateinit var date : String
    lateinit var start_date: String
    lateinit var end_date: String
    lateinit var startZeroMonth: String
    lateinit var startZeroDay: String
    lateinit var finishZeroMonth: String
    lateinit var finishZeroDay: String
    lateinit var data : ArrayList<GetRecruitDetail>
    lateinit var networkService: NetworkService

    override fun onClick(v: View?) {
        when(v)
        {
            invite1_next_btn -> {

                var intent = Intent(applicationContext, ApplyModify2Activity::class.java)
                intent.putExtra("position",data[0].position)
                intent.putExtra("number",invite_personnel_edit.text)
                intent.putExtra("start_date",invite_range_btn.text.split(" ~ ")[0])
                intent.putExtra("end_date,",invite_range_btn.text.split(" ~ ")[1])
                startActivity(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_invite)
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
        invite1_next_btn.setOnClickListener(this)

        invite_range_btn.setOnClickListener {
            val pd = MyYearMonthPickerDialog()
            pd.setOnConfirmDateListener { startYear, startMonth, startDay, finishYear, finishMonth, finishDay ->
                var startDay = startDay
                var finishDay = finishDay
                if (startMonth < 10) {
                    startZeroMonth = "0$startMonth"
                } else {
                    startZeroMonth = Integer.toString(startMonth)
                }
                if (startDay < 9) {
                    startDay += 1
                    startZeroDay = "0$startDay"
                } else {
                    startZeroDay = Integer.toString(startDay + 1)
                }
                if (finishMonth < 10) {
                    finishZeroMonth = "0$finishMonth"
                } else {
                    finishZeroMonth = Integer.toString(finishMonth)
                }
                if (finishDay < 9) {
                    finishDay += 1
                    finishZeroDay = "0$finishDay"
                } else {
                    finishZeroDay = Integer.toString(finishDay + 1)
                }
                this@ApplyModifyActivity.start_date = startYear.toString() + "-" + startZeroMonth + "-" + startZeroDay
                this@ApplyModifyActivity.end_date = finishYear.toString() + "-" + finishZeroMonth + "-" + finishZeroDay
                invite_range_btn.text = date
            }
            pd.show(fragmentManager, "YearMonthPickerTest")
        }


        get()
    }

    fun get()
    {

        val pref = applicationContext.getSharedPreferences("auto", Activity.MODE_PRIVATE)
        val token = pref.getString("token","")
        val recruit_idx = getIntent().getStringExtra("recruit_idx").toString()
        val project_idx = getIntent().getStringExtra("project_idx").toString()
        val getRecruitDetailResponse = networkService.getRecruitDetail(token,project_idx,recruit_idx)

        getRecruitDetailResponse.enqueue(object : Callback<GetRecruitDetailResponse> {
            override fun onFailure(call: Call<GetRecruitDetailResponse>?, t: Throwable?) {
                Toast.makeText(applicationContext,"서버 연결 실패", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<GetRecruitDetailResponse>?, response: Response<GetRecruitDetailResponse>?) {
                if(response!!.isSuccessful)
                {
                    data = response.body().result
                    var start = data[0].start_date.split(",").toString()
                    var end = data[0].end_date.split(",").toString()
                    date = start + " ~ " + end
                    if(data[0].position.equals("PM")) invite_pm_btn.isSelected = true
                    else if(data[0].position.equals("기획자")) invite_planner_btn.isSelected = true
                    else if(data[0].position.equals("디자이너")) invite_designer_btn.isSelected = true
                    else if(data[0].position.equals("개발자")) invite_developer_btn.isSelected = true
                    else invite_role_tv.isSelected = true
                    invite_personnel_edit.setText(data[0].number)
                    invite_range_btn.text = date
                }
                else Toast.makeText(applicationContext,"실패",Toast.LENGTH_SHORT).show()
            }
        })
    }
}