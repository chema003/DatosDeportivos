package com.acing.eventos;

import java.util.Collection;
import java.util.stream.Collectors;

public interface GestorSucesos extends Evento {

	default <T extends Suceso> Collection<T> getSucesosGestionados(Class<T> tipo){
		return (Collection<T>) getSucesos().stream()
				.filter(s -> s.getClass().isAssignableFrom(tipo))
				.collect(Collectors.toList());
	}
	
	default int getSucesosParticipante(Participante participante){
		return (int) getSucesos().stream()
				.filter(s -> s.getParticipante().equals(participante))
				.count();
	}

	default <T extends Suceso> void addSuceso(T suceso) {
		getSucesos().add(suceso);
	}
	
	default <T extends Suceso> void addSucesos(Class<T> tipo, int numero, Participante participante) {
		for(int i = 0; i < numero; i++) {
			T suceso;
			try {
				suceso = tipo.newInstance();
				suceso.setParticipante(participante);
				addSuceso(suceso);
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
	default <T extends Suceso> void addSuceso(Class<T> tipo, Participante participante) {
		addSucesos(tipo, 1, participante);
	}

}
