package inc.appscode0.actionbound;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class StartBoundsActivity_Play extends AppCompatActivity
{
BootstrapButton letsgo;
BootstrapEditText team_name,team_memberone,team_membertwo,team_member_three;
    String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypefaceProvider.registerDefaultIconSets();
        setContentView(R.layout.activity_start_bounds__play);


        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }



        Intent intent = getIntent();
        final String bound_name=intent.getStringExtra("bound_name");
        data=intent.getStringExtra("data");
        getSupportActionBar().setTitle(bound_name);




        team_name=(BootstrapEditText)findViewById(R.id.editText) ;
        team_memberone=(BootstrapEditText)findViewById(R.id.editText2) ;
        team_membertwo=(BootstrapEditText)findViewById(R.id.editText3) ;
        team_member_three=(BootstrapEditText)findViewById(R.id.editText5) ;



        letsgo=(BootstrapButton)findViewById(R.id.starttwo);
        letsgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String team_nam, teamone, teamtwo;

                team_nam=team_name.getText().toString();
                teamone=team_memberone.getText().toString();

               if(confirm_names(team_nam, teamone))
               {
                   save_team(team_name.getText().toString(),
                           team_memberone.getText().toString(),
                           team_membertwo.getText().toString(),
                           team_member_three.getText().toString(),
                           bound_name);
               }



            }
        });





    }

    boolean confirm_names(String teamname, String teamtwo)
    {
        boolean isTrue=true;

        if(teamname.isEmpty()||teamname.trim().equals(""))
        {
            isTrue=false;
            Toast.makeText(this, "Enter team name", Toast.LENGTH_SHORT).show();
        }

       else if(teamtwo.isEmpty()||teamtwo.trim().equals(""))
        {
            isTrue=false;
            Toast.makeText(this, "Enter at least one team name", Toast.LENGTH_SHORT).show();
        }


        return isTrue;
    }
    public  void save_team(final String team_name, final String team_name1, final String team_name2, final String team_name3, final String bound_id)
    {

        final ProgressDialog progress = new ProgressDialog(StartBoundsActivity_Play.this);
        progress.setMessage("Saving team");
        progress.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://actionbound.herokuapp.com/insertTeam",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //Yeah

                        progress.dismiss();


                        try {
                            JSONObject json = new JSONObject(response);
                            String ssd = json.getString("status");

                            if(ssd.equals("1"))
                            {
                                TastyToast.makeText(StartBoundsActivity_Play.this, "Saved successfully", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                                startActivity(new Intent(StartBoundsActivity_Play.this, ChooseStage_FinishBound.class)
                                        .putExtra("data",data)
                                );


                            }
                            else {

                                TastyToast.makeText(StartBoundsActivity_Play.this, "Action failed!", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress.dismiss();

                        Toast.makeText(StartBoundsActivity_Play.this, String.valueOf(error), Toast.LENGTH_SHORT).show();



                    }
                }){
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("bound_id",bound_id);
                params.put("team",team_name);
                params.put("member1",team_name1);
                params.put("member2", team_name2);
                params.put("member3",team_name3);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(StartBoundsActivity_Play.this);
        requestQueue.add(stringRequest);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == android.R.id.home)
        {
            finish();

        }
        return super.onOptionsItemSelected(item);
    }
    }
