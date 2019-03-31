package com.example.root.re_presencas.controller;

import android.text.TextUtils;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class Controller<T> implements CRUD<T> {

    private DatabaseReference databaseObjects;

    public Controller(String table) {
        databaseObjects = FirebaseDatabase.getInstance().getReference(table);
    }

    @Override
    public boolean insert(T object) {
        String id = databaseObjects.push().getKey();
        assert id != null;
        databaseObjects.child(id).setValue(object);
        return true;
    }

    @Override
    public List<T> readAll() {
        return null;
    }

    @Override
    public boolean update(T object, String id) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("").child(id);
        databaseReference.setValue(object);
        return true;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }
}
