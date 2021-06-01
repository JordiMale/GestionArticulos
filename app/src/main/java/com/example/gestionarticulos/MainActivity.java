


package com.example.gestionarticulos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
//import android.support.v4.widget.SimpleCursorAdapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {



    private static int TASK_ADD = 1;
    private static int TASK_UPDATE = 2;
    TextView Ha;
    TextView Po;
    TextView HaInic;
    TextView HaFinal;
    String [] Fechas;
    String Codi22;
    String Codi23;
   // ImageView Calen;

    private Datasource bd;
    private long idActual;
    private int positionActual;
    private adapterTodoIcon scTasks;

    private static String[] from = new String[]{
            Datasource.CODI,
            Datasource.DESCRIPCION,
            Datasource.PREU,
            Datasource.FAMILIA,
            Datasource.STOCK};

    private static int[] to = new int[]{
            R.id.lblCodi,
            R.id.lblDescription,
            R.id.lblPreu,
            R.id.lblFamilia,
            R.id.lblStock};


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tulabar, menu);

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Calen = (ImageView) findViewById(R.id.OtraActivity);
        Fechas = new String [3];
        bd = new Datasource(this);
        loadTasks();
    }

    private void loadTasks() {

        // Demanem totes les tasques
        Cursor cursorTasks = bd.toDoList();

        // Now create a simple cursor adapter and set it to display
        scTasks = new adapterTodoIcon(this,
                R.layout.row_simple,
                cursorTasks,
                from,
                to,
                1);

        ListView lv = (ListView) findViewById(R.id.list1);
        lv.setAdapter(scTasks);

        lv.setOnItemClickListener(
                new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view,
                                            int position, long id) {

                        // modifiquem el id
                        updateTask(id);
                    }
                }
        );
    }

    private void refreshTasks() {

        // Demanem totes les tasques
        Cursor cursorTasks = bd.toDoList();

        // Now create a simple cursor adapter and set it to display
        scTasks.changeCursor(cursorTasks);
        scTasks.notifyDataSetChanged();
    }


    private void addTask() {
        // Cridem a l'activity del detall de la tasca enviant com a id -1
        Bundle bundle = new Bundle();
        bundle.putLong("id",-1);

        idActual = -1;

        Intent i = new Intent(this, Task.class );
        i.putExtras(bundle);
        startActivityForResult(i,TASK_ADD);
    }

    private void updateTask(long id) {
        // Cridem a l'activity del detall de la tasca enviant com a id -1
        Bundle bundle = new Bundle();
        bundle.putLong("id",id);

        idActual = id;


        Intent i = new Intent(this, Task.class );
        i.putExtras(bundle);
        startActivityForResult(i,TASK_UPDATE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TASK_ADD) {
            if (resultCode == RESULT_OK) {
                // Carreguem totes les tasques a lo bestia
                refreshTasks();
            }
        }

        if (requestCode == TASK_UPDATE) {
            if (resultCode == RESULT_OK) {
                refreshTasks();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.crear:
                //Crear un nuevo producto
                addTask();
                return true;
            case R.id.filtrarDescripcio:
                //Filtrar por llos productos con descripcion
                filterPendents();
                return true;
            case R.id.filtrarStoc:
                //Filtrar por los productos sin stock
                filterFinalitzades();
                return true;
            case R.id.OtraActivity:
                    AlertCambio();
                    return true;
            case R.id.Casa:
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.Tiemposss:
                AlertDialogTiempo();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }


    //Alert dialog de que pide poner tiempo
    private void AlertDialogTiempo() {

        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alertrtiempo, null);


        final EditText editText = (EditText) dialogView.findViewById(R.id.edt_comment);
        Button button1 = (Button) dialogView.findViewById(R.id.buttonSubmit);
        Button button2 = (Button) dialogView.findViewById(R.id.buttonCancel);
        final String[] Ciuda = {" "};
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ciuda[0] = editText.getText().toString();
                Intent intent = new Intent(MainActivity.this, Tiempo.class);
                Bundle bun = new Bundle();
                bun.putString("1234", Ciuda[0]);
                intent.putExtras(bun);
                startActivity(intent);

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // DO SOMETHINGS
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);


            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();



    }






    //Alert dialgo filtrar por 1 fecha
    private void AlertCambio() {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            LayoutInflater layout = this.getLayoutInflater();

            View vista = layout.inflate(R.layout.alertentrada2, null);
            setContentView(vista);


            AlertDialog dialog = builder.create();
            dialog.show();

            Po = (TextView) vista.findViewById(R.id.input_fecha2);


            Button btnAcceptar = (Button) findViewById(R.id.button2aceptar);


            Dataa2();

            btnAcceptar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent inte = new Intent(MainActivity.this, MainActivity2.class);
                    Bundle b = new Bundle();
                    String [] cont = new String[3];
                    cont[0] = Po.getText().toString();
                    cont[2] = "1";

                    b.putStringArray ("Data",cont);
                    inte.putExtras(b);
                    startActivity(inte);
                }
            });

        }

        //Data2 para el alert dialgo de litrar por 1 fecha utilizado en el alertcambio
        public void Dataa2() {
            Button Fecha = (Button) findViewById(R.id.Fechaentrada2);
            Fecha.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Calendar cal = Calendar.getInstance();
                    int any = cal.get(Calendar.YEAR);
                    int mes = cal.get(Calendar.MONTH);
                    int dia = cal.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog dpd = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            String Halooo;
                            int monthaux = month + 1;
                            Halooo = dayOfMonth + "-" + monthaux + "-" + year;
                            Po.setText(Halooo);
                        }
                    }, any, mes, dia);
                    dpd.show();

                }
            });
        }



    private void filterPendents() {
        // Demanem totes les tasques pendents
        Cursor cursorTasks = bd.filtrardescripcio();
        //filterActual = filterKind.FILTER_PENDING;

        // Notifiquem al adapter que les dades han canviat i que refresqui
        scTasks.changeCursor(cursorTasks);
        scTasks.notifyDataSetChanged();

        // Ens situem en el primer registre
        ListView lv = (ListView) findViewById(R.id.list1);
        lv.setSelection(0);

        Snackbar.make(findViewById(android.R.id.content), "Està visualitzant taques pendents...", Snackbar.LENGTH_LONG)
                .show();
    }

    private void filterFinalitzades() {
        // Demanem totes les tasques finalitzades
        Cursor cursorTasks = bd.filtrarestock();
        //filterActual = filterKind.FILTER_COMPLETED;

        // Notifiquem al adapter que les dades han canviat i que refresqui
        scTasks.changeCursor(cursorTasks);
        scTasks.notifyDataSetChanged();

        // Ens situem en el primer registre
        ListView lv = (ListView) findViewById(R.id.list1);
        lv.setSelection(0);

        Snackbar.make(findViewById(android.R.id.content), "Està visualitzant tasques finalitzades...", Snackbar.LENGTH_LONG)
                .show();
    }


    //Alert dialgo para aumentar stock
    public void alertDialog(int ID) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        LayoutInflater layout = this.getLayoutInflater();
        Cursor Cur = bd.task(ID);
        Cur.moveToFirst();
        String Codi;

        Codi = Cur.getString(Cur.getColumnIndex(Datasource.CODI));

        View vista = layout.inflate(R.layout.alertentrada, null);
        setContentView(vista);

        EditText Poner_Stock = (EditText) vista.findViewById(R.id.StockPO);

        AlertDialog dialog = builder.create();
        dialog.show();

        TextView Po = (TextView) vista.findViewById(R.id.posar_codi);
        Po.setText(Codi);

        Ha = (TextView) vista.findViewById(R.id.input_fecha);

        Button btnAcceptar = (Button) findViewById(R.id.button2);



        Dataa();
        btnAcceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int Guardar_Stock = Integer.parseInt(Poner_Stock.getText().toString());
                bd.add2(Guardar_Stock, Ha.getText().toString(), Codi , "Entrada");
                bd.UpdateStock(Guardar_Stock, ID);
                Intent inte = new Intent(MainActivity.this, MainActivity.class);
                startActivity(inte);
            }
        });

        Button btnCancelar = (Button)findViewById(R.id.buttoncalc);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(MainActivity.this, MainActivity.class);
                startActivity(inte);
            }
        });
    }

        //Metodo data para poner la fecha la cual se aumento el stock, utilizado en el metodo alertDialog i en el metodo alertdialog2 que es el de eliminar stock.
        public void Dataa() {
            Button Fecha = (Button) findViewById(R.id.Fecha);
            Fecha.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Calendar cal = Calendar.getInstance();
                    int any = cal.get(Calendar.YEAR);
                    int mes = cal.get(Calendar.MONTH);
                    int dia = cal.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog dpd = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            String Halooo;
                            int monthaux = month + 1;
                            Halooo = dayOfMonth + "-" + monthaux + "-" + year;
                            Ha.setText(Halooo);
                        }
                    }, any, mes, dia);
                    dpd.show();

                }
            });
        }

    //Alert dialog para eliminar stock.
    public void alertDialog2(int ID2){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);



        LayoutInflater layout = this.getLayoutInflater();
        Cursor Cur = bd.task(ID2);
        Cur.moveToFirst();

        String Codi;
        Codi = Cur.getString(Cur.getColumnIndex(Datasource.CODI));


        View vista = layout.inflate(R.layout.alertentrada, null);
        setContentView(vista);

        EditText Poner_Stock = (EditText) vista.findViewById(R.id.StockPO);

        AlertDialog dialog = builder.create();
        dialog.show();

        TextView Po = (TextView) vista.findViewById(R.id.posar_codi);
        Po.setText(Codi);

        Ha = (TextView) vista.findViewById(R.id.input_fecha);

        Button btnAcceptar = (Button) findViewById(R.id.button2);

        Dataa();
        btnAcceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int Guardar_Stock2 = Integer.parseInt(Poner_Stock.getText().toString());
                bd.add3(Guardar_Stock2 , Ha.getText().toString(),Codi, "Sortida");
                bd.UpsateStock2(Guardar_Stock2, ID2);
                Intent inte = new Intent(MainActivity.this, MainActivity.class);
                startActivity(inte);
            }
        });

        Button btnCancelar = (Button)findViewById(R.id.buttoncalc);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(MainActivity.this, MainActivity.class);
                startActivity(inte);
            }
        });
    }

    //Alert dialgo para filtrar por dos fechas un producto.
    public void alertDialog3(int ID){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);



        LayoutInflater layout = this.getLayoutInflater();
        Cursor Cur = bd.task(ID);
        Cur.moveToFirst();

        View vista = layout.inflate(R.layout.perdia, null);
        setContentView(vista);

        AlertDialog dialog = builder.create();
        dialog.show();

        HaInic = (TextView) vista.findViewById(R.id.PonerDataInici);
        HaFinal = (TextView) vista.findViewById(R.id.PonerDataFinal);

        Button btnAcceptar = (Button) findViewById(R.id.PonerDosFechasBut);

        Dataa3();

        btnAcceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(MainActivity.this, MainActivity2.class);
                Bundle b = new Bundle();
                String [] cont = new String[3];
                cont[0] = Fechas[0];
                cont[1] = Fechas[1];
                cont[2] = "2";

                b.putStringArray ("Data",cont);
                inte.putExtras(b);
                startActivity(inte);

            }
        });
    }

    //Metodo data para poner las dos fechas , donde se llama al metodo alertdialog3.
    public void Dataa3() {
        Button PonerFechaInici = (Button)findViewById(R.id.DataIni);
        Button PonerFechaFinal = (Button)findViewById(R.id.DataFi);

        PonerFechaInici.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();
                int any = cal.get(Calendar.YEAR);
                int mes = cal.get(Calendar.MONTH);
                int dia = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String Halooo;
                        int monthaux = month + 1;
                        Halooo = dayOfMonth + "-" + monthaux + "-" + year;
                        HaInic.setText(Halooo);
                        Fechas [0] = Halooo;
                    }
                }, any, mes, dia);
                dpd.show();

            }
        });

        PonerFechaFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();
                int any = cal.get(Calendar.YEAR);
                int mes = cal.get(Calendar.MONTH);
                int dia = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String Halooo;
                        int monthaux = month + 1;
                        Halooo = dayOfMonth + "-" + monthaux + "-" + year;
                        HaFinal.setText(Halooo);
                        Fechas [1] = Halooo;
                    }
                }, any, mes, dia);
                dpd.show();

            }
        });
    }

    public void deleteTask(int ID) {

        // Pedimos confirmación
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setMessage("¿Desitja eliminar la tasca?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                bd.Borrar(ID);

                Intent mIntent = new Intent();
                mIntent.putExtra("id", -1);  // Devolvemos -1 indicant que s'ha eliminat
                setResult(RESULT_OK, mIntent);
                refreshTasks();

            }
        });

        builder.setNegativeButton("No", null);

        builder.show();
    }


}

