package app.movies;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

import app.movies.ui.view.PopularFragment;
import app.movies.ui.view.NoInternetFragment;
import app.movies.ui.view.NoResultsFragment;

import app.movies.ui.view.TopRatedFragment;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.text.TextUtils.*;
import static app.movies.utils.Constants.FAVORITES;
import static app.movies.utils.Constants.POPULAR;
import static app.movies.utils.Constants.TOP_RATED;

/**
 * Manager of the application. Contains the main layout that will be filled with
 * the different Fragment objects.
 */
public class MainActivity extends AppCompatActivity implements NoInternetFragment.RetryClickListener,
        NavigationView.OnNavigationItemSelectedListener {
    private final String TAG = getClass().getSimpleName();
    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    public DrawerLayout drawer;
    @BindView(R.id.nav_view)    public NavigationView navigationView;
    @BindView(R.id.search_view)    public MaterialSearchView searchView ;
    @BindString(R.string.query_text_key)    public String queryTextKey;

    private SearchView mSearchView;
    @DisplayMode
    int displayMode = POPULAR;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initToolBarAndDrawerNavigation();
        initSearchView();
        if (savedInstanceState == null) {
            openLandingFragment(POPULAR);
        }
    }

    private void initToolBarAndDrawerNavigation() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }
    private void initSearchView()
    {
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newText) {
                //Do some magic
                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic

                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!isEmpty(searchWrd)) {
                    searchView.setQuery(searchWrd, false);
                }
            }

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        final MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
//        searchView.setMenuItem(searchItem);
        mSearchView = (SearchView) searchItem.getActionView();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(final String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                if (newText.isEmpty()) {
                    Log.i("Typing", "empty");
                }
                Log.i("Typing", newText);
                final Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container);
                if (currentFragment instanceof PopularFragment) {
                    ((PopularFragment) currentFragment).getLandingPresenter().setQueryText(newText, displayMode);
                } else if (currentFragment instanceof TopRatedFragment) {
                    ((TopRatedFragment) currentFragment).getLandingPresenter().setQueryText(newText, displayMode);
                } else if (currentFragment instanceof NoResultsFragment) {
                    openLandingFragment(POPULAR);
                }
                return false;
            }
        });
        return true;
    }

    /**
     * Opens a new PopularFragment in order to try to connect to the TMBD API, passing
     * the current text query at the SearchView.
     */
    @Override
    public void retryConnection() {
        openLandingFragment(POPULAR);
    }

    private void openLandingFragment(@DisplayMode int mode) {
        this.displayMode = mode;
        Fragment landingFragment=null;
        switch (displayMode) {
            case POPULAR:
                landingFragment = PopularFragment.newInstance();
                break;
            case TOP_RATED:
                landingFragment = TopRatedFragment.newInstance();
                break;
            case FAVORITES:
                showNoFavoritesSetDialog();
                break;
        }
        final Bundle args = new Bundle();
        if (mSearchView == null) {
            args.putString(queryTextKey, "");
        } else {
            args.putString(queryTextKey, mSearchView.getQuery().toString());
        }
        args.putInt("mode", mode);
        landingFragment.setArguments(args);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, landingFragment)
                .commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.popular_movies) {
            setDisplayMode(POPULAR);
            openLandingFragment(POPULAR);
            setTitle(R.string.drawer_popular_movies);
        } else if (id == R.id.highest_rated_movies) {
            setDisplayMode(TOP_RATED);
            openLandingFragment(TOP_RATED);
            setTitle(R.string.drawer_top_rated);
        }else if(id== R.id.favorite_movies)
        {
            showNoFavoritesSetDialog();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void showNoFavoritesSetDialog() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("Favorites");
        builder1.setIcon(R.drawable.ic_favorite_selected);
        builder1.setMessage(R.string.dialog_no_favorites_set);
        builder1.setCancelable(true);
        builder1.setNeutralButton(R.string.dialog_no_favorites_ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert1 = builder1.create();
        alert1.show();
    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onBackPressed() {

    /*    if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        }*/
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            super.onBackPressed();
        }
    }

    @IntDef({POPULAR, TOP_RATED, FAVORITES})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DisplayMode {
    }

    @DisplayMode
    int mTMdisplayMode = POPULAR;

    public void setDisplayMode(@DisplayMode int displayMode) {
        mTMdisplayMode = displayMode;
    }

    @DisplayMode
    public int getDisplayMode() {
        return mTMdisplayMode;
    }


}
