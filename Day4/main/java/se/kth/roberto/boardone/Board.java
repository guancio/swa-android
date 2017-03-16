package se.kth.roberto.boardone;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by guancio on 15/03/2017.
 */

public class Board extends View {
    private GestureDetector mDetector;
    private Boardhandler handler;

    class mListener extends GestureDetector.SimpleOnGestureListener {
        public boolean onDown(MotionEvent e) {
            return true;
        }
    }

    public void initDetector() {
        mDetector = new GestureDetector(this.getContext(), new mListener());
    }
    public Board(Context ctx) {
        super(ctx);
        initDetector();
    }

    public Board(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDetector();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int w = getWidth();
        int h = getHeight();

        Paint p = new Paint();
        p.setARGB(255, 255, 0, 255);
        Paint p2 = new Paint();
        p2.setARGB(255, 0, 255, 0);
        int recW = w/8;
        int recH = h/8;
        //Paint currentColor = p;
        for (int j =0; j<9; j++) {
            for (int i = 0; i < 8; i++) {
                Paint currentColor = ((i+j) % 2 == 0) ? p : p2;
                canvas.drawRect(i * recW, j*recH, recW + i * recW, recH+j*recH, currentColor);
            }
        }
    }

    public interface  Boardhandler {
        public void onClick(int cellX, int cellY);
    }

    public void setHandler(Boardhandler handler) {
        this.handler = handler;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean res = mDetector.onTouchEvent(event);
        if (! res) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                int w = getWidth();
                int h = getHeight();

                int cellX = (int)event.getX() / (w / 8);
                int cellY = (int)event.getY() / (h / 8);

                if (handler != null)
                    handler.onClick(cellX, cellY);
                res = true;
            }
         }
        return res;
    }
}
