package principal;

import com.google.gson.Gson;

import eventos.Evento;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class GestorDeEventos {

    private static final Gson gson = new Gson();

    public static List<Evento> cargarEventos(String rutaCarpeta) {
        List<Evento> eventos = new ArrayList<>();

        File carpeta = new File(rutaCarpeta);
        File[] archivos = carpeta.listFiles((dir, name) -> name.endsWith(".json"));

        if (archivos == null) {
            System.err.println("No se pudo leer la carpeta: " + rutaCarpeta);
            return eventos;
        }

        for (File archivo : archivos) {
            try (FileReader reader = new FileReader(archivo)) {
                Evento evento = gson.fromJson(reader, Evento.class);
                eventos.add(evento);
            } catch (Exception e) {
                System.err.println("Error cargando: " + archivo.getName());
                e.printStackTrace();
            }
        }

        return eventos;
    }
    
    public static Evento cargarEvento(String path) {
        try (FileReader reader = new FileReader(path)) {
            return gson.fromJson(reader, Evento.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}