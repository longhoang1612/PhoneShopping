package hoanglong.thesis.graduation.juncomputer.screen.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import hoanglong.thesis.graduation.juncomputer.screen.search.SearchActivity;
import hoanglong.thesis.graduation.juncomputer.screen.favorites.FavoritesFragment;
import hoanglong.thesis.graduation.juncomputer.screen.manageOrder.ManagerOrderFragment;
import hoanglong.thesis.graduation.juncomputer.R;
import hoanglong.thesis.graduation.juncomputer.data.model.user.User;
import hoanglong.thesis.graduation.juncomputer.data.source.local.realm.RealmCart;
import hoanglong.thesis.graduation.juncomputer.data.source.local.realm.RealmUser;
import hoanglong.thesis.graduation.juncomputer.listener.UpdateCart;
import hoanglong.thesis.graduation.juncomputer.screen.cart.CartActivity;
import hoanglong.thesis.graduation.juncomputer.screen.category.CategoryFragment;
import hoanglong.thesis.graduation.juncomputer.screen.home.homefragment.HomeFragment;
import hoanglong.thesis.graduation.juncomputer.screen.login.LoginActivity;
import hoanglong.thesis.graduation.juncomputer.screen.notification.NotificationFragment;
import hoanglong.thesis.graduation.juncomputer.screen.userinfo.UserInfoActivity;
import hoanglong.thesis.graduation.juncomputer.utils.Constant;
import hoanglong.thesis.graduation.juncomputer.utils.FragmentTransactionUtils;
import hoanglong.thesis.graduation.juncomputer.utils.SharedPrefs;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, UpdateCart {

    // tags used to attach the fragments

    private static final String TAG_HOME = "TAG_HOME";
    private static final String TAG_LOGIN = "TAG_LOGIN";
    private static final String TAG_CATEGORY = "TAG_CATEGORY";
    private static final String TAG_MANAGER_ORDER = "TAG_MANAGER_ORDER";
    private static final String TAG_NOTIFICATIONS = "TAG_NOTIFICATIONS";
    private static final String TAG_MANAGER_ACCOUNT = "TAG_MANAGER_ACCOUNT";
    private static final String TAG_FAVORITES = "TAG_FAVORITES";

    // index to identify current nav menu item
    public static int navItemIndex = 0;
    public static String CURRENT_TAG = TAG_HOME;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_number_cart)
    TextView mTextNumberCart;
    @BindView(R.id.ic_shopping_cart)
    RelativeLayout mRelativeShoppingCart;
    @BindView(R.id.card_view_search)
    CardView mCardSearch;
    private TextView textName;
    private TextView textEmail;
    private boolean isLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        isLogin = SharedPrefs.getInstance().get(Constant.Login.LOGIN_STATUS, Boolean.class);

        navigationView.setNavigationItemSelectedListener(this);
        mCardSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
        View headerView = navigationView.getHeaderView(0);
        LinearLayout linearLogin = headerView.findViewById(R.id.linear_login);
        textName = headerView.findViewById(R.id.text_name);
        textEmail = headerView.findViewById(R.id.text_email);

        linearLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isLogin = SharedPrefs.getInstance().get(Constant.Login.LOGIN_STATUS, Boolean.class);
                if (isLogin) {
                    Intent intent = new Intent(HomeActivity.this, UserInfoActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        mRelativeShoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CartActivity.class);
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

    private void updateLogin(TextView textName, TextView textEmail) {
        Gson gson = new Gson();
        String json = SharedPrefs.getInstance().get("MyObject", String.class);
        User user = gson.fromJson(json, User.class);

        if (user == null) {
            textName.setText("Thông tin người dùng ");
            textEmail.setText("Đăng nhập/đăng ký ");
            return;
        }
        textName.setText(user.getFullName());
        textEmail.setText(user.getEmail());
    }

    @Override
    protected void onResume() {
        super.onResume();
        onUpdateCart();
        updateLogin(textName, textEmail);
    }

    private void loadHomeFragment() {

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
                        false, -1, -1);
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
            case TAG_MANAGER_ACCOUNT:
                if (isLogin) {
                    Intent intent = new Intent(HomeActivity.this, UserInfoActivity.class);
                    startActivity(intent);
                    drawer.closeDrawers();
                } else {
                    Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case TAG_MANAGER_ORDER:
                if (!isLogin) {
                    Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                    drawer.closeDrawers();
                } else {
                    if (fragment == null) {
                        fragment = new ManagerOrderFragment();
                    }
                    FragmentTransactionUtils.addFragment(
                            getSupportFragmentManager(),
                            fragment, R.id.frame_home, TAG_MANAGER_ORDER,
                            true, -1, -1);
                }
                break;
            case TAG_NOTIFICATIONS:
                if (fragment == null) {
                    fragment = new NotificationFragment();
                }
                FragmentTransactionUtils.addFragment(
                        getSupportFragmentManager(),
                        fragment, R.id.frame_home, TAG_NOTIFICATIONS,
                        true, -1, -1);
                break;
            case TAG_FAVORITES:
                if (!isLogin) {
                    Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                    drawer.closeDrawers();
                } else {
                    if (fragment == null) {
                        fragment = new FavoritesFragment();
                    }
                    FragmentTransactionUtils.addFragment(
                            getSupportFragmentManager(),
                            fragment, R.id.frame_full_home, TAG_FAVORITES,
                            true, -1, -1);
                }
                break;
            default:
                if (fragment == null) {
                    fragment = new HomeFragment();
                }
                FragmentTransactionUtils.addFragment(
                        getSupportFragmentManager(),
                        fragment, R.id.frame_home, TAG_HOME,
                        true, -1, -1);
                break;
        }

        drawer.closeDrawers();
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

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
                        drawer.closeDrawers();
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_MANAGER_ORDER;
                        loadHomeFragment();
                        break;
                    case R.id.nav_favorites:
                        drawer.closeDrawers();
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_FAVORITES;
                        loadHomeFragment();
                        break;
                    case R.id.nav_manage_account:
                        drawer.closeDrawers();
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_MANAGER_ACCOUNT;
                        loadHomeFragment();
                        break;
                    case R.id.nav_notification:
                        drawer.closeDrawers();
                        navItemIndex = 5;
                        CURRENT_TAG = TAG_NOTIFICATIONS;
                        loadHomeFragment();
                        return true;
                    default:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        loadHomeFragment();
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

        if (navItemIndex != 0) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
            return;
        }
        if (getSupportFragmentManager().findFragmentByTag(TAG_HOME) instanceof HomeFragment) {
            finish();
        }
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawers();
//            return;
//        }
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
            case R.id.nav_category:
                FragmentTransactionUtils.addFragment(
                        getSupportFragmentManager(),
                        new CategoryFragment(), R.id.frame_home, TAG_CATEGORY,
                        true, -1, -1);
                break;
            case R.id.nav_manage_account:
                Intent intent = new Intent(HomeActivity.this, UserInfoActivity.class);
                startActivity(intent);
                drawer.closeDrawers();
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

    @Override
    public void onUpdateCart() {
        if (RealmCart.getCartOffline() == null) {
            return;
        }
        mTextNumberCart.setText(String.valueOf(RealmCart.getCartOffline().size()));
    }
}
