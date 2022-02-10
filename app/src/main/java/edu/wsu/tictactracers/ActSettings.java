package edu.wsu.tictactracers;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class ActSettings extends AppCompatActivity {

    //----------------------------------------------------------------
    // Constants and variables
    //----------------------------------------------------------------

    // Declare constants
    public static final int MAX_COLOR_VALUE = 400;
    public static final int MID_COLOR_VALUE = 128;

    // Declare variables
    private SeekBar bgSeekBar;
    private SeekBar tileSeekBar;
    private int progress;
    private TextView bgSampleColor;
    private TextView tileSampleColor;
    private TextView statsVal;
    private String hexColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laysettings);

        // Define background color seek bar
        bgSeekBar = (SeekBar) findViewById(R.id.bgSeekBar);
        bgSeekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener()
                {
                    @Override
                    public void onProgressChanged(SeekBar seekBar,
                                                  int progress, boolean fromUser)
                    {
                        boolean v = false;
                        setControlsFromSeekBar(progress, v);
                    }
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar)
                    {}
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar)
                    {}
                });

        // Connect to sample color controls
        bgSampleColor = (TextView) findViewById(R.id.bgSampleColor);

        // Define tile color seek bar
        tileSeekBar = (SeekBar) findViewById(R.id.tileSeekBar);
        tileSeekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener()
                {
                    @Override
                    public void onProgressChanged(SeekBar seekBar,
                                                  int progress, boolean fromUser)
                    {
                        boolean v = true;
                        setControlsFromSeekBar(progress, v);
                    }
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar)
                    {}
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar)
                    {}
                });

        // Connect to sample color controls
        tileSampleColor = (TextView) findViewById(R.id.tileSampleColor);

        statsVal = (TextView) findViewById(R.id.statsVal);
        statsVal.setText(String.format("%d", Shared.Data.resets));
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
    }

    //----------------------------------------------------------------
    // finish
    //----------------------------------------------------------------
    @Override
    public void finish()
    {
        // Return to main screen
        super.finish();
    }

    //----------------------------------------------------------------
    // setControlsFromSeekBar
    //----------------------------------------------------------------
    public void setControlsFromSeekBar(int value, boolean v)
    {
        // Convert seek bar progress to hex color
        hexColor = String.format("#%06X", (0xFFFFFF & (value * 167772)));

        // Set sample background color
        if(v == false) {
            bgSampleColor.setBackgroundColor(Color.parseColor(hexColor));
            Shared.Data.sharedBGColor = hexColor;
        }
        else {
            tileSampleColor.setBackgroundColor(Color.parseColor(hexColor));
            Shared.Data.sharedTileColor = hexColor;
        }
    }

    //----------------------------------------------------------------
    // onRadioButtonClicked
    //----------------------------------------------------------------
    public void onRadioButtonClicked(View view)
    {
        // Get checked status
        boolean checked = ((RadioButton) view).isChecked();

        // Test which radio button changed
        switch(view.getId())
        {
            case R.id.imgs1:
                if (checked)
                    Shared.Data.sharedHuman = getResources().getIdentifier("neonx", "drawable", getPackageName());
                    Shared.Data.sharedComputer = getResources().getIdentifier("neono", "drawable", getPackageName());
                    break;
            case R.id.imgs2:
                if (checked)
                    Shared.Data.sharedHuman = getResources().getIdentifier("hearteyes", "drawable", getPackageName());
                    Shared.Data.sharedComputer = getResources().getIdentifier("dizzy", "drawable", getPackageName());
                    break;
            case R.id.imgs3:
                if (checked)
                    Shared.Data.sharedHuman = getResources().getIdentifier("baby_yoda", "drawable", getPackageName());
                    Shared.Data.sharedComputer = getResources().getIdentifier("baby_nut", "drawable", getPackageName());
                    break;
        }

    }

}
