package dk.tec.mygraphicstouch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout layout = findViewById(R.id.layout);

        MyGraphics mg = new MyGraphics(this);

        Thread thread = new Thread(mg);
        thread.start();


        layout.addView(mg);

    }
}