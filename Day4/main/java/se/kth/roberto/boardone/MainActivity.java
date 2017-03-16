package se.kth.roberto.boardone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements Board.Boardhandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Board board = (Board) findViewById(R.id.boardOne);
        board.setHandler(this);
    }

    @Override
    public void onClick(int cellX, int cellY) {
        TextView w = (TextView)findViewById(R.id.textOne);
        w.setText("Hello" + cellX + " " + cellY);
    }
}
