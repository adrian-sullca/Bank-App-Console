package com.app.dao;

import java.util.List;

public interface DAO<T, k> {   
    public int insertReturnId(T t);
    public void update(T t);
    public void delete(T t);
    public T selectById(k id);
    public List<T> selectAll();
}