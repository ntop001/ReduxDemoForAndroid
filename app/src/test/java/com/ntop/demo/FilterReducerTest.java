package com.ntop.demo;

import com.ntop.demo.redux.Action;
import com.ntop.demo.todo.actions.FilterVisibility;
import com.ntop.demo.todo.actions.FilterVisibility.Visibility;
import com.ntop.demo.todo.reducers.FilterReducer;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by ntop on 16/6/22.
 */
public class FilterReducerTest {

    @Test
    public void reduce_nullInitialState() {
        FilterReducer filterReducer =  new FilterReducer();
        Visibility state = filterReducer.reduce(null, new Action() {
            @Override
            public String getType() {
                return "Random:" + System.currentTimeMillis();
            }
        });
        assertEquals(Visibility.SHOW_ALL, state);
    }

    @Test
    public void reduce_filterActionShowActive() {
        FilterReducer filterReducer =  new FilterReducer();
        Visibility state = filterReducer.reduce(Visibility.SHOW_ALL, mockAction(Visibility.SHOW_ACTIVE));
        assertEquals(Visibility.SHOW_ACTIVE, state);
    }


    private Action mockAction(Visibility visibility) {
        return new FilterVisibility(visibility);
    }
}
