package inc.appscode0.actionbound;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ActionBound extends AppCompatActivity   implements NavigationView.OnNavigationItemSelectedListener {

    public static SharedPreferences sharedpreferences;
    Double latitude=0.000, longtitude=0.00;
    RecyclerView recyclerView;
    GridView grid;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    RelativeLayout mRelativeLayout;
    private Context mContext;
    Dialog login223;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_action_bound);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getLocation();
            }
        });

        getSupportActionBar().setSubtitle("BETA");
        try {


            mContext = getApplicationContext();
            mRelativeLayout = (RelativeLayout) findViewById(R.id.rl);
            recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            layoutManager = new GridLayoutManager(mContext, 2);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new RecyclerAdapter();

            recyclerView.setAdapter(adapter);
            //grid.setAdapter((ListAdapter) adapter);
        } catch (Exception ex) {
            //Toast.makeText(mContext, String.valueOf(ex), Toast.LENGTH_SHORT).show();
        }


    }

    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
        public static final String MyPREFERENCES = "MyPrefs";
        //        SharedPreferences sharedpreferences;
        private String[] titles = {
                "FIND A BOUND",
                "SCAN CODE",
                "BOUNDS NEARBY",
                "SETTINGS",
                "INFO",
        };

        private String[] details = {
                "Item one details",
                "Item two details", "Item three details",
                "Item four details", "Item file details",
                "Item six details", "Item seven details",
                "Item eight details"
        };

        private int[] images = {
                R.drawable.ic_find_in_page,
                R.drawable.qr,
                R.drawable.loc,
                R.drawable.settings,
                R.drawable.info,
//                R.drawable.legleg
        };


        class ViewHolder extends RecyclerView.ViewHolder {

            public int currentItem;
            public ImageView itemImage;
            public TextView itemTitle;
            public TextView itemDetail;

            public ViewHolder(View itemView) {
                super(itemView);

                itemImage = (ImageView) itemView.findViewById(R.id.item_image);
                itemTitle = (TextView) itemView.findViewById(R.id.item_title);
                // itemDetail = (TextView)itemView.findViewById(R.id.item_detail);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();


                        if (!InternetConnection.checkConnection(ActionBound.this))
                        {
                            TastyToast.makeText(ActionBound.this, "Please enable your data connection", TastyToast.CONFUSING, TastyToast.LENGTH_LONG).show();
                        } else {
                            switch (position) {
                                case 0:
                                    startActivity(new Intent(ActionBound.this, FindBound.class));
                                    break;
                                case 1:
                                    startActivity(new Intent(ActionBound.this, QrScanner.class));
                                    break;
                                case 2:

                                            startActivity(new Intent(ActionBound.this, BoundActivity.class)
                                                    .putExtra("activity_name","Bounds Nearby")
                                                    .putExtra("latitude",latitude)
                                                    .putExtra("longtitude",longtitude)
                                                    .putExtra("search_field","nearby"));



                                    break;

                                case 3:
                                    startActivity(new Intent(ActionBound.this, Etc_main_page.class)
                                            .putExtra("action", "settings")
                                    );
                                    break;
                                case 4:
                                    startActivity(new Intent(ActionBound.this, Etc_main_page.class)
                                            .putExtra("action", "info")
                                    );
                                    break;


                            }
                        }


                    }
                });
            }
        }

        /*
            <View
        android:layout_width="match_parent"
        android:layout_height="1dp"
        android:layout_below="@+id/textView68"
        android:background="@android:color/darker_gray"
        android:id="@+id/view" />
         */

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.mainactivitycardview, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            viewHolder.itemTitle.setText(titles[i]);
            //  viewHolder.itemDetail.setText(details[i]);
            viewHolder.itemImage.setImageResource(images[i]);
        }

        @Override
        public int getItemCount() {
            return titles.length;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_bound, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera)
        {
            startActivity(new Intent(ActionBound.this, FindBound.class));

        } else if (id == R.id.nav_gallery)
        {
            startActivity(new Intent(ActionBound.this, QrScanner.class));

        } else if (id == R.id.nav_slideshow)
        {
            startActivity(new Intent(ActionBound.this, BoundActivity.class)
                    .putExtra("activity_name","Bounds Nearby")
                    .putExtra("latitude",latitude)
                    .putExtra("longtitude",longtitude)
                    .putExtra("search_field","nearby"));


        } else if (id == R.id.nav_manage)
        {
            startActivity(new Intent(ActionBound.this, Etc_main_page.class)
                    .putExtra("action", "settings")
            );

        } else if (id == R.id.nav_share)
        {


        } else if (id == R.id.info)
        {
            startActivity(new Intent(ActionBound.this, Etc_main_page.class)
                    .putExtra("action", "info") );

        }

        /*
         case 0:
                                    startActivity(new Intent(ActionBound.this, FindBound.class));
                                    break;
                                case 1:
                                    startActivity(new Intent(ActionBound.this, QrScanner.class));
                                    break;
                                case 2:

                                            startActivity(new Intent(ActionBound.this, BoundActivity.class)
                                                    .putExtra("activity_name","Bounds Nearby")
                                                    .putExtra("latitude",latitude)
                                                    .putExtra("longtitude",longtitude)
                                                    .putExtra("search_field","nearby"));



                                    break;

                                case 3:
                                    startActivity(new Intent(ActionBound.this, Etc_main_page.class)
                                            .putExtra("action", "settings")
                                    );
                                    break;
                                case 4:
                                    startActivity(new Intent(ActionBound.this, Etc_main_page.class)
                                            .putExtra("action", "info")
                                    );
                                    break;
         */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public String getLocation()
    {
        JSONObject location = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(ActionBound.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return null;
            }
            Location lastKnownLocationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastKnownLocationGPS != null) {

                latitude = lastKnownLocationGPS.getLatitude();
                longtitude = lastKnownLocationGPS.getLongitude();
                try {
                    location.put("latitude", latitude);
                    location.put("longtitude", longtitude);
                    jsonArray.put(location);
                } catch (JSONException js) {

                }


                return String.valueOf(jsonArray);

            } else {

                Location loc = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

                try {
                    latitude = loc.getLatitude();
                    longtitude = loc.getLongitude();
                    location.put("latitude", latitude);
                    location.put("longtitude", longtitude);
                    jsonArray.put(location);


                } catch (Exception ex) {

                }


                return String.valueOf(jsonArray);
            }


        } else {
            return String.valueOf(jsonArray);
        }
    }
}
