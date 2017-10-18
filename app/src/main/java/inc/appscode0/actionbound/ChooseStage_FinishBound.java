package inc.appscode0.actionbound;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.easyvideoplayer.EasyVideoCallback;
import com.afollestad.easyvideoplayer.EasyVideoPlayer;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.beardedhen.androidbootstrap.BootstrapLabel;
import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.google.zxing.Result;
import com.sdsmdg.tastytoast.TastyToast;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.tiagohm.markdownview.MarkdownView;
import br.tiagohm.markdownview.css.InternalStyleSheet;
import br.tiagohm.markdownview.css.styles.Github;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ChooseStage_FinishBound extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs";
    public static int qrcode = 0;
    public static Double total_points;
    public static String quiz_question,
            quiz_answer,
            quiz_points,
            quiz_solution,
            quiz_answerRequierd, quiz_hint, quiz_image;
    public static String team_namename;
    static String data;
    static String play_mode;
    static int previous, start_node;
    static String bound_name;
    static int length;
    static String bound_id, bid;
    static String url;
    static Fragment fragment;
    SharedPreferences sharedpreferences;
    String[] order;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TypefaceProvider.registerDefaultIconSets();
        setContentView(R.layout.activity_choose_stage__finish_bound);
        Intent intent = getIntent();
        data = intent.getStringExtra("data");
        play_mode=intent.getStringExtra("play_mode");
        bound_id=intent.getStringExtra("bound_id");
        bid=bound_id;
       // start_node = 0;
      //  Toast.makeText(this, "bound id"+ bound_id, Toast.LENGTH_SHORT).show();


        bound_name=intent.getStringExtra("bound_name");

        url=intent.getStringExtra("url");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(bound_name);
        }

        try {

            JSONObject jsonObject = new JSONObject(data);
            String ssd = jsonObject.getString("status");

            if (ssd.equals("1")) {
                String jssjss = jsonObject.getString("data");
                JSONArray json2 = new JSONArray(jssjss);
                length = json2.length();


                if (previous >= length)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChooseStage_FinishBound.this);
                    builder.setTitle("Huuray!")
                            .setMessage("You have successfully completed the game!")
                            .setCancelable(false)
                            .setPositiveButton("Finish", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    previous = 0;

                                    Intent intent =new Intent(getApplicationContext(), ActionBound.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);


                                }
                            })
                            .setNegativeButton("Provide feedback", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Fragment fragment3 = new FragmentFeedback();
                                    FragmentManager fm = getFragmentManager();
                                    FragmentTransaction ft = fm.beginTransaction();
                                    ft.replace(R.id.fragment_place, fragment3);
                                    ft.commit();
                                }
                            });
                    builder.show();

                } else {


                    if (start_node == 0) {
                        Fragment fragment3 = new FragmentStartInformation();
                        Bundle b = new Bundle();
                        b.putString("bound_name",bound_name);
                        b.putString("url",url);
                        fragment3.setArguments(b);

                        FragmentManager fm2 = getFragmentManager();
                        FragmentTransaction ft2 = fm2.beginTransaction();
                        ft2.replace(R.id.fragment_place, fragment3);
                        ft2.commit();
                        start_node=1;

                    } else {

                        JSONObject e = json2.getJSONObject(previous);
                    String type = e.getString("type");
                    String content = e.getString("content");
                    //.makeText(this, String.valueOf(length), Toast.LENGTH_SHORT).show();


                    if (type.equals("information")) {
                        //Toast.makeText(this, type, Toast.LENGTH_SHORT).show();
                        Fragment fr;
                        fr = new FragmentInformation();
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_place, fr);
                        fragmentTransaction.commit();
                    } else if (type.equals("quiz")) {
                        Fragment fragment2 = new FragmentQuestion();
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.fragment_place, fragment2);
                        ft.commit();
                    } else if (type.equals("stage")) {
                        intent = new Intent(this, StartBoundActivity.class);
                        intent.putExtra("data", data);
                        startActivity(intent);
                    } else if (type.equals("mission")) {
                        Fragment fragment2 = new FragmentMission();
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.fragment_place, fragment2);
                        ft.commit();
                    } else if (type.equals("scan_code")) {

                        Fragment fragment2 = new FragmentQRSCANNER();
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.fragment_place, fragment2);
                        ft.commit();
                    }
                }
                }

            }


        } catch (JSONException e) {
            Toast.makeText(this, String.valueOf(e), Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == android.R.id.home) {
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

    public static class FragmentFeedback extends Fragment
    {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {


            View rootView = inflater.inflate(R.layout.feedback, container, false);

            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Feedback");

            AwesomeTextView awesomeTextView=(AwesomeTextView)rootView.findViewById(R.id.textView12);
            awesomeTextView.setText("Did you like it? Give a feedback to the creator of the Bound ");

            final RatingBar ratingBaroverall, ratingBarfun, ratingBarvariety, ratingBarintresting, ratingBardifficult, ratingBareducational;
            ratingBaroverall = (RatingBar) rootView.findViewById(R.id.ratingBar5);
            ratingBarfun = (RatingBar) rootView.findViewById(R.id.ratingBar5);
            ratingBarvariety = (RatingBar) rootView.findViewById(R.id.ratingBar5);
            ratingBarintresting = (RatingBar) rootView.findViewById(R.id.ratingBar5);
            ratingBardifficult = (RatingBar) rootView.findViewById(R.id.ratingBar5);
            ratingBareducational = (RatingBar) rootView.findViewById(R.id.ratingBar5);
            final EditText descEditText = (EditText) rootView.findViewById(R.id.bootstrapEditText);
            BootstrapButton send = (BootstrapButton) rootView.findViewById(R.id.send);


            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getActivity(), bound_id, Toast.LENGTH_SHORT).show();
                    send_date(
                            String.valueOf(bound_id),
                            "",
                            ratingBaroverall.getNumStars(),
                            ratingBarfun.getNumStars(),
                            ratingBarvariety.getNumStars(),
                            ratingBarintresting.getNumStars(),
                            ratingBardifficult.getNumStars(),
                            ratingBareducational.getNumStars(), team_namename

                    );
                }
            });
            return rootView;
        }

        public void send_date(final String boundid,
                              final String feedback,
                              float or,
                              final float funr,
                              final float varietyr,
                              final float intrestingr,
                              final float difficult,
                              final float education,
        final String team_namename) {

            final ProgressDialog progress = new ProgressDialog(getActivity());
            progress.setMessage("Saving your ratings");
            progress.show();

           // Toast.makeText(getActivity(), bound_id, Toast.LENGTH_SHORT).show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.url+"insertRatings",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progress.dismiss();

                            new AlertDialog.Builder(getActivity())
                                    .setTitle("Huuray!")
                                    .setMessage("Game over. Thanks for providing your feedback.")
                                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            previous = 0;
                                            getActivity().finish();
                                            startActivity(new Intent(getActivity(), ActionBound.class));
                                        }
                                    })
                                    .show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progress.dismiss();
                             Toast.makeText(getActivity(), String.valueOf(error), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("bound_id", boundid);
                    params.put("user_id", "1");
                    params.put("fun", Float.toString(funr));
                    params.put("variety", Float.toString(varietyr));
                    params.put("interesting_places", Float.toString(intrestingr));
                    params.put("difficulty", Float.toString(difficult));
                    params.put("educational", Float.toString(education));
                    return params;
                }

            };
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);


            StringRequest stringRequest2 = new StringRequest(Request.Method.POST, Constants.url+"finishedBound",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                           // progress.dismiss();


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                           // progress.dismiss();
                           // Toast.makeText(getActivity(), String.valueOf(error), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("device_id", getDeviceId(getActivity()));
                    params.put("bound_id", bid);
                    params.put("player", team_namename);
                    params.put("points", Double.toString(total_points));
                    return params;
                }

            };
            RequestQueue requestQueue2 = Volley.newRequestQueue(getActivity());
            requestQueue2.add(stringRequest2);


        }

    }

    public static class FragmentRegsterMembers extends Fragment
    {
        BootstrapButton letsgo;
        String ssd;
        AwesomeTextView textView7,textView8;
        BootstrapEditText team_name,team_memberone,team_membertwo,team_member_three;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

    if(play_mode.equals("single_player"))

    {
        View rootView = inflater.inflate(R.layout.activity_start_bounds__play, container, false);
    //    ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle();



        team_name=(BootstrapEditText)rootView.findViewById(R.id.editText) ;
        textView7 =(AwesomeTextView)rootView.findViewById(R.id.textView7);
        textView8 =(AwesomeTextView)rootView.findViewById(R.id.textView8);
        textView7.setText("Hello,Please enter your name");
        textView8.setVisibility(View.GONE);
        team_memberone=(BootstrapEditText)rootView.findViewById(R.id.editText2) ;
        team_memberone.setVisibility(View.GONE);
        team_membertwo=(BootstrapEditText)rootView.findViewById(R.id.editText3) ;
        team_membertwo.setVisibility(View.GONE);
        team_member_three=(BootstrapEditText)rootView.findViewById(R.id.editText5) ;
        team_member_three.setVisibility(View.GONE);
        letsgo=(BootstrapButton)rootView.findViewById(R.id.starttwo);

        letsgo.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v)
           {
               String  teamone, teamtwo;

               team_namename=team_name.getText().toString();
               if(!team_namename.isEmpty())
               {
                  // Toast.makeText(getActivity(), bound_id, Toast.LENGTH_SHORT).show();
                   save_team(team_namename,"", bound_id);
               }
           }
       });


               // Toast.makeText(getActivity(), play_mode, Toast.LENGTH_SHORT).show();
        return rootView;
    }
    else
    {
        View rootView = inflater.inflate(R.layout.activity_start_bounds__play, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Register Members");
      //  Toast.makeText(getActivity(), play_mode, Toast.LENGTH_SHORT).show();


        team_name=(BootstrapEditText)rootView.findViewById(R.id.editText) ;
        textView7 =(AwesomeTextView)rootView.findViewById(R.id.textView7);
        textView8 =(AwesomeTextView)rootView.findViewById(R.id.textView8);
       // textView7.setText("Hello,Please enter your name");
       // textView8.setVisibility(View.GONE);
        team_memberone=(BootstrapEditText)rootView.findViewById(R.id.editText2) ;
        //team_memberone.setVisibility(View.GONE);
        team_membertwo=(BootstrapEditText)rootView.findViewById(R.id.editText3) ;
       // team_membertwo.setVisibility(View.GONE);
        team_member_three=(BootstrapEditText)rootView.findViewById(R.id.editText5) ;
      //  team_member_three.setVisibility(View.GONE);
        letsgo=(BootstrapButton)rootView.findViewById(R.id.starttwo);

        letsgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String team_nam, teamone, teamtwo;

                team_namename=team_name.getText().toString();
                teamone=team_memberone.getText().toString()+","+team_membertwo.getText().toString()+","+team_member_three.getText().toString();

              if(confirm_names(team_namename, teamone))
                  save_team(teamone,team_namename, bound_id);

//                if(!team_nam.isEmpty()||!team_memberone.getText().toString().isEmpty())
//                {
//                    // Toast.makeText(getActivity(), bound_id, Toast.LENGTH_SHORT).show();
//
//                }
//                else {
//                    TastyToast.makeText(getActivity(), "Please fill team name, and at least one team member", TastyToast.WARNING, TastyToast.DEFAULT);
//                }
            }
        });



        return rootView;
    }




        }



        boolean confirm_names(String teamname, String teamtwo)
        {
            boolean isTrue=true;

            if(teamname.isEmpty()||teamname.trim().equals(""))
            {
                isTrue=false;
                Toast.makeText(getActivity(), "Enter team name", Toast.LENGTH_SHORT).show();
            }

            else if(teamtwo.isEmpty()||teamtwo.trim().equals(""))
            {
                isTrue=false;
                Toast.makeText(getActivity(), "Enter at least one team name", Toast.LENGTH_SHORT).show();
            }


            return isTrue;
        }

        public  void save_team(final String player,final String team_name, final String bound_id)
        {

            final ProgressDialog progress = new ProgressDialog(getActivity());
            progress.setMessage("Saving team");
            progress.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.url+"startBound",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            //Yeah

                            progress.dismiss();


                            try {
                                JSONObject json = new JSONObject(response);
                                ssd = json.getString("status");

                                if(ssd.equals("1"))
                                {


                                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                                    alert.setMessage(String.valueOf("Saved successfully"));
                                    alert.setTitle("Hurray!");
                                    alert.setCancelable(false);
                                    alert.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which)
                                        {





    try {

    JSONObject jsonObject = new JSONObject(data);
    ssd = jsonObject.getString("status");

    if (ssd.equals("1")) {
    String jssjss = jsonObject.getString("data");
    JSONArray json2 = new JSONArray(jssjss);
    length = json2.length();


    if (previous >= length)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Huuray!")
                .setMessage("You have successfully completed the game!")
                .setCancelable(false)
                .setPositiveButton("Finish", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        previous = 0;

                        Intent intent =new Intent(getActivity(), ActionBound.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);


                    }
                })
                .setNegativeButton("Provide feedback", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Fragment fragment3 = new FragmentFeedback();
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.fragment_place, fragment3);
                        ft.commit();
                    }
                });
        builder.show();

    }
    else {


        JSONObject e = json2.getJSONObject(previous);
        String type = e.getString("type");
        String content = e.getString("content");
        //.makeText(this, String.valueOf(length), Toast.LENGTH_SHORT).show();



        if (type.equals("information")) {
            //Toast.makeText(this, type, Toast.LENGTH_SHORT).show();
            Fragment fr;
            fr = new FragmentInformation();
            FragmentManager fm = getFragmentManager();
            Bundle b = new Bundle();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_place, fr);
            fragmentTransaction.commit();
        } else if (type.equals("quiz")) {
            Fragment fragment2 = new FragmentQuestion();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragment_place, fragment2);
            ft.commit();
        } else if (type.equals("stage"))
        {
            Intent intent = new Intent(getActivity(), StartBoundActivity.class);
            intent.putExtra("data", data);
            startActivity(intent);
        } else if (type.equals("mission")) {
            Fragment fragment2 = new FragmentMission();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragment_place, fragment2);
            ft.commit();
        } else if (type.equals("scan_code")) {

            Fragment fragment2 = new FragmentQRSCANNER();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragment_place, fragment2);
            ft.commit();
        }
    }

    }


    } catch (JSONException e) {
    Toast.makeText(getActivity(), String.valueOf(e), Toast.LENGTH_SHORT).show();
    }


                                        }
                                    });
                                    alert.show();

                                }
                                else {

                                    TastyToast.makeText(getActivity(), "Action failed!", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
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

                            Toast.makeText(getActivity(), String.valueOf(error), Toast.LENGTH_SHORT).show();



                        }
                    }){
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("bound_id",bound_id);
                    params.put("team",team_name);
                    params.put("player",player);
                    params.put("device_id",getDeviceId(getActivity()));
                    return params;
                }

            };
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);}

    }




    public static class FragmentYoudidIt extends Fragment
    {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.youdidit, container, false);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Congratulations");
            BootstrapButton send = (BootstrapButton) rootView.findViewById(R.id.showresults);

            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Fragment fragmentResults = new FragmentResults();
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.addToBackStack("getback1");
                    ft.replace(R.id.fragment_place, fragmentResults);
                    ft.commit();

                }
            });
            return rootView;
        }


    }
    public static String getDeviceId(Context context)
    {
        String android_id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return android_id;
    }

    public static void mover(final Context context, final FragmentManager fm)
    {

        // Toast.makeText(context, String.valueOf(previous), Toast.LENGTH_SHORT).show();
        if (previous+1 >= length)
        {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Huuray!")
                    .setMessage("You have successfully completed the game!")
                    .setCancelable(false)

                    .setNegativeButton("Continue...", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            Fragment fragment3 = new FragmentFeedback();
                            start_node=0;
                            FragmentTransaction ft = fm.beginTransaction();
                            ft.replace(R.id.fragment_place, fragment3);
                            ft.commit();
                            //  Toast.makeText(context, String.valueOf(total_points), Toast.LENGTH_SHORT).show();
                        }
                    });
            builder.show();





        } else
        {

            String ssd = null;
            try {
                previous = previous + 1;
                JSONObject jsonObject = new JSONObject(data);

                if (previous == length) {
                    //Toast.makeText(getActivity(), "Game over", Toast.LENGTH_SHORT).show();
                }


                ssd = jsonObject.getString("status");


                if (ssd.equals("1")) {

                    String jssjss = jsonObject.getString("data");
                    JSONArray json2 = new JSONArray(jssjss);

                    JSONObject e = json2.getJSONObject(previous);
                    String type = e.getString("type");
                    String content = e.getString("content");


                    if (type.equals("information")) {
                        Fragment fr;
                        fr = new FragmentInformation();
                        Bundle b = new Bundle();
                        b.putString("data",data);
                        fr.setArguments(b);
                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_place, fr);
                        fragmentTransaction.commit();

                    } else if (type.equals("quiz")) {
                        Fragment fragment2 = new FragmentQuestion();
                        FragmentTransaction ft = fm.beginTransaction();
//                                            ft.addToBackStack("sd");
                        ft.replace(R.id.fragment_place, fragment2);
                        ft.commit();
                    }
                    if (type.equals("scan_code")) {
                        Fragment fragment2 = new FragmentQRSCANNER();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.fragment_place, fragment2);
                        ft.commit();

                    } else if (type.equals("stage"))
                    {
                        Intent intent = new Intent(context, StartBoundActivity.class);
                        intent.putExtra("data", data);
                        context.startActivity(intent);
                    } else if (type.equals("mission")) {

                        Fragment fragment2 = new FragmentMission();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.fragment_place, fragment2);
                        ft.commit();
                    }


                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }

    public static class FragmentResults extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {


            View rootView = inflater.inflate(R.layout.showresultss, container, false);

            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("You did it");
            BootstrapButton send = (BootstrapButton) rootView.findViewById(R.id.button3);

            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Fragment fragment3 = new FragmentFeedback();
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.addToBackStack("getback0");
                    ft.replace(R.id.fragment_place, fragment3);
                    ft.commit();
                }
            });
            return rootView;
        }
    }

    public static class FragmentQuestion extends Fragment {

        EditText editText;
        ListView listView;
        MarkdownView question;
        RelativeLayout coordinatorLayout;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.quizlayoutquiz, container, false);

            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Quiz");

            BootstrapButton send = (BootstrapButton) rootView.findViewById(R.id.button7);
            editText = (EditText) rootView.findViewById(R.id.email);

            listView = (ListView) rootView.findViewById(R.id.listview);

            question = (MarkdownView) rootView.findViewById(R.id.textView35);
            InternalStyleSheet css = new Github();
            css.addMedia("screen and (min-width: 1281px)");
            css.addRule("h1", "color: orange");
            css.endMedia();
            question.addStyleSheet(css);
            //http://stackoverflow.com/questions/6370690/media-queries-how-to-target-desktop-tablet-and-mobile

            coordinatorLayout = (RelativeLayout) rootView.findViewById(R.id.relative_layout);

            String answer = editText.getText().toString();
            try {

                JSONObject jsonObject = new JSONObject(data);
                String ssd = jsonObject.getString("status");
                if (ssd.equals("1")) {
                    String jssjss = jsonObject.getString("data");
                    JSONArray json2 = new JSONArray(jssjss);
                    JSONObject e = json2.getJSONObject(previous);
                    String type = e.getString("type");
                    String content;
                    if (type.equals("quiz")) {
                        content = e.getString("content");
                        JSONObject jsonanswerdata = new JSONObject(content);
                        JSONObject jsonanswer = new JSONObject(jsonanswerdata.get("question").toString());
                        final String mode, text, points, answer_required, show_solution, attempts, penalty, time_limit, threshold;
                        text = jsonanswer.get("text").toString();
                        question.loadMarkdown(text);
                        mode = jsonanswer.get("mode").toString();
                        points = jsonanswer.get("points").toString();
                        answer_required = jsonanswer.get("answer_required").toString();
                        show_solution = jsonanswer.get("show_solution").toString();
                        attempts = jsonanswer.get("attempts").toString();
                        penalty = jsonanswer.get("penalty").toString();
                        time_limit = jsonanswer.get("time_limit").toString();
                        threshold = jsonanswer.get("threshold").toString();
                        //

                        JSONObject jsonquestion = new JSONObject(jsonanswerdata.get("answer").toString());


                        switch (mode) {
                            case "Solution Input": {
                                listView.setVisibility(View.GONE);
                                final String solution, correct_answer;
                                solution = jsonquestion.get("solution").toString();
                                correct_answer = jsonquestion.get("correct_answer").toString();

                                send.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {


                                        if (checker()) {
                                            if (!editText.getText().toString().toLowerCase().equals(correct_answer.toLowerCase())) {

                                                Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "You've provided a wrong answer. Hint " + solution, Snackbar.LENGTH_LONG);
                                                snackbar1.show();

                                            } else {

                                                total_points=total_points+Double.parseDouble(points);
                                                final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                                                alert.setMessage("Right answer provided. You have been awarded " + points + " points.");
                                                alert.setTitle("Hurray!");
                                                alert.setCancelable(false);
                                                alert.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        FragmentManager fm = getFragmentManager();
                                                        mover(getActivity(), fm);
                                                    }
                                                });
                                                alert.show();
//API 22: Android 5.1 (Lollipop)

                                            }


                                        }

                                    }
                                });


                                break;
                            }
                            case "Multiple Choice": {

                                final String solution, correct_answer;
                                solution = jsonquestion.get("solution").toString();
                                correct_answer = jsonquestion.get("correct_answer").toString();
                                send.setVisibility(View.GONE);
                                editText.setVisibility(View.GONE);
                                List<String> elephantList = Arrays.asList(solution.split(","));
                                int len = elephantList.size();
                                ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

                                try {

                                    for (int i = 0; i < elephantList.size(); i++)
                                    {

                                        HashMap<String, String> employees = new HashMap<>();
                                        employees.put("soln", elephantList.get(i));
                                        list.add(employees);

                                    }

                                } catch (Exception e2) {

                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                                    alertDialogBuilder.setTitle("Attention!").setMessage(String.valueOf(e2))
                                            .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {

                                                }
                                            })
                                            .setCancelable(true).show();
                                }

                                ListAdapter adapter = new SimpleAdapter(getActivity(), list, R.layout.quiz_listview_layout,
                                        new String[]{"soln"}, new int[]{R.id.checkBox2});
                                listView.setAdapter(adapter);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                                        HashMap<String, String> map = (HashMap) parent.getItemAtPosition(position);
                                        final String soln = map.get("soln");
                                        if (!correct_answer.equals(soln)) {
                                            final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                                            alert.setMessage("Wrong answer provided. Try again.");
                                            alert.setTitle("Error!");
                                            alert.setPositiveButton("Try again", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                }
                                            });
                                            alert.setNegativeButton("Skip.", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    FragmentManager fm = getFragmentManager();
                                                    mover(getActivity(), fm);
                                                }
                                            });

                                            alert.show();
                                        } else
                                            {

                                            total_points=total_points+Double.parseDouble(points);
                                            final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                                            alert.setMessage("Right answer provided. You have been awarded " + points + " points.");
                                            alert.setTitle("Hurray!");
                                            alert.setCancelable(false);
                                            alert.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    FragmentManager fm = getFragmentManager();
                                                    mover(getActivity(), fm);
                                                }
                                            });
                                            alert.show();

                                        }
                                    }
                                });

                                break;
                            }
                            case "Estimate Number":
                            {
                                listView.setVisibility(View.GONE);

                                final String correct_val=jsonquestion.getString("correct_val").toString();
                               final  String max_val=jsonquestion.getString("max_val").toString();
                              final  String min_val=jsonquestion.getString("min_val").toString();
                               final  String deviation=jsonquestion.getString("deviation").toString();


                                send.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {


                                        if (checker()) {
                                            if (!editText.getText().toString().toLowerCase().equals(correct_val.toLowerCase()))
                                            {

//                                                Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "You've provided a wrong answer. Hint solution between " + min_val+ " and "+max_val, Snackbar.LENGTH_LONG);
//                                                snackbar1.show();



                                                final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                                                alert.setMessage("You've provided a wrong answer. Hint solution between " + min_val+ " and "+max_val);
                                                alert.setTitle("Attention!");
                                                alert.setCancelable(false);
                                                alert.setPositiveButton("Try again", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                    }
                                                });
                                                alert.setNegativeButton("Pass!", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        FragmentManager fm = getFragmentManager();
                                                        mover(getActivity(), fm);
                                                    }
                                                });
                                                alert.show();



                                            } else {

                                                total_points=total_points+Double.parseDouble(points);
                                                final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                                                alert.setMessage("Right answer provided. You have been awarded " + points + " points.");
                                                alert.setTitle("Hurray!");
                                                alert.setCancelable(false);
                                                alert.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        FragmentManager fm = getFragmentManager();
                                                        mover(getActivity(), fm);
                                                    }
                                                });
                                                alert.show();
