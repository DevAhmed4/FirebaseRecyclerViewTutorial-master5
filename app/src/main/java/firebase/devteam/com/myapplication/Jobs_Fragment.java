package firebase.devteam.com.myapplication;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class Jobs_Fragment extends android.app.Fragment {

    private EmptyRecyclerView mRecyclerView;
    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<News, NewsActivity.NewsViewHolder> mPeopleRVAdapter;
    Dialog mDialog;
    public Jobs_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_jobs_, container, false);
        //Initialize dialog fragment
        mDialog = new Dialog(getActivity());
        //==========================================================================================
        //"News" here will reflect what you have called your database in Firebase.
        mDatabase = FirebaseDatabase.getInstance().getReference().child("News");
        mDatabase.keepSynced(true);

        mRecyclerView = (EmptyRecyclerView) v.findViewById(R.id.myRecycleView);

        DatabaseReference personsRef = FirebaseDatabase.getInstance().getReference().child("News");

        Query personsQuery = FirebaseDatabase.getInstance().getReference("/News").limitToLast(100000);

        mRecyclerView.hasFixedSize();
        View EmptyView = v.findViewById(R.id.simpleProgressBar);
        mRecyclerView.setEmptyView(EmptyView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        FirebaseRecyclerOptions personsOptions = new FirebaseRecyclerOptions.Builder<News>().setQuery(personsQuery,News.class).build();

        mPeopleRVAdapter = new FirebaseRecyclerAdapter<News, NewsActivity.NewsViewHolder>(personsOptions) {
            @Override
            protected void onBindViewHolder(NewsActivity.NewsViewHolder holder, final int position, final News model) {
                holder.setTitle(model.getTitle());
                holder.setDesc(model.getDesc());
                holder.setImage(getActivity(), model.getImage());
                holder.setPublishDate(model.getPublishDate());
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String url = model.getUrl();
                        //==========================================================================
                        mDialog.setContentView(R.layout.custom_fragment);
                        TextView txtcllose = (TextView)mDialog.findViewById(R.id.txtclose);
                        TextView title_fragment = (TextView)mDialog.findViewById(R.id.title_fragment);
                        TextView desc_fragment = (TextView)mDialog.findViewById(R.id.desc_fragment);
                        ImageView image_fragment = (ImageView)mDialog.findViewById(R.id.image_fragment);
                        Button btnFollow = (Button)mDialog.findViewById(R.id.btnfollow);
                        title_fragment.setText(model.getTitle());
                        desc_fragment.setText(model.getDesc());

                        Picasso.with(getActivity()).load(model.getImage()).into(image_fragment);
                        //Listener to see more detailes
                        btnFollow.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            Intent i = new Intent(getActivity(),NewsWebView.class);
                            i.putExtra("id",url);
                            startActivity(i);
                            }
                        });
                        //Listener to close the fragment
                        txtcllose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mDialog.dismiss();
                            }
                        });
                        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        mDialog.show();
                        //==========================================================================


                    }
                });
            }

            @Override
            public NewsActivity.NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.news_row, parent, false);

                return new NewsActivity.NewsViewHolder(view);
            }
        };


        mRecyclerView.setAdapter(mPeopleRVAdapter);
        // Inflate the layout for this fragment
        return v;

    }

    @Override
    public void onStart() {
        super.onStart();
        mPeopleRVAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPeopleRVAdapter.stopListening();


    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public NewsViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setTitle(String title) {
            TextView post_title = (TextView) mView.findViewById(R.id.post_title);
            post_title.setText(title);
        }

        public void setDesc(String desc) {

            TextView post_desc = (TextView) mView.findViewById(R.id.post_desc);
            String used_string = desc.substring(0,70);
            post_desc.setText(desc);
        }

        public void setImage(Context ctx, String image,ImageView imageView) {
            if(TextUtils.isEmpty(image)){
                image = "https://www.shareicon.net/download/2017/05/24/886420_job_512x512.png";
            }
            ImageView post_image = (ImageView) mView.findViewById(imageView.getId());
            Picasso.with(ctx).load(image).placeholder(R.drawable.jobbag).into(post_image, new Callback() {
                @Override
                public void onSuccess() {
                    //Image loaded successfully
                }

                @Override
                public void onError() {
                    //Image failed to load
                }
            });


        }
    }

    //==========================================================================================

}
