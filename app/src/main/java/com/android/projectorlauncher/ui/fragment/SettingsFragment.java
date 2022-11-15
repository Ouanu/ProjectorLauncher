package com.android.projectorlauncher.ui.fragment;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.projectorlauncher.R;
import com.android.projectorlauncher.databinding.ItemSettingsBinding;
import com.android.projectorlauncher.databinding.FragmentSettingsBinding;

import java.util.ArrayList;
import java.util.List;


public class SettingsFragment extends Fragment {
    FragmentSettingsBinding settingsBinding;
    private List<String> options = new ArrayList<>();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (options.size() == 0) {
            options.add("无线网络");
            options.add("有线网络");
            options.add("画面设置");
            options.add("声音设置");
            options.add("梯度矫正");
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        settingsBinding = FragmentSettingsBinding.inflate(inflater, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        settingsBinding.recyclerView.setLayoutManager(layoutManager);
        settingsBinding.recyclerView.setAdapter(new SettingsAdapter());
        settingsBinding.recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
//                outRect.right = 5;
//                outRect.left = 5;
//                outRect.top = 10;
//                outRect.bottom = 10;
            }
        });
        getParentFragmentManager().beginTransaction().add(R.id.container_frameLayout, new WifiFragment()).commit();
        return settingsBinding.getRoot();
    }

    class SettingsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemSettingsBinding binding;
        int position;
        public SettingsViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemSettingsBinding.bind(itemView);
            itemView.setOnClickListener(this);
        }

        public void bind(int position) {
            this.position = position;
            binding.option.setText(options.get(position));
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(requireContext(), options.get(position), Toast.LENGTH_SHORT).show();
        }
    }

    class SettingsAdapter extends RecyclerView.Adapter<SettingsViewHolder> {

        @NonNull
        @Override
        public SettingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ItemSettingsBinding binding = ItemSettingsBinding.inflate(getLayoutInflater(), parent, false);
            return new SettingsViewHolder(binding.getRoot());
        }

        @Override
        public void onBindViewHolder(@NonNull SettingsViewHolder holder, int position) {
            holder.bind(position);
        }

        @Override
        public int getItemCount() {
            return options.size();
        }
    }
}
