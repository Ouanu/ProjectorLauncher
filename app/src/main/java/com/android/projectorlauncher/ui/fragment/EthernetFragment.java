package com.android.projectorlauncher.ui.fragment;

import android.content.Context;
import android.net.EthernetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.projectorlauncher.R;
import com.android.projectorlauncher.databinding.FragmentEthernetBinding;
import com.android.projectorlauncher.utils.EthernetUtils;

public class EthernetFragment extends Fragment implements View.OnClickListener {
    private FragmentEthernetBinding ethernetBinding;
    private boolean mode = false;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ethernetBinding = FragmentEthernetBinding.inflate(inflater, container, false);
        ethernetBinding.btnSwitch.setTag(EthernetUtils.getMode(requireContext()));
        mode = EthernetUtils.getMode(requireContext()).equals(EthernetManager.ETHERNET_CONNECT_MODE_DHCP);
        updateUI();
        ethernetBinding.btnSwitch.setOnClickListener(this);
        ethernetBinding.connect.setOnClickListener(this);
        return ethernetBinding.getRoot();
    }

    private void updateUI() {
        if (mode) {
            ethernetBinding.ipAddress.setEnabled(false);
            ethernetBinding.gatewayAddress.setEnabled(false);
            ethernetBinding.netmask.setEnabled(false);
            ethernetBinding.dns1.setEnabled(false);
            ethernetBinding.btnSwitch.setText("动态IP");
            ethernetBinding.btnSwitch.setNextFocusDownId(ethernetBinding.btnSwitch.getId());
        } else {
            ethernetBinding.ipAddress.setEnabled(true);
            ethernetBinding.gatewayAddress.setEnabled(true);
            ethernetBinding.netmask.setEnabled(true);
            ethernetBinding.dns1.setEnabled(true);
            ethernetBinding.btnSwitch.setText("静态IP");
            ethernetBinding.btnSwitch.setNextFocusDownId(ethernetBinding.ipAddress.getId());
        }
        ethernetBinding.connect.setNextFocusDownId(ethernetBinding.connect.getId());
        ethernetBinding.ipAddress.setText(EthernetUtils.getIpAddress(requireContext()));
        ethernetBinding.gatewayAddress.setText(EthernetUtils.getGateway(requireContext()));
        ethernetBinding.netmask.setText(EthernetUtils.getNetmask(requireContext()));
        ethernetBinding.dns1.setText(EthernetUtils.getDNS(requireContext()));
    }

    @Override
    public void onClick(View v) {
        if (v == ethernetBinding.btnSwitch) {
            mode = !mode;
            updateUI();
        } else if (v == ethernetBinding.connect) {
            if (mode) {
                EthernetUtils.setDhcpIp(requireContext());
            } else {
                EthernetUtils.setStaticIp(
                        requireContext(),
                        String.valueOf(ethernetBinding.ipAddress.getText()),
                        String.valueOf(ethernetBinding.gatewayAddress.getText()),
                        String.valueOf(ethernetBinding.netmask.getText()),
                        String.valueOf(ethernetBinding.dns1.getText()));
            }
            Toast.makeText(requireContext(), R.string.switch_ethernet_success, Toast.LENGTH_SHORT).show();

        }

        Log.d("EthernetFragment", "onClick: " + mode);

    }
}
