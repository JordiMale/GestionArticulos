package com.example.gestionarticulos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by rafafernandezdominguez on 26/1/17.
 */

public class Helper extends SQLiteOpenHelper {

    // database version
    private static final int database_VERSION = 4;

    // database name
    private static final String database_NAME = "toDoListDataBase";

    public Helper(Context context) {
        super(context, database_NAME, null, database_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TODOLIST =
                "CREATE TABLE todolist ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "codi TEXT," +
                        "description TEXT," +
                        "preu REAL," +
                        "familia TEXT," +
                        "stock REAL)";

        db.execSQL(CREATE_TODOLIST);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // De moment no fem res
        String UPGRADE_TODOLIST =
                "CREATE TABLE list ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "codi TEXT," +
                        "DIA TEXT," +
                        "QUANTITAT REAL," +
                        "TIPUS TEXT)";

        db.execSQL(UPGRADE_TODOLIST);
    }

}
