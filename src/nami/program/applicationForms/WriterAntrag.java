package nami.program.applicationForms;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import nami.program.NamiMitgliedComperable;

import org.odftoolkit.simple.TextDocument;

/**
 * abstract class to generate and output application form.
 * 
 * @author Tobias Miosczka
 *
 */
public abstract class WriterAntrag {
	
	private TextDocument odtDoc;
	private JFrame owner;
	
	protected UserInput userInput;
	
	protected SimpleDateFormat sdfDB = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");	
	
	/**
	 * Constructor
	 */
	public WriterAntrag(JFrame owner){
		this.owner = owner;
	}
	
	/**
	 * manipulates and saves ONE page
	 * 
	 * @param input
	 * 				filename of pregenerated application form as string
	 * @param output
	 * 				path to output file
	 * @param participants
	 * 				List of all participants
	 * @throws Exception
	 * 				
	 */
	private void runOneDoc(String output, List<NamiMitgliedComperable> participants) throws Exception{
		//input		
		InputStream s = Thread.currentThread().getContextClassLoader().getResourceAsStream("resources/"+getResourceFileName());
		odtDoc=(TextDocument)TextDocument.loadDocument(s);
		//manipulate doc
		Logger.getLogger("org.apache.zookeeper").setLevel(Level.WARNING);
		Logger.getLogger("org.apache.hadoop.hbase.zookeeper").setLevel(Level.WARNING);
		Logger.getLogger("org.apache.hadoop.hbase.client").setLevel(Level.WARNING);
		doTheMagic(participants, odtDoc);
		//output
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setSelectedFile(new File(output));
		fc.showDialog(null, "Speichern");
		odtDoc.save(fc.getSelectedFile().getAbsolutePath());
	}
	
	/**
	 * Returns a path as string with the given page number.
	 * 
	 * @param filename
	 * 				filename
	 * @param number
	 * 				number of the current page
	 * @return
	 * 				Returns a path as string with the given page number.
	 * 				Example: "example.odt" -> "example-1.odt"
	 */
	private String convertPathString(String filename, int number){
		return filename.substring(0, filename.length()-4)+"-"+String.valueOf(number)+filename.substring(filename.length()-4, filename.length());
	}
	
	/**
	 * manipulates and saves ALL pages
	 * 
	 * @param input
	 * 				path of pregenerated application form as string
	 * @param output
	 * 				path to output file
	 * @param participants
	 * 				List of all participants
	 * @throws Exception
	 * 				
	 */	
	public void run(String output, List<NamiMitgliedComperable> participants) throws Exception{
		int pages = (int) Math.ceil((double)(participants.size())/(double)(getMaxParticipantsPerPage()));
		if(pages==0){
			//no pages to export
			System.out.println("No participants were selected. Select at least one participant to export an application form.");
			return;
		}
		System.out.println("Application form ''"+getResourceFileName()+"''(pages: "+String.valueOf(pages)+") will be exported.");
        //get info
		userInput = new UserInput(owner);
		initializeOptions();
		if(!userInput.showModal()){
			//Canceled
			System.out.println("Application form export has been canceled.");
			return;
		}
		try {
			if(getMaxParticipantsPerPage()==0){
				runOneDoc(output, participants);
			}else{
				for(int i=0;i<pages;i++){
					
					int from=i*getMaxParticipantsPerPage();
					int to=((i+1)*getMaxParticipantsPerPage());					
					runOneDoc(convertPathString(output, i+1), participants.subList(from, to > participants.size() ? participants.size() : to));
				}
			}				
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
		
	/**
	 * Returns the number of participants on one page of the application form.
	 * If it returns 0, an infinite number of participants can be inserted in one page.
	 * 
	 * @return number of participants on one page of the application form, returns 0 if this number is infinite
	 */
	public abstract int getMaxParticipantsPerPage();
	
	/**
	 * abstract function to manipulate the application form
	 * 
	 * @param participants
	 * 				List of all participants
	 * @param odtDoc
	 * 				document object
	 */	
	public abstract void doTheMagic(List<NamiMitgliedComperable> participants, TextDocument odtDoc);
		
	/**
	 * returns resource file name as a string. the resource files saved in "/resources"
	 * 
	 * @return
	 * 				returns file name as a string
	 */
	protected abstract String getResourceFileName();
	
	
	/**
	 * abstract method to initialize all additional options
	 * example:
	 * 		addOption("name of that option");
	 */
	protected abstract void initializeOptions();
}
