package com.gmail.tylerfilla.androidgaf;

/**
 * Representation of a control structure. Control structures are user interface entities that exist
 * within the context of the game, but offer no consequential effect to the gameplay. For instance,
 * a control structure might indicate focus of a particular game piece under user control. Control
 * structures are abstract and do not inherently feature graphic or auditory implementation. It is
 * not necessary to utilize control structures, depending on the game.
 */
public interface ControlStructure {

    /**
     * Update the state of this control structure. Do not call this method directly; it is called
     * automatically by the {@link Stage} object (on the game loop thread) to which this control
     * structure belongs.
     */
    void update();

}
