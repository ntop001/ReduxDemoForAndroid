package com.ntop.demo.todo.actions;

import com.ntop.demo.redux.Action;

/**
 * Created by ntop on 16/6/21.
 */
public class CompleteTodo implements Action {
    public static final String type = "complete_todo";

    private int index;

    public CompleteTodo(int index) {
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
