package com.example.sampleappcollection.recyclerview.recycler.data;

import java.util.UUID;

/**
 * 描述：实体类
 *
 * @author zhangqin
 * @date 2016/7/7
 */
public class MyData {

    private UUID id;
    private String title;

    private String text;

    public MyData() {

        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
