package com.android.projectorlauncher.ui.view;

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

import com.android.projectorlauncher.R;
import com.android.projectorlauncher.bean.MatchCard;
import com.android.projectorlauncher.databinding.FragmentMatchBinding;
import com.android.projectorlauncher.databinding.ItemMatchBinding;
import com.android.projectorlauncher.presenter.MatchPresenter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MatchFragment extends Fragment implements MatchView{
    private FragmentMatchBinding binding;
    private MatchPresenter presenter;
    private final MutableLiveData<List<MatchCard>> matchCards = new MutableLiveData<>();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        presenter = new MatchPresenter(requireActivity());
        matchCards.setValue(new ArrayList<>());
        presenter.setView(this);
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

    private final ViewTreeObserver.OnGlobalFocusChangeListener changeListener = new ViewTreeObserver.OnGlobalFocusChangeListener() {
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
        if (matchCards.getValue() == null || matchCards.getValue().size() == 0 || presenter.sizeOfCards() == 0) {
            presenter.init();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.getRoot().getViewTreeObserver().removeOnGlobalFocusChangeListener(changeListener);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void update(List<MatchCard> cards) {
        matchCards.setValue(cards);
        if (binding.recyclerView.getAdapter() == null) return;
        binding.recyclerView.getAdapter().notifyDataSetChanged();
    }

    class MatchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemMatchBinding matchBinding;
        int index = 0;
        public MatchViewHolder(@NonNull View itemView) {
            super(itemView);
            matchBinding = ItemMatchBinding.bind(itemView);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(binding.recyclerView.getHeight()/2, binding.recyclerView.getHeight()/2);
            matchBinding.cardView.setLayoutParams(layoutParams);
            matchBinding.cardView.setRadius(binding.recyclerView.getHeight()/4f);
            itemView.setOnClickListener(this);
        }

        public void bind(int position) {
            if (matchCards.getValue() == null) return;
            index = position;
            if (position == matchCards.getValue().size() - 1) {
                matchBinding.cardView.setCardBackgroundColor(requireContext().getColor(R.color.self_7_un_focus));
            }
            if (position < matchCards.getValue().size() - 1) {
                itemView.setOnFocusChangeListener(new MatchAnimation());
            } else {
                itemView.setOnFocusChangeListener((v, hasFocus) -> {
                    if (hasFocus) {
                        matchBinding.cardView.setCardBackgroundColor(requireContext().getColor(R.color.self_7));
                        ViewCompat.animate(v)
                                .scaleX(1.05f)
                                .scaleY(1.05f)
                                .setDuration(250)
                                .translationZ(1.2f)
                                .start();
                    } else {
                        matchBinding.cardView.setCardBackgroundColor(requireContext().getColor(R.color.self_7_un_focus));
                        ViewCompat.animate(v)
                                .scaleX(1f)
                                .scaleY(1f)
                                .setDuration(250)
                                .translationZ(1f)
                                .start();
                    }
                });
            }
            matchBinding.cardView.setImageResource(matchCards.getValue().get(position).getImgSrc());
        }

        @Override
        public void onClick(View v) {
            presenter.turnToChannel(index);
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
            if (position == 0)
                holder.itemView.setNextFocusLeftId(holder.itemView.getId());
            if(matchCards.getValue() != null && position == matchCards.getValue().size() - 1)
                holder.itemView.setNextFocusRightId(holder.itemView.getId());
            holder.bind(position);
        }

        @Override
        public int getItemCount() {
            if (matchCards.getValue() == null) return 0;
            return matchCards.getValue().size();
        }
    }

    static class MatchAnimation implements View.OnFocusChangeListener {

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
