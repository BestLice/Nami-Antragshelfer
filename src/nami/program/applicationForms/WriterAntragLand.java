package nami.program.applicationForms;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;

import nami.connector.Geschlecht;
import nami.connector.namitypes.NamiMitglied;
import nami.program.NamiMitgliedComperable;

import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.table.Table;


public class WriterAntragLand extends WriterAntrag{

	public WriterAntragLand(JFrame owner) {
		super(owner);
	}

	@Override
	public void doTheMagic(List<NamiMitgliedComperable> participants, TextDocument odtDoc){
		@SuppressWarnings("unused")
		SimpleDateFormat sdfUserInput = new SimpleDateFormat("dd.MM.yyyy");		
		SimpleDateFormat sdfData = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");	
		//association data
		Table tAssociation = odtDoc.getTableList().get(0);
		//Mitgliedsverband
		tAssociation.getCellByPosition(2, 0).setStringValue(getOptionValue(0));
		//Träger
		tAssociation.getCellByPosition(2, 1).setStringValue(getOptionValue(1));
		
		//event data
		Table tEvent = odtDoc.getTableList().get(1);
		//Datum (von-bis)
		tEvent.getCellByPosition(1, 0).setStringValue(getOptionValue(2)+" - "+getOptionValue(3));
		//PLZ Ort
		tEvent.getCellByPosition(3, 0).setStringValue(getOptionValue(4));
		//Land
		tEvent.getCellByPosition(5, 0).setStringValue(getOptionValue(5));
		
		//participants data
		Table tParticipants = odtDoc.getTableList().get(2);
		for(int i=0; i<participants.size(); i++){
			int row = i+1;
			NamiMitglied m = participants.get(i).getNamiMitglied();
			if(m!=null){
				//Lfd. Nr.
				
				//Kursleiter K= Kursleiter, R= Referent, L= Leiter
					
				//Name, Vorname
				tParticipants.getCellByPosition(2, row).setStringValue(m.getNachname()+", "+m.getVorname());
				//Anschrift: Straße, PLZ, Wohnort
				tParticipants.getCellByPosition(3, row).setStringValue(m.getStraße()+", "+m.getPLZ()+", "+m.getOrt());
				//w=weibl. m=männl.
				if(m.getGeschlecht()==Geschlecht.MAENNLICH){
					tParticipants.getCellByPosition(4, row).setStringValue("m");
				}
				if(m.getGeschlecht()==Geschlecht.WEIBLICH){
					tParticipants.getCellByPosition(4, row).setStringValue("w");
				}
				//Alter
				Date bd = null;
				try {
					bd = sdfData.parse(m.getGeburtsDatum());
				} catch (ParseException e) {
					e.printStackTrace();
				}			
				Calendar dob = Calendar.getInstance();  
				dob.setTime(bd);  
				Calendar today = Calendar.getInstance();  
				int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);  
				if (today.get(Calendar.MONTH) < dob.get(Calendar.MONTH)) {
				  age--;  
				} else if (today.get(Calendar.MONTH) == dob.get(Calendar.MONTH)
				    && today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)) {
				  age--;  
				}			
				tParticipants.getCellByPosition(5, row).setStringValue(String.valueOf(age));			
								
			}
		}
	}

	@Override
	public int getMaxParticipantsPerPage() {
		return 15;
	}

	@Override
	protected String getResourceFileName() {
		return "Land_Blanco.odt";
	}

	@Override
	protected void initializeOptions() {
		addOption("Mitgliedsverband", "DPSG Diözesanverband Münster");
		addOption("Träger", "BDKJ Stadtverband Dinslaken");	
		addOption("Anfangsdatum");
		addOption("Enddatum");
		addOption("PLZ Ort", "46535 Dinslaken");
		addOption("Land", "Deutschland");
	}
}
