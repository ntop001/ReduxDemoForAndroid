package com.ntop.demo.tools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ntop on 16/6/22.
 */
public class CollectionTool {
    public static  <E> List<E> filter(List<E> list, Filter filter) {
        List<E> newList = new ArrayList<>();
        for(E e : list) {
            if (filter.apply(e)) {
                newList.add(e);
            }
        }

        return newList;
    }

    public static interface Filter<E> {
        public boolean apply(E e);
    }
}
