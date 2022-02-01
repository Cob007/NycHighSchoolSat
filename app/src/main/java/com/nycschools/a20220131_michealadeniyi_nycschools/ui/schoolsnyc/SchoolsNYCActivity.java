package com.nycschools.a20220131_michealadeniyi_nycschools.ui.schoolsnyc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.nycschools.a20220131_michealadeniyi_nycschools.BaseApplication;
import com.nycschools.a20220131_michealadeniyi_nycschools.R;
import com.nycschools.a20220131_michealadeniyi_nycschools.data.model.SchoolModel;
import com.nycschools.a20220131_michealadeniyi_nycschools.databinding.ActivitySchoolsNycactivityBinding;
import com.nycschools.a20220131_michealadeniyi_nycschools.network.ApiResponse;
import com.nycschools.a20220131_michealadeniyi_nycschools.ui.schoolsat.SchoolSatActivity;

import java.util.List;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class SchoolsNYCActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    ActivitySchoolsNycactivityBinding binding;
    SchoolsNYCAdapter adapter;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/OpenSan-Regular.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
        binding = DataBindingUtil.setContentView(this, R.layout.activity_schools_nycactivity);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setUp();
        loadSchoolList();
    }

    private void loadSchoolList() {
        if (BaseApplication.getInstance().isNetworkAvailable(this)) {
            binding.progressBar.setVisibility(View.VISIBLE);
            SchoolsNYCViewModel schoolsNYCViewModel = new SchoolsNYCViewModel();
            schoolsNYCViewModel.getData().observe(this, (ApiResponse<List<SchoolModel>> schoolModels) -> {
                binding.progressBar.setVisibility(View.GONE);
                if (schoolModels == null) {
                    //handle error : add empty screen

                } else if (schoolModels.getError() == null) {
                    if (!schoolModels.response.isEmpty()) {
                        Log.e(TAG, "observe onChanged()=" + schoolModels.response.size());
                        binding.progressBar.setVisibility(View.GONE);
                        adapter.addSchoolList(schoolModels.response);
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(SchoolsNYCActivity.this, "School List is empty", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Throwable e = schoolModels.getError();
                    Snackbar snackbar = Snackbar
                            .make(binding.layout,  e.getMessage(), Snackbar.LENGTH_LONG)
                            .setAction("RETRY", view -> {
                                //call get data here again
                                loadSchoolList();
                            });
                    snackbar.show();
                    Log.e(TAG, "Error is " + e.getLocalizedMessage());
                }
            });
        } else {
            //add snackbar toast here so that they can retry
            Snackbar snackbar = Snackbar
                    .make(binding.layout, "No Internet Connection ", Snackbar.LENGTH_LONG)
                    .setAction("RETRY", view -> {
                        //call get data here again
                        loadSchoolList();
                    });
            snackbar.show();
        }
    }

    private void setUp() {
        SchoolsNYCAdapter.ItemClickListener itemClickListener =
                schoolModel -> startActivity(
                        SchoolSatActivity.getIntentStarted(SchoolsNYCActivity.this,
                                schoolModel.getDbn(), schoolModel.getSchoolName()));
        binding.rvHolidayList.setHasFixedSize(true);
        binding.rvHolidayList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SchoolsNYCAdapter(itemClickListener);
        binding.rvHolidayList.setAdapter(adapter);
    }
}