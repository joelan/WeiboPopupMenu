package lrq.com.addpopmenu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * 弹出菜单
 * Created by Joe on 16/12/10
 */
public class PopMenu {


    /**
     * 默认的列数为4个
     */
    private static final int DEFAULT_COLUMN_COUNT = 3;

    /**
     * 动画时间
     */
    private static final int DEFAULT_DURATION = 300;

    /**
     * 拉力系数
     */
    private static final int DEFAULT_TENSION = 10;
    /**
     * 摩擦力系数
     */
    private static final int DEFAULT_FRICTION = 5;

    /**
     * item水平之间的间距
     */
    private static final int DEFAULT_HORIZONTAL_PADDING = 40;
    /**
     * item竖直之间的间距
     */
    private static final int DEFAULT_VERTICAL_PADDING = 15;

    private Activity mActivity;
    private int mColumnCount;
    private List<PopMenuItem> mMenuItems = new ArrayList<>();
    private RelativeLayout mAnimateLayout;
    private GridLayout mGridLayout;
    private ImageView mCloseIv;
    private int mDuration;
    private double mTension;
    private double mFriction;
    private int mHorizontalPadding;
    private int mVerticalPadding;
    private PopMenuItemListener mPopMenuItemListener;

    private int mScreenWidth;
    private int mScreenHeight;

    private boolean isShowing = false;

    public float getmMarginTopRemainSpace() {
        return mMarginTopRemainSpace;
    }

    public void setmMarginTopRemainSpace(float mMarginTopRemainSpace) {
        this.mMarginTopRemainSpace = mMarginTopRemainSpace;
    }

    public boolean ismIsmalpositionAnimatOut() {
        return mIsmalpositionAnimatOut;
    }

    public void setmIsmalpositionAnimatOut(boolean mIsmalpositionAnimatOut) {
        this.mIsmalpositionAnimatOut = mIsmalpositionAnimatOut;
    }






    /**
     * Menu相对于屏幕顶部的距离（去掉菜单本身高度剩下部分除以这个倍数因子）
     */

  private   float mMarginTopRemainSpace=1.5f;

    /**
     * 是否错位弹出菜单
     */
  private   boolean mIsmalpositionAnimatOut=true;

    /**
     * 错位动画时间（毫秒）默认50
     */
  private   int malposition=50;



    public int getMalposition() {
        return malposition;
    }

    public void setMalposition(int malposition) {
        this.malposition = malposition;
    }

    private SpringSystem mSpringSystem;

    {
        mSpringSystem = SpringSystem.create();
    }

