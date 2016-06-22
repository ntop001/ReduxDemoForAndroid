package com.ntop.demo.todo.model;

/**
 * Created by ntop on 16/6/21.
 */
public class Task {
    private final String name;
    private final boolean state;

    public Task(String name, boolean complete) {
        this.name = name;
        this.state = complete;
    }

    public String getText() {
        return name;
    }

    public boolean isComplete() {
        return state;
    }
}
