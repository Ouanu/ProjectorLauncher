package com.android.projectorlauncher.ui.view;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.projectorlauncher.R;
import com.android.projectorlauncher.bean.VideoCard;
import com.android.projectorlauncher.databinding.FragmentPagerBinding;
import com.android.projectorlauncher.databinding.ItemCardBinding;
import com.android.projectorlauncher.utils.JumpToApplication;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.tabs.TabLayout;

import java.util.List;
import java.util.Objects;

public class PagerFragment extends Fragment {
    private final LiveData<List<VideoCard>> cards;
    private View selectView;

    public PagerFragment(List<VideoCard> cards) {
        this.cards = new LiveData<List<VideoCard>>(cards) {
            @Override
            public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super List<VideoCard>> observer) {
                super.observe(owner, observer);
            }
        };

    }

    public void setCards(List<VideoCard> cards) {
        Objects.requireNonNull(this.cards.getValue()).addAll(cards);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        com.android.projectorlauncher.databinding.FragmentPagerBinding pagerBinding = FragmentPagerBinding.inflate(inflater, container, false);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 7);
        pagerBinding.recyclerView.setLayoutManager(layoutManager);
        pagerBinding.recyclerView.setAdapter(new PagerAdapter());
        pagerBinding.recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = 20;
                outRect.bottom = 15;
                outRect.left = 20;
                outRect.right = 20;
            }
        });
//        pagerBinding.recyclerView.getViewTreeObserver().addOnGlobalFocusChangeListener((oldFocus, newFocus) -> {
//
//            if (selectView != null && (!(newFocus instanceof CardView) && !(newFocus instanceof TabLayout.TabView))) {
//                selectView.setFocusable(true);
//                selectView.requestFocus();
//            }
//
//            if (newFocus instanceof CardView)
//                selectView = newFocus;
//
//        });
        pagerBinding.more.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                ViewCompat.animate(v)
                        .setDuration(200)
                        .scaleX(1.10f)
                        .scaleY(1.10f)
                        .start();
            } else {
                ViewCompat.animate(v)
                        .setDuration(200)
                        .scaleX(1f)
                        .scaleY(1f)
                        .start();
            }
        });
        pagerBinding.search.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                ViewCompat.animate(v)
                        .setDuration(200)
                        .scaleX(1.10f)
                        .scaleY(1.10f)
                        .start();
            } else {
                ViewCompat.animate(v)
                        .setDuration(200)
                        .scaleX(1f)
                        .scaleY(1f)
                        .start();
            }
        });
        pagerBinding.history.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                ViewCompat.animate(v)
                        .setDuration(200)
                        .scaleX(1.10f)
                        .scaleY(1.10f)
                        .start();
            } else {
                ViewCompat.animate(v)
                        .setDuration(200)
                        .scaleX(1f)
                        .scaleY(1f)
                        .start();
            }
        });

        return pagerBinding.getRoot();
    }



    class PagerAdapter extends RecyclerView.Adapter<PagerAdapter.CardViewHolder> {

        @NonNull
        @Override
        public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ItemCardBinding cardBinding = ItemCardBinding.inflate(getLayoutInflater(), parent, false);
            return new CardViewHolder(cardBinding.getRoot());
        }

        @Override
        public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
            if (cards.getValue() == null) return;
            if ((position + 1) % 7 == 0)
                holder.itemView.setNextFocusRightId(holder.itemView.getId());
            holder.bind(cards.getValue().get(position));

        }

        @Override
        public int getItemCount() {
            if (cards.getValue() == null) return 0;
            return cards.getValue().size();
        }

        public class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            ItemCardBinding binding;
            VideoCard card;

            public CardViewHolder(@NonNull View itemView) {
                super(itemView);
                binding = ItemCardBinding.bind(itemView);
                itemView.setOnFocusChangeListener((v, hasFocus) -> {
                    if (hasFocus) {
                        ViewCompat.animate(v)
                                .setDuration(200)
                                .scaleX(1.10f)
                                .scaleY(1.10f)
                                .start();
                    } else {
                        ViewCompat.animate(v)
                                .setDuration(200)
                                .scaleX(1f)
                                .scaleY(1f)
                                .start();
                    }
                });
                itemView.setOnClickListener(this);
            }

            public void bind(VideoCard card) {
                binding.title.setText(card.getName());
                Glide.with(itemView)
                        .load(R.drawable.moon_man)
                        .error(R.color.black)
                        .centerInside()
                        .skipMemoryCache(true)//跳过内存缓存
                        .diskCacheStrategy(DiskCacheStrategy.NONE)//不缓冲disk硬盘中
                        .into(binding.image);
                this.card = card;
            }

            @Override
            public void onClick(View v) {
                JumpToApplication.turnToKtcp(getContext(), card.getId());
            }
        }
    }
}
