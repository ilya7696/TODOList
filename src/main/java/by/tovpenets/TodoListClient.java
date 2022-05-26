package by.tovpenets;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Implementation for
 *
 * Создать toDo list
 * Тема.
 * Список задачь (Активные, завершенные)
 *
 * Создать меню проекта.
 * 1. Можно вывести список листов.
 * 2. Добавить.
 * 3. Удалить лист.
 * 4. Выбрать лист.
 * 5. Выйти из приложения.
 *
 * В листе.
 * 1. Можно добавить задачу.
 * 2. Удалить.
 * 3. Изменить.
 * 4. Пометить, как завершенную.
 * 5. Пометить, как активную.
 *
 * Состояние пока сохраняем в файл.
 * Т. е. при выходе из приложения сериализуем данные в файл.
 *
 * При входе считываем данные из файла.
 */
public class TodoListClient {

    private static final String CHOOSE_PROJECT_OPTIONS =
            "1. Show list of sheets.\n" +
            "2. Create sheet.\n" +
            "3. Remove sheet.\n" +
            "4. Select sheet.\n" +
            "5. Exit from application.\n";

    private static final String CHOOSE_SHEET_OPTIONS =
            "1. Create task.\n" +
            "2. Remove task.\n" +
            "3. Update task.\n" +
            "4. Show tasks.\n" +
            "5. Tag ACTIVE.\n" +
            "6. Tag CLOSED.\n" +
            "7. Exit from application.\n";

    private final static String PROJECT_PATH = "./project.json";

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static Project project;


    public static void main(String[] args) {
        System.out.println("Welcome in our TODOList!");

        try {
            project = MAPPER.readValue(new File(PROJECT_PATH), Project.class);

            navigateByUserChoice(Root.PROJECT);

            MAPPER.writeValue(new File(PROJECT_PATH), project);
        } catch (IOException e) {
            throw new RuntimeException("Something wrong while reading base project structure from json!", e);
        }
    }

    private static void navigateByUserChoice(Root root) {
        Scanner scanner = new Scanner(System.in);
        if (root == Root.PROJECT) {
            System.out.println(CHOOSE_PROJECT_OPTIONS);

            int userChoice = scanner.nextInt(); scanner.nextLine();

            if (1 == userChoice) {
                List<TasksList> tasksLists = project.getListsOfTasks();
                for (int i = 0; i < tasksLists.size(); i++)
                    System.out.println((i + 1) + ". Sheet name: " + tasksLists.get(i).getListName());
            } else if (2 == userChoice) {
                System.out.println("Write new sheet name");

                String sheetName = scanner.nextLine();

                TasksList newTasksList = new TasksList(sheetName, new ArrayList<>());
                project.getListsOfTasks().add(newTasksList);

                System.out.println("New sheet with name " + newTasksList.getListName() + " was created!");
            } else if (3 == userChoice) {
                System.out.println("Write sheet number");

                int sheetIndex = scanner.nextInt() - 1;
                String listName = project.getListsOfTasks().remove(sheetIndex).getListName();

                System.out.println("Sheet with name " + listName + " removed");
            } else if (4 == userChoice) {
                navigateByUserChoice(Root.SHEET);
            } else if (5 == userChoice) {
                return;
            } else {
                throw new RuntimeException("Wrong navigate input=" + userChoice);
            }
            System.out.println("\n\n\n");
            navigateByUserChoice(Root.PROJECT);
        } else {
            System.out.println("Choose sheet number");
            int sheetIndex = scanner.nextInt() - 1;

            System.out.println(CHOOSE_SHEET_OPTIONS);
            int userChoice = scanner.nextInt(); scanner.nextLine();

            if (1 == userChoice) {
                System.out.println("Write task name");
                String taskName = scanner.nextLine();

                System.out.println("Write task description");
                String taskDescription = scanner.nextLine();

                Task newTask = new Task(taskName, taskDescription, Status.ACTIVE);

                System.out.println("Test mess task " + newTask);

                project.getListsOfTasks().get(sheetIndex).getTasks().add(newTask);
                System.out.println("Task with name " + newTask.getTaskName() + " was created");
            } else if (2 == userChoice) {
                System.out.println("Write task number");
                int taskIndex = scanner.nextInt() - 1;

                String taskName = project.getListsOfTasks().get(sheetIndex).getTasks().remove(taskIndex).getTaskName();

                System.out.println("Task with name " + taskName + " was removed");
            } else if (3 == userChoice) {
                System.out.println("Write task number");
                int taskIndex = scanner.nextInt() - 1; scanner.nextLine();

                System.out.println("Write new task description");
                String updatedDescription = scanner.nextLine();

                project.getListsOfTasks().get(sheetIndex).getTasks().get(taskIndex).setDescription(updatedDescription);
                System.out.println("Task description was changed");
            } else if (4 == userChoice) {
                String sheetName = project.getListsOfTasks().get(sheetIndex).getListName();
                System.out.println("Show all tasks in sheet " + sheetName);

                List<Task> tasks = project.getListsOfTasks().get(sheetIndex).getTasks();
                for (int i = 0; i < tasks.size(); i++)
                    System.out.println((i + 1) + ". " + tasks.get(i).getTaskName());
            } else if (5 == userChoice) {
                System.out.println("Write task number");
                int taskIndex = scanner.nextInt() - 1;

                String taskName = project.getListsOfTasks().get(sheetIndex).getTasks().get(taskIndex).getTaskName();
                project.getListsOfTasks().get(sheetIndex).getTasks().get(taskIndex).setStatus(Status.ACTIVE);

                System.out.println("Status of task with name " + taskName + " was changed to ACTIVE");
            } else if (6 == userChoice) {
                System.out.println("Write task number");
                int taskIndex = scanner.nextInt() - 1;

                String taskName = project.getListsOfTasks().get(sheetIndex).getTasks().get(taskIndex).getTaskName();
                project.getListsOfTasks().get(sheetIndex).getTasks().get(taskIndex).setStatus(Status.CLOSED);

                System.out.println("Status of task with name " + taskName + " was changed to CLOSED");
            } else if (7 == userChoice) {
                return;
            } else {
                throw new RuntimeException("Wrong navigate input=" + userChoice);
            }
            System.out.println("\n\n\n");
            navigateByUserChoice(Root.SHEET);
        }
    }

}

enum Root {
    PROJECT, SHEET
}
