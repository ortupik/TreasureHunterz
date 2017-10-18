package inc.appscode0.actionbound;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Find_a_bound extends AppCompatActivity {
    Button btn;
    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    private Context mContext;
    RecyclerView.LayoutManager layoutManager;
    public List<BoundsData> listitems;
    EditText search;



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == android.R.id.home) {
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypefaceProvider.registerDefaultIconSets();

        setContentView(R.layout.activity_find_a_bound);
        getSupportActionBar().setTitle("Search for a Bound");

        btn=(Button)findViewById(R.id.button);
        search=(EditText)findViewById(R.id.editText4);
        mContext=getApplicationContext();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(search.getText().toString().isEmpty())
                {

//                 TastyToast.makeText(mContext, "Please enter a search string", TastyToast.WARNING, TastyToast.LENGTH_SHORT).show();
                    Toast.makeText(mContext, "Please enter a search string", Toast.LENGTH_SHORT).show();

                }
                else
                search(search.getText().toString());
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


    }
    public void search(String parameter)
    {

        final ProgressDialog sweeetaSweetAlertDialog = new ProgressDialog(Find_a_bound.this);
        sweeetaSweetAlertDialog.setTitle("Please wait");
        sweeetaSweetAlertDialog.setMessage("Processing your request");
        // Instantiate the RequestQueue.
        sweeetaSweetAlertDialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.url+"searchBounds?search_field="+parameter,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        sweeetaSweetAlertDialog.cancel();

                       // Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();

//                        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
                        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);//done here
                        layoutManager = new GridLayoutManager(mContext, 1);
                        recyclerView.setLayoutManager(layoutManager);
                        try {
                            String bound_name;
                            String bound_description;
                            String bound_id;
                            String bound_category;
                            String bounds_length;
                            String bounds_duration;
                            listitems = new ArrayList<>();
                            HashMap<String,String> bounds_data= new HashMap<String, String>();

                            JSONObject json = new JSONObject(response);
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
                                        bound_id=e.getString("id");
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

                                        bounddata = new BoundsData(
                                                bound_name,
                                                bound_description ,
                                                bound_category,
                                                bounds_length,
                                                bounds_duration,
                                                bound_id,
                                                bounds_data, url, play_mode
                                        );
                                        listitems.add(bounddata);
                                        adapter = new BoundsAdapter(listitems, Find_a_bound.this);
                                        recyclerView.setAdapter(adapter);

                                    }
                            }


                            else
                            {
                                new TastyToast().makeText(Find_a_bound.this, "No bound found", TastyToast.LENGTH_LONG,TastyToast.ERROR).show();
                            }



                        }
                        catch (JSONException e)
                        {

                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                sweeetaSweetAlertDialog.cancel();

                try
                {
                   Toast.makeText(mContext, "An error has occurred!",Toast.LENGTH_SHORT).show();
                }
                catch (Exception ex)
                {

                }

            }
        });

        queue.add(stringRequest);

    }

}
