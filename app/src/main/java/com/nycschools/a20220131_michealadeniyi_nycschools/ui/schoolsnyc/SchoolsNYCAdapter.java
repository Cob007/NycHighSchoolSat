package com.nycschools.a20220131_michealadeniyi_nycschools.ui.schoolsnyc;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nycschools.a20220131_michealadeniyi_nycschools.R;
import com.nycschools.a20220131_michealadeniyi_nycschools.data.model.SchoolModel;
import com.nycschools.a20220131_michealadeniyi_nycschools.databinding.ItemSchoolsBinding;

import java.util.ArrayList;
import java.util.List;

public class SchoolsNYCAdapter extends RecyclerView.Adapter<SchoolsNYCAdapter.ViewHolder> {

    ItemClickListener itemClickListener;

    private List<SchoolModel> schoolModelList;

    public SchoolsNYCAdapter(ItemClickListener itemClickListener) {
        schoolModelList = new ArrayList<>();
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSchoolsBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_schools, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SchoolsNYCAdapter.ViewHolder holder, int position) {
        holder.binding.setModel( schoolModelList.get(position));
        holder.binding.layout.setOnClickListener(v-> itemClickListener.onItemClicked(
                schoolModelList.get(position)));
    }

    @Override
    public int getItemCount() {
        return schoolModelList != null ? schoolModelList.size() : 0;    }

    public void addSchoolList(List<SchoolModel> schoolModels) {
        this.schoolModelList = schoolModels;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemSchoolsBinding binding;
        public ViewHolder(@NonNull ItemSchoolsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface ItemClickListener{
        void onItemClicked(SchoolModel schoolModel);
    }
}
