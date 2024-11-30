package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskDAO {
    private SQLiteDatabase db;

    public TaskDAO(Context context) {
        TaskDatabaseHelper dbHelper = new TaskDatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long addTask(String taskName) {
        ContentValues values = new ContentValues();
        values.put(TaskDatabaseHelper.COLUMN_TASK_NAME, taskName);
        return db.insert(TaskDatabaseHelper.TABLE_TASKS, null, values);
    }

    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        Cursor cursor = db.query(TaskDatabaseHelper.TABLE_TASKS, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(TaskDatabaseHelper.COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndex(TaskDatabaseHelper.COLUMN_TASK_NAME));
            boolean isDone = cursor.getInt(cursor.getColumnIndex(TaskDatabaseHelper.COLUMN_IS_DONE)) == 1;
            tasks.add(new Task(id, name, isDone));
        }
        cursor.close();
        return tasks;
    }

    public void updateTask(Task task) {
        ContentValues values = new ContentValues();
        values.put(TaskDatabaseHelper.COLUMN_TASK_NAME, task.getName());
        values.put(TaskDatabaseHelper.COLUMN_IS_DONE, task.isDone() ? 1 : 0);
        db.update(TaskDatabaseHelper.TABLE_TASKS, values, TaskDatabaseHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(task.getId())});
    }

    public void deleteTask(int taskId) {
        db.delete(TaskDatabaseHelper.TABLE_TASKS, TaskDatabaseHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(taskId)});
    }
}
