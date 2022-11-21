package com.android.projectorlauncher.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import com.android.projectorlauncher.databinding.FragmentHomeBinding;
import com.android.projectorlauncher.utils.JumpToApplication;

public class HomeFragment extends Fragment implements View.OnClickListener, View.OnFocusChangeListener {
    private FragmentHomeBinding homeBinding;
    private OnListenerClick listener;
    public interface OnListenerClick {
       void turnToPager(int position);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (OnListenerClick) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false);
        homeBinding.application.setOnClickListener(this);
        homeBinding.game.setOnClickListener(this);
        homeBinding.localResource.setOnClickListener(this);
        homeBinding.movie.setOnClickListener(this);
        homeBinding.recentWatch.setOnClickListener(this);
        homeBinding.search.setOnClickListener(this);
        homeBinding.settings.setOnClickListener(this);
        homeBinding.shows.setOnClickListener(this);
        homeBinding.tv.setOnClickListener(this);

        homeBinding.application.setOnFocusChangeListener(this);
        homeBinding.game.setOnFocusChangeListener(this);
        homeBinding.localResource.setOnFocusChangeListener(this);
        homeBinding.movie.setOnFocusChangeListener(this);
        homeBinding.recentWatch.setOnFocusChangeListener(this);
        homeBinding.search.setOnFocusChangeListener(this);
        homeBinding.settings.setOnFocusChangeListener(this);
        homeBinding.shows.setOnFocusChangeListener(this);
        homeBinding.tv.setOnFocusChangeListener(this);

        return homeBinding.getRoot();
    }

    @Override
    public void onClick(View v) {
        if (v == homeBinding.application) {
            listener.turnToPager(7);
        } else if (v == homeBinding.tv) {
            listener.turnToPager(2);
        } else if (v == homeBinding.movie) {
            listener.turnToPager(1);
        } else if (v == homeBinding.shows) {
            listener.turnToPager(3);
        } else if (v == homeBinding.settings) {
            listener.turnToPager(8);
        } else if (v == homeBinding.recentWatch) {
            JumpToApplication.turnToHistory(requireContext());
        } else if (v == homeBinding.search) {
            JumpToApplication.turnToSearch(requireContext());
        }
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            ViewCompat.animate(v)
                    .scaleX(1.2f)
                    .scaleY(1.2f)
                    .setDuration(250)
                    .translationZ(2f)
                    .start();
        } else {
            ViewCompat.animate(v)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(250)
                    .translationZ(1f)
                    .start();
        }
    }
}
