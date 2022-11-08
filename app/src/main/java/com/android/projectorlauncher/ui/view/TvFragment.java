package com.android.projectorlauncher.ui.view;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
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
import com.android.projectorlauncher.databinding.FragmentTvBinding;
import com.android.projectorlauncher.databinding.ItemTvBinding;
import com.google.android.material.tabs.TabLayout;
import java.util.Arrays;
import java.util.List;

public class TvFragment extends Fragment {

    private final List<Integer> resList = Arrays.asList(R.drawable.uncharted, R.drawable.moon_man, R.drawable.batman);
    FragmentTvBinding tvBinding;
    View headView;
    View tailView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        tvBinding = FragmentTvBinding.inflate(inflater, container, false);
        tvBinding.recommend.setImageResource(R.drawable.uncharted);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        tvBinding.recyclerView.setLayoutManager(layoutManager);
        tvBinding.recyclerView.setAdapter(new TvAdapter());
        tvBinding.recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = 10;
                outRect.bottom = 10;
                outRect.left = 7;
                outRect.right = 7;
            }
        });
        tvBinding.recommend1.setImageResource(R.drawable.moon_man);
        tvBinding.recommend2.setImageResource(R.drawable.batman);
        tvBinding.recommend.setOnFocusChangeListener(new CardAnimationOnFocusChange());
        tvBinding.recommend1.setOnFocusChangeListener(new CardAnimationOnFocusChange());
        tvBinding.recommend2.setOnFocusChangeListener(new CardAnimationOnFocusChange());
        tvBinding.category.setCardElevation(3f);
        tvBinding.search.setCardElevation(3f);
        tvBinding.category.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                tvBinding.category.setCardBackgroundColor(getResources().getColor(R.color.self_6, null));
                ViewCompat.animate(v)
                        .scaleX(1.05f)
                        .scaleY(1.05f)
                        .setDuration(300)
                        .translationZ(1f)
                        .start();
            } else {
                tvBinding.category.setCardBackgroundColor(getResources().getColor(R.color.self_6_un_focus, null));
                ViewCompat.animate(v)
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(300)
                        .translationZ(1f)
                        .start();
            }
        });
        tvBinding.search.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                tvBinding.search.setCardBackgroundColor(getResources().getColor(R.color.self_6, null));
                ViewCompat.animate(v)
                        .scaleX(1.05f)
                        .scaleY(1.05f)
                        .setDuration(300)
                        .translationZ(1.2f)
                        .start();
            } else {
                tvBinding.search.setCardBackgroundColor(getResources().getColor(R.color.self_6_un_focus, null));
                ViewCompat.animate(v)
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(300)
                        .translationZ(1f)
                        .start();
            }
        });

        return tvBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        tvBinding.getRoot().getViewTreeObserver().addOnGlobalFocusChangeListener(changeListener);
        tvBinding.recommend.requestFocus();
    }

    @Override
    public void onPause() {
        super.onPause();
        tvBinding.getRoot().getViewTreeObserver().removeOnGlobalFocusChangeListener(changeListener);
    }

    private ViewTreeObserver.OnGlobalFocusChangeListener changeListener = new ViewTreeObserver.OnGlobalFocusChangeListener() {
        @Override
        public void onGlobalFocusChanged(View oldFocus, View newFocus) {
            if (oldFocus instanceof TabLayout.TabView && !(newFocus instanceof TabLayout.TabView)) {
                tvBinding.recommend.requestFocus();
            }
        }
    };

    class TvViewHolder extends RecyclerView.ViewHolder{
        ItemTvBinding binding;
        public TvViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemTvBinding.bind(itemView);
            itemView.setOnFocusChangeListener(new CardAnimationOnFocusChange());
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams((int) (tvBinding.recyclerView.getWidth() / 8.5), (int) (tvBinding.recyclerView.getHeight() * 0.93));
            binding.getRoot().setLayoutParams(layoutParams);
        }

        public void bind(int resId) {
            binding.image.setImageResource(resId);
        }
    }

    class TvAdapter extends RecyclerView.Adapter<TvViewHolder> {

        @NonNull
        @Override
        public TvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ItemTvBinding binding = ItemTvBinding.inflate(getLayoutInflater(), parent, false);
            return new TvViewHolder(binding.getRoot());
        }

        @Override
        public void onBindViewHolder(@NonNull TvViewHolder holder, int position) {
            if (position == 0) {
                headView = holder.itemView;
            } else if(position == 7) {
                tailView = holder.itemView;
                headView.setNextFocusLeftId(tailView.getId());
                tailView.setNextFocusRightId(headView.getId());
            }
            holder.bind(resList.get(position % 3));
        }

        @Override
        public int getItemCount() {
            return 8;
        }
    }

    static class CardAnimationOnFocusChange implements View.OnFocusChangeListener {

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                ViewCompat.animate(v)
                        .scaleX(1.05f)
                        .scaleY(1.05f)
                        .setDuration(300)
                        .translationZ(1.2f)
                        .start();
            } else {
                ViewCompat.animate(v)
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(300)
                        .translationZ(1f)
                        .start();
            }

        }
    }


}
