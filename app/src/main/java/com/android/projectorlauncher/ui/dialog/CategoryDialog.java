package com.android.projectorlauncher.ui.dialog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.android.projectorlauncher.R;
import com.android.projectorlauncher.databinding.DialogCategoryBinding;
import com.android.projectorlauncher.ui.activity.ResourceActivity;

import java.util.Objects;

public class CategoryDialog extends DialogFragment implements View.OnClickListener {
    private DialogCategoryBinding categoryBinding;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, androidx.preference.R.style.Base_Theme_AppCompat_Dialog_FixedSize);
        Settings.System.putInt(requireContext().getContentResolver(), Settings.System.SOUND_EFFECTS_ENABLED, 1);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).setBackgroundDrawableResource(R.color.none);
        categoryBinding = DialogCategoryBinding.inflate(inflater, container, false);

        onClick();
        onFocus();

        return categoryBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        categoryBinding.video.requestFocus();
    }

    private void onClick() {
        categoryBinding.video.setOnClickListener(this);
        categoryBinding.audio.setOnClickListener(this);
        categoryBinding.image.setOnClickListener(this);
    }

    private void onFocus() {
        categoryBinding.video.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                categoryBinding.video.setTextColor(requireContext().getColor(R.color.background_color));
            } else {
                categoryBinding.video.setTextColor(requireContext().getColor(R.color.self_4));
            }
        });

        categoryBinding.audio.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                categoryBinding.audio.setTextColor(requireContext().getColor(R.color.background_color));
            } else {
                categoryBinding.audio.setTextColor(requireContext().getColor(R.color.self_4));
            }
        });

        categoryBinding.image.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                categoryBinding.image.setTextColor(requireContext().getColor(R.color.background_color));
            } else {
                categoryBinding.image.setTextColor(requireContext().getColor(R.color.self_4));
            }
        });
    }


    @Override
    public void onClick(View v) {
        if (v == categoryBinding.video) {
            Intent intent = new Intent(requireActivity(), ResourceActivity.class);
            intent.putExtra("TYPE", 0);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else if (v == categoryBinding.audio) {
            Intent intent = new Intent(requireActivity(), ResourceActivity.class);
            intent.putExtra("TYPE", 1);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else if (v == categoryBinding.image) {
            Intent intent = new Intent(requireActivity(), ResourceActivity.class);
            intent.putExtra("TYPE", 2);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
