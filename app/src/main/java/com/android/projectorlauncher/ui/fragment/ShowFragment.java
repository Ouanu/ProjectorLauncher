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
import com.android.projectorlauncher.databinding.FragmentUnityShowBinding;
import com.android.projectorlauncher.presenter.ShowPresenter;
import com.android.projectorlauncher.ui.view.MovieView;
import com.bumptech.glide.Glide;

public class ShowFragment extends Fragment implements View.OnClickListener, MovieView {
    private FragmentUnityShowBinding showBinding;
    private ShowPresenter presenter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        presenter = new ShowPresenter(requireActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        showBinding = FragmentUnityShowBinding.inflate(inflater, container, false);
        setImageResources();
        setClick();
        return showBinding.getRoot();
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
        Glide.with(showBinding.recommend1)
                .load(presenter.getImage(0))
                .error(R.color.white)
                .fitCenter()
                .into(showBinding.recommend1);
        Glide.with(showBinding.recommend2)
                .load(presenter.getImage(1))
                .error(R.color.white)
                .fitCenter()
                .into(showBinding.recommend2);
        Glide.with(showBinding.recommend3)
                .load(presenter.getImage(2))
                .error(R.color.white)
                .fitCenter()
                .into(showBinding.recommend3);
        Glide.with(showBinding.recommend4)
                .load(presenter.getImage(3))
                .error(R.color.white)
                .fitCenter()
                .into(showBinding.recommend4);
        Glide.with(showBinding.recommend5)
                .load(presenter.getImage(4))
                .error(R.color.white)
                .fitCenter()
                .into(showBinding.recommend5);
        Glide.with(showBinding.recommend6)
                .load(presenter.getImage(5))
                .error(R.color.white)
                .fitCenter()
                .into(showBinding.recommend6);
        Glide.with(showBinding.recommend7)
                .load(presenter.getImage(6))
                .error(R.color.white)
                .fitCenter()
                .into(showBinding.recommend7);
        Glide.with(showBinding.recommend8)
                .load(presenter.getImage(7))
                .error(R.color.white)
                .fitCenter()
                .into(showBinding.recommend8);
        Glide.with(showBinding.recommend9)
                .load(presenter.getImage(8))
                .error(R.color.white)
                .fitCenter()
                .into(showBinding.recommend9);
        Glide.with(showBinding.recommend10)
                .load(presenter.getImage(9))
                .error(R.color.white)
                .fitCenter()
                .into(showBinding.recommend10);
        Glide.with(showBinding.recommend11)
                .load(presenter.getImage(10))
                .error(R.color.white)
                .fitCenter()
                .into(showBinding.recommend11);
        Glide.with(showBinding.recommend12)
                .load(presenter.getImage(11))
                .error(R.color.white)
                .fitCenter()
                .into(showBinding.recommend12);
    }



    private void setClick() {
        showBinding.recommend1.setOnClickListener(this);
        showBinding.recommend2.setOnClickListener(this);
        showBinding.recommend3.setOnClickListener(this);
        showBinding.recommend4.setOnClickListener(this);
        showBinding.search.setOnClickListener(this);
        showBinding.more.setOnClickListener(this);
    }

    // 设置点击后的事件
    @Override
    public void onClick(View v) {
        if (v == showBinding.recommend1) {
            presenter.turnVideoDetailPage(0);
        } else if (v == showBinding.recommend2) {
            presenter.turnVideoDetailPage(1);
        } else if (v == showBinding.recommend3) {
            presenter.turnVideoDetailPage(2);
        } else if (v == showBinding.recommend4) {
            presenter.turnVideoDetailPage(3);
        } else if (v == showBinding.recommend5) {
            presenter.turnVideoDetailPage(4);
        } else if (v == showBinding.recommend6) {
            presenter.turnVideoDetailPage(5);
        } else if (v == showBinding.recommend7) {
            presenter.turnVideoDetailPage(6);
        } else if (v == showBinding.recommend8) {
            presenter.turnVideoDetailPage(7);
        } else if (v == showBinding.recommend9) {
            presenter.turnVideoDetailPage(8);
        } else if (v == showBinding.recommend10) {
            presenter.turnVideoDetailPage(9);
        } else if (v == showBinding.recommend11) {
            presenter.turnVideoDetailPage(10);
        } else if (v == showBinding.recommend12) {
            presenter.turnVideoDetailPage(11);
        } else if (v == showBinding.recentWatch) {
            presenter.turnToRecentWatch();
        } else if (v == showBinding.search) {
            presenter.turnToSearchPage();
        } else if (v == showBinding.more) {
            presenter.turnToShowCategoryPage();
        }
    }

    @Override
    public void update() {
        setImageResources();
    }


}
