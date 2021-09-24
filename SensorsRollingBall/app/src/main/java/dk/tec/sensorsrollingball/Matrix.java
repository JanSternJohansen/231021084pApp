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
        elements[0][1] = new TrackElement(false, true, false);

        elements[1][1] = new TrackElement(false, true, false);
        elements[1][2] = new TrackElement(false, true, true);
        elements[1][3] = new TrackElement(false, false, true);
        elements[1][4] = new TrackElement(false, false, true);
        elements[1][5] = new TrackElement(false, false, true);
        elements[1][6] = new TrackElement(false, false, true);
        elements[1][7] = new TrackElement(false, false, true);
        elements[1][8] = new TrackElement(false, false, true);
        elements[1][9] = new TrackElement(false, true, false);

        elements[2][1] = new TrackElement(false, true, false);
        elements[2][2] = new TrackElement(false, true, false);
        elements[2][3] = new TrackElement(false, true, true);
        elements[2][4] = new TrackElement(false, false, true);
        elements[2][5] = new TrackElement(false, false, true);
        elements[2][6] = new TrackElement(false, false, true);
        elements[2][7] = new TrackElement(false, false, true);
        elements[2][8] = new TrackElement(false, true, false);
        elements[2][9] = new TrackElement(false, true, false);

        elements[3][1] = new TrackElement(false, true, false);
        elements[3][2] = new TrackElement(false, true, false);
        elements[3][3] = new TrackElement(false, true, false);
        elements[3][4] = new TrackElement(false, true, true);
        elements[3][5] = new TrackElement(false, true, false);
        elements[3][6] = new TrackElement(false, true, true);
        elements[3][7] = new TrackElement(false, true, false);
        elements[3][8] = new TrackElement(false, true, false);
        elements[3][9] = new TrackElement(false, true, false);

        elements[4][1] = new TrackElement(false, true, false);
        elements[4][2] = new TrackElement(false, true, false);
        elements[4][3] = new TrackElement(false, false, true);
        elements[4][4] = new TrackElement(false, false, true);
        elements[4][5] = new TrackElement(false, false, true);
        elements[4][6] = new TrackElement(false, false, true);
        //elements[4][7] = new TrackElement(false, true, true);
        elements[4][8] = new TrackElement(false, true, false);
        elements[4][9] = new TrackElement(false, true, false);

        elements[5][1] = new TrackElement(false, true, false);
        elements[5][2] = new TrackElement(false, false, true);
        elements[5][3] = new TrackElement(false, false, true);
        elements[5][4] = new TrackElement(false, false, true);
        elements[5][5] = new TrackElement(false, false, true);
        elements[5][6] = new TrackElement(false, false, true);
        elements[5][7] = new TrackElement(false, false, true);
        //elements[5][8] = new TrackElement(false, true, true);
        elements[5][9] = new TrackElement(false, true, false);

        elements[6][1] = new TrackElement(false, false, true);
        elements[6][2] = new TrackElement(false, false, true);
        elements[6][3] = new TrackElement(false, false, true);
        elements[6][4] = new TrackElement(false, false, true);
        elements[6][5] = new TrackElement(false, false, true);
        elements[6][6] = new TrackElement(false, false, true);
        elements[6][7] = new TrackElement(false, false, true);
        elements[6][8] = new TrackElement(false, false, true);
        //elements[6][9] = new TrackElement(false, true, true);


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
        Log.d("draw", "Efter verWalls");

        for(Rect r : horWalls)
            canvas.drawRect(r, paint);

        Log.d("draw", "Efter horWalls");
        paint.setColor(Color.BLACK);
        for(Rect r : holes)
            canvas.drawCircle((r.left + r.right)/2,
                              (r.top + r.bottom)/2,
                           ELEMENT_SIZE/2 - 10,
                                  paint);

        Log.d("draw", "Efter holes");
    }

    public boolean isCollisionVertical(float ballX1, float ballY1, float ballX2, float ballY2, float margin)
    {
        Rect rect = new Rect((int)(ballX1 + margin), (int)(ballY1 + margin),
                             (int)(ballX2 - margin), (int)(ballY2 - margin));
        for(Rect r : verWalls)
            if(Rect.intersects(r, rect))
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
            if(Rect.intersects(r, rect))
            {
                return true;
            }
        return false;
    }
}
