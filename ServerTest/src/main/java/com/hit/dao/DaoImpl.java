//package com.hit.dao;
//
//
//public enum DaoImpl {
//    File,
//    DB;
//
//    private DaoImpl(){}
//
//    public static <V> IDao<V> createAccess(DaoImpl kind) {
//        return (IDao)  new DaoFileImpl2();
//
////        return (IDao) (kind == File ? new DaoFileImpl2() : new DaoDBImpl());
//
//    }
//}
