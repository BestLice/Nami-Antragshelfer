package nami.connector.namitypes;

import java.io.IOException;

import nami.connector.Geschlecht;
import nami.connector.Mitgliedstyp;
import nami.connector.NamiConnector;
import nami.connector.exception.NamiApiException;

public class NamiMitgliedListElement extends NamiAbstractMitglied implements
        Comparable<NamiMitgliedListElement> {
    public static class EntriesType {
        @SuppressWarnings("unused")
		private String id;

        private String vorname;
        private String nachname;

        private String email;
        @SuppressWarnings("unused")
		private String emailVertretungsberechtigter;
        @SuppressWarnings("unused")
		private String telefon1;
        @SuppressWarnings("unused")
		private String telefon2;
        @SuppressWarnings("unused")
		private String telefon3;
        @SuppressWarnings("unused")
		private String telefax;

        // nur in Suche, nicht in Mitgliederverwaltung
        private String gruppierungId;
        private String gruppierung;

        @SuppressWarnings("unused")
		private String stufe;
        @SuppressWarnings("unused")
		private String geburtsDatum;

        private String mglType;
        @SuppressWarnings("unused")
		private String status;

        @SuppressWarnings("unused")
		private String staatsangehoerigkeit;
        @SuppressWarnings("unused")
		private String staatangehoerigkeitText;
        private String geschlecht;
        @SuppressWarnings("unused")
		private String konfession;
        @SuppressWarnings("unused")
		private String rowCssClass;
        @SuppressWarnings("unused")
		private String lastUpdated;
        @SuppressWarnings("unused")
		private String version;
        @SuppressWarnings("unused")
		private String wiederverwendenFlag;
        private String mitgliedsNummer;
    }

    @SuppressWarnings("unused")
	private String descriptor;
    private EntriesType entries;
    private int id;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getGruppierungId() {
        return Integer.parseInt(entries.gruppierungId);
    }

    @Override
    public String getGruppierung() {
        return entries.gruppierung;
    }

    @Override
    public String getVorname() {
        return entries.vorname;
    }

    @Override
    public String getNachname() {
        return entries.nachname;
    }

    @Override
    public String getEmail() {
        return entries.email;
    }

    @Override
    public Mitgliedstyp getMitgliedstyp() {
        return Mitgliedstyp.fromString(entries.mglType);
    }

    @Override
    public Geschlecht getGeschlecht() {
        return Geschlecht.fromString(entries.geschlecht);
    }

    @Override
    public int getMitgliedsnummer() {
        return Integer.parseInt(entries.mitgliedsNummer);
    }

    @Override
    public NamiMitglied getFullData(NamiConnector con) throws NamiApiException,
            IOException {
        return NamiMitglied.getMitgliedById(con, id);
    }

    @Override
    public int compareTo(NamiMitgliedListElement o) {
        return Integer.valueOf(this.id).compareTo(Integer.valueOf(o.id));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof NamiMitgliedListElement)) {
            return false;
        }
        NamiMitgliedListElement other = (NamiMitgliedListElement) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }
}
