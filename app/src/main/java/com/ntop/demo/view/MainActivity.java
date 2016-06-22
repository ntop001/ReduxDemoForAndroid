package com.ntop.demo.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ntop.demo.App;
import com.ntop.demo.R;
import com.ntop.demo.redux.Observer;
import com.ntop.demo.todo.ActionHelper;
import com.ntop.demo.todo.TodoStore;
import com.ntop.demo.todo.actions.FilterVisibility;
import com.ntop.demo.todo.actions.FilterVisibility.Visibility;
import com.ntop.demo.todo.model.Task;
import com.ntop.demo.tools.CollectionTool;

import java.util.List;

public class MainActivity extends AppCompatActivity implements Observer {
    private EditText mainInput;
    private ViewGroup mainLayout;

    private ActionHelper actionHelper;
    private TodoStore todoStore;
    private TodoRecyclerAdapter todoRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        di();
        setUpViews();
    }

    private void di() {
        todoStore = App.getStore();
        actionHelper = App.getActionHelper();
        todoRecyclerAdapter = new TodoRecyclerAdapter(App.getActionHelper());

    }

    private void setUpViews() {
        // mainLayout = ((ViewGroup) findViewById(R.id.main_layout));
        mainInput = (EditText) findViewById(R.id.main_input);

        Button mainAdd = (Button) findViewById(R.id.main_add);
        mainAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewTodo();
            }
        });

        RecyclerView mainList = (RecyclerView) findViewById(R.id.main_list);
        mainList.setLayoutManager(new LinearLayoutManager(this));
        mainList.setAdapter(todoRecyclerAdapter);

        render();
    }

    public void update() {
        render();
    }

    private void addNewTodo() {
        if (mainInput.getText() != null && mainInput.getText().length() > 0) {
            actionHelper.addTodo(mainInput.getText().toString());
            mainInput.setText(null);
        }
    }

    private void render() {
        List<Task> todos = todoStore.getState().getTasks();
        Visibility filter = todoStore.getState().getFilter();
        todoRecyclerAdapter.setItems(selectTask(todos, filter));
    }

    private List<Task> selectTask(List<Task> todos, FilterVisibility.Visibility filter) {
        switch (filter) {
            case SHOW_ACTIVE:
                return CollectionTool.filter(todos, new CollectionTool.Filter<Task>() {
                    @Override
                    public boolean apply(Task task) {
                        return !task.isComplete();
                    }
                });
            case SHOW_COMPLETE:
                return CollectionTool.filter(todos, new CollectionTool.Filter<Task>() {
                    @Override
                    public boolean apply(Task task) {
                        return task.isComplete();
                    }
                });
            case SHOW_ALL:
            default:
                return todos;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_show_all:
                actionHelper.filter(FilterVisibility.Visibility.SHOW_ALL);
                return true;
            case R.id.action_show_active:
                actionHelper.filter(FilterVisibility.Visibility.SHOW_ACTIVE);
                return true;
            case R.id.action_show_complete:
                actionHelper.filter(FilterVisibility.Visibility.SHOW_COMPLETE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        todoStore.unSubscribe(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        todoStore.subscribe(this);
    }
}
