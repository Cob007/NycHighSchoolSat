package com.nycschools.a20220131_michealadeniyi_nycschools.ui.schoolsat;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.nycschools.a20220131_michealadeniyi_nycschools.data.model.SchoolSatModel;
import com.nycschools.a20220131_michealadeniyi_nycschools.network.ApiResponse;

import java.util.List;

public class SchoolSatViewModel extends ViewModel {
    private final SchoolsSatRepo schoolsSatRepo;
    private final MediatorLiveData<ApiResponse<List<SchoolSatModel>>> mediatorLiveData;

    public SchoolSatViewModel(){
        mediatorLiveData = new MediatorLiveData<>();
        schoolsSatRepo = new SchoolsSatRepo();
    }

    public LiveData<ApiResponse<List<SchoolSatModel>>> getDataBysSchoolDBN(String dbn) {
        mediatorLiveData.addSource(schoolsSatRepo.getSchoolsSat(dbn), mediatorLiveData::setValue);
        return mediatorLiveData;
    }
}
