package com.nycschools.a20220131_michealadeniyi_nycschools.network;

import com.nycschools.a20220131_michealadeniyi_nycschools.data.model.SchoolModel;
import com.nycschools.a20220131_michealadeniyi_nycschools.data.model.SchoolSatModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

//base url https://data.cityofnewyork.us/
public interface ApiInterface {
    @GET("resource/s3k6-pzi2.json")
    Call<List<SchoolModel>> getSchoolsInNYC();

    @GET("resource/f9bf-2cp4.json")
    Call<List<SchoolSatModel>> getSchoolSatInNycByDBN(@Query("dbn") String dbn);
}
