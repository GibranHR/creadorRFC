package com.itche.gibranhr.generarrfc;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;
import android.widget.DatePicker;
import android.app.DatePickerDialog;

public class MainActivity extends AppCompatActivity {

    EditText editApPaterno;
    EditText editApMaterno;
    EditText editNombre;
    EditText editFechaNaci;
    TextView edRFC;

    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1;
        btn1 = findViewById(R.id.button);

        editFechaNaci = (EditText) findViewById(R.id.editFechaNaci);
        editApPaterno = (EditText) findViewById(R.id.editApPaterno);
        editApMaterno = (EditText) findViewById(R.id.editApMaterno);
        editNombre = (EditText) findViewById(R.id.editNombre);
        edRFC = (TextView) findViewById(R.id.edRFC);

        editFechaNaci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        editFechaNaci.setText(checkDigit(day) + "/" + checkDigit((month + 1)) + "/" + year);
                    }
                }, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editNombre.getText().toString().trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "No se puede generar RFC", Toast.LENGTH_SHORT).show();
                    edRFC.setText("");
                    editNombre.setError(editNombre.getResources().getString(R.string.CampoObligatorio));
                }else if (editApPaterno.getText().toString().trim().length() == 0){
                    Toast.makeText(getApplicationContext(), "No se puede generar RFC", Toast.LENGTH_SHORT).show();
                    edRFC.setText("");
                    editApPaterno.setError(editApPaterno.getResources().getString(R.string.CampoObligatorio));
                }else if (editApMaterno.getText().toString().trim().length() == 0){
                    Toast.makeText(getApplicationContext(), "No se puede generar RFC", Toast.LENGTH_SHORT).show();
                    edRFC.setText("");
                    editApMaterno.setError(editApMaterno.getResources().getString(R.string.CampoObligatorio));
                }else if(editFechaNaci.getText().toString().trim().length() == 0){
                    Toast.makeText(getApplicationContext(), "No se puede generar RFC", Toast.LENGTH_SHORT).show();
                    edRFC.setText("");
                    editFechaNaci.setError(editFechaNaci.getResources().getString(R.string.CampoObligatorio));
                }else{
                    generarRFC(view);
                    editNombre.setError(null);
                    editApPaterno.setError(null);
                    editApMaterno.setError(null);
                    editFechaNaci.setError(null);
                }
            }
        });
    }
    public String checkDigit(int number){

        return number<=9?"0"+number:String.valueOf(number);
    }
    public void generarRFC(View v) {
        //Variables
        String appaterno = (editApPaterno.getText().toString().toUpperCase().trim());
        String apmaterno = (editApMaterno.getText().toString().toUpperCase().trim());
        String nombre = (editNombre.getText().toString().toUpperCase().trim());

        String año = (editFechaNaci.getText().toString().trim().substring(8));
        String mes = (editFechaNaci.getText().toString().trim().substring(3,5));
        String dia = (editFechaNaci.getText().toString().trim().substring(0,2));
        String rfc = " ";

        //Encuentra la primer letra del apellido paterno
        char primerLetraAP = appaterno.charAt(0);

        //Encuentra la primer letra del apellido materno
        char primerLetraAM = apmaterno.charAt(0);

        //Encuentra la primer letra del nombre
        char primerLetraN = nombre.charAt(0);

        //Encuentra la primera vocal del apellido paterno forma 1
        char primervocal=0;
        int desplazamiento=0;

        for(int i=1; (desplazamiento==0 && i<appaterno.length()); i++)
        {
            primervocal=appaterno.charAt(i);
            if(primervocal==('A')||primervocal==('E')||
                    primervocal==('I')||primervocal==('O')||
                    primervocal==('U')) {
                desplazamiento++;
            }
        }

        //Generar homoclave aleatoria
        char [] caracteres = {'0','1','2','3','4','5','6','7','8','9',
                'A','B','C','D','E','F','G','H','I','J',
                'K','L','M','N','Ñ','O','P','Q','R','S',
                'T','U','V','W','X','Y','Z'};
        char [] conjunto = new char[3];
        String homoclave="";

        for(int i=0;i<=2;i++){
            conjunto[i] = caracteres[(int)(Math.random()*37)];
            homoclave=homoclave+=conjunto[i];
        }

        //Concatenar los datos
        rfc+=primerLetraAP;
        rfc+=primervocal;
        rfc+=primerLetraAM;
        rfc+=primerLetraN;
        rfc+=año;
        rfc+=mes;
        rfc+=dia;
        rfc+=homoclave;

        //verifica si encontró la vocal para imprimir rfc
        if (desplazamiento!=1) {
            Toast.makeText(this, "No se encontró vocal", Toast.LENGTH_SHORT).show();
            edRFC.setText(" ");
        }else{
            edRFC.setText("Tu RFC es: " + "\n" + rfc.toUpperCase().trim());
        }
    }
    public void LimpiarRFC(View V){
        editApPaterno.setText(null);
        editApMaterno.setText(null);
        editNombre.setText(null);
        editFechaNaci.setText(null);
        edRFC.setText("");
        Toast.makeText(this, "Datos eliminados", Toast.LENGTH_SHORT).show();
    }
}