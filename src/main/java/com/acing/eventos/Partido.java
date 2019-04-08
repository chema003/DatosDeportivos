package com.acing.eventos;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Partido extends EventoImpl implements GestorSucesos {
	private final static SimpleDateFormat sdfToString= new SimpleDateFormat("dd/MM/yy HH:mm");
	public Participante local;
	public Participante visitante;
//	String resultado;
	
	@Override
	public String getResultado() {
		long golesLocal = 0;// = getSucesos().stream()
//							.filter(s -> s.getParticipante().equals(local))
//							.count();
		long golesVisitante = 0;
//		for (Suceso s : getSucesos()) {
//			if(s.getParticipante().equals(visitante))
//				golesVisitante++;
//			if(s.getParticipante().equals(local))
//				golesLocal++;
//		}
		golesLocal = getSucesosParticipante(local);
		golesVisitante = getSucesosParticipante(visitante);
		
		return golesLocal + "-" + golesVisitante;
	}
	
//	public void setResultado(String resultado) {
////		this.resultado = resultado;
//	}

	public Partido() {}
	
	public Partido(Participante local, Participante visitante, Date fecha) {
		super();
		this.local = local;
		this.visitante = visitante;
		setFecha(fecha);
	}

	@Override
	public String toString() {
		return "(" + sdfToString.format(getFecha()) + ") " + local + " vs " + visitante + " => " + getResultado();
	}
	
}
