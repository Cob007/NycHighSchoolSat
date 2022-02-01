package com.nycschools.a20220131_michealadeniyi_nycschools.ui.schoolsat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.nycschools.a20220131_michealadeniyi_nycschools.BaseApplication;
import com.nycschools.a20220131_michealadeniyi_nycschools.R;
import com.nycschools.a20220131_michealadeniyi_nycschools.data.model.SchoolSatModel;
import com.nycschools.a20220131_michealadeniyi_nycschools.databinding.ActivitySchoolSatBinding;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class SchoolSatActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    public static final String INTENT_KEY = "dbn_key";
    public static final String INTENT_KEY_SCHOOL = "schoolname_key";

    ActivitySchoolSatBinding binding;

    public static Intent getIntentStarted(Context con, String dbn, String schoolName) {
        Intent intent = new Intent(con, SchoolSatActivity.class);
        intent.putExtra(INTENT_KEY, dbn);
        intent.putExtra(INTENT_KEY_SCHOOL, schoolName);
        return intent;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // configuration to implement custom font
        // but can be implemented better with baseActivity
        // to avoid repetition
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/OpenSan-Regular.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
        //binding
        binding = DataBindingUtil.setContentView(SchoolSatActivity.this, R.layout.activity_school_sat);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //accepting intent data from the list of school
        String dbnData = getIntent().getStringExtra(INTENT_KEY);
        String schoolName = getIntent().getStringExtra(INTENT_KEY_SCHOOL);

        binding.tvTitle.setText(schoolName);

        //check if the network connection
        if (BaseApplication.getInstance().isNetworkAvailable(this)) {
            //show progress bar
            binding.progressBar.setVisibility(View.VISIBLE);
            SchoolSatViewModel schoolSatViewModel = new SchoolSatViewModel();
            schoolSatViewModel.getDataBysSchoolDBN(dbnData).observe(this, v -> {
                binding.progressBar.setVisibility(View.GONE);
                if (v == null) {
                    //handle error

                } else if (v.getError() == null) {
                    if (!v.response.isEmpty()) {
                        binding.llNonEmpty.setVisibility(View.VISIBLE);
                        binding.llEmpty.setVisibility(View.GONE);
                        SchoolSatModel schoolSatModel = v.response.get(0);
                        binding.setSat(schoolSatModel);
                    } else {
                        //successful response but empty
                        //update user about the error
                        binding.llNonEmpty.setVisibility(View.GONE);
                        binding.llEmpty.setVisibility(View.VISIBLE);
                        Toast.makeText(SchoolSatActivity.this, "There is not response for this school ", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Throwable e = v.getError();
                    binding.llNonEmpty.setVisibility(View.GONE);
                    binding.llEmpty.setVisibility(View.VISIBLE);
                    binding.tvEmpty.setText(e.getMessage());
                    Toast.makeText(SchoolSatActivity.this, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Error is " + e.getLocalizedMessage());
                }
            });
        } else {
            Snackbar snackbar = Snackbar
                    .make(binding.layout, "No Internet Connection ", Snackbar.LENGTH_LONG)
                    .setAction("RETRY", view -> {
                        //call get data here again
                    });
            snackbar.show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}