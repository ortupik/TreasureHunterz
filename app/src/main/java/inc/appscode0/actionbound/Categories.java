package inc.appscode0.actionbound;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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

import com.sdsmdg.tastytoast.TastyToast;

public class Categories extends AppCompatActivity {


    RecyclerView recyclerView;
    GridView grid;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    RelativeLayout mRelativeLayout;
    private Context mContext;
    Dialog login223 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        getSupportActionBar().setTitle("Categories");

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

                        "SINGLE PLAYER",
                        "MULTIPLAYER",
                        "URBAN",
                        "OUTDOORS",
                        "FUN",
                        "EDUCATIONAL",
                        "QUIZ",
                        "SIGHT SEEING",
                        "EVENTS",
                        "VERIFIED BONDS"

                };

        private String[] details = {
                "Item one details",
                "Item two details", "Item three details",
                "Item four details", "Item file details",
                "Item six details", "Item seven details",
                "Item eight details"};

        private int[] images = {
                R.drawable.single,
                R.drawable.users,
                R.drawable.urban,
                R.drawable.outdoors,
                R.drawable.funfun,
                R.drawable.educational,
                R.drawable.quiz,
                R.drawable.ic_sightseeing,
                R.drawable.events,
                R.drawable.verified
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


                        if (!InternetConnection.checkConnection(Categories.this)) {
                            TastyToast.makeText(Categories.this,"Please enable your data connection", TastyToast.CONFUSING, TastyToast.LENGTH_LONG).show();
                        } else {
                            switch (position)
                            {

//                                String actvity_title=intent.getStringExtra("activity_name");
//                                getSupportActionBar().setTitle(actvity_title);
//                                String search_field=intent.getStringExtra("search_field");
                                case 0:
                                    startActivity(new Intent(Categories.this, BoundActivity.class).putExtra("activity_name","Single Player").putExtra("search_field","single_player"));
                                    break;
                                case 1:
                                    startActivity(new Intent(Categories.this, BoundActivity.class).putExtra("activity_name","Multi Player").putExtra("search_field","multi_player"));
                                    break;
                                case 2:
                                    startActivity(new Intent(Categories.this, BoundActivity.class).putExtra("activity_name","Urban").putExtra("search_field","urban"));
                                    break;
                                case 3:
                                    startActivity(new Intent(Categories.this, BoundActivity.class).putExtra("activity_name","Outdoors").putExtra("search_field","outdoors"));
                                    break;
                                case 4:
                                    startActivity(new Intent(Categories.this, BoundActivity.class).putExtra("activity_name","fun").putExtra("search_field","Fun"));
                                    break;
                                case 5:
                                    startActivity(new Intent(Categories.this, BoundActivity.class).putExtra("activity_name","Educational").putExtra("search_field","educational"));
                                    break;
                                case 6:
                                    startActivity(new Intent(Categories.this, BoundActivity.class).putExtra("activity_name","Quiz").putExtra("search_field","quiz"));
                                    break;
                                case 7:
                                    startActivity(new Intent(Categories.this, BoundActivity.class).putExtra("activity_name","Sight Seeing").putExtra("search_field","sight_seeing"));
                                    break;
                                case 8:
                                    startActivity(new Intent(Categories.this, BoundActivity.class).putExtra("activity_name","Events").putExtra("search_field","events"));
                                    break;
                                case 9:
                                    startActivity(new Intent(Categories.this, BoundActivity.class).putExtra("activity_name","Verified bonds").putExtra("search_field","verified_bonds"));
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
