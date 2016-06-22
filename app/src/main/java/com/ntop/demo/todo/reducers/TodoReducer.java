package com.ntop.demo.todo.reducers;

import com.ntop.demo.redux.Action;
import com.ntop.demo.redux.Reducer;
import com.ntop.demo.todo.actions.AddTodo;
import com.ntop.demo.todo.actions.CompleteTodo;
import com.ntop.demo.todo.actions.DestroyTodo;
import com.ntop.demo.todo.model.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ntop on 16/6/21.
 */
public class TodoReducer implements Reducer<List<Task>, Action> {
    private static final List<Task> initialState = Collections.EMPTY_LIST;

    @Override
    public List<Task> reduce(List<Task> state, Action action) {
        if (state == null) {
            state = initialState;
        }

        switch (action.getType()){
            case AddTodo.type: {
                return addTodo(state, ((AddTodo) action).getText());
            }
            case CompleteTodo.type:{
                return completeTodo(state, ((CompleteTodo)action).getIndex());
            }
            case DestroyTodo.type:{
                return destroyTodo(state, ((DestroyTodo)action).getIndex());
            }
            default:
                return state;
        }
    }

    private List<Task> addTodo(List<Task> tasks, String taskName) {
        List<Task> newTask = new ArrayList<>(tasks);
        newTask.add(new Task(taskName, false));
        return newTask;
    }

    private List<Task> completeTodo(List<Task> tasks, int id) {
        Task task = tasks.get(id);
        List<Task> newTask = new ArrayList<>();

        int size = tasks.size();
        for(int i = 0; i < size; i++) {
            if (i == id) {
                newTask.add(new Task(task.getText(), true));
            } else {
                newTask.add(tasks.get(i));
            }
        }

        return newTask;
    }

    private List<Task> destroyTodo(List<Task> tasks, int id) {
        List<Task> newTask = new ArrayList<>();

        int size = tasks.size();
        for(int i = 0; i < size; i++) {
            if (i != id) {
                newTask.add(tasks.get(i));
            }
        }

        return newTask;
    }
}
