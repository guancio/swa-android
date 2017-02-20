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
in implementing a new graphical widget: a 8x8 chess board.

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
proxy between the widget and the sub-rectangle of the screen
that Android assigned to our widget.
In fact, our BoardView can coexist on the screen with several other widgets.

Intuitively, to draw a chess board we need two pens (Paint): some cells of the
board will be painted with the black pen (blackPaint), while others
with the white pen (whitePaint).
When you create a new Paint, its color is black by default. 
The method `setARGB(a, r, g, b);` allows us to change the color
of the paint by specifying the alpha/opacity of the color (i.e. 0 is a
completely transparent, while 255 is a completely opaque),
the red component (from 0 to 255),
the green component
and the blue component.

The chess board consists of 8x8 cells; every iteration of the double 
loop draws one cell of the board.
Let the cell i,h be the cell on the row i and column j. The first
cell (i.e. 0,0 topmost left cell) should have a black color.
The second cell on the same row (i.e. 1,0) should have white color.
and so on. It is easy to show that the cell i,j should be black if the sum of
i and j is a multiple of 2 (i.e. `(i+j)%2 == 0`).

When we program our BoardView, we do not know statically the portion (size
and resolution)
of the screen that will be allocated to out widget. This can also
change at run-time if the interface is updated dynamically.
Thus we ask the Android runtime
the height (`getHeight()`) and width (`getWidth()`) allocated to our
widget.

The width of every cell will be `width/8` and its height will be `height/8`.
Thus the top-most left cell will be a rectangle occupying the pixels starting from `(0,0)`
to `width/8, height/8`. The second cell on the same row will occupy
the pixels starting from `(width/8,0)` to `(width/8 + width/8, height/8)`.
The third cell from `(width/8 + width/8, height/8)` to `(3*width/8, height/8)` and so on.


In our Activity we create a minimal `LinearLayour` and we add the `BoardView` to it.
Notice that we are not creating the Activity Content using `XML`.

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


# Use of the new widget in XML
Writing Activity layouts in java is a pain. We would like to
be able to add our BoardView to an XML file similarly to
other Android widgets (like TextEntry).

First, we must modify add to BoardView with the following methods:
```Java
public BoardView(Context context, AttributeSet attrs) {
  super(context, attrs);
}

@Override
protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
  this.setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
}
```
Using XML the UI designer can set-up additional custom attributes to our widget (i.e.
color of the cells, number of cells). Thus we need a new constructor that
receives the custom attributes from the XML file. Currently we ignore them and we
forward them to the View constructor, which can handle the standard attributes (like width,
height, etc).
Additionally we should inform Android about the dimensions used by our widget.
We must call `setMeasuredDimension(int, int)` to store the measured width and height of this view.


Now we can use the `BoardView` in the `activity_main.xml`
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
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"
        android:id="@+id/boardView" />

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World!" />

</LinearLayout>
```
Notice that
(1) the BoardView is in the middle of two TextView in a vertical layout,
(2) we use the full qualified class name
to identify the widget (`com.example.guancio.canvasapp.BoardView`),
(3) we ask the widget to use the whole available width (`layout_width="match_parent"`),
(4) we ask the widget to expand his height to fill the space between
the two TextView (`layout_height="0dp"` and `layout_weight="1"`).


We can create the MainActivity loading the XML layout
```Java
@Override
protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_main);
}
```


# Adding an image to the board
Hereafter we load an image and we display it on top of the board.
First, copy the image in the path `app/src/main/res/drawable/kth.png`

![kth](kth.png)

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
From the resources, we get the drawable kth one and we depict it in the cell
`(5,7)`.


# Intercept the user events and move the KTH logo
We would like to move the kth logo using user touches. If the user
touches the left part of the board, we move the logo on the left;
if it touches the right part, we move the logo on the right.

First, we need to remember the position of the logo. With this purpose we add a
new field `xpos`, we initialize it to zero and we change the `onDraw` method to
display the logo in the right position:
```Java
public class BoardView extends View {
    int xpos;
    ...
    public BoardView(Context context) {
        super(context);
        xpos = 0;
    }
    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        xpos = 0;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        ...
        Resources res = getResources();
        Drawable draw = res.getDrawable(R.drawable.kth);
        draw.setBounds(width/8*(xpos), height/8*6, width/8*(xpos+1), height/8*7);
        draw.draw(canvas);
    }
```
Android has a complex gesture mechanism, which permit to handle
clicks, swipes etc.
`SimpleOnGestureListener` is a convenience class to extend when you only want to listen for a subset of all the gestures, in our case `onDown`.
This is notified when a tap occurs with the down MotionEvent that triggered it. This will be triggered immediately for every down event. All other events should be preceded by this.
We should override the handler to inform Android that we are interested in
`onDown` events.

```Java
public class BoardView extends View {
    ...
    class mListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
    ...
    private final GestureDetector mDetector;
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
}
```

Finally, we should handle the `MotionEvent`, reacting if the 
`GestureDetector` detected `onDown`:
```Java
public class BoardView extends View {
    ...
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
Notice that we invoke `this.invalidate()`. This inform Android that
something happened to the data baking back the `View` and that this must
be redrawn. **Never** invoke `onDraw` directly.

The resulting code of `BoardView` is the following
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
Handling the user input in the View is discouraged: this prevents
the View to be used in different activities. Imagine the onClick event
for a Button, we do not want to complete handling the event in the Button view,
since different Activities would like to react to this event differently.
Thus our goal is to forward the event from the view to a listener (e.g. an Activity).

First, we define the main traits of this listener: a class handling the BoardView
events should handle clicks. Also, the listener is interested in knowing with cell the
user clicked:
```Java
    public interface BoardViewListener {
        public void onClick(int x, int y);
    }
```

In order to update the View, the Activity (i.e. controller) should update the model behind
the view (in this case `xpos`). Thus we need the following methods:
```Java
public class BoardView extends View {
   ...
    public int getXpos() {
        return this.xpos;
    }
    public void setXpos(int x) {
        this.xpos = x;
    }
}

Finally, we need a mechanism to set the listener, remember it
and forward to it the events:
```Java
public class BoardView extends View {
    ...

    private BoardViewListener eventListener = null;
    public void setEventListener(BoardViewListener listener) {
        this.eventListener = listener;
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
Notice that we do not forward to the listener the exact coordinate clicked by the user.
We abstract from them and we forward the cell that contains the coordinate.
We now can handle the event directly from the Activity, by registering the proper handler:
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
                if (x > 5)
                    newX += 1;
                else if (x < 3)
                    newX -= 1;

		board.setXpos(newX);
                board.invalidate();
            }
        });
    }
}
```
Again, notice the `invalidate()` methods, which informs Android to update the view
of the board.
