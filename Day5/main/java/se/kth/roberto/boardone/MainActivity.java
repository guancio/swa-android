package se.kth.roberto.boardone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements Board.Boardhandler {
    private GameState currentGame;

    GameState createLevel1() {
        GameState game = new GameState();
        game.playerX = 0;
        game.playerY = 0;

        game.targetX = 6;
        game.targetY = 4;

        game.obstacles = new boolean[8][8];
        game.obstacles[1][3] = true;
        game.obstacles[1][4] = true;
        game.obstacles[1][5] = true;
        game.obstacles[2][5] = true;
        game.obstacles[3][5] = true;
        game.obstacles[4][5] = true;

        return game;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Board board = (Board) findViewById(R.id.boardOne);
        currentGame = createLevel1();

        board.setGame(currentGame);
        board.setHandler(this);
    }

    void checkEnd() {
        if ((currentGame.playerX == currentGame.targetX)
            && currentGame.playerY == currentGame.targetY) {
            TextView view = (TextView )findViewById(R.id.textOne);
            view.setText("END game");

            this.currentGame = createLevel1();
            Board board = (Board)findViewById(R.id.boardOne);
            board.setGame(this.currentGame);
            board.invalidate();
        }
    }

    @Override
    public boolean onRight() {
        if (currentGame.playerX >= 7) {
            return false;
        }
        if (currentGame.obstacles
                [currentGame.playerX+1]
                [currentGame.playerY])
            return false;
        currentGame.playerX += 1;

        checkEnd();
        return true;
    }

    @Override
    public void onLeft() {

    }

    @Override
    public void onTop() {

    }

    @Override
    public void onDown() {
        if (currentGame.playerY >= 7) {
            return;
        }
        if (currentGame.obstacles
                [currentGame.playerX]
                [currentGame.playerY+1])
            return;
        currentGame.playerY += 1;
        findViewById(R.id.boardOne).invalidate();
        checkEnd();
    }
}
