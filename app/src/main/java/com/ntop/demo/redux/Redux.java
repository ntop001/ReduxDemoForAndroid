package com.ntop.demo.redux;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ntop on 16/6/21.
 */
public class Redux {
    /**
     * 默认规则: 每个Reducer负责处理State对象的一个属性字段,并约定
     * State的对象的属性命名需要和Reducer的key匹配.
     * @param parent
     * @param keys
     * @param reducers
     */
    public static <T> Reducer combineReducers(Class<T> parent, String[] keys, Reducer[] reducers) {
        return new NodeReducer(parent, keys, reducers);
    }

    public static <T> T createStore(Class<T> store, Reducer reducer) {
        try {
            Constructor<T> constructor = store.getConstructor(Reducer.class);
            return constructor.newInstance(reducer);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Node<T, K extends Action>  define generic type
     * Reducer<T, K> use it
     * Created by ntop on 16/6/21.
     */
    static class NodeReducer<T, K extends Action> implements Reducer<T, K> {
        private Map<String, Reducer> reducerMap = new HashMap<>();
        private Class<T> parent;

        public NodeReducer(Class<T> parent, String[] keys, Reducer[] reducers) {
            assert keys.length == reducers.length;

            this.parent = parent;
            for(int i = 0; i < keys.length; i++) {
                reducerMap.put(keys[i], reducers[i]);
            }
        }

        @Override
        public T reduce(T state, K action) {
            try {
                T newT = newT();
                for (Map.Entry<String, Reducer> entry : reducerMap.entrySet()) {
                    Object oldState = getT(state, entry.getKey());
                    Object newState = entry.getValue().reduce(oldState, action);
                    setT(newT, entry.getKey(), newState);
                }

                return newT;
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return state;
        }

        private void reduce(T t, String field, Reducer reducer, K action) throws Exception {
            Object oldState = getT(t, field);
            Object newState = reducer.reduce(oldState, action);
            setT(t, field, newState);
        }

        private T newT() throws Exception {
            return parent.newInstance();
        }

        private Object getT(T t, String field) throws Exception {
            Field prop = parent.getDeclaredField(field);
            prop.setAccessible(true);
            return prop.get(t);
        }

        private void setT(T t, String field, Object state) throws Exception {
            Field prop = parent.getDeclaredField(field);
            prop.setAccessible(true);
            prop.set(t, state);
        }
    }

}
