package com.example.algorithms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;

public class MainActivity extends AppCompatActivity {
    EditText proccess_editText, brustTime_editText;
    Button insert, calculate;
    ListView pro, bt, wt;
    TextView average_waiting_time, average_TAT;
    ArrayAdapter <String> adapter, adapter1, adapter2;

    ArrayList <String> proccess_list = new ArrayList <String> ();
    ArrayList <String> brust_time_list = new ArrayList <String> ();
    ArrayList <String> waiting_time_list;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        proccess_editText  = findViewById(R.id.editText_proccess);
        brustTime_editText = findViewById(R.id.editText_brustTime);

        insert    = findViewById(R.id.button_insert);
        calculate = findViewById(R.id.button_calculate);

        pro = findViewById(R.id.listView_proc);
        bt  = findViewById(R.id.listView_bt);
        wt  = findViewById(R.id.listView_wt);

        average_waiting_time = findViewById(R.id.textView_av);
        average_TAT          = findViewById(R.id.textView11);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, proccess_list);
        adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, brust_time_list);
        bt.setAdapter(adapter1);
        pro.setAdapter(adapter);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String proc = proccess_editText.getText().toString();
                String bt = brustTime_editText.getText().toString();

                if (proc.isEmpty() || bt.isEmpty()) {
                    Toast.makeText(MainActivity.this, "in vaild", Toast.LENGTH_SHORT).show();
                }
                else {
                    proccess_list.add(proc);
                    brust_time_list.add(bt);
                    adapter.notifyDataSetChanged();
                    adapter1.notifyDataSetChanged();

                    Toast.makeText(MainActivity.this, "plz enter both value", Toast.LENGTH_SHORT).show();
                    proccess_editText.setText("");
                    brustTime_editText.setText("");
                }
            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                waiting_time_list = new ArrayList<>(waiting_time(brust_time_list));
                adapter2 = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, waiting_time_list);
                wt.setAdapter(adapter2);
                average_waiting_time.setText("Average waiting time : " + String.valueOf(avWaitingTime(waiting_time_list)));
                average_TAT.setText("Average Turn Around Time : " + TAT(waiting_time_list, brust_time_list));
            }
        });
    }
    public static ArrayList <String> waiting_time( ArrayList <String> brust_time ) {
        ArrayList<String> wait_time = new ArrayList<String>();
            wait_time.add(0, "0");
            int counter = 0;

            for (int i = 1; i < brust_time.size(); i++) {
                wait_time.add(i, String.valueOf(Integer.parseInt(wait_time.get(i - 1)) + Integer.parseInt(brust_time.get(counter))));
                counter++;
            }
        return wait_time ;
    }

    public static double avWaitingTime( ArrayList<String> waiting_time ) {
        int av_wait_time = 0;
        for (int i = 0; i < waiting_time.size(); i++)
            av_wait_time += Integer.parseInt(waiting_time.get(i));
        return  av_wait_time / waiting_time.size();
    }

    public static int TAT (ArrayList<String> brust_time_list, ArrayList<String> waiting_time_list) {
        int turnAroundTime = 0;
        for (int i = 0; i < brust_time_list.size(); i++)
            turnAroundTime += Integer.parseInt(brust_time_list.get(i)) + Integer.parseInt(waiting_time_list.get(i));
        return turnAroundTime;
    }
}
