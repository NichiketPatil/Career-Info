package com.anspace.reviews.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.anspace.reviews.Model.PostsCard;
import com.anspace.reviews.Model.SubjectCard;
import com.anspace.reviews.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;





public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private Context mContext;
    ArrayList<PostsCard> objects;

    public  PostsAdapter(Context context, ArrayList<PostsCard> objects){

        this.mContext = context;
        this.objects = objects;
        //   this.mResource = resource;
    }

    @NonNull
    @Override
    public PostsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.post_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsAdapter.ViewHolder holder, int position) {
        holder.title.setText(objects.get(position).getTitle());
        holder.description.setText(objects.get(position).getDescription());

//        holder.relativeLayout.setBackground(mContext.getResources().getDrawable(R.drawable.round_transparent));

        if (position%2==0)
            holder.relativeLayout.setCardBackgroundColor(Color.parseColor("#D5EBDA"));
        else
            holder.relativeLayout.setCardBackgroundColor(Color.parseColor("#F3E4F1"));

        holder.imageView.setImageDrawable(objects.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        ImageView imageView;
        CardView relativeLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            imageView = itemView.findViewById(R.id.post_image);
            relativeLayout = itemView.findViewById(R.id.relative_layout);

        }
    }
}