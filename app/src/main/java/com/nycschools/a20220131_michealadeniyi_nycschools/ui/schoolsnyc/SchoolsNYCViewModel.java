package com.nycschools.a20220131_michealadeniyi_nycschools.ui.schoolsnyc;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import com.nycschools.a20220131_michealadeniyi_nycschools.data.model.SchoolModel;
import com.nycschools.a20220131_michealadeniyi_nycschools.network.ApiResponse;
import java.util.List;

public class SchoolsNYCViewModel extends ViewModel {
    private final SchoolsNYCRepo schoolsNYCRepo;
    private final MediatorLiveData<ApiResponse<List<SchoolModel>>> mediatorLiveData;

    public SchoolsNYCViewModel(){
        mediatorLiveData = new MediatorLiveData<>();
        schoolsNYCRepo = new SchoolsNYCRepo();
    }
    public LiveData<ApiResponse<List<SchoolModel>>> getData() {
        mediatorLiveData.addSource(schoolsNYCRepo.getSchoolsInNyc(), mediatorLiveData::setValue);
        return mediatorLiveData;
    }
}
