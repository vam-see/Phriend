package vamsee.phriend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main extends AppCompatActivity {

    Button btnStartSer, btnStopSer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStartSer = (Button) findViewById(R.id.btnStartService);
        btnStopSer = (Button) findViewById(R.id.btnStopService);

        btnStartSer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), OverlayService.class);
                intent.putExtra("BR","0");
                startService(intent);
            }
        });

        btnStopSer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), OverlayService.class);
                stopService(intent);
            }
        });
    }
}
