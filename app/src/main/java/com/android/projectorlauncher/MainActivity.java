package com.android.projectorlauncher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.projectorlauncher.databinding.ActivityMainBinding;
import com.android.projectorlauncher.ui.fragment.ApplicationFragment;
import com.android.projectorlauncher.ui.fragment.ChildrenFragment;
import com.android.projectorlauncher.ui.fragment.ComicsFragment;
import com.android.projectorlauncher.ui.fragment.MatchFragment;
import com.android.projectorlauncher.ui.fragment.MovieFragment;
import com.android.projectorlauncher.ui.fragment.ShowFragment;
import com.android.projectorlauncher.ui.fragment.TvFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private final List<String> titles = Arrays.asList("电影", "剧集", "综艺", "动漫", "少儿", "体育", "应用");
    private final ArrayList<Fragment> fragments = new ArrayList<>();
    private TabLayoutMediator mediator;
    private final int normalSize = 18;
    private View selectView = null;
    private final MovieFragment fragment = new MovieFragment();
    private final TvFragment tvFragment = new TvFragment();
    private final ShowFragment showFragment = new ShowFragment();
    private final MatchFragment matchFragment = new MatchFragment();
    private final ComicsFragment comicsFragment = new ComicsFragment();
    private final ChildrenFragment childrenFragment = new ChildrenFragment();
    private final ApplicationFragment applicationFragment = new ApplicationFragment();
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (binding != null) {
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                binding.time.setText(dateFormat.format(new Date(System.currentTimeMillis())));
            }
        }
    };
    private final BroadcastReceiver receiver1 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (isNetworkConnected(context)) {
                Toast.makeText(context, "网络已连接", Toast.LENGTH_SHORT).show();
                if (binding != null) {
                    binding.netStat.setImageResource(R.drawable.ic_baseline_signal_cellular_4_bar_24);
                }
            } else {
                Toast.makeText(context, "网络已断开", Toast.LENGTH_SHORT).show();
                if (binding != null) {
                    binding.netStat.setImageResource(R.drawable.ic_baseline_signal_cellular_off_24);
                }
            }

        }

        public boolean isNetworkConnected(Context context) {
            ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connManager.getActiveNetworkInfo();
            if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
                return false;
            }
            return activeNetworkInfo.getDetailedState() == NetworkInfo.DetailedState.CONNECTED;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        initBroadcast();
    }

    private void initBroadcast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_TICK);
        registerReceiver(receiver, filter);
        IntentFilter filter1 = new IntentFilter();
        filter1.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter1.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(receiver1, filter1);
    }

    private void initView() {
        int cnt = 0;
        for (String ignored : titles) {
            if (cnt == titles.size()) {
                break;
            }
            if (cnt == 0) {
                fragments.add(fragment);
            } else if (cnt == 1) {
                fragments.add(tvFragment);
            } else if (cnt == 2) {
                fragments.add(showFragment);
            } else if (cnt == 3 || cnt == 7) {
                fragments.add(comicsFragment);
            } else if (cnt == 4) {
                fragments.add(childrenFragment);
            } else if (cnt == 5) {
                fragments.add(matchFragment);
            } else {
                fragments.add(applicationFragment);
            }

            cnt++;
        }
        binding.tabLayout.getViewTreeObserver().addOnGlobalFocusChangeListener((oldFocus, newFocus) -> {
            if (!(oldFocus instanceof TabLayout.TabView) && newFocus instanceof TabLayout.TabView && selectView != null) {
                selectView.setFocusable(true);
                selectView.requestFocus();
            }
        });

        binding.viewPager.setAdapter(new FragmentStateAdapter(getSupportFragmentManager(), getLifecycle()) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return fragments.get(position);
            }

            @Override
            public int getItemCount() {
                return fragments.size();
            }
        });
        binding.viewPager.registerOnPageChangeCallback(changeCallback);
        binding.viewPager.setUserInputEnabled(false);
        mediator = new TabLayoutMediator(binding.tabLayout, binding.viewPager, (tab, position) -> {
            TextView tabView = new TextView(MainActivity.this);
            tabView.setText(titles.get(position));
            tabView.setTextSize(normalSize);
            tabView.setTextColor(Color.WHITE);
            tabView.setFocusable(true);
            tabView.setPadding(10, 0, 10, 0);
            tabView.setGravity(Gravity.CENTER);
            tab.setCustomView(tabView);
            tab.view.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
            tab.view.setNextFocusDownId(R.id.viewPager);
            tab.view.setGravity(Gravity.CENTER);

        });
        mediator.attach();

        binding.tabLayout.setFocusable(true);
//        binding.tabLayout.requestFocus();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        binding.time.setText(dateFormat.format(new Date(System.currentTimeMillis())));

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    private final ViewPager2.OnPageChangeCallback changeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            int tabCount = binding.tabLayout.getTabCount();
            for (int i = 0; i < tabCount; i++) {
                TabLayout.Tab tab = binding.tabLayout.getTabAt(i);
                if (tab == null) return;
                TextView tabView = (TextView) tab.getCustomView();
                if (tabView == null) return;
                if (tab.getPosition() == position) {
                    int activeSize = 25;
                    tabView.setTextSize(activeSize);
                    tabView.setTypeface(Typeface.DEFAULT_BOLD);
                    selectView = tab.view;
                    tab.view.setFocusable(true);
                    tab.view.requestFocus();
                    if (position == 0) {
                        tabView.setTextColor(getColor(R.color.self_5));
                        binding.tabLayout.setSelectedTabIndicatorColor(getColor(R.color.self_5));
                    } else if (position == 1) {
                        tabView.setTextColor(getColor(R.color.self_6));
                        binding.tabLayout.setSelectedTabIndicatorColor(getColor(R.color.self_6));
                    } else if (position == 2) {
                        tabView.setTextColor(getColor(R.color.self_2));
                        binding.tabLayout.setSelectedTabIndicatorColor(getColor(R.color.self_2));
                    } else if (position == 3) {
                        tabView.setTextColor(getColor(R.color.self_4));
                        binding.tabLayout.setSelectedTabIndicatorColor(getColor(R.color.self_4));
                    } else if (position == 4) {
                        tabView.setTextColor(getColor(R.color.self_8));
                        binding.tabLayout.setSelectedTabIndicatorColor(getColor(R.color.self_8));
                    } else {
                        tabView.setTextColor(getColor(R.color.self_7));
                        binding.tabLayout.setSelectedTabIndicatorColor(getColor(R.color.self_7));
                    }
                } else {
                    tabView.setTextSize(normalSize);
                    tabView.setTypeface(Typeface.DEFAULT);
                    tabView.setTextColor(getColor(R.color.white));
                }
            }
        }
    };

    @Override
    public void onBackPressed() {
        binding.tabLayout.setFocusable(true);
        binding.viewPager.clearFocus();
        binding.tabLayout.requestFocus();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        unregisterReceiver(receiver1);
        mediator.detach();
        binding.viewPager.unregisterOnPageChangeCallback(changeCallback);
        super.onDestroy();
    }
}