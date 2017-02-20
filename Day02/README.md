# Android Day02 custom canvas
Some resources useful for this lecture:
* https://developer.android.com/guide/topics/graphics/2d-graphics.html
* https://developer.android.com/training/custom-views/create-view.html
* https://developer.android.com/guide/topics/ui/custom-components.html

Even if the Android framework provides a wide range of
components/widgets (in Android called View), we need
sometime to implement an ad-hoc View to display custom graphics.
This is specially true for games, whose interface is difficult
to build on top of existing components. In this lecture we will experiment
in defining a new graphical widget: a 8x8 chess board.

To create a new widget, we define the class BoardView
that extends the class View
``` Java
public class BoardView extends View {
    public BoardView(Context context) {
        super(context);
    }
```

The main purpose of a view is to display something on the
screen. The Android runtine invokes the onDraw method of the
View whenever it needs to update the graphics of our widget.
Add the following method to the BoardView class:

```Java
@Override
protected void onDraw(Canvas canvas) {
    int height = getHeight();
    int width = getWidth();
    Paint blackPaint = new Paint();
    Paint whitePaint = new Paint();
    whitePaint.setARGB(255, 255, 255, 255);
    for (int i=0; i<8; i++) {
        for (int j=0; j<8; j++) {
            RectF rect = new RectF(width/8*i, height/8*j, width/8*(i+1), height/8*(j+1));
            if ((i+j)%2 == 0)
                canvas.drawRect(rect, blackPaint);
            else
                canvas.drawRect(rect, whitePaint);
        }
    }
}
```

The parameter of the method is a `Canvas`. This is the graphical
context where we can draw our interface. Imagine a canvas as a
proxy between the widget and a sub-rectangle of the screen
that Android assigned to our widget.
In fact, our BoardView can coexist on the screen with several other widgets.

Intuitively, to draw a chess board we need two pens (Paint): some cells of the
board will be painted with the black pen (blackPaint), while others
with the white pen (whitePaint).
When you create a new Paint, its color is by default black. 
The method `setARGB(a, r, g, b);` allows us to change the color
of the paint by specifying the alpha/opacity of the color (i.e. 0 is a
completely transparent, while 255 is a completely opaque),
the red component (from 0 to 255),
the green component
and the blue component.

The chess board consists of 8x8 cells; every iteration of the double 
loop draw one cell of the board.
Let the cell i,h be the cell on the row i and column j. The first
cell (i.e. 0,0) should have a black color.
The second cell on the same row (i.e. 1,0) should have white color.
and so on. It is easy to show that the cell i,j should be black if the sum of
i and j is a multiple of 2.

When we program our BoardView, we do not know statically the portion (size
and resolution)
of the screen that will be allocated to out widget. This can also
change at run-time. So we ask the Android runtime
the height (`getHeight()`) and width (`getWidth()`) allocated to our
widget.


Main activity
```Java
protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  mLinearLayout = new LinearLayout(this);

  // Instantiate our custom View and define its properties
  BoardView bw = new BoardView(this);

  // Add the View to the layout and set the layout as the content view
  mLinearLayout.addView(bw);
  setContentView(mLinearLayout);
}
```


# Use of the new view in The XML
Add the following method to BoardView

```Java
public BoardView(Context context, AttributeSet attrs) {
  super(context, attrs);
}

@Override
protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
  this.setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
}
```

Do not create the layout programmatically deom the MainActivity,
load the XML resource:
```Java
@Override
protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_main);
}
```

Define the resource `activity_main.xml`
```XML
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/activity_main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.example.guancio.canvasapp.MainActivity"
    android:orientation="vertical">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World!" />

    <com.example.guancio.canvasapp.BoardView
        android:layout_width="wrap_content"
        android:layout_height="0dp"
        android:layout_weight="1"
        android:id="@+id/boardView" />

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World!" />

</LinearLayout>
```

# Adding an image to the board
Copy the image in the path `app/src/main/res/drawable/kth.png`

Change the BoardView as follows:
```Java
protected void onDraw(Canvas canvas) {
  int height = getHeight();
  int width = getWidth();
  Paint blackPaint = new Paint();
  Paint whitePaint = new Paint();
  whitePaint.setARGB(0, 255, 255, 255);
  for (int i=0; i<8; i++) {
    for (int j=0; j<8; j++) {
      RectF rect = new RectF(width/8*i, height/8*j, width/8*(i+1), height/8*(j+1));
      if ((i+j)%2 == 0)
          canvas.drawRect(rect, blackPaint);
      else
          canvas.drawRect(rect, whitePaint);
    }
  }

  Resources res = getResources();
  Drawable draw = res.getDrawable(R.drawable.kth);
  draw.setBounds(width/8*4, height/8*6, width/8*5, height/8*7);
  draw.draw(canvas);
}
```


# Intercept the user events and move the KTH logo
```Java

public class BoardView extends View {
    private final GestureDetector mDetector;
    int xpos;

    class mListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
    }

    public BoardView(Context context) {
        super(context);

        mDetector = new GestureDetector(this.getContext(), new mListener());
        xpos = 0;
    }
    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mDetector = new GestureDetector(this.getContext(), new mListener());
        xpos = 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int height = getHeight();
        int width = getWidth();
        Paint blackPaint = new Paint();
        Paint whitePaint = new Paint();
        whitePaint.setARGB(0, 255, 255, 255);
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                RectF rect = new RectF(width/8*i, height/8*j, width/8*(i+1), height/8*(j+1));
                if ((i+j)%2 == 0)
                    canvas.drawRect(rect, blackPaint);
                else
                    canvas.drawRect(rect, whitePaint);
            }
        }
        Resources res = getResources();
        Drawable draw = res.getDrawable(R.drawable.kth);
        draw.setBounds(width/8*(xpos), height/8*6, width/8*(xpos+1), height/8*7);
        draw.draw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = mDetector.onTouchEvent(event);
        if (!result) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getX() < this.getWidth() / 2)
                    this.xpos -= 1;
                else
                    this.xpos += 1;

                this.invalidate();

                result = true;
            }
        }
        return result;
    }
}
```

# Let the MainActivity to handle the events
Change the board view as follows:

```Java
public class BoardView extends View {


    ...

    public interface BoardViewListener {
        public void onClick(int x, int y);
    }
    private BoardViewListener eventListener = null;
    public void setEventListener(BoardViewListener listener) {
        this.eventListener = listener;
    }

    int xpos = 0;
    int ypos = 0;
    public int getXpos() {
        return this.xpos;
    }

    public int getYpos() {
        return this.ypos;
    }

    public void setCoordinates(int x, int y) {
        this.xpos = x;
        this.ypos = y;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (eventListener == null)
            return true;

        boolean result = mDetector.onTouchEvent(event);
        if (!result) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                eventListener.onClick((int)(event.getX()/this.getWidth()*8), (int)(event.getY()/this.getHeight()*8));

                result = true;
            }
        }
        return result;
    }
```

And the MainActivity as follows:
```Java
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final BoardView board = (BoardView) findViewById(R.id.boardView);
        board.setEventListener(new BoardView.BoardViewListener() {
            @Override
            public void onClick(int x, int y) {
                int newX = board.getXpos();
                int newY = board.getYpos();
                if (x > 5)
                    newX += 1;
                else if (x < 3)
                    newX -= 1;

                if (y >  5)
                    newY += 1;
                else if (y < 3)
                    newY -= 1;

                board.setCoordinates(newX, newY);
                board.invalidate();
            }
        });
    }
}
```