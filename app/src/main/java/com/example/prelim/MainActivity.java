package com.example.prelim;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    EditText textsearch;
    ListView lv;
    AlertDialog.Builder builder;
    ArrayList<String> list = new ArrayList<String>(); //add an arraylist
    ArrayAdapter<String> adapter; //add an arrayadapter


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textsearch = (EditText) findViewById(R.id.textsearch);

        lv = (ListView) findViewById(R.id.listview);
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, list);
        lv.setAdapter(adapter);

        //
        builder = new AlertDialog.Builder(this);
        lv.setOnItemClickListener(this);

        this.textsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //using regular expression

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case R.id.action_add:
                showDialog(); //calling the showDialog() method
                return true;

            default:
                 return super.onOptionsItemSelected(item);
        }
    }

    public void showDialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText textName = (EditText) dialogView.findViewById(R.id.editName);

        dialogBuilder.setTitle("New Item");
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //set trappings
                if(!textName.getText().toString().equals("")){
                    list.add(textName.getText().toString());
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), "Item added!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Fields can not be empty!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        dialogBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog build = dialogBuilder.create();
        build.show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.list.remove(position);
        this.adapter.notifyDataSetChanged();
        Toast.makeText(getApplicationContext(), "Name deleted!", Toast.LENGTH_LONG).show();
    }
}
