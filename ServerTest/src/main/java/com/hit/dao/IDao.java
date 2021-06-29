package com.hit.dao;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface IDao<V> {
    void save(String source, V v) throws IOException;
    V read(String source) throws IOException, ClassNotFoundException;
}