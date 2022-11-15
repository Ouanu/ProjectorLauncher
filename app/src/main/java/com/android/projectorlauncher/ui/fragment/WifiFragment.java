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
import com.android.projectorlauncher.ui.view.WifiView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WifiFragment extends Fragment implements View.OnClickListener, WifiView {
    private FragmentWifiBinding wifiBinding;
    private WifiPresenter presenter;
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                Log.d("WifiFragment", "onReceive: 刷新数据");
                presenter.updateNetworks();
            }
        }
    };
    private final MutableLiveData<List<ScanResult>> nearbyList = new MutableLiveData<>();
    private final MutableLiveData<List<ScanResult>> saveList = new MutableLiveData<>();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        presenter = new WifiPresenter(this);
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
        wifiBinding.saveRecycleView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        wifiBinding.wifiRecycleView.setAdapter(new WifiAdapter(nearbyList));
        wifiBinding.saveRecycleView.setAdapter(new WifiAdapter(saveList));
        return wifiBinding.getRoot();
    }

    private void onFocus() {
        wifiBinding.btnSwitch.setOnFocusChangeListener(new WifiAnimation());
        wifiBinding.btnRefresh.setOnFocusChangeListener(new WifiAnimation());
        wifiBinding.btnAddNetwork.setOnFocusChangeListener(new WifiAnimation());
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
        requireActivity().registerReceiver(receiver, filter);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter.isWifiEnabled()) {
            wifiBinding.btnSwitch.setBackgroundResource(R.drawable.btn_wifi_background_enable_focus);
            wifiBinding.btnSwitch.setText("停用");
        } else {
            wifiBinding.btnSwitch.setBackgroundResource(R.drawable.btn_wifi_background_focus);
            wifiBinding.btnSwitch.setText("启用");
        }
    }

    @Override
    public void onClick(View v) {
        if (v == wifiBinding.btnRefresh) {
            presenter.refreshNearbyNetworks();
        } else if (v == wifiBinding.btnSwitch) {
            presenter.changeWifiState();
        } else if (v == wifiBinding.btnAddNetwork) {

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void update() {
        nearbyList.setValue(presenter.getNearbyResults());
        saveList.setValue(presenter.getSaveResults());
        if (saveList.getValue() != null && saveList.getValue().size() == 0) {
            wifiBinding.saveRecycleView.setVisibility(View.GONE);
            wifiBinding.connectedWifiList.setVisibility(View.GONE);
        } else {
            wifiBinding.saveRecycleView.setVisibility(View.VISIBLE);
            wifiBinding.connectedWifiList.setVisibility(View.VISIBLE);
        }
        Log.d("WifiFragment", "update: nearbyList=" + Objects.requireNonNull(nearbyList.getValue()).size() + "  saveList=" + Objects.requireNonNull(saveList.getValue()).size());
        if (wifiBinding.wifiRecycleView.getAdapter() != null) {
            wifiBinding.wifiRecycleView.getAdapter().notifyDataSetChanged();
        }
        if (wifiBinding.saveRecycleView.getAdapter() != null) {
            wifiBinding.saveRecycleView.getAdapter().notifyDataSetChanged();
        }
    }

    static class WifiViewHolder extends RecyclerView.ViewHolder {
        ItemWifiBinding binding;

        public WifiViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemWifiBinding.bind(itemView);
        }
        public void bind(ScanResult result) {
            binding.wifiName.setText(result.SSID);
            if (result.capabilities.contains("WPA-PSK") || result.capabilities.contains("WPA2-PSK") || result.capabilities.contains("WEP")) {
                binding.lock.setVisibility(View.VISIBLE);
            } else {
                binding.lock.setVisibility(View.INVISIBLE);
            }
            Log.d("WifiFragment", "bind: " + result.level);
        }
    }

    class WifiAdapter extends RecyclerView.Adapter<WifiViewHolder> {

        private MutableLiveData<List<ScanResult>> results;

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
            holder.bind(results.getValue().get(position));
        }

        @Override
        public int getItemCount() {
            return results.getValue().size();
        }
    }

    static class WifiAnimation implements View.OnFocusChangeListener {

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
