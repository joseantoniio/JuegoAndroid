package juegoandroid.managers;

import com.badlogic.gdx.utils.Array;

import juegoandroid.entidades.MiEntidad;

/**
 * Created by jose on 03/03/2015.
 */
public class EntidadesManager {
    //Esta clase se encargará de manejar las entidades del mundo

    private static Array<MiEntidad> entidades;

    public static void incializarEntidades(){
        entidades=new Array();
    }

    public static void anhadirEntidad(MiEntidad entidad){
        entidades.add(entidad);
    }

    public static void render(float delta){
        for(MiEntidad entidad:entidades)
            entidad.render(delta);
    }

    public static void eliminarEntidad(MiEntidad entidad){
        entidad.getCuerpo().setUserData("DELETE");
        entidades.removeValue(entidad,true);
    }

    public static void clear(){
        entidades.clear();
    }

    /*
    public static void reiniciarEntidades(Class clase){
        Iterator iterator=entidades.iterator();
        while(iterator.hasNext()){
            MiEntidad entidad= (MiEntidad) iterator.next();
            if(entidad.getClass().equals(clase)) {
                entidad.getCuerpo().setUserData("DELETE");
                iterator.remove();
            }
        }
    }*/
}
