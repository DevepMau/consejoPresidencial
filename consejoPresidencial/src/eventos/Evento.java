package eventos;

import java.util.List;
import java.util.Map;

public class Evento {

	public String id;
    public String titulo;
    public String tipo;
    public String descripcion;
    
    public List<Escena> escenas;
    public Map<String, Integer> condiciones;
    
}