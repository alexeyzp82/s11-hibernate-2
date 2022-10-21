package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.model.State;
import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.repository.TaskRepository;
import com.softserve.itacademy.repository.ToDoRepository;
import com.softserve.itacademy.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    private TaskRepository taskRepository;
    private ToDoRepository toDoRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, ToDoRepository toDoRepository) {
        this.taskRepository = taskRepository;
        this.toDoRepository = toDoRepository;
    }

    private void verify(Task task, long id) {
        if (task == null) {
            throw new IllegalArgumentException("don't find task with id = " + id);
        }
    }

    @Override
    public Task create(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task readById(long id) {
        Task task = taskRepository.findById(id).orElse(null);
        verify(task, id);
        return task;
    }

    @Override
    public Task update(Task task) {
        Task existingTask = taskRepository.findById(task.getId()).orElse(null);
        verify(existingTask, task.getId());
        existingTask.setName(task.getName());
        existingTask.setPriority(task.getPriority());
        existingTask.setTodo(task.getToDo());
        existingTask.setState(task.getState());
        return existingTask;

    }

    @Override
    public void delete(long id) {
        Task task = taskRepository.findById(id).orElse(null);
        verify(task, id);
        taskRepository.deleteById(id);
    }

    @Override
    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    @Override
    public List<Task> getByTodoId(long todoId) {
        return taskRepository.getByTodoId(todoId);
    }
}
