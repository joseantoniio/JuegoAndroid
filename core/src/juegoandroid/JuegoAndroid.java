package juegoandroid;

import com.badlogic.gdx.Game;

import juegoandroid.managers.EntidadesManager;
import juegoandroid.managers.MiAssetsManager;
import juegoandroid.pantallas.PantallaPresentacion;

public class JuegoAndroid extends Game {

    private PantallaPresentacion pantallaPresentacion;

    @Override
    public void create() {
        MiAssetsManager.cargar();
        EntidadesManager.incializarEntidades();
        pantallaPresentacion=new PantallaPresentacion(this);
        setScreen(pantallaPresentacion);
    }

    @Override
    public void dispose(){
        super.dispose();
        MiAssetsManager.dispose();
        pantallaPresentacion.dispose();
    }
}