class adapterTodoIcon extends android.widget.SimpleCursorAdapter {

    private static final String colorTaskPending = "#d78290";
    private static final String colorTaskCompleted = "#d7d7d7";

    private  MainActivity oTodoListIcon;

    public adapterTodoIcon(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        oTodoListIcon = (MainActivity) context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = super.getView(position, convertView, parent);

        // Agafem l'objecte de la view que es una LINEA DEL CURSOR
        Cursor linia = (Cursor) getItem(position);

        int done = linia.getInt(linia.getColumnIndexOrThrow(Datasource.STOCK));

        // Pintem el fons de la view segons està completada o no
        if (done <= 0) {
            view.setBackgroundColor(Color.parseColor(colorTaskCompleted));
        } else {
            view.setBackgroundColor(Color.parseColor(colorTaskPending));
        }


        //Imagen para aumentar el stock, se redirige al metodo llamado alertdialog, pasandole el id del cual el usuario ha pulsado.
        ImageView btnponer = (ImageView) view.findViewById(R.id.entrada);

        btnponer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Busco la ROW
                View row = (View) v.getParent();
                // Busco el ListView
                ListView lv = (ListView) row.getParent();
                // Busco quina posicio ocupa la Row dins de la ListView
                int position = lv.getPositionForView(row);

                // Carrego la linia del cursor de la posició.
                Cursor linia = (Cursor) getItem(position);

                oTodoListIcon.alertDialog(linia.getInt(linia.getColumnIndexOrThrow(Datasource.ID)));
            }
        });


        //Imagen para eliminar stock, se redirige al metodo llamado alertdialog2, pasandole el id que ha pulsado el usuario igual que la imagen poner.
        ImageView btnextraer = (ImageView) view.findViewById(R.id.salida);

        btnextraer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Busco la ROW
                View row = (View) v.getParent();
                // Busco el ListView
                ListView lv = (ListView) row.getParent();
                // Busco quina posicio ocupa la Row dins de la ListView
                int position = lv.getPositionForView(row);

                // Carrego la linia del cursor de la posició.
                Cursor linia = (Cursor) getItem(position);

                oTodoListIcon.alertDialog2(linia.getInt(linia.getColumnIndexOrThrow(Datasource.ID)));
            }
        });


        //Imagen para filtrar por dos fechas, se redirige al metodo alertidalog3, pasandole el id del que el usuario ha pulsado.
        ImageView btnFecha = (ImageView) view.findViewById(R.id.DosFechas);

        btnFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Busco la ROW
                View row = (View) v.getParent();
                // Busco el ListView
                ListView lv = (ListView) row.getParent();
                // Busco quina posicio ocupa la Row dins de la ListView
                int position = lv.getPositionForView(row);

                // Carrego la linia del cursor de la posició.
                Cursor linia = (Cursor) getItem(position);

                oTodoListIcon.alertDialog3(linia.getInt(linia.getColumnIndexOrThrow(Datasource.ID)));
            }
        });

        //Imagen para eliminar el producto seleccionado por el usuario.
       // ImageView btnTrsah = (ImageView)view.findViewById(R.id.Basura);
/*
        btnTrsah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Busco la ROW
                View row = (View) v.getParent();
                // Busco el ListView
                ListView lv = (ListView) row.getParent();
                // Busco quina posicio ocupa la Row dins de la ListView
                int position = lv.getPositionForView(row);

                // Carrego la linia del cursor de la posició.
                Cursor linia = (Cursor) getItem(position);

                oTodoListIcon.deleteTask(linia.getInt(linia.getColumnIndexOrThrow(Datasource.ID)));
            }
        });

 */


        return view;

    }

}





