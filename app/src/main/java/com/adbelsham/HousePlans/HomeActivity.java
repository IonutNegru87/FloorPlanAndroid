package com.adbelsham.HousePlans;


import android.os.Bundle;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.Gravity;


import Adapter.NavigationDrawerAdapter;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {

    @InjectView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @InjectView(R.id.left_drawer_recycleView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.inject(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        NavigationDrawerAdapter adapter = new NavigationDrawerAdapter(this);
        recyclerView.setAdapter(adapter);
        updateFragment(0);

    }

    @OnClick(R.id.menuBtn)
    public void menuClick() {
        drawerLayout.openDrawer(Gravity.LEFT);
    }

    public void updateFragment(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (position) {
            case 0:
                Fragment fragment = new HomeFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .commit();
                break;
            case 1:
                Fragment favouriteFragment = new FavouritesFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, favouriteFragment)
                        .commit();
                break;
            case 2:
                Fragment planFragment = new PlansFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, planFragment)
                        .commit();
                break;
        }

        drawerLayout.closeDrawer(Gravity.LEFT);
    }
}
