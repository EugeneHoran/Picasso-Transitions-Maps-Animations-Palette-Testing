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
import android.widget.Toast;

import com.horan.eugene.imagestesting.Adapters.LogImages;
import com.horan.eugene.imagestesting.Adapters.LogImagesAdapter;
import com.horan.eugene.imagestesting.Adapters.OnRecyclerViewItemClickListener;
import com.horan.eugene.imagestesting.Util.Equations;

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
        for (int i = 0; i < link.length; i++) {
            LogImages logImages = new LogImages();
            logImages.setImage(link[i]);
            logImages.setDescription(description[i]);
            logImages.setTitle(title[i]);
            itemList.add(logImages);
        }

        logAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener<LogImages>() {
            @Override
            public void onItemClick(View view, LogImages log) {
                Toast.makeText(getActivity(), log.getDescription(), Toast.LENGTH_SHORT).show();
            }
        });
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


    String link[] = {
            "http://ak-hdl.buzzfed.com/static/2014-03/enhanced/webdr04/6/13/enhanced-buzz-wide-2757-1394130706-28.jpg",
            "http://ak-hdl.buzzfed.com/static/2014-03/enhanced/webdr07/10/18/grid-cell-16616-1394489170-9.jpg",
            "http://wallpaperput.com/wp-content/uploads/2014/11/nature-landscape-other-beautiful-new-york-city-sunset-wallpaper.jpg",
            "http://www.underworldmagazines.com/wp-content/uploads/2011/02/New-York-Brooklyn-Bridge-Sunset.jpg",
            "https://c1.staticflickr.com/7/6058/6339855817_537b1a82fe_b.jpg",
            "http://www.canyontours.com/wp-content/uploads/2013/09/West-Rim.jpg",
            "http://ak-hdl.buzzfed.com/static/2014-03/enhanced/webdr06/8/21/enhanced-buzz-wide-7005-1394331176-9.jpg",
            "http://ak-hdl.buzzfed.com/static/2014-03/enhanced/webdr06/6/14/enhanced-buzz-wide-21845-1394134018-7.jpg",
            "http://ak-hdl.buzzfed.com/static/2014-03/enhanced/webdr08/6/14/enhanced-buzz-wide-17183-1394133085-7.jpg",
            "http://ak-hdl.buzzfed.com/static/2014-03/enhanced/webdr06/6/13/enhanced-buzz-wide-17646-1394132084-26.jpg",
            "http://ak-hdl.buzzfed.com/static/2014-03/enhanced/webdr06/6/13/enhanced-buzz-wide-31314-1394131874-24.jpg",
            "http://ak-hdl.buzzfed.com/static/2014-03/enhanced/webdr06/6/14/enhanced-buzz-wide-18339-1394133640-22.jpg",
            "http://ak-hdl.buzzfed.com/static/2014-03/enhanced/webdr08/6/13/enhanced-buzz-wide-13916-1394131679-12.jpg",
            "http://ak-hdl.buzzfed.com/static/2014-03/enhanced/webdr08/6/14/enhanced-buzz-wide-14641-1394132509-7.jpg",
            "http://ak-hdl.buzzfed.com/static/2014-03/enhanced/webdr05/9/20/enhanced-buzz-wide-2009-1394410611-9.jpg",
            "http://ak-hdl.buzzfed.com/static/2014-03/enhanced/webdr06/8/22/enhanced-buzz-wide-8797-1394335143-6.jpg",
            "http://ak-hdl.buzzfed.com/static/2014-03/enhanced/webdr06/6/16/enhanced-buzz-wide-31778-1394141034-26.jpg",
            "http://ak-hdl.buzzfed.com/static/2014-03/enhanced/webdr03/7/10/enhanced-buzz-wide-6105-1394206825-20.jpg",
            "http://ak-hdl.buzzfed.com/static/2014-03/enhanced/webdr05/8/22/enhanced-buzz-wide-23846-1394335889-8.jpg",
            "http://ak-hdl.buzzfed.com/static/2014-03/enhanced/webdr08/7/11/enhanced-buzz-wide-11401-1394210543-22.jpg",
            "http://ak-hdl.buzzfed.com/static/2014-03/enhanced/webdr06/6/15/enhanced-buzz-wide-30275-1394137658-19.jpg",
            "http://ak-hdl.buzzfed.com/static/2014-03/enhanced/webdr04/7/16/enhanced-buzz-wide-12379-1394226157-19.jpg",
            "http://ak-hdl.buzzfed.com/static/2014-03/enhanced/webdr04/7/15/enhanced-buzz-wide-12258-1394225160-22.jpg",
            "http://ak-hdl.buzzfed.com/static/2014-03/enhanced/webdr07/7/17/enhanced-buzz-wide-28784-1394230208-20.jpg",
            "http://ak-hdl.buzzfed.com/static/2014-03/enhanced/webdr05/8/22/enhanced-buzz-wide-24357-1394334202-7.jpg",
            "http://ak-hdl.buzzfed.com/static/2014-03/enhanced/webdr02/7/13/enhanced-buzz-wide-8678-1394215378-20.jpg",
            "sdf"};
    String description[] = {
            "Glacier",
            "Antelope Canyon",
            "Water Front",
            "Brooklyn Bridge",
            "Fall Season",
            "Grand Canyon",
            "Oneonta Gorge",
            "Maroon Bells-Snowmass",
            "Dry Tortugas National Park",
            "Zion National Park",
            "Watkins Glen State Park",
            "Yosemite Valley",
            "Grand Prismatic Spring",
            "Haiku Stairs of Oahu",
            "Carlsbad Caverns",
            "Hamilton Pool",
            "Northern Lights",
            "Bryce Canyon",
            "Lake Tahoe",
            "Smoky Mountains",
            "The Wave",
            "Thor's Well",
            "Badlands National Park",
            "Palouse Falls",
            "Na Pali Coast State Park",
            "Devils Tower",
            "Broken Image Example"};
    String title[] = {
            "Alaska",
            "Arizona",
            "New York City",
            "New York City",
            "Boston",
            "Arizona",
            "Oregon",
            "Colorado",
            "Florida",
            "Utah",
            "New York",
            "California",
            "Wyoming",
            "Hawaii",
            "New Mexico",
            "Texas",
            "Alaska",
            "Utah",
            "California",
            "North Carolina/Tennessee",
            "Arizona",
            "Oregon",
            "South Dakota",
            "Washington",
            "Hawaii",
            "Wyoming",
            "No Image Provided"};
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
