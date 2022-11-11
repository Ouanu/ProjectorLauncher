package com.android.projectorlauncher.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
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
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.projectorlauncher.bean.VideoCard;
import com.android.projectorlauncher.databinding.FragmentChildrenBinding;
import com.android.projectorlauncher.databinding.ItemChildrenBinding;
import com.android.projectorlauncher.databinding.ItemTvBinding;
import com.android.projectorlauncher.presenter.ChildrenPresenter;
import com.android.projectorlauncher.ui.view.ChildrenView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ChildrenFragment extends Fragment implements ChildrenView, View.OnClickListener {

    private FragmentChildrenBinding tvBinding;
    private View headView;
    private ChildrenPresenter presenter;
    private final MutableLiveData<List<VideoCard>> cards = new MutableLiveData<>();
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        cards.setValue(new ArrayList<>());
        presenter = new ChildrenPresenter(requireActivity());
        presenter.setView(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        tvBinding = FragmentChildrenBinding.inflate(inflater, container, false);
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

        setFocus();
        setClick();
        return tvBinding.getRoot();
    }

    private void setClick() {
        tvBinding.recommend.setOnClickListener(this);
        tvBinding.recommend1.setOnClickListener(this);
        tvBinding.recommend2.setOnClickListener(this);
        tvBinding.search.setOnClickListener(this);
        tvBinding.category.setOnClickListener(this);
    }

    private void setFocus() {
        tvBinding.recommend.setOnFocusChangeListener(new CardAnimationOnFocusChange());
        tvBinding.recommend1.setOnFocusChangeListener(new CardAnimationOnFocusChange());
        tvBinding.recommend2.setOnFocusChangeListener(new CardAnimationOnFocusChange());
        tvBinding.category.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                ViewCompat.animate(tvBinding.categoryCard)
                        .scaleX(1.05f)
                        .scaleY(1.05f)
                        .setDuration(300)
                        .translationZ(1f)
                        .start();
            } else {
                ViewCompat.animate(tvBinding.categoryCard)
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(300)
                        .translationZ(1f)
                        .start();
            }
        });
        tvBinding.search.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                ViewCompat.animate(tvBinding.searchCard)
                        .scaleX(1.05f)
                        .scaleY(1.05f)
                        .setDuration(300)
                        .translationZ(1.2f)
                        .start();
            } else {
                ViewCompat.animate(tvBinding.searchCard)
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(300)
                        .translationZ(1f)
                        .start();
            }
        });
    }
    @Override
    public void onClick(View v) {
        if (v == tvBinding.recommend) {
            presenter.turnVideoDetailPage(0);
        } else if (v == tvBinding.recommend1) {
            presenter.turnVideoDetailPage(1);
        } else if (v == tvBinding.recommend2) {
            presenter.turnVideoDetailPage(2);
        } else if (v == tvBinding.search) {
            presenter.turnToSearchPage();
        } else if (v == tvBinding.category) {
            presenter.turnToTvCategoryPage();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        tvBinding.getRoot().getViewTreeObserver().addOnGlobalFocusChangeListener(changeListener);
        tvBinding.recommend.requestFocus();
        if (cards.getValue() == null || cards.getValue().size() == 0 || presenter.sizeOfCards() == 0) {
            presenter.init();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        tvBinding.getRoot().getViewTreeObserver().removeOnGlobalFocusChangeListener(changeListener);
    }

    private final ViewTreeObserver.OnGlobalFocusChangeListener changeListener = new ViewTreeObserver.OnGlobalFocusChangeListener() {
        @Override
        public void onGlobalFocusChanged(View oldFocus, View newFocus) {
            if (oldFocus instanceof TabLayout.TabView && !(newFocus instanceof TabLayout.TabView)) {
                tvBinding.recommend.requestFocus();
            }
        }
    };

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void update(VideoCard r1, VideoCard r2, VideoCard r3, List<VideoCard> videoCards) {
        if (r1 != null) {
            tvBinding.recommend.setImageResource(r1.getImgSrc());
        }
        if (r2 != null) {
            tvBinding.recommend1.setImageResource(r2.getImgSrc());
        }
        if (r2 != null) {
            tvBinding.recommend2.setImageResource(r3.getImgSrc());
        }
        if (videoCards != null) {
            cards.setValue(videoCards);
            if (tvBinding.recyclerView.getAdapter() != null)
                tvBinding.recyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    class TvViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemChildrenBinding binding;
        int index = -1;
        public TvViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemChildrenBinding.bind(itemView);
            itemView.setOnFocusChangeListener(new CardAnimationOnFocusChange());
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams((int) (tvBinding.recyclerView.getWidth() / 8.5), (int) (tvBinding.recyclerView.getHeight() * 0.93));
            binding.getRoot().setLayoutParams(layoutParams);
            itemView.setOnClickListener(this);
        }

        public void bind(int position) {
            if (cards.getValue() == null) return;
            binding.image.setImageResource(presenter.getRecommendsImage(position));
            index = position;
        }

        @Override
        public void onClick(View v) {
            presenter.fromRecommendsToVideoDetailPage(index);
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
            } else if(cards.getValue() != null && position == cards.getValue().size() - 1) {
                View tailView = holder.itemView;
                headView.setNextFocusLeftId(tailView.getId());
                tailView.setNextFocusRightId(headView.getId());
            }
            holder.bind(position);
        }

        @Override
        public int getItemCount() {
            if (cards.getValue() == null) return 0;
            return cards.getValue().size();
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
