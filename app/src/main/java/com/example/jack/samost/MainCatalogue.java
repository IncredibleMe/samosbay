package com.example.jack.samost;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.Profile;
import com.facebook.login.LoginManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

//import com.j256.ormlite.dao.Dao;
//import com.j256.ormlite.dao.DaoManager;
//import com.j256.ormlite.jdbc.JdbcConnectionSource;
//import com.j256.ormlite.support.ConnectionSource;
//import com.j256.ormlite.table.TableUtils;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;

public class MainCatalogue extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AsyncResponse, RecyclerViewClickListener {

    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private List<Product> itemList;
    private static List<Product> initialItemList;
    private NavigationView navigationView;
    private boolean isLoggedIn;
    private List<Product> filteredList;

    public static List<Product> getInitList(){
        return new ArrayList<>(initialItemList);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_catalogue);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //get the current user authToken
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        isLoggedIn = accessToken != null && !accessToken.isExpired();
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        String userId = "";
        if (isLoggedIn) {
            userId = Profile.getCurrentProfile().getId();

            View hView = navigationView.getHeaderView(0);
            CircleImageView imgvw = (CircleImageView) hView.findViewById(R.id.imageView);
            TextView profileName = (TextView) hView.findViewById(R.id.userProfileName);

            Uri profilePictureUri = Profile.getCurrentProfile().getProfilePictureUri(96, 96);
            Glide.with(this).load(profilePictureUri)
                    .into(imgvw);
            profileName.setText(Profile.getCurrentProfile().getFirstName() + Profile.getCurrentProfile().getMiddleName() + Profile.getCurrentProfile().getLastName());

        } else {
            //if the user is logged out then log him in
            //LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
        }


        try {
            DBConnector.delegate = this;
            DBConnector.connectAndInsert();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        //initCollapsingToolbar();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        itemList = new ArrayList<>();
        initialItemList = new ArrayList<>();
        filteredList = new ArrayList<>();
        adapter = new ItemAdapter(this, itemList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

//        try {
//            Glide.with(this).load(R.drawable.cover).into((ImageView) findViewById(R.id.backdrop));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // Inflate menu to add items to action bar if it is present.
        //inflater.inflate(R.menu.menu_main, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d("SEARCH", s);
                adapter.getFilter().filter(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d("SEARCH", s);
                adapter.getFilter().filter(s);

                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.myprofile) {
            Intent myIntent = new Intent(MainCatalogue.this, UserProfile.class);
            MainCatalogue.this.startActivity(myIntent);

        } else if (id == R.id.home) {
            Log.d("Home", "Home");
            itemList.clear();
            this.itemList.addAll(initialItemList);
            adapter.notifyDataSetChanged();
        } else if (id == R.id.favorite) {

        } else if (id == R.id.watchist) {

        } else if (id == R.id.newProduct) {
            Intent myIntent = new Intent(MainCatalogue.this, AddNewProduct.class);
            MainCatalogue.this.startActivity(myIntent);
        } else if (id == R.id.logOut) {
            if (isLoggedIn) {
                //logout the user
                LoginManager.getInstance().logOut();
            }
            Intent myIntent = new Intent(MainCatalogue.this, MainActivity.class);
            MainCatalogue.this.startActivity(myIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void processFinish(ArrayList<Product> items) {
        this.itemList.addAll(items);
        this.initialItemList.addAll(itemList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        Intent myIntent = new Intent(v.getContext(), ViewProduct.class);
        myIntent.putExtra("viewproduct", itemList.get(position));
        v.getContext().startActivity(myIntent);
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}
