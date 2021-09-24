package dk.tec.sensorsrollingball;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;
import android.view.View;

public class RollerGraphics extends View implements SensorEventListener
{
    MainActivitySensors mas;

    Drawable ball;
    float ballX = 100;
    float ballY = 100;
    float deltaY, deltaX;
    int ballWidth, ballHeight;

    int screenWidth, screenHeight;
    int ballMargin = 40;

    float bounce = -0.7f;

    int numberDraw, numberSensorChanged;

    Matrix matrix = new Matrix(7, 10);

    public RollerGraphics(MainActivitySensors mainActivitySensors)
    {
        super(mainActivitySensors);
        mas = mainActivitySensors;
        ball = mainActivitySensors.getDrawable(R.drawable.ball);
        ballHeight = (int)(ball.getIntrinsicHeight()/2.5);
        ballWidth = (int)(ball.getIntrinsicWidth()/2.5);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        screenWidth = 700;    // w;
        screenHeight = 1000;  //h;
        Log.d("OnSize", "Width: " + w + "  Height: " + h);
    }

    @Override
    public void draw(Canvas canvas)
    {
        //Log.d("Jan", "Draw: " + numberDraw++);
        super.draw(canvas);
        //ball.setBounds((int)ballX, (int)ballY, Math.round(ballX + ballWidth), Math.round(ballY + ballHeight));
        //ball.draw(canvas);
        matrix.drawMatrix(canvas);

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        canvas.drawCircle(ballX + ballWidth/2, ballY + ballWidth/2, 30, paint);



    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent)
    {
        //Log.d("Jan", "Sensor: " + numberSensorChanged++);

        mas.txtXslope.setText( String.format("%10.2f", sensorEvent.values[0]));
        mas.txtYslope.setText( String.format("%10.2f", sensorEvent.values[1]));
        mas.txtZslope.setText( String.format("%10.2f", sensorEvent.values[2]));

        deltaX -= sensorEvent.values[0];
        deltaY += sensorEvent.values[1];
        ballX += deltaX;
        ballY += deltaY;

        if(ballX + ballMargin < 0)
        {
            deltaX *= bounce;
            ballX = -ballMargin;
        }
        if(ballX + ballWidth - ballMargin > screenWidth)
        {
            deltaX *= bounce;
            ballX = screenWidth - ballWidth + ballMargin;
        }
        if(ballY + ballMargin < 0)
        {
            deltaY *= bounce;
            ballY = -ballMargin;
        }
        if(ballY + ballHeight - ballMargin > screenHeight)
        {
            deltaY *= bounce;
            ballY = screenHeight - ballHeight + ballMargin;
        }
        /////////////////////////////////////////////////////
        if(matrix.isCollisionVertical(ballX, ballY, ballX + ballWidth, ballY + ballHeight, ballMargin))
        {
            deltaX *= bounce;
            if(deltaX > 0)
                ballX += 10;
            else
                ballX -= 10;
            //Log.d("Delta","DeltaX: " + deltaX);
        }

        if(matrix.isCollisionHorizontal(ballX, ballY, ballX + ballWidth, ballY + ballHeight, ballMargin))
        {
            deltaY *= bounce; // * -0.7
            if(deltaY > 0)
                ballY += 10;
            else
                ballY -= 10;

            Log.d("Delta","DeltaY: " + deltaY);
        }

        invalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) { }
}
