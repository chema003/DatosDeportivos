
package com.acing.serial;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.acing.eventos.Partido;
import com.acing.eventos.Suceso;

import es.lanyu.commons.config.Propiedades;

public class ConectorBD {

	Propiedades configuracionBD = new Propiedades("datos/BD.properties");
	String url = "jdbc:postgresql://"
    		+ configuracionBD.leerPropiedad("url") + ":5432/"
    		+ configuracionBD.leerPropiedad("username");
    String username = (String) configuracionBD.leerPropiedad("username");
    String password = (String) configuracionBD.leerPropiedad("password");
    
    static Map<String, String>idsPorNombre = new HashMap<>();
    static {
	    Propiedades propiedades = new Propiedades("datos/mapaCSV.properties");
	    propiedades.forEach((k, v) -> idsPorNombre.put((String)v, (String)k));
    }
    
    public void cargarPartido(Collection<Partido> partidos) {
    	
    	try (Connection conn = DriverManager.getConnection(url, username, password)){
            PreparedStatement psEventos = conn.prepareStatement(
                    "INSERT INTO Eventos (fecha) VALUES (?)", PreparedStatement.RETURN_GENERATED_KEYS);//Necesito leer ids
            PreparedStatement psPartidos = conn.prepareStatement(
                    "INSERT INTO Partidos (evento_id, local_id, visitante_id) VALUES (?, ?, ?)");
            PreparedStatement psSucesos = conn.prepareStatement(
                    "INSERT INTO Sucesos (evento_id, participante_id, tipo) VALUES (?, ?, ?)");
            psSucesos.setString(3, "gol");//Se van a cargar goles
            
            conn.setAutoCommit(false);
            
            for(Partido partido : partidos) {
	            //Cargo Evento
	            psEventos.setDate(1, new java.sql.Date(partido.getFecha().getTime()));
	            int filasAfectadas = psEventos.executeUpdate();
	            ResultSet rsEvento = psEventos.getGeneratedKeys();
	            System.out.println(filasAfectadas + " | " + rsEvento.next());//No estoy asegurando nada, habría que usar asegurar o lanzar excepcion
	            int idEvento = rsEvento.getInt(1);//Leer id generado
	            
	            //Cargo Partido
	            psPartidos.setInt(1, idEvento);
	            psPartidos.setString(2, idsPorNombre.get(partido.local.getNombre()));
	            psPartidos.setString(3, idsPorNombre.get(partido.visitante.getNombre()));
	            psPartidos.executeUpdate();
	            
	            //Cargo goles
	            for(Suceso g : partido.getSucesos()) {
	                psSucesos.setInt(1, idEvento);
	                psSucesos.setString(2, idsPorNombre.get(g.getParticipante().getNombre()));
	                psSucesos.setString(3, "gol");
	                psSucesos.executeUpdate();
	            }
            }
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
