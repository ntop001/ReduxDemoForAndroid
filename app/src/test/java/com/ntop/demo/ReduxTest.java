package com.ntop.demo;

import com.ntop.demo.redux.Action;
import com.ntop.demo.redux.Observer;
import com.ntop.demo.redux.Reducer;
import com.ntop.demo.redux.Redux;
import com.ntop.demo.redux.Store;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ntop on 16/6/21.
 */
public class ReduxTest {

    @Test
    public void combineReducer() {
        String key = "aField";
        Reducer reducer = mockReducer(null);

        Reducer cReducer = Redux.combineReducers(ATestClass.class, new String[]{key}, new Reducer[]{reducer});
        assertNotNull(cReducer);
    }

    @Test
    public void combineReducerReducer() {
        String key = "aField";
        Reducer reducer = mockReducer("123");

        Reducer cReducer = Redux.combineReducers(ATestClass.class, new String[]{key}, new Reducer[]{reducer});
        assertNotNull(cReducer);

        ATestClass testClass = new ATestClass();
        Object newState = cReducer.reduce(testClass, mockAction());

        assertTrue(newState instanceof ATestClass);

        assertEquals("123", ((ATestClass) newState).getaField());
    }

    @Test
    public void createStore() {
        Reducer reducer = mockReducer("some string");
        Store store = Redux.createStore(AStore.class, reducer);

        assertNotNull(store);
        assertTrue( store instanceof AStore);
    }

    /*
    @Test
    public void createStore() {
        fail();
    }*/

    private Action mockAction() {
        return new Action() {
            @Override
            public String getType() {
                return null;
            }
        };
    }

    private Reducer mockReducer(final String result) {
        return new Reducer() {
            @Override
            public Object reduce(Object state, Action action) {
                return result;
            }
        };
    }

    public static class ATestClass {
        private String aField;

        public String getaField() {
            return aField;
        }
    }

    public static class AStore implements Store {
        public AStore(Reducer reducer) {
        }

        @Override
        public Object getState() {
            return null;
        }

        @Override
        public void dispatch(Action action) {

        }

        @Override
        public void subscribe(Observer object) {

        }

        @Override
        public void unSubscribe(Observer object) {

        }
    }
}
