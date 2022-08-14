package com.easygrader.exampracapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {


    File file;
    String[] arr;
    ArrayList<fileParts> fileSplit = new ArrayList<>();
    RecyclerView recyclerView;

    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //past exam scrollview
        recyclerView = findViewById(R.id.pastExamScroll);
        linearLayout = findViewById(R.id.linearLayout);

        //Show past exams by looking at files directory
        file = getFilesDir();
        arr = file.list();
        if(arr != null) {
            for (String s : arr) {
                if (s.endsWith(".txt")) {
                    load(s);
                }
            }
        }

        //sorting itemss
        for (int i = 0; i < fileSplit.size() - 1; i++)
            for (int j = 0; j < fileSplit.size() - i - 1; j++)
                if (fileSplit.get(j).getCurTimee() < fileSplit.get(j + 1).getCurTimee()) {
                    fileParts temp = fileSplit.get(j);
                    fileSplit.set(j, fileSplit.get(j + 1));
                    fileSplit.set(j + 1, temp);
                }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        new ItemTouchHelper(callback).attachToRecyclerView(recyclerView);

        recyclerView.setAdapter(new CustomAdapter(fileSplit));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        //onClick recyclerView
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        openActivity3(fileSplit.get(position).getFileTitle() + ".txt");
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                }));
    }

    ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            Snackbar snackbar = Snackbar.make(recyclerView, "Item Deleted", Snackbar.LENGTH_LONG);
            snackbar.show();

            //Get exact file and delete
            File filee = new File(getFilesDir() + "/" + fileSplit.get(viewHolder.getAdapterPosition()).getFileTitle() + ".txt");
            filee.delete();
            fileSplit.remove(viewHolder.getAdapterPosition());
            Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
        }
    };



    public void onNewExamBtnClick(View view) {
        openActivity2();
    }

    public void load(String fileName) {
        FileInputStream fis = null;

        try {
            fis = openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            //Get title and score of a file given fileName
            String line;
            String[] lineArray;

            boolean firstLine = true;
            while((line = br.readLine()) != null && firstLine) {
                lineArray = line.split(",");
                fileSplit.add(new fileParts(lineArray[0], Double.valueOf(lineArray[4]), Integer.parseInt(lineArray[5])));
                firstLine = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void openActivity2() {
        Intent intent = new Intent(this, GenerateExamActivity.class);
        startActivity(intent);
    }

    public void openActivity3(String title) {
        Intent intent = new Intent(this, TakeExamActivity.class);
        intent.putExtra("fileName", title);
        startActivity(intent);
    }
}