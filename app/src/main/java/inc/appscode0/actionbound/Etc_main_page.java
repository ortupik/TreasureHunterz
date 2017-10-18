package inc.appscode0.actionbound;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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

public class Etc_main_page extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static  SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypefaceProvider.registerDefaultIconSets();
        setContentView(R.layout.activity_etc_main_page);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Intent intent = getIntent();
        String action = intent.getStringExtra("action");
      //  Toast.makeText(this, action, Toast.LENGTH_SHORT).show();



        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        if(action.equals("settings"))
        {
            Fragment fr;
            fr= new Etc_main_page.FragmentSettings();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_place, fr);
            fragmentTransaction.commit();
        }

        else

        if(action.equals("info"))
        {
            Fragment fr;
            fr= new Etc_main_page.FragmentInfo();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_place, fr);
            fragmentTransaction.commit();
        }
        else
        if(action.equals("location"))
        {
            Fragment fr;
            fr= new Etc_main_page.FragmentNearby();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_place, fr);
            fragmentTransaction.commit();
        }

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == android.R.id.home) {
            finish();

        }
        return super.onOptionsItemSelected(item);
    }


    public static class FragmentSettings extends Fragment
    {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {


            View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Settings");
            final BootstrapEditText usernamebootstrapEditText= (BootstrapEditText)rootView.findViewById(R.id.editText10);
            final BootstrapEditText passwordbootstrapEditText= (BootstrapEditText)rootView.findViewById(R.id.editText11);
           final BootstrapButton bootstrapButton= (BootstrapButton)rootView.findViewById(R.id.button8);
            final BootstrapButton bootstrapButtonsignup= (BootstrapButton)rootView.findViewById(R.id.button9);
            final BootstrapButton logout=(BootstrapButton)rootView.findViewById(R.id.button14);
            logout.setVisibility(View.GONE);
            final TextView loggedinas=(TextView)rootView.findViewById(R.id.textView50);
            String is_logged_in = sharedpreferences.getString("logged_in","false");
            loggedinas.setVisibility(View.GONE);
            if(is_logged_in.equals("true"))
            {
                usernamebootstrapEditText.setVisibility(View.GONE);
                passwordbootstrapEditText.setVisibility(View.GONE);
                bootstrapButton.setVisibility(View.GONE);
                bootstrapButtonsignup.setVisibility(View.GONE);
                logout.setVisibility(View.VISIBLE);
                loggedinas.setVisibility(View.VISIBLE);
                String username=sharedpreferences.getString("username", "username");
                loggedinas.setText("You are logged in as "+username);
            }

            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("logged_in", "false");
                    editor.putString("username", "null");
                    editor.putString("user_id", "null");
                    editor.putString("profile_photo", "null");
                    editor.commit();
                    getActivity().finish();
                }
            });

            bootstrapButtonsignup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String url = Constants.url+"register";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);

                }
            });

            bootstrapButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    final String username=usernamebootstrapEditText.getText().toString();
                     final String password=passwordbootstrapEditText.getText().toString();





                    final ProgressDialog progress = new ProgressDialog(getActivity());
                    progress.setMessage("Signing you in");
                    progress.show();
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.url+"loginUser",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    progress.dismiss();

                                  //  Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();

                                    try {
                                        JSONObject json = new JSONObject(response);
                                        String ssd = json.getString("success");

                                        if (ssd.equals("0"))
                                        {

                                            TastyToast.makeText(getActivity(), "Login failed. Please try again", TastyToast.LENGTH_SHORT, TastyToast.ERROR).show();
                                        } else
                                        {
                                            String username = json.getString("username");
                                            String user_id = json.getString("user_id");
                                            String profile_photo = json.getString("profile_photo");


                                            SharedPreferences.Editor editor = sharedpreferences.edit();
                                            editor.putString("logged_in", "true");
                                            editor.putString("username", username);
                                            editor.putString("user_id", user_id);
                                            editor.putString("profile_photo", profile_photo);
                                            editor.commit();

                                            usernamebootstrapEditText.setVisibility(View.GONE);
                                            passwordbootstrapEditText.setVisibility(View.GONE);
                                            bootstrapButton.setVisibility(View.GONE);
                                            bootstrapButtonsignup.setVisibility(View.GONE);
                                            logout.setVisibility(View.VISIBLE);
                                            loggedinas.setVisibility(View.VISIBLE);
                                            loggedinas.setText("You are logged in as "+username);

                                        }


                                    } catch (JSONException e)
                                    {
                                       // Toast.makeText(getActivity(), String.valueOf(e), Toast.LENGTH_SHORT).show();
                                    }

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    progress.dismiss();

                                    Toast.makeText(getActivity(), String.valueOf(error), Toast.LENGTH_SHORT).show();

                                }
                            }){
                        protected Map<String,String> getParams(){
                            Map<String,String> params = new HashMap<String, String>();
                            params.put("email",username);
                            params.put("password",password);
                            return params;
                        }

                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                    requestQueue.add(stringRequest);
                }
            });

            return rootView;
        }
    }


    public static class FragmentInfo extends Fragment
    {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {


            View rootView = inflater.inflate(R.layout.fragment_info, container, false);

            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Info");

            return rootView;
        }
    }


    public static class FragmentNearby extends Fragment
    {
        Double latitude,longtitude;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {


            View rootView = inflater.inflate(R.layout.fragmentbounds_nearby, container, false);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Nearby Treasures");





            return rootView;
        }



    }


}
