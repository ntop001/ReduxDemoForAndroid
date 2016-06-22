package com.ntop.demo.redux;

/**
 * Created by ntop on 16/6/21.
 */
public interface Store<T> {
    public T  getState();
    public void dispatch(Action action);
    public void subscribe(Observer object);
    public void unSubscribe(Observer object);
}
