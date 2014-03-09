package nami.program;

import java.util.List;

import org.odftoolkit.simple.TextDocument;

public abstract class WriterAntrag {
	
	private TextDocument odtDoc;
	
	public WriterAntrag(String input, String output, List<NamiMitgliedComperable> participants) throws Exception {
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
	
	public abstract void doTheMagic(List<NamiMitgliedComperable> participants, TextDocument odtDoc);
	
}
