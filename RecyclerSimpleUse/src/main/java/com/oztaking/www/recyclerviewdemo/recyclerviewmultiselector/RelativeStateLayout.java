package com.oztaking.www.recyclerviewdemo.recyclerviewmultiselector;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.oztaking.www.recyclerviewdemo.R;

/**
 * @function
 */

public class RelativeStateLayout extends RelativeLayout{

    private static final int[] STATE_CLICK = { R.attr.state_click };
    private boolean isLayoutClick = false;


    public boolean isLayoutClick() {
        return isLayoutClick;
    }

    public void setLayoutClick(boolean layoutClick) {
        if (this.isLayoutClick != layoutClick) {
            isLayoutClick = layoutClick;
            refreshDrawableState();
        }
    }


    public RelativeStateLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace)
    {
        if (isLayoutClick) {
            final int[] drawableState = super
                    .onCreateDrawableState(extraSpace + 1);
            mergeDrawableStates(drawableState, STATE_CLICK);
            return drawableState;
        }
        return super.onCreateDrawableState(extraSpace);
    }

}

