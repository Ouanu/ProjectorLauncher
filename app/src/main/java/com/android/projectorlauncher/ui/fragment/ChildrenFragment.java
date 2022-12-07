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
import com.android.projectorlauncher.databinding.FragmentUnityChildrenBinding;
import com.android.projectorlauncher.presenter.ChildrenPresenter;
import com.android.projectorlauncher.ui.view.MovieView;
import com.bumptech.glide.Glide;

public class ChildrenFragment extends Fragment implements View.OnClickListener, MovieView {
    private FragmentUnityChildrenBinding childrenBinding;
    private ChildrenPresenter presenter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        presenter = new ChildrenPresenter(requireActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        childrenBinding = FragmentUnityChildrenBinding.inflate(inflater, container, false);
        setImageResources();
        setClick();
        return childrenBinding.getRoot();
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
        Glide.with(childrenBinding.recommend1)
                .load(presenter.getImage(0))
                .error(R.color.white)
                .fitCenter()
                .into(childrenBinding.recommend1);
        Glide.with(childrenBinding.recommend2)
                .load(presenter.getImage(1))
                .error(R.color.white)
                .fitCenter()
                .into(childrenBinding.recommend2);
        Glide.with(childrenBinding.recommend3)
                .load(presenter.getImage(2))
                .error(R.color.white)
                .fitCenter()
                .into(childrenBinding.recommend3);
        Glide.with(childrenBinding.recommend4)
                .load(presenter.getImage(3))
                .error(R.color.white)
                .fitCenter()
                .into(childrenBinding.recommend4);
        Glide.with(childrenBinding.recommend5)
                .load(presenter.getImage(4))
                .error(R.color.white)
                .fitCenter()
                .into(childrenBinding.recommend5);
        Glide.with(childrenBinding.recommend6)
                .load(presenter.getImage(5))
                .error(R.color.white)
                .fitCenter()
                .into(childrenBinding.recommend6);
        Glide.with(childrenBinding.recommend7)
                .load(presenter.getImage(6))
                .error(R.color.white)
                .fitCenter()
                .into(childrenBinding.recommend7);
        Glide.with(childrenBinding.recommend8)
                .load(presenter.getImage(7))
                .error(R.color.white)
                .fitCenter()
                .into(childrenBinding.recommend8);
        Glide.with(childrenBinding.recommend9)
                .load(presenter.getImage(8))
                .error(R.color.white)
                .fitCenter()
                .into(childrenBinding.recommend9);
        Glide.with(childrenBinding.recommend10)
                .load(presenter.getImage(9))
                .error(R.color.white)
                .fitCenter()
                .into(childrenBinding.recommend10);
        Glide.with(childrenBinding.recommend11)
                .load(presenter.getImage(10))
                .error(R.color.white)
                .fitCenter()
                .into(childrenBinding.recommend11);
        Glide.with(childrenBinding.recommend12)
                .load(presenter.getImage(11))
                .error(R.color.white)
                .fitCenter()
                .into(childrenBinding.recommend12);
    }



    private void setClick() {
        childrenBinding.recommend1.setOnClickListener(this);
        childrenBinding.recommend2.setOnClickListener(this);
        childrenBinding.recommend3.setOnClickListener(this);
        childrenBinding.recommend4.setOnClickListener(this);
        childrenBinding.search.setOnClickListener(this);
        childrenBinding.more.setOnClickListener(this);
    }

    // 设置点击后的事件
    @Override
    public void onClick(View v) {
        if (v == childrenBinding.recommend1) {
            presenter.turnVideoDetailPage(0);
        } else if (v == childrenBinding.recommend2) {
            presenter.turnVideoDetailPage(1);
        } else if (v == childrenBinding.recommend3) {
            presenter.turnVideoDetailPage(2);
        } else if (v == childrenBinding.recommend4) {
            presenter.turnVideoDetailPage(3);
        } else if (v == childrenBinding.recommend5) {
            presenter.turnVideoDetailPage(4);
        } else if (v == childrenBinding.recommend6) {
            presenter.turnVideoDetailPage(5);
        } else if (v == childrenBinding.recommend7) {
            presenter.turnVideoDetailPage(6);
        } else if (v == childrenBinding.recommend8) {
            presenter.turnVideoDetailPage(7);
        } else if (v == childrenBinding.recommend9) {
            presenter.turnVideoDetailPage(8);
        } else if (v == childrenBinding.recommend10) {
            presenter.turnVideoDetailPage(9);
        } else if (v == childrenBinding.recommend11) {
            presenter.turnVideoDetailPage(10);
        } else if (v == childrenBinding.recommend12) {
            presenter.turnVideoDetailPage(11);
        } else if (v == childrenBinding.recentWatch) {
            presenter.turnToRecentWatch();
        } else if (v == childrenBinding.search) {
            presenter.turnToSearchPage();
        } else if (v == childrenBinding.more) {
            presenter.turnToMovieCategoryPage();
        }
    }

    @Override
    public void update() {
        setImageResources();
    }


}
