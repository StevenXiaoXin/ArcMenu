package com.example.liuzhuang.arcmenu.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.example.liuzhuang.arcmenu.R;

/**
 * Created by liuzhuang on 2016/11/14.
 */

public class ArcMenu extends ViewGroup implements View.OnClickListener {

    private static final int POS_LEFT_TOP = 0;
    private static final int POS_LEFT_BOTTOM = 1;
    private static final int POS_RIGHT_TOP = 2;
    private static final int POS_RIGHT_BOTTOM = 3;

    private Position mPosition = Position.RIGHT_BOTTOM;
    private int mRadius;
    /**
     * 菜单的状态
     */
    private Status mCurrentStatus = Status.CLOSE;
    private View mButton;

    private OnMenuItemClickListener mMenuItemClickListener;

    public enum Status {
        OPEN, CLOSE
    }


    /**
     * 菜单的位置枚举类
     */
    public enum Position {
        LEFT_TOP,LEFT_BOTTOM,RIGHT_TOP, RIGHT_BOTTOM
    }

    /**
     * 点击子菜单回调接口
     */
    public interface OnMenuItemClickListener {
        void onClick(View view,int pos);
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener mMenuItemClickListener) {
        this.mMenuItemClickListener = mMenuItemClickListener;
    }

    public ArcMenu(Context context) {
        this(context,null);
    }

    public ArcMenu(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ArcMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
        //获取自定义属性
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ArcMenu, defStyleAttr, 0);

       int pos = a.getInt(R.styleable.ArcMenu_position,POS_RIGHT_BOTTOM);

        switch (pos) {
            case POS_LEFT_TOP:
                mPosition = Position.LEFT_TOP;
                break;
            case POS_LEFT_BOTTOM:
                mPosition = Position.LEFT_BOTTOM;
                break;
            case POS_RIGHT_TOP:
                mPosition = Position.RIGHT_TOP;
                break;
            case POS_RIGHT_BOTTOM:
                mPosition = Position.RIGHT_BOTTOM;
                break;
        }

