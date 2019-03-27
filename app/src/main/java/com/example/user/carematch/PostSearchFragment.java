package com.example.user.carematch;
//jason
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class PostSearchFragment extends android.support.v4.app.Fragment {

    private View PostListview;
    private static final String TAG ="FireLog";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView mMainList;
    private PostListAdapter PostListAdapter;
    private List<Post> PostList;

    //分類
    private TabLayout tabLayout;
    private ViewPager firstViewPager;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        PostListview = inflater.inflate(R.layout.fragment_post_search, container, false);


        firstViewPager = (ViewPager) PostListview.findViewById(R.id.viewpager_content);
        // Set Tabs inside Toolbar
        tabLayout = (TabLayout) PostListview.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(firstViewPager);
        setupViewPager(firstViewPager);


//        PostList = new ArrayList<>();
//        PostListAdapter = new PostListAdapter(getApplicationContext(),PostList);
//        //取得RecylerView物件，設定佈局及adapter
//        mMainList = (RecyclerView) PostListview.findViewById(R.id.post_list);
//        mMainList.setHasFixedSize(true);
//        mMainList.setLayoutManager(new LinearLayoutManager(getActivity()));
//        mMainList.setAdapter(PostListAdapter);
//
//        db.collection("Post").orderBy("Post_title").addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
//                if (e != null) {
//
//                    Log.d(TAG, "Error :" + e.getMessage());
//                } else {
//                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
//
//                        if (doc.getType() == DocumentChange.Type.ADDED) {
//
//                            String post_id = doc.getDocument().getId();
//
//                            Post post = doc.getDocument().toObject(Post.class).withId(post_id);//抓ID
//                            PostList.add(post);
//                            PostListAdapter.notifyDataSetChanged();
//
//                        }
//
//                    }
//
//
//                }
//
//            }
//        });

        return PostListview;
    }

    //收藏分類
    private void setupViewPager(ViewPager viewPager) {


        Adapter adapter = new Adapter(getChildFragmentManager());
        adapter.addFragment(new Reading1Fragment(), "最新文章");
        adapter.addFragment(new Reading2Fragment(), "熱門文章");
        adapter.addFragment(new Reading3Fragment(), "CM推薦文章");
        viewPager.setAdapter(adapter);


    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}



