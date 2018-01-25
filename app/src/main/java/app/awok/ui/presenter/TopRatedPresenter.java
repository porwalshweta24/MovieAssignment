package app.awok.ui.presenter;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import javax.inject.Inject;

import app.awok.App;
import app.awok.MainActivity;
import app.awok.R;
import app.awok.network.MovieResponse;
import app.awok.network.RestClient;
import app.awok.ui.view.MoviesAdapter;
import app.awok.ui.view.NoInternetFragment;
import app.awok.ui.view.NoResultsFragment;
import app.awok.ui.view.PopularFragment;
import app.awok.ui.view.TopRatedFragment;
import app.awok.utils.Constants;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static app.awok.utils.Constants.FAVORITES;
import static app.awok.utils.Constants.POPULAR;
import static app.awok.utils.Constants.TOP_RATED;

/**
 * Presenter for managing the PopularFragment api calls.
 */

public class TopRatedPresenter {

    private final TopRatedFragment view;

    @Inject
    RestClient.TMDBService service;

    private final String TAG = TopRatedPresenter.class.getSimpleName();

    private int currentPage;
    private String queryText;

    public TopRatedPresenter(final TopRatedFragment view) {
        ((App) view.getContext().getApplicationContext()).getAppComponent().inject(this);
        this.view = view;
        initializePage();
    }


    private void initializePage() {
        this.currentPage = 1;
    }

    public void passPage() {
        this.currentPage += 1;
    }

    public String getQueryText() {
        return queryText;
    }
    /**
     * Sets the value of the queryText, updates the displayed list and scrolls it
     * to its top.
     *
     * @param newText String with the new value of the queryText.
     */

    public void setQueryText(final String newText, final int mode) {
        initializePage();
        this.queryText = newText;
        if(this.queryText.isEmpty()) {
            getMoviesByTopRated();
        } else {
            getMoviesByQuery(this.queryText);
        }
        scrollToTop();
    }

    /**
     * Computes if the last visible item is displayed.
     *
     * @return true if the last item of the adapter is already displayed, false otherwise.
     */
    public boolean isLastItemDisplaying() {
        if (view.cardsRecyclerView.getAdapter().getItemCount() != 0) {
            final LinearLayoutManager layoutManager = ((LinearLayoutManager) view.cardsRecyclerView.getLayoutManager());
            final int visibleItemCount = layoutManager.getChildCount();
            final int totalItemCount = layoutManager.getItemCount();
            final int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0)
                return true;
        }
        return false;
    }

    /**
     * Scrolls the recyclerView to its top.
     */
    private void scrollToTop() {
        view.cardsRecyclerView.scrollToPosition(0);
    }

    /**
     * Fills the RecyclerView adapter with the movies with a most similar title
     * to the String param from TMDB.
     *
     * @param text String with the text that is going to be compared to the movie titles.
     */
    public void getMoviesByQuery(final String text) {
        view.loadingMoviesBar.setVisibility(View.VISIBLE);
        final Observable<MovieResponse> call = service.moviesByQuery(Constants.API_KEY, text, currentPage);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieResponse>() {

                    @Override
                    public void onSubscribe(final Disposable d) {
                        Log.i(TAG, "getMoviesByQuery subscribed");
                    }

                    @Override
                    public void onNext(final MovieResponse moviesResponse) {
                        if(moviesResponse != null) {
                            if (currentPage == 1) {
                                if(moviesResponse.getMovies().isEmpty()) {
                                    openNoResultsFragment();
                                } else {
                                    Log.d(TAG, "getFilteredMovies replacing");
                                    ((MoviesAdapter) view.cardsRecyclerView.getAdapter()).replaceMovies(moviesResponse.getMovies());
                                }
                            } else {
                                Log.d(TAG, "getFilteredMovies adding");
                                ((MoviesAdapter) view.cardsRecyclerView.getAdapter()).addMovies(moviesResponse.getMovies());
                            }
                            view.loadingMoviesBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(final Throwable t) {
                        t.printStackTrace();
                        openConnectionError();
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "getMoviesByQuery completed");
                    }
                });
    }

    /**
     * Fills the RecyclerView adapter with the most popular movies from TMDB.
     */
    public void getMoviesByTopRated() {
        view.loadingMoviesBar.setVisibility(View.VISIBLE);
        final Observable<MovieResponse> call = service.moviesByTopRated(Constants.API_KEY, currentPage);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieResponse>() {

                    @Override
                    public void onSubscribe(final Disposable d) {
                        Log.i(TAG, "getMoviesByTopRated subscribed");
                    }

                    @Override
                    public void onNext(final MovieResponse moviesResponse) {
                        if(moviesResponse != null) {
                            view.loadingMoviesBar.setVisibility(View.GONE);
                            if (currentPage == 1) {
                                ((MoviesAdapter) view.cardsRecyclerView.getAdapter()).replaceMovies(moviesResponse.getMovies());
                            } else {
                                ((MoviesAdapter) view.cardsRecyclerView.getAdapter()).addMovies(moviesResponse.getMovies());
                            }
                            Log.d(TAG, "getMovies response = " + new Gson().toJson(moviesResponse));
                        }
                    }

                    @Override
                    public void onError(final Throwable t) {
                        t.printStackTrace();
                        openConnectionError();
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "getMoviesByTopRated completed");
                    }
                });
    }
    public void getMoviesByPopularity() {
        view.loadingMoviesBar.setVisibility(View.VISIBLE);
        final Observable<MovieResponse> call = service.moviesByPopularity(Constants.API_KEY, currentPage);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieResponse>() {

                    @Override
                    public void onSubscribe(final Disposable d) {
                        Log.i(TAG, "getMoviesByPopularity subscribed");
                    }

                    @Override
                    public void onNext(final MovieResponse moviesResponse) {
                        if(moviesResponse != null) {
                            view.loadingMoviesBar.setVisibility(View.GONE);
                            if (currentPage == 1) {
                                ((MoviesAdapter) view.cardsRecyclerView.getAdapter()).replaceMovies(moviesResponse.getMovies());
                            } else {
                                ((MoviesAdapter) view.cardsRecyclerView.getAdapter()).addMovies(moviesResponse.getMovies());
                            }
                            Log.d(TAG, "getMovies response = " + new Gson().toJson(moviesResponse));
                        }
                    }

                    @Override
                    public void onError(final Throwable t) {
                        t.printStackTrace();
                        openConnectionError();
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "getMoviesByPopularity completed");
                    }
                });
    }
    private void openNoResultsFragment() {
        final NoResultsFragment noResultsFragment = NoResultsFragment.newInstance();
        final Bundle args = new Bundle();
        args.putString(view.queryTextKey, this.queryText);
        noResultsFragment.setArguments(args);
        try {
            view.getHostFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, noResultsFragment)
                    .commit();
        } catch (final NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void openConnectionError() {
        try {
            view.getHostFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, NoInternetFragment.newInstance())
                    .commit();
        } catch (final NullPointerException e) {
            e.printStackTrace();
        }
    }
}
