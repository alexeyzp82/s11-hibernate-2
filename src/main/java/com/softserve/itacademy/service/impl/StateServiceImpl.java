package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.model.State;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.repository.StateRepository;
import com.softserve.itacademy.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StateServiceImpl implements StateService {
    private StateRepository stateRepository;

    @Autowired
    public StateServiceImpl(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    private void verify(State state, long id) {
        if (state == null) {
            throw new IllegalArgumentException("don't find state with id = " + id);
        }
    }

    @Override
    public State create(State state) {
        return stateRepository.save(state);
    }

    @Override
    public State readById(long id) {
        State state = stateRepository.findById(id).orElse(null);
        verify(state, id);
        return state;
    }

    @Override
    public State update(State state) {
        State existingState = stateRepository.findById(state.getId()).orElse(null);
        verify(existingState, state.getId());
        existingState.setName(state.getName());
        existingState.setTasks(state.getTasks());
        return existingState;
    }

    @Override
    public void delete(long id) {
        State state = stateRepository.findById(id).orElse(null);
        verify(state, id);
        stateRepository.delete(state);
    }

    @Override
    public List<State> getAll() {
        return stateRepository.findAll();
    }

    @Override
    public State getByName(String name) {
        return stateRepository.findByName(name);
    }

    @Override
    public List<State> getSortAsc() {
        return stateRepository.sortByName();
    }
}
