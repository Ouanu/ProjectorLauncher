package com.android.projectorlauncher.ui.view;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
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

import com.android.projectorlauncher.databinding.FragmentShowBinding;
import com.android.projectorlauncher.databinding.ItemShowBinding;
import com.google.android.material.tabs.TabLayout;

public class ShowFragment extends Fragment {
    private FragmentShowBinding showBinding;
    private View selectView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        showBinding = FragmentShowBinding.inflate(inflater, container, false);
        LinearLayoutManager layout = new LinearLayoutManager(requireContext());
        layout.setOrientation(LinearLayoutManager.HORIZONTAL);
        showBinding.recyclerView.setLayoutManager(layout);
        showBinding.recyclerView.setAdapter(new ShowAdapter());
        showBinding.recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = 15;
                outRect.bottom = 10;
                outRect.left = 10;
                outRect.right = 10;
            }
        });

        showBinding.recommend1.setOnFocusChangeListener(new ShowAnimation());
        showBinding.recommend2.setOnFocusChangeListener(new ShowAnimation());
        showBinding.recommend3.setOnFocusChangeListener(new ShowAnimation());
        showBinding.recommend4.setOnFocusChangeListener(new ShowAnimation());
        showBinding.category.setOnFocusChangeListener(new ShowAnimation());
        showBinding.search.setOnFocusChangeListener(new ShowAnimation());
        return showBinding.getRoot();
    }

    private ViewTreeObserver.OnGlobalFocusChangeListener changeListener = new ViewTreeObserver.OnGlobalFocusChangeListener() {
        @Override
        public void onGlobalFocusChanged(View oldFocus, View newFocus) {
            if (oldFocus instanceof TabLayout.TabView && !(newFocus instanceof TabLayout.TabView)) {
                showBinding.recyclerView.requestFocus();
            }
            if(newFocus instanceof RecyclerView) {
                selectView.requestFocus();
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        showBinding.getRoot().getViewTreeObserver().addOnGlobalFocusChangeListener(changeListener);
        showBinding.recyclerView.requestFocus();
    }

    @Override
    public void onPause() {
        super.onPause();
        showBinding.getRoot().getViewTreeObserver().removeOnGlobalFocusChangeListener(changeListener);
    }

    class ShowViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemShowBinding binding;

        public ShowViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemShowBinding.bind(itemView);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(showBinding.recyclerView.getWidth() / 5, (int) (showBinding.recyclerView.getHeight() * 0.95));
            binding.getRoot().setLayoutParams(layoutParams);
            itemView.setOnClickListener(this);
            itemView.setOnFocusChangeListener((v, hasFocus) -> {
                if (hasFocus) {
                    ViewCompat.animate(v)
                            .scaleX(1.05f)
                            .scaleY(1.05f)
                            .setDuration(250)
                            .translationZ(1.2f)
                            .start();
                    selectView = itemView;
                } else {
                    ViewCompat.animate(v)
                            .scaleX(1f)
                            .scaleY(1f)
                            .setDuration(250)
                            .translationZ(1f)
                            .start();
                }
            });
        }

        public void bind() {

        }

        @Override
        public void onClick(View v) {

        }
    }

    class ShowAnimation implements View.OnFocusChangeListener {

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

    class ShowAdapter extends RecyclerView.Adapter<ShowViewHolder> {

        @NonNull
        @Override
        public ShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ItemShowBinding binding = ItemShowBinding.inflate(getLayoutInflater(), parent, false);
            return new ShowViewHolder(binding.getRoot());
        }

        @Override
        public void onBindViewHolder(@NonNull ShowViewHolder holder, int position) {
            if (position == 0) {
                if(selectView == null)
                    selectView = holder.itemView;
                holder.itemView.setNextFocusLeftId(holder.itemView.getId());
            }
            if (position == getItemCount()-1) {
                holder.itemView.setNextFocusRightId(holder.itemView.getId());
            }
        }

        @Override
        public int getItemCount() {
            return 5;
        }
    }
}
