package com.anspace.reviews;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.anspace.reviews.Model.Writer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;


public class CustomListAdapter extends RecyclerView.Adapter<CustomListAdapter.ViewHolder> {

     String TAG = "CustomListAdapter";
     private Context mContext;
     //int mResource;
     int lastPosition = -1;
     DatabaseReference reference;
     FirebaseUser fuser;
     ArrayList<String> mPhoneNumber;
     ArrayList<card> objects;


    public  CustomListAdapter(Context context, int resource, ArrayList<card> objects){

        this.mContext = context;
        this.objects = objects;
     //   this.mResource = resource;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

        @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        //get the persons information
        String title = objects.get(position).getTitle();
        String imgUrl = objects.get(position).getImgURL();
        String pURL = objects.get(position).getProfile();

        try {

            lastPosition = position;
            holder.title.setText(title);
            Glide.with(mContext)
                    .load(pURL)
                    .error(R.mipmap.ic_launcher_round)
                    .placeholder(R.mipmap.ic_launcher_round)
                    .into(holder.profile_img);

            holder.dialog.setVisibility(View.VISIBLE);
            Glide.with(mContext)
                    .load(imgUrl)
                    .error(R.drawable.handwriting_sample)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.dialog.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.dialog.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(holder.image);



            holder.card_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    fuser = FirebaseAuth.getInstance().getCurrentUser();
                    reference = FirebaseDatabase.getInstance().getReference("Users");
                    mPhoneNumber = new ArrayList<>();

                    reference.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Writer writer = snapshot.getValue(Writer.class);
                                if (writer.getUt().equals("w"))
                                    mPhoneNumber.add(writer.getNo());
                            }

//                            Pair[] pair = new Pair[4];
//                            pair[0] = new Pair<View, String>(holder.image, "shared_card");
//                            pair[1] = new Pair<View, String>(holder.title, "shared_location");
//                            pair[2] = new Pair<View, String>(holder.ratingBar, "shared_rating");
//                            pair[3] = new Pair<View, String>(holder.profile_img, "shared_prof");

//                            Intent intent = new Intent(mContext, Individual.class);
//
//                            ActivityOptions val = ActivityOptions.makeSceneTransitionAnimation(
//                                    (Activity) mContext, pair// The transition name to be matched in Activity B.
//                            );
//                            intent.putExtra("userID", mPhoneNumber.get(position));
//                            mContext.startActivity(intent, val.toBundle());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });


                }
            });

        } catch (IllegalArgumentException e) {
            Log.e(TAG, "getView: IllegalArgumentException: " + e.getMessage());

        }
    }



    public static class ViewHolder extends  RecyclerView.ViewHolder{
        TextView title;
        ImageView image;
        ProgressBar dialog;
        CardView card_view;
        RatingBar ratingBar;
        ImageView profile_img;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.location);
            image = itemView.findViewById(R.id.cardImage);
            dialog = itemView.findViewById(R.id.cardProgressDialog);
            card_view = itemView.findViewById(R.id.card_view);
            ratingBar = itemView.findViewById(R.id.ratingBarsmall);
            profile_img = itemView.findViewById(R.id.profile_image);

        }
    }


}






