package net.yeoubi.waiter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

/**
 * @author InJung Chung
 */
public class WaitingView extends FrameLayout {

    private ProgressBar progressBar;

    public WaitingView(Context context) {
        super(context);
        init(context);
    }

    public WaitingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        setAttributes(attrs);
    }

    public WaitingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        setAttributes(attrs);
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.view_waiting, this, false);

        progressBar = new ProgressBar(context, null, android.R.attr.progressBarStyle);
        viewGroup.addView(progressBar);

        addView(viewGroup);
    }

    private void setAttributes(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.WaitingView);

        try {
            boolean isWaiting = attributes.getBoolean(R.styleable.WaitingView_waiting, false);
            int position = attributes.getInteger(R.styleable.WaitingView_waitingPosition, Gravity.CENTER);
            int color = attributes.getResourceId(R.styleable.WaitingView_waitingColor, 0);
            int width = attributes.getDimensionPixelOffset(R.styleable.WaitingView_waitingWidth, -1);
            int height = attributes.getDimensionPixelOffset(R.styleable.WaitingView_waitingHeight, -1);
            int margin = attributes.getDimensionPixelSize(R.styleable.WaitingView_waitingMargin, 0);
            int marginTop = attributes.getDimensionPixelSize(R.styleable.WaitingView_waitingMarginTop, margin);
            int marginBottom = attributes.getDimensionPixelSize(R.styleable.WaitingView_waitingMarginBottom, margin);
            int marginStart = attributes.getDimensionPixelSize(R.styleable.WaitingView_waitingMarginStart, margin);
            int marginEnd = attributes.getDimensionPixelSize(R.styleable.WaitingView_waitingMarginEnd, margin);

            setWaiting(isWaiting);
            setPosition(position);
            setWaitingColor(getContext().getResources().getColor(color));
            if (width > -1) {
                setWaitingWidth(width);
            }
            if (height > -1) {
                setWaitingHeight(height);
            }
            setWaitingMargin(marginStart, marginTop, marginEnd, marginBottom);
        } finally {
            attributes.recycle();
        }
    }

    /**
     * Getter for waiting attribute.
     */
    public boolean getWaiting() {
        return progressBar != null && progressBar.getVisibility() == View.VISIBLE;
    }

    /**
     * Setter for waiting attribute.
     */
    public void setWaiting(boolean waiting) {
        if (progressBar != null) {
            progressBar.setVisibility(waiting ? View.VISIBLE : View.GONE);
        }

        setEnabled(!waiting);
    }

    /**
     * Getter for progress bar position.
     */
    public int getPosition() {
        return progressBar != null ? ((LayoutParams) progressBar.getLayoutParams()).gravity : Gravity.CENTER;
    }

    /**
     * Setter for progress bar position.
     */
    public void setPosition(int position) {
        if (progressBar == null) {
            return;
        }

        LayoutParams params = (LayoutParams) progressBar.getLayoutParams();
        params.gravity = position;
        progressBar.requestLayout();
    }

    /**
     * Setter for progress bar color.
     */
    public void setWaitingColor(int color) {
        progressBar.getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
    }

    public void setWaitingWidth(int width) {
        if (progressBar == null) {
            return;
        }

        ViewGroup.LayoutParams params = progressBar.getLayoutParams();
        params.width = width;
        progressBar.requestLayout();
    }

    public void setWaitingHeight(int height) {
        if (progressBar == null) {
            return;
        }

        ViewGroup.LayoutParams params = progressBar.getLayoutParams();
        params.height = height;
        progressBar.requestLayout();
    }

    public void setWaitingMargin(int marginStart, int marginTop, int marginEnd, int marginBottom) {
        if (progressBar == null) {
            return;
        }

        ViewGroup.MarginLayoutParams params = (MarginLayoutParams) progressBar.getLayoutParams();
        params.leftMargin = marginStart;
        params.topMargin = marginTop;
        params.rightMargin = marginEnd;
        params.bottomMargin = marginBottom;
        progressBar.requestLayout();
    }
}
