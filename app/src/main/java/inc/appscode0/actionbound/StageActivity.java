package inc.appscode0.actionbound;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
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

public class StageActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
String latitude, longtitude;
    BootstrapButton btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_stage);
        TypefaceProvider.registerDefaultIconSets();
        btn=(BootstrapButton) findViewById(R.id.button6) ;

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(StageActivity.this);

        Intent intent= getIntent();
        String data =intent.getStringExtra("data");


        //Toast.makeText(this, data, Toast.LENGTH_SHORT).show();

        //stage
        String stage_title;
        String stage_cordinate;




        //information

        String information_type;
        String information_text;
        String information_image;
        String information_video;
        String information_audio;
        String information_link;

        //quiz

        String quiz_text;
        String quiz_image;
        String quiz_answer;
        String quiz_hint;

        try {
            JSONObject jsonObject= new JSONObject(data);
            String ssd = jsonObject.getString("status");

            if(ssd.equals("1")) {

                String jssjss=jsonObject.getString("data");
                JSONArray json2 = new JSONArray(jssjss);

                for (int i = 0; i < json2.length(); i++)
                {
                    JSONObject e = json2.getJSONObject(i);
                    String type=e.getString("type");
                    String content=e.getString("content");
                    if(type.equals("stage"))
                    {

                        JSONArray js3= new JSONArray(content);

                        for (int i2 = 0; i2 < js3.length(); i2++)
                        {
                            JSONObject e2 = js3.getJSONObject(i2);
                            stage_title=e2.getString("title");
                            stage_cordinate=e2.getString("coordinate");
                            String cor[]=stage_cordinate.split(",");
                            latitude =cor[0];
                            longtitude=cor[1];
                        }




                        }

                    if(type.equals("information"))
                    {
                       // Toast.makeText(this, type, Toast.LENGTH_SHORT).show();
                    }
                    if(type.equals("quiz"))
                    {
                        //Toast.makeText(this, type, Toast.LENGTH_SHORT).show();
                    }





                }
            }


            } catch (JSONException e) {
            Toast.makeText(this, String.valueOf(e), Toast.LENGTH_SHORT).show();
        }


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });




    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        // Add a marker in Sydney and move the camera
        LatLng TutorialsPoint = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longtitude));

        mMap.addMarker(new
                MarkerOptions().position(TutorialsPoint).title("Tutorialspoint.com"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(TutorialsPoint));
    }




}
