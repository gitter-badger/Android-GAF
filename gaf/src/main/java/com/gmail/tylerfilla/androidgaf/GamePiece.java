package com.gmail.tylerfilla.androidgaf;

/**
 * Representation of a game piece. Game pieces are the primary building blocks of a GAF-based game.
 * Game pieces are not inherently physical. For instance, a GamePiece implementation might represent
 * a pawn in a game of chess or an enemy player in a first-person shooter. Game pieces receive
 * regular updates from the {@link Stage} object to which they belong.
 */
public interface GamePiece {

    /**
     * Update the state of this game piece. Do not call this method directly; it is called
     * automatically by the {@link Stage} object (on the game loop thread) to which this game piece
     * belongs.
     */
    void update();

}
