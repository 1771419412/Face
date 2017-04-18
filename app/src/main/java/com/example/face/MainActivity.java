package com.example.face;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.aip.face.AipFace;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    //设置APPID/AK/SK
    public static final String APP_ID = "9533086";
    public static final String API_KEY = "scy0t2WavPAhjhfFtgQNU0fs";
    public static final String SECRET_KEY = "6sSiGP0z0xOR5et4KzYlB5WBkQO7t2Bk";
    private AipFace client;
    private FaceView imgFace;
    private Button btnFace;
    private Handler mHandler;
    private int jjy=10086;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initclient();
        initHandler();
        initViews();
    }

    private void initHandler() {
        mHandler=new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what==jjy){
                    Rect rect= (Rect) msg.obj;
                    imgFace.drawFace(rect);
                    Toast.makeText(MainActivity.this, "jjy", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }

    private void initViews() {
        imgFace= (FaceView) findViewById(R.id.imgFace);
        btnFace= (Button) findViewById(R.id.btnFace);
        btnFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initbitmip();
            }
        });
    }

    private void initbitmip() {
       // Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.raw.timg2);
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.raw.dili3);
        imgFace.setImageBitmap(bitmap1);
        final byte[] imgByte=Bitmap2Bytes(bitmap1);
        final HashMap<String,String> paraMap=new HashMap<String,String>();
       paraMap.put("max_face_num", "1");
        paraMap.put("face_fields","age,beauty,expression,faceshape,gender,glasses,landmark,race,qualities");
        new Thread(new Runnable() {
            @Override
            public void run() {
               JSONObject jsonObject=client.detect(imgByte,paraMap);
                //left":243,"top":203,"width":215,"height":199
                Rect r=new Rect(243,203,215,199);
               Message msg=new Message();
                msg.what=jjy;
                msg.obj=r;
                mHandler.sendMessage(msg);


            }
        }).start();
    }

    private void initclient() {
        // 初始化一个FaceClient
        client = new AipFace(APP_ID, API_KEY, SECRET_KEY);
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

    }
    public static byte[] Bitmap2Bytes(Bitmap bm){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }
}
