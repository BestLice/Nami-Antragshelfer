package nami.connector;

/**
 * Mitgliedstypen, die es in NaMi gibt.
 * 
 * @author Fabian Lipp
 * 
 */
public enum Mitgliedstyp {
    // Die Werte des Enums entsprechen genau den Mitgliedstyp-Konstanten aus
    // NaMi (müssen so von der toString-Methode geliefert werden, da sie
    // beispielsweise direkt in Suchanfragen eingefügt werden)
    /**
     * Normale Mitgliedschaft.
     */
    MITGLIED,

    /**
     * Keine Mitgliedschaft.
     */
    NICHT_MITGLIED,

    /**
     * Schnuppermitgliedschaft.
     */
    SCHNUPPER_MITGLIED;

    /**
     * Setzt einen String in den entsprechenden Mitgliedstyp um.
     * 
     * Edit Tobias Miosczka: Code updated to be compatible with JRE 1.6
     * 
     * @param str
     *            String-Repräsentation des Mitgliedstyps
     * @return entsprechender Mitgliedstyp; <code>null</code>, wenn der String
     *         nicht umgesetzt werden kann
     */
    public static Mitgliedstyp fromString(String str) {
    	if(str == null){
    		return null;
    	}
    	if(str.equalsIgnoreCase("mitglied")){
    		return MITGLIED;
    	}
    	if(str.equalsIgnoreCase("nicht-mitglied")||str.equalsIgnoreCase("nicht mitglied")||str.equalsIgnoreCase("nicht_mitglied")){
    		return NICHT_MITGLIED;
    	}
    	if(str.equalsIgnoreCase("schnuppermitglied")||str.equalsIgnoreCase("schnupper mitglied")||str.equalsIgnoreCase("nicht-mitglied")||str.equalsIgnoreCase("nicht_mitglied")){
    		return SCHNUPPER_MITGLIED;
    	}
    	if(str.equalsIgnoreCase("")){
    		return null;
    	}
        throw new IllegalArgumentException("Unexpected String for Mitgliedstyp: " + str);
    }
}
