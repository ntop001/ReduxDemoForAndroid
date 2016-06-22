package com.ntop.demo.todo.reducers;

import com.ntop.demo.redux.Action;
import com.ntop.demo.redux.Reducer;
import com.ntop.demo.todo.actions.FilterVisibility.Visibility;
import com.ntop.demo.todo.actions.FilterVisibility;

/**
 * Created by ntop on 16/6/21.
 */
public class FilterReducer implements Reducer<Visibility, Action> {
    private static final Visibility initialState = Visibility.SHOW_ALL;

    @Override
    public Visibility reduce(Visibility state, Action action) {
        if (state == null) {
            state = initialState;
        }

        switch (action.getType()) {
            case FilterVisibility.type: {
                return filter(state, ((FilterVisibility)action).getFilter());
            }
            default:
                return state;
        }
    }

    private Visibility filter(Visibility state, Visibility filter) {
        return filter;
    }
}
