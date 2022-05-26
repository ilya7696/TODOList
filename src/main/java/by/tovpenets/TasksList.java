package by.tovpenets;

import java.util.List;
import java.util.Objects;

public class TasksList {

    private String listName;
    private List<Task> tasks;

    public TasksList() {}
    public TasksList(String listName, List<Task> tasks) {
        this.listName = listName;
        this.tasks = tasks;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TasksList tasksList = (TasksList) o;
        return Objects.equals(listName, tasksList.listName) && Objects.equals(tasks, tasksList.tasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listName, tasks);
    }

    @Override
    public String toString() {
        return "TasksList{" +
                "listName='" + listName + '\'' +
                ", tasks=" + tasks +
                '}';
    }
}
