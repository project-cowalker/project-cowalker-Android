package com.jemcom.cowalker.Jemin.Activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.jemcom.cowalker.Jemin.Adapter.ApplyMemberAdapter
import com.jemcom.cowalker.Network.ApplicationController
import com.jemcom.cowalker.Network.Get.GetApplyMemberMessage
import com.jemcom.cowalker.Network.Get.Response.GetApplyMemberResponse
import com.jemcom.cowalker.Network.NetworkService
import com.jemcom.cowalker.Network.Put.Response.PutCreaterDecideResponse
import com.jemcom.cowalker.Network.Put.Response.PutProjectChangeResponse

import com.jemcom.cowalker.R
import com.jemcom.cowalker.R.id.apply_member_list_recyclerview
import kotlinx.android.synthetic.main.activity_apply_member.*
import kotlinx.android.synthetic.main.activity_apply_paper.*
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApplyMemberActivity : AppCompatActivity(), View.OnClickListener {

    private val context: Context = this

    override fun onClick(v: View?) {
        Log.v("TAG","지원자 멤버 클릭")
    }

    var recruit_idx: String = ""
    var applyMemberItems : ArrayList<GetApplyMemberMessage> = ArrayList()
    lateinit var applyMemberData : ArrayList<GetApplyMemberMessage>
    lateinit var networkService : NetworkService
    var applyMemberNumber : Int = 0;
    var applyMemberPosition : String= "";
    var applyMemberProfileUrl : String= "";
    lateinit var requestManager : RequestManager // 이미지를 불러올 때 처리하는 변수
    lateinit var applyMemberAdapter : ApplyMemberAdapter

    var applicant_idx_result : Int = 0
    var applicant_idx : String = ""
    var apply_idx : String = ""
    var num : String = ""
    var task : String = ""
    var token : String = ""
    var flag : Int =1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apply_member)

        val pref = getSharedPreferences("auto", Activity.MODE_PRIVATE)
        token = pref.getString("token","")


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

        applyMemberActivity = this
        val intent = intent
        flag=intent.getIntExtra("flag",0)
        num = intent.getStringExtra("num")

        Log.v("asdf","멤버액티비티 num = "+num)
        task = intent.getStringExtra("task")
        Log.v("asdf","멤버액티비티 태스크 = "+task)


        // 흐름 순차적
        if(flag==1)
        {
            recruit_idx = intent.getStringExtra("recruit_idx")
        }
        else if(flag==3)
        {
            recruit_idx = intent.getStringExtra("recruit_idx")
        }



        //Log.v("asdf","멤버액티비티 num2 = "+num)

        networkService = ApplicationController.instance.networkSerVice // 어플리케이션을 실행하자마자 어플리케이션 콘트롤러가 실행되는데 그 때 사용?
        requestManager = Glide.with(this)
        getMember()



    }

    companion object {
        lateinit var applyMemberActivity: ApplyMemberActivity
        //일종의 스태틱
    }

    fun getMember()
    {
        val pref = getSharedPreferences("auto", Activity.MODE_PRIVATE)
        val token = pref.getString("token","")
        var getApplyMemberResponse = networkService.getApplyMemberList(token ,recruit_idx) // 네트워크 서비스의 getContent 함수를 받아옴
        getApplyMemberResponse.enqueue(object : Callback<GetApplyMemberResponse> {
            override fun onResponse(call: Call<GetApplyMemberResponse>?, response: Response<GetApplyMemberResponse>?) {
                Log.v("TAG","지원 멤버 통신 성공")
                if(response!!.isSuccessful)
                {

                    if(response.body().result == null){
                        Log.v("TAG","널값은여기")
                    }
                    else {
                        applyMemberData = response.body().result
                        applyMemberAdapter = ApplyMemberAdapter(this@ApplyMemberActivity, applyMemberData, requestManager)
                        apply_member_list_recyclerview.layoutManager = LinearLayoutManager(this@ApplyMemberActivity)
                        apply_member_list_recyclerview.adapter = applyMemberAdapter
                    }
                }

            }

            override fun onFailure(call: Call<GetApplyMemberResponse>?, t: Throwable?) {
                Log.v("TAG","지원멤버 통신 실패")
            }

        })
    }

    fun changeAdapterJoin(token2:String, apply_idx2:String, applicant_idx2:String, join2:Int ) {

        var token : String = token2
        var apply_idx : String = apply_idx2
        var applicant_idx : String = applicant_idx2
        var join : Int = join2
        val putCreaterDecideResponse = networkService.putCreaterDecide(token, apply_idx, applicant_idx, join)

        putCreaterDecideResponse.enqueue(object : retrofit2.Callback<PutCreaterDecideResponse>{

            override fun onResponse(call: Call<PutCreaterDecideResponse>, response: Response<PutCreaterDecideResponse>) {
                Log.v("TAG", "조인 통신 성공")
                if(response.isSuccessful){

                    Log.v("TAG", "조인 수정 성공")


                }
            }

            override fun onFailure(call: Call<PutCreaterDecideResponse>, t: Throwable?) {
                Toast.makeText(applicationContext,"서버 연결 실패", Toast.LENGTH_SHORT).show()
            }

        })

    }

    fun activityFinish(){
        finish();
    }


}
