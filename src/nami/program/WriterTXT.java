package nami.program;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.io.FileUtils;

import nami.connector.namitypes.NamiMitglied;

public class WriterTXT {
	
	private JFrame frame;
	private String pfad;
	
	public WriterTXT(JFrame frame){
		this.frame=frame;
	}
	
	public void SaveAsTXT(java.util.List<NamiMitgliedComperable>list){
		String r="";
		for(NamiMitgliedComperable element : list){
			NamiMitglied e = element.getNamiMitglied();
			r=r+e.getVorname()+" "+e.getNachname()+" "+e.getStufe()+" "+e.getId()+" "+e.getGeschlecht().toString()+"\r\n";
		}
		saveAs();
		System.out.println(pfad);
		try {
			FileUtils.writeStringToFile(new File(pfad), r);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	 private boolean saveAs() {
		 	JFileChooser chooser;
	        if (pfad == null)
	            pfad = System.getProperty("user.home");
	        File file = new File(pfad.trim());
	        chooser = new JFileChooser(pfad);
	        chooser.setDialogType(JFileChooser.SAVE_DIALOG);
	        FileNameExtensionFilter plainFilter = new FileNameExtensionFilter(
	                "Plaintext: txt", "txt");
	        FileNameExtensionFilter markUpFilter = new FileNameExtensionFilter(
	                "Markup: txt", "txt");
	        chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());
	        chooser.setFileFilter(plainFilter);
	        chooser.setFileFilter(markUpFilter);
	        chooser.setDialogTitle("Speichern unter...");
	        chooser.setVisible(true);
	        int result = chooser.showSaveDialog(frame);
	        if (result == JFileChooser.APPROVE_OPTION){
	            pfad = chooser.getSelectedFile().toString();
	            file = new File(pfad);
	            if (plainFilter.accept(file) || markUpFilter.accept(file))
	                System.out.println(pfad + " kann gespeichert werden.");
	            else
	                System.out.println(pfad + " ist der falsche Dateityp.");
	            chooser.setVisible(false);
	            return true;
	        }
	        chooser.setVisible(false);
	        return false;
	    } 
}
