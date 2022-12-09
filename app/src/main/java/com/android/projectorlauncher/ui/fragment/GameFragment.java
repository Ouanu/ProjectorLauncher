package com.android.projectorlauncher.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.projectorlauncher.databinding.FragmentGameBinding;

public class GameFragment extends Fragment {

    FragmentGameBinding gameBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        gameBinding = FragmentGameBinding.inflate(inflater, container, false);
        
        return gameBinding.getRoot();
    }


}
