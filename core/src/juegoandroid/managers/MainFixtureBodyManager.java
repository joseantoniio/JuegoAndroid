package juegoandroid.managers;

import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.math.Rectangle;

import juegoandroid.MiMundo;


/**
 * Created by jose on 01/03/2015.
 */
public class MainFixtureBodyManager {


    /**
     * Con este método creamos un cuerpo en el mundo cuyo tamaño y forma es el rectángulo pasado
     * @param mundo World
     * @param rectangle Rectángulo para calcular tamaño
     * @param definicion Una definición para poder saber que entidad es el cuerpo
     * @return Body
     */
    public static Body colisionRectangulo(World mundo,Rectangle rectangle,String definicion){
        //Creamos el cuerpo, lo definimos estático por defecto y lo posicionamos en el mundo
        BodyDef bodyDef=new BodyDef();
        bodyDef.position.set(rectangle.getX()/ MiMundo.CONVERSOR,rectangle.getY()/MiMundo.CONVERSOR);
        bodyDef.type= BodyDef.BodyType.StaticBody;

        //Creamos la forma que va a tener, es decir un cuadrado cuyos vértices serán los del
        //rectángulo, convertidos a tamaño Box2D
        PolygonShape shape=new PolygonShape();
        shape.set(new Vector2[]{
                new Vector2(0,0),
                new Vector2(rectangle.getWidth()/MiMundo.CONVERSOR,0),
                new Vector2(rectangle.getWidth()/MiMundo.CONVERSOR,rectangle.getHeight()/MiMundo.CONVERSOR),
                new Vector2(0,rectangle.getHeight()/MiMundo.CONVERSOR)
        });

        FixtureDef fixtureDef=new FixtureDef();
        fixtureDef.shape=shape;

        //Creamos el cuerpo dentro del mundo, y le añadimos la definición a la fixture para poder
        //identificarla
        Body body=mundo.createBody(bodyDef);
        body.createFixture(fixtureDef).setUserData(definicion);
        shape.dispose();
        return body;
    }

    /**
     * Con este método creamos un cuerpo en el mundo cuyo tamaño y forma es el polígono pasado
     * @param mundo World
     * @param polygon Pligono para calcular tamaño
     * @param definicion Una definición para poder saber que entidad es el cuerpo
     * @return Body
     */
    public static Body colisionPoligono(World mundo,Polygon polygon,String definicion){
        BodyDef bodyDef=new BodyDef();
        bodyDef.position.set(polygon.getX()/MiMundo.CONVERSOR,polygon.getY()/MiMundo.CONVERSOR);
        bodyDef.type= BodyDef.BodyType.StaticBody;

        PolygonShape shape=new PolygonShape();
        //Transformamos los vértices a coordenadas del mundo Box2D usando el conversor
        float[] vertices=polygon.getVertices();
        float[] verticesEscalados=new float[vertices.length];
        for(int i=0;i<vertices.length;i++)
            verticesEscalados[i]=vertices[i]/MiMundo.CONVERSOR;
        shape.set(verticesEscalados);

        FixtureDef fixtureDef=new FixtureDef();
        fixtureDef.shape=shape;

        Body body=mundo.createBody(bodyDef);
        body.createFixture(fixtureDef).setUserData(definicion);
        shape.dispose();
        return body;
    }
}
