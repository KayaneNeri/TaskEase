package com.example.teskease;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import data.TaskDAO;
import model.Task;
import ui.TaskAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TaskDAO taskDAO;
    private TaskAdapter taskAdapter; // Variável corrigida: inicial minúscula.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskDAO = new TaskDAO(this);

        EditText etTaskName = findViewById(R.id.etTaskName);
        Button btnAddTask = findViewById(R.id.btnAddTask);
        RecyclerView rvTasks = findViewById(R.id.rvTasks);

        List<Task> tasks = taskDAO.getAllTasks();
        taskAdapter = new TaskAdapter(tasks, taskDAO); // Correção: inicial minúscula.
        rvTasks.setLayoutManager(new LinearLayoutManager(this));
        rvTasks.setAdapter(taskAdapter);

        btnAddTask.setOnClickListener(v -> {
            String taskName = etTaskName.getText().toString();
            if (!taskName.isEmpty()) {
                taskDAO.addTask(taskName);
                taskAdapter.updateTasks(taskDAO.getAllTasks());
                etTaskName.setText("");
            }
        });
    }
}
