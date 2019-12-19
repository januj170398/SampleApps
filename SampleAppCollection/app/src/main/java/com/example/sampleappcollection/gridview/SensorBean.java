package com.example.sampleappcollection.gridview;

/**
 * 传感器信息容器类
 *
 * @author asus
 */
public class SensorBean {
    // 传感器名称
    private String mNameString = "";
    // 传感器的值
    private int mValue = 0;
    // 传感器阀值的最小值
    private int minValue = Integer.MIN_VALUE;
    // 传感器阀值的最大值
    private int maxValue = Integer.MAX_VALUE;

    /**
     * 构造函数
     *
     * @param name 传感器名称
     */
    public SensorBean(String name) {
        this.mNameString = name;
    }

    /**
     * 获取传感器名称
     *
     * @return 传感器名称
     */
    public String getName() {
        return mNameString;
    }

    /**
     * 设置传感器名称
     *
     * @param name 传感器名称
     */
    public void setName(String name) {
        this.mNameString = name;
    }

    /**
     * 获取传感器的值
     *
     * @return 传感器值
     */
    public int getValue() {
        return mValue;
    }

    /**
     * 设置传感器的值
     *
     * @param value 传感器值
     */
    public void setValue(int value) {
        this.mValue = value;
    }

    /**
     * 获取传感器最小值
     *
     * @return 传感器最小值
     */
    public int getMinValue() {
        return minValue;
    }

    /**
     * 设置传感器最小值
     *
     * @param minValue 传感器最小值
     */
    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    /**
     * 获取传感器最大值
     *
     * @return 传感器最大值
     */
    public int getMaxValue() {
        return maxValue;
    }

    /**
     * 设置传感器最大值
     *
     * @param maxValue 传感器最大值
     */
    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

}
