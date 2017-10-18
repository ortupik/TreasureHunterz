package inc.appscode0.actionbound;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

import static inc.appscode0.actionbound.ChooseStage_FinishBound.previous;
import static inc.appscode0.actionbound.ChooseStage_FinishBound.total_points;

public class BoundsAdapter extends RecyclerView.Adapter<BoundsAdapter.ViewHolder>  {
    public static String bound_id;
    private List<BoundsData> listItems;
    private Context context;

    public BoundsAdapter(List<BoundsData> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.bound_layout, parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final BoundsData boundsData =listItems.get(position);

        holder.bound_name.setText(boundsData.getBound_name());
        holder.bound_description.setText(boundsData.getBound_description());
        holder.bound_category.setText(boundsData.getBound_category());
        holder.bounds_length.setText(boundsData.getBounds_length() +" Kilometres");
        holder.bound_distance.setText("Approx "+boundsData.getBounds_duration()+" Minutes");
        //Toast.makeText(context, "sd", Toast.LENGTH_SHORT).show();

        Random r = new Random();
        int Random =10 +  (int)(Math.random()*(91));
        float Rand=0+(float)(Math.random()*6);
        holder.ratingBar.setRating(Rand);

        holder.boundRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(context, boundsData.getBound_id(), Toast.LENGTH_SHORT).show();

                final ProgressDialog progress = new ProgressDialog(context);
                progress.setMessage("preloading treasure...");
                progress.show();
                StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.url+"getPages?bound_id="+boundsData.getBound_id(),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response)
                            {
                                progress.dismiss();
                                previous=0;
                                bound_id=boundsData.getBound_id();
                                total_points=0.0;
//                               Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                                Intent intent= new Intent(context, ChooseStage_FinishBound.class)
                                        .putExtra("bound_name",boundsData.getBound_name())
                                        .putExtra("data",response)
                                        .putExtra("bound_name", boundsData.getBound_name())
                                        .putExtra("bound_id",boundsData.getBound_id())
                                        .putExtra("url",boundsData.getUrl())
                                        .putExtra("single",boundsData.getBound_category())
                                        .putExtra("distance",boundsData.getBounds_length())
                                        .putExtra("durations",boundsData.getBounds_duration())
                                        .putExtra("play_mode", boundsData.getPlay_mode());
                                context.startActivity(intent);


                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progress.dismiss();

                            }
                        });

                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(stringRequest);


            }
        });



try {

    //Toast.makeText(context, boundsData.getUrl(), Toast.LENGTH_SHORT).show();
    Picasso.with(context).load(boundsData.getUrl())
            .into(holder.boundslogo, new com.squareup.picasso.Callback()
            {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError()
                {

                }
            });


}catch (Exception ex)
{

}
    }

    @Override
    public int getItemCount() {
        return  listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView bound_name;
        public TextView bound_description;
        public TextView bound_category;
        public TextView bounds_length;
        public RoundedImageView boundslogo;
        public TextView bound_distance;
        public RatingBar ratingBar;
        public LinearLayout boundRelativeLayout;




        public ViewHolder(View itemView)
        {
            super(itemView);
            bound_name=(TextView)itemView.findViewById(R.id.bootstrapLabel);
            bound_description=(TextView)itemView.findViewById(R.id.textView2);
            boundslogo=(RoundedImageView)itemView.findViewById(R.id.imageView2);
            bound_category=(TextView)itemView.findViewById(R.id.Singleplayer);
            bounds_length=(TextView)itemView.findViewById(R.id.textView51);
            bound_distance=(TextView) itemView.findViewById(R.id.textView3);
            ratingBar=(RatingBar)itemView.findViewById(R.id.ratingBar);
            boundRelativeLayout= (LinearLayout)itemView.findViewById(R.id.boundRelativeLayout);
        }
    }
    {

    }



}


/*

    try {
                            JSONObject jsonObject= new JSONObject(response);
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
//                            stage_title=e2.getString("title");
                                            String stage_cordinate=e2.getString("coordinate");
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
//                            Toast.makeText(this, String.valueOf(e), Toast.LENGTH_SHORT).show();
                        }
 */