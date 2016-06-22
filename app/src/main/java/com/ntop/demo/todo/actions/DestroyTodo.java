package com.ntop.demo.todo.actions;

import com.ntop.demo.redux.Action;

/**
 * Created by ntop on 16/6/21.
 */
public class DestroyTodo implements Action {
    public static final String type = "destroy_todo";

    private int index;

    public DestroyTodo(int index) {
        this.index = index;
    }

    @Override
    public String getType() {
        return type;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
