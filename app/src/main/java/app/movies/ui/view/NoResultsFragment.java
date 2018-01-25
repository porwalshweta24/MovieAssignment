package app.movies.ui.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.movies.R;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Represents the no results Fragment, with a message explaining that the current query
 * has not any linked movie.
 *
 * Created by shweta 23/05/2017.
 */

public class NoResultsFragment extends Fragment {

    @BindView(R.id.no_results_found_text)
    TextView noResultsTextView;

    @BindString(R.string.no_results_found_for)
    String noResultsForText;

    @BindString(R.string.query_text_key)
    String queryKey;


    @Override
    public View onCreateView(final LayoutInflater inflater,
                             final ViewGroup container,
                             final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.no_results_found_fragment, container, false);
        ButterKnife.bind(this, view);
        final String noResultsForCompleteText = String.format("%s %s",
                noResultsForText,
                getArguments().getString(queryKey, ""));
        noResultsTextView.setText(noResultsForCompleteText);
        return view;
    }

    public static NoResultsFragment newInstance() {
        return new NoResultsFragment();
    }
}
