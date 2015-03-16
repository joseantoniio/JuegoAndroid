package juegoandroid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

import juegoandroid.entidades.Personaje;

/**
 * Created by jose on 02/03/2015.
 */
public class PersonajeInputAdapter extends InputAdapter {

    private Personaje personaje;

    public PersonajeInputAdapter(Personaje personaje){
        this.personaje=personaje;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        //Variable para testeo rápido
        boolean android=false;

        if(android) {
            if (screenX < Gdx.graphics.getWidth() / 2)
                personaje.saltar();
            else
                personaje.disparar();
            return true;
        }else{
            if(button==Input.Buttons.LEFT){
                personaje.saltar();
                return true;
            }
            if(button==Input.Buttons.RIGHT){
                personaje.disparar();
                return true;
            }
        }
        return false;
    }
}