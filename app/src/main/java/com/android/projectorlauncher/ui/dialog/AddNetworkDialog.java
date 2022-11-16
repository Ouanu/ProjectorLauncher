package com.android.projectorlauncher.ui.dialog;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.DialogFragment;

import com.android.projectorlauncher.R;
import com.android.projectorlauncher.databinding.DialogAddNetworkBinding;
import com.android.projectorlauncher.utils.WifiManagerUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 添加隐藏WIFI网络弹窗
 */
public class AddNetworkDialog extends DialogFragment {
    // 加密类型
    private final List<String> array = Arrays.asList("NO", "WEP", "WPA/WAP2-Personal");

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Dialog_NoActionBar);
        Settings.System.putInt(requireContext().getContentResolver(), Settings.System.SOUND_EFFECTS_ENABLED, 1);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).setBackgroundDrawableResource(R.color.none);
        DialogAddNetworkBinding binding = DialogAddNetworkBinding.inflate(inflater, container, false);
        binding.spinner.setAdapter(new ArrayAdapter<>(getContext(), R.layout.item_option, array));
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        binding.passwd.setVisibility(View.GONE);
                        binding.passwdName.setVisibility(View.GONE);
                        break;
                    case 1:
                    case 2:
                        binding.passwd.setVisibility(View.VISIBLE);
                        binding.passwdName.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                binding.passwd.setVisibility(View.GONE);
            }
        });

        binding.connect.setOnClickListener(v -> {
            String name = binding.ssid.getText().toString();
            String passwd = binding.passwd.getText().toString();
            WifiConfiguration wifiInfo = WifiManagerUtils.createWifiInfo(
                    name,
                    passwd,
                    true,
                    array.get(binding.spinner.getSelectedItemPosition()));
            WifiManagerUtils.connectWifi((WifiManager) requireContext().getSystemService(Context.WIFI_SERVICE), wifiInfo);

            dismiss();
        });

        binding.ssid.setOnFocusChangeListener(new ImageButtonAnimation());
        binding.passwd.setOnFocusChangeListener(new ImageButtonAnimation());
        binding.spinner.setOnFocusChangeListener(new ImageButtonAnimation());
        binding.connect.setOnFocusChangeListener(new ImageButtonAnimation());
        return binding.getRoot();
    }

    private static class ImageButtonAnimation implements View.OnFocusChangeListener {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                ViewCompat.animate(v)
                        .scaleX(1.1f)
                        .scaleY(1.1f)
                        .setDuration(250)
                        .start();
            } else {
                ViewCompat.animate(v)
                        .scaleX(1.0f)
                        .scaleY(1.0f)
                        .setDuration(250)
                        .start();
            }
        }
    }
}
