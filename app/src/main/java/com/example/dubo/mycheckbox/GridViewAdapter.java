package com.example.dubo.mycheckbox;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.HashMap;

public class GridViewAdapter extends BaseAdapter {
    //用来控制checkBox的选中状况
    private static HashMap<Integer, Boolean> isSelected;
    private Context context;
    private String[] items;
    //传过来的checkbox的选中状况数组
    boolean[] statas;
    //用来导入布局
    private LayoutInflater inflater = null;

    public GridViewAdapter(Context context, String[] items, boolean[] statas) {
        this.context = context;
        this.items = items;
        isSelected = new HashMap<Integer, Boolean>();
        inflater = LayoutInflater.from(context);
        this.statas = statas;
        initDate();
    }

    // 初始化isSelected的数据
    private void initDate() {
        for (int i = 0; i < statas.length; i++) {
            if (statas[i] == true) {
                getIsSelected().put(i, true);
            } else {
                getIsSelected().put(i, false);
            }
        }
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.gridview_item_floor, null);
            holder.tv_floor_num = (TextView) convertView.findViewById(R.id.tv_floor_num);
            holder.item_floor_image = (CheckBox) convertView.findViewById(R.id.item_floor_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //给TextView赋值
        holder.tv_floor_num.setText(items[position]);
        // 根据isSelected来设置checkbox的选中状况
        holder.item_floor_image.setChecked(getIsSelected().get(position));
        if (getIsSelected().get(position) == true) {
            holder.tv_floor_num.setTextColor(Color.parseColor("#ffffff"));
        } else {
            holder.tv_floor_num.setTextColor(Color.parseColor("#000000"));
        }
        return convertView;
    }

    public static class ViewHolder {
        public TextView tv_floor_num;
        public CheckBox item_floor_image;
    }

    public static HashMap<Integer, Boolean> getIsSelected() {
        return isSelected;
    }
}