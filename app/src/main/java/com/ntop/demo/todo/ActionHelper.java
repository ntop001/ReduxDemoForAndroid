package com.ntop.demo.todo;

import com.ntop.demo.todo.actions.AddTodo;
import com.ntop.demo.todo.actions.CompleteTodo;
import com.ntop.demo.todo.actions.DestroyTodo;
import com.ntop.demo.todo.actions.FilterVisibility;

/**
 * Created by ntop on 16/6/22.
 */
public class ActionHelper {
    private TodoStore todoStore;

    private ActionHelper(TodoStore todoStore) {
        this.todoStore = todoStore;
    }

    public static ActionHelper newActionHelper(TodoStore todoStore) {
        return new ActionHelper(todoStore);
    }

    public void addTodo(String text) {
        todoStore.dispatch(new AddTodo(text));
    }

    public void completeTodo(int index) {
        todoStore.dispatch(new CompleteTodo(index));
    }

    public void destroyTodo(int index) {
        todoStore.dispatch(new DestroyTodo(index));
    }

    public void filter(FilterVisibility.Visibility visibility) {
        todoStore.dispatch(new FilterVisibility(visibility));
    }
}
