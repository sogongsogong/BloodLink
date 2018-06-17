package com.example.yeongrae.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class BoardActivity extends AppCompatActivity{
    private ListView listview ;
    private ListViewAdapter adapter;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        // Adapter 생성
        adapter = new ListViewAdapter() ;
        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listview1);
        setData();
        listview.setAdapter(adapter);

        button = (Button)findViewById(R.id.write_Bnt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(getApplicationContext(), WriteActivity.class);
                startActivity(intent);
            }
        });


        // 위에서 생성한 listview에 클릭 이벤트 핸들러 정의.
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // get item
                ListViewItem item = (ListViewItem) parent.getItemAtPosition(position) ;

                int order = item.getOrder();
                String title = item.getTitle() ;
                String writer = item.getWriter() ;
                int goal = item.getGoal() ;
                String content = item.getContent();

                Intent intent = new Intent(BoardActivity.this, ListsClickedActivity.class);
                intent.putExtra("order", order);
                intent.putExtra("title", title);
                intent.putExtra("writer", writer);
                intent.putExtra("goal", goal);
                intent.putExtra("content", content);
                startActivity(intent);
                // TODO : use item data.
            }
        }) ;
    }

    private void setData(){
        int[] orders = getResources().getIntArray(R.array.order);
        String[] titles = getResources().getStringArray(R.array.title);
        String[] writers = getResources().getStringArray(R.array.writer);
        int[] goals = getResources().getIntArray(R.array.goal);
        String[] contents = getResources().getStringArray(R.array.contents);

        for(int i = 0; i < orders.length; i++){
            ListViewItem item = new ListViewItem();
            item.setOrder(orders[i]);
            item.setTitle(titles[i]);
            item.setWriter(writers[i]);
            item.setGoal(goals[i]);
            item.setContent(contents[i]);

            adapter.addItem(item);
        }
    }
}
