package edu.aitu.oop3.repositories;

import java.util.List;

public interface IGenericRepository<T> {
    boolean create(T entity);
    T getById(int id);
    List<T> getAll();
}