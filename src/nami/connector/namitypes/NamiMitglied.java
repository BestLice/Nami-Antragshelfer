package nami.connector.namitypes;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import nami.connector.Geschlecht;
import nami.connector.Mitgliedstyp;
import nami.connector.NamiConnector;
import nami.connector.NamiResponse;
import nami.connector.NamiURIBuilder;
import nami.connector.exception.NamiApiException;
import nami.connector.exception.NamiException;

import org.apache.http.client.methods.HttpGet;

import com.google.gson.reflect.TypeToken;

/**
 * Stellt ein Mitglied der DPSG dar.
 * 
 * @author Fabian Lipp, Tobias Miosczka
 * 
 */
public class NamiMitglied extends NamiAbstractMitglied implements Comparable<NamiMitglied>{
    /**
     * Beschreibt die Bankverbindung eines Mitglieds.
     */
    public static class KontoverbindungType {
		private String id;
		private String mitgliedsNummer;
		private String kontoinhaber;
		private String kontonummer;
		private String bankleitzahl;
		private String institut;
		private String iban;
		private String bic;
		
		public String getId() {return id;}
		public String getMitgliedsNummer() {return mitgliedsNummer;}
		public String getKontoinhaber() {return kontoinhaber;}
		public String getKontonummer() {return kontonummer;}
		public String getBic() {return bic;}
		public String getBankleitzahl() {return bankleitzahl;}
		public String getInstitut() {return institut;}
		public String getIban() {return iban;}

    }

    private int id;
    private int mitgliedsNummer;
	private String beitragsarten;
	private Collection<Integer> beitragsartenId;
	private String statusId; // ENUM??
	private String status; // ENUM?? (z.B. AKTIV)

    private String vorname;
    private String nachname;
    private String strasse;
    private String plz;
    private String ort;
    private String telefon1;
    private String telefon2;
    private String telefon3;
    private String telefax;
    private String email;
    private String emailVertretungsberechtigter;
	private String staatsangehoerigkeitId; // int?
	private String staatsangehoerigkeit;
	private String staatsangehoerigkeitText;
	private String mglTypeId; // ENUM?? z.B. NICHT_MITGLIED
    private String mglType;
    private String geburtsDatumFormatted;
	private String geburtsDatum;
	private String regionId; // int? (null mÃ¶glich)
	private String region;
	private String landId; // int?
	private String land;
    private String gruppierung;
    private int gruppierungId;
	private String ersteUntergliederung;// private String ersteUntergliederungId" : null, //?
	private String ersteTaetigkeitId;
	private String ersteTaetigkeit;
    private String stufe;
	private boolean wiederverwendenFlag;
	private boolean zeitschriftenversand;
	private String konfessionId; // int?
	private String konfession; // ENUM?
	private String geschlechtId;
    private String geschlecht;

    private String eintrittsdatum;
	private String zahlungsweise;
	private int version;
	private String lastUpdated;
	private KontoverbindungType kontoverbindung;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getVorname() {
        return vorname;
    }

