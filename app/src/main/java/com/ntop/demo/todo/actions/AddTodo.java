package com.ntop.demo.todo.actions;

import com.ntop.demo.redux.Action;

/**
 * Created by ntop on 16/6/21.
 */
public class AddTodo implements Action {
    public static final String type = "add_todo";

    private String text;

    public AddTodo(){}

    public AddTodo(String text) {
        this.text = text;
    }

    @Override
    public String getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
