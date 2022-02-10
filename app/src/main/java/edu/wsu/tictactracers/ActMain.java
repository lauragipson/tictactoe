//Application: Tic Tac Tracers
package edu.wsu.tictactracers;

// Import packages
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

import static java.lang.Character.getNumericValue;

//--------------------------------------------------------------------
// class ActMain
//--------------------------------------------------------------------
public class ActMain extends AppCompatActivity {

    //----------------------------------------------------------------
    // Constants and variables
    //----------------------------------------------------------------
    public static final String APP_NAME = "Tic Tac Tracers";
    public static final String APP_VERSION = "1.0";
    public static final String APP_CREATOR = "Laura Gipson";
    private Toolbar tbrMain;
    private ImageButton tile0;
    private ImageButton tile1;
    private ImageButton tile2;
    private ImageButton tile3;
    private ImageButton tile4;
    private ImageButton tile5;
    private ImageButton tile6;
    private ImageButton tile7;
    private ImageButton tile8;
    private Button resGameBtn;
    private Button resStatsBtn;
    private TableLayout gameBoard;
    private TextView statusDisplay;
    private TextView gamesVal;
    private TextView huWinsVal;
    private TextView huWinsPct;
    private TextView comWinsVal;
    private TextView comWinsPct;
    private String[] tileStatus = new String[9];
    private int[] available = new int[9];
    private String gameOverMessage;
    private int moves;
    private int games;
    private int hWins;
    private int cWins;
    private boolean gameOver = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laymain);

        //Declare controls
        gamesVal = (TextView) findViewById(R.id.gamesVal);
        huWinsVal = (TextView) findViewById(R.id.huWinsVal);
        huWinsPct = (TextView) findViewById(R.id.huWinsPct);
        comWinsVal = (TextView) findViewById(R.id.comWinsVal);
        comWinsPct = (TextView) findViewById(R.id.comWinsPct);
        tile0 = (ImageButton) findViewById(R.id.t0);
        tile1 = (ImageButton) findViewById(R.id.t1);
        tile2 = (ImageButton) findViewById(R.id.t2);
        tile3 = (ImageButton) findViewById(R.id.t3);
        tile4 = (ImageButton) findViewById(R.id.t4);
        tile5 = (ImageButton) findViewById(R.id.t5);
        tile6 = (ImageButton) findViewById(R.id.t6);
        tile7 = (ImageButton) findViewById(R.id.t7);
        tile8 = (ImageButton) findViewById(R.id.t8);
        statusDisplay = (TextView) findViewById(R.id.statusDisplay);

        // Initialize enum variables
        Shared.Data.games = 0;
        Shared.Data.hWins = 0;
        Shared.Data.cWins = 0;

        // Define default game board layout
        gameBoard = (TableLayout) findViewById(R.id.gameBoard);
        Shared.Data.sharedBGColor = "#000000";
        Shared.Data.sharedTileColor = "#ffffff";
        Shared.Data.sharedHuman = getResources().getIdentifier("neonx", "drawable",
                getPackageName());
        Shared.Data.sharedComputer = getResources().getIdentifier("neono", "drawable",
                getPackageName());

        // Define and connect to toolbar
        tbrMain = findViewById(R.id.tbrMain);
        setSupportActionBar(tbrMain);
        tbrMain.setNavigationIcon(R.mipmap.ic_launcher_ttt);

        // Define Reset Game button
        resGameBtn = (Button) findViewById(R.id.resGameBtn);
        resGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(v.getContext());
                builder.setTitle("Reset game?");
                builder.setMessage("All tiles will be reset.");
                builder.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int option) {
                                for (int i = 0; i < 9; i++) {
                                    String temp = "t" + i;
                                    ImageButton clear = (ImageButton)
                                            findViewById(getResources().getIdentifier(temp,
                                                    "id", getPackageName()));
                                    clear.setImageResource(android.R.color.transparent);
                                    clear.setBackgroundColor(Color.parseColor(Shared.Data.sharedTileColor));
                                    tileStatus[i] = null;
                                    available[i] = 0;
                                }
                                statusDisplay.setText("(game result)");
                                gameOver = false;
                                moves = 0;
                            }
                        });

                builder.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int option) {
                                System.out.println("### DEBUG ### " +
                                        "Two-button dialog box: \"No\" " +
                                        "button pressed.");
                            }
                        });
                builder.show();
            }
        });

        // Define Reset Stats button
        resStatsBtn = (Button) findViewById(R.id.resStatsBtn);
        Shared.Data.resets = 0;
        resStatsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(v.getContext());
                builder.setTitle("Reset stats?");
                builder.setMessage("All game play stats, wins, and percentage" +
                        " info. will be reset.");
                builder.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int option) {
                                Shared.Data.resets += 1;
                                gamesVal.setText("");
                                huWinsVal.setText("");
                                comWinsVal.setText("");
                                huWinsPct.setText("");
                                comWinsPct.setText("");
                                Shared.Data.cWins = 0;
                                Shared.Data.hWins = 0;
                                Shared.Data.games = 0;
                            }
                        });

                builder.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int option) {
                                System.out.println("### DEBUG ### " +
                                        "Two-button dialog box: \"No\" " +
                                        "button pressed.");
                            }
                        });
                builder.show();
            }
        });

    }

    //----------------------------------------------------------------
    // onCreateOptionsMenu
    //----------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.overflow, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //----------------------------------------------------------------
    // onOptionsItemSelected
    //----------------------------------------------------------------
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Intent intent = new Intent(getApplicationContext(),
                        ActSettings.class);
                startActivity(intent);
                return true;

            case R.id.about:
                android.app.AlertDialog.Builder builder =
                        new android.app.AlertDialog.Builder(this);
                builder.setTitle(APP_NAME);
                builder.setMessage("Version: " + APP_VERSION + "\nAuthor: " +
                        APP_CREATOR);
                builder.setPositiveButton("OK", null);
                builder.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //----------------------------------------------------------------
    // onPause
    //----------------------------------------------------------------
    @Override
    protected void onPause() {
        super.onPause();
    }

    //----------------------------------------------------------------
    // onResume
    //----------------------------------------------------------------
    @Override
    protected void onResume() {
        super.onResume();
        gameBoard.setBackgroundColor(Color.parseColor(Shared.Data.sharedBGColor));
        tile0.setBackgroundColor(Color.parseColor(Shared.Data.sharedTileColor));
        tile1.setBackgroundColor(Color.parseColor(Shared.Data.sharedTileColor));
        tile2.setBackgroundColor(Color.parseColor(Shared.Data.sharedTileColor));
        tile3.setBackgroundColor(Color.parseColor(Shared.Data.sharedTileColor));
        tile4.setBackgroundColor(Color.parseColor(Shared.Data.sharedTileColor));
        tile5.setBackgroundColor(Color.parseColor(Shared.Data.sharedTileColor));
        tile6.setBackgroundColor(Color.parseColor(Shared.Data.sharedTileColor));
        tile7.setBackgroundColor(Color.parseColor(Shared.Data.sharedTileColor));
        tile8.setBackgroundColor(Color.parseColor(Shared.Data.sharedTileColor));
    }

    //----------------------------------------------------------------
    // onImageButtonClicked
    //----------------------------------------------------------------
    public void onImageButtonClicked(View view) {
        // Declare variables
        Toast toast;
        String message;
        String res = view.getResources().getResourceEntryName(view.getId());
        int resID = getNumericValue(res.charAt(1));

        // Test whether to show image
        if (gameOver == false) {
            showGameImage(resID);
        }
        else {
            message = "Game Over. Press Reset Game button to start a new game.";
            // Show message
            toast = Toast.makeText(getApplicationContext(),
                    message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.show();
        }
    }

    //----------------------------------------------------------------
    // showGameImage
    //----------------------------------------------------------------
    public void showGameImage(int resID) {
        // Declare variables
        Toast toast;
        String message;
        String tempHuman = "t" + resID;
        ImageButton humanTile = (ImageButton)
                findViewById(getResources().getIdentifier(tempHuman, "id", getPackageName()));

        // Test game tile availability and play
        if (available[resID] == 0) {
            humanTile.setBackgroundColor(0x00000000);
            humanTile.setImageResource(Shared.Data.sharedHuman);
            tileStatus[resID] = "human";
            available[resID] = 1;
            moves += 1;
            if (moves > 4 && gameOver == false) {
                checkWinner();
            }
            if (moves < 9 && gameOver == false) {
                Random random = new Random();
                int rand = random.nextInt(8);
                while (available[rand] == 1) {
                    rand = random.nextInt(8);
                }
                String tempComputer = "t" + rand;
                ImageButton computerTile =
                        (ImageButton) findViewById(getResources().getIdentifier(tempComputer,
                                "id", getPackageName()));
                computerTile.setBackgroundColor(0x00000000);
                computerTile.setImageResource(Shared.Data.sharedComputer);
                tileStatus[rand] = "computer";
                available[rand] = 1;
                moves += 1;
                checkWinner();
            }
            gamesVal.setText(String.format("%d", Shared.Data.games));
            huWinsVal.setText(String.format("%.0f", Shared.Data.hWins));
            comWinsVal.setText(String.format("%.0f", Shared.Data.cWins));
            if (Shared.Data.hWins == 0){
                huWinsPct.setText("0%");
            }
            else{
                huWinsPct.setText(String.format("%.0f",((Shared.Data.hWins/Shared.Data.games)*100)) + "%");
            }
            if (Shared.Data.cWins == 0){
                comWinsPct.setText("0%");
            }
            else {
                comWinsPct.setText(String.format("%.0f",((Shared.Data.cWins/Shared.Data.games)*100)) + "%");
            }

        }
        else {
            message = "Cell already chosen. Choose another cell.";
            // Show message
            toast = Toast.makeText(getApplicationContext(),
                    message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.show();
        }
    }

    //----------------------------------------------------------------
    // checkWinner
    //----------------------------------------------------------------
    public void checkWinner() {
        // Test 8 possible ways to win
        if ((tileStatus[0] == tileStatus[1]) && (tileStatus[1] == tileStatus[2])
                && tileStatus[0] != null) {
            if (tileStatus[0] == "human") {
                Shared.Data.hWins += 1;
            }
            else {
                Shared.Data.cWins += 1;
            }
            gameOverMessage = tileStatus[0] + " WINS!";
            statusDisplay.setText((gameOverMessage.toUpperCase()));
            Shared.Data.games += 1;
            gameOver = true;
            return;
        }
        else if ((tileStatus[3] == tileStatus[4]) && (tileStatus[4] == tileStatus[5])
                && tileStatus[3] != null) {
            if (tileStatus[3] == "human") {
               Shared.Data.hWins += 1;
            }
            else {
                Shared.Data.cWins += 1;
            }
            gameOverMessage = tileStatus[3] + " WINS!";
            statusDisplay.setText((gameOverMessage.toUpperCase()));
            Shared.Data.games += 1;
            gameOver = true;
        }
        else if ((tileStatus[6] == tileStatus[7]) && (tileStatus[7] == tileStatus[8])
                && tileStatus[6] != null) {
            if (tileStatus[6] == "human") {
                Shared.Data.hWins += 1;
            }
            else {
                Shared.Data.cWins += 1;
            }
            gameOverMessage = tileStatus[6] + " WINS!";
            statusDisplay.setText((gameOverMessage.toUpperCase()));
            Shared.Data.games += 1;
            gameOver = true;
        }
        else if ((tileStatus[0] == tileStatus[3]) && (tileStatus[3] == tileStatus[6])
                && tileStatus[0] != null) {
            if (tileStatus[0] == "human") {
                Shared.Data.hWins += 1;
            }
            else {
                Shared.Data.cWins += 1;
            }
            gameOverMessage = tileStatus[0] + " WINS!";
            statusDisplay.setText((gameOverMessage.toUpperCase()));
            Shared.Data.games += 1;
            gameOver = true;
        }
        else if ((tileStatus[1] == tileStatus[4]) && (tileStatus[4] == tileStatus[7])
                && tileStatus[1] != null) {
            if (tileStatus[1] == "human") {
                Shared.Data.hWins += 1;
            }
            else {
                Shared.Data.cWins += 1;
            }
            gameOverMessage = tileStatus[1] + " WINS!";
            statusDisplay.setText((gameOverMessage.toUpperCase()));
            Shared.Data.games += 1;
            gameOver = true;
        }
        else if ((tileStatus[2] == tileStatus[5]) && (tileStatus[5] == tileStatus[8])
                && tileStatus[2] != null) {
            if (tileStatus[2] == "human") {
                Shared.Data.hWins += 1;
            }
            else {
                Shared.Data.cWins += 1;
            }
            gameOverMessage = tileStatus[2] + " WINS!";
            statusDisplay.setText((gameOverMessage.toUpperCase()));
            Shared.Data.games += 1;
            gameOver = true;
        }
        else if ((tileStatus[0] == tileStatus[4]) && (tileStatus[4] == tileStatus[8])
                && tileStatus[0] != null) {
            if (tileStatus[0] == "human") {
                Shared.Data.hWins += 1;
            }
            else {
                Shared.Data.cWins += 1;
            }
            gameOverMessage = tileStatus[0] + " WINS!";
            statusDisplay.setText((gameOverMessage.toUpperCase()));
            Shared.Data.games += 1;
            gameOver = true;
        }
        else if (((tileStatus[2] == tileStatus[4]) && (tileStatus[4] == tileStatus[6]))
                && tileStatus[2] != null) {
            if (tileStatus[2] == "human") {
                Shared.Data.hWins += 1;
            }
            else {
                Shared.Data.cWins += 1;
            }
            gameOverMessage = tileStatus[2] + " WINS!";
            statusDisplay.setText((gameOverMessage.toUpperCase()));
            Shared.Data.games += 1;
            gameOver = true;
        }
        else {
            if (moves < 9) {
                return;
            }
            else {
                gameOverMessage = "NOBODY WINS - TIE GAME!";
                statusDisplay.setText(gameOverMessage);
                Shared.Data.games += 1;
                gameOver = true;
            }
        }
    }
}