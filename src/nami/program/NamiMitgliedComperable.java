package nami.program;

import nami.connector.namitypes.NamiMitglied;

/**
 * Class to make <NamiMitglied> comparable
 * 
 * @author Tobias Miosczka
 *
 */
public class NamiMitgliedComperable implements Comparable<NamiMitgliedComperable>{

	private NamiMitglied namiMitglied;
	
	/**
	 * Constructor
	 * 
	 * @param namiMitglied
	 * 				<NamiMitglied> saved in object
	 */
	public NamiMitgliedComperable(NamiMitglied namiMitglied) {
		this.namiMitglied = namiMitglied;
	}
	
	/**
	 * returns the <NamiMitglied> saved in the object
	 * 
	 * @return
	 * 				<NamiMitglied> saved in object
	 */
	public NamiMitglied getNamiMitglied(){
		return namiMitglied;
	}

	/** 
	 * Comperator
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(NamiMitgliedComperable o) {
		return (getNamiMitglied().getNachname()+getNamiMitglied().getVorname()).compareTo(o.getNamiMitglied().getNachname()+o.getNamiMitglied().getVorname());
	}
	
	/**
	 * returns object as a string (name+lastname)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return getNamiMitglied().getVorname()+" "+getNamiMitglied().getNachname();
	}
}
