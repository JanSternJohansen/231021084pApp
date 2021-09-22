package dk.tec.mygraphicstouch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.content.ContextCompat;

public class MyGraphics extends View implements View.OnTouchListener, Runnable
{
    float circleX = 200, circleY = 200, radius = 50;
    boolean moving = false;  // Default
    float prevX, prevY;
    int screenWidth, screenHeight;
    /////////////////////////////////////////////
    Drawable img;
    int imgX = 100, imgY = 100;
    int imgWidth, imgHeight;
    int imgDeltaX = 1, imgDeltaY = 1;


    public MyGraphics(Context context) {
        super(context);
        this.setOnTouchListener(this);

        img = context.getDrawable(R.drawable.einstein10);
        imgHeight = img.getIntrinsicHeight()/4;
        imgWidth = img.getIntrinsicWidth()/4;

        //d = ContextCompat.getDrawable(getContext(), R.drawable.einstein10);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        screenWidth = w;
        screenHeight = h;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        img.setBounds(imgX, imgY, imgX + imgWidth, imgY + imgHeight);
        img.draw(canvas);
        //////////////////////////////////////////////
        Paint paint = new Paint();
        paint.setColor(Color.MAGENTA);
        paint.setTextSize(250);
        canvas.drawCircle( circleX, circleY, radius, paint);
        paint.setColor(Color.BLUE);
        canvas.drawText("Hej fra Jan", 200, 550, paint);

        Rect rect1 = new Rect(100,700,450, 1250);
        Rect rect2 = new Rect(180, 680, 300, 900);
        canvas.drawRect(rect1, paint);
        paint.setColor(Color.RED);
        canvas.drawRect(rect2, paint);
        if(rect1.intersect(rect2)){
            Log.d("Jan", "Der er kollision");
        }
        else
        {
            Log.d("Jan", "Der er ikke kollision");
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent)
    {
        float newX = motionEvent.getX();
        float newY = motionEvent.getY();

        switch(motionEvent.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                float xDelta = circleX - newX;
                float yDelta = circleY - newY;
                float delta = (float)Math.sqrt(Math.pow(xDelta, 2) + Math.pow(yDelta, 2));
                if(delta < radius)
                {
                    moving = true;
                    prevX = newX;
                    prevY = newY;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(moving)
                {
                    float xMoveTo = circleX + newX - prevX;
                    float yMoveTo = circleY + newY - prevY;

                    if(xMoveTo > 0 + radius && xMoveTo < screenWidth - radius
                       && yMoveTo > 0 + radius && yMoveTo < screenHeight - radius)
                    {
                        circleX = xMoveTo;
                        circleY = yMoveTo;
                        prevX = newX;
                        prevY = newY;

                        invalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                moving = false;
                break;
        }
        return true;
    }

    @Override
    public void run()
    {
        while(true)
        {
            imgX += imgDeltaX;
            imgY += imgDeltaY;
            if(imgX < 0 || imgX > screenWidth - imgWidth)
                imgDeltaX *= -1;
            if(imgY < 0 || imgY > screenHeight - imgHeight)
                imgDeltaY *= -1;



            postInvalidate(); // Fordi det er en anden tråd
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e)
            {
            }
        }
    }
}
