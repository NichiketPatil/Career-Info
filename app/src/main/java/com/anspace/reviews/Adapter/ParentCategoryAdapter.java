package com.anspace.reviews.Adapter;




import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anspace.reviews.Model.ParentCategory;
import com.anspace.reviews.R;

import java.util.List;


public class ParentCategoryAdapter extends RecyclerView.Adapter<ParentCategoryAdapter.ParentViewHolder> {

    // An object of RecyclerView.RecycledViewPool
    // is created to share the Views
    // between the child and
    // the parent RecyclerViews
    private RecyclerView.RecycledViewPool viewPool  = new RecyclerView.RecycledViewPool();
    private List<ParentCategory> itemList;
    private Context context;

    public ParentCategoryAdapter(List<ParentCategory> itemList)
    {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ParentViewHolder onCreateViewHolder(
            @NonNull ViewGroup viewGroup,
            int i)
    {

        // Here we inflate the corresponding
        // layout of the parent item
        View view = LayoutInflater.from(viewGroup.getContext()).inflate( R.layout.categories_item, viewGroup, false);
        this.context = viewGroup.getContext();

        return new ParentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ParentViewHolder parentViewHolder,
            int position)
    {

        // Create an instance of the ParentItem
        // class for the given position
        ParentCategory parentCategory
                = itemList.get(position);

        // For the created instance,
        // get the title and set it
        // as the text for the TextView
        parentViewHolder
                .ParentItemTitle
                .setText(parentCategory.getCatName());

        // Create a layout manager
        // to assign a layout
        // to the RecyclerView.

        // Here we have assigned the layout
        // as LinearLayout with vertical orientation


        GridLayoutManager layoutManager  = new GridLayoutManager(  parentViewHolder.ChildRecyclerView.getContext(),3);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);

        // Since this is a nested layout, so
        // to define how many child items
        // should be prefetched when the
        // child RecyclerView is nested
        // inside the parent RecyclerView,
        // we use the following method
//        layoutManager
//                .setInitialPrefetchItemCount(
//                        parentCategory
//                                .getSubjectList()
//                                .size());

        // Create an instance of the child
        // item view adapter and set its
        // adapter, layout manager and RecyclerViewPool
        SubjectsAdapter subjectsAdapter= new SubjectsAdapter(context,parentCategory.getSubjectList());
        parentViewHolder
                .ChildRecyclerView
                .setLayoutManager(layoutManager);
        parentViewHolder
                .ChildRecyclerView
                .setAdapter(subjectsAdapter);
        parentViewHolder
                .ChildRecyclerView
                .setRecycledViewPool(viewPool);
    }

    // This method returns the number
    // of items we have added in the
    // ParentItemList i.e. the number
    // of instances we have created
    // of the ParentItemList
    @Override
    public int getItemCount()
    {

        return itemList.size();
    }

    // This class is to initialize
    // the Views present in
    // the parent RecyclerView
    class ParentViewHolder
            extends RecyclerView.ViewHolder {

        private TextView ParentItemTitle;
        private RecyclerView ChildRecyclerView;

        ParentViewHolder(final View itemView){
            super(itemView);

            ParentItemTitle  = itemView.findViewById(R.id.txt1);
            ChildRecyclerView= itemView.findViewById(R.id.recycler_subjects);
        }
    }
}
