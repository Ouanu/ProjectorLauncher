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
import com.android.projectorlauncher.databinding.FragmentUnityComicsBinding;
import com.android.projectorlauncher.presenter.ComicsPresenter;
import com.android.projectorlauncher.ui.view.MovieView;
import com.bumptech.glide.Glide;

public class ComicsFragment extends Fragment implements View.OnClickListener, MovieView {
    private FragmentUnityComicsBinding comicsBinding;
    private ComicsPresenter presenter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        presenter = new ComicsPresenter(requireActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        comicsBinding = FragmentUnityComicsBinding.inflate(inflater, container, false);
        setImageResources();
        setClick();
        return comicsBinding.getRoot();
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
        Glide.with(comicsBinding.recommend1)
                .load(presenter.getImage(0))
                .error(R.color.white)
                .fitCenter()
                .into(comicsBinding.recommend1);
        Glide.with(comicsBinding.recommend2)
                .load(presenter.getImage(1))
                .error(R.color.white)
                .fitCenter()
                .into(comicsBinding.recommend2);
        Glide.with(comicsBinding.recommend3)
                .load(presenter.getImage(2))
                .error(R.color.white)
                .fitCenter()
                .into(comicsBinding.recommend3);
        Glide.with(comicsBinding.recommend4)
                .load(presenter.getImage(3))
                .error(R.color.white)
                .fitCenter()
                .into(comicsBinding.recommend4);
        Glide.with(comicsBinding.recommend5)
                .load(presenter.getImage(4))
                .error(R.color.white)
                .fitCenter()
                .into(comicsBinding.recommend5);
        Glide.with(comicsBinding.recommend6)
                .load(presenter.getImage(5))
                .error(R.color.white)
                .fitCenter()
                .into(comicsBinding.recommend6);
        Glide.with(comicsBinding.recommend7)
                .load(presenter.getImage(6))
                .error(R.color.white)
                .fitCenter()
                .into(comicsBinding.recommend7);
        Glide.with(comicsBinding.recommend8)
                .load(presenter.getImage(7))
                .error(R.color.white)
                .fitCenter()
                .into(comicsBinding.recommend8);
        Glide.with(comicsBinding.recommend9)
                .load(presenter.getImage(8))
                .error(R.color.white)
                .fitCenter()
                .into(comicsBinding.recommend9);
        Glide.with(comicsBinding.recommend10)
                .load(presenter.getImage(9))
                .error(R.color.white)
                .fitCenter()
                .into(comicsBinding.recommend10);
        Glide.with(comicsBinding.recommend11)
                .load(presenter.getImage(10))
                .error(R.color.white)
                .fitCenter()
                .into(comicsBinding.recommend11);
        Glide.with(comicsBinding.recommend12)
                .load(presenter.getImage(11))
                .error(R.color.white)
                .fitCenter()
                .into(comicsBinding.recommend12);
    }



    private void setClick() {
        comicsBinding.recommend1.setOnClickListener(this);
        comicsBinding.recommend2.setOnClickListener(this);
        comicsBinding.recommend3.setOnClickListener(this);
        comicsBinding.recommend4.setOnClickListener(this);
        comicsBinding.search.setOnClickListener(this);
        comicsBinding.more.setOnClickListener(this);
    }

    // 设置点击后的事件
    @Override
    public void onClick(View v) {
        if (v == comicsBinding.recommend1) {
            presenter.turnVideoDetailPage(0);
        } else if (v == comicsBinding.recommend2) {
            presenter.turnVideoDetailPage(1);
        } else if (v == comicsBinding.recommend3) {
            presenter.turnVideoDetailPage(2);
        } else if (v == comicsBinding.recommend4) {
            presenter.turnVideoDetailPage(3);
        } else if (v == comicsBinding.recommend5) {
            presenter.turnVideoDetailPage(4);
        } else if (v == comicsBinding.recommend6) {
            presenter.turnVideoDetailPage(5);
        } else if (v == comicsBinding.recommend7) {
            presenter.turnVideoDetailPage(6);
        } else if (v == comicsBinding.recommend8) {
            presenter.turnVideoDetailPage(7);
        } else if (v == comicsBinding.recommend9) {
            presenter.turnVideoDetailPage(8);
        } else if (v == comicsBinding.recommend10) {
            presenter.turnVideoDetailPage(9);
        } else if (v == comicsBinding.recommend11) {
            presenter.turnVideoDetailPage(10);
        } else if (v == comicsBinding.recommend12) {
            presenter.turnVideoDetailPage(11);
        } else if (v == comicsBinding.recentWatch) {
            presenter.turnToRecentWatch();
        } else if (v == comicsBinding.search) {
            presenter.turnToSearchPage();
        } else if (v == comicsBinding.more) {
            presenter.turnToMovieCategoryPage();
        }
    }

    @Override
    public void update() {
        setImageResources();
    }


}
