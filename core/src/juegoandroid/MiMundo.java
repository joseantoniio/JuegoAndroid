package juegoandroid;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import juegoandroid.managers.MainFixtureBodyManager;
import juegoandroid.managers.MiAssetsManager;
import juegoandroid.pantallas.PantallaJuego;

/**
 * Created by jose on 01/03/2015.
 */
public class MiMundo {

    public final static int ANCHO=600;
    public final static int ALTO=450;
    public static final float CONVERSOR=100f;

    private World mundo;
    private PantallaJuego pantallaJuego;
    private Box2DDebugRenderer mundoDebug;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer tiledMapRenderer;

    public MiMundo(PantallaJuego pantallaJuego){
        this.pantallaJuego = pantallaJuego;
        mundo=new World(new Vector2(0,-9.8f),true);
        mundo.setContactListener(new MiContactListener(pantallaJuego));
        mundoDebug=new Box2DDebugRenderer();
        tiledMap= MiAssetsManager.getManager().get("mapas/level1.tmx",TiledMap.class);
        tiledMapRenderer=new OrthogonalTiledMapRenderer(tiledMap);
        crearColisiones();
    }

    /**
     * Método para crear las colisiones del mapa
     */
    private void crearColisiones(){
        MapObjects mapObjects=tiledMap.getLayers().get(1).getObjects();
        for(MapObject mapObject:mapObjects){
            //Creamos los cuerpos con sus respectivas fixtures en el mundo y le asignamos su
            //definición correspondiente dependiendo que propiedades cumplan
            if(mapObject instanceof RectangleMapObject)
                if(mapObject.getProperties().containsKey("rebotable"))
                    MainFixtureBodyManager.colisionRectangulo(mundo,
                            ((RectangleMapObject) mapObject).getRectangle(), "Trampolin");
                else if(mapObject.getProperties().containsKey("arbol")) {
                    Body body = MainFixtureBodyManager.colisionRectangulo(mundo,
                            ((RectangleMapObject) mapObject).getRectangle(), "Arbol");
                    //Cogemos la única fixture que tiene el cuerpo después de pasar por el método anterior
                    //(por eso su posición es 0) para transformar los árboles en sensores
                    body.getFixtureList().get(0).setSensor(true);
                }else if(mapObject.getProperties().containsKey("hielo")) {
                    MainFixtureBodyManager.colisionRectangulo(mundo,
                            ((RectangleMapObject) mapObject).getRectangle(), "Hielo");
                }else if(mapObject.getProperties().containsKey("fin")) {
                    Body body = MainFixtureBodyManager.colisionRectangulo(mundo,
                            ((RectangleMapObject) mapObject).getRectangle(), "Puerta");
                    body.getFixtureList().get(0).setSensor(true);
                }else
                    MainFixtureBodyManager.colisionRectangulo(mundo,
                            ((RectangleMapObject) mapObject).getRectangle(), "RectanguloTerreno");
            else if(mapObject instanceof PolygonMapObject)
                MainFixtureBodyManager.colisionPoligono(mundo,
                        ((PolygonMapObject) mapObject).getPolygon(), "PoligonoTerreno");
        }
    }

    public void render(float delta){
        tiledMapRenderer.setView(pantallaJuego.getCamara());
        mundo.step(delta,8,6);
        tiledMapRenderer.render();
        mundoDebug.render(mundo, pantallaJuego.getCamara().combined.cpy().scl(CONVERSOR));
        borrarCuerposMarcados();
    }

    public void dispose(){
        tiledMapRenderer.dispose();
        mundoDebug.dispose();
        mundo.dispose();
    }

    private void borrarCuerposMarcados(){
        Array<Body> cuerpos=new Array();
        mundo.getBodies(cuerpos);
        for(Body body:cuerpos){
            if(body!=null){
                if(body.getUserData()=="DELETE"){
                    mundo.destroyBody(body);
                    body.setUserData(null);
                    body=null;
                }
            }
        }
    }

    public World getWorld(){return mundo;}
}
