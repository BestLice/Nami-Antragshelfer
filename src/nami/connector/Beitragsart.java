package nami.connector;

/**
 * Beschreibt die m�glichen Beitragsarten. Ein m�glicher Stiftungseuro wird
 * ignoriert, weil er f�r die lokalen Anwendungen keine Rolle spielt.
 * 
 * @author Fabian Lipp
 * 
 */
public enum Beitragsart {
    /**
     * Normaler Beitragssatz.
     */
    VOLLER_BEITRAG,

    /**
     * Familienerm��igung.
     */
    FAMILIEN_BEITRAG,

    /**
     * Sozialerm��igung.
     */
    SOZIALERMAESSIGUNG,

    /**
     * Personen, die keinen Mitgliedsbeitrag bezahlen m�ssen.
     */
    KEIN_BEITRAG;

    /**
     * Setzt einen String in die entsprechende Beitragsart um.
     * 
     * @param str
     *            String-Repr�sentation der Beitragsart
     * @return entsprechende Beitragsart; <code>null</code>, wenn keine
     *         Beitragsart angegeben ist
     */
    public static Beitragsart fromString(String str) {
        switch (str) {
        case "Voller Beitrag":
        case "Voller Beitrag - Stiftungseuro":
        case "VOLLER_BEITRAG":
            return VOLLER_BEITRAG;
        case "Familienerm��igt":
        case "Familienerm��igt - Stiftungseuro":
        case "FAMILIEN_BEITRAG":
            return FAMILIEN_BEITRAG;
        case "Sozialerm��igt":
        case "Sozialerm��igt - Stiftungseuro":
        case "SOZIALERMAESSIGUNG":
            return SOZIALERMAESSIGUNG;
        case "NICHT_MITGLIEDER":
        case "KEIN_BEITRAG":
        case "(keine Beitragsarten zugeordnet)":
            return KEIN_BEITRAG;
        case "":
            return null;
        default:
            throw new IllegalArgumentException(
                    "Unexpected String for Beitragsart:" + str);
        }
    }
}
