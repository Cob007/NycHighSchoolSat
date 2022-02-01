package com.nycschools.a20220131_michealadeniyi_nycschools.ui.schoolsat;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.nycschools.a20220131_michealadeniyi_nycschools.BaseApplication;
import com.nycschools.a20220131_michealadeniyi_nycschools.data.model.SchoolSatModel;
import com.nycschools.a20220131_michealadeniyi_nycschools.network.ApiInterface;
import com.nycschools.a20220131_michealadeniyi_nycschools.network.ApiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SchoolsSatRepo {
    private final String TAG = getClass().getSimpleName();
    public MutableLiveData<ApiResponse<List<SchoolSatModel>>> getSchoolsSat(String dbn) {

        final MutableLiveData<ApiResponse<List<SchoolSatModel>>> mutableLiveData = new MutableLiveData<>();

        ApiInterface apiService =
                BaseApplication.getRetrofitClient().create(ApiInterface.class);

        apiService.getSchoolSatInNycByDBN(dbn).enqueue(new Callback<List<SchoolSatModel>>() {
            @Override
            public void onResponse(Call<List<SchoolSatModel>> call,
                                   Response<List<SchoolSatModel>> response) {
                Log.e(TAG, "response="+response );
                if (response.isSuccessful() && response.body()!=null ) {
                    Log.e(TAG, "response.size="+response.body().size());
                    mutableLiveData.postValue(new ApiResponse<>(response.body()));
                    //mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<SchoolSatModel>> call, Throwable t) {
                Log.e(TAG, "onFailure" + call.toString());
                mutableLiveData.postValue(new ApiResponse<>(t));
            }
        });

        return mutableLiveData;
    }
}
