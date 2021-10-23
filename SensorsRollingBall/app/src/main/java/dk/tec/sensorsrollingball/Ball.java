package dk.tec.sensorsrollingball;

import android.graphics.Rect;

public class Ball
{
    private float xCenter, yCenter;
    private int radius;
    private int color;

    public Ball(float x, float y, int radius, int color)
    {
        this.xCenter = x;
        this.yCenter = y;
        this.radius = radius;
        this.color = color;
    }

    public float getXCenter(){return xCenter;}
    public float getYCenter(){return yCenter;}
    public float getRadius(){return radius;}
    public float getColor(){return color;}

    public void setXCenter(float xCenter)
    {
        this.xCenter = xCenter;
    }

    public void setYCenter(float yCenter)
    {
        this.yCenter = yCenter;
    }

    public void setColor(int color)
    {
        this.color = color;
    }

    public void setRadius(int radius)
    {
        this.radius = radius;
    }

    public Rect getNorthRect()
    {
        Rect rect = new Rect((int)xCenter - radius, (int)yCenter - radius * 2,
                (int)xCenter + radius, (int)yCenter - radius);
        return rect;
    }
    public Rect getSouthRect()
    {
        Rect rect = new Rect((int)xCenter - radius, (int)yCenter + radius,
                (int)xCenter + radius, (int)yCenter + radius * 2);
        return rect;
    }
    public Rect getEastRect()
    {
        Rect rect = new Rect((int)xCenter + radius, (int)yCenter - radius,
                (int)xCenter + radius * 2, (int)yCenter + radius);
        return rect;
    }
    public Rect getWestRect()
    {
        Rect rect = new Rect((int)xCenter - radius * 2, (int)yCenter - radius,
                (int)xCenter - radius, (int)yCenter + radius);
        return rect;
    }
}
