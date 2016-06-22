package com.ntop.demo;

import com.ntop.demo.redux.Action;
import com.ntop.demo.todo.actions.AddTodo;
import com.ntop.demo.todo.actions.CompleteTodo;
import com.ntop.demo.todo.actions.DestroyTodo;
import com.ntop.demo.todo.model.Task;
import com.ntop.demo.todo.reducers.TodoReducer;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by ntop on 16/6/22.
 */
public class TodoReducerTest {

    @Test
    public void reduce_nullInitialState() {
        TodoReducer todoReducer =  new TodoReducer();
        List<Task> state = todoReducer.reduce(null, mockUnrelatedAction());

        assertEquals(state, Collections.EMPTY_LIST);
    }

    @Test
    public void reduce_add1Todo() {
        TodoReducer todoReducer = new TodoReducer();
        List<Task> state = todoReducer.reduce(Collections.EMPTY_LIST, mockAddAction("todo.1"));
        assertEquals(1, state.size());
        assertEquals("todo.1", state.get(0).getText());
    }

    @Test
    public void reduce_add2Todo() {
        TodoReducer todoReducer = new TodoReducer();
        List<Task> state1 = todoReducer.reduce(Collections.EMPTY_LIST, mockAddAction("todo.1"));
        List<Task> state2 = todoReducer.reduce(state1, mockAddAction("todo.2"));

        assertEquals(2, state2.size());
        assertEquals("todo.1", state2.get(0).getText());
        assertEquals("todo.2", state2.get(1).getText());
    }

    @Test
    public void reduce_complete() {
        TodoReducer todoReducer = new TodoReducer();
        List<Task> state =  new ArrayList<>();
        state.add(new Task("todo.1", false));

        List<Task> newState = todoReducer.reduce(state, mockCompleteAction(0));
        assertEquals(1, newState.size());
        assertEquals(true, newState.get(0).isComplete());
    }

    private Action mockAddAction(String text) {
        return new AddTodo(text);
    }

    private Action mockCompleteAction(int index) {
        return new CompleteTodo(index);
    }

    private Action mockDestroyAction(int index) {
        return new DestroyTodo(index);
    }

    private Action mockUnrelatedAction() {
        return new Action() {
            @Override
            public String getType() {
                return "Random:" + System.currentTimeMillis();
            }
        };
    }
}
