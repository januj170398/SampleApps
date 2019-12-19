package com.example.sampleappcollection.retrofit.model;

/**
 * 描述：
 *
 * @author zhangqin
 * @date 2017/5/16
 */
public class RequestModel {

    /**
     * uuid : 92770f95-0b9e-4655-9277-940233053d1a
     * name : 码农Mrz
     * value : www.sdwfqin.com
     * time : 2017-05-16 18:07:09
     */

    private String uuid;
    private String name;
    private String value;
    private String time;

    @Override
    public String toString() {
        return "RequestModel{" +
                "uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
