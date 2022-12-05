package com.android.projectorlauncher.ui.fragment;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.projectorlauncher.R;
import com.android.projectorlauncher.databinding.FragmentTimeBinding;
import com.android.projectorlauncher.databinding.PopupWindowDateBinding;
import com.android.projectorlauncher.databinding.PopupWindowModeBinding;
import com.android.projectorlauncher.databinding.PopupWindowTimeBinding;
import com.android.projectorlauncher.ui.dialog.SpinnerPopupWindow;
import com.android.projectorlauncher.utils.TimeUtil;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class TimeFragment extends Fragment implements View.OnClickListener {

    private final List<String> array = Arrays.asList("自动设置", "手动设置");
    private static final int TIME_HOUR = 0;
    private static final int TIME_MINUTE = 1;
    private static int timeChange = 0;
    private static int hour = 0;
    private static int minute = 0;
    private static long date = 0;
    private PopupWindowModeBinding modeBinding;
    private FragmentTimeBinding timeBinding;
    private SpinnerPopupWindow popupWindow;
    private PopupWindowTimeBinding windowTimeBinding;
    private PopupWindowDateBinding dateBinding;
    private PopupWindow timeWindow;
    private PopupWindow calendarWindow;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        timeBinding = FragmentTimeBinding.inflate(inflater, container, false);
        View view1 = LayoutInflater.from(requireContext()).inflate(R.layout.popup_window_mode, container, false);
        View view2 = LayoutInflater.from(requireContext()).inflate(R.layout.popup_window_time, container, false);
        View view3 = LayoutInflater.from(requireContext()).inflate(R.layout.popup_window_date, container, false);
        modeBinding = PopupWindowModeBinding.bind(view1);
        windowTimeBinding = PopupWindowTimeBinding.bind(view2);
        dateBinding = PopupWindowDateBinding.bind(view3);

        popupWindow = new SpinnerPopupWindow(modeBinding.getRoot(), WRAP_CONTENT, WRAP_CONTENT, true);
        timeWindow = new PopupWindow(windowTimeBinding.getRoot(), WRAP_CONTENT, WRAP_CONTENT, true);
        calendarWindow = new PopupWindow(dateBinding.getRoot(), WRAP_CONTENT, WRAP_CONTENT, true);


        modeChangeOnClick();
        timeChangeOnClick();
        calendarOnClick();

        timeBinding.timeHour.setOnClickListener(this);
        timeBinding.timeMinute.setOnClickListener(this);
        timeBinding.dateSet.setOnClickListener(this);
        timeBinding.timeZone.setOnClickListener(this);
        timeBinding.mode.setOnClickListener(this);
        timeBinding.confirm.setOnClickListener(this);
        return timeBinding.getRoot();
    }

    private void calendarOnClick() {
        dateBinding.confirm.setOnClickListener(v -> {
            date = dateBinding.calendar.getDate();
            calendarWindow.dismiss();
        });
    }

    private void modeChangeOnClick() {
        modeBinding.modeManual.setOnClickListener(v -> {
            timeBinding.modeName.setText(array.get(1));
            timeBinding.timeSettings.setVisibility(View.VISIBLE);
            popupWindow.dismiss();
        });
        modeBinding.modeAuto.setOnClickListener(v -> {
            timeBinding.modeName.setText(array.get(0));
            timeBinding.timeSettings.setVisibility(View.INVISIBLE);
            popupWindow.dismiss();
        });
    }

    private void timeChangeOnClick() {
        windowTimeBinding.add.setOnClickListener(v -> {
            if (timeChange == TIME_HOUR) {
                hour++;
                if (hour > 23) hour = 0;
                timeBinding.timeHourTitle.setText(String.valueOf(hour));
            } else if (timeChange == TIME_MINUTE) {
                minute++;
                if (minute > 59) minute = 0;
                timeBinding.timeMinuteTitle.setText(String.valueOf(minute));
            }
        });
        windowTimeBinding.reduce.setOnClickListener(v -> {
            if (timeChange == TIME_HOUR) {
                hour--;
                if (hour < 0) hour = 23;
                timeBinding.timeHourTitle.setText(String.valueOf(hour));
            } else if (timeChange == TIME_MINUTE) {
                minute--;
                if (minute < 0) minute = 59;
                timeBinding.timeMinuteTitle.setText(String.valueOf(minute));
            }
        });
        windowTimeBinding.confirm.setOnClickListener(v -> timeWindow.dismiss());
    }

    public void initPopupWindow(View view) {
        popupWindow.setAnimationStyle(R.anim.anim_pop);
        popupWindow.showAsDropDown(view, 500, -50);
    }

    @Override
    public void onClick(View v) {
        if (v == timeBinding.timeHour) {
            if (timeWindow.isShowing()) {
                timeWindow.dismiss();
            }
            timeChange = 0;
            timeWindow.setAnimationStyle(R.anim.anim_pop);
            timeWindow.showAsDropDown(v, 200, 0);
        } else if (v == timeBinding.timeMinute) {
            if (timeWindow.isShowing()) {
                timeWindow.dismiss();
            }
            timeChange = 1;
            timeWindow.setAnimationStyle(R.anim.anim_pop);
            timeWindow.showAsDropDown(v, 200, 0);
        } else if (v == timeBinding.dateSet) {
            if (calendarWindow.isShowing()) {
                calendarWindow.dismiss();
            }
            calendarWindow.setAnimationStyle(R.anim.anim_pop);
            calendarWindow.showAsDropDown(v, 500, 0);
        } else if (v == timeBinding.mode) {
            initPopupWindow(v);
        } else if (v == timeBinding.confirm) {
            TimeUtil.setData(date);
            TimeUtil.setTime(hour, minute);
            Calendar calendar = Calendar.getInstance();
            Log.d("TimeFragment", "onClick: " + calendar.getTimeInMillis());

        }
    }
}
