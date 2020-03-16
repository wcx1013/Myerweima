package com.example.myerweima;

import android.app.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

public class MainActivity extends AppCompatActivity {

    private ZXingView mZxingview;
    private Button mStartSpot;
    private Button mStopSpot;
    private Button mOpenFlashlight;
    private Button mCloseFlashlight;
    private Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();

    }

    private void initview() {
        mZxingview = findViewById(R.id.zxingview);
        mStartSpot = findViewById(R.id.start_spot);
        mStopSpot = findViewById(R.id.stop_spot);
        mOpenFlashlight = findViewById(R.id.open_flashlight);
        mCloseFlashlight = findViewById(R.id.close_flashlight);
        mZxingview.changeToScanBarcodeStyle();//扫二维码
        mZxingview.setDelegate(new QRCodeView.Delegate() {
            @Override
            public void onScanQRCodeSuccess(String result) {
                Log.d("二维码扫描结果","result"+result);
                Toast.makeText(activity,result, Toast.LENGTH_SHORT).show();
                //扫描得到结果震动一下表示
                vibrate();

                //获取结果后三秒后，重新开始扫描
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mZxingview.startSpot();
                    }
                },3000);
            }



            @Override
            public void onScanQRCodeOpenCameraError() {
                Toast.makeText(activity, "打开相机错误！", Toast.LENGTH_SHORT).show();
            }
        });
        mStartSpot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mZxingview.startSpot();
                Toast.makeText(activity, "startSpot", Toast.LENGTH_SHORT).show();
            }
        });
        mStopSpot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mZxingview.stopSpot();
                Toast.makeText(activity,"stopSpot",Toast.LENGTH_SHORT).show();
            }
        });
        mOpenFlashlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mZxingview.openFlashlight();
                Toast.makeText(activity,"openFlashlight",Toast.LENGTH_SHORT).show();
            }
        });
        mCloseFlashlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mZxingview.closeFlashlight();
                Toast.makeText(activity,"closeFlashlight",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mZxingview.startCamera();
        //强制手机摄像头镜头朝向前边
        //mZxingview.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);
    mZxingview.showScanRect();//显示扫描方框
    }

    @Override
    protected void onStop() {
        mZxingview.startCamera();
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        mZxingview.onDestroy();
        super.onDestroy();
    }

    private void vibrate() {
        Vibrator vibrator= (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }
}
