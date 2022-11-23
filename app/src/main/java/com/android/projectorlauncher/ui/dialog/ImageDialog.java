package com.android.projectorlauncher.ui.dialog;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.android.projectorlauncher.R;
import com.android.projectorlauncher.databinding.DialogImageBinding;

public class ImageDialog extends DialogFragment {
    private String src = null;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, android.R.style.Theme_Light);
        if (getArguments() != null) {
            src = getArguments().getString("PATH");
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DialogImageBinding imageBinding = DialogImageBinding.inflate(inflater, container, false);
        if (src != null) {
            imageBinding.image.setImageURI(Uri.parse(src));
        } else {
            imageBinding.image.setImageResource(R.drawable.error_cover_can_t_found);
        }
        return imageBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        requireDialog().setOnKeyListener((dialog, keyCode, event) -> {
            dismiss();
            return true;
        });
    }
}