        mRadius = (int) a.getDimension(R.styleable.ArcMenu_radius,
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics()));

        Log.e("TAG","position="+mPosition+"radius="+mRadius);

        a.recycle();


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        for (int i=0;i<count;i++) {
            //完成子view的测量
            measureChild(getChildAt(i),widthMeasureSpec,heightMeasureSpec);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        if (changed) {
            layoutCButton();

            //给子菜单布局定位
            int count = getChildCount();

            for (int i=0;i<count-1;i++) {
                View child = getChildAt(i + 1);
                child.setVisibility(View.GONE);

                //距离左和上的距离
                int cl = (int) (mRadius * Math.sin(Math.PI / 2 / (count - 2)
                        * i));
                int ct = (int) (mRadius * Math.cos(Math.PI / 2 / (count - 2)
                        * i));

                //图标的宽高
                int cWidth = child.getMeasuredWidth();
                int cHeight = child.getMeasuredHeight();

                // 如果菜单位置在底部 左下，右下
                if (mPosition == Position.LEFT_BOTTOM
                        || mPosition == Position.RIGHT_BOTTOM)
                {
                    ct = getMeasuredHeight() - cHeight - ct;
                }
                // 右上，右下
                if (mPosition == Position.RIGHT_TOP
                        || mPosition == Position.RIGHT_BOTTOM)
                {
                    cl = getMeasuredWidth() - cWidth - cl;
                }
                child.layout(cl, ct, cl + cWidth, ct + cHeight);
            }
        }
    }

    private void layoutCButton() {
        mButton = getChildAt(0);
        mButton.setOnClickListener(this);

        int l=0;
        int t=0;
        int width = mButton.getMeasuredWidth();
        int height = mButton.getMeasuredHeight();

        switch (mPosition) {
            case LEFT_TOP:
                l=0;
                t=0;
                break;
            case LEFT_BOTTOM:
                l=0;
                t=getMeasuredHeight()-height;
                break;
            case RIGHT_TOP:
                l=getMeasuredWidth()-width;
                t=0;
                break;
            case RIGHT_BOTTOM:
                l=getMeasuredWidth()-width;
                t=getMeasuredHeight()-height;
                break;

        }

        mButton.layout(l,t,l+width,t+width);

    }
    @Override
    public void onClick(View v) {

//        mButton = findViewById(R.id.id_button);
//
//        if (mButton == null) {
//            mButton = getChildAt(0);
//        }

        rotaButton(v, 0f, 360f, 1500);
        toggleMenu(300);

    }

    public boolean isOpen()
    {
        return mCurrentStatus == Status.OPEN;
    }

    /**
     * 切换菜单
     * @param duration
     */
    public void toggleMenu(int duration) {
        //为menuItem添加平移和旋转动画
        int count = getChildCount();
        for (int i=0;i<count-1;i++) {
            final View childView = getChildAt(i + 1);
            childView.setVisibility(View.VISIBLE);

            //距离左和上的距离
            int cl = (int) (mRadius * Math.sin(Math.PI / 2 / (count - 2)
                    * i));
            int ct = (int) (mRadius * Math.cos(Math.PI / 2 / (count - 2)
                    * i));

            int xflag = 1;
            int yflag = 1;
            if (mPosition == Position.LEFT_TOP || mPosition == Position.LEFT_BOTTOM) {
                xflag = -1;
            }
            if (mPosition == Position.LEFT_TOP || mPosition == Position.RIGHT_TOP) {
                yflag = -1;
            }

            AnimationSet animset = new AnimationSet(true);
            Animation tranAnim = null;

            if (mCurrentStatus == Status.CLOSE) {
                tranAnim = new TranslateAnimation(xflag * cl, 0, yflag * ct, 0);
                childView.setClickable(true);
                childView.setFocusable(true);

            } else {
                tranAnim = new TranslateAnimation(0,xflag * cl,  0, yflag * ct);
                childView.setClickable(false);
                childView.setFocusable(false);
            }

            tranAnim.setFillAfter(true);
            tranAnim.setDuration(duration);
            tranAnim.setStartOffset((i*1500)/count);

            tranAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {

                    if (mCurrentStatus == Status.CLOSE) {
                        childView.setVisibility(View.GONE);

                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            RotateAnimation rotateAnimation=new RotateAnimation(0, 720,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                    0.5f);
            rotateAnimation.setDuration(duration);
            rotateAnimation.setStartOffset((i*1500)/count);
            rotateAnimation.setFillAfter(true);

            animset.addAnimation(rotateAnimation);
            animset.addAnimation(tranAnim);
            childView.startAnimation(animset);

            final int pos = i + 1;
            childView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mMenuItemClickListener != null)
                        mMenuItemClickListener.onClick(childView, pos);
                        menuItemAnim(pos - 1);
                        changeStatus();

                }


            });

        }
        changeStatus();
    }

    /**
     * 添加menuItem的点击动画
     * @param pos
     */
    private void menuItemAnim(int pos) {
        for (int i=0;i<getChildCount()-1;i++) {
            View childView = getChildAt(i + 1);
            if (i == pos) {
                childView.startAnimation(scaleBigAnim(300));

            } else {

                childView.startAnimation(scaleSmallAnim(300));
            }
            childView.setClickable(false);
            childView.setFocusable(false);

        }
    }
    /**
     * 为当前未点击的item设置隐藏
     * @param duration
     * @return
     */
    private Animation scaleSmallAnim(int duration) {
        AnimationSet animationSet = new AnimationSet(true);

        ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        AlphaAnimation alphaAnim = new AlphaAnimation(1f, 0.0f);
        animationSet.addAnimation(scaleAnim);
        animationSet.addAnimation(alphaAnim);
        animationSet.setDuration(duration);
        animationSet.setFillAfter(true);
        return animationSet;
    }

    /**
     * 为当前点击的item设置变大和透明动画
     * @param duration
     * @return
     */
    private Animation scaleBigAnim(int duration) {
        AnimationSet animationSet = new AnimationSet(true);

        ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 4.0f, 1.0f, 4.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        AlphaAnimation alphaAnim = new AlphaAnimation(1f, 0.0f);

        animationSet.addAnimation(scaleAnim);
        animationSet.addAnimation(alphaAnim);

        animationSet.setDuration(duration);
        animationSet.setFillAfter(true);
        return animationSet;
    }

    /**
     * 修改childview的状态
     */
    private void changeStatus() {
        mCurrentStatus = (mCurrentStatus == Status.CLOSE ? Status.OPEN : Status.CLOSE);
    }

    private void rotaButton(View v, float start, float end, int duration) {
        RotateAnimation anim = new RotateAnimation(start, end,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        anim.setDuration(duration);
        anim.setFillAfter(true);
        v.startAnimation(anim);

    }

}
