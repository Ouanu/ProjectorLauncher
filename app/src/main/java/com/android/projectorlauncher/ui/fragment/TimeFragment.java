package com.android.projectorlauncher.ui.fragment;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.android.projectorlauncher.R;
import com.android.projectorlauncher.databinding.FragmentTimeBinding;
import com.android.projectorlauncher.databinding.ItemTimezoneBinding;
import com.android.projectorlauncher.databinding.PopupWindowDateBinding;
import com.android.projectorlauncher.databinding.PopupWindowModeBinding;
import com.android.projectorlauncher.databinding.PopupWindowTimeBinding;
import com.android.projectorlauncher.databinding.PopupWindowTimezoneBinding;
import com.android.projectorlauncher.ui.dialog.SpinnerPopupWindow;
import com.android.projectorlauncher.utils.TimeUtil;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class TimeFragment extends Fragment implements View.OnClickListener {

    private final List<String> array = Arrays.asList("自动设置", "手动设置");
    private static final int TIME_HOUR = 0;
    private static final int TIME_MINUTE = 1;
    private static int timeChange = 0;
    private static int hour = 0;
    private static int minute = 0;
    private static int year = 0;
    private static int month = 0;
    private static int day = 0;
    private static String timezone = null;
    private PopupWindowModeBinding modeBinding;
    private FragmentTimeBinding timeBinding;
    private SpinnerPopupWindow popupWindow;
    private PopupWindowTimeBinding windowTimeBinding;
    private PopupWindowDateBinding dateBinding;
    private PopupWindowTimezoneBinding timezoneBinding;
    private PopupWindow timeWindow;
    private PopupWindow calendarWindow;
    private PopupWindow timezoneWindow;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        timeBinding = FragmentTimeBinding.inflate(inflater, container, false);
        View view1 = LayoutInflater.from(requireContext()).inflate(R.layout.popup_window_mode, container, false);
        View view2 = LayoutInflater.from(requireContext()).inflate(R.layout.popup_window_time, container, false);
        View view3 = LayoutInflater.from(requireContext()).inflate(R.layout.popup_window_date, container, false);
        View view4 = LayoutInflater.from(requireContext()).inflate(R.layout.popup_window_timezone, container, false);
        modeBinding = PopupWindowModeBinding.bind(view1);
        windowTimeBinding = PopupWindowTimeBinding.bind(view2);
        dateBinding = PopupWindowDateBinding.bind(view3);
        timezoneBinding = PopupWindowTimezoneBinding.bind(view4);

        popupWindow = new SpinnerPopupWindow(modeBinding.getRoot(), WRAP_CONTENT, WRAP_CONTENT, true);
        timeWindow = new PopupWindow(windowTimeBinding.getRoot(), WRAP_CONTENT, WRAP_CONTENT, true);
        calendarWindow = new PopupWindow(dateBinding.getRoot(), WRAP_CONTENT, WRAP_CONTENT, true);
        timezoneWindow = new PopupWindow(timezoneBinding.getRoot(), WRAP_CONTENT, WRAP_CONTENT, true);

        modeChangeOnClick();
        timeChangeOnClick();
        calendarOnClick();
        timeBindingOnClick();

        if (TimeUtil.isAutoSetTime(requireContext()) == 0) {
            timeBinding.modeName.setText(array.get(1));
            timeBinding.timeSettings.setVisibility(View.VISIBLE);
        } else {
            timeBinding.modeName.setText(array.get(0));
            timeBinding.timeSettings.setVisibility(View.INVISIBLE);
        }

        getSystemCalendar();
        showSystemCalendar();

        return timeBinding.getRoot();
    }

    private void timeBindingOnClick() {
        timeBinding.timeHour.setOnClickListener(this);
        timeBinding.timeMinute.setOnClickListener(this);
        timeBinding.dateSet.setOnClickListener(this);
        timeBinding.timeZone.setOnClickListener(this);
        timeBinding.mode.setOnClickListener(this);
        timeBinding.confirm.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void getSystemCalendar() {
        Calendar calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        timezone = TimeZone.getDefault().getDisplayName();
        timeBinding.timeHourTitle.setText(String.format(Locale.US,"%02d", hour));
        timeBinding.timeMinuteTitle.setText(String.format(Locale.US,"%02d", minute));
        timeBinding.timeZoneSet.setText(timezone);
    }
    private void showSystemCalendar() {
        String buffer = String.valueOf(year) + '-' +
                String.format(Locale.US,"%02d", (month + 1)) + '-' +
                String.format(Locale.US,"%02d", day);
        timeBinding.dateSetTitle.setText(buffer);
    }

    private void calendarOnClick() {
        dateBinding.confirm.setOnClickListener(v -> {
            calendarWindow.dismiss();
            showSystemCalendar();
        });
        dateBinding.calendar.setOnDateChangeListener((view, y, m, d) -> {
            year = y;
            month = m;
            day = d;
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
            TimeUtil.setAutoDateTime(requireContext(), 1);
            TimeUtil.setAutoTimeZone(requireContext(), 1);
            Intent intent = new Intent(Intent.ACTION_TIME_TICK);
            requireActivity().sendBroadcast(intent);
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
                timeBinding.timeMinuteTitle.setText(String.format(Locale.US,"%02d", minute));
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
            timeWindow.showAsDropDown(v, 50, 0);
        } else if (v == timeBinding.timeMinute) {
            if (timeWindow.isShowing()) {
                timeWindow.dismiss();
            }
            timeChange = 1;
            timeWindow.setAnimationStyle(R.anim.anim_pop);
            timeWindow.showAsDropDown(v, 50, 0);
        } else if (v == timeBinding.dateSet) {
            if (calendarWindow.isShowing()) {
                calendarWindow.dismiss();
            }
            calendarWindow.setAnimationStyle(R.anim.anim_pop);
            calendarWindow.showAsDropDown(v, 500, 0);
        } else if (v == timeBinding.mode) {
            initPopupWindow(v);
        } else if (v == timeBinding.timeZone) {
            timezoneBinding.recyclerView.setAdapter(new TimeZoneAdapter(TimeUtil.getTimeZoneIds()));
            timezoneWindow.setAnimationStyle(R.anim.anim_pop);
            timezoneWindow.showAsDropDown(v, 500, 0);
        } else if (v == timeBinding.confirm) {
            TimeUtil.setData(year, month, day, hour, minute);
            if (timezone.contains("Etc")) {
                TimeUtil.setTimeZone(requireContext(), timezone);
            }
            TimeUtil.setAutoDateTime(requireContext(), 0);
            TimeUtil.setAutoTimeZone(requireContext(), 0);
            Intent intent = new Intent(Intent.ACTION_TIME_TICK);
            requireActivity().sendBroadcast(intent);
        }
    }

    class TimeZoneViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemTimezoneBinding timezoneBinding;
        public TimeZoneViewHolder(@NonNull View itemView) {
            super(itemView);
            timezoneBinding = ItemTimezoneBinding.bind(itemView);
            itemView.setOnClickListener(this);
        }

        private void bind(String s) {
            timezoneBinding.text.setText(s);
        }

        @Override
        public void onClick(View v) {
            timezone = timezoneBinding.text.getText().toString();
            timeBinding.timeZoneSet.setText(timezone);
            timezoneWindow.dismiss();
        }
    }

    class TimeZoneAdapter extends RecyclerView.Adapter<TimeZoneViewHolder> {

        private List<String> ids;

        public TimeZoneAdapter(List<String> ids) {
            this.ids = ids;
        }

        @NonNull
        @Override
        public TimeZoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ItemTimezoneBinding timezoneBinding = ItemTimezoneBinding.inflate(getLayoutInflater(), parent, false);
            return new TimeZoneViewHolder(timezoneBinding.getRoot());
        }

        @Override
        public void onBindViewHolder(@NonNull TimeZoneViewHolder holder, int position) {
            holder.bind(ids.get(position));
        }

        @Override
        public int getItemCount() {
            return ids.size();
        }
    }
}


