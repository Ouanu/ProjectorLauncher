package com.android.projectorlauncher.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.projectorlauncher.databinding.FragmentAdvanceBinding;
import com.android.projectorlauncher.ui.activity.KeystoneActivity;

public class AdvanceFragment extends Fragment implements View.OnClickListener {

    FragmentAdvanceBinding advanceBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        advanceBinding = FragmentAdvanceBinding.inflate(inflater, container, false);
        advanceBinding.keystone.setOnClickListener(this);
        advanceBinding.picture.setOnClickListener(this);
        return advanceBinding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        if (v == advanceBinding.keystone) {
            Intent intent = new Intent(requireActivity(), KeystoneActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
