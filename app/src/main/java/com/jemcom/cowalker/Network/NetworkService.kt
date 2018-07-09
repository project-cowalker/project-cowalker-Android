package com.jemcom.cowalker.Network


import com.jemcom.cowalker.Network.Delete.DeleteProjectResponse
import com.jemcom.cowalker.Network.Get.GetSearchResponse
import com.jemcom.cowalker.Network.Get.Response.*
import com.jemcom.cowalker.Network.Post.*
import com.jemcom.cowalker.Network.Post.Response.*
import com.jemcom.cowalker.Network.Put.Response.PutProjectChangeResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface NetworkService {

    @POST("/api/signup")
    fun postSignup(
            @Body signup: PostSignup
    ): Call<PostSignupResponse>

    @GET("/api/signup/check")
    fun getSignupCheck(
            @Query("email") email : String
    ) : Call<GetSignupResponse>

    @POST("/api/signin")
    fun postLogin(
            @Body login : PostLogin
    ) : Call<PostLoginResponse>

    @Multipart
    @POST("/api/project")
    fun uploadProject(
            @Header("authorization") authorization : String,
            @Part("title") title : RequestBody,
            @Part("summary") summary : RequestBody,
            @Part("area") area: RequestBody,
            @Part("department") department : RequestBody,
            @Part("aim") aim : RequestBody,
            @Part("explain") explain : RequestBody,
            @Part img : ArrayList<MultipartBody.Part?>
    ) : Call<PostProjectResponse>


    @POST("/api/project/recruit")
    fun postInvite(
            @Header("authorization") authorization: String,
            @Body inviteUp : PostInvite
    ) : Call<PostInviteResponse>

    @POST("/api/apply")
    fun postJoin(
            @Header("authorization") authorization : String,
            @Body join : PostJoin
    ) : Call<PostJoinResponse>

    @GET("/api/apply/{apply_idx}")
    fun getApplyMine(
            @Header("authorization") authorization : String,
            @Path("apply_idx") apply_idx : String
    ) : Call<GetApplyResponse>

    @POST("/api/message/{user_id}")
    fun postMessage(
            @Header("authorization") authorization : String,
            @Body contents : PostMessageSend,
            @Path("user_id") user_id : String
    ) : Call<PostMessageResponse>

    @GET("/api/message")
    fun getMessage(
            @Header("authorization") authorization: String
    ) : Call<GetMessageResponse>
    @GET("/api/message/{partner_id}")
    fun getMessageLook(
            @Header("authorization") authorization: String,
            @Path("partner_id") partner_id : String
    ) : Call<GetMessageLookResponse>




    @GET("/api/home")
    fun getProject(
            @Header("authorization") authorization : String
    ) : Call<GetProjectResponse>

    @Multipart
    @PUT("/api/project/{project_idx}")
    fun putChangeProject(
            @Path("project_idx") project_idx : String,
            @Part("title") title : RequestBody,
            @Part("summary") summary : RequestBody,
            @Part("area") area: RequestBody,
            @Part("department") department : RequestBody,
            @Part("aim") aim : RequestBody,
            @Part("explain") explain : RequestBody,
            @Part img : ArrayList<MultipartBody.Part?>
    ) : Call<PutProjectChangeResponse>

    @GET("/api/project/{project_idx}")
    fun getDetailProject(
            @Header("authorization") authorization: String,
            @Path("project_idx") project_idx : String
    ) : Call<GetProjectDetailResponse>

    @DELETE("/api/project/{project_idx}")
    fun deleteProject(
            @Path("project_idx") project_idx : String
    ) : Call<DeleteProjectResponse>

    @GET("/api/project/team/{project_idx}")
    fun getMemberProject(
            @Path("project_idx") project_idx : String
    ) : Call<GetProjectMemberResponse>


    @GET("/api/mypage")
    fun getMypage(
            @Header("authorization") authorization: String
    ) : Call<GetMypageResponse>

    @GET("/api/mypage/{user_idx}")
    fun getMypageOther(
            @Header("authorization") authorization: String,
            @Path("user_idx") user_id: String
    ) : Call<GetMypageOtherResponse>

    @GET("/api/search")
    fun getSearch(
            @Query("aim") aim : String

    ) : Call<GetSearchResponse>

}