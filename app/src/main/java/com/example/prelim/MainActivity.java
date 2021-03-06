package com.example.prelim;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

        DatabaseHelper db;

        Uri imageUri;
        EditText textsearch;
        ListView lv;
        AlertDialog.Builder builder;
        ArrayList<Names> list = new ArrayList<Names>(); //this is my reference list
        ArrayList<Names> displaylist = new ArrayList<Names>(); //arraylist to display all data
//        ArrayList<Names> findnames = new ArrayList<>(); //arraylist to display filter search
        Adapter adapter, adapter1, madapter;
        private Names names = new Names();

        private static final int PICK_IMAGE = 100;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            textsearch = (EditText) findViewById(R.id.textsearch);
            lv = (ListView) findViewById(R.id.listview);

            db = new DatabaseHelper(this);
            displaylist = db.getAll();

//            adapter1 = new Adapter(this, list);
            adapter = new Adapter(this, displaylist);
            Adapter madapter = new Adapter(this, list);
            lv.setAdapter(adapter);
//            lv.setAdapter(madapter);
            adapter.notifyDataSetChanged();

            //
            builder = new AlertDialog.Builder(this);
            lv.setOnItemClickListener(this);

            this.textsearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    list.clear();
                    String s1 = s.toString();
                    Pattern pattern = Pattern.compile(s1);

                    for (int i=0; i<displaylist.size(); i++){
                        String find = displaylist.get(i).getName().toLowerCase();
                        Matcher matcher = pattern.matcher(find);
                        if(matcher.find()){
                            list.add(displaylist.get(i));
                            lv.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }//end if
                    }
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
                    showDialog();
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
            final ImageView image = (ImageView) dialogView.findViewById(R.id.editImage);
            image.setImageURI(imageUri);

            image.setOnClickListener(new View.OnClickListener() { //adding listener to the imageview
                @Override
                public void onClick(View v) {
                    Intent gallery = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.INTERNAL_CONTENT_URI); //opening the gallery
                    startActivityForResult(gallery, PICK_IMAGE);
                }
            });

            dialogBuilder.setTitle("New Item");
            dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if(!textName.getText().toString().equals("")){
                        String name = textName.getText().toString();

                        long result = db.addNames(String.valueOf(imageUri),name);
                        Names names = new Names(imageUri, name);

                        list.add(names);
                        lv.setAdapter(madapter);
                        madapter.notifyDataSetChanged();
                        if(result > 0){

                            Toast.makeText(getApplicationContext(), "New Item added!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Fields can not be empty!", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            dialogBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    image.setImageResource(R.drawable.add_user);
                    textName.setText("");
                }
            });
            AlertDialog build = dialogBuilder.create();
            build.show();
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Names selecteditem = displaylist.get(position);
            int nameid = selecteditem.getId();
            db.deleteNames(nameid);
            list.remove(names);
            displaylist.remove(names);
            adapter.notifyDataSetChanged();
            if(nameid!=0){
                Toast.makeText(getApplicationContext(),"Deleted successfully.", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getApplicationContext(),"Unable to delete item.", Toast.LENGTH_LONG).show();

            }
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode != 0){
            if(data != null){
                imageUri = data.getData();
            }//end if
        }else{

        }

    }
}
