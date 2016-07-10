package com.gmail.tylerfilla.androidgaf;

import java.util.ArrayList;
import java.util.List;

/**
 * A stage represents a collection of game pieces and control structures.
 */
public class Stage {

    /**
     * The list of game pieces currently on this stage.
     */
    private List<GamePiece> gamePieceList;

    /**
     * The list of control structures currently on this stage.
     */
    private List<ControlStructure> controlStructureList;

    public Stage() {
        gamePieceList = new ArrayList<>();
        controlStructureList = new ArrayList<>();
    }

    /**
     * Get the list of game pieces on this stage.
     *
     * @return The game piece list
     */
    public List<GamePiece> getGamePieceList() {
        return gamePieceList;
    }

    /**
     * Add a game piece to this stage.
     *
     * @param gamePiece The game piece to add
     */
    public void addGamePiece(GamePiece gamePiece) {
        gamePieceList.add(gamePiece);
    }

    /**
     * Remove a game piece from this stage.
     *
     * @param gamePiece The game piece to remove
     * @return Whether or not the removal was successful
     */
    public boolean removeGamePiece(GamePiece gamePiece) {
        return gamePieceList.remove(gamePiece);
    }

    /**
     * Get the list of control structures on this stage.
     *
     * @return The control structure list
     */
    public List<ControlStructure> getControlStructureList() {
        return controlStructureList;
    }

    /**
     * Add a control structure to this stage.
     *
     * @param controlStructure The control structure to add
     */
    public void addControlStructure(ControlStructure controlStructure) {
        controlStructureList.add(controlStructure);
    }

    /**
     * Remove a control structure from this stage.
     *
     * @param controlStructure The control structure to remove
     * @return Whether or not the removal was successful
     */
    public boolean removeControlStructure(ControlStructure controlStructure) {
        return controlStructureList.remove(controlStructure);
    }

    /**
     * Update the state of this stage. Do not call this method directly; it is called automatically
     * by the {@link Game} object (on the game loop thread) to which this stage belongs.
     */
    public void update() {

    }

}
