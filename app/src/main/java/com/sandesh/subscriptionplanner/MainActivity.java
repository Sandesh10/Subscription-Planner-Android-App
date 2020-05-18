package com.sandesh.subscriptionplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private SubscriptionAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<SubscriptionClass> subsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newEntry = new Intent(MainActivity.this, NewEntry.class);
                startActivityForResult(newEntry,1);
            }
        });

        loadData();
        buildRecyclerView();
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("myDataStore", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(subsList);
        editor.putString("mySubsList", json);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("myDataStore", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("mySubsList", null);
        Type type = new TypeToken<ArrayList<SubscriptionClass>>() {}.getType();
        subsList = gson.fromJson(json, type);

        if (subsList == null) {
            subsList = new ArrayList<>();
            createSubscriptionList();
        }

    }

    public void createSubscriptionList() {
        subsList.add(new SubscriptionClass("Netflix","This is a demo app. You can delete this","01/02/2020","9.99"));
    }

    public void buildRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new SubscriptionAdapter(subsList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new SubscriptionAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
                Toast.makeText(MainActivity.this, "Item deleted successfully",
                            Toast.LENGTH_SHORT).show();
                saveData();
            }
        });
    }

    public void removeItem(int position) {
        subsList.remove(position);
        adapter.notifyItemRemoved(position);
    }

    /* ---------------------------- OPTION MENU -----------------------------------*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.help){
            Intent help = new Intent(MainActivity.this, Help.class);
            startActivity(help);
        } else if (id == R.id.exit){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                // get the message
                SubscriptionClass message = NewEntry.getResultMessageKey(data);
                subsList.add(new SubscriptionClass(message.getSubsName(),message.getSubsDescription(),
                        message.getSubsDate(),message.getSubsAmount()));
                String result = message.getSubsName()+" "+message.getSubsDescription()+" "+
                        message.getSubsDate()+" "+message.getSubsAmount();
                saveData();

                Log.i("MyApp", "Successfully sent "+result);
            } else {
                Log.i("MyApp", "Activity canceled or something went wrong");
            }
        }
    }
}
