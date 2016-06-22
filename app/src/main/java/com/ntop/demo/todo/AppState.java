package com.ntop.demo.todo;

import com.ntop.demo.redux.State;
import com.ntop.demo.todo.actions.FilterVisibility.Visibility;
import com.ntop.demo.todo.model.Task;

import java.util.Collections;
import java.util.List;

/**
 * Created by ntop on 16/6/21.
 */
public class AppState implements State {
    private Visibility filter;
    private List<Task> tasks;

    public AppState() {
        filter = Visibility.SHOW_ALL;
        tasks = Collections.EMPTY_LIST;
    }

    public Visibility getFilter() {
        return filter;
    }

    public List<Task> getTasks() {
        return tasks;
    }
}
