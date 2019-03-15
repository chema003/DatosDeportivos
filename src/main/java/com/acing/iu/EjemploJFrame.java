package com.acing.iu;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.acing.eventos.Partido;
import com.acing.serial.SerializadorCSV;
import com.esotericsoftware.tablelayout.Value;
import com.esotericsoftware.tablelayout.swing.Table;

public class EjemploJFrame extends JFrame {

	private JComboBox cb_VariosTipos;
	private JCheckBox ckb_ParaAlgo;
	private JTextField txt_Input;

	public EjemploJFrame() {
		super();
		initialize();
	}
	
	public static void main(String... args) {
		JFrame prueba = new EjemploJFrame();
		prueba.setVisible(true);
	}
	
	private void initialize() {
		setName("Ejemplo");
        setLocale(new Locale("es", "ES"));
        setMinimumSize(new Dimension(500, 600));
        //Establece el tamaño
        setBounds(100, 100, 1100, 400);
        
        setTitle("Mi titulo");
        
      //Para que termine la ejecucion al cerrar
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
      //Forma normal con swing
//      JPanel pn_Principal = new JPanel();
//      BoxLayout boxLayout = new BoxLayout(pn_Principal, BoxLayout.Y_AXIS);
//      pn_Principal.setLayout(boxLayout);
//      getContentPane().add(pn_Principal, BorderLayout.NORTH);
//      
//      JPanel pn_AccesoDirecto = new JPanel();
//      ((FlowLayout) pn_AccesoDirecto.getLayout()).setAlignment(FlowLayout.LEFT);
//      modificarValores = new BotonAccion("Añadir/Modificar Valores", "Modificar Valores");
//      pn_AccesoDirecto.add(modificarValores);
      
      //Componentes
      JLabel lbl_1 = new JLabel("Label 1");
      JLabel lbl_2 = new JLabel("Pinta toString() por defecto");
      JLabel lbl_3 = new JLabel("Label 3");
      JLabel lbl_4 = new JLabel("Label 4");
      JLabel lbl_Resultado = new JLabel("Resultado de una operación");
      JButton btn_1 = new JButton("Botón 1");
      JButton btn_2 = new JButton("Botón 2");
      JComboBox<Object> cb_String = new JComboBox<Object>(new SerializadorCSV("datos/SP1.csv")
    		  	.getEventos().stream()
    		  	.map(e -> e.toString()).collect(Collectors.toList()).toArray());
    		  //new String[]{ "Primero", "Segundo" });
      
      //Para usarlo en el combobox de abajo
      Object objetoPersonalizado = new Object() {
          @Override
          public String toString() {
              return "Mi Objeto especial";
          } };
      
      //Acceso desde otro scope
      cb_VariosTipos = new JComboBox<>(new Object[]{ "Primero", 2, true, objetoPersonalizado });
      ckb_ParaAlgo = new JCheckBox("Es Algo (label CheckBox)");
      txt_Input = new JTextField();
      txt_Input.setText("Algo escrito para pruebas");
      
    //Listeners
      btn_1.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			lbl_Resultado.setText("Pulsado " + ((JButton)e.getSource()).getText());
		}
	});
//      btn_1.addActionListener(e -> lbl_Resultado.setText("Pulsado " + ((JButton)e.getSource()).getText().toString()));
      btn_2.addActionListener(e -> lbl_Resultado.setText(validarInput()));
      
    //Layout (configuracion general)
      Table tabla = new Table();
      Value padding = new Value.FixedValue(5);
      tabla.top().left().pad(padding);
      tabla.defaults().pad(padding).left();
//    tabla.columnDefaults(1).width(new Value.FixedValue(100));
      tabla.columnDefaults(2).expandX();
      getContentPane().add(tabla);
      
      //Tamanhos
      Value anchoCombos = new Value.FixedValue(200);
      
      //Modo debug (se ven los bordes del layout)
//      tabla.debug(Debug.all);
      
      //Agregar los componentes
      tabla.addCell(lbl_1);
      tabla.addCell(cb_String).width(anchoCombos);
      tabla.row();
      tabla.addCell(lbl_3);
      tabla.addCell(cb_VariosTipos).fillX();
      tabla.addCell(lbl_2);//.expandX();
      tabla.row();
      tabla.addCell(new JLabel("Es Algo"));
//      ckb_ParaAlgo.setHorizontalTextPosition(SwingConstants.LEFT);
      tabla.addCell(ckb_ParaAlgo);
      tabla.row();
      tabla.addCell(lbl_4);//lbl_1);
      tabla.addCell(txt_Input).colspan(2).fillX();
      tabla.row();
      tabla.addCell(btn_1).center();
      tabla.addCell(lbl_Resultado).colspan(2);
      tabla.addCell(btn_2);
//      lbl_Ciu.setText("Label 1 cambiada");
	}

	public void mostrarPartido(Partido partido) {
		txt_Input.setText(partido.toString());
	}
	
	private String validarInput() {
		return ckb_ParaAlgo.isSelected() ?
				txt_Input.getText() :
					cb_VariosTipos.getSelectedItem().toString();
	}
}
