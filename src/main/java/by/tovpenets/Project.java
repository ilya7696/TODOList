package by.tovpenets;

import java.util.List;
import java.util.Objects;

public class Project {

    List<TasksList> listsOfTasks;

    public Project() {}

    public Project(List<TasksList> listsOfTasks) {
        this.listsOfTasks = listsOfTasks;
    }

    public List<TasksList> getListsOfTasks() {
        return listsOfTasks;
    }

    public void setListsOfTasks(List<TasksList> listsOfTasks) {
        this.listsOfTasks = listsOfTasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(listsOfTasks, project.listsOfTasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listsOfTasks);
    }

    @Override
    public String toString() {
        return "Project{" +
                "listsOfTasks=" + listsOfTasks +
                '}';
    }
}
