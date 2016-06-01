package vamsee.phriend;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Vamsee on 3/12/2016.
 */
public class OverlayService extends Service {
    String value = "test";
    WindowManager windowManager;
    ImageView imageView;
    LayoutParams params;

    @Override
    public void onCreate(){
        super.onCreate();
//        drawOnScreen();
        imageView = new ImageView(this);

        imageView.setImageResource(R.drawable.deadpool);
        Log.d("onCreate:OverlayService", value);
        windowManager = (WindowManager)getSystemService(WINDOW_SERVICE);

        params = new WindowManager.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT,
                LayoutParams.TYPE_PHONE,
                LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
        );
        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 0;

        // add a overlay image in window
        windowManager.addView(imageView, params);

        //for moving the picture on touch and slide
        imageView.setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;
            private long touchStartTime = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //remove overlay image on long press
                if (System.currentTimeMillis() - touchStartTime > ViewConfiguration.getLongPressTimeout() && initialTouchX == event.getX()) {
                    windowManager.removeView(imageView);
                    stopSelf();
                    return false;
                }
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        touchStartTime = System.currentTimeMillis();
                        initialX = params.x;
                        initialY = params.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);
                        windowManager.updateViewLayout(v, params);
                        break;
//                    case MotionEvent.ACTION_BUTTON_PRESS:
//                        Toast.makeText(getApplicationContext(),"Hey",Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try{
            if (windowManager != null){
                windowManager.removeView(imageView);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        super.onStartCommand(intent, flags, startId);
        value = intent.getExtras().getString("BR");
        if (value.equalsIgnoreCase("1")){
            imageView.setImageResource(R.drawable.deadpool_msg);
            windowManager.updateViewLayout(imageView, params);
            Log.d("onStartCommand", value.toString());
        }
        else if (value.equalsIgnoreCase("2")){
            imageView.setImageResource(R.drawable.deadpool_call);
            windowManager.updateViewLayout(imageView, params);
            Log.d("onStartCommand", value.toString());
        }
        return START_STICKY;
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
