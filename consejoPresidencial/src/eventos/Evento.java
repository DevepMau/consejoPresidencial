package eventos;

import java.util.List;
import java.util.Map;

public class Evento {

	public String id;
    public String titulo;
    public String tipo;
    public String habla;

    // evento rápido
    public String descripcion;
    public List<Opcion> opciones;

    // historia
    public List<Escena> escenas;
    public Map<String, Integer> condiciones;
    
}