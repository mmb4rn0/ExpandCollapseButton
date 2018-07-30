package com.mmbarno.expandcollapsebtn;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.MissingResourceException;

/**
 * Created by mmbarno on 7/29/18.
 * Email: manzur.mehedi@gagagugu.com
 */
@SuppressWarnings("ALL")
public class ExpandCollapseButton extends FrameLayout implements View.OnClickListener {

    @IntDef({FlipAxis.x, FlipAxis.y})
    @Retention(RetentionPolicy.SOURCE)
    public @interface FlipAxis {
        int x = 1;
        int y = 2;
    }

    // Attributes
    private int mFlipAxis = FlipAxis.y;
    private boolean mChecked;
    private boolean mInvertFlip;
    private int mFlipDrawableResource;
    private int mFlipAnimDuration;

    private Drawable mFlipDrawable;

    private OnCheckedChangeListener mListener;

    private ButtonView mFlippingBtnView;
    private boolean isAnimationRunning;

    public ExpandCollapseButton(Context context) {
        this(context, null);
    }

    public ExpandCollapseButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandCollapseButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ExpandCollapseButton);
            mChecked = a.getBoolean(R.styleable.ExpandCollapseButton_checked, false);
            mFlipDrawableResource = a.getResourceId(R.styleable.ExpandCollapseButton_checked_state_src, 0);
            mFlipAxis = a.getInteger(R.styleable.ExpandCollapseButton_flip_axis, FlipAxis.x);
            mInvertFlip = a.getBoolean(R.styleable.ExpandCollapseButton_invert_flip, false);
            mFlipAnimDuration = a.getInteger(R.styleable.ExpandCollapseButton_flip_anim_duration, 300);
            a.recycle();
        }

        mFlipDrawable = ContextCompat.getDrawable(getContext(), mFlipDrawableResource);
        if (mFlipDrawable == null) {
            throw new MissingResourceException("Need checked_state_src", this.getClass().getSimpleName(), "missing_resource");
        }

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER;
        mFlippingBtnView = new ButtonView(getContext());
        mFlippingBtnView.setLayoutParams(layoutParams);
        mFlippingBtnView.setOnClickListener(this);
        mFlippingBtnView.setImageDrawable(mFlipDrawable);
        addView(mFlippingBtnView);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (getChildCount() > 1) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                if (child instanceof ButtonView) {
                    continue;
                }
                removeView(child);
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void onClick(View v) {
        if (v == mFlippingBtnView) {
            setChecked(!mChecked, true);
        }
    }

    public void setChecked(boolean mChecked) {
        setChecked(mChecked, false);
    }

    public boolean isChecked() {
        return mChecked;
    }

    private void setChecked(boolean checked, boolean withAnim) {
        if (mChecked == checked || (withAnim && isAnimationRunning)) {
            return;
        }
        this.mChecked = checked;
        if (mListener != null && withAnim) {
            mListener.onCheckedChange(this, checked);
        }
        mFlippingBtnView.startFlipping(withAnim);
    }

    @FlipAxis
    public int getFlipAxis() {
        return mFlipAxis;
    }

    public void setFlipAxis(@FlipAxis int flipAxis) {
        if (mFlipAxis == flipAxis) {
            return;
        }
        this.mFlipAxis = flipAxis;
        mFlippingBtnView.startFlipping(false);
    }

    public boolean isInvertFlip() {
        return mInvertFlip;
    }

    public void setInvertFlip(boolean invertFlip) {
        if (mInvertFlip == invertFlip) {
            return;
        }
        this.mInvertFlip = invertFlip;
        mFlippingBtnView.startFlipping(false);
    }

    public void setFlipDrawableResource(int flipDrawableResource) {
        if (mFlipDrawableResource == flipDrawableResource) {
            return;
        }
        this.mFlipDrawableResource = flipDrawableResource;
        Drawable flipDrawable = ContextCompat.getDrawable(getContext(), mFlipDrawableResource);
        setFlipDrawable(flipDrawable);
    }

    public Drawable getFlipDrawable() {
        return mFlipDrawable;
    }

    public void setFlipDrawable(Drawable flipDrawable) {
        if (mFlipDrawable == flipDrawable) {
            return;
        }
        this.mFlipDrawable = flipDrawable;
        if (mFlipDrawable == null) {
            throw new NullPointerException("Flip drawable can not be null");
        }
        mFlippingBtnView.setImageDrawable(mFlipDrawable);
    }

    public int getFlipAnimDuration() {
        return mFlipAnimDuration;
    }

    public void setFlipAnimDuration(int flipAnimDuration) {
        this.mFlipAnimDuration = flipAnimDuration;
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        mListener = listener;
    }

    public interface OnCheckedChangeListener {
        void onCheckedChange(View view, boolean isChecked);
    }

    private class ButtonView extends ImageView {
        private float from, to;
        private ObjectAnimator animator;

        public ButtonView(Context context) {
            super(context);
            setScaleType(ScaleType.FIT_XY);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
        }

        public void startFlipping(boolean withAnim) {
            loadRotation();
            int distance = mFlipAxis == FlipAxis.x ? getMeasuredHeight() : getMeasuredWidth();
            if (distance > getCameraDistance()) {
                float scale = getResources().getDisplayMetrics().density;
                setCameraDistance(distance * 2 * scale);
            }
            if (animator != null && isAnimationRunning) {
                animator.cancel();
            }

            setLayerType(View.LAYER_TYPE_HARDWARE, null);

            animator = ObjectAnimator.ofFloat(this, mFlipAxis == FlipAxis.x ? ROTATION_X : ROTATION_Y, from, to);
            animator.setInterpolator(new LinearInterpolator());
            animator.addListener(animatorListener);
            animator.setStartDelay(0);
            animator.setDuration(withAnim ? mFlipAnimDuration : 0);
            animator.start();
        }

        private Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isAnimationRunning = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                resetAnimator();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                resetAnimator();
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        };

        private void resetAnimator() {
            isAnimationRunning = false;
            animator.removeListener(animatorListener);
            animator = null;
            setLayerType(LAYER_TYPE_NONE, null);
        }


        private void loadRotation() {
            int checkedRotation = 0;
            int uncheckedRotation = 180;
            if (mInvertFlip) {
                int temp = checkedRotation;
                checkedRotation = uncheckedRotation;
                uncheckedRotation = temp;
            }
            from = mChecked ? (float) uncheckedRotation : (float) checkedRotation;
            to = mChecked ? (float) checkedRotation : (float) uncheckedRotation;
        }

        @Override
        public void setImageDrawable(@Nullable Drawable drawable) {
            super.setImageDrawable(drawable);
            startFlipping(false);
        }
    }
}
