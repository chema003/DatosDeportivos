package com.acing.app;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;

import javax.swing.JFrame;

import com.acing.eventos.Partido;
import com.acing.iu.EjemploJFrame;
import com.acing.eventos.Evento;
import com.acing.eventos.EventoDAO;
import com.acing.eventos.Participante;
import com.acing.serial.SerializadorCSV;
import com.acing.serial.SerializadorJson;
import com.esotericsoftware.jsonbeans.Json;
import com.esotericsoftware.jsonbeans.OutputType;

public class App {
	EventoDAO eventoDAO;

	public static void main(String[] args) {
		System.out.println("FUNCIONA!!!");
		
		Collection<? extends Evento> eventos;
		String rutaCsv = "datos/SP1.csv";
		String rutaJson = "datos/eventos.json";
		
		App app = new App();
		
		// LEER DESDE EL CSV
		app.eventoDAO = new SerializadorCSV(rutaCsv);
		eventos = app.eventoDAO.getEventos();
		
		// CONVERTIR eventos a JSON
		app.eventoDAO = new SerializadorJson(rutaJson);
		app.eventoDAO.guardarEventos(eventos.toArray(new Partido[] {}));
		eventos = app.eventoDAO.getEventos();
		
		Collection<Participante> participantes = eventos.stream()
				.map(e -> ((Partido)e).local)
				.distinct()
				.sorted((p1, p2) -> p1.getNombre().compareTo(p2.getNombre()))
				.collect(Collectors.toList());
		
		eventos.stream()
//				.filter(e1 -> e1.getResultado().equals("0-0"))
//				.map(e2 -> {String texto = e2.getFecha().toString();
//							texto += " " + e2.getResultado();
//							return texto;
//							})
				.forEach(System.out::println);

		participantes.forEach(p -> System.out.println(p));
		EjemploJFrame frame = new EjemploJFrame(participantes);
		frame.mostrarPartido((Partido) ((List)eventos).get(0));
		frame.setVisible(true);
		
	}

}
