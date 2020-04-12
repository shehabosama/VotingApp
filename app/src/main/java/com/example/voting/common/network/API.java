package com.example.voting.common.network;


import com.example.voting.common.model.Candidate;
import com.example.voting.common.model.CandidatesResponse;
import com.example.voting.common.model.CenterResponse;
import com.example.voting.common.model.LoginResponse;
import com.example.voting.common.model.MainResponse;
import com.example.voting.common.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
public interface API {


    @POST("Login_user.php")
    Call<LoginResponse> loginUser(@Body User user);//to send request and response the message if the account is login or not

    @POST("register_user.php")
    Call<MainResponse> registerUser(@Body User user);// to send request and response the message if the user inserted in database or not

    @POST("getAllCenters.php")
    Call<CenterResponse> getAllCenters();
    @FormUrlEncoded
    @POST("electionLogin.php")
    Call<MainResponse> electionLogin(@Field("code") String code);

    @FormUrlEncoded
    @POST("getAllElections.php")
    Call<CandidatesResponse> getAllCandidates(@Field("center_id") int centerId);

    @FormUrlEncoded
    @POST("updateUserCandidateStatus.php")
    Call<MainResponse> updateUserCandidate(@Field("candid_id") int candid_id,@Field("user_id")int user_id);

    @FormUrlEncoded
    @POST("getCandidateStatistics.php")
    Call<CandidatesResponse> getInformationAboutCandidates(@Field("candid_id") int candidId);
    @FormUrlEncoded
    @POST("UpdateInfromation.php")
    Call<MainResponse> updateUserInformation(@Field("user_id") int userId,
                                                   @Field("user_name") String userName,
                                                   @Field("password")String password,
                                                   @Field("address_details")String addressDetails);

}
