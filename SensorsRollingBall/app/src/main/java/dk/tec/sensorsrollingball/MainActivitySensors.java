package dk.tec.sensorsrollingball;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivitySensors extends AppCompatActivity implements SensorEventListener
{
    TextView txtXslope, txtYslope, txtZslope;
    LinearLayout layout;
    SensorManager sensorManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtXslope = findViewById(R.id.txtXslope);
        txtYslope = findViewById(R.id.txtYslope);
        txtZslope = findViewById(R.id.txtZslope);

        layout = findViewById(R.id.layout);
        ///////////////////////////////////////////////////////////



        //////////////////////////////////////////////////////////////


        RollerGraphics roller = new RollerGraphics(this);
        layout.addView(roller);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(roller, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),1000000);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent)
    {
        txtXslope.setText( String.format("%10.2f", sensorEvent.values[0]));
        txtYslope.setText( String.format("%10.2f", sensorEvent.values[1]));
        txtZslope.setText( String.format("%10.2f", sensorEvent.values[2]));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) { }
}