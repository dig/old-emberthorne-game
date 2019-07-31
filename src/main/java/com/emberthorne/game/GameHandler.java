package com.emberthorne.game;

public interface GameHandler {
	
    String getName();

    void onEnable();

    interface MiniHandler{
        void onCall();

        void onStop();
    }
}
