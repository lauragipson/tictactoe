//====================================================================
//
// Application: Tic Tac Tracers
// Class:       Shared
// Description:
//   This Android application class holds data shared among activities.
//
//====================================================================
package edu.wsu.tictactracers;

//--------------------------------------------------------------------
// enum Shared
//--------------------------------------------------------------------
public enum Shared {
    // Define enum value
    Data;

    // Declare enum fields
    public String sharedBGColor;
    public String sharedTileColor;
    public int sharedHuman;
    public int sharedComputer;
    public int resets;
    public int games;
    public float hWins;
    public float cWins;
}

