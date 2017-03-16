package se.kth.roberto.boardone;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
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

    private GameState game;
    public void setGame(GameState game) {
        this.game = game;
    }

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

        Paint wall = new Paint();
        wall.setARGB(128, 0, 0, 0);

        int recW = w/8;
        int recH = h/8;
        //Paint currentColor = p;
        for (int j =0; j<8; j++) {
            for (int i = 0; i < 8; i++) {
                Paint currentColor = ((i+j) % 2 == 0) ? p : p2;
                canvas.drawRect(i * recW, j*recH, recW + i * recW, recH+j*recH, currentColor);
                if (game.obstacles[i][j])
                    canvas.drawRect(i * recW, j*recH, recW + i * recW, recH+j*recH, wall);
            }
        }

        Resources res = getResources();
        Drawable draw = res.getDrawable(R.drawable.roberto);
        draw.setBounds(game.playerX*recW, game.playerY*recH, (game.playerX+1)*recW, (game.playerY+1)*recH);
        draw.draw(canvas);

        Drawable drawT = res.getDrawable(R.drawable.treasure);
        drawT.setBounds(
                game.targetX*recW, game.targetY*recH,
                (game.targetX+1)*recW, (game.targetY+1)*recH);
        drawT.draw(canvas);
    }

    public interface  Boardhandler {
        public boolean onRight();
        public void onLeft();
        public void onTop();
        public void onDown();
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

                if (handler != null) {
                    int dx = cellX - game.playerX;
                    int dy = cellY - game.playerY;
                    double angle = Math.atan((double)dy/dx);

                    boolean res1 = false;
                    if (cellX > game.playerX && cellY == game.playerY);
                        res1 = handler.onRight();
                    if (cellX < game.playerX && cellY == game.playerY)
                        handler.onLeft();
                    if (cellY < game.playerY&& cellX == game.playerX)
                        handler.onTop();
                    if (cellY > game.playerY&& cellX == game.playerX)
                        handler.onDown();

                    if (res1)
                        this.invalidate();
                }
                res = true;
            }
         }
        return res;
    }
}
