package com.example.gestionarticulos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;



public class Datasource {

    public static final String table = "todolist";
    public static final String ID = "_id";
    public static final String CODI = "codi";
    public static final String DESCRIPCION = "description";
    public static final String PREU = "preu";
    public static final String FAMILIA = "familia";
    public static final String STOCK = "stock";




    public static final String tables = "list";
    public static final String codi = "codi";
    public static final String dia = "DIA";
    public static final String quantitat = "QUANTITAT";
    public static final String tipus = "TIPUS";


    private Helper dbHelper;
    private SQLiteDatabase dbW, dbR;

    // CONSTRUCTOR
    public Datasource(Context ctx) {
        // En el constructor directament obro la comunicació amb la base de dades
        dbHelper = new Helper(ctx);

        // amés també construeixo dos databases un per llegir i l'altre per alterar
        open();
    }

    // DESTRUCTOR
    protected void finalize () {
        // Cerramos los databases
        dbW.close();
        dbR.close();
    }

    private void open() {
        dbW = dbHelper.getWritableDatabase();
        dbR = dbHelper.getReadableDatabase();
    }


    public Cursor toDoList() {
        // Retorem totes les tasques
        return dbR.query(table, new String[]{ID,CODI,DESCRIPCION,PREU,FAMILIA,STOCK},
                null, null,
                null, null, ID);
    }
/*
    public Cursor toDoList2() {
        // Retorem totes les tasques
        return dbR.query(tables, new String[]{ID,codi,dia,quantitat,tipus},
                null, null,
                null, null, ID);
    }

 */

    //Filtrar per una data
    public Cursor MoviX1Dia(String data1) {
        String query="SELECT * FROM list WHERE DIA = ? ORDER BY codi";
        String[] selectionArgs = {data1};
        Cursor c = dbR.rawQuery(query, selectionArgs);
        return c;
    }

    //Filtrar un producte per dos dates
    public Cursor MoviX2Dia(String data1, String data2) {
        String query="SELECT * FROM list WHERE DIA between ? and ? ORDER BY codi";
        String[] selectionArgs = {data1, data2};
        Cursor c = dbR.rawQuery(query, selectionArgs);
        return c;
    }

    //Filtrar per descripció
    public Cursor filtrardescripcio() {
        // Retornem les tasques que el camp DONE = 0
        return dbR.query(table, new String[]{ID,CODI,DESCRIPCION,PREU,FAMILIA,STOCK},
                 DESCRIPCION + "!=?", new String[]{""},
                null, null, ID);
    }


    //FIltrar per els productes que tinguin 0 o menys de 0 de stock.
    public Cursor filtrarestock() {
        // Retornem les tasques que el camp DONE = 1
        return dbR.query(table, new String[]{ID,CODI,DESCRIPCION,PREU,FAMILIA,STOCK},
                STOCK + "<=?", new String[]{String.valueOf(0)},
                null, null, ID);
    }


    public Cursor task(long id) {
        // Retorna un cursor només amb el id indicat
        // Retornem les tasques que el camp DONE = 1
        return dbR.query(table, new String[]{ID,CODI,DESCRIPCION,PREU,FAMILIA,STOCK},
                ID+ "=?", new String[]{String.valueOf(id)},
                null, null, null);

    }


    public long add(String title, String description, int preu, String guardarFamili, int stock) {
        // Creem una nova tasca i retornem el id crear per si el necessiten
        ContentValues values = new ContentValues();
        values.put(CODI, title);
        values.put(DESCRIPCION, description);
        values.put(PREU,preu);
        values.put(FAMILIA,guardarFamili);
        values.put(STOCK,stock);  // Forcem 0 pq si s'està creant la tasca no pot estar finalitzada

        return dbW.insert(table,null,values);
    }

    public long add2(int stock, String Data,String Codi , String Tipo) {

        ContentValues values = new ContentValues();

        values.put(codi,Codi);
        values.put(dia,Data);
        values.put(quantitat,stock);
        values.put(tipus,Tipo);

        return dbW.insert("list",null,values);
    }

    public long add3(int stock, String Data, String Codi , String Tipo) {

        ContentValues values = new ContentValues();

        values.put(codi,Codi);
        values.put(dia,Data);
        values.put(quantitat,stock);
        values.put(tipus,Tipo);


        return dbW.insert("list",null,values);
    }

    public void Update(long id, String title, String description, int preu, String guardarFamili, int stock) {
        ContentValues values = new ContentValues();
        values.put(CODI, title);
        values.put(DESCRIPCION, description);
        values.put(PREU,preu);
        values.put(FAMILIA,guardarFamili);
        values.put(STOCK,stock);

        dbW.update(table,values, ID + " = ?", new String[] { String.valueOf(id) });
    }

    //Metodo que aumenta el stock
    public void UpdateStock(int stock12, int Codi2){
        dbW.execSQL("UPDATE todolist SET stock = stock  + " + String.valueOf(stock12)+ " WHERE _id=" + String.valueOf(Codi2));
    }

    //Metodo que disminuye el stock
    public void UpsateStock2(int stock12, int Codi2){
        dbW.execSQL("UPDATE todolist SET stock = stock  - " + String.valueOf(stock12)+ " WHERE _id=" + String.valueOf(Codi2));
    }

    //Metodo que elimina el stock selecionado
    public void Borrar(long id) {
        // Eliminem la task amb clau primària "id"
        dbW.delete(table,ID + " = ?", new String[] { String.valueOf(id) });
    }

    public void Pendiente(long id) {
        // Modifiquem al estat de pendent la task indicada
        ContentValues values = new ContentValues();
        values.put(STOCK,0);

        dbW.update(table,values, ID + " = ?", new String[] { String.valueOf(id) });
    }

    public void Completada(long id) {
        // Modifiquem al estat de pendent la task indicada
        ContentValues values = new ContentValues();
        values.put(STOCK,1);

        dbW.update(table,values, ID + " = ?", new String[] { String.valueOf(id) });
    }



}


