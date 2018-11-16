package hoanglong.thesis.graduation.juncomputer.screen.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import hoanglong.thesis.graduation.juncomputer.R;
import hoanglong.thesis.graduation.juncomputer.screen.category.CategoryFragment;
import hoanglong.thesis.graduation.juncomputer.screen.home.homefragment.HomeFragment;
import hoanglong.thesis.graduation.juncomputer.screen.login.LoginActivity;
import hoanglong.thesis.graduation.juncomputer.utils.FragmentTransactionUtils;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // tags used to attach the fragments
    private static final String TAG_HOME = "home";
    private static final String TAG_LOGIN = "login";
    private static final String TAG_CATEGORY = "category";
    private static final String TAG_MOVIES = "movies";
    private static final String TAG_NOTIFICATIONS = "notifications";
    private static final String TAG_SETTINGS = "settings";
    // index to identify current nav menu item
    public static int navItemIndex = 0;
    public static String CURRENT_TAG = TAG_HOME;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        LinearLayout linearLogin = headerView.findViewById(R.id.linear_login);
        linearLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }

    }

    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(CURRENT_TAG);
        switch (CURRENT_TAG) {
            case TAG_HOME:
                if (fragment == null) {
                    fragment = new HomeFragment();
                }
                FragmentTransactionUtils.addFragment(
                        getSupportFragmentManager(),
                        fragment, R.id.frame_home, TAG_HOME,
                        true, -1, -1);
                break;
            case TAG_CATEGORY:
                if (fragment == null) {
                    fragment = new CategoryFragment();
                }
                FragmentTransactionUtils.addFragment(
                        getSupportFragmentManager(),
                        fragment, R.id.frame_home, TAG_CATEGORY,
                        true, -1, -1);
                break;
        }

        drawer.closeDrawers();
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        loadHomeFragment();
                        break;
                    case R.id.nav_category:
                        drawer.closeDrawers();
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_CATEGORY;
                        loadHomeFragment();
                        break;
                    case R.id.nav_order:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_MOVIES;
                        break;
                    case R.id.nav_favorites:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_NOTIFICATIONS;
                        break;
                    case R.id.nav_manage_account:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_SETTINGS;
                        break;
                    case R.id.nav_notification:
                        // launch new intent instead of loading fragment
//                        startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                        drawer.closeDrawers();
                        return true;
                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }
//        if (shouldLoadHomeFragOnBackPress) {
//            if (navItemIndex != 0) {
//                navItemIndex = 0;
//                CURRENT_TAG = TAG_HOME;
//                loadHomeFragment();
//                return;
//            }
//        }

        super.onBackPressed();
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_home:
                FragmentTransactionUtils.addFragment(
                        getSupportFragmentManager(),
                        new HomeFragment(), R.id.frame_home,
                        TAG_HOME,
                        true, -1, -1);
                break;
            default:
                FragmentTransactionUtils.addFragment(
                        getSupportFragmentManager(),
                        new HomeFragment(), R.id.frame_home,
                        TAG_HOME,
                        true, -1, -1);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
