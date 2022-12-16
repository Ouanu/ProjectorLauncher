package com.android.projectorlauncher.ui.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.projectorlauncher.R;
import com.android.projectorlauncher.bean.GameCard;
import com.android.projectorlauncher.databinding.FragmentGameBinding;
import com.android.projectorlauncher.databinding.ItemGameCardBinding;
import com.android.projectorlauncher.databinding.ItemGameTabBinding;
import com.android.projectorlauncher.presenter.GamePresenter;
import com.android.projectorlauncher.ui.view.GameView;
import com.bumptech.glide.Glide;
import java.util.Arrays;
import java.util.List;

public class GameFragment extends Fragment implements GameView {

    private FragmentGameBinding gameBinding;
    private GamePresenter presenter;
    private List<String> tabs = Arrays.asList("健身", "休闲", "竞技");

    private final List<GameCard> cards = Arrays.asList(
            new GameCard("1", "1", "2", "2", 0),
            new GameCard("1", "1", "2", "2", 0),
            new GameCard("1", "1", "2", "2", 0),
            new GameCard("1", "1", "2", "2", 0),
            new GameCard("1", "1", "2", "2", 0),
            new GameCard("1", "1", "2", "2", 0));
    private final MutableLiveData<List<GameCard>> mutableCards = new MutableLiveData<>(cards);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        gameBinding = FragmentGameBinding.inflate(inflater, container, false);
        presenter = new GamePresenter(requireActivity());
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
//        presenter.init();
    }

    @Override
    public void update() {
//        mutableCards.setValue(presenter.getCards());
    }

    class CategoryTabViewHolder extends RecyclerView.ViewHolder {
        ItemGameTabBinding tabBinding;

        public CategoryTabViewHolder(@NonNull View itemView) {
            super(itemView);
            tabBinding = ItemGameTabBinding.bind(itemView);
        }

        public void bind(String imgSrc, String title) {
            tabBinding.title.setText(title);
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
            holder.bind("123", tabs.get(position));
        }

        @Override
        public int getItemCount() {
            return tabs.size();
        }
    }

    class CardViewHolder extends RecyclerView.ViewHolder{
        ItemGameCardBinding cardBinding;
        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            cardBinding = ItemGameCardBinding.bind(itemView);
        }

        private void bind(int position) {
            Glide.with(cardBinding.cardView)
                    .load(R.drawable.moon_man)
                    .centerCrop()
                    .into(cardBinding.cardView);
            if (position == 0) {
                itemView.setNextFocusUpId(R.id.tabLayout);
            }
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
            return cards.size();
        }
    }


}
