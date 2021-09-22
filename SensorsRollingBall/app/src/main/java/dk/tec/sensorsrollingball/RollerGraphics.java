package dk.tec.sensorsrollingball;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.view.View;

public class RollerGraphics extends View implements SensorEventListener
{
    MainActivitySensors mas;

    Drawable ball;
    float ballX = 100;
    float ballY = 100;
    float deltaY, deltaX;
    int ballWidth, ballHeight;

    public RollerGraphics(MainActivitySensors mainActivitySensors)
    {
        super(mainActivitySensors);
        mas = mainActivitySensors;
        ball = mainActivitySensors.getDrawable(R.drawable.ball);
        ballHeight = ball.getIntrinsicHeight();
        ballWidth = ball.getIntrinsicWidth();
    }

    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);
        ball.setBounds((int)ballX, (int)ballY, (int)ballX + ballWidth, (int)ballY + ballHeight);
        ball.draw(canvas);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent)
    {


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) { }
}
