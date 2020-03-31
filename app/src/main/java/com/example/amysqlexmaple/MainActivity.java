package com.example.amysqlexmaple;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

public class MainActivity extends AppCompatActivity {

    private TextView mTxtMysqlData;
    private Button mButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        mTxtMysqlData = findViewById(R.id.txtMysqlData);
        new Thread(new Runnable() {
            @Override
            public void run() {
                MysqlConnect mysqlConnect = new MysqlConnect();
                mysqlConnect.CONN();
                final String str = mysqlConnect.getResponse();
                mTxtMysqlData.post(new Runnable() {
                    @Override
                    public void run() {
                        mTxtMysqlData.setText(str);
                    }
                });
            }
        }).start();

        mButton = findViewById(R.id.button);
        mButton.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, ReceiveDataActivity.class);
            startActivity(intent);
            //new Thread(mutiThread).start();
        }
    };

    public void ButtonOnClickLister(View v){

        int BtnId = v.getId();
        if(BtnId == R.id.button2){
            Toast.makeText(MainActivity.this, "send data", Toast.LENGTH_SHORT).show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    MysqlConnect mysqlConnect = new MysqlConnect();
                    mysqlConnect.sendData();

                    final String str = mysqlConnect.getResponse();
                    mTxtMysqlData.post(new Runnable() {
                        @Override
                        public void run() {
                            mTxtMysqlData.setText(str);
                        }
                    });
                }
            }).start();
        }else if(BtnId == R.id.button3){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    MysqlConnect mysqlConnect = new MysqlConnect();
                    mysqlConnect.testSendData();

                    final String str = mysqlConnect.getResponse();
                    mTxtMysqlData.post(new Runnable() {
                        @Override
                        public void run() {
                            mTxtMysqlData.setText(str);
                        }
                    });
                }
            }).start();
        }
    }
    /* ======================================== */

    // 建立一個執行緒執行的事件取得網路資料
    // Android 有規定，連線網際網路的動作都不能再主線程做執行
    // 畢竟如果使用者連上網路結果等太久整個系統流程就卡死了
    /*private Runnable mutiThread = new Runnable(){
        private String result = "";
        public void run()
        {
            try {
                URL url = new URL("http://www.usblab.nctu.me/40643249test/GetData5.php");//http://localhost/getdata.php http://192.168.50.50/getData.php
                // 開始宣告 HTTP 連線需要的物件，這邊通常都是一綑的
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                // 建立 Google 比較挺的 HttpURLConnection 物件
                connection.setRequestMethod("POST");
                // 設定連線方式為 POST
                connection.setDoOutput(true); // 允許輸出
                connection.setDoInput(true); // 允許讀入
                connection.setUseCaches(false); // 不使用快取
                connection.connect(); // 開始連線

                int responseCode =
                        connection.getResponseCode();
                // 建立取得回應的物件
                if(responseCode ==
                        HttpURLConnection.HTTP_OK){
                    // 如果 HTTP 回傳狀態是 OK ，而不是 Error
                    InputStream inputStream =
                            connection.getInputStream();
                    // 取得輸入串流
                    BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
                    // 讀取輸入串流的資料
                    String box = ""; // 宣告存放用字串
                    String line = null; // 宣告讀取用的字串
                    while((line = bufReader.readLine()) != null) {
                        box += line + "\n";
                        // 每當讀取出一列，就加到存放字串後面
                    }
                    inputStream.close(); // 關閉輸入串流
                    result = box; // 把存放用字串放到全域變數
                }
                // 讀取輸入串流並存到字串的部分
                // 取得資料後想用不同的格式
                // 例如 Json 等等，都是在這一段做處理

            } catch(Exception e) {
                result = e.toString(); // 如果出事，回傳錯誤訊息
            }

            // 當這個執行緒完全跑完後執行
            runOnUiThread(new Runnable() {
                public void run() {
                    mTxtMysqlData.setText(result); // 更改顯示文字
                }
            });
        }
    };*/
}
