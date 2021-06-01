package com.example.gestionarticulos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Task extends AppCompatActivity {

    private long idTask;
    private Datasource bd;

    RadioButton radio;
    RadioButton radio1;
    RadioButton radio2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        bd = new Datasource(this);
        idTask = this.getIntent().getExtras().getLong("id");

        radio = (RadioButton)findViewById(R.id.Hardware);
        radio1 = (RadioButton)findViewById(R.id.Software);
        radio2 = (RadioButton)findViewById(R.id.Altres);

        // Botones de aceptar y cancelar
        // Boton ok
        Button  btnOk = (Button) findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                aceptarCambios();
            }
        });

        // Boton eliminar
        Button  btnDelete = (Button) findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                deleteTask();
            }
        });

        // Boton cancelar
        Button  btnCancel = (Button) findViewById(R.id.btnCancelar);
        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                cancelarCambios();
            }
        });

        // Busquem el id que estem modificant
        // si el el id es -1 vol dir que s'està creant
        idTask = this.getIntent().getExtras().getLong("id");

        if (idTask != -1) {
            // Si estem modificant carreguem les dades en pantalla
            cargarDatos();
        }

    }

    private void cargarDatos() {

        // Demanem un cursor que retorna un sol registre amb les dades de la tasca
        // Això es podria fer amb un classe pero...
        Cursor datos = bd.task(idTask);
        datos.moveToFirst();

        // Carreguem les dades en la interfície
        TextView tv, tv2;


        tv = (TextView) findViewById(R.id.edtCodi);
        tv.setText(datos.getString(datos.getColumnIndex(Datasource.CODI)));

        tv = (TextView) findViewById(R.id.edtDescripcion);
        tv.setText(datos.getString(datos.getColumnIndex(Datasource.DESCRIPCION)));



        String Auxiliar = (datos.getString(datos.getColumnIndex(Datasource.FAMILIA)));
        if(Auxiliar.equalsIgnoreCase("Hardware")){
            radio.setChecked(true);
        }else{
            if(Auxiliar.equalsIgnoreCase("Software")){
                radio1.setChecked(true);
            }else{
                if(Auxiliar.equalsIgnoreCase("Altres")){
                    radio2.setChecked(true);
                }
            }
        }


        tv = (TextView) findViewById(R.id.edtPreu);
        tv.setText(datos.getString(datos.getColumnIndex(Datasource.PREU)));

        tv = (TextView) findViewById(R.id.Stock);
        tv.setText(datos.getString(datos.getColumnIndex(Datasource.STOCK)));
        // Finalment el checkbox
        int finalitzada = datos.getInt(datos.getColumnIndex(Datasource.STOCK));


    }

    private void aceptarCambios() {
        // Validem les dades
        TextView tv;


        String GuardarFamili = "";

        // Títol ha d'estar informat
        tv = (TextView) findViewById(R.id.edtCodi);
        String codi = tv.getText().toString();
        if (codi.trim().equals("")) {
            //myDialogs.showToast(this,"Ha d'informar el títol");
            return;
        }



        if (radio.isChecked()){
            GuardarFamili = "Hardware";
        }else{
            if(radio1.isChecked()){
                GuardarFamili = "Software";
            }else{
                if(radio2.isChecked()){
                    GuardarFamili = "Altres";
                }
            }
        }


        // La Prioritat entre 1 i 5
        tv = (TextView) findViewById(R.id.edtPreu);
        int preu;
        try {
            preu = Integer.valueOf(tv.getText().toString());
        }
        catch (Exception e) {
            //myDialogs.showToast(this,"La prioritat ha de ser un valor entre 1 i 5");
            return;
        }

        if ((preu < 1) || (preu > 5)) {
            //myDialogs.showToast(this,"La prioritat ha de ser un valor entre 1 i 5");
            return;
        }

        tv = (TextView) findViewById(R.id.edtDescripcion);
        String descripcion = tv.getText().toString();

        tv = (TextView) findViewById(R.id.Stock);
        int stock = 0;
        try{
            stock = Integer.valueOf(tv.getText().toString());
        }catch (Exception e){

        }


        if (idTask == -1) {
            idTask = bd.add(codi, descripcion, preu, GuardarFamili, stock);
        }
        else {
            bd.Update(idTask, codi, descripcion, preu, GuardarFamili, stock);


        }

        Intent mIntent = new Intent();
        mIntent.putExtra("id", idTask);
        setResult(RESULT_OK, mIntent);

        finish();
    }

    private void cancelarCambios() {
        Intent mIntent = new Intent();
        mIntent.putExtra("id", idTask);
        setResult(RESULT_CANCELED, mIntent);

        finish();
    }

    private void deleteTask() {

        // Pedimos confirmación
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("¿Desitja eliminar la tasca?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                bd.Borrar(idTask);

                Intent mIntent = new Intent();
                mIntent.putExtra("id", -1);  // Devolvemos -1 indicant que s'ha eliminat
                setResult(RESULT_OK, mIntent);

                finish();
            }
        });

        builder.setNegativeButton("No", null);

        builder.show();
    }
}