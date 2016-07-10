package com.gmail.tylerfilla.androidgaf;

import java.util.ArrayList;
import java.util.List;

/**
 * A game piece group is a special type of game piece that, itself, further contains game pieces.
 * Depending on the game, this might be used to build complex structures of dependent parts,
 * offering a hierarchy solution to your game logic. Like in {@link Stage}s, child game pieces are
 * ordered, but no physical properties (e.g. relative position in a world) are maintained. Game loop
 * updates are passed through to all child game pieces in an undefined order.
 */
public class GamePieceGroup implements GamePiece {

    private List<GamePiece> gamePieceList;

    public GamePieceGroup() {
        gamePieceList = new ArrayList<>();
    }

    /**
     * Get the list of game pieces in this group.
     *
     * @return The game piece list
     */
    public List<GamePiece> getGamePieceList() {
        return gamePieceList;
    }

    /**
     * Add a game piece to this group.
     *
     * @param gamePiece The game piece to add
     */
    public void addGamePiece(GamePiece gamePiece) {
        gamePieceList.add(gamePiece);
    }

    /**
     * Remove a game piece from this group.
     *
     * @param gamePiece The game piece to remove
     * @return Whether or not the removal was successful
     */
    public boolean removeGamePiece(GamePiece gamePiece) {
        return gamePieceList.remove(gamePiece);
    }

    @Override
    public void update() {
        // Update all child game pieces
        for (GamePiece gamePiece : gamePieceList) {
            gamePiece.update();
        }
    }

}
