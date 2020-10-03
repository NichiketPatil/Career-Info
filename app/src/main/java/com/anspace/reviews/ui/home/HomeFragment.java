package com.anspace.reviews.ui.home;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anspace.reviews.Adapter.ParentCategoryAdapter;
import com.anspace.reviews.Adapter.PostsAdapter;
import com.anspace.reviews.Adapter.SubjectsAdapter;
import com.anspace.reviews.CustomListAdapter;
import com.anspace.reviews.Model.ParentCategory;
import com.anspace.reviews.Model.PostsCard;
import com.anspace.reviews.Model.SubjectCard;
import com.anspace.reviews.Model.Writer;
import com.anspace.reviews.R;
import com.anspace.reviews.card;
import com.anspace.reviews.scrollingActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {

    RecyclerView recyclerParent;
    RecyclerView recycler_posts;
//    DatabaseReference reference;
//    FirebaseUser fuser;
//    Location location;
//    List<Writer> writerList;
//    ArrayList<SubjectCard> subjectList;
    ArrayList<ParentCategory> parentList;
    ArrayList<PostsCard> postsList;
    SubjectsAdapter subjectsAdapter;
    ParentCategoryAdapter parentCategoryAdapter;
    PostsAdapter postsAdapter;
    FirebaseUser firebaseUser;
    CircleImageView profile_image_view;


    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerParent =  root.findViewById(R.id.recycler_parent);

        recycler_posts =  root.findViewById(R.id.recycler_posts);
        profile_image_view = root.findViewById(R.id.profile_image);

        parentCategoryAdapter = new ParentCategoryAdapter(ParentItemList());

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Glide.with(getContext())
                .load(firebaseUser.getPhotoUrl())
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(profile_image_view);

//
        ((scrollingActivity) getActivity()).getSupportActionBar().setTitle("HOME");
//
//        fuser = FirebaseAuth.getInstance().getCurrentUser();
//        reference = FirebaseDatabase.getInstance().getReference("Users");
//
////        locationList = new ArrayList<>();
////        newLocationList = new ArrayList<>();
//        writerList = new ArrayList<>();
//        if (!fuser.isAnonymous()) {
//            myReference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
//            myReference.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    Writer writer = dataSnapshot.getValue(Writer.class);
//                    assert writer != null;
//                    myLatitude = Double.parseDouble(writer.getLat());
//                    myLongitude = Double.parseDouble(writer.getLon());
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//                }
//            });
//        }

//        list = new ArrayList<>();
//
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                list.clear();
//                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
//                    location = new Location("providerNA");
//                    Writer writer = snapshot.getValue(Writer.class);
//                    assert writer!=null;
//                    assert  fuser!=null;
//                    if (writer.getUt().equals("w")){
////                        location.setLatitude(Double.parseDouble(writer.getLat()));
////                        location.setLongitude(Double.parseDouble(writer.getLon()));
////                        locationList.add(location);
////                        writerList.add(writer);
//                        list.add(new card(writer.getH1URL(),writer.getLoc(getContext()),writer.getpURL()));
//                    }
//                }
////                myLocation = new Location("providerNA");
////                myLocation.setLongitude(myLongitude);
////                myLocation.setLatitude(myLatitude);
////                newLocationList = sortLocations(locationList,myLocation);
//
////                for (int j = 0; j < writerList.size(); j++) {
////                    for (int i = 0; i < newLocationList.size(); i++) {
////                        if (newLocationList.get(j) == locationList.get(i)){
////                            list.add(new card(writerList.get(i).getH1URL(), writerList.get(i).getLoc(getContext()), writerList.get(i).getpURL()));
////                            break;
////                        }
////                    }
////                }
//                if (getContext()!=null){
//                    adapter = new CustomListAdapter(getContext(), R.layout.card_layout, list);
//                    LinearLayoutManager llm = new LinearLayoutManager(getContext());
//                    llm.setOrientation(LinearLayoutManager.VERTICAL);
//                    recyclerView.setLayoutManager(llm);
//                    recyclerView.setAdapter(adapter);}
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {}
//        });
//        subjectList = new ArrayList<>();
        postsList = new ArrayList<>();


//        subjectList.add(new SubjectCard("SUBJECT",getResources().getDrawable(R.drawable.tenth)));
//        subjectList.add(new SubjectCard("SUBJECT",getResources().getDrawable(R.drawable.other_class)));
//        subjectList.add(new SubjectCard("SUBJECT",getResources().getDrawable(R.drawable.twelth)));

//        subjectsAdapter = new SubjectsAdapter(getContext(),subjectList);




        postsList.add(new PostsCard(
                getString(R.string.post_title),
                getString(R.string.description),
                getResources().getDrawable(R.drawable.header_image)));
        postsList.add(new PostsCard(
                getString(R.string.post_title),
                getString(R.string.description),
                getResources().getDrawable(R.drawable.sidenav_bg)));
        postsList.add(new PostsCard(
                getString(R.string.post_title),
                getString(R.string.description),
                getResources().getDrawable(R.drawable.handwriting_sample)));
        postsList.add(new PostsCard(
                getString(R.string.post_title),
                getString(R.string.description),
                getResources().getDrawable(R.drawable.sidenav_bg)));

        postsAdapter = new PostsAdapter(getContext(),postsList);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerParent.setLayoutManager(llm);
        recyclerParent.setAdapter(parentCategoryAdapter);

        LinearLayoutManager llm3 = new LinearLayoutManager(getContext());
        llm3.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_posts.setLayoutManager(llm3);
        recycler_posts.setAdapter(postsAdapter);

        return root;
    }



    private ArrayList<ParentCategory> ParentItemList()
    {
        ArrayList<ParentCategory> itemList
                = new ArrayList<>();

        ParentCategory item
                = new ParentCategory(
                "Title 1",
                ChildItemList());
        itemList.add(item);
        ParentCategory item1
                = new ParentCategory(
                "Title 2",
                ChildItemList());
        itemList.add(item1);
        ParentCategory item2
                = new ParentCategory(
                "Title 3",
                ChildItemList());
        itemList.add(item2);
        ParentCategory item3
                = new ParentCategory(
                "Title 4",
                ChildItemList());
        itemList.add(item3);

        return itemList;
    }

    // Method to pass the arguments
    // for the elements
    // of child RecyclerView
    private ArrayList<SubjectCard> ChildItemList()
    {
        ArrayList<SubjectCard> ChildItemList = new ArrayList<>();

        ChildItemList.add(new SubjectCard("SUBJECT",getResources().getDrawable(R.drawable.tenth)));
        ChildItemList.add(new SubjectCard("SUBJECT",getResources().getDrawable(R.drawable.other_class)));
        ChildItemList.add(new SubjectCard("SUBJECT",getResources().getDrawable(R.drawable.twelth)));

        return ChildItemList;
    }


}