package com.tdmr.demo.two.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.tdmr.demo.two.R;

/**
 * Created by abigail on 6/6/2016.
 */
public class BoundedRecyclerViewLayout extends RecyclerView {

    private final float mBoundedWidth;
    private final float mBoundedHeight;

    public BoundedRecyclerViewLayout(Context context) {
        super(context);
        mBoundedWidth = 0;
        mBoundedHeight = 0;
    }

    public BoundedRecyclerViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BoundedView);
        mBoundedWidth = a.getFloat(R.styleable.BoundedView_bounded_width, 0);
        mBoundedHeight = a.getFloat(R.styleable.BoundedView_bounded_height, 0);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Adjust width as necessary
        int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
        int width = (int) (mBoundedWidth * ((View) getParent()).getWidth());
        int height = (int) (mBoundedHeight * ((View) getParent()).getHeight());
        if (mBoundedWidth > 0 && mBoundedWidth < measuredWidth) {
            int measureMode = MeasureSpec.getMode(widthMeasureSpec);
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, measureMode);
        }
        // Adjust height as necessary
        int measuredHeight = MeasureSpec.getSize(heightMeasureSpec);
        if (mBoundedHeight > 0 && mBoundedHeight < measuredHeight) {
            int measureMode = MeasureSpec.getMode(heightMeasureSpec);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, measureMode);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}