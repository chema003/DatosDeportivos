package com.acing.iu;

import java.awt.Component;
import java.util.Collection;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import com.acing.eventos.Partido;

public class TablaPartidos extends JTable {

	public TablaPartidos(Collection<? extends Partido> partidos) {
		
//		TableModel dataModel = new AbstractTableModel() {
//	          public int getColumnCount() { return 10; }
//	          public int getRowCount() { return 100;}
//	          public Object getValueAt(int row, int col) { return new Integer(row*col); }
//	    };
//	      
//	    setModel(dataModel);
		
		Vector<String> nombresColumna = new Vector<>();
		nombresColumna.add("local");
		nombresColumna.add("visitante");
		nombresColumna.add("resultado");
		nombresColumna.add("fecha");
		Vector<Vector<Object>> datos = new Vector<>();
		Vector<Object> fila;
		for(Partido p : partidos) {
			fila = new Vector<>();
			fila.add(p);
			fila.add(p.visitante.getNombre());
			fila.add(p.getResultado());
			fila.add(p.getFecha());
			datos.add(fila);
		}
		
		DefaultTableModel modelo = new DefaultTableModel(datos, nombresColumna) {
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return (columnIndex == 0) ? Partido.class : super.getColumnClass(columnIndex);
			}
		};
		setModel(modelo);
		
		//Formateo de columnas
		int indiceLocal = modelo.findColumn("local");
		getColumnModel().getColumn(indiceLocal).setPreferredWidth(200);
		getColumnModel().getColumn(1).setPreferredWidth(200);
		getColumnModel().getColumn(modelo.findColumn("resultado")).setPreferredWidth(20);
		getColumnModel().getColumn(modelo.findColumn("fecha")).setPreferredWidth(200);
		
		//Render de celdas
		TableCellRenderer render = new TableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				return new JLabel(((Partido)value).local.toString());
			}
		};
//		getColumnModel().getColumn(indiceLocal).setCellRenderer(render);
		setDefaultRenderer(Partido.class, render);
		
	}
	
	public void mostrarPartidos(Collection<? extends Partido> partidos) {
		
	}
}
