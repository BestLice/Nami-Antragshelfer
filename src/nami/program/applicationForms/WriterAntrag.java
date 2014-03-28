package nami.program.applicationForms;

import java.util.List;

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
	private void runOneDoc(String input, String output, List<NamiMitgliedComperable> participants) throws Exception{
		//input
		odtDoc=(TextDocument)TextDocument.loadDocument(input);
		//manipulate doc
		doTheMagic(participants, odtDoc);
		//output
		odtDoc.save(output);
	}
	
	private String convertPathString(String path, int number){
		String newPath = path.substring(0, path.length()-4)+"-"+String.valueOf(number)+".odt";
		return newPath;
	}
	
	public void run(String input, String output, List<NamiMitgliedComperable> participants) throws Exception{
		int pages = (int) Math.ceil((double)(participants.size())/(double)(getMaxParticipantsPerPage()));
		System.out.println(pages);
		try {
			if(getMaxParticipantsPerPage()==0){
				runOneDoc(input, output, participants);
			}else{
				for(int i=0;i<pages;i++){
					
					int from=i*getMaxParticipantsPerPage();
					int to=((i+1)*getMaxParticipantsPerPage());					
					runOneDoc(input, convertPathString(output, i+1), participants.subList(from, to > participants.size() ? participants.size() : to));
				}
			}				
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
	
	public abstract int getMaxParticipantsPerPage();
	
	public abstract void doTheMagic(List<NamiMitgliedComperable> participants, TextDocument odtDoc);
	
}
