package juegoandroid.managers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

/**
 * Created by jose on 01/03/2015.
 */
public class MiAssetsManager{

    private static AssetManager manager;

    public static void cargar(){
        manager=new AssetManager();
        manager.setLoader(TiledMap.class,
                new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load("mapas/level1.tmx", TiledMap.class);
        manager.load("animaciones/narutoRun.png", Texture.class);
        manager.load("animaciones/narutoAirThrow.png",Texture.class);
        manager.load("animaciones/narutoWin.png",Texture.class);
        manager.load("animaciones/kunai.png",Texture.class);
        manager.load("gameover.png",Texture.class);
        manager.load("pantallaInicio.png",Texture.class);
        manager.load("sonidos/knife_slash1.mp3",Sound.class);
        manager.load("sonidos/jump_air.mp3",Sound.class);
        manager.load("sonidos/leap_whoosh.mp3",Sound.class);
        manager.finishLoading();
    }

    public static void dispose(){
        manager.dispose();
    }

    public static AssetManager getManager(){return manager;}
}
