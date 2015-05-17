package nami.program.applicationForms;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;

import nami.connector.namitypes.NamiMitglied;

import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.table.Table;


public class WriterNotfallliste extends WriterAntrag{

	public WriterNotfallliste(JFrame owner) {
		super(owner);
	}

	@Override
	public void doTheMagic(List<NamiMitglied> participants, TextDocument odtDoc){		
		//participants data
		Table tParticipants = odtDoc.getTableList().get(0);
		for(int i=0; i<participants.size(); i++){
			tParticipants.appendRow();
			int row = i+1;
			NamiMitglied m = participants.get(i);
			if(m!=null){
				//Vorname
				tParticipants.getCellByPosition(0, row).setStringValue(m.getVorname());
				//Nachname
				tParticipants.getCellByPosition(1, row).setStringValue(m.getNachname());
				//Telefonnummern
				//TODO:Magic Code to seperate all phone numbers
				
				tParticipants.getCellByPosition(2, row).setStringValue(m.getTelefon1() + " \n" + m.getTelefon2() + " \n" + m.getTelefon3() + " \n" + m.getTelefax());
				
				//Stadt
				tParticipants.getCellByPosition(3, row).setStringValue(m.getOrt());
				//PLZ
				tParticipants.getCellByPosition(4, row).setStringValue(m.getPLZ());
				//Straße
				tParticipants.getCellByPosition(5, row).setStringValue(m.getStraße());
				//Geburtsdatum
				SimpleDateFormat sdfOutput = new SimpleDateFormat("dd.MM.yyyy");
				Date birthDate = null;
				try {
					birthDate = sdfDB.parse(m.getGeburtsDatum());
					tParticipants.getCellByPosition(6, row).setStringValue(sdfOutput.format(birthDate));
				} catch (ParseException e) {
					//should be dead code
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public int getMaxParticipantsPerPage() {
		return 0;
	}

	@Override
	protected String getResourceFileName() {
		return "Notfallliste.odt";
	}

	@Override
	protected void initializeOptions() {
		//nothing until now
	}
}
