package app.movies.ui.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;


import app.movies.MainActivity;
import app.movies.R;
import app.movies.ui.presenter.PopularPresenter;
import butterknife.BindInt;
import app.movies.utils.RecyclerViewMargin;

import butterknife.BindDimen;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Represents the main fragment, where all the movies are going to be displayed.
 *
 * Created by shweta 17/05/2017.
 */

public class PopularFragment extends Fragment {

    @BindInt(R.integer.movie_list_column_number)
    int numberOfColumns;

    @BindView(R.id.loading_movies_progress_bar)
    public ProgressBar loadingMoviesBar;

    @BindView(R.id.cards_recicler_view)
    public RecyclerView cardsRecyclerView;

    @BindString(R.string.query_text_key)
    public String queryTextKey;

    @BindDimen(R.dimen.distance_between_recycler_view_items)
    public int distanceBetweenItems;

    private PopularPresenter landingPresenter;


    @Override
    public View onCreateView(final LayoutInflater inflater,
                             final ViewGroup container,
                             final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.landing_fragment, container, false);
        ButterKnife.bind(this, view);
        this.landingPresenter = new PopularPresenter(this);
        @MainActivity.DisplayMode int mode= getArguments().getInt("mode", 1);
        this.landingPresenter.setQueryText(getArguments().getString(queryTextKey, ""),mode);


//        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
//        LinearLayoutManager layoutManager
//                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        final RecyclerView.LayoutManager layoutManager= new GridLayoutManager(getActivity(),2);
        cardsRecyclerView.setLayoutManager(layoutManager);
        cardsRecyclerView.setHasFixedSize(true);
        cardsRecyclerView.setAdapter(new MoviesAdapter());
        cardsRecyclerView.addOnScrollListener(mScrollListener);
        final RecyclerViewMargin decoration = new RecyclerViewMargin(distanceBetweenItems, numberOfColumns);
        cardsRecyclerView.addItemDecoration(decoration);
        return view;
    }

    public static PopularFragment newInstance() {
        return new PopularFragment();
    }

    /**
     * ScrollListener that checks if the last item is displayed, if it is, loads another
     * page of movies, by popularity or by query depending on the current type of search.
     */
    public RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(final RecyclerView recyclerView, final int dx, final int dy) {
            if (landingPresenter.isLastItemDisplaying() && !loadingMoviesBar.isShown()) {
                landingPresenter.passPage();
                if(landingPresenter.getQueryText().isEmpty()) {

                    landingPresenter.getMoviesByPopularity();
                } else {
                    landingPresenter.getMoviesByQuery(landingPresenter.getQueryText());
                }
            }
        }
    };

    public FragmentManager getHostFragmentManager() {
        FragmentManager manager = getFragmentManager();
        if (manager == null && isAdded()) {
            manager = getActivity().getSupportFragmentManager();
        }
        return manager;
    }

    public PopularPresenter getLandingPresenter() {
        return this.landingPresenter;
    }

}
