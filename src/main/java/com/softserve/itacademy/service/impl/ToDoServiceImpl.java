package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.repository.ToDoRepository;
import com.softserve.itacademy.repository.UserRepository;
import com.softserve.itacademy.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ToDoServiceImpl implements ToDoService {

    private ToDoRepository toDoRepository;
    private UserRepository userRepository;
    @Autowired
    public ToDoServiceImpl(ToDoRepository toDoRepository, UserRepository userRepository) {
        this.toDoRepository = toDoRepository;
        this.userRepository = userRepository;
    }

    private void verify(ToDo todo, long id) {
        if (todo == null) {
            throw new IllegalArgumentException("don't find todo with id = " + id);
        }
    }

    @Override
    public ToDo create(ToDo todo) {
        return toDoRepository.save(todo);
    }

    @Override
    public ToDo readById(long id) {
        ToDo todo = toDoRepository.findById(id).orElse(null);
        verify(todo, id);
        return todo;
    }



    @Override
    public ToDo update(ToDo todo) {
        ToDo existingTodo = toDoRepository.findById(todo.getId()).orElse(null);
        verify(existingTodo, todo.getId());
        verify(existingTodo, todo.getId());
        existingTodo.setTitle(todo.getTitle());
        existingTodo.setOwner(todo.getOwner());
        existingTodo.setTasks(todo.getTasks());
        return toDoRepository.save(existingTodo);
    }

    @Override
    public void delete(long id) {
        ToDo todo =  toDoRepository.findById(id).orElse(null);
        verify(todo, id);
        toDoRepository.delete(todo);
    }

    @Override
    public List<ToDo> getAll() {
        return toDoRepository.findAll();
    }

    @Override
    public List<ToDo> getByUserId(long userId) {
       return toDoRepository.findAllByOwnerId(userId);
    }
}
