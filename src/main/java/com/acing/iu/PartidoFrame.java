package com.acing.iu;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.acing.eventos.Evento;
import com.acing.eventos.Partido;
import com.acing.serial.SerializadorCSV;
import com.esotericsoftware.tablelayout.BaseTableLayout.Debug;
import com.esotericsoftware.tablelayout.Value;
import com.esotericsoftware.tablelayout.swing.Table;

public class PartidoFrame extends JFrame {

	private JLabel lbl_Partido;

	public PartidoFrame() {
		super();
		initialize();
	}
	
	public static void main(String... args) {
		JFrame prueba = new PartidoFrame();
		prueba.setVisible(true);
	}
	
	private void initialize() {
		setName("Partido");
        setLocale(new Locale("es", "ES"));
        setMinimumSize(new Dimension(500, 600));
        //Establece el tamaño
        setBounds(100, 100, 1100, 400);
        
        setTitle("Partido");
        
      //Para que termine la ejecucion al cerrar
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
     
      //Componentes
      lbl_Partido = new JLabel("Partido");
      lbl_Partido.setFont(new Font("Times New Roman", Font.BOLD, 40));
      
    //Layout (configuracion general)
      Table tabla = new Table();
      Value padding = new Value.FixedValue(5);
      tabla.top().left().pad(padding);
      tabla.defaults().pad(padding).left();
      getContentPane().add(tabla);
      
      //Modo debug (se ven los bordes del layout)
//      tabla.debug(Debug.all);
      
      List<Partido> eventos = 
    		  (List<Partido>) new SerializadorCSV("datos/SP1.csv").getEventos();
      
      //Agregar los componentes
      tabla.addCell(lbl_Partido).center().fill();
      
      mostrarPartido((Partido) ((List<Partido>)eventos).get(0));
      
      TablaPartidos tablaPartidos = new TablaPartidos(eventos);
      tablaPartidos.getSelectionModel().addListSelectionListener(e -> 
      		mostrarPartido((Partido) tablaPartidos.getValueAt(tablaPartidos.getSelectedRow(), 0)));
      
      @SuppressWarnings("unchecked")
	SimpleJTable<Partido> listaSimple = new SimpleJTable<Partido>(
              eventos,
              new String[] { "Local", "Visitante", "Resultado", "Fecha" },
              p -> p.local,
              p -> p.visitante,
              Partido::getResultado,
              Partido::getFecha) {
    	  @Override
    	  protected void cambiaLaTabla(Object obj) {
//    		  System.out.println("Metodo cambiado");
    		  setTitle("Partidos (" + eventos.size() + ")");
    	  };
      };
      
      listaSimple.setAnchosPreferidos(200, 200, 20, 200);
      
      
//      JScrollPane scPanel = new JScrollPane(tablaPartidos);
      JScrollPane scPanel = new JScrollPane(listaSimple);
      tabla.row();
      tabla.addCell(scPanel).expand().fill();//.width(new Value.FixedValue(400));

	}

	public void mostrarPartido(Partido partido) {
		lbl_Partido.setText(partido.toString());
	}

}
