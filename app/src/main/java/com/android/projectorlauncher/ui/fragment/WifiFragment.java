package com.android.projectorlauncher.ui.fragment;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
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

import com.android.projectorlauncher.databinding.FragmentWifiBinding;
import com.android.projectorlauncher.databinding.ItemWifiBinding;
import com.android.projectorlauncher.presenter.WifiPresenter;
import com.android.projectorlauncher.ui.view.WifiView;

import java.util.ArrayList;
import java.util.List;

public class WifiFragment extends Fragment implements View.OnClickListener, WifiView {
    private FragmentWifiBinding wifiBinding;
    private WifiPresenter presenter;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == WifiManager.SCAN_RESULTS_AVAILABLE_ACTION) {
                presenter.updateNetworks();
            }
        }
    };
    private MutableLiveData<List<ScanResult>> nearbyList = new MutableLiveData<>();
    private MutableLiveData<List<ScanResult>> saveList = new MutableLiveData<>();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        presenter = new WifiPresenter(requireActivity());
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
        wifiBinding.wifiRecycleView.setAdapter(new WifiAdapter(nearbyList.getValue()));
        wifiBinding.saveRecycleView.setAdapter(new WifiAdapter(saveList.getValue()));
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
        if (wifiBinding.wifiRecycleView.getAdapter() != null) {
            wifiBinding.wifiRecycleView.getAdapter().notifyDataSetChanged();
        }
        if (wifiBinding.saveRecycleView.getAdapter() != null) {
            wifiBinding.saveRecycleView.getAdapter().notifyDataSetChanged();
        }
    }

    class WifiViewHolder extends RecyclerView.ViewHolder {
        ItemWifiBinding binding;

        public WifiViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemWifiBinding.bind(itemView);

        }
    }

    class WifiAdapter extends RecyclerView.Adapter<WifiViewHolder> {

        private final List<ScanResult> results;

        public WifiAdapter(List<ScanResult> results) {
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

        }

        @Override
        public int getItemCount() {
            return results.size();
        }
    }

    class WifiAnimation implements View.OnFocusChangeListener {

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
