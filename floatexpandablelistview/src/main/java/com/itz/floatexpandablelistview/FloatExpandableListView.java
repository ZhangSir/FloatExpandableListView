package com.itz.floatexpandablelistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ExpandableListView;

/**
 * Group条目浮动的ExpandableListView
 * Created by zhangshuo on 2018/2/5.
 */

public class FloatExpandableListView extends ExpandableListView {

    private final String TAG = FloatExpandableListView.class.getSimpleName();

    /**
     * 悬浮显示的floatView
     */
    private View floatView;

    /**
     * floatView的高度
     */
    private int floatViewHeight = 0;
    /**
     * floatView所显示内容对应ExpandableListView中group的position
     */
    private int floatViewGroupPosition = AbsListView.INVALID_POSITION;

    private int tempY = 0;

    private OnFloatViewListener listener;

    public FloatExpandableListView(Context context) {
        super(context);
        init(context);
    }

    public FloatExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FloatExpandableListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        initListener();
    }

    private void initListener() {
        this.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (null == floatView) {
                    return;
                }
                int firstPos = view.pointToPosition(0, 0);// 其实就是firstVisibleItem
                if (firstPos == AdapterView.INVALID_POSITION) // 如果第一个位置值无效
                    return;

                long firstPackedPos = getExpandableListPosition(firstPos);
                int firstChildPos = ExpandableListView.getPackedPositionChild(firstPackedPos);// 获取第一行child的id
                int firstGroupPos = ExpandableListView.getPackedPositionGroup(firstPackedPos);// 获取第一行group的id
                if ((firstGroupPos == AdapterView.INVALID_POSITION || !isGroupExpanded(firstGroupPos))
                        && firstChildPos == AdapterView.INVALID_POSITION) {
                    /*
                     * 显示出来的第一个item既不是group也不是child，这时应该是header，隐藏indicatorGroup
					 */
                    floatView.setVisibility(View.GONE);
                    return;
                } else if (firstChildPos == AdapterView.INVALID_POSITION) {
					/* 显示出来的第一个item是group, 取其高度，用来初始化indicatorGroupHeight */
                    floatViewHeight = getChildAt(0).getHeight();
                    floatView.setVisibility(View.VISIBLE);
                } else {
                    floatView.setVisibility(View.VISIBLE);
                }

                if (floatViewHeight == 0) {
                    return;
                }

                if (firstGroupPos != AdapterView.INVALID_POSITION) {
                    if (firstGroupPos != floatViewGroupPosition) {// 如果指示器显示的不是当前group
                        updateFloatView(firstGroupPos);
                        floatViewGroupPosition = firstGroupPos;
                    }
                }

                /**
                 * calculate point (0,indicatorGroupHeight) 下面是形成往上推出的效果
                 */
                int topOffset = floatViewHeight;
                int secondPos = pointToPosition(0, floatViewHeight + getDividerHeight());// 第二个item的位置
                if (secondPos == AdapterView.INVALID_POSITION) // 如果无效直接返回
                    return;
                long secondPackedPos = getExpandableListPosition(secondPos);
                int secondGroupPos = ExpandableListView.getPackedPositionGroup(secondPackedPos);// 获取第二个group的id
                if (secondGroupPos != AdapterView.INVALID_POSITION && secondGroupPos != floatViewGroupPosition) {// 如果不等于指示器当前的group
                    topOffset = getChildAt(secondPos - getFirstVisiblePosition()).getTop();
                }
                tempY = floatViewHeight - topOffset;
                if (tempY < 0) {
                    tempY = 0;
                }
                // floatView.scrollTo(0, tempY);
                MarginLayoutParams layoutParams = (MarginLayoutParams) floatView.getLayoutParams();
                layoutParams.topMargin = -tempY;
                floatView.setLayoutParams(layoutParams);
            }
        });
    }

    /**
     * 设置FloatView的点击事件，是FloatView兼具GroupItem的点击效果
     */
    private void setFloatViewClickListener() {
        if (null == floatView) {
            return;
        }
        floatView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isGroupExpanded(floatViewGroupPosition)) {
                    collapseGroup(floatViewGroupPosition);
                } else {
                    expandGroup(floatViewGroupPosition, true);
                }
                if(null != listener){
                    listener.onClick(v);
                }
            }
        });
    }

    public void setFloatView(View floatView) {
        this.floatView = floatView;
        this.setFloatViewClickListener();
    }

    public void setListener(OnFloatViewListener listener) {
        this.listener = listener;
    }

    private void updateFloatView(int groupPosition) {
        if (null != listener) {
            listener.onUpdate(groupPosition);
        }
    }

    public interface OnFloatViewListener {
        void onUpdate(int groupPosition);
        void onClick(View v);
    }
}
