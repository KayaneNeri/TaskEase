package ui;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.teskease.R;

import data.TaskDAO;
import model.Task;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private List<Task> tasks;
    private final TaskDAO taskDAO;

    public TaskAdapter(List<Task> tasks, TaskDAO taskDAO) {
        this.tasks = tasks;
        this.taskDAO = taskDAO;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.tvTaskName.setText(task.getName());
        holder.cbTaskDone.setChecked(task.isDone());

        // Marcar como concluída
        holder.cbTaskDone.setOnCheckedChangeListener((buttonView, isChecked) -> {
            task.setDone(isChecked);
            taskDAO.updateTask(task); // Atualiza no banco de dados
        });

        // Excluir tarefa
        holder.btnDeleteTask.setOnClickListener(v -> {
            taskDAO.deleteTask(task.getId()); // Remove do banco de dados
            tasks.remove(position); // Remove da lista
            notifyItemRemoved(position); // Atualiza RecyclerView
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateTasks(List<Task> newTasks) {
        this.tasks = newTasks;
        notifyDataSetChanged(); // Atualiza a lista
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView tvTaskName;
        CheckBox cbTaskDone;
        ImageButton btnDeleteTask;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTaskName = itemView.findViewById(R.id.tvTaskName);
            cbTaskDone = itemView.findViewById(R.id.cbTaskDone);
            btnDeleteTask = itemView.findViewById(R.id.btnDeleteTask);
        }
    }
}
