package com.ntop.demo.redux;

/**
 * Created by ntop on 16/6/21.
 */
public interface Reducer<T, K extends Action> {
    public T reduce(T state, K action);
}
