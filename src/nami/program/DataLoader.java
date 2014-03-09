package nami.program;

import java.util.Collection;
import java.util.List;

import javax.swing.SwingWorker;

import nami.connector.NamiConnector;
import nami.connector.namitypes.NamiMitgliedListElement;
import nami.connector.namitypes.NamiSearchedValues;

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
	
	private NamiConnector con;
	private List<NamiMitgliedComperable> member;
	private Window window;
	
	public DataLoader(NamiConnector con, List<NamiMitgliedComperable> member, Window window){
		this.con=con;
		this.member=member;
		this.window=window;
	}

	@Override
    protected void process(List<IntegerAndString> chunks) {
        IntegerAndString i = chunks.get(chunks.size()-1);
        window.getProgressBar().setValue(i.getInteger());
        window.getProgressBar().setString("Lädt "+i.getString()+" "+window.getProgressBar().getValue()+"%");
        window.updateLists();
    }

	@Override
	protected Void doInBackground() throws Exception {
		double t1=System.currentTimeMillis();
		NamiSearchedValues search = new NamiSearchedValues();
		Collection<NamiMitgliedListElement> result = search.getAllResults(con);
		int i=0;
		int items=result.size();
		for(NamiMitgliedListElement element : result){
			member.add(new NamiMitgliedComperable(element.getFullData(con)));
			i++;
			publish(new IntegerAndString((int)(100*i/items), element.getVorname()+" "+element.getNachname()));
		}
		publish(new IntegerAndString(100, "Alle Mitglieder geladen. "+(System.currentTimeMillis() - t1)+" ms"));
		return null;
	}	
}