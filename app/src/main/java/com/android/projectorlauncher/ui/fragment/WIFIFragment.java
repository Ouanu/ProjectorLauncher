package com.android.projectorlauncher.ui.fragment;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.projectorlauncher.R;
import com.android.projectorlauncher.bean.Wifi;
import com.android.projectorlauncher.databinding.FragmentWifiBinding;
import com.android.projectorlauncher.databinding.ItemWifiBinding;
import com.android.projectorlauncher.presenter.WIFIPresenter;
import com.android.projectorlauncher.ui.dialog.WIFIDialog;
import com.android.projectorlauncher.ui.view.WIFIView;
import com.android.projectorlauncher.utils.WIFIUtils;

import java.util.ArrayList;
import java.util.List;

public class WIFIFragment extends Fragment implements WIFIView {
    private FragmentWifiBinding binding;
    List<Wifi> saveList = new ArrayList<>();
    List<Wifi> wifiList = new ArrayList<>();
    WIFIAdapter saveAdapter;
    WIFIAdapter wifiAdapter;
    private WIFIPresenter presenter;
    private static boolean isConnected = false;
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
//            Log.d("WIFI", "onReceive: ==================");
            if (intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
                Parcelable parcelable = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                NetworkInfo networkInfo = (NetworkInfo) parcelable;
                NetworkInfo.State state = networkInfo.getState();
                if (state == NetworkInfo.State.DISCONNECTED) {
                    isConnected = false;
                    binding.ipTitle.setText(R.string.not_connected_to_wifi_network);
                    binding.address.setText("");
                } else if (state == NetworkInfo.State.CONNECTING) {
                    isConnected = false;
                    binding.ipTitle.setText(R.string.connecting_to_the_network);
                    binding.address.setText(presenter.connectedName());
                } else if (state == NetworkInfo.State.CONNECTED) {
                    binding.ipTitle.setText(R.string.network_connected);
                    binding.address.setText(presenter.connectedName());
                    isConnected = true;
                }
                presenter.init();

            } else if (intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
                //此处是 WIFI模块 启用关闭状态的广播
                int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, -1);
                switch (wifiState) {
                    case WifiManager.WIFI_STATE_DISABLED:
                    case WifiManager.WIFI_STATE_UNKNOWN:
                        binding.progressBar.setVisibility(View.GONE);
                        binding.btnSwitch.setVisibility(View.VISIBLE);
                        binding.btnRefresh.requestFocus();
                        binding.btnSwitch.setBackgroundResource(R.drawable.btn_wifi_background_focus);
                        binding.btnSwitch.setEnabled(true);
                        binding.btnSwitch.setText(R.string.on);
                        break;
                    case WifiManager.WIFI_STATE_DISABLING:
                    case WifiManager.WIFI_STATE_ENABLING:
                        binding.progressBar.setVisibility(View.VISIBLE);
                        binding.btnSwitch.setVisibility(View.INVISIBLE);
                        binding.btnSwitch.setBackgroundResource(R.drawable.btn_wifi_background_stop_focus);
                        binding.btnSwitch.setEnabled(false);
                        break;
                    case WifiManager.WIFI_STATE_ENABLED:
                        binding.progressBar.setVisibility(View.GONE);
                        binding.btnSwitch.setVisibility(View.VISIBLE);
                        binding.btnRefresh.requestFocus();
                        binding.btnSwitch.setBackgroundResource(R.drawable.btn_wifi_background_enable_focus);
                        binding.btnSwitch.setEnabled(true);
                        binding.btnSwitch.setText(R.string.off);
                        break;

                }
                Log.d("WIFI", "onReceive: ==================" + wifiState);
            }
        }
    };
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        presenter = new WIFIPresenter(this, requireActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentWifiBinding.inflate(inflater, container, false);
        binding.saveRecycleView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.wifiRecycleView.setLayoutManager(new LinearLayoutManager(requireContext()));
        saveAdapter = new WIFIAdapter(saveList);
        wifiAdapter = new WIFIAdapter(wifiList);


        return binding.getRoot();
    }

    @Override
    public void updateSaveList(List<Wifi> list) {

    }

    @Override
    public void updateWIFIList(List<Wifi> list) {

    }

    @Override
    public void updateSwitchState(boolean state) {

    }

    class WIFIViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ItemWifiBinding binding;
        Wifi wifi;

        public WIFIViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemWifiBinding.bind(itemView);
            itemView.setOnFocusChangeListener(new WifiAnimation());
            itemView.setOnClickListener(this);
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        void bind(Wifi item) {
            wifi = item;
            if (isConnected && presenter.connectedName().equals(wifi.getSSID())) {
                binding.getRoot().setBackgroundResource(R.drawable.item_wifi_connected);
                binding.getRoot().setOnFocusChangeListener((v, hasFocus) -> {
                    if (hasFocus) {
                        ViewCompat.animate(v)
                                .setDuration(200)
                                .scaleX(1.10f)
                                .scaleY(1.10f)
                                .start();
                        binding.getRoot().setBackgroundResource(R.drawable.item_wifi_connected_focus);
                        binding.lock.setImageResource(R.drawable.wifi_key_focus);
                        binding.btnTurn.setImageResource(R.drawable.wifi_in_focus);
                    } else {
                        ViewCompat.animate(v)
                                .setDuration(200)
                                .scaleX(1f)
                                .scaleY(1f)
                                .start();
                        binding.getRoot().setBackgroundResource(R.drawable.item_wifi_connected);
                        binding.lock.setImageResource(R.drawable.wifi_key);
                        binding.btnTurn.setImageResource(R.drawable.wifi_in);
                    }
                });

//                binding.ivWifi.setColorFilter(Color.WHITE);
//                binding.wifiName.setTextColor(Color.WHITE);
            } else {
                binding.getRoot().setOnFocusChangeListener((v, hasFocus) -> {
                    if (hasFocus) {
                        ViewCompat.animate(v)
                                .setDuration(200)
                                .scaleX(1.10f)
                                .scaleY(1.10f)
                                .start();
                        binding.lock.setImageResource(R.drawable.wifi_key_focus);
                        binding.btnTurn.setImageResource(R.drawable.wifi_in_focus);
                    } else {
                        ViewCompat.animate(v)
                                .setDuration(200)
                                .scaleX(1f)
                                .scaleY(1f)
                                .start();
                        binding.lock.setImageResource(R.drawable.wifi_key);
                        binding.btnTurn.setImageResource(R.drawable.wifi_in);
                    }
                });
            }
            binding.wifiName.setText(item.getSSID());
//            binding.wifiCapabilities.setText(item.getCapabilities());
            if (item.getType() == 1) {
                binding.lock.setVisibility(View.INVISIBLE);
            }
            if (item.getLevel() <= -55) {
                binding.ivWifi.setImageResource(R.drawable.wifi_strong);
            } else if (item.getLevel() > -55 && item.getLevel() <= -66) {
                binding.ivWifi.setImageResource(R.drawable.wifi_middle);
            } else {
                binding.ivWifi.setImageResource(R.drawable.wifi_weak);
            }

        }

        @Override
        public void onClick(View v) {
//            audioUtil.play();
            if (wifi.getType() == 1) {
                WIFIUtils.getInstance().connectWifi(wifi.getSSID(), false, null, 1);
                presenter.init();
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putString("SSID", wifi.getSSID());
            bundle.putString("BSSID", wifi.getBSSID());
            bundle.putInt("type", wifi.getType());
            WIFIDialog dialog = new WIFIDialog();
            presenter.init();
            dialog.setArguments(bundle);
            dialog.show(getParentFragmentManager(), "wifi-tag");

        }
    }

    class WIFIAdapter extends RecyclerView.Adapter<WIFIViewHolder> {
        private final List<Wifi> list;

        public WIFIAdapter(List<Wifi> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public WIFIViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ItemWifiBinding binding = ItemWifiBinding.inflate(getLayoutInflater(), parent, false);
            return new WIFIViewHolder(binding.getRoot());
        }

        @Override
        public void onBindViewHolder(@NonNull WIFIViewHolder holder, int position) {
            holder.bind(list.get(position));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    private class WifiAnimation implements View.OnFocusChangeListener {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
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
        }
    }
}