//API 22: Android 5.1 (Lollipop)

                                            }


                                        }

                                    }
                                });
                                break;
                            }
                            case "Sort List":

                                {
                                    final String solution, correct_answer;
                                    solution = jsonquestion.get("solution").toString();
                                    correct_answer = jsonquestion.get("correct_answer").toString();
                                    BootstrapLabel bs =(BootstrapLabel)rootView.findViewById(R.id.textView26);
                                    bs.setText("Sort the List below. Separate list items with a comma ','");
                                listView.setVisibility(View.GONE);
                                send.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {



                                        if (checker()) {
                                            if (!editText.getText().toString().toLowerCase().equals(correct_answer.toLowerCase())) {

                                                Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "You've provided a wrong answer. Hint " + solution, Snackbar.LENGTH_LONG);
                                                snackbar1.show();

                                            } else {
                                                total_points=total_points+Double.parseDouble(points);
                                                final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                                                alert.setMessage("Right answer provided. You have been awarded " + points + " points.");
                                                alert.setTitle("Hurray!");
                                                alert.setCancelable(false);
                                                alert.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        FragmentManager fm = getFragmentManager();
                                                        mover(getActivity(), fm);
                                                    }
                                                });
                                                alert.show();
//API 22: Android 5.1 (Lollipop)

                                            }


                                        }

                                    }
                                });

                                break;
                            }
                        }



                    }


                }


            } catch (JSONException e) {
                Toast.makeText(getActivity(), String.valueOf(e), Toast.LENGTH_LONG).show();
            }


            return rootView;
        }

        boolean checker() {
            boolean istrue = true;

            if (editText.getText().toString().isEmpty()) {
                editText.setError("Please input an answer to continue!");
                istrue = false;
            } else {
                editText.setError(null);
            }

            return istrue;
        }
    }

    public static class FragmentQRSCANNER extends Fragment implements ZXingScannerView.ResultHandler {
        EditText editText;
         String qr_code, text;
        private ZXingScannerView mScannerView;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
/*
{
	"status": 1,
	"data": [
		{
			"type": "scan_code",
			"page_id": 622,
			"content": {
				"qr_code": "Who is the president of the united states",
				"text": "Scan the Qr code below to become a champion champ!!\n![image](http://actionbound.herokuapp.com/image_uploads/bounds/photo4.jpg)"
			}
		}
	],
	"message": "Success"
}
 */


            View rootView = inflater.inflate(R.layout.scannerlayout, container, false);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("QR code scanner");
           // BootstrapLabel bs = (BootstrapLabel) rootView.findViewById(R.id.textView10);

            InternalStyleSheet mStyle = new Github();
            MarkdownView markdownView = (MarkdownView)rootView.findViewById(R.id.markdownView10);
            markdownView.addStyleSheet(mStyle);
            //http://stackoverflow.com/questions/6370690/media-queries-how-to-target-desktop-tablet-and-mobile
            mStyle.addMedia("screen and (min-width: 320px)");
            mStyle.addRule("h1", "color: green");
            mStyle.endMedia();
            mStyle.addMedia("screen and (min-width: 481px)");
            mStyle.addRule("h1", "color: red");
            mStyle.endMedia();
            mStyle.addMedia("screen and (min-width: 641px)");
            mStyle.addRule("h1", "color: blue");
            mStyle.endMedia();
            mStyle.addMedia("screen and (min-width: 961px)");
            mStyle.addRule("h1", "color: yellow");
            mStyle.endMedia();
            mStyle.addMedia("screen and (min-width: 1025px)");
            mStyle.addRule("h1", "color: gray");
            mStyle.endMedia();
            mStyle.addMedia("screen and (min-width: 1281px)");
            mStyle.addRule("h1", "color: orange");
            mStyle.endMedia();




            BootstrapButton scan_now = (BootstrapButton) rootView.findViewById(R.id.button12);
            BootstrapButton skip = (BootstrapButton) rootView.findViewById(R.id.button13);
            mScannerView = new ZXingScannerView(getActivity());
 try {
                JSONObject jsonObject = null;
                jsonObject = new JSONObject(data);

            String ssd = jsonObject.getString("status");
            if (ssd.equals("1")) {
                String jssjss = jsonObject.getString("data");
                JSONArray json2 = new JSONArray(jssjss);
                JSONObject e = json2.getJSONObject(previous);
                String type = e.getString("type");
                String content;
                if (type.equals("scan_code"))
                {
                    content = e.getString("content");
                    JSONObject jsonanswerdata = new JSONObject(content);

                    text = jsonanswerdata.get("text").toString();
                    markdownView.loadMarkdown(text);
                    qr_code=jsonanswerdata.get("qr_code").toString();



                }}

            } catch (JSONException e)
 {
     Toast.makeText(getActivity(), String.valueOf(e), Toast.LENGTH_SHORT).show();
            }
            scan_now.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                        getActivity().setContentView(mScannerView);
                    } catch (Exception ex) {

                        Toast.makeText(getActivity(), String.valueOf(ex), Toast.LENGTH_SHORT).show();

                    }
                }
            });
            skip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    previous=previous+1;
                    Intent intent = new Intent(getActivity(), ChooseStage_FinishBound.class);
                    intent.putExtra("data", data);
                    startActivity(intent);

                }
            });


            return rootView;
        }

        @Override
        public void onResume() {
            super.onResume();
            // Register ourselves as a handler for scan results.
            mScannerView.setResultHandler(this);
            // Start camera on resume
            mScannerView.startCamera();

            //  new IntentIntegrator(this).initiateScan();
        }

        @Override
        public void onPause() {
            super.onPause();
            // Stop camera on pause
            mScannerView.stopCamera();
        }


        @Override
        public void handleResult(Result result) {

            String returned_test = result.getText();

            if(!returned_test.equals(qr_code))
            {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setMessage("Scanned code doesn't match Game Code!");
                alert.setTitle("Attention!");
                alert.setCancelable(false);
                alert.setPositiveButton("Rescan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {


                        getActivity().setContentView(mScannerView);

                    }
                });
                alert.show();

            }

            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
            alert.setMessage(String.valueOf(returned_test));
            alert.setTitle("Hurray!");
            alert.setCancelable(false);
            alert.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {

                    previous=previous+1;
                    Intent intent = new Intent(getActivity(), ChooseStage_FinishBound.class);
                    intent.putExtra("data", data);
                    startActivity(intent);


                }
            });
            alert.show();


        }
    }

    public static class FragmentMission extends Fragment {
        // in a missionone can provide a solution in terms of a text, upload image, upload video, upload audionor none
        EditText editText;
        CoordinatorLayout coordinatorLayout;
        ImageView imageView5;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.layout_mission_main, container, false);

            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Mission!");

            BootstrapButton send = (BootstrapButton) rootView.findViewById(R.id.button11);
            BootstrapButton choose = (BootstrapButton) rootView.findViewById(R.id.button10);
            imageView5 = (ImageView) rootView.findViewById(R.id.imageView5);


            try {
                JSONObject jsonObject = new JSONObject(data);
                String ssd = jsonObject.getString("status");
                if (ssd.equals("1")) {
                    String jssjss = jsonObject.getString("data");
                    JSONArray json2 = new JSONArray(jssjss);
                    JSONObject e = json2.getJSONObject(previous);
                    String type = e.getString("type");
                    String content;
                    if (type.equals("quiz")) {
                        content = e.getString("content");

//                        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE).setContentText(String.valueOf(content)).show();

                        JSONArray js3 = new JSONArray(content);

                        //  question
                        //         answer
//
                        for (int i2 = 0; i2 < js3.length(); i2++) {
                            JSONObject e2 = js3.getJSONObject(i2);
                            String t = e2.getString("type");
                            if (t.equals("text")) {
                                quiz_question = e2.getString("text");
                            }

                            if (t.equals("image")) {
                                quiz_image = e2.getString("image");
                            }
                        }

                        String answer_data = e.getString("answer_data");
                        js3 = new JSONArray(answer_data);
                        for (int i2 = 0; i2 < js3.length(); i2++) {
                            JSONObject e2 = js3.getJSONObject(i2);
                            String mode = e2.getString("mode");
                            quiz_points = e2.getString("points");
                            quiz_answer = e2.getString("answer");
                            quiz_solution = e2.getString("answer_required");
                            quiz_hint = e2.getString("hint");

                        }


                    }


                }


            } catch (JSONException e) {
                //     new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE).setContentText(String.valueOf(e)).show();
            }

            final int RESULT_LOAD_IMG = 1;


            choose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                    startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
                }
            });


            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (previous + 1 == length) {
                        TastyToast.makeText(getActivity(), "Huuray!! GAME OVER", TastyToast.SUCCESS, TastyToast.LENGTH_LONG).show();
                        Fragment fragment3 = new FragmentFeedback();
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.fragment_place, fragment3);
                        ft.commit();

                    } else {

                        String ssd = null;
                        try {
                            previous = previous + 1;
                            JSONObject jsonObject = new JSONObject(data);

                            if (previous == length) {
                                //Toast.makeText(getActivity(), "Game over", Toast.LENGTH_SHORT).show();
                            }


                            ssd = jsonObject.getString("status");


                            if (ssd.equals("1")) {
                                String jssjss = jsonObject.getString("data");
                                JSONArray json2 = new JSONArray(jssjss);

                                JSONObject e = json2.getJSONObject(previous);
                                String type = e.getString("type");
                                String content = e.getString("content");


                                if (type.equals("information"))
                                {
                                    Fragment fr;
                                    fr = new FragmentInformation();
                                    FragmentManager fm = getFragmentManager();
                                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                                    fragmentTransaction.replace(R.id.fragment_place, fr);
                                    fragmentTransaction.commit();

                                } else if (type.equals("quiz"))
                                {
                                    Fragment fragment2 = new FragmentQuestion();
                                    FragmentManager fm = getFragmentManager();
                                    FragmentTransaction ft = fm.beginTransaction();
//                                            ft.addToBackStack("sd");
                                    ft.replace(R.id.fragment_place, fragment2);
                                    ft.commit();
                                } else if (type.equals("mission")) {
                                    Fragment fragment2 = new FragmentMission();
                                    FragmentManager fm = getFragmentManager();
                                    FragmentTransaction ft = fm.beginTransaction();
                                    ft.replace(R.id.fragment_place, fragment2);
                                    ft.commit();
                                }


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }


                }
            });
            return rootView;
        }

        boolean checker() {
            boolean istrue = true;

            if (editText.getText().toString().isEmpty()) {
                editText.setError("Please input an answer to continue!");
                istrue = false;
            } else {
                editText.setError(null);
            }

            return istrue;
        }

        @Override
        public void onActivityResult(int reqCode, int resultCode, Intent data) {
            super.onActivityResult(reqCode, resultCode, data);


            if (resultCode == RESULT_OK) {
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    imageView5.setImageBitmap(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(getActivity(), "You haven't picked Image", Toast.LENGTH_LONG).show();
            }
        }
    }
    public  static class FragmentInformation extends Fragment implements EasyVideoCallback {

        SeekBar seekBar;
        double startTime = 0;
        ImageView playpause;
        Boolean playing = false;
        MediaPlayer mediaPlayer;
        public  String information_image, information_text;
        public  String quiz_question,
                quiz_answer,
                quiz_points,
                quiz_solution,
                quiz_answerRequierd, quiz_hint, quiz_image;

        private   int previous, start_node;



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.quizlayout1, container, false);
            EasyVideoPlayer player;
            TextView showText;
            ImageView showImage;

            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("INFO");

           // data = getArguments().getString("data");

            try {
                JSONObject jsonObject = new JSONObject(data);
                String ssd = jsonObject.getString("status");

                if (ssd.equals("1")) {
                    String jssjss = jsonObject.getString("data");
                    JSONArray json2 = new JSONArray(jssjss);

                    LinearLayout linearLayout = (LinearLayout) rootView.findViewById(R.id.primarylayout);
                    RelativeLayout textLayout = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.layout_text, null);
                    RelativeLayout imageLayout = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.layout_image, null);
                    RelativeLayout videolayout = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.layout_video, null);
                    RelativeLayout audio = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.layout_audio, null);


                    player = (EasyVideoPlayer) videolayout.findViewById(R.id.player);

                    MarkdownView markdownView = (MarkdownView) textLayout.findViewById(R.id.markdownView);

                    InternalStyleSheet mStyle = new Github();
                    markdownView.addStyleSheet(mStyle);
                    //http://stackoverflow.com/questions/6370690/media-queries-how-to-target-desktop-tablet-and-mobile
                    mStyle.addMedia("screen and (min-width: 320px)");
                    mStyle.addRule("h1", "color: green");
                    mStyle.endMedia();


                    //  showText = (TextView) textLayout.findViewById(R.id.textView50);
                    showImage = (ImageView) imageLayout.findViewById(R.id.imageView);
                    seekBar = (SeekBar) audio.findViewById(R.id.seekBar);
                    playpause = (ImageView) audio.findViewById(R.id.imageView10);
                    mediaPlayer = new MediaPlayer();
                    JSONObject e = json2.getJSONObject(previous);
                    String type = e.getString("type");
                    String content = e.getString("content");

                    if (type.equals("information")) {
                        JSONArray js3 = new JSONArray(content);
                        for (int i2 = 0; i2 < js3.length(); i2++) {

                            JSONObject e2 = js3.getJSONObject(i2);
                            String t = e2.getString("type");
                            if (t.equals("text")) {

                                information_text = e2.getString("text");
                                // Toast.makeText(getActivity(), information_text, Toast.LENGTH_SHORT).show();
                                // showText.setText(information_text);
                                markdownView.loadMarkdown(information_text);
                                linearLayout.addView(textLayout);


                            } else if (t.equals("image")) {


                                information_image = e2.getString("image");

                                Picasso.with(getActivity()).load(information_image)
                                        .into(showImage, new com.squareup.picasso.Callback() {
                                            @Override
                                            public void onSuccess() {

                                            }

                                            @Override
                                            public void onError() {
                                                Toast.makeText(getActivity(), "An error occured while loading the image!", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                linearLayout.addView(imageLayout);

                            } else if (t.equals("video")) {
                                String videourl = "http://192.168.43.180/ariana.mp4";//e2.getString("video");
                                player.setCallback(this);
                                player.setSource(Uri.parse(videourl));
                                linearLayout.addView(videolayout);
                            } else if (t.equals("audio")) {


                                final String audiourl = "http://192.168.43.180/love.mp3";//e2.getString("audio");


                                new Thread(new Runnable() {
                                    public void run() {

                                        try {
                                            String url = audiourl; // your URL here
                                            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                            mediaPlayer.setDataSource(url);
                                            mediaPlayer.prepare(); // might take long! (for buffering, etc)


                                        } catch (IOException e1) {
                                            e1.printStackTrace();
                                        }


                                        playpause.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                if (!playing) {
                                                    mediaPlayer.start();
                                                    playing = true;
                                                    playpause.setImageResource(android.R.drawable.ic_media_pause);
                                                } else if (playing) {
                                                    mediaPlayer.pause();
                                                    playing = false;
                                                    playpause.setImageResource(android.R.drawable.ic_media_play);
                                                } else if (mediaPlayer == null) {

                                                    TastyToast.makeText(getActivity(), "Media not ready", TastyToast.LENGTH_SHORT, TastyToast.WARNING).show();

                                                }


                                            }
                                        });


                                        int pos = 0;
                                        seekBar.setMax(mediaPlayer.getDuration());
                                        while (mediaPlayer != null) {
                                            try {

                                                Thread.sleep(1000);
                                                pos = mediaPlayer.getCurrentPosition();

                                            } catch (Exception e1) {
                                                Toast.makeText(getActivity(), String.valueOf(e1), Toast.LENGTH_SHORT).show();
                                            }
                                            seekBar.setProgress(pos);
                                        }

                                    }
                                }).start();


                                linearLayout.addView(audio);

                            }


                        }

                    }
                    if (type.equals("quiz")) {

                        content = e.getString("content");

                        JSONArray js3 = new JSONArray(content);
                        for (int i2 = 0; i2 < js3.length(); i2++) {
                            JSONObject e2 = js3.getJSONObject(i2);
                            String t = e2.getString("type");
                            if (t.equals("text")) {
                                quiz_question = e2.getString("text");
                            }

                            if (t.equals("image")) {
                                quiz_image = e2.getString("image");
                            }
                        }

                        String answer_data = e.getString("answer_data");
                        js3 = new JSONArray(answer_data);
                        for (int i2 = 0; i2 < js3.length(); i2++) {
                            JSONObject e2 = js3.getJSONObject(i2);
                            String mode = e2.getString("mode");
                            quiz_points = e2.getString("points");
                            quiz_answer = e2.getString("answer");
                            quiz_solution = e2.getString("answer_required");
                            quiz_hint = e2.getString("hint");

                            //   Toast.makeText(this,quiz_hint, Toast.LENGTH_SHORT).show();

                        }


                    }


                }


            } catch (JSONException e) {
                Toast.makeText(getActivity(), String.valueOf(e), Toast.LENGTH_SHORT).show();
            }
            BootstrapButton send = (BootstrapButton) rootView.findViewById(R.id.button2);
            send.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {

                    previous = previous + 1;
                    FragmentManager fm =getFragmentManager();
                      mover(getActivity(),fm );



                }
            });


            return rootView;
        }

        @Override
        public void onStarted(EasyVideoPlayer player) {

        }

        @Override
        public void onPaused(EasyVideoPlayer player) {

        }

        @Override
        public void onPreparing(EasyVideoPlayer player) {

        }

        @Override
        public void onPrepared(EasyVideoPlayer player) {

        }

        @Override
        public void onBuffering(int percent) {

        }

        @Override
        public void onError(EasyVideoPlayer player, Exception e) {

        }

        @Override
        public void onCompletion(EasyVideoPlayer player) {

        }

        @Override
        public void onRetry(EasyVideoPlayer player, Uri source) {

        }

        @Override
        public void onSubmit(EasyVideoPlayer player, Uri source) {

        }
    }


}





























