package com.android.projectorlauncher.ui.fragment;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
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
import com.android.projectorlauncher.databinding.FragmentWifiBinding;
import com.android.projectorlauncher.databinding.ItemWifiBinding;
import com.android.projectorlauncher.presenter.WifiPresenter;
import com.android.projectorlauncher.ui.dialog.AddNetworkDialog;
import com.android.projectorlauncher.ui.dialog.WIFIDialog;
import com.android.projectorlauncher.ui.view.WifiView;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WifiFragment extends Fragment implements View.OnClickListener, WifiView {
    private FragmentWifiBinding wifiBinding;
    private WifiPresenter presenter;
    private final MutableLiveData<List<ScanResult>> nearbyList = new MutableLiveData<>();
    private final MutableLiveData<List<ScanResult>> saveList = new MutableLiveData<>();
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                Log.d("WifiFragment", "onReceive: 刷新数据");
                presenter.updateNetworks();
            } else if (intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
                int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
                switch (wifiState) {
                    case WifiManager.WIFI_STATE_DISABLED:
                        Log.d("WifiFragment", "onReceive: wifi 关闭");
                        wifiBinding.progressCircular.setVisibility(View.INVISIBLE);
                        wifiBinding.btnSwitch.setVisibility(View.VISIBLE);
                        presenter.refreshNearbyNetworks();
                        presenter.setWifiConnectState(false);
                        break;
                    case WifiManager.WIFI_STATE_DISABLING:
                    case WifiManager.WIFI_STATE_ENABLING:
                        wifiBinding.progressCircular.setVisibility(View.VISIBLE);
                        wifiBinding.btnSwitch.setVisibility(View.INVISIBLE);
                        break;
                    case WifiManager.WIFI_STATE_ENABLED:
                        Log.d("WifiFragment", "onReceive: wifi 打开");
                        wifiBinding.progressCircular.setVisibility(View.INVISIBLE);
                        wifiBinding.btnSwitch.setVisibility(View.VISIBLE);
                        presenter.refreshNearbyNetworks();
                        break;
                    case WifiManager.WIFI_STATE_UNKNOWN:
                        wifiBinding.progressCircular.setVisibility(View.INVISIBLE);
                        wifiBinding.btnSwitch.setVisibility(View.VISIBLE);
                        break;
                }
                wifiStateChange();
            }
        }
    };

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        nearbyList.setValue(new ArrayList<>());
        saveList.setValue(new ArrayList<>());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        wifiBinding = FragmentWifiBinding.inflate(inflater, container, false);
        onClick();
        onFocus();
        wifiBinding.wifiRecycleView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        wifiBinding.wifiRecycleView.setAdapter(new WifiAdapter(nearbyList));
        return wifiBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new WifiPresenter(this);
    }

    private void onFocus() {
        wifiBinding.btnSwitch.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                ViewCompat.animate(v)
                        .scaleX(1.05f)
                        .scaleY(1.05f)
                        .setDuration(250)
                        .start();
                wifiBinding.btnSwitch.setTextColor(requireContext().getColor(R.color.self_4));
            } else {
                ViewCompat.animate(v)
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(250)
                        .start();
                wifiBinding.btnSwitch.setTextColor(requireContext().getColor(R.color.white));
            }
        });
        wifiBinding.btnRefresh.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                ViewCompat.animate(v)
                        .scaleX(1.05f)
                        .scaleY(1.05f)
                        .setDuration(250)
                        .start();
                wifiBinding.btnRefresh.setTextColor(requireContext().getColor(R.color.self_4));
            } else {
                ViewCompat.animate(v)
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(250)
                        .start();
                wifiBinding.btnRefresh.setTextColor(requireContext().getColor(R.color.white));
            }
        });
        wifiBinding.btnAddNetwork.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                ViewCompat.animate(v)
                        .scaleX(1.05f)
                        .scaleY(1.05f)
                        .setDuration(250)
                        .start();
                wifiBinding.btnAddNetwork.setTextColor(requireContext().getColor(R.color.self_4));
            } else {
                ViewCompat.animate(v)
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(250)
                        .start();
                wifiBinding.btnAddNetwork.setTextColor(requireContext().getColor(R.color.white));
            }
        });
    }

    private void onClick() {
        wifiBinding.btnRefresh.setOnClickListener(this);
        wifiBinding.btnAddNetwork.setOnClickListener(this);
        wifiBinding.btnSwitch.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        requireActivity().registerReceiver(receiver, filter);
    }

    @Override
    public void onResume() {
        super.onResume();
        wifiStateChange();
        presenter.refreshNearbyNetworks();
    }

    @Override
    public void onClick(View v) {
        if (v == wifiBinding.btnRefresh) {
            presenter.refreshNearbyNetworks();
        } else if (v == wifiBinding.btnSwitch) {
            presenter.changeWifiState();
            wifiBinding.btnRefresh.requestFocus();
        } else if (v == wifiBinding.btnAddNetwork) {
            AddNetworkDialog dialog = new AddNetworkDialog();
            Bundle bundle = new Bundle();
            dialog.setArguments(bundle);
            dialog.show(getParentFragmentManager(), "add-network-tag");
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void update() {
        nearbyList.setValue(presenter.getNearbyResults());
        saveList.setValue(presenter.getSaveResults());
        Log.d("WifiFragment", "update: nearbyList=" + Objects.requireNonNull(nearbyList.getValue()).size() + "  saveList=" + Objects.requireNonNull(saveList.getValue()).size());
        if (wifiBinding.wifiRecycleView.getAdapter() != null) {
            wifiBinding.wifiRecycleView.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public void wifiStateChange() {
        if (presenter.isWifiEnabled()) {
            wifiBinding.btnSwitch.setBackgroundResource(R.drawable.btn_wifi_background_enable_focus);
            wifiBinding.btnSwitch.setText("停用");
        } else {
            wifiBinding.btnSwitch.setBackgroundResource(R.drawable.btn_wifi_background_focus);
            wifiBinding.btnSwitch.setText("启用");
        }
    }

    @Override
    public void wifiConnectState(boolean isWifiConnected) {
        if (wifiBinding == null) {
            return;
        }
        if (!isWifiConnected) {
            wifiBinding.ipTitle.setText(R.string.not_connected_to_wifi_network);
            wifiBinding.address.setText("");
        } else {
            wifiBinding.ipTitle.setText(R.string.network_connected);
            wifiBinding.address.setText(presenter.getConnectSSID());
        }
    }

    class WifiViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnFocusChangeListener {
        ItemWifiBinding binding;
        ScanResult result;

        public WifiViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemWifiBinding.bind(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnFocusChangeListener(this);
        }

        public void bind(ScanResult result) {
            this.result = result;
            binding.wifiName.setText(result.SSID);
            if (result.capabilities.contains("WPA-PSK") || result.capabilities.contains("WPA2-PSK") || result.capabilities.contains("WEP")) {
                binding.lock.setVisibility(View.VISIBLE);
            } else {
                binding.lock.setVisibility(View.INVISIBLE);
            }
            if (result.level >= -55) {
                binding.ivWifi.setImageResource(R.drawable.wifi_strong);
            } else if (result.level >= -77) {
                binding.ivWifi.setImageResource(R.drawable.wifi_middle);
            } else {
                binding.ivWifi.setImageResource(R.drawable.wifi_weak);
            }
            Log.d("WifiFragment", "bind: " + result.level);
        }

        @Override
        public void onClick(View v) {
            if (result.capabilities.contains("WPA-PSK") || result.capabilities.contains("WPA2-PSK") || result.capabilities.contains("WEP")) {
                Bundle bundle = new Bundle();
                bundle.putString("SSID", result.SSID);
                bundle.putString("capabilities", result.capabilities);
                WIFIDialog dialog = new WIFIDialog();
                dialog.setArguments(bundle);
                dialog.show(getParentFragmentManager(), "wifi-tag");
            } else {
                binding.lock.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                ViewCompat.animate(v)
                        .scaleX(1.05f)
                        .scaleY(1.05f)
                        .setDuration(250)
                        .start();
                binding.btnTurn.setImageResource(R.drawable.wifi_in_focus);
            } else {
                ViewCompat.animate(v)
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(250)
                        .start();
                binding.btnTurn.setImageResource(R.drawable.wifi_in);
            }
        }
    }

    class WifiAdapter extends RecyclerView.Adapter<WifiViewHolder> {

        private final MutableLiveData<List<ScanResult>> results;

        public WifiAdapter(MutableLiveData<List<ScanResult>> results) {
            this.results = results;
        }

        @NonNull
        @Override
        public WifiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ItemWifiBinding binding = ItemWifiBinding.inflate(getLayoutInflater(), parent, false);
            return new WifiViewHolder(binding.getRoot());
        }

        @Override
        public void onBindViewHolder(@NonNull WifiViewHolder holder, int position) {
            if (results.getValue() == null) {
                return;
            }
            holder.bind(results.getValue().get(position));
        }

        @Override
        public int getItemCount() {
            if (results.getValue() == null) return 0;
            return results.getValue().size();
        }
    }

}
