package com.example.gestionarticulos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity2 extends AppCompatActivity {

    private static int TASK_ADD = 1;
    private static int TASK_UPDATE = 2;



    private Datasource bd;
    private long idActual;
    private int positionActual;
    private adapterTodoIcon2 scTasks;

    private static String[] from = new String[]{
            Datasource.codi,
            Datasource.dia,
            Datasource.quantitat,
            Datasource.tipus};

    private static int[] to = new int[]{
            R.id.lblCodi2,
            R.id.lblDia,
            R.id.lblQuanti,
            R.id.lblTipus};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle bundle = getIntent().getExtras();
        String [] Rebut = new String[3];
        Rebut = bundle.getStringArray("Data");
        bd = new Datasource(this);
        if(Rebut[2].equalsIgnoreCase("1")){
            loadTasks(Rebut[0]);
        }else{
            loadTasks2(Rebut[0], Rebut[1]);
        }

    }

    private void loadTasks(String Rebut) {

        // Demanem totes les tasques

        Cursor cursorTasks = bd.MoviX1Dia(Rebut);

        // Now create a simple cursor adapter and set it to display
        scTasks = new adapterTodoIcon2(this,
                R.layout.activity_main2,
                cursorTasks,
                from,
                to,
                1);

        ListView lv = (ListView) findViewById(R.id.list1);
        lv.setAdapter(scTasks);

/*
        lv.setOnItemClickListener(
                new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view,
                                            int position, long id) {

                        // modifiquem el id
                        //updateTask(id);
                    }
                }
        );


*/
    }

    private void loadTasks2(String Rebut2, String Rebut3) {

        // Demanem totes les tasques

        Cursor cursorTasks = bd.MoviX2Dia(Rebut2, Rebut3);

        // Now create a simple cursor adapter and set it to display
        scTasks = new adapterTodoIcon2(this,
                R.layout.activity_main2,
                cursorTasks,
                from,
                to,
                1);

        ListView lv = (ListView) findViewById(R.id.list1);
        lv.setAdapter(scTasks);

/*
        lv.setOnItemClickListener(
                new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view,
                                            int position, long id) {

                        // modifiquem el id
                        //updateTask(id);
                    }
                }
        );


*/
    }

}

class adapterTodoIcon2 extends android.widget.SimpleCursorAdapter {

    private static final String colorTaskPending = "#d78290";
    private static final String colorTaskCompleted = "#d7d7d7";

    private  MainActivity2 oTodoListIcon2;

    public adapterTodoIcon2(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        oTodoListIcon2 = (MainActivity2) context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = super.getView(position, convertView, parent);


        return view;

    }

}

