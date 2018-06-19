package com.example.yeongrae.myapplication;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ListsClickedActivity extends AppCompatActivity {

    private Intent intent;
    private TextView order;
    private TextView title;
    private TextView writer;
    private TextView goal;
    private TextView content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists_clicked);

        intent = getIntent(); // 보내온 Intent를 얻는다
        order = (TextView)findViewById(R.id.order);
        title = (TextView)findViewById(R.id.title);
        writer = (TextView)findViewById(R.id.writer);
        goal = (TextView)findViewById(R.id.goal);
        content = (TextView)findViewById(R.id.content);


        order.setText(intent.getIntExtra("order", 1));
        title.setText(intent.getStringExtra("title"));
        writer.setText(intent.getStringExtra("writer"));
        goal.setText(intent.getIntExtra("goal", 0));
        content.setText(intent.getStringExtra("content"));
    }
}
