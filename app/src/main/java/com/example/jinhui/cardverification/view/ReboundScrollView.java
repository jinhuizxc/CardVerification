package com.example.jinhui.cardverification.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ExpandableListView;
import android.widget.ScrollView;

/**
 * Created by jinhui on 2018/1/3.
 * Email:1004260403@qq.com
 * <p>
 * 有弹性的ScrollView
 */

public class ReboundScrollView extends ScrollView {

    private static final String TAG = "ReboundScrollView";

    private static final float MOVE_FACTOR = 0.2f;
    private static final int ANIM_TIME = 300;
    private View contentView;
    private float startY;
    private Rect originalRect = new Rect();
    private boolean canPullDown = false;
    private boolean canPullUp = false;
    private boolean isMoved = false;
    private OnTouch onTouch;


    public ReboundScrollView(Context context) {
        super(context);
    }

    public ReboundScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ReboundScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * onFinishInflate
     * 当View中所有的子控件均被映射成xml后触发
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.e(TAG, "onFinishInflate");
        if (getChildCount() > 0) {
            contentView = getChildAt(0);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.e(TAG, "onLayout");
        if (contentView == null) return;
        originalRect.set(contentView.getLeft(), contentView.getTop(),
                contentView.getRight(), contentView.getBottom());
    }

    /**
     * 在触摸事件中, 处理上拉和下拉的逻辑
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        setOnTouchs();
        if (contentView == null) {
            return super.dispatchTouchEvent(ev);
        }
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                canPullDown = isCanPullDown();
                canPullUp = isCanPullUp();
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_UP:
                if (!isMoved)
                    break;
                TranslateAnimation animation = new TranslateAnimation(0, 0,
                        contentView.getTop(), originalRect.top);
                animation.setDuration(ANIM_TIME);
                contentView.startAnimation(animation);
                contentView.layout(originalRect.left, originalRect.top,
                        originalRect.right, originalRect.bottom);
                canPullDown = false;
                canPullUp = false;
                isMoved = false;
                break;
            case MotionEvent.ACTION_MOVE:
                if (!canPullDown && !canPullUp) {
                    startY = ev.getY();
                    canPullDown = isCanPullDown();
                    canPullUp = isCanPullUp();
                    break;
                }

                float nowY = ev.getY();
                int deltaY = (int) (nowY - startY);
                boolean shouldMove = (canPullDown && deltaY > 0)
                        || (canPullUp && deltaY < 0) || (canPullUp & canPullDown);
                if (shouldMove) {
                    int offset = (int) (deltaY * MOVE_FACTOR);
                    contentView.layout(originalRect.left,
                            originalRect.top + offset, originalRect.right,
                            originalRect.bottom + offset);
                    isMoved = true;
                }
                break;
            default:
                break;


        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 判断是否滚动到底部
     *
     * @return
     */
    private boolean isCanPullUp() {
        Log.e(TAG, "contentView.getHeight() =" + contentView.getHeight() + "," + getHeight() + "," + getScrollY());
        return contentView.getHeight() <= getHeight() + getScrollY();
    }

    /**
     * 判断是否滚动到顶部
     *
     * @return
     */
    private boolean isCanPullDown() {
        return getScrollY() == 0 || contentView.getHeight() < getHeight() + getScrollY();
    }

    private void setOnTouchs() {
        if (onTouch != null) {
            onTouch.setOnTouch();
        }
    }

    // 接口回调
    public interface OnTouch {
        void setOnTouch();
    }

    public void setOnTouch(OnTouch onTouch) {
        this.onTouch = onTouch;
    }
}
