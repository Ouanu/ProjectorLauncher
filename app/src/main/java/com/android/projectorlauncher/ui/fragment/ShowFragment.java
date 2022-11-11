package com.android.projectorlauncher.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.projectorlauncher.R;
import com.android.projectorlauncher.bean.VideoCard;
import com.android.projectorlauncher.databinding.FragmentShowBinding;
import com.android.projectorlauncher.databinding.ItemShowBinding;
import com.android.projectorlauncher.presenter.ShowPresenter;
import com.android.projectorlauncher.ui.view.ShowView;

import java.util.ArrayList;
import java.util.List;

public class ShowFragment extends Fragment implements ShowView, View.OnClickListener {
    private FragmentShowBinding showBinding;
    private View selectView;
    private ShowPresenter presenter;
    private final MutableLiveData<List<VideoCard>> videoCards = new MutableLiveData<>();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        videoCards.setValue(new ArrayList<>());
        presenter = new ShowPresenter(requireActivity());
        presenter.setView(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        showBinding = FragmentShowBinding.inflate(inflater, container, false);
        LinearLayoutManager layout = new LinearLayoutManager(requireContext());
        layout.setOrientation(LinearLayoutManager.HORIZONTAL);
        showBinding.recyclerView.setLayoutManager(layout);
        showBinding.recyclerView.setAdapter(new ShowAdapter());
        showBinding.recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = 15;
                outRect.bottom = 10;
                outRect.left = 10;
                outRect.right = 10;
            }
        });

        setFocus();
        setClick();
        Log.d("ShowFragment", "onCreateView: ========");
        return showBinding.getRoot();
    }

    private void setClick() {
        showBinding.recommend1.setOnClickListener(this);
        showBinding.recommend2.setOnClickListener(this);
        showBinding.recommend3.setOnClickListener(this);
        showBinding.recommend4.setOnClickListener(this);
        showBinding.search.setOnClickListener(this);
        showBinding.category.setOnClickListener(this);
    }

    private void setFocus() {
        showBinding.recommend1.setOnFocusChangeListener(new ShowAnimation());
        showBinding.recommend2.setOnFocusChangeListener(new ShowAnimation());
        showBinding.recommend3.setOnFocusChangeListener(new ShowAnimation());
        showBinding.recommend4.setOnFocusChangeListener(new ShowAnimation());
        showBinding.category.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                ViewCompat.animate(v)
                        .scaleX(1.05f)
                        .scaleY(1.05f)
                        .setDuration(250)
                        .translationZ(1.2f)
                        .start();
                showBinding.category.setCardBackgroundColor(requireContext().getColor(R.color.self_2));
            } else {
                ViewCompat.animate(v)
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(250)
                        .translationZ(1f)
                        .start();
                showBinding.category.setCardBackgroundColor(requireContext().getColor(R.color.self_1));
            }
        });
        showBinding.search.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                ViewCompat.animate(v)
                        .scaleX(1.05f)
                        .scaleY(1.05f)
                        .setDuration(250)
                        .translationZ(1.2f)
                        .start();
                showBinding.search.setCardBackgroundColor(requireContext().getColor(R.color.self_2));
            } else {
                ViewCompat.animate(v)
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(250)
                        .translationZ(1f)
                        .start();
                showBinding.search.setCardBackgroundColor(requireContext().getColor(R.color.self_1));
            }
        });
    }

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
        } else if (v == showBinding.search) {
            presenter.turnToSearchPage();
        } else if (v == showBinding.category) {
            presenter.turnToVarietyCategoryPage();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (videoCards.getValue() == null || videoCards.getValue().size() == 0 || presenter.sizeOfCards() == 0) {
            presenter.init();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void update(VideoCard r1, VideoCard r2, VideoCard r3, VideoCard r4, List<VideoCard> cards) {
        if (r1 != null) showBinding.recommend1.setImageResource(r1.getImgSrc());
        if (r2 != null) showBinding.recommend2.setImageResource(r2.getImgSrc());
        if (r3 != null) showBinding.recommend3.setImageResource(r3.getImgSrc());
        if (r4 != null) showBinding.recommend4.setImageResource(r4.getImgSrc());
        if (cards != null) {
            videoCards.setValue(cards);
            if (showBinding.recyclerView.getAdapter() == null) return;
            showBinding.recyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    class ShowViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemShowBinding binding;
        int index;
        public ShowViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemShowBinding.bind(itemView);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(showBinding.recyclerView.getWidth() / 5, (int) (showBinding.recyclerView.getHeight() * 0.95));
            binding.getRoot().setLayoutParams(layoutParams);
            itemView.setOnClickListener(this);
            itemView.setOnFocusChangeListener((v, hasFocus) -> {
                if (hasFocus) {
                    ViewCompat.animate(v)
                            .scaleX(1.05f)
                            .scaleY(1.05f)
                            .setDuration(250)
                            .translationZ(1.2f)
                            .start();
                    selectView = itemView;
                } else {
                    ViewCompat.animate(v)
                            .scaleX(1f)
                            .scaleY(1f)
                            .setDuration(250)
                            .translationZ(1f)
                            .start();
                }
            });
        }

        public void bind(int index) {
            this.index = index;
            if (videoCards.getValue() == null) return;
            binding.image.setImageResource(presenter.getRecommendsImage(index));
        }

        @Override
        public void onClick(View v) {
            presenter.fromRecommendsToVideoDetailPage(index);
        }
    }

    static class ShowAnimation implements View.OnFocusChangeListener {

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

    class ShowAdapter extends RecyclerView.Adapter<ShowViewHolder> {

        @NonNull
        @Override
        public ShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ItemShowBinding binding = ItemShowBinding.inflate(getLayoutInflater(), parent, false);
            return new ShowViewHolder(binding.getRoot());
        }

        @Override
        public void onBindViewHolder(@NonNull ShowViewHolder holder, int position) {
            if (position == 0) {
                if(selectView == null){
                    selectView = holder.itemView;
                    selectView.requestFocus();
                }
                holder.itemView.setNextFocusLeftId(holder.itemView.getId());
            }
            if (videoCards.getValue() != null && position == videoCards.getValue().size()-1) {
                holder.itemView.setNextFocusRightId(holder.itemView.getId());
            }
            holder.bind(position);
        }

        @Override
        public int getItemCount() {
            if (videoCards.getValue() != null)
                return videoCards.getValue().size();
            return 0;
        }
    }
}
