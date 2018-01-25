package app.awok.utils;

import android.graphics.Rect;
import android.support.annotation.IntRange;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by shweta 23/05/2017.
 */

public class RecyclerViewMargin extends RecyclerView.ItemDecoration {

    private final int columns;
    private int margin;

    public RecyclerViewMargin(@IntRange(from=0)final int margin ,
                              @IntRange(from=0) final int columns ) {
        this.margin = margin;
        this.columns=columns;

    }

    @Override
    public void getItemOffsets(final Rect outRect,
                               final View view,
                               final RecyclerView parent,
                               final RecyclerView.State state) {

        final int position = parent.getChildLayoutPosition(view);
        outRect.right = margin;
        outRect.bottom = margin;
        if (position < columns) {
            outRect.top = margin;
        }
        if(position % columns == 0){
            outRect.left = margin;
        }
    }
}