package inc.appscode0.actionbound;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;


import com.sdsmdg.tastytoast.TastyToast;

public class FindBound extends AppCompatActivity {


    RecyclerView recyclerView;
    GridView grid;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    RelativeLayout mRelativeLayout;
    private Context mContext;
    Dialog login223 ;
    public static SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_bound);
        getSupportActionBar().setTitle("Find A bound");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        try {


            mContext = getApplicationContext();
            mRelativeLayout = (RelativeLayout) findViewById(R.id.rl);
            //mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            layoutManager = new GridLayoutManager(mContext, 2);
            recyclerView.setLayoutManager(layoutManager);
            // grid.setLayoutManager(layoutManager);
            adapter = new RecyclerAdapter();

            recyclerView.setAdapter(adapter);
            //grid.setAdapter((ListAdapter) adapter);
        } catch (Exception ex) {
            Toast.makeText(mContext, String.valueOf(ex), Toast.LENGTH_SHORT).show();
        }


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == android.R.id.home) {
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>
    {
        public static final String MyPREFERENCES = "MyPrefs";
        //        SharedPreferences sharedpreferences;
        private String[] titles =
                {


                        "SEARCH",
                        "CATEGORIES",
                        "MY BOUNDS",
                        "SECRET BOUNDS",
                        "TOP RATED",
                        "NEW BOUNDS"
                };

        private String[] details = {
                "Item one details",
                "Item two details", "Item three details",
                "Item four details", "Item file details",
                "Item six details", "Item seven details",
                "Item eight details"};

        private int[] images = {
                R.drawable.search,
                R.drawable.categories,
                R.drawable.mybounds,
                R.drawable.lock2,
                R.drawable.star,
                R.drawable.newnew
        };


        class ViewHolder extends RecyclerView.ViewHolder {

            public int currentItem;
            public ImageView itemImage;
            public TextView itemTitle;
            public TextView itemDetail;

            public ViewHolder(View itemView) {
                super(itemView);

                itemImage = (ImageView) itemView.findViewById(R.id.item_image);
                itemTitle = (TextView) itemView.findViewById(R.id.item_title);
                // itemDetail = (TextView)itemView.findViewById(R.id.item_detail);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();

//                        final Intent intent = new Intent(MainActivity.this, Phonnumber.class);
                        // Toast.makeText(MainActivity.this, phone, Toast.LENGTH_SHORT).show();

                        /// rememeber to uncomment this later

                        // Toast.makeText(MainActivity.this, phone, Toast.LENGTH_SHORT).show();
                        if (!InternetConnection.checkConnection(FindBound.this)) {

                            TastyToast.makeText(FindBound.this,"Please enable your data connection", TastyToast.CONFUSING, TastyToast.LENGTH_LONG).show();

                        } else {
                            switch (position)
                            {
                                case 0:
                                    startActivity(new Intent(FindBound.this, Find_a_bound.class));
                                    break;
                                case 1:
                                    startActivity(new Intent(FindBound.this, Categories.class));
                                    break;
                                case 2:

                                  sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

                                  String user_id=sharedpreferences.getString("user_id","null");

                                   // Toast.makeText(FindBound.this, user_id, Toast.LENGTH_SHORT).show();
                                  if(user_id.equals("null"))
                                  {
                                      Toast.makeText(FindBound.this, "Please log in first", Toast.LENGTH_SHORT).show();
                                  }
                                  else
                                  {
                                      startActivity(new Intent(FindBound.this, BoundActivity.class).
                                              putExtra("activity_name","My Bounds").
                                              putExtra("search_field",user_id));
                                  }



                                    break;
                                case 3:
                                    startActivity(new Intent(FindBound.this, Categories.class));
                                    break;
                                case 4:
                                    startActivity(new Intent(FindBound.this, BoundActivity.class).
                                            putExtra("activity_name","Top Rated").
                                            putExtra("search_field","top_rated"));
                                    break;
                                case 5:
                                    startActivity(new Intent(FindBound.this, BoundActivity.class).
                                            putExtra("activity_name","New Bounds").
                                            putExtra("search_field","new"));
                                    break;
                              
                            }
                        }


                    }
                });
            }
        }

        /*
            <View
        android:layout_width="match_parent"
        android:layout_height="1dp"
        android:layout_below="@+id/textView68"
        android:background="@android:color/darker_gray"
        android:id="@+id/view" />
         */

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.mainactivitycardview, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            viewHolder.itemTitle.setText(titles[i]);
            //  viewHolder.itemDetail.setText(details[i]);
            viewHolder.itemImage.setImageResource(images[i]);
        }

        @Override
        public int getItemCount() {
            return titles.length;
        }
    }
}
