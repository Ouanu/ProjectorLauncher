package com.android.projectorlauncher.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.android.projectorlauncher.databinding.FragmentHomeBinding;
import com.android.projectorlauncher.ui.dialog.CategoryDialog;
import com.android.projectorlauncher.utils.JumpToApplication;

import java.util.Date;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private FragmentHomeBinding homeBinding;
    private OnListenerClick listener;
    public interface OnListenerClick {
       void turnToPager(int position);
    }
    private BroadcastReceiver timeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
                updateTime();
            }
        }
    };

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
        return homeBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_TICK);
        requireActivity().registerReceiver(timeReceiver, filter);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateTime();
    }

    @Override
    public void onStop() {
        super.onStop();
        requireActivity().unregisterReceiver(timeReceiver);
    }

    private void updateTime() {
        long l = System.currentTimeMillis();
        Date date = new Date(l);
        CharSequence clock = DateFormat.format("hh:mm", date);
        CharSequence day = DateFormat.format("MM-dd EEEE", date);
        homeBinding.timeClock.setText(clock);
        homeBinding.timeDate.setText(day);
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
        } else if (v == homeBinding.localResource) {
            CategoryDialog dialog = new CategoryDialog();
            dialog.show(getParentFragmentManager(), "category");
        }
    }

}
