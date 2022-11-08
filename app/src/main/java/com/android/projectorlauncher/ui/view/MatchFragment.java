package com.android.projectorlauncher.ui.view;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.projectorlauncher.R;
import com.android.projectorlauncher.databinding.FragmentMatchBinding;
import com.android.projectorlauncher.databinding.ItemMatchBinding;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MatchFragment extends Fragment {
    private FragmentMatchBinding binding;
    private List<Integer> imageList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageList.add(R.drawable.cba);
        imageList.add(R.drawable.nba);
        imageList.add(R.drawable.uefa);
        imageList.add(R.drawable.more_category);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMatchBinding.inflate(inflater, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(new MatchAdapter());
        binding.recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = 10;
                outRect.bottom = 10;
                outRect.left = 10;
                outRect.right = 10;
            }
        });
        return binding.getRoot();
    }

    private ViewTreeObserver.OnGlobalFocusChangeListener changeListener = new ViewTreeObserver.OnGlobalFocusChangeListener() {
        @Override
        public void onGlobalFocusChanged(View oldFocus, View newFocus) {
            if (oldFocus instanceof TabLayout.TabView && !(newFocus instanceof TabLayout.TabView)) {
                binding.recyclerView.requestFocus();
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        binding.getRoot().getViewTreeObserver().addOnGlobalFocusChangeListener(changeListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.getRoot().getViewTreeObserver().removeOnGlobalFocusChangeListener(changeListener);
    }

    class MatchViewHolder extends RecyclerView.ViewHolder {
        ItemMatchBinding matchBinding;
        public MatchViewHolder(@NonNull View itemView) {
            super(itemView);
            matchBinding = ItemMatchBinding.bind(itemView);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(binding.recyclerView.getHeight()/2, binding.recyclerView.getHeight()/2);
            matchBinding.cardView.setLayoutParams(layoutParams);
            matchBinding.cardView.setRadius(binding.recyclerView.getHeight()/4f);
            itemView.setOnFocusChangeListener(new MatchAnimation());
        }

        public void bind(int position) {
            matchBinding.cardView.setImageResource(imageList.get(position));
        }
    }

    class MatchAdapter extends RecyclerView.Adapter<MatchViewHolder> {

        @NonNull
        @Override
        public MatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ItemMatchBinding matchBinding = ItemMatchBinding.inflate(getLayoutInflater(), parent, false);
            return new MatchViewHolder(matchBinding.getRoot());
        }

        @Override
        public void onBindViewHolder(@NonNull MatchViewHolder holder, int position) {
            holder.bind(position);
        }

        @Override
        public int getItemCount() {
            return imageList.size();
        }
    }

    class MatchAnimation implements View.OnFocusChangeListener {

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                ViewCompat.animate(v)
                        .scaleX(1.05f)
                        .scaleY(1.05f)
                        .setDuration(250)
                        .translationZ(1.2f)
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

}
