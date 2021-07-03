package com.hit.dao;


public enum DaoImpl {
    File,
    DB;

    private DaoImpl(){}

    public static <V> IDao<V> createAccess(DaoImpl kind, String entitiesRoot) {
        return (kind == File ? new DaoFileImpl2<>(entitiesRoot) : new DaoDBImpl<>());
    }
}
