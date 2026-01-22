package principal;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;

import javax.swing.JPanel;

import eventos.Dialogo;
import eventos.Escena;
import eventos.Evento;
import eventos.Opcion;
import objetos.NPC;
import utilidades.Herramientas;

public class PanelDeJuego extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;
	
	// CONFIGURACIÓN DE PANTALLA
	final int tamañoOriginalDeBaldosa = 16;
	final int escala = 3;

	public final int tamañoDeBaldosa = tamañoOriginalDeBaldosa * escala;
	public final int maxColDePantalla = 20;
	public final int maxFilaDePantalla = 12;
	public final int anchoDePantalla = tamañoDeBaldosa * maxColDePantalla;
	public final int altoDePantalla = tamañoDeBaldosa * maxFilaDePantalla;
	
	//CONFIGURACION DEL MUNDO
	public final int maxColDeMundo = 16;
	public final int maxFilaDeMundo = 12;
	public final int anchoMundo = tamañoDeBaldosa * maxColDeMundo;
	public final int altoMundo = tamañoDeBaldosa * maxFilaDeMundo;

	//SISTEMA
	public Teclado teclado = new Teclado(this);
	Raton raton = new Raton(this);
	Sonido musica = new Sonido();
	Sonido se = new Sonido();
	Thread hiloDeJuego;
	UI ui = new UI(this);
	
	//VARIABLES DE JUEGO
	public final Point POSICION_CENTRO = new Point(this.anchoDePantalla/2 - 150, this.altoDePantalla - 400);
	public final Point POSICION_IZQUIERDA = new Point(this.anchoDePantalla/2 - 350,this.altoDePantalla - 400);
	public final Point POSICION_DERECHA = new Point(this.anchoDePantalla/2 +50,this.altoDePantalla - 400);
	
	//ENTIDADES Y OBJETOS
	public Rectangle ventanaDialogo;
 	public Rectangle ventanaNombre;
 	public Rectangle ventanaTitulo;
 	public Rectangle ventanaOpciones[];
 	
	NPC toto = new NPC("Toto-chan", "Ministra de Economia", this);
	
	//EVENTOS
	public Evento evento;
	public List<Escena> escenas;
	public List<Dialogo> dialogos;
	public List<Opcion> opciones;
	public int indiceEscena = -1;
	public int indiceDialogo = 0;
	public boolean habilitarRaton = true; 


	//ESTADO DE JUEGO
	public int modoActual;
	
	public final int MODO_TITULO = 0;
	public final int MODO_JUEGO = 1;
	public final int MODO_PAUSA = 2;
	public final int MODO_DIALOGO = 3;
	
	// FPS
	int FPS = 60;

	public PanelDeJuego() {

		this.setPreferredSize(new Dimension(anchoDePantalla, altoDePantalla));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(teclado);
		this.setFocusable(true);
		this.addMouseListener(raton);
	    this.addMouseMotionListener(raton);

	}
	
	public void configuracionDeJuego() {
		setearVentanas();
		toto.cargarImagenes("/cabezas/toto_cabeza", "/cuerpos/toto_cuerpo");
		modoActual = MODO_DIALOGO;

	}

	public void iniciarHiloDeJuego() {
		hiloDeJuego = new Thread(this);
		hiloDeJuego.start();

	}
	
	public void reproducirMusica(int i) {

		musica.cargarArchivo(i);
		musica.reproducir();
		musica.repetir();

	}

	public void detenerMusica() {

		musica.detener();

	}

	public void ReproducirSE(int i) {

		se.cargarArchivo(i);
		se.reproducir();

	}

	@Override
	public void run() {

		double intervaloDeDibujo = 1000000000 / FPS;
		double delta = 0;
		long ultimoTiempo = System.nanoTime();
		long tiempoActual;

		while(hiloDeJuego != null) {

			tiempoActual = System.nanoTime();
			delta += (tiempoActual - ultimoTiempo) / intervaloDeDibujo;
			ultimoTiempo = tiempoActual;

			if(delta >= 1) {
				actualizar();
				repaint();
				delta--;
			}
		}

	}

	public void actualizar() {

		if(modoActual == MODO_TITULO) {
		}
		if(modoActual == MODO_JUEGO) {	
			toto.actualizar();
		}
		if(modoActual == MODO_PAUSA) {
		}
		if(modoActual == MODO_DIALOGO) {
			iniciarEvento("toto_sample_01");
			
			if(!raton.CLICK) {
				
				habilitarRaton = true;
			}
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

	    Graphics2D g2 = (Graphics2D) g;

	    // Tamaño real del panel
	    int anchoReal = getWidth();
	    int altoReal = getHeight();

	    // Escala dinámica
	    double escalaX = (double) anchoReal / anchoDePantalla;
	    double escalaY = (double) altoReal / altoDePantalla;

	    // Mantener proporción (opcional pero recomendado)
	    double escala = Math.min(escalaX, escalaY);

	    int offsetX = (int) ((anchoReal - anchoDePantalla * escala) / 2);
	    int offsetY = (int) ((altoReal - altoDePantalla * escala) / 2);

	    g2.translate(offsetX, offsetY);

	    g2.scale(escala, escala);
		
	    //DIBUJAR A PARTIR DE AQUI
	    g2.setColor(Color.white);
	    g2.drawRect(0, 0, this.anchoDePantalla, this.altoDePantalla);
		
		//DEBUG
		long drawStart = 0;
		if(teclado.comprobarTiempoDeDibujado == true) {
			drawStart = System.nanoTime();
		}
		ui.dibujar(g2);
		//TITULO
		if(modoActual == MODO_TITULO) {
		}
		//JUEGO
		if(modoActual == MODO_JUEGO) {
			toto.dibujar(g2);
		}
		//OTROS
		else {
			
		}

		//DEBUG
		if(teclado.comprobarTiempoDeDibujado == true) {
			long drawEnd = System.nanoTime();
			long passed = drawEnd - drawStart;
			g2.setColor(Color.white);
			g2.drawString("Draw Time: " + passed, 10, 400);
		}

		g2.dispose();

	}
	
	//METODOS VARIOS
	
	public void iniciarEvento(String nombreEvento) {
		this.evento = Herramientas.cargarJSonEvento(
	            "data/eventos/"+nombreEvento+".json"
	        );
		
		if(evento != null) {
			
			if(indiceEscena == -1) {
				
				ui.setEmisorDialogo("");
				ui.setTituloDialogo(evento.titulo);
				ui.setContenidoDialogo(evento.descripcion);
				indiceEscena++;
			}
			if(raton.CLICK && habilitarRaton && indiceEscena >= 0) {
				
				habilitarRaton = false;
				ui.resetDialogo();
				if(indiceEscena < evento.escenas.size()) {
					
					if(indiceDialogo < evento.escenas.get(indiceEscena).dialogos.size()) {
						
						ui.setEmisorDialogo(evento.escenas.get(indiceEscena).dialogos.get(indiceDialogo).habla);
						ui.setContenidoDialogo(evento.escenas.get(indiceEscena).dialogos.get(indiceDialogo).texto);
						
						opciones = evento.escenas.get(indiceEscena).dialogos.get(indiceDialogo).opciones;
						if(opciones != null) {
							System.out.println("hay opciones");
						}
						indiceDialogo++;
					}
					if(indiceDialogo >= evento.escenas.get(indiceEscena).dialogos.size()) {
						
						indiceEscena++;
						indiceDialogo = 0;
						opciones = null;
					}
				}
				else {
					modoActual = MODO_JUEGO;
					evento = null;
				}	
			}
		}
	}
	
	public void setearVentanas() {
		int unidad = this.tamañoDeBaldosa;
		int separacion = 56;
		
		this.ventanaDialogo = new Rectangle(unidad*2, unidad*9, unidad*16, unidad*3);
	 	this.ventanaNombre = new Rectangle(unidad*2, unidad*8, unidad*3, unidad);
	 	this.ventanaTitulo = new Rectangle(unidad*12, unidad*8, unidad*6, unidad);
	 	this.ventanaOpciones = new Rectangle[3];
		this.ventanaOpciones[0] = new Rectangle(unidad*5, unidad*3, unidad*10, unidad);
		this.ventanaOpciones[1] = new Rectangle(unidad*5, this.ventanaOpciones[0].y + separacion, unidad*10, unidad);
		this.ventanaOpciones[2] = new Rectangle(unidad*5, this.ventanaOpciones[1].y + separacion, unidad*10, unidad);
	}
}
