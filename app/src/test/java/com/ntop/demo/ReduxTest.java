package com.ntop.redux.demo;

import com.ntop.redux.demo.redux.Action;
import com.ntop.redux.demo.redux.Reducer;
import com.ntop.redux.demo.redux.Redux;

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
    public void nodeReducerReducer() {
        String key = "aField";
        Reducer reducer = mockReducer("123");

        Reducer cReducer = Redux.combineReducers(ATestClass.class, new String[]{key}, new Reducer[]{reducer});
        assertNotNull(cReducer);

        ATestClass testClass = new ATestClass();
        Object newState = cReducer.reduce(testClass, mockAction());

        assertTrue(newState instanceof ATestClass);

        assertEquals("123", ((ATestClass)newState).getaField());
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
}
