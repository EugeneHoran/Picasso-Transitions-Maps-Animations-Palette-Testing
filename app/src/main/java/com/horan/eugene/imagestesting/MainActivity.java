package com.horan.eugene.imagestesting;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity implements FragmentUS.FragmentCallbacks {
    private DrawerLayout mNavigationDrawer;

    private Fragment fragment;
    private static String FRAGMENT_TAG = "";

    public static final String NAV_ITEM_ID = "navItemId";
    private int mNavItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            mNavItemId = savedInstanceState.getInt(NAV_ITEM_ID);
        } else {
            mNavItemId = R.id.nav_us;
            switchFragment(mNavItemId);
        }
        mNavigationDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView mNavigationView = (NavigationView) findViewById(R.id.nav);
        mNavigationView.inflateMenu(R.menu.nav_menu);
        mNavigationView.getMenu().findItem(mNavItemId).setChecked(true);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mNavItemId = menuItem.getItemId();
                switchFragment(menuItem.getItemId());
                menuItem.setChecked(true);
                handleNavigationDrawer();
                return false;
            }
        });
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(NAV_ITEM_ID, mNavItemId);
    }

    /**
     * Handles Navigation Logic
     */
    private void switchFragment(int menuId) {
        switch (menuId) {
            case R.id.nav_us:
                fragment = new FragmentUS();
                FRAGMENT_TAG = "US";
                break;
            case R.id.nav_other:

                break;

            default:
                break;
        }
        if (fragment != null && fragment != getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG)) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, FRAGMENT_TAG).commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawer.isDrawerOpen(GravityCompat.START)) {
            mNavigationDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Used to handle the closing and opening of the Navigation Drawer.
     * Prevent repetitive statements
     */
    private void handleNavigationDrawer() {
        if (mNavigationDrawer != null) {
            if (mNavigationDrawer.isDrawerOpen(GravityCompat.START)) {
                mNavigationDrawer.closeDrawer(GravityCompat.START);
            } else {
                mNavigationDrawer.openDrawer(GravityCompat.START);
            }
        }
    }

    @Override
    public void openNavigationDrawer() {
        handleNavigationDrawer();
    }
}
