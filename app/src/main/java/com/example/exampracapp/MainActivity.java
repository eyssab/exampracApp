package com.example.exampracapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaRouter;
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

public class MainActivity extends AppCompatActivity {

    RecyclerView scrollView;

    String fileTitle;
    Double curScore;

    File file;
    String[] arr;
    ArrayList<String> fileArr;
    ArrayList<Double> scores;
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
        file = new File("/data/user/0/com.example.exampracapp/files");
        arr = file.list();
        scores = new ArrayList<Double>(arr.length);
        fileArr = new ArrayList<String>(arr.length);
        if(arr != null) {
            for (int i = 0; i < arr.length; i++) {
                String s = arr[i];
                if (s.endsWith(".txt")) {
                    //System.out.println(s);
                    load(s);
                    fileArr.add(fileTitle);
                    scores.add(curScore);
                }
            }
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        new ItemTouchHelper(callback).attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(new CustomAdapter(fileArr,scores));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }


        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            Snackbar snackbar = Snackbar.make(recyclerView, "Item Deleted", Snackbar.LENGTH_LONG);
            snackbar.show();

            File filee = new File("/data/user/0/com.example.exampracapp/files/" + fileArr.get(viewHolder.getAdapterPosition()) + ".txt");
            System.out.println(filee);
            filee.delete();
            fileArr.remove(viewHolder.getAdapterPosition());
            scores.remove(viewHolder.getAdapterPosition());
            recyclerView.getAdapter().notifyDataSetChanged();
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
//ds
            String line;
            String[] lineArray;

            boolean firstLine = true;
            while((line = br.readLine()) != null && firstLine) {
                lineArray = line.split(",");
                fileTitle = lineArray[0];
                curScore = Double.valueOf(lineArray[3]);
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
}