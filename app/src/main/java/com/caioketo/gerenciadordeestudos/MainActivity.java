package com.caioketo.gerenciadordeestudos;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;

import com.caioketo.gerenciadordeestudos.fragmentos.AddAulaFragment;
import com.caioketo.gerenciadordeestudos.fragmentos.AulasListFragment;
import com.caioketo.gerenciadordeestudos.fragmentos.CalendarioFragment;
import com.caioketo.gerenciadordeestudos.fragmentos.CardsFragment;
import com.caioketo.gerenciadordeestudos.fragmentos.GraphicsFragment;
import com.caioketo.gerenciadordeestudos.fragmentos.ICFragment;
import com.caioketo.gerenciadordeestudos.fragmentos.MateriasListFragment;
import com.caioketo.gerenciadordeestudos.fragmentos.ProvasListFragment;
import com.caioketo.gerenciadordeestudos.fragmentos.TrabalhosListFragment;

import java.util.ArrayList;


public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */


    public static MainActivity instance;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    public ICFragment mCurrentFrag;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        instance = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        Fragment frag = null;

        if (position == 0) {
            MateriasListFragment mFrag = new MateriasListFragment();
            frag = mFrag;
            mCurrentFrag = mFrag;
        }
        else if (position == 1) {
            AddAulaFragment mFrag = new AddAulaFragment();
            frag = mFrag;
            mCurrentFrag = mFrag;
        }
        else if (position == 2) {
            CalendarioFragment mFrag = new CalendarioFragment();
            frag = mFrag;
            mCurrentFrag = mFrag;
        }
        else if (position == 3) {
            CardsFragment mFrag = new CardsFragment();
            frag = mFrag;
            mCurrentFrag = mFrag;
        }
        else if (position == 4) {
            GraphicsFragment mFrag = new GraphicsFragment();
            frag = mFrag;
            mCurrentFrag = mFrag;
        }
        else if (position == 5) {
            ProvasListFragment mFrag = new ProvasListFragment();
            frag = mFrag;
            mCurrentFrag = mFrag;
        }
        else if (position == 6) {
            TrabalhosListFragment mFrag = new TrabalhosListFragment();
            frag = mFrag;
            mCurrentFrag = mFrag;
        }
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, frag)
                .commit();
        invalidateOptionsMenu();
    }


    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            if (mCurrentFrag != null) {
                return mCurrentFrag.fCreateOptionMenu(menu);
            }
            else {
                getMenuInflater().inflate(R.menu.main, menu);
                restoreActionBar();
                return true;
            }
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
        //}
        if (mCurrentFrag != null) {
            if (mCurrentFrag.fOptionsItemSelected(item)) {
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed()
    {
        if (mCurrentFrag != null) {
            if (mCurrentFrag.fOnBackPressed()) {
                return;
            }
        }
        super.onBackPressed();
    }
}
