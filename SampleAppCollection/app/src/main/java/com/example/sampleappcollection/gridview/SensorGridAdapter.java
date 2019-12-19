package com.example.sampleappcollection.gridview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sampleappcollection.R;

import java.util.List;

/**
 * 描述：
 *
 * @author 张钦
 * @date 2017/3/10
 */
public class SensorGridAdapter extends ArrayAdapter<SensorBean> {

    private static class ViewHolder {
        LinearLayout mBgLayout;// 背景布局
        TextView mNameTV; // 显示传感器名称
        TextView mStautsTV;// 显示传感器状态
        TextView mSetValeuTV;// 显示传感器的阀值
        TextView mValueTV;// 显示传感器的值
    }

    // 传感器值的集合
    private List<SensorBean> mDataArray;
    private Context mContext;
    private LayoutInflater mInflater;

    public SensorGridAdapter(Context context, List<SensorBean> array) {
        super(context, 0, array);
        mContext = context;
        mDataArray = array;
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mDataArray.size();
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public SensorBean getItem(int position) {
        return mDataArray.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        SensorBean itemInfo = getItem(position);
        ViewHolder holder = null;

        if (convertView == null){
            convertView = mInflater.inflate(R.layout.item_sensor_grid,
                    parent, false);
            holder = new ViewHolder();
            // 界面初始化
            holder.mBgLayout = convertView
                    .findViewById(R.id.sensor_item_layout);
            holder.mNameTV = convertView
                    .findViewById(R.id.sensor_name_text);
            holder.mStautsTV = convertView
                    .findViewById(R.id.status_text);
            holder.mSetValeuTV = convertView
                    .findViewById(R.id.set_value_text);
            holder.mValueTV = convertView
                    .findViewById(R.id.sensor_value_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(itemInfo != null){
            holder.mNameTV.setText(itemInfo.getName());
            holder.mValueTV.setText("" + itemInfo.getValue());

            // 显示设定值内容
            String setStr = "设定值：";
            final int invalidMin = Integer.MIN_VALUE;
            int minV = itemInfo.getMinValue();
            int maxV = itemInfo.getMaxValue();
            if (minV > invalidMin && maxV > invalidMin) {
                setStr += minV + "~" + maxV;
            } else if (minV > invalidMin) {
                setStr += ">" + minV;
            } else if (maxV > invalidMin) {
                setStr += "<" + maxV;
            }
            holder.mSetValeuTV.setText(setStr);

            // 根据传感器的值来判断当前是否应该告警
            boolean isWarning = false;
            int value = itemInfo.getValue();
            if (value > invalidMin
                    && ((minV > invalidMin || maxV > invalidMin))) {
                if (minV > invalidMin && value < minV) {
                    isWarning = true;
                }
                if (maxV > invalidMin && value > maxV) {
                    isWarning = true;
                }
            }

            if (isWarning) {
                // 如果需要告警，背景则显示红色
                holder.mBgLayout.setBackgroundResource(R.color.card_bg_red);
                holder.mStautsTV.setText("预警");
            } else {
                // 不必告警则显示绿色
                holder.mBgLayout
                        .setBackgroundResource(R.color.card_bg_green);
                holder.mStautsTV.setText("正常");
            }
        }

        return convertView;
    }
}
