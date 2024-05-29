package com.webTest.dao;

import java.util.List;

public interface Dao<T> {
    int create(T t);
    List<T> read();
    int update(T t);
    int delete(int id);
}
