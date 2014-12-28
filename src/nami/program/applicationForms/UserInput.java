package nami.program.applicationForms;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UserInput implements ActionListener{
	
	abstract class Input<Type>{
		private JLabel label;
		
		public Input(JPanel parent, int index, String description) {
			label = new JLabel(description+":");
			label.setBounds(10, 11+(index*30), 130,  20);
			parent.add(label);
		}
		
		public void showError(){
			label.setForeground(Color.RED);
		}
		
		public abstract boolean check();
		public abstract Type getValue();
		public abstract String toString();
	}
	
	private class InputString extends Input<String>{
		JTextField textField;
		public InputString(JPanel parent, int index, String description, String preview) {
			super(parent, index, description);
			textField = new JTextField(preview);
			textField.setBounds(150,  11+(index*30), 220,  20);
			parent.add(textField);
		}

		@Override
		public boolean check() {
			return true;
		}

		@Override
		public String getValue() {
			return textField.getText();
		}

		@Override
		public String toString() {
			return getValue();
		}
	}
	
	@SuppressWarnings("unused")
	private class InputInteger extends Input<Integer>{
		JTextField textField;
		public InputInteger(JPanel parent, int index, String description, int preview) {
			super(parent, index, description);
			textField = new JTextField(preview);
			textField.setBounds(150,  11+(index*30), 220,  20);
			parent.add(textField);
		}

		@Override
		public boolean check() {
			try { 
		        Integer.parseInt(textField.getText()); 
		    } catch(NumberFormatException e) { 
		        return false; 
		    }
		    return true;
		}

		@Override
		public Integer getValue() {
			Integer i = Integer.getInteger(textField.getText());
			return null;
		}

		@Override
		public String toString() {
			return String.valueOf(getValue());
		}

	}
	
	private class InputDate extends Input<Date>{
		JTextField textField;
		SimpleDateFormat sdfUserInput = new SimpleDateFormat("dd.MM.yyyy");
		
		public InputDate(JPanel parent, int index, String description, Date preview) {
			super(parent, index, description);
			textField = new JTextField(sdfUserInput.format(preview));
			textField.setBounds(150,  11+(index*30), 220,  20);
			parent.add(textField);
		}

		@Override
		public boolean check() {
			try {
				sdfUserInput.parse(textField.getText());
			} catch (ParseException e) {
				return false;
			}
			return true;
		}

		@Override
		public Date getValue() {
			try {
				return sdfUserInput.parse(textField.getText());
			} catch (ParseException e) {
				return null;
			}
		}

		@Override
		public String toString() {
			return sdfUserInput.format(getValue());
		}	
	}
	
	private class InputBoolean extends Input<Boolean>{
		JCheckBox checkBox;
		public InputBoolean(JPanel parent, int index, String description, boolean preview) {
			super(parent, index, description);
			checkBox = new JCheckBox();
			checkBox.setSelected(false);
			checkBox.setBounds(150,  11+(index*30), 220,  20);
			parent.add(checkBox);
		}

		@Override
		public boolean check() {
			return true;
		}

		@Override
		public Boolean getValue() {
			return checkBox.isSelected();
		}

		@Override
		public String toString() {
			return null;
		}
		
	}
	
	private JDialog dialog;
	private JPanel panel;	
	@SuppressWarnings("rawtypes")
	private List<Input> inputList;
	private boolean isOK;
	
	private JButton btnOK,
					btnCancel;
		
	/**
	 * Create the panel.
	 */
	@SuppressWarnings("rawtypes")
	public UserInput(JFrame owner) {	
		
		dialog = new JDialog(owner, true);
		isOK = false;
		dialog.setTitle("Optionen");
		dialog.setResizable(false);
		inputList = new LinkedList<Input>();
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
	
	public int addStringOption(String description, String preview){
		inputList.add(new InputString(panel, inputList.size(), description, preview));
		dialog.setBounds(0, 0, 400, (inputList.size()*30)+80);
		return inputList.size()-1;
	}
	
	public int addIntegerOption(String description, int preview){
		inputList.add(new InputInteger(panel, inputList.size(), description, preview));
		dialog.setBounds(0, 0, 400, (inputList.size()*30)+80);
		return inputList.size()-1;
	}

	public int addDateOption(String description, Date preview){
		inputList.add(new InputDate(panel, inputList.size(), description, preview));
		dialog.setBounds(0, 0, 400, (inputList.size()*30)+80);
		return inputList.size()-1;
	}
	
	public int addBooleanOption(String description, boolean preview){
		inputList.add(new InputBoolean(panel, inputList.size(), description, preview));
		dialog.setBounds(0, 0, 400, (inputList.size()*30)+80);
		return inputList.size()-1;
	}
	
	public boolean getIsOK(){
		return isOK;
	}
	
	public boolean showModal(){
		dialog.setVisible(true);
		return isOK;
	}
	
	@SuppressWarnings("rawtypes")
	public Input getOption(int index){
		return inputList.get(index);
	}
	
	@SuppressWarnings("rawtypes")
	private boolean check(){
		boolean valid = true;
		for (Input input : inputList) {
			if(!input.check()){
				input.showError();
				valid = false;
			}
		}
		return valid;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source==btnOK){
			if(check()){
				isOK = true;
				dialog.setVisible(false);
			}
		}
		if(source==btnCancel){
			isOK = false;
			dialog.setVisible(false);
		}
	}
}
