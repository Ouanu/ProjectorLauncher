package com.android.projectorlauncher.ui.fragment;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.projectorlauncher.R;
import com.android.projectorlauncher.databinding.ItemSettingsBinding;
import com.android.projectorlauncher.databinding.FragmentSettingsBinding;

import java.util.ArrayList;
import java.util.List;


public class SettingsFragment extends Fragment {
    private final List<String> options = new ArrayList<>();
    private TextView selectView = null;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (options.size() == 0) {
            options.add("系统信息");
            options.add("无线网络");
            options.add("有线网络");
//            options.add("画面设置");
//            options.add("声音设置");
            options.add("时间设置");
            options.add("进阶设置");
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentSettingsBinding settingsBinding = FragmentSettingsBinding.inflate(inflater, container, false);
        settingsBinding.recyclerViewSettings.setAdapter(new SettingsAdapter());
        settingsBinding.recyclerViewSettings.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.right = 45;
                outRect.left = 45;
                outRect.top = 10;
                outRect.bottom = 10;
            }
        });
        getParentFragmentManager().beginTransaction()
                .replace(R.id.container_frameLayout, new SystemInfoFragment())
                .commit();
        return settingsBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        selectView = null;
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
            if (position == 0) {
                itemView.setNextFocusUpId(R.id.tabLayout);
                if (selectView == null) {
                    selectView = binding.option;
                    ViewCompat.animate(itemView)
                            .scaleX(1.2f)
                            .scaleY(1.2f)
                            .setDuration(250)
                            .start();
                }
            }

        }

        @Override
        public void onClick(View v) {
            switch (position) {
                case 0:
                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.container_frameLayout, new SystemInfoFragment())
                            .commit();
                    break;
                case 1:
                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.container_frameLayout, new WifiFragment())
                            .commit();
                    binding.option.setNextFocusRightId(R.id.btn_switch);
                    break;
                case 2:
                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.container_frameLayout, new EthernetFragment())
                            .commit();
                    binding.option.setNextFocusRightId(R.id.btn_switch);
                    break;
                case 3:
                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.container_frameLayout, new TimeFragment())
                            .commit();
                    binding.option.setNextFocusRightId(R.id.mode);
                    break;
                case 4:
                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.container_frameLayout, new AdvanceFragment())
                            .commit();
                    binding.option.setNextFocusRightId(R.id.keystone);
                    break;
                default:
                    break;

            }
            Toast.makeText(requireContext(), options.get(position), Toast.LENGTH_SHORT).show();
            if (selectView != null) {
                ViewCompat.animate(selectView)
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(250)
                        .start();
            }
            selectView = binding.option;
            ViewCompat.animate(selectView)
                    .scaleX(1.2f)
                    .scaleY(1.2f)
                    .setDuration(250)
                    .start();
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
