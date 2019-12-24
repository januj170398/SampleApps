package com.example.samplegooglemaps;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samplegooglemaps.events.AddressFetchErrorEvent;
import com.example.samplegooglemaps.events.AddressFetchSuccessEvent;
import com.example.samplegooglemaps.events.StartFetchingAddressEvent;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        OnMapReadyCallback,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnCameraChangeListener{

    // TextView width and height
    public static final int TEXTVIEW_WIDTH = 300;
    public static final int TEXTVIEW_HEIGHT = 100;

    // Keys for storing TextView text and lat/lon in a Bundle
    public static final String TEXTVIEW_TEXT_KEY = "textview_text_key";
    public static final String LAT_KEY = "lat_key";
    public static final String LON_KEY = "lon_key";

    // References
    private GoogleMap mGoogleMap;
    private FrameLayout mLayout;
    private TextView mTextView;

    // Tap coordinates
    private LatLng mTapCoordinates;

    // Text for TextView
    private String mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLayout = (FrameLayout) findViewById(R.id.layout);

        // Check if there is saved instance state Bundle
        if (savedInstanceState != null) {
            // Get TextView text and tap coordinates from the Bundle
            mText = savedInstanceState.getString(TEXTVIEW_TEXT_KEY, "");
            double lat = savedInstanceState.getDouble(LAT_KEY, 0);
            double lon = savedInstanceState.getDouble(LON_KEY, 0);

            // Save tap coordinates
            mTapCoordinates = new LatLng(lat, lon);
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // When the map is ready, get reference to it
        mGoogleMap = googleMap;

        // Set listeners
        mGoogleMap.setOnMapClickListener(this);
        mGoogleMap.setOnCameraChangeListener(this);

        // Enable zoom buttons
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);

        // Check if there is text for TextView
        // (which means that TextView previously existed).
        if (mText != null) {
            if (!mText.equals("")) {
                // Add TextView with this text to Layout
                addTextViewToLayout();
            }
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        // Save tap coordinates
        mTapCoordinates = latLng;

        // Remove previously added TextView if exists
        if (mTextView != null) {
            mLayout.removeView(mTextView);
        }

        // Get tap lat/lon
        double lat = latLng.latitude;
        double lon = latLng.longitude;

        // Start fetching address for tap lat/lon
        EventBus.getDefault().post(new StartFetchingAddressEvent(lat, lon));

        // Get TextView text from lat/lon
        DecimalFormat latlngFormat = new DecimalFormat("0.000");
        String latString = latlngFormat.format(lat);
        String lonString = latlngFormat.format(lon);
        mText = latString + ", " + lonString;

        // Add TextView with tap coordinates to layout
        addTextViewToLayout();
    }

    // Create new TextView and add to layout
    private void addTextViewToLayout() {
        mTextView = new TextView(this);
        mTextView.setText(mText);
        setTextViewCoordinates();
        mLayout.addView(mTextView);
    }

    // Set position of TextView on screen
    private void setTextViewCoordinates() {
        // Get screen coordinates from map coordinates
        Projection projection = mGoogleMap.getProjection();
        Point point = projection.toScreenLocation(mTapCoordinates);

        // Check if tap coordinates are in visible area.
        // If not, position TextView at the corresponding edge of screen.

        if (point.x < 0) {
            point.x = 0;
        }

        if (point.y < 0) {
            point.y = 0;
        }

        // Determine maximum screen coordinates as coordinates of
        // the bottom right edge of the layout.
        int maxX = mLayout.getRight();
        int maxY = mLayout.getBottom();

        if (point.x + TEXTVIEW_WIDTH > maxX) {
            point.x = maxX - TEXTVIEW_WIDTH;
        }

        if (point.y + TEXTVIEW_HEIGHT > maxY) {
            point.y = maxY - TEXTVIEW_HEIGHT;
        }

        // Create and set layout parameters
        FrameLayout.LayoutParams layoutParams =
                new FrameLayout.LayoutParams(TEXTVIEW_WIDTH, TEXTVIEW_HEIGHT);
        layoutParams.topMargin = point.y;
        layoutParams.leftMargin = point.x;
        mTextView.setLayoutParams(layoutParams);
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        if (mTextView != null) {
            // If TextView exists, set its new position on screen
            setTextViewCoordinates();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Check if TextView exists
        if (mTextView != null) {
            // Save TextView text and tap coordinates in out state Bundle
            outState.putString(TEXTVIEW_TEXT_KEY, mText);
            outState.putDouble(LAT_KEY, mTapCoordinates.latitude);
            outState.putDouble(LON_KEY, mTapCoordinates.longitude);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // Register to listen to EventBus events
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();

        // Unregister from EventBus
        EventBus.getDefault().unregister(this);
    }

    // Called when StartFetchingAddressEvent is posted to the EventBus.
    // Fetches address for tap coordinates.
    // Runs in background thread not to block the UI by network access.
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onStartFetchingAddressEvent(StartFetchingAddressEvent startFetchingAddressEvent) {
        // Get lat/lon from the event
        double lat = startFetchingAddressEvent.getLat();
        double lon = startFetchingAddressEvent.getLon();

        // Address for coordinates is fetched by Geocoder
        Geocoder geocoder = new Geocoder(this);

        try {
            // Get results from the Geocoder.
            // Network access is executed here.
            List<Address> resultList = geocoder.getFromLocation(lat, lon, 1);

            // Check if there are any results
            if (resultList != null && resultList.size() > 0) {
                // Get first result
                Address resultAddress = resultList.get(0);
                // Get country name
                String addressName = resultAddress.getCountryName();
                // Get locality
                String locality = resultAddress.getLocality();
                // Check if locality exists
                if (locality != null) {
                    // Add locality to the address string
                    addressName = addressName + ", " + locality;
                }
                // Post success event with address string to EventBus
                EventBus.getDefault().post(new AddressFetchSuccessEvent(addressName));
            }
        } catch (Exception e) {
            // In case of exceptions post error event to EventBus
            EventBus.getDefault().post(new AddressFetchErrorEvent());
        }
    }

    // Called when AddressFetchSuccessEvent is posted to EventBus.
    // Runs in the main thread to update UI.
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAddressFetchSuccessEvent(AddressFetchSuccessEvent addressFetchSuccessEvent) {
        // Get address from the event
        String address = addressFetchSuccessEvent.getAddress();

        // Add address to TextView text
        mText = mText + "\n" + address;

        // Update TextView with new text if exists
        if (mTextView != null) {
            mTextView.setText(mText);
        }
    }

    // Called when AddressFetchErrorEvent is posted to EventBus.
    // Runs in the main thread to display Toast with error message.
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAddressFetchErrorEvent(AddressFetchErrorEvent addressFetchErrorEvent) {
        Toast.makeText(this, "Error fetching address", Toast.LENGTH_SHORT).show();
    }
}
