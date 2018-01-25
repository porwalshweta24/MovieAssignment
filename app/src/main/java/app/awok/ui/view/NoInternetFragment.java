package app.awok.ui.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.awok.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTouch;

/**
 * Represents the connection error Fragment, with an error message and a clickable text
 * to retry the connection.
 *
 * Created by shweta 21/05/2017.
 */

public class NoInternetFragment extends Fragment {

    @BindView(R.id.retry_text)
    TextView retryText;

    RetryClickListener mCallback;

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             final ViewGroup container,
                             final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.no_internet_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public static NoInternetFragment newInstance() {
        return new NoInternetFragment();
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        try {
            mCallback = (RetryClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    /**
     * Action of touching the retry TextView, if the event is the start of the touch event,
     * changes the color of the TextView (in order to give some feedback to the user), if the
     * event is the finish, tries to connect to the API via the MainActivity.
     *
     * @param view clicked TextView.
     * @param motionEvent press or stop pressing the TextView event.
     * @return true if the method is correctly finished, false otherwise.
     */
    @OnTouch(R.id.retry_text)
    boolean retry(final View view, final MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            retryText.setTextColor(ContextCompat.getColor(getContext(), R.color.accent_dark));
        } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            mCallback.retryConnection();
        }
        return true;
    }

    public interface RetryClickListener {
        void retryConnection();
    }

}
