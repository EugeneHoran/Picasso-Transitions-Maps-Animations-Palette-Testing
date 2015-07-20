package com.horan.eugene.imagestesting;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.horan.eugene.imagestesting.Adapters.LogImages;
import com.horan.eugene.imagestesting.Adapters.LogImagesAdapter;
import com.horan.eugene.imagestesting.Util.Equations;
import com.horan.eugene.imagestesting.Util.Globals;

import java.util.ArrayList;

public class FragmentUS extends Fragment {
    private View v;
    Toolbar toolbar;
    RecyclerView recycler;
    LogImagesAdapter logAdapter;
    ArrayList<LogImages> itemList = new ArrayList<>();

    private static final String LIST_OR_GRID = "list_or_grid";
    int listOrGrid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_us, container, false);
        if (savedInstanceState != null) {
            listOrGrid = savedInstanceState.getInt(LIST_OR_GRID);
        } else {
            listOrGrid = R.id.sub_list;
        }

        toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.openNavigationDrawer();
            }
        });
        recycler = (RecyclerView) v.findViewById(R.id.recycler);
        filterItems(listOrGrid);
        for (int i = 0; i < Globals.link.length; i++) {
            LogImages logImages = new LogImages();
            logImages.setImage(Globals.link[i]);
            logImages.setDescription(Globals.description[i]);
            logImages.setTitle(Globals.location[i]);
            itemList.add(logImages);
        }


        FloatingActionButton mFab = (FloatingActionButton) v.findViewById(R.id.fab);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) mFab.getLayoutParams();
            p.setMargins(0, 0, Equations.dpToPx(getActivity(), 8), 0); // get rid of margins since shadow area is now the margin
            mFab.setLayoutParams(p);
        }
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.sub_list:
                        if (item.isChecked()) item.setChecked(false);
                        else item.setChecked(true);

                        filterItems(R.id.sub_list);
                        break;
                    case R.id.sub_grid:
                        if (item.isChecked()) item.setChecked(false);
                        else item.setChecked(true);

                        filterItems(R.id.sub_grid);
                        break;

                    default:
                        break;
                }
                return false;
            }
        });
        return v;
    }

    private void filterItems(int id) {
        listOrGrid = id;
        boolean whichIsChecked = true;
        switch (id) {
            case R.id.sub_list:
                toolbar.getMenu().findItem(R.id.sub_list).setChecked(true);
                recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                whichIsChecked = true;
                break;
            case R.id.sub_grid:
                toolbar.getMenu().findItem(R.id.sub_grid).setChecked(true);
                recycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                whichIsChecked = false;
                break;
            default:
                break;
        }
        logAdapter = new LogImagesAdapter(getActivity(), itemList, R.layout.log_row, whichIsChecked);
        recycler.setAdapter(logAdapter);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(LIST_OR_GRID, listOrGrid);
        super.onSaveInstanceState(savedInstanceState);
    }


    /**
     * Interface to communicate to the parent activity (MainActivity.java)
     */
    private FragmentCallbacks mCallbacks;


    public interface FragmentCallbacks {
        void openNavigationDrawer();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (FragmentCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement Fragment One.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }
}
