package nami.connector;

/**
 * Beschreibt das Geschlecht eines Mitglieds.
 * 
 * @author Fabian Lipp
 * 
 */
public enum Geschlecht {
    /**
     * Männlich.
     */
    MAENNLICH,

    /**
     * Weiblich.
     */
    WEIBLICH;

    /**
     * Setzt einen String ins entsprechende Geschlecht um.
     * 
     * Edit Tobias Miosczka: Code updated to be compatible with JRE 1.6
     * 
     * @param str
     *            String-Repräsentation des Geschlechts
     * @return entsprechende Ebene; <code>null</code>, wenn der String nicht
     *         umgesetzt werden kann
     */
    public static Geschlecht fromString(String str) {
    	if(str == null){
    		return null;
    	}
    	if(str.equalsIgnoreCase("maennlich")||str.equalsIgnoreCase("männlich")){
    		return MAENNLICH;
    	}
    	if(str.equalsIgnoreCase("weiblich")){
    		return WEIBLICH;
    	}
    	if(str.equalsIgnoreCase("")){
    		return null;
    	}
    	throw new IllegalArgumentException("Unexpected String for Geschlecht: " + str);
    }
}
