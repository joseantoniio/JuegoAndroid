package juegoandroid;

import com.badlogic.gdx.Game;

public class JuegoAndroid extends Game {

    @Override
    public void create() {
        setScreen(new PantallaJuego());
    }
}