    private PopMenu(Builder builder) {
        this.mActivity = builder.activity;
        this.mMenuItems.clear();
        this.mMenuItems.addAll(builder.itemList);

        this.mColumnCount = builder.columnCount;
        this.mDuration = builder.duration;
        this.mTension = builder.tension;
        this.mFriction = builder.friction;
        this.mHorizontalPadding = builder.horizontalPadding;
        this.mVerticalPadding = builder.verticalPadding;
        this.mPopMenuItemListener = builder.popMenuItemListener;

        mScreenWidth = mActivity.getResources().getDisplayMetrics().widthPixels;
        mScreenHeight = mActivity.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 显示菜单
     */
    public void show() {
        buildAnimateGridLayout();

        if (mAnimateLayout.getParent() != null) {
            ViewGroup viewGroup = (ViewGroup) mAnimateLayout.getParent();
            viewGroup.removeView(mAnimateLayout);
        }

        ViewGroup decorView = (ViewGroup) mActivity.getWindow().getDecorView();
        decorView.addView(mAnimateLayout);

        //执行显示动画
        showSubMenus(mGridLayout);

        isShowing = true;
    }

    /**
     * 隐藏菜单
     */
    public void hide() {
        //先执行消失的动画
        if (isShowing && mGridLayout != null) {
            hideSubMenus(mGridLayout, new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    ViewGroup decorView = (ViewGroup) mActivity.getWindow().getDecorView();
                    decorView.removeView(mAnimateLayout);
                }
            });
            isShowing = false;
        }
    }

    public boolean isShowing() {
        return isShowing;
    }

    /**
     * 构建动画布局
     */
    private void buildAnimateGridLayout() {
        mAnimateLayout = new RelativeLayout(mActivity);


        mAnimateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });

        mGridLayout = new GridLayout(mActivity);
        mGridLayout.setColumnCount(mColumnCount);
        mGridLayout.setBackgroundColor(Color.parseColor("#f0f3f3f3"));
        int hPadding = dp2px(mActivity, mHorizontalPadding);
        int vPadding = dp2px(mActivity, mVerticalPadding);
        int itemWidth = (mScreenWidth - (mColumnCount + 1) * hPadding) / mColumnCount;

        int rowCount = mMenuItems.size() % mColumnCount == 0 ? mMenuItems.size() / mColumnCount :
                mMenuItems.size() / mColumnCount + 1;

        int topMargin = (int) ((mScreenHeight - (itemWidth + vPadding) * rowCount + vPadding) / mMarginTopRemainSpace);

        for (int i = 0; i < mMenuItems.size(); i++) {
            final int position = i;
            PopSubView subView = new PopSubView(mActivity);
            PopMenuItem menuItem = mMenuItems.get(i);
            subView.setPopMenuItem(menuItem);
            subView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mPopMenuItemListener != null) {
                        mPopMenuItemListener.onItemClick(PopMenu.this, position);
                    }
                    hide();
                }
            });

            GridLayout.LayoutParams lp = new GridLayout.LayoutParams();
            lp.width = itemWidth;
            lp.leftMargin = hPadding;

            if (i / mColumnCount == 0) {
                lp.topMargin = topMargin;
            } else {
                lp.topMargin = vPadding;
            }
            mGridLayout.addView(subView, lp);
        }


        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams2.addRule(RelativeLayout.CENTER_HORIZONTAL);

        mAnimateLayout.addView(mGridLayout,layoutParams2);




        mCloseIv = new ImageView(mActivity);
        mCloseIv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        mCloseIv.setImageResource(R.drawable.tabbar_compose_background_icon_close);
        mCloseIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layoutParams.bottomMargin = dp2px(mActivity, 10);
        mAnimateLayout.addView(mCloseIv, layoutParams);
    }

    /**
     * show sub menus with animates
     *
     * @param viewGroup
     */
    private void showSubMenus(ViewGroup viewGroup) {
        if (viewGroup == null) return;
        int childCount = viewGroup.getChildCount();

        for (int i = 0; i < childCount; i++) {
            final View view = viewGroup.getChildAt(i);
              view.setVisibility(View.INVISIBLE);

            animationAction(i, view);

        }
    }

    /**
     * 动画动作
     * @param i
     * @param view
     */
    private void animationAction(int i, final View view) {

        if(mIsmalpositionAnimatOut) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    view.setVisibility(View.VISIBLE);
                    animateViewDirection(view, mScreenHeight, 0, mTension, mFriction);


                }
            }, i * malposition);
        }
        else
        {

            view.setVisibility(View.VISIBLE);
            animateViewDirection(view, mScreenHeight, 0, mTension, mFriction);

        }
    }

    /**
     * hide sub menus with animates
     *
     * @param viewGroup
     * @param listener
     */
    private void hideSubMenus(ViewGroup viewGroup, final AnimatorListenerAdapter listener) {
        if (viewGroup == null) return;
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = viewGroup.getChildAt(i);
            view.animate().translationY(mScreenHeight).setDuration(mDuration).setListener(listener).start();
        }
    }

    /**
     * 弹簧动画
     *
     * @param v        动画View
     * @param from
     * @param to
     * @param tension  拉力系数
     * @param friction 摩擦力系数
     */
    private void animateViewDirection(final View v, float from, float to, double tension, double friction) {
        Spring spring = mSpringSystem.createSpring();
        spring.setCurrentValue(from);
        spring.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(tension, friction));
        spring.addListener(new SimpleSpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                v.setTranslationY((float) spring.getCurrentValue());
            }
        });
        spring.setEndValue(to);
    }

    public static class Builder {

        private Activity activity;
        private int columnCount = DEFAULT_COLUMN_COUNT;
        private List<PopMenuItem> itemList = new ArrayList<>();
        private int duration = DEFAULT_DURATION;
        private double tension = DEFAULT_TENSION;
        private double friction = DEFAULT_FRICTION;
        private int horizontalPadding = DEFAULT_HORIZONTAL_PADDING;
        private int verticalPadding = DEFAULT_VERTICAL_PADDING;
        private PopMenuItemListener popMenuItemListener;

        public Builder attachToActivity(Activity activity) {
            this.activity = activity;
            return this;
        }

        public Builder columnCount(int count) {
            this.columnCount = count;
            return this;
        }

        public Builder addMenuItem(PopMenuItem menuItem) {
            this.itemList.add(menuItem);
            return this;
        }

        public Builder duration(int duration) {
            this.duration = duration;
            return this;
        }

        public Builder tension(double tension) {
            this.tension = tension;
            return this;
        }

        public Builder friction(double friction) {
            this.friction = friction;
            return this;
        }

        public Builder horizontalPadding(int padding) {
            this.horizontalPadding = padding;
            return this;
        }

        public Builder verticalPadding(int padding) {
            this.verticalPadding = padding;
            return this;
        }

        public Builder setOnItemClickListener(PopMenuItemListener listener) {
            this.popMenuItemListener = listener;
            return this;
        }

        public PopMenu build() {
            final PopMenu popMenu = new PopMenu(this);
            return popMenu;
        }
    }

    /**
     * dp 2 px
     *
     * @param context
     * @param dpVal
     * @return
     */
    protected int dp2px(Context context, int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }
}
