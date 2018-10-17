package com.example.administrator.myapplication;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final FocusView focusView=new FocusView(this);
        ViewGroup view =findViewById(R.id.rootview);
        view.addView(focusView);
        RecyclerView recyslerview = findViewById(R.id.recyclerview_test);
        CenterLinearLayoutManager layoutmanager = new CenterLinearLayoutManager(this);
        layoutmanager.setOrientation( LinearLayoutManager.VERTICAL);
        recyslerview.setLayoutManager(layoutmanager);
        recyslerview.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        Adapter adapter = new Adapter(this,focusView);
        recyslerview.setAdapter(adapter);
        layoutmanager.setOnChildSelectedListener(new CenterLinearLayoutManager.OnChildSelectedListener() {
            @Override
            public void onChildSelected(View view, int position) {

             if (view.hasFocus()) {
                    focusView.animate(view);
                } else {

                }

            }

        });

    }
}
