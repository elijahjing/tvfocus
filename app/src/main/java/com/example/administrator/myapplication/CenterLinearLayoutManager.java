package com.example.administrator.myapplication;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 中间滑动可以快速滚动
 */
public class CenterLinearLayoutManager extends LinearLayoutManager {
    private View mSelectedView;
    private boolean isFirst = true;
    int visibleSize = 0;
    private OnChildSelectedListener mChildSelectedListener = null;

    private SelectionNotifier mSelectionNotifier;
    private boolean isScrolling;


    public CenterLinearLayoutManager(Context context) {
        super(context);
    }

    @Override
    public boolean requestChildRectangleOnScreen(final RecyclerView parent, View child, Rect rect, boolean immediate, boolean focusedChildVisible) {
        final int childLeft = child.getLeft() + rect.left;
        final int childTop = child.getTop() + rect.top;
        final int childRight = childLeft + rect.width();
        final int childBottom = childTop + rect.height();
        int centerB;
        int centerT;
        mSelectedView = child;
        if (mSelectionNotifier == null) {
            mSelectionNotifier = new SelectionNotifier();
        }

        if (isFirst) {
            visibleSize = findLastVisibleItemPosition();
            parent.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        isScrolling = false;
                        parent.post(mSelectionNotifier);
                    } else if (newState == RecyclerView.SCROLL_STATE_SETTLING) {
                        //相当于长按  或者  快速滑动吧
                        isScrolling = true;
                    } else {
                        isScrolling = true;
                    }
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                }
            });


            isFirst = false;
        }
        int itemCount = getItemCount();
        int curPosition = getPosition(child);
        int centerP = findFirstVisibleItemPosition() + visibleSize / 2;
        switch (getOrientation()) {
            case HORIZONTAL:
                centerB = child.getWidth() * (visibleSize / 2);
                centerT = child.getWidth() * (visibleSize / 2 + 1);

                if (curPosition > centerP && findLastVisibleItemPosition() < itemCount - 1 && childLeft > centerB) {
                    parent.smoothScrollBy(childLeft - centerB, 0);
                    return true;
                } else if (curPosition < centerP && findFirstVisibleItemPosition() > 0 && childRight < centerT) {
                    parent.smoothScrollBy(childRight - centerT, 0);
                    return true;
                }
                break;
            case VERTICAL:
                centerB = child.getHeight() * (visibleSize / 2 + 1);
                centerT = child.getHeight() * (visibleSize / 2);
                if (curPosition > centerP && findLastVisibleItemPosition() < itemCount - 1 && childBottom > centerB) {
                    parent.smoothScrollBy(0, childBottom - centerB);
                    return true;
                } else if (curPosition < centerP && findFirstVisibleItemPosition() > 0 && childTop < centerT) {
                    parent.smoothScrollBy(0, childTop - centerT);
                    return true;
                }
                break;
            default:
                break;
        }
        parent.post(mSelectionNotifier);
        return false;
    }

    /**
     * 焦点搜索失败处理.
     */
    @Override
    public View onFocusSearchFailed(View focused, int focusDirection, RecyclerView.Recycler recycler,
                                    RecyclerView.State state) {
        return focused;
    }

    private class SelectionNotifier implements Runnable {
        @Override
        public void run() {
            fireOnSelected();
        }
    }

    public View getSelectedView() {
        return mSelectedView;
    }


    private void fireOnSelected() {
        if (mChildSelectedListener != null) {
            int pos = getPosition(getSelectedView());
            mChildSelectedListener.onChildSelected( getSelectedView(), pos);
        }
    }

    public void setOnChildSelectedListener(OnChildSelectedListener listener) {
        mChildSelectedListener = listener;
    }
}
