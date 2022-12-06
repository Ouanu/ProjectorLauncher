package com.android.projectorlauncher.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.projectorlauncher.R;
import com.android.projectorlauncher.databinding.FragmentUnityTvBinding;
import com.android.projectorlauncher.presenter.TvPresenter;
import com.android.projectorlauncher.ui.view.MovieView;
import com.bumptech.glide.Glide;

public class TvFragment extends Fragment implements View.OnClickListener, MovieView {
    private FragmentUnityTvBinding tvBinding;
    private TvPresenter presenter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        presenter = new TvPresenter(requireActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        tvBinding = FragmentUnityTvBinding.inflate(inflater, container, false);
        setImageResources();
        setClick();
        return tvBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.setView(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter.sizeOfCards() == 0) {
            presenter.init();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    //设置图片
    private void setImageResources() {
        Glide.with(tvBinding.recommend1)
                .load(presenter.getImage(0))
                .error(R.color.white)
                .fitCenter()
                .into(tvBinding.recommend1);
        Glide.with(tvBinding.recommend2)
                .load(presenter.getImage(1))
                .error(R.color.white)
                .fitCenter()
                .into(tvBinding.recommend2);
        Glide.with(tvBinding.recommend3)
                .load(presenter.getImage(2))
                .error(R.color.white)
                .fitCenter()
                .into(tvBinding.recommend3);
        Glide.with(tvBinding.recommend4)
                .load(presenter.getImage(3))
                .error(R.color.white)
                .fitCenter()
                .into(tvBinding.recommend4);
        Glide.with(tvBinding.recommend5)
                .load(presenter.getImage(4))
                .error(R.color.white)
                .fitCenter()
                .into(tvBinding.recommend5);
        Glide.with(tvBinding.recommend6)
                .load(presenter.getImage(5))
                .error(R.color.white)
                .fitCenter()
                .into(tvBinding.recommend6);
        Glide.with(tvBinding.recommend7)
                .load(presenter.getImage(6))
                .error(R.color.white)
                .fitCenter()
                .into(tvBinding.recommend7);
        Glide.with(tvBinding.recommend8)
                .load(presenter.getImage(7))
                .error(R.color.white)
                .fitCenter()
                .into(tvBinding.recommend8);
        Glide.with(tvBinding.recommend9)
                .load(presenter.getImage(8))
                .error(R.color.white)
                .fitCenter()
                .into(tvBinding.recommend9);
        Glide.with(tvBinding.recommend10)
                .load(presenter.getImage(9))
                .error(R.color.white)
                .fitCenter()
                .into(tvBinding.recommend10);
        Glide.with(tvBinding.recommend11)
                .load(presenter.getImage(10))
                .error(R.color.white)
                .fitCenter()
                .into(tvBinding.recommend11);
        Glide.with(tvBinding.recommend12)
                .load(presenter.getImage(11))
                .error(R.color.white)
                .fitCenter()
                .into(tvBinding.recommend12);
    }



    private void setClick() {
        tvBinding.recommend1.setOnClickListener(this);
        tvBinding.recommend2.setOnClickListener(this);
        tvBinding.recommend3.setOnClickListener(this);
        tvBinding.recommend4.setOnClickListener(this);
        tvBinding.search.setOnClickListener(this);
        tvBinding.more.setOnClickListener(this);
    }

    // 设置点击后的事件
    @Override
    public void onClick(View v) {
        if (v == tvBinding.recommend1) {
            presenter.turnVideoDetailPage(0);
        } else if (v == tvBinding.recommend2) {
            presenter.turnVideoDetailPage(1);
        } else if (v == tvBinding.recommend3) {
            presenter.turnVideoDetailPage(2);
        } else if (v == tvBinding.recommend4) {
            presenter.turnVideoDetailPage(3);
        } else if (v == tvBinding.recommend5) {
            presenter.turnVideoDetailPage(4);
        } else if (v == tvBinding.recommend6) {
            presenter.turnVideoDetailPage(5);
        } else if (v == tvBinding.recommend7) {
            presenter.turnVideoDetailPage(6);
        } else if (v == tvBinding.recommend8) {
            presenter.turnVideoDetailPage(7);
        } else if (v == tvBinding.recommend9) {
            presenter.turnVideoDetailPage(8);
        } else if (v == tvBinding.recommend10) {
            presenter.turnVideoDetailPage(9);
        } else if (v == tvBinding.recommend11) {
            presenter.turnVideoDetailPage(10);
        } else if (v == tvBinding.recommend12) {
            presenter.turnVideoDetailPage(11);
        } else if (v == tvBinding.recentWatch) {
            presenter.turnToRecentWatch();
        } else if (v == tvBinding.search) {
            presenter.turnToSearchPage();
        } else if (v == tvBinding.more) {
            presenter.turnToMovieCategoryPage();
        }
    }

    @Override
    public void update() {
        setImageResources();
    }


}
