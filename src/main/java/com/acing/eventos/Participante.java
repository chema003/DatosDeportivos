package com.acing.eventos;
import java.util.HashMap;
import java.util.Map;

import es.lanyu.commons.identificable.AbstractNombrable;

public class Participante extends AbstractNombrable {
	public static Map<String, Participante> mapaParticipantes = new HashMap<>();
	
//	@Override
//	public String getIdentificador() {
//		return id;
//	}
	
	public Participante() {
	}
	
	public Participante(String nombre) {
//		this.nombre = nombre;
		setIdentificador(nombre);
		setNombre(nombre);
	}

	@Override
	public String toString() {
		return nombre;
	}
	
}
