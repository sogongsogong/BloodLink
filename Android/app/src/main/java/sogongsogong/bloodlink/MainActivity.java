//package sogongsogong.bloodlink;
//
//import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.view.View;
//import android.view.Menu;
//import android.view.MenuItem;
//
//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//}
package sogongsogong.bloodlink;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private Button button1;
    private ImageButton Imgbutton;
    private ListView listview;
    private ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Adapter 생성
        adapter = new ListViewAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listview1);
        setData();
        listview.setAdapter(adapter);


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

                Intent intent = new Intent(MainActivity.this, ListsClickedActivity.class);
                intent.putExtra("order", order);
                intent.putExtra("title", title);
                intent.putExtra("writer", writer);
                intent.putExtra("goal", goal);
                intent.putExtra("content", content);
                startActivity(intent);
                // TODO : use item data.
            }
        }) ;

        button = (Button)findViewById(R.id.loginBnt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button.getText().toString().equals("로그인")){
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
                else{
                    button.setText("로그인");
                    //로그아웃 처리
                }
            }
        });

        button1 = (Button)findViewById(R.id.registerBnt);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        Imgbutton = (ImageButton)findViewById(R.id.boardBnt);
        Imgbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BoardActivity.class);
                startActivity(intent);
            }
        });
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
