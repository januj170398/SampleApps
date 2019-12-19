package com.example.sampleappcollection.eventbus.eventbus;

/**
 * 定义 MessageEvent 对象
 * <p>
 * @author zhangqin
 * @date 2017/5/13
 */
public class MessageEvent {

    private String id;
    private String name;
    private String message;

    public MessageEvent() {
    }

    public MessageEvent(String id, String name, String message) {
        this.id = id;
        this.name = name;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "MessageEvent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
