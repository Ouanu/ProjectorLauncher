package com.android.projectorlauncher.ui.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.internal.app.LocalePicker;
import com.android.projectorlauncher.R;
import com.android.projectorlauncher.databinding.FragmentLanguageBinding;
import com.android.projectorlauncher.databinding.ItemLanguageBinding;
import com.android.projectorlauncher.utils.LanguageUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

public class LanguageFragment extends Fragment {
    FragmentLanguageBinding languageBinding;
    LinkedHashMap<String, LocalePicker.LocaleInfo> localMap = new LinkedHashMap<>();
    List<String> keys = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        languageBinding = FragmentLanguageBinding.inflate(inflater, container, false);
        localMap = LanguageUtils.getLocalMap(requireContext(), false);
        keys.addAll(localMap.keySet());
        languageBinding.languageRecycleView.setLayoutManager(new LinearLayoutManager(requireContext()));
        languageBinding.languageRecycleView.setAdapter(new LanguageAdapter());
        languageBinding.languageRecycleView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = 5;
                outRect.bottom = 5;
            }
        });
        return languageBinding.getRoot();
    }

    class LanguageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemLanguageBinding binding;
        int index;
        public LanguageViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemLanguageBinding.bind(itemView);
            itemView.setOnFocusChangeListener((v, hasFocus) -> {
                if (hasFocus) {
                    binding.option.setTextColor(requireContext().getColor(R.color.black));
                } else {
                    binding.option.setTextColor(requireContext().getColor(R.color.white));
                }
            });
            itemView.setOnClickListener(this);
        }

        public void bind(int position){
            binding.option.setText(keys.get(position));
            index = position;
        }

        @Override
        public void onClick(View v) {
            LanguageUtils.setLanguage(Objects.requireNonNull(localMap.get(keys.get(index))));
        }
    }

    class LanguageAdapter extends RecyclerView.Adapter<LanguageViewHolder> {

        @NonNull
        @Override
        public LanguageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ItemLanguageBinding binding = ItemLanguageBinding.inflate(getLayoutInflater(), parent, false);
            return new LanguageViewHolder(binding.getRoot());
        }

        @Override
        public void onBindViewHolder(@NonNull LanguageViewHolder holder, int position) {
            holder.bind(position);
        }

        @Override
        public int getItemCount() {
            return localMap.size();
        }
    }

}
