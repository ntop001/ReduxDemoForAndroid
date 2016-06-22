package com.ntop.demo.todo.actions;

import com.ntop.demo.redux.Action;

/**
 * Created by ntop on 16/6/21.
 */
public class FilterVisibility implements Action {
    public static final String type = "filter_visibility";
    public enum Visibility { SHOW_ALL, SHOW_COMPLETE, SHOW_ACTIVE }

    private Visibility filter;

    public FilterVisibility(Visibility filter) {
        this.filter = filter;
    }

    @Override
    public String getType() {
        return type;
    }

    public Visibility getFilter() {
        return filter;
    }

    public void setFilter(Visibility filter) {
        this.filter = filter;
    }
}
