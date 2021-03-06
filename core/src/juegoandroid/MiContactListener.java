package juegoandroid;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import juegoandroid.entidades.Kunai;
import juegoandroid.managers.EntidadesManager;
import juegoandroid.managers.MiAssetsManager;
import juegoandroid.pantallas.PantallaJuego;

/**
 * Created by jose on 01/03/2015.
 */
public class MiContactListener implements ContactListener {

    private PantallaJuego pantallaJuego;

    public MiContactListener(PantallaJuego pantallaJuego){
        this.pantallaJuego = pantallaJuego;
    }

    @Override
    public void beginContact(Contact contact) {

        //Fixture a es la que "es golpeada"
        //Fixture b la que "golpea"
        Fixture a=contact.getFixtureA();
        Fixture b=contact.getFixtureB();

        System.out.println(b.getUserData()+"  "+a.getUserData());

        //Si el personaje choca contra un muro su velocidad es 0 y cae al vacío
        //(Usamos el sensor para colisión lateral)
        if(b.getUserData()=="Lateral" &&
                (a.getUserData()=="RectanguloTerreno" || a.getUserData()=="PoligonoTerreno")){
            pantallaJuego.getPersonaje().setVelocidad(0);
        }

        //Si los pies entran en contacto con un trampolin se le aplica un impulso hacia arriba
        //(Sensor pies)
        if( b.getUserData()=="Pies" && a.getUserData()=="Trampolin"){
            MiAssetsManager.getManager().get("sonidos/leap_whoosh.mp3", Sound.class).play();
            pantallaJuego.getPersonaje().aplicarImpulso(new Vector2(0, 9.5f));
        }

        if( b.getUserData()=="Pies" && a.getUserData()=="Hielo"){
            pantallaJuego.getPersonaje().setVelocidad(5);
            Kunai.setVelocidad(10);
        }

        //Si los pies entran en contacto con algo, está apoyado
        if(b.getUserData()=="Pies" && a.getUserData()!="Arbol")
            pantallaJuego.getPersonaje().setApoyado(true);
        if(a.getUserData()=="Pies" && b.getUserData() instanceof Kunai)
            pantallaJuego.getPersonaje().setApoyado(true);

        //Si el personaje choca contra un kunai estático(clavado en un árbol),pierde su velocidad
        if(a.getUserData()=="Lateral" &&
                b.getUserData()=="CuerpoKunai" && b.getBody().getType()== BodyDef.BodyType.StaticBody)
            pantallaJuego.getPersonaje().setVelocidad(0);

        //El objeto kunai viene referenciado por el contacto de la punta del kunai
        if(b.getUserData() instanceof Kunai)
            if(a.getUserData()=="Arbol")
                //Cuando la punta toca un árbol transformamos el cuerpo en estático
                ((Kunai)b.getUserData()).isStaticBody(true);
            else if(a.getUserData()=="RectanguloTerreno"
                    || a.getUserData()=="PoligonoTerreno"
                    || a.getUserData()=="CuerpoKunai")
                //Si toca otra cosa lo eliminamos
                EntidadesManager.eliminarEntidad((Kunai) b.getUserData());

        //Esta colisión se produce cuando dos kunais se chocan entre si(Debido a que uno se clava
        // en un árbol y otro viene justo detrás)
        if(a.getUserData() instanceof Kunai && b.getUserData()=="CuerpoKunai")
            EntidadesManager.eliminarEntidad((Kunai) a.getUserData());

        if(b.getUserData()=="Personaje" && a.getUserData()=="Puerta")
            pantallaJuego.getPersonaje().ganar();

    }

    @Override
    public void endContact(Contact contact) {
        Fixture a=contact.getFixtureA();
        Fixture b=contact.getFixtureB();

        //Se vuelve a establecer su velocidad al no estar en contacto lateral con ningún obstáculo
        //del terreno
        if((a.getUserData()=="RectanguloTerreno" || a.getUserData()=="PoligonoTerreno")
                && b.getUserData()=="Lateral"){
            pantallaJuego.getPersonaje().setVelocidad(2.5f);
        }

        //Cuando los pies dejan de estar en contacto no está apoyado
        if(a.getUserData()=="Pies" || b.getUserData()=="Pies")
            pantallaJuego.getPersonaje().setApoyado(false);
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
