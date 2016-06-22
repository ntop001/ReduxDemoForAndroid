*本篇是基于 [Android Flux](http://www.jianshu.com/p/896ce1a8e4ed) 背景写的关于Redux在Android上应用的一文，需要提前了解一些Flux的知识。*

## Redux
---
Redux 是 Flux 模式的一种实现,目前在Flux社区比较热. Redux模式和Flux的思想是类似的，见下面的架构图：


![flux & redux](http://upload-images.jianshu.io/upload_images/653561-48850bc69edb2449.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

不同的是,在Flux的实现中,Store是多个,每一片逻辑相关的UI都会对应一个Store,但是在Redux模式中Store只有一个, 并通过这个大Store来维护整个App的状态.那么这样就会产生一个显而易见的问题,原来的UI状态是由不同的Store来维护的,每个Store负责一小片逻辑相关的UI,这样每个Store的内部状态都会比较简单。但是对于Redux来说,需要在Store内部维护一个巨大的状态树,它代表了整个App的状态, 这样做的好处是App的状态集中管理容易构架同构的App(Web应用中的术语),但是维护这个巨大的状态树就会变得麻烦,Redux的解决方案是:Reducer.

Store 内部维护多个Reducer,每个Reducer负责处理状态树的一部分,这样只要构建合理的Reducer来处理状态树的特定部分就可以了.


![state&reducer](http://upload-images.jianshu.io/upload_images/653561-b4707392b1b84cd6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

> 注: Reducer 是符合 *(state, action) => state* 规则的函数, 给它一个输入状态和动作,它会返回一个新的状态.

## Todo 应用
*这个是一个简单的应用,完全可以只用一个Reducer来维护状态变化,但是这里为了演示用两个Reducer分别来处理.*

具体的说是这样的,比如在Todo应用中,我们会设计一个状态(比较简单):

```
public class AppState implements State {
    private Visibility filter;
    private List<Task> tasks;

    public AppState() {
        filter = Visibility.SHOW_ALL;
        tasks = Collections.EMPTY_LIST;
    }
    // ... ignore setter& getter methods
}
```

属性 `filter` 用来表示当前列表的显示方式,他有三种显示方式:全部显示,仅显示完成的,仅显示未完成的. 属性 `tasks` 用来表示当前的todo列表. 可以用两个Reducer来维护各自的变化, 比如 `FilterReducer` 控制 `filter` 的变化, 而 `TodoReducer` 来控制todo的增删改查. 下面的代码展示这两个Reducer实现:


```
// FilterReducer.java
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
// TodoReducer.java
public class TodoReducer implements Reducer<List<Task>, Action> {
    private static final List<Task> initialState = Collections.EMPTY_LIST;

    @Override
    public List<Task> reduce(List<Task> state, Action action) {
        if (state == null) {
            state = initialState;
        }

        switch (action.getType()){
            case AddTodo.type: {
                return addTodo(state, ((AddTodo) action).getText());
            }
            case CompleteTodo.type:{
                return completeTodo(state, ((CompleteTodo)action).getIndex());
            }
            case DestroyTodo.type:{
                return destroyTodo(state, ((DestroyTodo)action).getIndex());
            }
            default:
                return state;
        }
    }
    // 省略部分实现,具体见源码
}
```

注意 `FilterReducer` 维护的状态是 `Visibility`,而 `TodoReducer` 维护的状态是 `List<Task>`, 而我们的应用实际状态是 `AppState`,这就涉及到了状态的合成,最后我们需要把每个Reducer处理之后的状态重新合成App的最终状态 `AppState`.

此处通过一个便捷方法 `Redux.combineReducers(Class<T> parent, String[] keys, Reducer[] reducers)` 来自动生成一个根节点的Reducer,这个Reducer负责合成结果.它接收的参数是各个子Reducer. 典型的用法是:

```
    Reducer taskReducer = new TodoReducer();
    Reducer filterReducer = new FilterReducer();
    String[] keys = new String[]{ "filter", "tasks" };
    Reducer[] reducers = new Reducer[] { filterReducer, taskReducer };
    Reducer combineReducer = Redux.combineReducers(AppState.class, keys, reducers);
```

这里约定了Reducer对应的key,是要被合成的父状态的属性的名称,简而言之就是这个意思:

```
AppState {
    filters: FilterReducer (state, action) => state,
    tasks: TodoReducer (state, action) => state,
}
```

这样便有了一个可以合成子Reducer处理结果的根Reducer,通过根Reducer可以创建 `Store`.

```
    todoStore = Redux.createStore(TodoStore.class, combineReducer);
```

现在看一下Store的内部实现, Store相当于一个容器本身没有业务逻辑, 调用Reducer来处理Action,同时通知注册的观察者状态的
变化:

```
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
```

在Flux架构中是通过Dispatcher来分发Action,但是在Redux中这一步被简化了,直接通过Store来发送Action(这并不奇怪,因为Redux只有一个Store就没有必要再用Dispatcher了). 这里面始终没有提及网络操作,和Flux一样,网络操作依然放在View和Dispatch之间.

## 总结

此处的代码并没有做任何优化,仅仅完成了Redux模式的基本流程(参考请慎重). Redux中没有提及`ActionCreator`的概念,所以在架构图中用虚线做的框,但是在实际开发中还是很有必要的,对于无论是Flux和Redux都应该在 `dispatch()`之前处理网络相关的操作,所以 `ActionCreator` 成了最佳场所.

对于Redux模式在Android上的移植并不看好, 通常我们在设计模型的时候往往会在内心认定一个片UI应该和一个模型对应,对于单页面应用(Web应用)来说,所有的UI处在一个页面,通过一个巨型的状态树来维护这个页面的状态显得合情合理,但是对于移动应用来说,由于屏幕的限制,往往页面被拆分成众多的小页面,这样的页面可以用Activity来分组或者用Fragment来表达. 对于复杂的移动应用来说不同的页面之间可能没有任何的关联, 如果用统一的状态树来管理,反而将逻辑复杂化.