    @Override
    public String getNachname() {
        return nachname;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public int getMitgliedsnummer() {
        return mitgliedsNummer;
    }

    @Override
    public Mitgliedstyp getMitgliedstyp() {
        return Mitgliedstyp.fromString(mglType);
    }

    @Override
    public Geschlecht getGeschlecht() {
        return Geschlecht.fromString(geschlecht);
    }

    @Override
    public int getGruppierungId() {
        return gruppierungId;
    }

    @Override
    public String getGruppierung() {
        return gruppierung;
    }

    @Override
    public NamiMitglied getFullData(NamiConnector con) throws NamiApiException,
            IOException {
        // do nothing (this object already contains the full data)
        return this;
    }
    
    public String getStufe(){
    	return stufe;
    }

    /**
     * Gibt die Stammdaten dieses Mitglieds als ausführlichen Text zurück. Der
     * Rückgabewert enthält also mehr Angaben als die Ausgabe der
     * <tt>toString</tt>-Method.
     * 
     * @return für die Ausgabe formatierte Mitgliedsdaten
     */
    public String toLongString() {
        StringBuilder str = new StringBuilder();

        /**
         * Speichert eine Zeile, die ausgegeben werden soll.
         */
        final class Row {
            private String key;
            private String value;

            private Row(String key, String value) {
                this.key = key;
                this.value = value;
            }
        }
        List<Row> rows = new LinkedList<Row>();
        rows.add(new Row("Nachname", nachname));
        rows.add(new Row("Vorname", vorname));
        rows.add(new Row("Straße", strasse));
        rows.add(new Row("PLZ, Ort", plz + " " + ort));
        rows.add(new Row("E-Mail", email));
        rows.add(new Row("E-Mail Vertr.", emailVertretungsberechtigter));
        rows.add(new Row("Telefon 1", telefon1));
        rows.add(new Row("Telefon 2", telefon2));
        rows.add(new Row("Telefon 3", telefon3));
        rows.add(new Row("Telefax", telefax));
        rows.add(new Row("Geburtsdatum", geburtsDatumFormatted));
        rows.add(new Row("Stammgruppierung", gruppierung));
        rows.add(new Row("Stufe", stufe));
        // TODO: Formatierung Eintrittsdatum
        rows.add(new Row("Eintrittsdatum", eintrittsdatum));

        int longestKey = 0;
        for (Row row : rows) {
            if (row.key.length() > longestKey) {
                longestKey = row.key.length();
            }
        }
        String formatString = "  %-" + (longestKey + 1) + "s %s\n";
        for (Row row : rows) {
            str.append(String.format(formatString, row.key + ":", row.value));
        }

        return str.toString();
    }

    /**
     * Holt den Datensatz eines Mitglieds aus NaMi.
     * 
     * @param con
     *            Verbindung zum NaMi-Server
     * @param id
     *            ID des Mitglieds
     * @return Mitgliedsdatensatz
     * @throws IOException
     *             IOException
     * @throws NamiApiException
     *             API-Fehler beim Zugriff auf NaMi
     */
    public static NamiMitglied getMitgliedById(NamiConnector con, int id)
            throws IOException, NamiApiException {
        NamiURIBuilder builder = con
                .getURIBuilder(NamiURIBuilder.URL_NAMI_MITGLIED);
        builder.appendPath(Integer.toString(id));

        HttpGet httpGet = new HttpGet(builder.build());

        Type type = new TypeToken<NamiResponse<NamiMitglied>>() { }.getType();
        NamiResponse<NamiMitglied> resp = con.executeApiRequest(httpGet, type);

        if (resp.isSuccess()) {
            return resp.getData();
        } else {
            return null;
        }
    }

    /**
     * Fragt die ID eines Mitglieds anhand der Mitgliedsnummer ab.
     * 
     * @param con
     *            Verbindung zum NaMi-Server
     * @param mitgliedsnummer
     *            Mitgliedsnummer des Mitglieds
     * @return Mitglieds-ID
     * @throws IOException
     *             IOException
     * @throws NamiException
     *             Fehler der bei der Anfrage an NaMi auftritt
     * 
     */
    public static int getIdByMitgliedsnummer(NamiConnector con,
            String mitgliedsnummer) throws IOException, NamiException {

        NamiSearchedValues search = new NamiSearchedValues();
        search.setMitgliedsnummer(mitgliedsnummer);

        NamiResponse<Collection<NamiMitgliedListElement>> resp = search
                .getSearchResult(con, 1, 1, 0);

        if (resp.getTotalEntries() == 0) {
            return -1;
        } else if (resp.getTotalEntries() > 1) {
            throw new NamiException(
                    "Mehr als ein Mitglied mit Mitgliedsnummer "
                            + mitgliedsnummer);
        } else {
            // genau ein Ergebnis -> Hol das erste Element aus Liste
            NamiMitgliedListElement result = resp.getData().iterator().next();
            return result.getId();
        }

    }
    
    //autor Tobias Miosczka
	
	/**
	 * @return straße
	 */
	public String getStraße() {
		return strasse;
	}
	
	/**
	 * @return ort
	 */
	public String getOrt() {
		return ort;
	}
	
	/**
	 * @return beitragsartenId
	 */
	public String getPLZ() {
		return plz;
	}
	
	/**   TODO: get this working
	 * @return formattiertes alter
	 
	public String getAlterFormatiert() {
		return geburtsDatumFormatted;
	}
	*/
	
	/**
	 * @return getGeburtsDatum, yyyy-MM-dd HH:mm:ss
	 */
	public String getGeburtsDatum() {
		return geburtsDatum;
	}

	/**
	 * @return beitragsarten
	 */
	public String getBeitragsarten() {
		return beitragsarten;
	}
	
	/**
	 * @return beitragsartenId
	 */
	public Collection<Integer> getBeitragsartenId() {
		return beitragsartenId;
	}

	/**
	 * @return statusId
	 */
	public String getStatusId() {
		return statusId;
	}

	/**
	 * @return status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return kontoverbindung
	 */
	public KontoverbindungType getKontoverbindung() {
		return kontoverbindung;
	}

	/**
	 * @returnlastUpdated
	 */
	public String getLastUpdated() {
		return lastUpdated;
	}

	/**
	 * @return version
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * @return zahlungsweise
	 */
	public String getZahlungsweise() {
		return zahlungsweise;
	}

	/**
	 * @return geschlechtId
	 */
	public String getGeschlechtId() {
		return geschlechtId;
	}
	
	/**
	 * @return konfession
	 */
	public String getKonfession() {
		return konfession;
	}

	/**
	 * @return konfessionId
	 */
	public String getKonfessionId() {
		return konfessionId;
	}

	/**
	 * @return zeitschriftenversand
	 */
	public boolean isZeitschriftenversand() {
		return zeitschriftenversand;
	}

	/**
	 * @return wiederverwendenFlag
	 */
	public boolean isWiederverwendenFlag() {
		return wiederverwendenFlag;
	}

	/**
	 * @return ersteTaetigkeit
	 */
	public String getErsteTaetigkeit() {
		return ersteTaetigkeit;
	}

	/**
	 * @return ersteTaetigkeitId
	 */
	public String getErsteTaetigkeitId() {
		return ersteTaetigkeitId;
	}

	/**
	 * @return ersteUntergliederung
	 */
	public String getErsteUntergliederung() {
		return ersteUntergliederung;
	}

	/**
	 * @return staatsangehoerigkeitId
	 */
	public String getStaatsangehoerigkeitId() {
		return staatsangehoerigkeitId;
	}

	/**
	 * @return staatsangehoerigkeit
	 */
	public String getStaatsangehoerigkeit() {
		return staatsangehoerigkeit;
	}

	/**
	 * @return staatsangehoerigkeitText
	 */
	public String getStaatsangehoerigkeitText() {
		return staatsangehoerigkeitText;
	}

	/**
	 * @return mglTypeId
	 */
	public String getMglTypeId() {
		return mglTypeId;
	}

	/**
	 * @return regionId
	 */
	public String getRegionId() {
		return regionId;
	}

	/**
	 * @return region
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * @return landId
	 */
	public String getLandId() {
		return landId;
	}

	/**
	 * @return land
	 */
	public String getLand() {
		return land;
	}

	@Override
	public int compareTo(NamiMitglied other) {
		return (getVorname() + getNachname()).compareTo(other.getVorname() + other.getNachname());
	}
	
	@Override
	public String toString(){
		return getVorname() + " " + getNachname();
	}
	//autor Tobias Miosczka end
}
