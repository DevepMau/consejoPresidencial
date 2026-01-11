package objetos;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import principal.PanelDeJuego;
import utilidades.Herramientas;

public class NPC {
	
	private final int DISTANCIA_MOVIMIENTO = 10;
	
	PanelDeJuego pdj;
	private BufferedImage cabeza;
	private BufferedImage cuerpo;
	private String[] dialogos;
	private int posX;
	private int posY;
	//VARIABLES DE ESTADISTICAS.
	private String nombre;
	private String puesto;
	private int animo;
	private int afecto;
	//VARIABES DE MOVIMIENTO.
	private int desplazamiento;
	private int movCont;
	private int movDir; //direccion del movimiento: 1 para abajo o -1 para arriba.
	private int movVel;
	
	public NPC(String nombre, String puesto, PanelDeJuego pdj) {
		this.pdj = pdj;
		
		this.nombre = nombre;
		this.puesto = puesto;
		this.animo = 50;
		this.afecto = 0;
		
		this.desplazamiento = 0;
		this.movCont = 0;
		this.movDir = -1;
		this.movVel = 1;
	}
	
	public void actualizar() {
		if(pdj.teclado.A) {
			this.posX = pdj.POSICION_IZQUIERDA.x;
			this.posY = pdj.POSICION_IZQUIERDA.y;
		}
		if(pdj.teclado.D) {
			this.posX = pdj.POSICION_DERECHA.x;
			this.posY = pdj.POSICION_DERECHA.y;
		}
		if(pdj.teclado.W) {
			this.posX = pdj.POSICION_CENTRO.x;
			this.posY = pdj.POSICION_CENTRO.y;
		}
		oscilarImagen();
	}
	
	public void dibujar(Graphics2D g2) {
		g2.drawImage(cuerpo, posX, posY - desplazamiento/2, null);
		g2.drawImage(cabeza, posX, posY - desplazamiento, null);
	}
	
	public void cargarImagenes(String pathCabeza, String pathCuerpo) {
		try {
			this.cabeza = configurarImagen(pathCabeza, 2);
			this.cuerpo = configurarImagen(pathCuerpo, 2);
		} catch(IOException e) {
			e.printStackTrace();
		}
		resetPosicionImagenes();
	}
	
	//METODOS PRIVADOS//////////////////////////////////////////////////////////////////////
	
	private void resetPosicionImagenes() {
		this.desplazamiento = 0;
		this.posX = pdj.anchoDePantalla/2 - cuerpo.getWidth()/2;
		this.posY = pdj.altoDePantalla - cuerpo.getHeight();
	}
	
	private void oscilarImagen() {
		if(movCont > 0) {
			//if( movCont%movVel == 0) {
				desplazamiento = desplazamiento -(movDir) * movVel;
			//}
			movCont = movCont - (movVel);
		}
		else {
			movCont = DISTANCIA_MOVIMIENTO;
			movDir = -(movDir);
			
		}
	}
	
	private BufferedImage configurarImagen(String rutaImagen, int escala) throws IOException {
        Herramientas uTool = new Herramientas();
        BufferedImage imagen = ImageIO.read(getClass().getResourceAsStream(rutaImagen + ".png"));
        return uTool.escalarImagen(imagen, imagen.getWidth() / 2 * escala, imagen.getHeight() / 2 * escala);
    }
	
	//GETTERS & SETTERS//////////////////////////////////////////////////////////////////
	
	public BufferedImage getRetrato() {
		return cuerpo;
	}

	public void setRetrato(BufferedImage retrato) {
		this.cuerpo = retrato;
	}

	public String[] getDialogos() {
		return dialogos;
	}

	public void setDialogos(String[] dialogos) {
		this.dialogos = dialogos;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPuesto() {
		return puesto;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	public int getAnimo() {
		return animo;
	}

	public void setAnimo(int animo) {
		this.animo = animo;
	}

	public int getAfecto() {
		return afecto;
	}

	public void setAfecto(int afecto) {
		this.afecto = afecto;
	}

	public BufferedImage getCabeza() {
		return cabeza;
	}

	public void setCabeza(BufferedImage cabeza) {
		this.cabeza = cabeza;
	}

}
