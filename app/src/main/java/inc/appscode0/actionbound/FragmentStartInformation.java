package inc.appscode0.actionbound;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

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

import static inc.appscode0.actionbound.BoundsAdapter.bound_id;

/**
 * Created by Chris on 11/10/2017.
 */

public  class FragmentStartInformation extends Fragment {

    private ImageView holder;
    private static String bound_name;
    private static int length;
    private static String  bid;
    private static String url;
    RatingBar ratingBaroverall, ratingBarfun, ratingBarvariety, ratingBarintresting, ratingBardifficult, ratingBareducational;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final  View rootView = inflater.inflate(R.layout.bound_info, container, false);
        BootstrapLabel bs=(BootstrapLabel)rootView.findViewById(R.id.bound_name) ;

        Bundle b = getArguments();
        bound_name = b.getString("bound_name");
        bs.setText(bound_name);




    //   Toast.makeText(getActivity(), String.valueOf(bound_id), Toast.LENGTH_SHORT).show();

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
       // progressDialog.cancel();

        url = Constants.url+"getRatings";
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                progressDialog.cancel();
              //  Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();



                try {

                    JSONObject json = new JSONObject(response);
                    String ssd = json.getString("status");

                    if (ssd.equals("0"))
                    {


                    } else
                    {




                        JSONObject e = json.getJSONObject("data");
                        String fun = e.getString("variety");
                        String variety=e.getString("variety");
                        String interesting_places=e.getString("interesting_places");
                        String difficulty=e.getString("difficulty");
                        String educational=e.getString("educational");
                        String overall_rating=e.getString("overall_rating");


                        if (!overall_rating.equals(null))
                        {
                            ratingBaroverall = (RatingBar) rootView.findViewById(R.id.ratingBar5);
                            ratingBaroverall.setRating(Float.parseFloat(overall_rating));
                        }

                        if (!fun.equals(null)||!fun.isEmpty())
                        {
                            ratingBarfun = (RatingBar) rootView.findViewById(R.id.ratingBar6);
                            ratingBarfun.setRating(Float.parseFloat(fun));
                        }


                        if (!variety.equals(null))
                        {
                            ratingBarvariety = (RatingBar) rootView.findViewById(R.id.ratingBar7);
                            ratingBarvariety.setRating(Float.parseFloat(variety));
                        }

                        if (!interesting_places.equals(null)) {
                            ratingBarintresting = (RatingBar) rootView.findViewById(R.id.ratingBar8);
                            ratingBarintresting.setRating(Float.parseFloat(interesting_places));
                        }

                        if (!difficulty.equals(null)) {
                            ratingBardifficult = (RatingBar) rootView.findViewById(R.id.ratingBar10);
                            ratingBardifficult.setRating(Float.parseFloat(difficulty));
                        }

                        if (!educational.equals(null)) {
                            ratingBareducational = (RatingBar) rootView.findViewById(R.id.ratingBar9);
                            ratingBareducational.setRating(Float.parseFloat(educational));
                        }


                    }



                }
                catch (JSONException e)
                {
                    Toast.makeText(getActivity(), String.valueOf(e), Toast.LENGTH_SHORT).show();
                }
                catch (Exception e)
                {
                    Toast.makeText(getActivity(), String.valueOf(e), Toast.LENGTH_SHORT).show();
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

