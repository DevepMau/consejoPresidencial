package principal;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import utilidades.Herramientas;

public class Raton implements MouseListener, MouseMotionListener {
	
	PanelDeJuego pdj;
	public int posX;
	public int posY;
	public boolean CLICK;
	private Cursor cursorNormal;
	private Cursor cursorClick;
	private BufferedImage manoCerrada;
	private BufferedImage manoApuntando;

	public Raton(PanelDeJuego pdj) {
		
		this.pdj = pdj;
		cargarImagenes();
		Point hotspot = new Point(0, 0);
        cursorNormal = Toolkit.getDefaultToolkit().createCustomCursor(manoApuntando, hotspot, "CursorNormal");
        cursorClick = Toolkit.getDefaultToolkit().createCustomCursor(manoCerrada, hotspot, "CursorClick");

	}
    
    @Override
    public void mouseClicked(MouseEvent e) {
        //CLICK = true;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        CLICK = true;
        pdj.setCursor(cursorClick);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        CLICK = false;
        pdj.setCursor(cursorNormal);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        pdj.setCursor(cursorNormal);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        pdj.setCursor(Cursor.getDefaultCursor());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        posX = e.getX();
        posY = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        pdj.setCursor(cursorClick);
    }
    
    public void cargarImagenes() {
    	try {
    		this.manoApuntando = configurarImagen("/cursores/mano_apuntando", 2);
    		this.manoCerrada = configurarImagen("/cursores/mano_cerrada", 2);
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    private BufferedImage configurarImagen(String rutaImagen, int escala) throws IOException {
        Herramientas uTool = new Herramientas();
        BufferedImage imagen = ImageIO.read(getClass().getResourceAsStream(rutaImagen + ".png"));
        return uTool.escalarImagen(imagen, imagen.getWidth() / 2 * escala, imagen.getHeight() / 2 * escala);
    }
}