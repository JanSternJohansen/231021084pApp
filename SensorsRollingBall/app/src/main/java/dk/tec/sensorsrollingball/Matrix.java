package dk.tec.sensorsrollingball;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;

public class Matrix
{
    TrackElement[][] elements;

    ArrayList<Rect> verWalls;
    ArrayList<Rect> horWalls;
    ArrayList<Rect> holes;

    final int ELEMENT_SIZE = 100;
    int columns, rows;

    public Matrix(int cols, int rows)
    {
        this.columns = cols;
        this.rows = rows;

        Log.d("Matrix", "Matrix cols: " + cols + "  Rows: " + rows);
        elements = new TrackElement[cols][rows];
        elements[0][1] = new TrackElement(false, true, true);
        elements[1][1] = new TrackElement(false, true, true);
        elements[2][1] = new TrackElement(false, true, true);
        elements[3][1] = new TrackElement(false, true, true);
        elements[4][1] = new TrackElement(false, true, true);
        elements[5][1] = new TrackElement(false, true, true);


        makePlayAreaLists();
    }

    public void makePlayAreaLists()
    {
        verWalls = new ArrayList<Rect>();
        horWalls = new ArrayList<Rect>();
        holes = new ArrayList<Rect>();


        for(int col = 0; col < columns; col++)
        {
            for (int row = 0; row < rows; row++)
            {
                if(elements[col][row] != null) {
                    if (elements[col][row].isVerWall()) {
                        verWalls.add(makeVerRectangle(col, row));
                    }
                    if (elements[col][row].isHorWall()) {
                        horWalls.add(makeHorRectangle(col, row));
                    }
                    if (elements[col][row].isHole()) {
                        holes.add(makeHole(col, row));
                    }
                }
            }
        }
    }

    public Rect makeVerRectangle(int col, int row)
    {
        Rect rectangle = new Rect(col * ELEMENT_SIZE,
                                  row * ELEMENT_SIZE,
                                 col* ELEMENT_SIZE + 5,
                               (row + 1) * ELEMENT_SIZE);
        return rectangle;
    }

    public Rect makeHorRectangle(int col, int row)
    {
        Rect rectangle = new Rect(col * ELEMENT_SIZE,
                                  row * ELEMENT_SIZE,
                                 (col + 1)* ELEMENT_SIZE,
                               row * ELEMENT_SIZE + 5);
        return rectangle;
    }

    public Rect makeHole(int col, int row)
    {
        Rect rectangle = new Rect(col * ELEMENT_SIZE,
                                  row * ELEMENT_SIZE,
                                 (col + 1)* ELEMENT_SIZE,
                               (row + 1) * ELEMENT_SIZE);
        return rectangle;
    }


    public void drawMatrix(Canvas canvas)
    {
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);

        for(Rect r : verWalls)
            canvas.drawRect(r, paint);

        for(Rect r : horWalls)
            canvas.drawRect(r, paint);

        paint.setColor(Color.BLACK);
        for(Rect r : holes)
            canvas.drawCircle((r.left + r.right)/2,
                              (r.top + r.bottom)/2,
                           ELEMENT_SIZE/2 - 10,
                                  paint);
    }

    public boolean isCollisionVertical(float ballX1, float ballY1, float ballX2, float ballY2, float margin)
    {
        Rect rect = new Rect((int)(ballX1 + margin), (int)(ballY1 + margin),
                             (int)(ballX2 - margin), (int)(ballY2 - margin));
        for(Rect r : verWalls)
            if(r.intersect(rect))
            {
                return true;
            }
        return false;
    }

    public boolean isCollisionHorizontal(float ballX1, float ballY1, float ballX2, float ballY2, float margin)
    {
        Rect rect = new Rect((int)(ballX1 + margin), (int)(ballY1 + margin),
                (int)(ballX2 - margin), (int)(ballY2 - margin));
        for(Rect r : horWalls)
            if(r.intersect(rect))
            {
                return true;
            }
        return false;
    }
}
