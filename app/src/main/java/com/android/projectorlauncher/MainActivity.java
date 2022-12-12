package com.android.projectorlauncher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.android.projectorlauncher.databinding.ActivityMainBinding;
import com.android.projectorlauncher.databinding.ItemMainTabBinding;
import com.android.projectorlauncher.ui.fragment.ApplicationFragment;
import com.android.projectorlauncher.ui.fragment.ChildrenFragment;
import com.android.projectorlauncher.ui.fragment.ComicsFragment;
import com.android.projectorlauncher.ui.fragment.GameFragment;
import com.android.projectorlauncher.ui.fragment.HomeFragment;
import com.android.projectorlauncher.ui.fragment.MatchFragment;
import com.android.projectorlauncher.ui.fragment.MovieFragment;
import com.android.projectorlauncher.ui.fragment.SettingsFragment;
import com.android.projectorlauncher.ui.fragment.ShowFragment;
import com.android.projectorlauncher.ui.fragment.TvFragment;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class MainActivity extends AppCompatActivity implements HomeFragment.OnListenerClick{
    ActivityMainBinding binding;
    private final List<String> titles = Arrays.asList("首页", "电影", "剧集", "综艺", "动漫", "少儿", "体育", "应用", "设置");
    private final ArrayList<Fragment> fragments = new ArrayList<>();
    private final HomeFragment homeFragment = new HomeFragment();
    private final MovieFragment fragment = new MovieFragment();
    private final TvFragment tvFragment = new TvFragment();
    private final ShowFragment showFragment = new ShowFragment();
    private final MatchFragment matchFragment = new MatchFragment();
    private final ComicsFragment comicsFragment = new ComicsFragment();
    private final ChildrenFragment childrenFragment = new ChildrenFragment();
    private final ApplicationFragment applicationFragment = new ApplicationFragment();
    private final SettingsFragment settingsFragment = new SettingsFragment();
    private final GameFragment gameFragment = new GameFragment();
    private final Map<Boolean, String> map = new HashMap<>();
    private TextView lastTextView = null;
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
        fragments.add(homeFragment);
        fragments.add(fragment);
        fragments.add(tvFragment);
        fragments.add(showFragment);
        fragments.add(comicsFragment);
        fragments.add(childrenFragment);
        fragments.add(matchFragment);
        fragments.add(applicationFragment);
        fragments.add(settingsFragment);
        fragments.add(gameFragment);

        binding.tabLayout.setAdapter(new TabAdapter());
        binding.tabLayout.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.left = 8;
                outRect.right = 8;
                outRect.top = 5;
                outRect.bottom = 5;
            }
        });
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        binding.time.setText(dateFormat.format(new Date(System.currentTimeMillis())));


//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.container_pager, settingsFragment)
//                .commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void turnToPager(int position) {
        switchFragment(position);
        ItemMainTabBinding tabBinding = ItemMainTabBinding.bind(Objects.requireNonNull(Objects.requireNonNull(binding.tabLayout.getLayoutManager()).findViewByPosition(position)));
        tabBinding.getRoot().requestFocus();
        setColor(tabBinding.mainTabText, position);
    }


    class TabViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemMainTabBinding tabBinding;
        int position = 0;
        public TabViewHolder(@NonNull View itemView) {
            super(itemView);
            tabBinding = ItemMainTabBinding.bind(itemView);
            itemView.setOnClickListener(this);
        }

        public void bind(int position) {
            this.position = position;
            tabBinding.mainTabText.setText(titles.get(position));
            itemView.setOnFocusChangeListener((v, hasFocus) -> {
                map.put(hasFocus, v.getTag().toString());
                if (hasFocus) {
                    ViewCompat.animate(itemView)
                            .scaleX(1.1f)
                            .scaleY(1.1f)
                            .setDuration(250)
                            .start();
                } else {
                    ViewCompat.animate(itemView)
                            .scaleX(1.0f)
                            .scaleY(1.0f)
                            .setDuration(250)
                            .start();
                }
            });
            if (position == 0) {
                if (lastTextView == null) {
                    lastTextView = tabBinding.mainTabText;
                    lastTextView.setTextColor(getColor(R.color.self_4));
                    ViewCompat.animate(lastTextView)
                            .scaleX(1.3f)
                            .scaleY(1.3f)
                            .setDuration(250)
                            .start();
                    switchFragment(9);
                }
            }
        }

        @Override
        public void onClick(View v) {
            setColor(tabBinding.mainTabText, position);
            switchFragment(position);
        }

    }

    private void setColor(TextView textView, int position) {
        if (lastTextView == null) {
            lastTextView = textView;
        } else {
            lastTextView.setTextColor(getColor(R.color.white));
            ViewCompat.animate(lastTextView)
                    .scaleX(1.0f)
                    .scaleY(1.0f)
                    .setDuration(250)
                    .start();
        }
        if (position == 0) {
            textView.setTextColor(getColor(R.color.self_4));
        } else if (position == 1) {
            textView.setTextColor(getColor(R.color.self_5));
        } else if (position == 2) {
            textView.setTextColor(getColor(R.color.self_6));
        } else if (position == 3) {
            textView.setTextColor(getColor(R.color.self_2));
        } else if (position == 4) {
            textView.setTextColor(getColor(R.color.self_4));
        } else if (position == 5) {
            textView.setTextColor(getColor(R.color.self_8));
        } else if (position == 6) {
            textView.setTextColor(getColor(R.color.self_7));
        } else if (position == 7){
            textView.setTextColor(getColor(R.color.self_6));
        } else if (position == 8){
            textView.setTextColor(getColor(R.color.self_4));
        } else if (position == 9) {
            textView.setTextColor(getColor(R.color.self_8));
        }
        lastTextView = textView;
        ViewCompat.animate(lastTextView)
                .scaleX(1.3f)
                .scaleY(1.3f)
                .setDuration(250)
                .start();
    }

    private void switchFragment(int position) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_pager, fragments.get(position))
                .commit();
    }

    class TabAdapter extends RecyclerView.Adapter<TabViewHolder> {

        @NonNull
        @Override
        public TabViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ItemMainTabBinding tabBinding = ItemMainTabBinding.inflate(getLayoutInflater(), parent, false);
            return new TabViewHolder(tabBinding.getRoot());
        }

        @Override
        public void onBindViewHolder(@NonNull TabViewHolder holder, int position) {
            holder.itemView.setTag(titles.get(position));
            holder.bind(position);
        }

        @Override
        public int getItemCount() {
            return titles.size();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        unregisterReceiver(receiver1);

        super.onDestroy();
    }
}