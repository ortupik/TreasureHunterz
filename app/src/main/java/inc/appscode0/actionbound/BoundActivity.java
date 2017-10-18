package inc.appscode0.actionbound;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BoundActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    private Context mContext;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    RecyclerView.LayoutManager layoutManager;
    String actvity_title;
    SharedPreferences sharedPreferences;
    public List<BoundsData> listitems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypefaceProvider.registerDefaultIconSets();
        setContentView(R.layout.activity_bound);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Intent intent = getIntent();
         actvity_title=intent.getStringExtra("activity_name");
        getSupportActionBar().setTitle(actvity_title);
        String search_field=intent.getStringExtra("search_field");

        if(search_field.equals("nearby"))
        {
            String latitude=intent.getStringExtra("latitude");
            String longtitude=intent.getStringExtra("longtitude");
            search_nearby(longtitude,latitude);
        }
        else {

            search(search_field);
            sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            mContext = getApplicationContext();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {

            finish();

        }
        return super.onOptionsItemSelected(item);
    }



    public void search_nearby(String longtitude, String latitude) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading hunts..");
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);

                StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.url+"getNearbyBounds?lat="+latitude+"&long="+longtitude,
                        new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.cancel();
                        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);//done here
                        layoutManager = new GridLayoutManager(mContext, 1);
                        recyclerView.setLayoutManager(layoutManager);
                        try {

                            String bound_name;
                            String bound_id;
                            String bound_description;
                            String bound_category;
                            String bounds_length;
                            String bounds_duration;

                            listitems = new ArrayList<>();
                            HashMap<String,String> bounds_data= new HashMap<String, String>();

                            JSONObject json = new JSONObject(response);
//                            JSONArray jsonArray= json.getJSONArray("status");
                            String ssd = json.getString("status");

                            BoundsData bounddata;

                            if(ssd.equals("1")) {

                                String jssjss=json.getString("data");
                                JSONArray json2 = new JSONArray(jssjss);

                                for (int i = 0; i < json2.length(); i++) {

                                    JSONObject e = json2.getJSONObject(i);

                                    bound_name= e.getString("name");
                                    bound_id = e.getString("id");
                                    bound_description= e.getString("description");
                                    bound_category= e.getString("category");
                                    bounds_length= e.getString("bounds_length");
                                    bounds_duration= e.getString("bounds_duration");
                                    bounds_data.put("bound_description",bound_description);
                                    bounds_data.put("bound_name",bound_name);
                                    bounds_data.put("bound_category",bound_category);
                                    bounds_data.put("bounds_length",bounds_length);
                                    bounds_data.put("bounds_duration",bounds_duration);
                                    String url=e.getString("bound_image");
                                    String play_mode=e.getString("play_mode");

                                    //bounds_duration

                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putString("bound_id", bound_id);
                                    editor.commit();

                                    bounddata = new BoundsData(
                                            bound_name,
                                            bound_description ,
                                            bound_category,
                                            bounds_length,
                                            bounds_duration,
                                            bound_id,
                                            bounds_data,
                                            url, play_mode
                                    );
                                    listitems.add(bounddata);
                                    adapter = new BoundsAdapter(listitems, BoundActivity.this);
                                    recyclerView.setAdapter(adapter);

                                }}


                            else
                            {

                                new TastyToast().makeText(BoundActivity.this, "No bound found", TastyToast.LENGTH_LONG,TastyToast.ERROR).show();

                            }



                        } catch (JSONException e) {
                            // Toast.makeText(mContext, String.valueOf(e), Toast.LENGTH_LONG).show();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(BoundActivity.this, "Server Error!", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(stringRequest);

    }



    public void search(String parameter)
    {


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Processing your request");
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url=Constants.url+"getCategoryBounds?category=";
        if(actvity_title.equals("My Bounds"))
        {
            url= Constants.url+"getMyBounds?user_id=";
        }

        //Toast.makeText(mContext, String.valueOf(url), Toast.LENGTH_SHORT).show();


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url+parameter,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.cancel();

//                        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
                        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);//done here
                        layoutManager = new GridLayoutManager(mContext, 1);
                        recyclerView.setLayoutManager(layoutManager);

                        try {

                             String bound_name;
                             String bound_id;
                             String bound_description;
                             String bound_category;
                             String bounds_length;
                             String bounds_duration;

                            listitems = new ArrayList<>();
                            HashMap<String,String> bounds_data= new HashMap<String, String>();

                            JSONObject json = new JSONObject(response);
//                            JSONArray jsonArray= json.getJSONArray("status");
                            String ssd = json.getString("status");

                            BoundsData bounddata
                                    ;

                            if(ssd.equals("1"))
                            {
                                String jssjss=json.getString("data");
                                JSONArray json2 = new JSONArray(jssjss);



                                    for (int i = 0; i < json2.length(); i++)
                                {
                                    JSONObject e = json2.getJSONObject(i);

                                    bound_name= e.getString("name");
                                    bound_id = e.getString("id");
                                    bound_description= e.getString("description");
                                    bound_category= e.getString("category");
                                    bounds_length= e.getString("bounds_length");
                                    bounds_duration= e.getString("bounds_duration");
                                    bounds_data.put("bound_description",bound_description);
                                    bounds_data.put("bound_name",bound_name);
                                    bounds_data.put("bound_category",bound_category);
                                    bounds_data.put("bounds_length",bounds_length);
                                    bounds_data.put("bounds_duration",bounds_duration);
                                    String url=e.getString("bound_image");
                                    String play_mode=e.getString("play_mode");


                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putString("bound_id", bound_id);
                                    editor.commit();

                                    bounddata = new BoundsData(
                                            bound_name,
                                            bound_description ,
                                            bound_category,
                                            bounds_length,
                                            bounds_duration,
                                            bound_id,
                                            bounds_data,
                                            url, play_mode
                                    );
                                    listitems.add(bounddata);
                                    adapter = new BoundsAdapter(listitems, BoundActivity.this);
                                    recyclerView.setAdapter(adapter);

                                }}


                            else
                            {

new TastyToast().makeText(BoundActivity.this, "No bound found", TastyToast.LENGTH_LONG,TastyToast.ERROR).show();

                            }



                        } catch (JSONException e) {
                  // Toast.makeText(mContext, String.valueOf(e), Toast.LENGTH_LONG).show();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(mContext, String.valueOf(error), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(stringRequest);

    }
}
