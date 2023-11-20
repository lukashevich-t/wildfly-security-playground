package org.example.model;

import java.util.concurrent.atomic.AtomicInteger;

public class Device {
    private static final AtomicInteger idGenerator = new AtomicInteger(0);
    private int id;
    private String name;
    private String status;
    private String type;
    private String description;

    public static int generateId() {
        return idGenerator.getAndIncrement();
    }

    public Device() {
    }

    public Device(int id, String name, String status, String type, String description) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.type = type;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}