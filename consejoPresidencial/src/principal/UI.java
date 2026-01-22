package principal;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import eventos.Opcion;

public class UI {
	
	PanelDeJuego pdj;
	Graphics2D g2;
	Font maruMonica;
	
	public float escala;
	public int anchoDeLinea = 4;
	public int contLetras = 0;
	public String textoMostrado = "";
	
	public Color blancoLinea  = new Color(255, 255, 255);
 	public Color azulMecanico  = new Color(72, 82, 98);
 	public Color negroTransparente = new Color(0, 0, 0, 205);
 	
 	//DIALOGO
 	private String tituloDialogo = "";
 	private String emisorDialogo = "";
 	private String contenidoDialogo = "";
 	private boolean dialogoTerminado = false;
 	
	public UI(PanelDeJuego pdj) {
		
		this.pdj = pdj;
		
		try {
 			InputStream is = getClass().getResourceAsStream("/fuentes/MaruMonica.ttf");
 			maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
 		} catch (FontFormatException e) {
 			e.printStackTrace();
 		} catch (IOException e) {
 			e.printStackTrace();
 		}
		
	}
	
	public void dibujar(Graphics2D g2) {
		this.g2 = g2;
		
		this.escala = Math.min(
		        (float) pdj.getWidth() / pdj.anchoDePantalla,
		        (float) pdj.getHeight() / pdj.altoDePantalla
		    );
		
		escala = Math.min(escala, 1.10f);
		
		g2.setFont(maruMonica);
		g2.setColor(Color.white);
 		g2.setFont(g2.getFont().deriveFont(Font.BOLD, (20f*escala)));
 		
 		switch(pdj.modoActual) {
 			case 3 -> dibujarPantallaDialogo();
 		}
		
	}
	
	public void dibujarPantallaDialogo() {
		dibujarSubVentana(pdj.ventanaNombre);
		dibujarSubVentana(pdj.ventanaTitulo);
		dibujarSubVentana(pdj.ventanaDialogo);
		
		int ajusteX = (int)(8 * escala);
		int ajusteY = (int)(24 * escala);
		int alturaY = 0;
		FontMetrics fm = g2.getFontMetrics();
		int alturaLinea = fm.getHeight();
		
		g2.setColor(blancoLinea);
		g2.drawString(tituloDialogo, pdj.ventanaTitulo.x + ajusteX, pdj.ventanaTitulo.y + ajusteY);
		g2.drawString(emisorDialogo, pdj.ventanaNombre.x + ajusteX, pdj.ventanaNombre.y + ajusteY);
		
		//Texto de dialogo
		if(contenidoDialogo != null) {
			if(contLetras < contenidoDialogo.length()) {
				textoMostrado += contenidoDialogo.charAt(contLetras);
				
				if(contLetras%84 == 0 && contLetras != 0) {
					textoMostrado += "\n";
				}
				contLetras++;
				dialogoTerminado = false;
			}
			else {
				dialogoTerminado = true;
			}
		}
		for(String line : textoMostrado.split("\n")) {
			g2.drawString(line, pdj.ventanaDialogo.x + ajusteX, pdj.ventanaDialogo.y + ajusteY + alturaY);
			alturaY += alturaLinea;
		}
		
		if(dialogoTerminado && pdj.opciones != null) {
			mostrarOpciones(pdj.opciones);
		}
		
	}
	
	public void mostrarOpciones(List<Opcion> opciones) {
		int ajusteX = (int) (8 * escala);
		int ajusteY = (int) (24 * escala);
		//FontMetrics fm = g2.getFontMetrics();
		//int alturaLinea = fm.getHeight();
		//int alturaY = 0;
		int indiceOpcion = 0;
		
		for (Opcion opcion : opciones) {
			dibujarSubVentana(pdj.ventanaOpciones[indiceOpcion]);
			g2.setColor(blancoLinea);
			g2.drawString(opcion.texto, pdj.ventanaOpciones[indiceOpcion].x + ajusteX, pdj.ventanaOpciones[indiceOpcion].y + ajusteY);
			//alturaY += alturaLinea;
			indiceOpcion++;
		}
	}
	
	public void resetDialogo() {
		this.contenidoDialogo = null;
		this.textoMostrado = "";
		this.contLetras = 0;
		this.dialogoTerminado = false;
	}
	
	private void dibujarSubVentana(Rectangle ventana) {
		 
 		g2.setColor(negroTransparente);
 		g2.fillRoundRect(ventana.x, ventana.y, ventana.width, ventana.height, 2, 2);
 		
 		g2.setColor(azulMecanico);
 		g2.setStroke(new BasicStroke(anchoDeLinea));
 		g2.drawRoundRect(ventana.x, ventana.y, ventana.width-10, ventana.height-10, 2, 2);
 		
 		g2.setStroke(new BasicStroke());
 		g2.setColor(blancoLinea);
 
 	}

	public String getTituloDialogo() {
		return tituloDialogo;
	}

	public void setTituloDialogo(String tituloDialogo) {
		this.tituloDialogo = tituloDialogo;
	}

	public String getEmisorDialogo() {
		return emisorDialogo;
	}

	public void setEmisorDialogo(String emisorDialogo) {
		this.emisorDialogo = emisorDialogo;
	}

	public String getContenidoDialogo() {
		return contenidoDialogo;
	}

	public void setContenidoDialogo(String contenidoDialogo) {
		this.contenidoDialogo = contenidoDialogo;
	}

	public boolean isDialogoTerminado() {
		return dialogoTerminado;
	}

	public void setDialogoTerminado(boolean dialogoTerminado) {
		this.dialogoTerminado = dialogoTerminado;
	}

}
