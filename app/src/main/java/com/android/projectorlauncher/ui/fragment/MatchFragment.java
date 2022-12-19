package com.android.projectorlauncher.ui.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import com.android.projectorlauncher.R;
import com.android.projectorlauncher.bean.Tag;
import com.android.projectorlauncher.databinding.FragmentMatchBinding;
import com.android.projectorlauncher.presenter.MatchPresenter;
import com.android.projectorlauncher.ui.design.PosterCardView;
import com.android.projectorlauncher.ui.view.MatchView;

import java.util.ArrayList;
import java.util.List;

public class MatchFragment extends Fragment implements MatchView, View.OnClickListener {
    private FragmentMatchBinding binding;
    private MatchPresenter presenter;
    private final List<PosterCardView> cardViews = new ArrayList<>();
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        presenter = new MatchPresenter(requireActivity());
        presenter.setView(this);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMatchBinding.inflate(inflater, container, false);
        binding.cardView4.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                binding.cardView4.setCardBackgroundColor(requireContext().getColor(R.color.self_7));
                ViewCompat.animate(v)
                        .scaleX(1.05f)
                        .scaleY(1.05f)
                        .setDuration(250)
                        .translationZ(1.2f)
                        .start();
            } else {
                binding.cardView4.setCardBackgroundColor(requireContext().getColor(R.color.self_7_un_focus));
                ViewCompat.animate(v)
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(250)
                        .translationZ(1f)
                        .start();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        cardViews.add(binding.cardView1);
        cardViews.add(binding.cardView2);
        cardViews.add(binding.cardView3);
        cardViews.add(binding.cardView4);
        for (int i = 0; i < cardViews.size(); i++) {
            cardViews.get(i).setOnClickListener(this);
            if (i != 3) {
                cardViews.get(i).setCardBackgroundColor(Color.WHITE);
            } else {
                cardViews.get(i).setCardBackgroundColor(requireContext().getColor(R.color.self_7_un_focus_0_3));
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.init();
    }

    @Override
    public void onPause() {
        super.onPause();
        cardViews.clear();
    }


    @Override
    public void updateAll() {
        for (int i = 0; i < presenter.sizeOfCards(); i++) {
            if (i < cardViews.size()) {
                cardViews.get(i).setImageDrawable(presenter.getImage(i), Tag.MATCH_IMAGE, i);
            }
        }
    }

    @Override
    public void updateIndex(int index) {
        if (presenter.getImage(index) != null) {
            if (cardViews.get(index) != null) {
                cardViews.get(index).setImageDrawable(presenter.getImage(index), Tag.MATCH_IMAGE, index);
            }
        }
    }

    @Override
    public void onClick(View v) {
        for (int i = 0; i < cardViews.size(); i++) {
            if (cardViews.get(i) == v) {
                presenter.turnToChannel(i);
                return;
            }
        }

    }
}
