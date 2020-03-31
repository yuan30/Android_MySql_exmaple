package com.example.amysqlexmaple;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReceiveDataActivity extends AppCompatActivity implements NegativeIonRVAdapter.OnItemClickListener {

    private MysqlConnect mMysqlConnect;
    private List<NegativeIonModel> mNegativeIonList;

    private RecyclerView rvNegativeIonRecyclerView;
    private NegativeIonRVAdapter mNegativeIonRVAdapter;

    //private ExecutorService mThreadPool;
    private Handler mHandler;
    private Runnable CONNRunable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_data);

        mMysqlConnect = new MysqlConnect();

        mNegativeIonList = new ArrayList<>();
        rvNegativeIonRecyclerView = findViewById(R.id.rv_negativeion_data);
        rvNegativeIonRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        rvNegativeIonRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        mNegativeIonRVAdapter = new NegativeIonRVAdapter(this, mNegativeIonList);
        rvNegativeIonRecyclerView.setAdapter(mNegativeIonRVAdapter);

        //mThreadPool = Executors.newCachedThreadPool();
        mHandler = new Handler();
        initRunnable();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume(){
        super.onResume();

        new Thread(CONNRunable).start();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
                default: return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void OnItemClick(View view, int position) {

    }

    private void initRunnable() {
        CONNRunable = new Runnable() {
            @Override
            public void run() {
                mMysqlConnect.CONN(); //NetworkOnMainThreadException 要新開Thread

                if(mMysqlConnect.getNegativeIonModelList() == null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ReceiveDataActivity.this, "資料讀取失敗", Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }

                rvNegativeIonRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mNegativeIonList.addAll(mMysqlConnect.getNegativeIonModelList());
                        mNegativeIonRVAdapter.notifyDataSetChanged();//要在原生Thread才能使用
                    }
                }, 500);

            }
        };
    }
}
