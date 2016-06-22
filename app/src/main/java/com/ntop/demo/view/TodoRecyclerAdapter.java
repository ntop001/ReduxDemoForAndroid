package com.ntop.demo.view;

import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ntop.demo.R;
import com.ntop.demo.todo.ActionHelper;
import com.ntop.demo.todo.model.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lgvalle on 28/07/15.
 */
public class TodoRecyclerAdapter extends RecyclerView.Adapter<TodoRecyclerAdapter.ViewHolder> {
    private static ActionHelper actionHelper;
    private List<Task> todos;

    public TodoRecyclerAdapter(ActionHelper actionHelper) {
        this.todos = new ArrayList<>();
        this.actionHelper = actionHelper;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_row_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.bindView(i, todos.get(i));
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    public void setItems(List<Task> todos) {
        this.todos = todos;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView todoText;
        public CheckBox todoCheck;
        public Button todoDelete;

        public ViewHolder(View v) {
            super(v);
            todoText = (TextView) v.findViewById(R.id.row_text);
            todoCheck = (CheckBox) v.findViewById(R.id.row_checkbox);
            todoDelete = (Button) v.findViewById(R.id.row_delete);

        }

        public void bindView(final int index, final Task todo) {
            if (todo.isComplete()) {
                SpannableString spanString = new SpannableString(todo.getText());
                spanString.setSpan(new StrikethroughSpan(), 0, spanString.length(), 0);
                todoText.setText(spanString);
            } else {
                todoText.setText(todo.getText());
            }

            todoCheck.setChecked(todo.isComplete());
            todoCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    actionHelper.completeTodo(index);
                }
            });

            todoDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    actionHelper.destroyTodo(index);
                }
            });
        }
    }
}
