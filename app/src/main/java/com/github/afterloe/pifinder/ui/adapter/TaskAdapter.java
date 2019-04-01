package com.github.afterloe.pifinder.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.afterloe.pifinder.R;
import com.github.afterloe.pifinder.domain.Task;

import java.io.Serializable;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskItem> implements Serializable {

    private Context context;
    private List<Task> tasks;

    public TaskAdapter(Context context, List<Task> tasks) {
        this.context = context;
        this.tasks = tasks;
    }

    public void addAll(List<Task> tasks) {
        this.tasks.clear();
        this.tasks.addAll(tasks);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskAdapter.TaskItem onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TaskItem(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_task, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.TaskItem taskItem, int i) {
        taskItem.bind(tasks.get(i));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    class TaskItem extends RecyclerView.ViewHolder {

        TaskItem(@NonNull View itemView) {
            super(itemView);
        }

        void bind(Task task) {
            // TODO
        }
    }
}
