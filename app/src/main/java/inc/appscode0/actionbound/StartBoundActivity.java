package inc.appscode0.actionbound;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapLabel;
import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static inc.appscode0.actionbound.ChooseStage_FinishBound.previous;

public class StartBoundActivity extends AppCompatActivity implements OnMapReadyCallback {
    BootstrapButton startBound;
    BootstrapLabel boundname;
    ImageView image;
    String data;
    TextView singel, category, distane;
    private GoogleMap mMap;
    String bound_name;
    String latitude, longtitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypefaceProvider.registerDefaultIconSets();
        setContentView(R.layout.activity_start_bound);

        Intent intent = getIntent();
        data = intent.getStringExtra("data");

       // Toast.makeText(this, "Previous value  of previous"+String.valueOf(previous), Toast.LENGTH_SHORT).show();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(StartBoundActivity.this);
        String stage_title="";

        try {
            JSONObject jsonObject = new JSONObject(data);
            String ssd = jsonObject.getString("status");
            if (ssd.equals("1")) {

                String jssjss = jsonObject.getString("data");
                JSONArray json2 = new JSONArray(jssjss);
                JSONObject e = json2.getJSONObject(previous);
                String type = e.getString("type");
                String content = e.getString("content");

                        JSONArray js3 = new JSONArray(content);
                        for (int i2 = 0; i2 < js3.length(); i2++) {
                            JSONObject e2 = js3.getJSONObject(i2);
                            stage_title = e2.getString("title");
                            String stage_cordinate = e2.getString("coordinate");
                            String cor[] = stage_cordinate.split(",");
                            latitude = cor[0];
                            longtitude = cor[1];
                        }


            }
        } catch (JSONException e)
        {
//                            Toast.makeText(this, String.valueOf(e), Toast.LENGTH_SHORT).show();
        }
        BootstrapLabel bootstrapLabel2= (BootstrapLabel)findViewById(R.id.bootstrapLabel2);
        bootstrapLabel2.setText("Stage title: "+stage_title);
        previous=previous+1;
        startBound = (BootstrapButton) findViewById(R.id.startstart);
        startBound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(StartBoundActivity.this, ChooseStage_FinishBound.class).
                        putExtra("where", "start")
                        .putExtra("bound_name", bound_name)
                        .putExtra("previous", previous)
                        .putExtra("data", data)

                );
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();

        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        LatLng TutorialsPoint = new LatLng(Double.parseDouble("0.00"), Double.parseDouble("0.00"));
        // Add a marker in Sydney and move the camera
        try {
            TutorialsPoint = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longtitude));
        } catch (Exception ex) {

        }
        mMap.addMarker(new
                MarkerOptions().position(TutorialsPoint).title(bound_name));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(TutorialsPoint));
        mMap.setMinZoomPreference(11);
    }
}
