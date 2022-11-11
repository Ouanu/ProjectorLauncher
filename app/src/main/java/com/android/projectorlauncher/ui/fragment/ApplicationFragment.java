package com.android.projectorlauncher.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ResolveInfo;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.android.projectorlauncher.R;
import com.android.projectorlauncher.databinding.FragmentApplicationBinding;
import com.android.projectorlauncher.databinding.ItemAppBinding;
import com.android.projectorlauncher.receiver.PackagesStatusReceiver;
import com.android.projectorlauncher.ui.design.ProjectorLayoutManager;
import com.android.projectorlauncher.utils.ApplicationsUtils;
import com.bumptech.glide.Glide;
import java.util.List;

public class ApplicationFragment extends Fragment implements PackagesStatusReceiver.PackagesCallback {
    private FragmentApplicationBinding applicationBinding;
    private PackagesStatusReceiver receiver;
    private List<ResolveInfo> resolveInfoList;
    private final ViewTreeObserver.OnGlobalFocusChangeListener focusChangeListener = (oldFocus, newFocus) -> {
        if (oldFocus instanceof LinearLayout && newFocus instanceof ImageView) {
            oldFocus.requestFocus();
        }
    };

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        receiver = new PackagesStatusReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_PACKAGE_ADDED);
        filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        filter.addAction(Intent.ACTION_PACKAGE_REPLACED);
        filter.addDataScheme("package");
        receiver.setCallback(this);
        context.registerReceiver(receiver, filter);
        resolveInfoList = ApplicationsUtils.getAllApplications(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        applicationBinding = FragmentApplicationBinding.inflate(inflater, container, false);
        ProjectorLayoutManager layoutManager = new ProjectorLayoutManager(getContext(), 4);
        applicationBinding.recyclerView.setLayoutManager(layoutManager);
        applicationBinding.recyclerView.setAdapter(new ApplicationAdapter());
        applicationBinding.recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = 10;
                outRect.bottom = 10;
                outRect.left = 20;
                outRect.right = 20;
            }
        });
        return applicationBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        applicationBinding.recyclerView.getViewTreeObserver().addOnGlobalFocusChangeListener(focusChangeListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        applicationBinding.recyclerView.getViewTreeObserver().removeOnGlobalFocusChangeListener(focusChangeListener);
    }

    @Override
    public void onDetach() {
        requireContext().unregisterReceiver(receiver);
        super.onDetach();
    }

    @Override
    public void statusChanged() {
        resolveInfoList = ApplicationsUtils.getAllApplications(requireContext());

    }

    class ApplicationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemAppBinding binding;
        int index;
        public ApplicationViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemAppBinding.bind(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnFocusChangeListener((v, hasFocus) -> {
                if (hasFocus) {
                    ViewCompat.animate(v)
                            .scaleX(1.05f)
                            .scaleY(1.05f)
                            .setDuration(250)
                            .start();
                } else {
                    ViewCompat.animate(v)
                            .scaleX(1f)
                            .scaleY(1f)
                            .setDuration(250)
                            .start();
                }
            });
        }

        public void bind(int position) {
            index = position;
            binding.label.setText(resolveInfoList.get(position).loadLabel(requireContext().getPackageManager()));
            itemView.setTag(position);
            Glide.with(binding.image)
                    .load(resolveInfoList.get(position).loadIcon(requireContext().getPackageManager()))
                    .error(R.color.white)
                    .centerCrop()
                    .into(binding.image);
        }

        @Override
        public void onClick(View v) {
            Intent intent = requireContext().getPackageManager().getLaunchIntentForPackage(resolveInfoList.get(index).activityInfo.packageName);
            if (intent != null) {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                requireContext().startActivity(intent);
            }
        }
    }

    class ApplicationAdapter extends RecyclerView.Adapter<ApplicationViewHolder> {
        ItemAppBinding binding;
        @NonNull
        @Override
        public ApplicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            binding = ItemAppBinding.inflate(getLayoutInflater(), parent, false);
            return new ApplicationViewHolder(binding.getRoot());
        }

        @Override
        public void onBindViewHolder(@NonNull ApplicationViewHolder holder, int position) {
            holder.bind(position);
        }

        @Override
        public int getItemCount() {
            return resolveInfoList.size();
        }
    }
}
