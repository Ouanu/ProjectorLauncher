package com.android.projectorlauncher.ui.fragment;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.projectorlauncher.MainActivity;
import com.android.projectorlauncher.R;
import com.android.projectorlauncher.databinding.FragmentGameBinding;
import com.android.projectorlauncher.databinding.ItemGameCardBinding;
import com.android.projectorlauncher.databinding.ItemGameTabBinding;
import com.android.projectorlauncher.presenter.GamePresenter;
import com.android.projectorlauncher.ui.view.GameView;
import com.bumptech.glide.Glide;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class GameFragment extends Fragment implements GameView {

    private FragmentGameBinding gameBinding;
    private GamePresenter presenter;
    private final List<String> tabs = Arrays.asList("健身", "休闲", "竞技");
    private int tag = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        gameBinding = FragmentGameBinding.inflate(inflater, container, false);
        presenter = new GamePresenter((MainActivity) requireActivity());
        gameBinding.categoryRecycleView.setAdapter(new CategoryTabAdapter());
        gameBinding.categoryRecycleView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = 10;
                outRect.bottom = 10;
                outRect.left = 20;
                outRect.right = 20;
            }
        });
        GridLayoutManager manager = new GridLayoutManager(requireContext(), 2, RecyclerView.HORIZONTAL, false);
        gameBinding.cardsRecycleView.setLayoutManager(manager);
        gameBinding.cardsRecycleView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = 10;
                outRect.bottom = 10;
                outRect.left = 20;
                outRect.right = 20;
            }
        });
        gameBinding.cardsRecycleView.setAdapter(new CardAdapter());

        return gameBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.setView(this);
        presenter.init();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void update() {
        Objects.requireNonNull(gameBinding.cardsRecycleView.getAdapter()).notifyDataSetChanged();
    }

    class CategoryTabViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemGameTabBinding tabBinding;
        int position = 0;
        public CategoryTabViewHolder(@NonNull View itemView) {
            super(itemView);
            tabBinding = ItemGameTabBinding.bind(itemView);
            itemView.setOnClickListener(this);
        }

        public void bind(String imgSrc, int position) {
            tabBinding.title.setText(tabs.get(position));
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            tag = position;
            presenter.setGameTag(tag);
        }
    }


    class CategoryTabAdapter extends RecyclerView.Adapter<CategoryTabViewHolder> {

        @NonNull
        @Override
        public CategoryTabViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ItemGameTabBinding tabBinding = ItemGameTabBinding.inflate(getLayoutInflater(), parent, false);
            return new CategoryTabViewHolder(tabBinding.getRoot());
        }

        @Override
        public void onBindViewHolder(@NonNull CategoryTabViewHolder holder, int position) {
            holder.bind("123", position);
        }

        @Override
        public int getItemCount() {
            return tabs.size();
        }
    }

    class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemGameCardBinding cardBinding;
        int position = 0;
        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            cardBinding = ItemGameCardBinding.bind(itemView);
            itemView.setOnClickListener(this);
        }

        private void bind(int position) {
            Glide.with(cardBinding.cardView)
                    .load(R.drawable.moon_man)
                    .centerCrop()
                    .into(cardBinding.cardView);
            if (position == 0) {
                itemView.setNextFocusUpId(R.id.tabLayout);
            }
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            presenter.turnToGame(presenter.getCard(position));
        }
    }

    class CardAdapter extends RecyclerView.Adapter<CardViewHolder> {

        @NonNull
        @Override
        public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ItemGameCardBinding cardBinding = ItemGameCardBinding.inflate(getLayoutInflater(), parent, false);
            return new CardViewHolder(cardBinding.getRoot());
        }

        @Override
        public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
            holder.bind(position);
        }

        @Override
        public int getItemCount() {
            return presenter.sizeOfCards(tag);
        }
    }


}
