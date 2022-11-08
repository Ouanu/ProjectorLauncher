package com.android.projectorlauncher.ui.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import com.android.projectorlauncher.R;
import com.android.projectorlauncher.bean.VideoCard;
import com.android.projectorlauncher.databinding.FragmentMovieBinding;
import com.android.projectorlauncher.utils.JumpToApplication;
import com.android.projectorlauncher.utils.TestFileUtils;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import java.util.List;

public class MovieFragment extends Fragment implements View.OnClickListener {
    private List<VideoCard> cards;
    private int currentIndex = 0;
    private int lastIndex = 0;
    FragmentMovieBinding movieBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        movieBinding = FragmentMovieBinding.inflate(inflater, container, false);
        TestFileUtils.writeTestFiles(requireActivity());
        setImageResources();
        setFocusChange();
        setClick();
        return movieBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        movieBinding.recommend1.requestFocus();
    }

    private void setImageResources() {
        cards = TestFileUtils.getCards(requireActivity());
        Glide.with(movieBinding.image)
                .load(cards.get(currentIndex).getImgSrc())
                .error(R.color.white)
                .centerCrop()
                .into(movieBinding.image);
        movieBinding.recommend1.setImageResource(cards.get(0).getImgSrc());
        movieBinding.recommend2.setImageResource(cards.get(1).getImgSrc());
        movieBinding.recommend3.setImageResource(cards.get(2).getImgSrc());
        movieBinding.recommend4.setImageResource(cards.get(3).getImgSrc());
    }

    private void setFocusChange() {
        movieBinding.recommend1.setOnFocusChangeListener(new PosterAnimationOnFocusChange());
        movieBinding.recommend2.setOnFocusChangeListener(new PosterAnimationOnFocusChange());
        movieBinding.recommend3.setOnFocusChangeListener(new PosterAnimationOnFocusChange());
        movieBinding.recommend4.setOnFocusChangeListener(new PosterAnimationOnFocusChange());
        movieBinding.search.setOnFocusChangeListener(new MovieAnimationOnFocusChange());
        movieBinding.category.setOnFocusChangeListener(new MovieAnimationOnFocusChange());

        movieBinding.getRoot().getViewTreeObserver().addOnGlobalFocusChangeListener((oldFocus, newFocus) -> {
            if (oldFocus instanceof TabLayout.TabView && !(newFocus instanceof TabLayout.TabView)) {
                movieBinding.recommend1.requestFocus();
            }
        });
    }

    private void setClick() {
        movieBinding.recommend1.setOnClickListener(this);
        movieBinding.recommend2.setOnClickListener(this);
        movieBinding.recommend3.setOnClickListener(this);
        movieBinding.recommend4.setOnClickListener(this);
        movieBinding.search.setOnClickListener(this);
        movieBinding.category.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == movieBinding.recommend1) {
            JumpToApplication.playVideo(requireActivity(), cards.get(0).getId());
        } else if (v == movieBinding.recommend2) {
            JumpToApplication.playVideo(requireActivity(), cards.get(1).getId());
        } else if (v == movieBinding.recommend3) {
            JumpToApplication.playVideo(requireActivity(), cards.get(2).getId());
        } else if (v == movieBinding.recommend4) {
            JumpToApplication.playVideo(requireActivity(), cards.get(3).getId());
        } else if (v == movieBinding.search) {
            JumpToApplication.turnToSearch(requireActivity());
        } else if (v == movieBinding.category) {
            JumpToApplication.turnToCategory(requireActivity(), "movie");
        }
    }


    class PosterAnimationOnFocusChange implements View.OnFocusChangeListener {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (v == movieBinding.recommend1) {
                currentIndex = 0;
            } else if (v == movieBinding.recommend2) {
                currentIndex = 1;
            } else if (v == movieBinding.recommend3) {
                currentIndex = 2;
            } else if (v == movieBinding.recommend4) {
                currentIndex = 3;
            }
            if (hasFocus) {
                ViewCompat.animate(v)
                        .scaleX(1.05f)
                        .scaleY(1.05f)
                        .setDuration(300)
                        .translationZ(1.2f)
                        .start();
                if (currentIndex == lastIndex) {
                    return;
                }
                Glide.with(movieBinding.image)
                        .load(cards.get(currentIndex).getImgSrc())
                        .error(R.color.white)
                        .centerCrop()
                        .into(movieBinding.image);
                lastIndex = currentIndex;
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
    class MovieAnimationOnFocusChange implements View.OnFocusChangeListener {

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                ViewCompat.animate(v)
                        .scaleX(1.05f)
                        .scaleY(1.05f)
                        .setDuration(300)
                        .translationZ(1.2f)
                        .start();
                ((CardView)v).setCardBackgroundColor(requireContext().getColor(R.color.self_5));
            } else {
                ViewCompat.animate(v)
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(300)
                        .translationZ(1f)
                        .start();
                ((CardView)v).setCardBackgroundColor(requireContext().getColor(R.color.self_5_un_focus));
            }
        }
    }


}
