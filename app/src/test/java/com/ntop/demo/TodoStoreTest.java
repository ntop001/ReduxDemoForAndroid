package com.ntop.demo;

import com.ntop.demo.redux.Reducer;
import com.ntop.demo.redux.Redux;
import com.ntop.demo.todo.AppState;
import com.ntop.demo.todo.TodoStore;
import com.ntop.demo.todo.actions.AddTodo;
import com.ntop.demo.todo.model.Task;
import com.ntop.demo.todo.reducers.FilterReducer;
import com.ntop.demo.todo.reducers.TodoReducer;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by ntop on 16/6/21.
 */
public class TodoStoreTest {


    public void setup() {

    }

    @Test
    public void addTodo() {
        Reducer taskReducer = new TodoReducer();
        Reducer filterReducer = new FilterReducer();
        String[] keys = new String[]{ "filter", "tasks" };
        Reducer[] reducers = new Reducer[] { filterReducer, taskReducer };
        Reducer combineReducer = Redux.combineReducers(AppState.class, keys, reducers);

        TodoStore store = Redux.createStore(TodoStore.class, combineReducer);

        store.dispatch(new AddTodo("write some test"));

        assertEquals(1, store.getState().getTasks().size());

        Task task = store.getState().getTasks().get(0);
        System.out.println("Add task: " + task.getText());

        store.dispatch(new AddTodo("write a second test"));

        assertEquals(2, store.getState().getTasks().size());
    }
}
