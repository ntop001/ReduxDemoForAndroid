package com.ntop.demo.todo;

import com.ntop.demo.redux.Action;
import com.ntop.demo.redux.Observer;
import com.ntop.demo.redux.Reducer;
import com.ntop.demo.redux.Store;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ntop on 16/6/21.
 */
public class TodoStore implements Store<AppState> {
    private List<Observer> observers = new ArrayList<>();
    private AppState currentState = new AppState();
    private Reducer reducer;

    public TodoStore(Reducer reducer) {
        this.reducer = reducer;
    }

    @Override
    public AppState getState() {
        return currentState;
    }

    /**
     * middleware
     * @param action
     */
    @Override
    public void dispatch(Action action) {
        currentState = (AppState)reducer.reduce(currentState, action);
        notifyChange();
    }

    @Override
    public void subscribe(Observer object) {
        if (!observers.contains(object)) observers.add(object);
    }

    @Override
    public void unSubscribe(Observer object) {
        observers.remove(object);
    }

    private void notifyChange() {
        for(Observer observer : observers) {
            observer.update();
        }
    }
}
