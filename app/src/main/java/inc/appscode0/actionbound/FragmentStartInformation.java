package inc.appscode0.actionbound;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapLabel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chris on 11/10/2017.
 */

public  class FragmentStartInformation extends Fragment {

    private ImageView holder;
    private static String bound_name;
    private static int length;
    private static String bound_id, bid;
    private static String url;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final  View rootView = inflater.inflate(R.layout.bound_info, container, false);
        BootstrapLabel bs=(BootstrapLabel)rootView.findViewById(R.id.bound_name) ;

        Bundle b = getArguments();
        bound_name = b.getString("bound_name");
        bs.setText(bound_name);

        String url = b.getString("url");

        holder=(ImageView)rootView.findViewById(R.id.imageView11);
        Picasso.with(getActivity()).load(url)
                .into(holder, new com.squareup.picasso.Callback()
                {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError()
                    {

                    }
                });

        BootstrapButton send = (BootstrapButton) rootView.findViewById(R.id.send);


        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Fetching ratings");
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        url = Constants.url+"getRatings";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                progressDialog.cancel();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");

                    if(status.equals("1")) {

                        String data = jsonObject.getString("data");
                        JSONArray json2 = new JSONArray(data);


                        String fun="",
                                overall_rating="",
                                variety="",
                                interesting_places= "",
                                educational= "",
                                difficulty= "";



                        for (int i = 0; i < json2.length(); i++)
                        {
                            JSONObject e = json2.getJSONObject(i);
                            fun= e.getString("fun");
                            overall_rating= e.getString("overall_rating");
                            variety= e.getString("variety");
                            interesting_places= e.getString("interesting_places");
                            educational= e.getString("educational");
                            difficulty= e.getString("difficulty");


                        }

                        final RatingBar ratingBaroverall, ratingBarfun, ratingBarvariety, ratingBarintresting, ratingBardifficult, ratingBareducational;

                        if(!overall_rating.equals(null))
                        {
                            ratingBaroverall = (RatingBar) rootView.findViewById(R.id.ratingBar5);
                            ratingBaroverall.setRating(Integer.parseInt(overall_rating));
                        }

                        if(!fun.equals(null))
                        {
                            ratingBarfun = (RatingBar) rootView.findViewById(R.id.ratingBar6);
                            ratingBarfun.setRating(Integer.parseInt(fun));
                        }


                        if(!variety.equals(null))
                        {
                            ratingBarvariety = (RatingBar) rootView.findViewById(R.id.ratingBar7);
                            ratingBarvariety.setRating(Integer.parseInt(variety));
                        }

                        if(!interesting_places.equals(null))
                        {
                            ratingBarintresting = (RatingBar) rootView.findViewById(R.id.ratingBar8);
                            ratingBarintresting.setRating(Integer.parseInt(interesting_places));
                        }

                        if(!difficulty.equals(null)) {
                            ratingBardifficult = (RatingBar) rootView.findViewById(R.id.ratingBar10);
                            ratingBardifficult.setRating(Integer.parseInt(difficulty));
                        }

                        if(!educational.equals(null))
                        {
                            ratingBareducational = (RatingBar) rootView.findViewById(R.id.ratingBar9);
                            ratingBareducational.setRating(Integer.parseInt(educational));
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.cancel();

            }
        })

        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("bound_id", bound_id);
                return params;
            }
        };

        queue.add(postRequest);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment3 = new ChooseStage_FinishBound.FragmentRegsterMembers();
                FragmentManager fm2 = getFragmentManager();
                FragmentTransaction ft2 = fm2.beginTransaction();
                ft2.replace(R.id.fragment_place, fragment3);
                ft2.commit();
            }
        });


        return rootView;
    }


}

