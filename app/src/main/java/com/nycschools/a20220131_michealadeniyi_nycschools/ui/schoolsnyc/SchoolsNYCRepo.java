package com.nycschools.a20220131_michealadeniyi_nycschools.ui.schoolsnyc;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.nycschools.a20220131_michealadeniyi_nycschools.BaseApplication;
import com.nycschools.a20220131_michealadeniyi_nycschools.data.model.SchoolModel;
import com.nycschools.a20220131_michealadeniyi_nycschools.network.ApiInterface;
import com.nycschools.a20220131_michealadeniyi_nycschools.network.ApiResponse;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SchoolsNYCRepo {
    private final String TAG = getClass().getSimpleName();

    public MutableLiveData<ApiResponse<List<SchoolModel>>> getSchoolsInNyc() {

        final MutableLiveData<ApiResponse<List<SchoolModel>>> mutableLiveData = new MutableLiveData<>();


        ApiInterface apiService =
                BaseApplication.getRetrofitClient().create(ApiInterface.class);

        apiService.getSchoolsInNYC().enqueue(new Callback<List<SchoolModel>>() {
            @Override
            public void onResponse(Call<List<SchoolModel>> call,
                                   @NotNull Response<List<SchoolModel>> response) {
                Log.e(TAG, "response="+response );

                if (response.isSuccessful() && response.body()!=null ) {
                    Log.e(TAG, "response.size="+response.body().size());
                    mutableLiveData.postValue(new ApiResponse<>(response.body()));
                }else{
                    mutableLiveData.postValue(new ApiResponse<>(new Throwable(response.message())));
                }
            }

            @Override
            public void onFailure(Call<List<SchoolModel>> call, Throwable t) {
                Log.e(TAG, "onFailure" + call.toString());
                mutableLiveData.postValue(new ApiResponse<>(t));
            }
        });

        return mutableLiveData;
    }
}
