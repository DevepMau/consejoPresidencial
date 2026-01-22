package utilidades;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileReader;

import com.google.gson.Gson;

import eventos.Evento;

public class Herramientas {
	
	private static final Gson gson = new Gson();

	public BufferedImage escalarImagen(BufferedImage original, int ancho, int alto) {

		BufferedImage imagenEscalada = new BufferedImage(ancho, alto, original.getType());
		Graphics2D g2 = imagenEscalada.createGraphics();
		g2.drawImage(original, 0, 0, ancho, alto, null);
		g2.dispose();

		return imagenEscalada;

	}
	
	public static Evento cargarJSonEvento(String path) {
		
        try (FileReader reader = new FileReader(path)) {
            return gson.fromJson(reader, Evento.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}