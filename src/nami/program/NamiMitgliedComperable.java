package nami.program;

import nami.connector.namitypes.NamiMitglied;

public class NamiMitgliedComperable implements Comparable<NamiMitgliedComperable>{

	private NamiMitglied namiMitglied;
	
	public NamiMitgliedComperable(NamiMitglied namiMitglied) {
		this.namiMitglied = namiMitglied;
	}

	public NamiMitglied getNamiMitglied(){
		return namiMitglied;
	}

	@Override
	public int compareTo(NamiMitgliedComperable o) {
		return (getNamiMitglied().getNachname()+getNamiMitglied().getVorname()).compareTo(o.getNamiMitglied().getNachname()+o.getNamiMitglied().getVorname());
	}
	
	public String toString(){
		return getNamiMitglied().getVorname()+" "+getNamiMitglied().getNachname();
	}
}
