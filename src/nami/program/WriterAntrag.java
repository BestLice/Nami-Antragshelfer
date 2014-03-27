package nami.program;

import java.util.List;
import org.odftoolkit.simple.TextDocument;

/**
 * abstract class to generate and output application form.
 * 
 * @author Tobias Miosczka
 *
 */
public abstract class WriterAntrag {
	
	private TextDocument odtDoc;
	
	/**
	 * Constructor
	 */
	public WriterAntrag(){
		
	}
	
	/**
	 * @param input
	 * 				path of pregenerated application form as string
	 * @param output
	 * 				path to output file
	 * @param participants
	 * 				List of all participants
	 * @throws Exception
	 * 				
	 */
	public void run(String input, String output, List<NamiMitgliedComperable> participants) throws Exception{
		//input
		odtDoc=(TextDocument)TextDocument.loadDocument(input);
		//compute
		doTheMagic(participants, odtDoc);
		//output
		try {
			odtDoc.save(output);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * abstract function to manipulate the application form
	 * 
	 * @param participants
	 * 				List of all participants
	 * @param odtDoc
	 * 				document object
	 */
	public abstract void doTheMagic(List<NamiMitgliedComperable> participants, TextDocument odtDoc);
	
}
