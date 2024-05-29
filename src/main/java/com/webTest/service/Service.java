package com.webTest.service;

import java.util.List;

public interface Service<T> {
    boolean create(T t);
    List<T> read();
    boolean update(T t);
    boolean delete(int id);
}
