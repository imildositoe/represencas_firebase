package com.example.root.re_presencas.components;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

public class Components {

    private Context context;

    public Components(Context context) {
        this.context = context;
    }

    public Toast toast(String message) {
        return Toast.makeText(context, message, Toast.LENGTH_SHORT);
    }

    public void oneButtonDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Ok ", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void infoDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setPositiveButton("Ok ", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void twoButtonDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Cancel ", null);
        builder.setPositiveButton("Ok ", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
