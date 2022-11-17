package com.android.projectorlauncher.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.projectorlauncher.R;
import com.android.projectorlauncher.databinding.FragmentSystemBinding;

public class SystemInfoFragment extends Fragment {
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        com.android.projectorlauncher.databinding.FragmentSystemBinding systemBinding = FragmentSystemBinding.inflate(inflater, container, false);
        TextView memory = new TextView(getContext());
        TextView storage = new TextView(getContext());
        TextView systemVersion = new TextView(getContext());
        TextView softwareVersion = new TextView(getContext());

        memory.setText(R.string.memory);
        memory.setGravity(Gravity.CENTER);
        memory.setTextColor(requireContext().getColor(R.color.self_4));
        float typeSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                13,
                getResources().getDisplayMetrics());
        memory.setTextSize(typeSize);
        memory.setPadding(20, 10, 20, 10);

        storage.setText(R.string.storage);
        storage.setGravity(Gravity.CENTER);
        storage.setTextColor(requireContext().getColor(R.color.self_4));
        storage.setTextSize(typeSize);
        storage.setPadding(20, 10, 20, 10);

        systemVersion.setText(R.string.system_version);
        systemVersion.setGravity(Gravity.CENTER);
        systemVersion.setTextColor(requireContext().getColor(R.color.self_4));
        systemVersion.setTextSize(typeSize);
        systemVersion.setPadding(20, 10, 20, 10);

        softwareVersion.setText(R.string.software_version);
        softwareVersion.setGravity(Gravity.CENTER);
        softwareVersion.setTextColor(requireContext().getColor(R.color.self_4));
        softwareVersion.setTextSize(typeSize);
        softwareVersion.setPadding(20, 10, 20, 10);

        systemBinding.gridLayout.addView(memory);
        systemBinding.gridLayout.addView(storage);
        systemBinding.gridLayout.addView(systemVersion);
        systemBinding.gridLayout.addView(softwareVersion);

        return systemBinding.getRoot();
    }
}
