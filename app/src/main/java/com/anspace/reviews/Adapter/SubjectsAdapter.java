package com.anspace.reviews.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.anspace.reviews.CustomListAdapter;
import com.anspace.reviews.Model.SubjectCard;
import com.anspace.reviews.R;
import com.anspace.reviews.card;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SubjectsAdapter extends RecyclerView.Adapter<SubjectsAdapter.ViewHolder> {

    private Context mContext;
    ArrayList<SubjectCard> objects;

    public  SubjectsAdapter(Context context, ArrayList<SubjectCard> objects){

        this.mContext = context;
        this.objects = objects;
        //   this.mResource = resource;
    }

    @NonNull
    @Override
    public SubjectsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.subject_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectsAdapter.ViewHolder holder, int position) {
        holder.subjectName.setText(objects.get(position).getSubjectName());

//        holder.cardView.setBackground(mContext.getResources().getDrawable(R.drawable.round_transparent));

        if (position%2==0)
            holder.cardView.setCardBackgroundColor(Color.parseColor("#D5EBDA"));
        else
            holder.cardView.setCardBackgroundColor(Color.parseColor("#F3E4F1"));

        holder.imageView.setImageDrawable(objects.get(position).getDrawable());
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView subjectName;
        CardView cardView;
        CircleImageView imageView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectName = itemView.findViewById(R.id.subject_name);
            cardView = itemView.findViewById(R.id.card_view);
            imageView = itemView.findViewById(R.id.subject_image);

        }
    }
}