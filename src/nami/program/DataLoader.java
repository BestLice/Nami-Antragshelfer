package nami.program;

import java.util.Collection;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import nami.connector.NamiConnector;
import nami.connector.namitypes.NamiMitgliedListElement;
import nami.connector.namitypes.NamiSearchedValues;

/**
 * Class for downloading Nami data packages in another thread.
 * 
 * @author Tobias Miosczka
 * 
 */
class IntegerAndString{
	private int integer;
	private String string;
	
	public IntegerAndString(int integer, String string){
		this.integer = integer;
		this.string = string;
	}
	
	public int getInteger(){
		return integer;
	}
	
	public String getString(){
		return string;
	}
}

class DataLoader extends SwingWorker<Void, IntegerAndString>{
	
	private Program program;
	
	public DataLoader(Program program){
		this.program = program;
	}
	
	/**
     * Updates the JProgressbar.
     * 
     * @param chunks
     *           	List<IntegerAndString> with information about the progress
     */
	@Override
    protected void process(List<IntegerAndString> chunks) {
        IntegerAndString i = chunks.get(chunks.size()-1);
        program.getWindow().getProgressBar().setValue(i.getInteger());
        program.getWindow().getProgressBar().setString("L�dt "+i.getString()+" "+program.getWindow().getProgressBar().getValue()+"%");
        program.getWindow().updateLists();
    }
	
	/**
     * L�dt alle daten in einem anderem Thread runter.
     */
	@Override
	protected Void doInBackground() throws Exception {
		double t1=System.currentTimeMillis();
		NamiSearchedValues search = new NamiSearchedValues();
		Collection<NamiMitgliedListElement> result = search.getAllResults(program.getConnection());
		int i=0;
		int items=result.size();
		for(NamiMitgliedListElement element : result){
			NamiMitgliedComperable e = new NamiMitgliedComperable(element.getFullData(program.getConnection()));
			program.getMember().add(e);
			i++;
			//JOptionPane.showMessageDialog(null, e.getNamiMitglied().getGeburtsDatum());
			publish(new IntegerAndString((int)(100*i/items), element.getVorname()+" "+element.getNachname()));
		}
		publish(new IntegerAndString(100, "Alle Mitglieder geladen. "+(System.currentTimeMillis() - t1)+" ms"));
		return null;
	}	
}