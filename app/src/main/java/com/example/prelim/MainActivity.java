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

        Uri imageUri;

        EditText textsearch;
        ListView lv;
        AlertDialog.Builder builder;
        ArrayList<Names> namesArrayList = new ArrayList<>(); //arraylist to display all data
        ArrayList<Names> findnames = new ArrayList<>(); //arraylist to display filter search
        Adapter adapter;

        private static final int PICK_IMAGE = 100;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            textsearch = (EditText) findViewById(R.id.textsearch);
            lv = (ListView) findViewById(R.id.listview);

            adapter = new Adapter(this, namesArrayList);
            final Adapter mAdapter = new Adapter(this, findnames); //adapter for searchlist array
            lv.setAdapter(adapter);

            //
            builder = new AlertDialog.Builder(this);
            lv.setOnItemClickListener(this);

            this.textsearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    findnames.clear();
                    String s1 = s.toString();
                    Pattern pattern = Pattern.compile(s1);

                    for (int i=0; i<namesArrayList.size(); i++){
                        String find = namesArrayList.get(i).getName().toLowerCase();
                        Matcher matcher = pattern.matcher(find);
                        if(matcher.find()){
                            findnames.add(namesArrayList.get(i));
                            lv.setAdapter(mAdapter); //call the second adapter when search is called
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
            image.setImageURI(imageUri); //setting the drawable image to display the uploaded image Uri

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
                        Names names = new Names(imageUri, name);
                        adapter.list.add(names);
                        adapter.notifyDataSetChanged();
                        lv.setAdapter(adapter);
                        Toast.makeText(getApplicationContext(), "Item added!", Toast.LENGTH_SHORT).show();
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
            namesArrayList.remove(position); //remove the item in the clicked position
            adapter.notifyDataSetChanged(); //refresh the list after removing the item
            lv.setAdapter(adapter); //call the all item list adapter
            Toast.makeText(getApplicationContext(), "Name deleted!", Toast.LENGTH_LONG).show();
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
