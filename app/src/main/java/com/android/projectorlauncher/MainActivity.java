package com.android.projectorlauncher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.projectorlauncher.databinding.ActivityMainBinding;
import com.android.projectorlauncher.presenter.MainPresenter;
import com.android.projectorlauncher.ui.view.PagerFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private final List<String> titles = Arrays.asList("电影", "剧集", "综艺", "排行榜", "应用");
    private final ArrayList<PagerFragment> fragments = new ArrayList<>();
    private TabLayoutMediator mediator;

    private final int normalSize = 18;
    private View selectView = null;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        initPresenter();
    }

    private void initPresenter() {
        presenter = new MainPresenter(this, fragments);
    }

    private void initView() {
        for (String title : titles) {
            fragments.add(new PagerFragment(new ArrayList<>()));
        }
        binding.tabLayout.getViewTreeObserver().addOnGlobalFocusChangeListener((oldFocus, newFocus) -> {
            Log.d("ViewTreeObserver", "initView: " + oldFocus + " " + newFocus);
            Log.d("ViewTreeObserver", "initView: " + selectView);
            if(!(oldFocus instanceof TabLayout.TabView) && newFocus instanceof TabLayout.TabView) {
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
        mediator = new TabLayoutMediator(binding.tabLayout, binding.viewPager, (tab, position) -> {
            TextView tabView = new TextView(MainActivity.this);
            tabView.setText(titles.get(position));
            tabView.setTextSize(normalSize);
            tabView.setTextColor(Color.WHITE);
            tabView.setFocusable(true);
            tabView.setPadding(10, 0, 10, 0);
            tabView.setGravity(Gravity.CENTER);
            tab.view.setBackgroundResource(R.drawable.tab_focus_selector);
            tab.setCustomView(tabView);
            tab.view.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
            tab.view.setNextFocusDownId(R.id.viewPager);
            tab.view.setGravity(Gravity.CENTER);

        });
        mediator.attach();
        binding.tabLayout.setFocusable(true);
        binding.tabLayout.requestFocus();

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
                } else {
                    tabView.setTextSize(normalSize);
                    tabView.setTypeface(Typeface.DEFAULT);
                }
            }
        }
    };

    @Override
    public void onBackPressed() {
//        binding.tabLayout.setFocusable(true);
        binding.viewPager.clearFocus();
        binding.tabLayout.requestFocus();
//        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        mediator.detach();
        binding.viewPager.unregisterOnPageChangeCallback(changeCallback);
        super.onDestroy();
    }
}