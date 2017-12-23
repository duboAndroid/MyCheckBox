package com.example.dubo.mycheckbox;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DialogGridView extends Activity {
    private TextView tv_count;
    private CheckBox select_true;//全选按钮
    private GridView gridview_floor;
    private String[] floorArray = {"1", "2", "1asdfa11asdfa11asdfa13", "4", "5", "6", "71asdfa11asdfa11asdfa1", "8", "9", "10", "1asdfa11asdfa11asdfa11asdfa1", "12"};//数据源
    private GridViewAdapter gridViewAdapter;//适配器
    List<String> listItemID = new ArrayList<String>();//存放选中的楼层内容
    private ArrayList<Integer> list;//存放已经选中的楼层下标
    private boolean[] floorState;//存放item的选中状态集合

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_count = (TextView) findViewById(R.id.tv_count);
        floorState = new boolean[floorArray.length];
        tv_count.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showdialog();
            }
        });
    }

    //弹出对话框方法
    public void showdialog() {
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setContentView(R.layout.checkdialog);
        select_true = (CheckBox) window.findViewById(R.id.select_true);
        gridview_floor = (GridView) window.findViewById(R.id.gridview_floor);
        Button bt_OK = (Button) window.findViewById(R.id.bt_OK);
        gridViewAdapter = new GridViewAdapter(DialogGridView.this, floorArray, floorState);
        gridview_floor.setAdapter(gridViewAdapter);
        String floor = tv_count.getText().toString();
        //截取字符串并将楼层数存到数组中
        String[] chrstr = floor.split(",");
        if (chrstr.length == floorArray.length) {
            select_true.setChecked(true);
        } else {
            select_true.setChecked(false);
        }
        //全选按钮监听事件
        select_true.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (select_true.isChecked()) {
                    // 遍历list的长度，将adapter中的map值全部设为true
                    for (int i = 0; i < floorArray.length; i++) {
                        GridViewAdapter.getIsSelected().put(i, true);
                    }
                    // 刷新listview
                    gridViewAdapter.notifyDataSetChanged();
                } else {
                    for (int i = 0; i < floorArray.length; i++) {
                        if (GridViewAdapter.getIsSelected().get(i)) {
                            GridViewAdapter.getIsSelected().put(i, false);
                        }
                    }
                    gridViewAdapter.notifyDataSetChanged();
                }
            }
        });
        //gridview的监听事件
        gridview_floor.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GridViewAdapter.ViewHolder holder = (GridViewAdapter.ViewHolder) view.getTag();
                //改变checkBox的选择状态
                holder.item_floor_image.toggle();
                gridViewAdapter.getIsSelected().put(position, holder.item_floor_image.isChecked());
                if (holder.item_floor_image.isChecked() == true) {
                    holder.tv_floor_num.setTextColor(Color.parseColor("#ffffff"));
                    //list  用来存放已经选中的楼层数
                    list = new ArrayList<Integer>();
                    for (int i = 0; i < gridViewAdapter.getIsSelected().size(); i++) {
                        if (gridViewAdapter.getIsSelected().get(i)) {
                            list.add(i);
                        }
                    }
                    //判断已经选中的楼层集合是否与总楼层数集合大小相等，用来控制全选按钮的状态
                    if (list.size() == floorArray.length) {
                        select_true.setChecked(true);
                    } else {
                        select_true.setChecked(false);
                    }
                } else {
                    holder.tv_floor_num.setTextColor(Color.parseColor("#000000"));
                    list = new ArrayList<Integer>();
                    for (int i = 0; i < gridViewAdapter.getIsSelected().size(); i++) {
                        if (gridViewAdapter.getIsSelected().get(i)) {
                            list.add(i);
                        }
                    }
                    if (list.size() == floorArray.length) {
                        select_true.setChecked(true);
                    } else {
                        select_true.setChecked(false);
                    }
                }
            }
        });
        //确定按钮的监听事件
        bt_OK.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                listItemID.clear();
                String s = "";
                list = new ArrayList<Integer>();
                for (int i = 0; i < gridViewAdapter.getIsSelected().size(); i++) {
                    if (gridViewAdapter.getIsSelected().get(i)) {
                        //得到选中的楼层数
                        listItemID.add(floorArray[i]);
                        list.add(i);
                    }
                }
                floorState = new boolean[floorArray.length];
                if (list != null) {
                    for (int i = 0; i < list.size(); i++) {
                        //将选中的楼层的状态设置为true
                        floorState[list.get(i)] = true;
                    }
                } else {
                    floorState[0] = true;
                }

                if (listItemID.size() == 0) {
                    tv_count.setText("暂无数据");
                } else {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < listItemID.size(); i++) {
                        if (i == listItemID.size() - 1) {
                            sb.append(listItemID.get(i));
                        } else {
                            sb.append(listItemID.get(i) + ",");
                        }
                    }
                    tv_count.setText("您选择了：" + sb.toString() + "");
                }
                dialog.dismiss();
            }
        });
    }
}