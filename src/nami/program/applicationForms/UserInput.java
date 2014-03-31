package nami.program.applicationForms;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.BorderLayout;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UserInput implements ActionListener{
	
	private class Input{
		
		private JLabel label;
		private JTextField textField;	
		
		public Input(JPanel parent, int index, String description){
			label = new JLabel(description+":");
			textField = new JTextField();
			
			label.setBounds    ( 10,  11+(index*30), 130,  20);
			textField.setBounds(150,  11+(index*30), 220,  20);
			
			parent.add(label);
			parent.add(textField);			
		}
		
		public Input(JPanel parent, int index, String description, String preview){
			label = new JLabel(description+":");
			textField = new JTextField();
			
			label.setBounds    ( 10,  11+(index*30), 130,  20);
			textField.setBounds(150,  11+(index*30), 220,  20);
			
			textField.setText(preview);
			
			parent.add(label);
			parent.add(textField);			
		}
		
		public String getValue(){
			return textField.getText();
		}
	}
	private JDialog dialog;
	private JPanel panel;	
	private List<Input> inputs;
	private boolean isOK;
	
	private JButton btnOK,
					btnCancel;
		
	/**
	 * Create the panel.
	 */
	public UserInput(JFrame owner) {	
		
		dialog = new JDialog(owner, true);
		isOK = false;
		dialog.setTitle("Optionen");
		dialog.setResizable(false);
		inputs = new LinkedList<Input>();
		dialog.getContentPane().setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		dialog.getContentPane().add(panel);
		panel.setLayout(null);
		
		JPanel panel_OK_CANCEL = new JPanel();
		dialog.getContentPane().add(panel_OK_CANCEL, BorderLayout.SOUTH);
		
		btnOK = new JButton("Fertig");
		btnOK.addActionListener(this);
		panel_OK_CANCEL.add(btnOK);
		
		btnCancel = new JButton("Abbrechen");
		btnCancel.addActionListener(this);
		panel_OK_CANCEL.add(btnCancel);
	}
	
	public int addOption(String description){
		inputs.add(new Input(panel, inputs.size(), description));
		dialog.setBounds(0, 0, 400, (inputs.size()*30)+80);
		return inputs.size()-1;
	}
	
	public int addOption(String description, String preview){
		inputs.add(new Input(panel, inputs.size(), description, preview));
		dialog.setBounds(0, 0, 400, (inputs.size()*30)+80);
		return inputs.size()-1;
	}
	
	public boolean getIsOK(){
		return isOK;
	}
	
	public boolean showModal(){
		dialog.setVisible(true);
		return isOK;
	}
	
	public String getOptionValue(int index){
		return inputs.get(index).getValue();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source==btnOK){
			isOK = true;
			dialog.setVisible(false);
		}
		if(source==btnCancel){
			isOK = false;
			dialog.setVisible(false);
		}
	}
}
