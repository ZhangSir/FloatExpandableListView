package com.itz.testFEListview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by zhangshuo on 2018/2/6.
 */

public class MainAdapter extends BaseExpandableListAdapter {

        private Context mContext;
        private List<Model> listModels;

        public MainAdapter(Context con, List<Model> listModels) {
            this.mContext = con;
            this.listModels = listModels;
        }

        @Override
        public int getGroupCount() {
            // TODO Auto-generated method stub
            return listModels.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            // TODO Auto-generated method stub
            try {
                if (listModels.size() == 0 || null == listModels.get(groupPosition)) {
                    return 0;
                }
                if (null == listModels.get(groupPosition).getListSubModels()
                        || listModels.get(groupPosition).getListSubModels().size() == 0) {
                    return 0;
                }
                return listModels.get(groupPosition).getListSubModels().size();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                return 0;
            }
        }

        @Override
        public Object getGroup(int groupPosition) {
            // TODO Auto-generated method stub
            return listModels.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            // TODO Auto-generated method stub
            try {
                if (listModels.size() == 0 || null == listModels.get(groupPosition)
                        || null == listModels.get(groupPosition).getListSubModels()
                        || listModels.get(groupPosition).getListSubModels().size() == 0) {
                    return null;
                }
                return listModels.get(groupPosition).getListSubModels().get(childPosition);
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public long getGroupId(int groupPosition) {
            // TODO Auto-generated method stub
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            // TODO Auto-generated method stub
            return childPosition;
        }

        /**
         * 指定位置相应的组视图
         */
        @Override
        public boolean hasStableIds() {
            // TODO Auto-generated method stub
            return true;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            GroupHolder holder = null;
            if (convertView == null) {
                holder = new GroupHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_group_item, parent,
                        false);
                holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_group_item);
                convertView.setTag(holder);
            } else {
                holder = (GroupHolder) convertView.getTag();
            }

            final Model model = listModels.get(groupPosition);
            holder.tvTitle.setText(model.getName());
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
                                 ViewGroup parent) {
            // TODO Auto-generated method stub
            ChildHolder holder = null;
            if (convertView == null) {
                holder = new ChildHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_child_item, parent,
                        false);
                holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_child_item);
                convertView.setTag(holder);
            } else {
                holder = (ChildHolder) convertView.getTag();
            }
            SubModel subModel = listModels.get(groupPosition).getListSubModels().get(childPosition);
            holder.tvTitle.setText(subModel.getName());

            return convertView;
        }

        /**
         * 当选择子节点的时候，调用该方法
         */
        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            // TODO Auto-generated method stub
            return true;
        }

        @Override
        public void notifyDataSetChanged() {
            // TODO Auto-generated method stub
            super.notifyDataSetChanged();
			/* 数据更改之后，重新刷新headerView显示的内容 */
//            int firstPos = mListView.pointToPosition(0, 0);// 其实就是firstVisibleItem
//            if (firstPos == AdapterView.INVALID_POSITION) // 如果第一个位置值无效
//                return;
//            long firstPackedPos = mListView.getExpandableListPosition(firstPos);
//            int firstGroupPos = ExpandableListView.getPackedPositionGroup(firstPackedPos);// 获取第一行group的id
//            if (firstGroupPos != AdapterView.INVALID_POSITION) {
//                refreshHeaderViewData(firstGroupPos);
//                headerViewPos = firstGroupPos;
//            }

        }

        class GroupHolder {
            TextView tvTitle;
        }

        class ChildHolder {
            TextView tvTitle;
        }
}
