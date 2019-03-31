package com.example.root.re_presencas.controller;

import java.util.List;

public interface CRUD<T> {

    /**
     * Method to store objects on database.
     * */
    boolean insert(T object);


    /**
     * Method to read objects from database.
     * */
    List<T> readAll();


    /**
     * Method to edit objects from database.
     * */
    boolean update(T object, String id);


    /**
     * Method to delete an objects from database.
     * */
    boolean delete(String id);
}
