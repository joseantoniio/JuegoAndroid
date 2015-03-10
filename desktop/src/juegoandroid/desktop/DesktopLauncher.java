package juegoandroid.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import juegoandroid.JuegoAndroid;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        System.out.println(config.width+"  "+config.height);
        new LwjglApplication(new JuegoAndroid(), config);
	}
}
