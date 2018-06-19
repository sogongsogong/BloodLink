package sogongsogong.bloodlink;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class WriteActivity extends AppCompatActivity {

    private ListView listview;
    private ListViewAdapter adapter;
    private Button button;
    private Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        button = (Button)findViewById(R.id.register_Board);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Adapter 생성
                adapter = new ListViewAdapter() ;

                // 리스트뷰 참조 및 Adapter달기
                listview = (ListView) findViewById(R.id.listview1);

                //item 저장
                ListViewItem item = new ListViewItem();

                // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
                int order = adapter.getCount() + 1;
                String title = ((TextView) findViewById(R.id.title_Board)).getText().toString() ;
                String writer = ((TextView) findViewById(R.id.writer_Board)).getText().toString() ;
                int goal = Integer.parseInt(((TextView) findViewById(R.id.goal_Board)).getText().toString());
                String content = ((TextView) findViewById(R.id.content_Board)).getText().toString();

                item.setOrder(order);
                item.setTitle(title);
                item.setWriter(writer);
                item.setGoal(goal);
                item.setContent(content);
                adapter.addItem(item);
                listview.setAdapter(adapter);
                finish();
            }
        });

        button1 = (Button)findViewById(R.id.cancel_Board);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(getApplicationContext(), BoardActivity.class);
                startActivity(intent);
            }
        });
    }
}
