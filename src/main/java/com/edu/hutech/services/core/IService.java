package com.edu.hutech.services.core;

import java.util.List;

public interface IService<T> {

    void save(T t);

    void update(T t);

    void delete(Integer theId);

    T findById(Integer theId);

    List<T> getAll();

}
