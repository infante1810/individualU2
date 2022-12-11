package com.upv.pm_2022.sep_dic.asignacionindividual2;

/*

https://github.com/PhilJay/MPAndroidChart

modificaciones al archivo build.graddle (ALL Projects y Module)
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.PhilJay:MPAndroidChart:v3.1.0'
}

 */

import android.content.Context;
import android.graphics.Color;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.EditText;
//import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.charts.BarChart;
//import com.github.mikephil.charting.data.BarData;
//import com.github.mikephil.charting.data.BarDataSet;
//import com.github.mikephil.charting.data.BarEntry;
//import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.obsez.android.lib.filechooser.ChooserDialog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Context context;
    final int[] position = {0};
    final String startingDir = Environment.getExternalStorageDirectory().toString();
    final String path = Environment.getExternalStorageDirectory().toString();
    TextView TV2;
    String arrayText;
    private HorizontalBarChart chart;
    private SeekBar seekBarX, seekBarY;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            context = this;
        }catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        TV2 = findViewById(R.id.textView);
        Button button = findViewById(R.id.button);
        EditText editText = findViewById(R.id.editText);
        //Toast.makeText(this, "Hola", Toast.LENGTH_SHORT).show();

        //Toast.makeText(this, "WOrking2", Toast.LENGTH_SHORT).show();

        TV2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                ArrayList<Float> array= new ArrayList<Float>();
                try{

                    array = getArray(arrayText);
                    //Toast.makeText(context, arrayText, Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    //Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    HorizontalBarChart bchart = findViewById(R.id.chart);
                    bchart.clear();
                    bchart.invalidate();
                    return;
                }
                //Toast.makeText(context, "WOrking", Toast.LENGTH_SHORT).show();
                HorizontalBarChart bchart = findViewById(R.id.chart);
                ArrayList<BarEntry> yVals1 = new ArrayList<>();
                //Toast.makeText(context, array.size()+"<-size", Toast.LENGTH_SHORT).show();
                for (int i = 0; i < array.size(); i=i+2) {
                    yVals1.add(new BarEntry(array.get(i+1),array.get(i)));
                    //Toast.makeText(context, "("+array.get(i)+","+array.get(i+1)+")", Toast.LENGTH_SHORT).show();
                }

                /*
                for (int i = 0; i < 10 + 1; i++) {
                    float val = (float) (Math.random());
                    yVals1.add(new BarEntry(i, val));
                    position[0] = i;
                }

                position[0] = position[0] + 1;
                float xValue = 3.5F;
                float yValue = 2.0F;
                yVals1.add(new BarEntry(xValue, yValue));
*/
                BarDataSet set1;

                set1 = new BarDataSet(yVals1, "Grafica Horizontal");
                set1.setColors(ColorTemplate.MATERIAL_COLORS);

                ArrayList<IBarDataSet> dataSets = new ArrayList<>();
                dataSets.add(set1);


                BarData data = new BarData(dataSets);

                data.setValueTextSize(10f);
                data.setBarWidth(0.9f);
                bchart.setTouchEnabled(false);
                bchart.setData(data);
                bchart.animateXY(2000, 2000);
                bchart.invalidate();

            }


        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarTEX(v);


                /*try {
                    Toast.makeText(context, "position: "+position[0], Toast.LENGTH_SHORT).show();
                    float val = Float.parseFloat(editText.getText().toString());
                    yVals1.add(new BarEntry(position[0], val));
                    position[0]++;
                    BarDataSet set1;
                    set1 = new BarDataSet(yVals1, "Data Set");
                    set1.setColors(ColorTemplate.MATERIAL_COLORS);
                    set1.setDrawValues(false);
                    ArrayList<IBarDataSet> dataSets = new ArrayList<>();
                    dataSets.add(set1);
                    BarData data = new BarData(dataSets);
                    bchart.setData(data);
                    bchart.invalidate();



                } catch (Exception e) {

                    Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();

                }*/


            }
        });


    }

    public ArrayList<Float> getArray(String arrayText){
        ArrayList<Float> array = new ArrayList<>();
        String[] arrayString = arrayText.split("[()(,]+");

        //Toast.makeText(context, "arrayString: "+arrayString.length, Toast.LENGTH_SHORT).show();
        int cont = 0;
        for (String a : arrayString) {
            try {
                array.add(Float.parseFloat(a));
                if(cont==0) {
                    Toast.makeText(context, "valueOfa: " + a, Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){
                //Toast.makeText(context, "Parece ser que existe_un problema en sintaxis:"+cont, Toast.LENGTH_SHORT).show();
                if(cont!=0) {
                    Toast.makeText(context, "Parece ser que existe un problema en sintaxis" , Toast.LENGTH_SHORT).show();
                }
            }
            cont++;
        }
        return array;
    }

    private String LeerArchivoTexto (String Ruta) throws IOException {
        //Ruta= Environment.getExternalStorageDirectory().getAbsolutePath().toString()+"/"+NombreDirectorio;
        String aBuffer ="";
        String aDataRow ="";
        File F = new File (Ruta);
        if (F.exists()) { // Ya existe el directorio
            FileInputStream fIn = new FileInputStream(F);
            BufferedReader myReader = new BufferedReader(
                    new InputStreamReader(fIn));
            aDataRow = "";
            aBuffer = "";
            while ((aDataRow = myReader.readLine()) != null) {
                aBuffer += aDataRow + "\n";
            }
            myReader.close();
            Toast.makeText(getBaseContext(),
                    "Se leyó el archivo",
                    Toast.LENGTH_SHORT).show();
        }
        return (aBuffer);
    }

    public void seleccionarTEX (View v) {
        new ChooserDialog(MainActivity.this)
                .withFilterRegex(false, true, ".*\\.(tex)$")
                .withStartFile(path)
                //.withResources(R.string.title_choose_file, R.string.title_choose, R.string.dialog_cancel)
                .withChosenListener(new ChooserDialog.Result() {
                    @Override
                    public void onChoosePath(String path, File pathFile) {
                        //Toast.makeText(MainActivity.this, "FILE: " + path, Toast.LENGTH_SHORT).show();
                        // Funcion para leer archivo
                        //String contenido="";
                        //TV1.setText(path);
                       // Toast.makeText(MainActivity.this, "FILE: " + path, Toast.LENGTH_SHORT).show();
                        String contenido= null;
                        arrayText=null;
                        try {
                            contenido = LeerArchivoTexto(path);
                        } catch (IOException e) {
                            Toast.makeText(MainActivity.this, "Error al leer el archivo", Toast.LENGTH_SHORT).show();
                        }
                        String find = "\\begin{tikzpicture}";
                        int i = contenido.indexOf(find);
                        if(i<0){
                            Toast.makeText(MainActivity.this, "No se encontro algun grafico", Toast.LENGTH_SHORT).show();
                            TV2.setText("");
                            arrayText=null;
                            return;
                        }else{
                            //Toast.makeText(MainActivity.this, "Se encontro el inicio del grafico: "+i, Toast.LENGTH_SHORT).show();
                        }
                        String find2 = "\\end{tikzpicture}";
                        int j = contenido.indexOf(find2);
                        if(j<0){
                            Toast.makeText(MainActivity.this, "Parece ser que existe un problema en sintaxis", Toast.LENGTH_SHORT).show();
                            TV2.setText("");
                            arrayText=null;
                            return;
                        }else{
                            //Toast.makeText(MainActivity.this, "Se encontro el final del grafico: "+j, Toast.LENGTH_SHORT).show();
                        }
                        String grafico = contenido.substring(i,j+find2.length());
                        String begin = "\\begin{axis}";
                        int k = grafico.indexOf(begin);
                        if(k<0){
                            Toast.makeText(MainActivity.this, "Parece ser que existe un problema en sintaxis", Toast.LENGTH_SHORT).show();
                            TV2.setText("");
                            arrayText=null;
                            return;
                        }else{
                            //Toast.makeText(MainActivity.this, "Se encontro el inicio del grafico: "+k, Toast.LENGTH_SHORT).show();
                        }
                        String addplot = "\\addplot";
                        int ubicacionAddplot = grafico.indexOf(addplot);
                        if(ubicacionAddplot<0){
                            Toast.makeText(MainActivity.this, "Parece ser que existe un problema en sintaxis", Toast.LENGTH_SHORT).show();
                            TV2.setText("");
                            arrayText=null;
                            return;
                        }else{
                            String buscarXbar = grafico.substring(k+begin.length(),ubicacionAddplot);
                            int xbar = buscarXbar.indexOf("xbar");
                            if(xbar<0) {
                                Toast.makeText(MainActivity.this, "Parece ser que existe un problema en sintaxis", Toast.LENGTH_SHORT).show();
                                TV2.setText("");
                                arrayText=null;
                                return;
                            }else{
                                String buscarXbar2 = buscarXbar.replaceAll("\\s+","");
                                int xbar2 = buscarXbar2.indexOf("[xbar]");
                                if(xbar2<0) {
                                    Toast.makeText(MainActivity.this, "Parece ser que existe un problema en sintaxis", Toast.LENGTH_SHORT).show();
                                    TV2.setText("");
                                    arrayText=null;
                                    return;
                                }else{
                                    //Toast.makeText(MainActivity.this, buscarXbar2+" lon:"+xbar2, Toast.LENGTH_SHORT).show();
                                }
                            }


                            //Toast.makeText(MainActivity.this, buscarXbar2+" longit:"+buscarXbar2.length(), Toast.LENGTH_SHORT).show();
                        }
                        String end = "\\end{axis}";
                        int l = grafico.indexOf(end);
                        if(l<0) {
                            Toast.makeText(MainActivity.this, "Parece ser que existe un problema en sintaxis", Toast.LENGTH_SHORT).show();
                            TV2.setText("");
                            arrayText=null;
                            return;
                        }
                        String grafico2 = grafico.substring(ubicacionAddplot+addplot.length(),l);
                        int llave = grafico2.indexOf("{");
                        if(llave<0) {
                            Toast.makeText(MainActivity.this, "Parece ser que existe un problema de sintaxis", Toast.LENGTH_SHORT).show();
                            TV2.setText("");
                            arrayText=null;
                            return;
                        }
                        else{
                            String coordinates = grafico2.substring(0,llave);
                            //Toast.makeText(MainActivity.this, coordinates, Toast.LENGTH_SHORT).show();
                            String sinEspacios = coordinates.replaceAll("\\s+","");
                            int coor = sinEspacios.indexOf("coordinates");
                            if(coor<0) {
                                Toast.makeText(MainActivity.this, "Parece ser que existe un problema en sintaxis", Toast.LENGTH_SHORT).show();
                                TV2.setText("");
                                arrayText=null;
                                return;
                            }
                        }
                        String valoresTexto = grafico2.substring(llave+1);
                        String valoresTexto2 = valoresTexto.replaceAll("\\s+","");
                        arrayText = valoresTexto2.substring(0,valoresTexto2.length()-2);
                        TV2.setText(arrayText);
                    }
                })
                .build()
                .show();
    }

}