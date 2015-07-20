package com.horan.eugene.imagestesting;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.horan.eugene.imagestesting.Util.Equations;
import com.horan.eugene.imagestesting.Util.Globals;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;


public class DetailsActivity extends AppCompatActivity {
    int position;
    Toolbar toolbar;
    private GoogleMap mMap;
    private LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        position = getIntent().getExtras().getInt("POSITION");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_details);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_satellite:
                        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                        toolbar.getMenu().clear();
                        toolbar.inflateMenu(R.menu.menu_details_two);
                        break;
                    case R.id.action_map:
                        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                        toolbar.getMenu().clear();
                        toolbar.inflateMenu(R.menu.menu_details);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supportFinishAfterTransition();
            }
        });
        setImage();
        setText();
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        if (mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private int Zoom(int pos) {
        int zoom = 12;

        switch (pos) {
            case 0:
                zoom = 12;
                break;
            case 1:
                zoom = 9;
                break;
            case 2:
                zoom = 13;
                break;
            case 3:
                zoom = 15;
                break;
            case 4:
                zoom = 15;
                break;
            case 5:
                zoom = 9;
                break;
            case 6:
                zoom = 12;
                break;
            case 7:
                zoom = 8;
                break;
            case 8:
                zoom = 14;
                break;
            case 9:
                zoom = 9;
                break;
            case 10:
                zoom = 12;
                break;
            case 11:
                zoom = 12;
                break;
            case 12:
                zoom = 12;
                break;
            case 13:
                zoom = 12;
                break;
            default:
                break;
        }
        return zoom;
    }

    private void setUpMap() {
        Geocoder coder = new Geocoder(this);
        try {
            List<Address> addressList = coder.getFromLocationName(Globals.description[position] + ", " + Globals.location[position], 1);
            if (addressList != null && addressList.size() > 0) {
                double lat = addressList.get(0).getLatitude();
                double lng = addressList.get(0).getLongitude();
                latLng = new LatLng(lat, lng);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(Zoom(position)).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mMap.addMarker(new MarkerOptions().position(latLng).title(Globals.description[position]));
    }

    private void setText() {
        TextView txtDescription = (TextView) findViewById(R.id.txtDescription);
        txtDescription.setText(Globals.description[position]);
        TextView txtLocation = (TextView) findViewById(R.id.txtLocation);
        txtLocation.setText(Globals.location[position]);
    }

    private void setImage() {
        final ImageView image = (ImageView) findViewById(R.id.image);
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Picasso.with(this).load(Globals.link[position]).centerCrop().resize(size.x, 1200).into(image, new Callback() {
            @Override
            public void onSuccess() {
                Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
                if (bitmap != null) {
                    Palette palette = Palette.from(bitmap).generate();
                    int vibrant = palette.getDarkVibrantColor(0x40000000);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        Window window = getWindow();
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        window.setStatusBarColor(vibrant);
                    }
                    LinearLayout linearLayout = (LinearLayout) findViewById(R.id.txtBack);
                    linearLayout.setBackgroundColor(Equations.getColorWithAlpha(vibrant, 0.5f));
                }
            }

            @Override
            public void onError() {

            }
        });
    }
}
