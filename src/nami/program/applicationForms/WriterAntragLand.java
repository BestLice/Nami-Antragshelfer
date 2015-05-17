package nami.program.applicationForms;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;

import nami.connector.Geschlecht;
import nami.connector.namitypes.NamiMitglied;

import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.table.Table;


public class WriterAntragLand extends WriterAntrag{

	public WriterAntragLand(JFrame owner) {
		super(owner);
	}

	@Override
	public void doTheMagic(List<NamiMitglied> participants, TextDocument odtDoc){
		//association data
		Table tAssociation = odtDoc.getTableList().get(0);
		//Mitgliedsverband
		tAssociation.getCellByPosition(2, 0).setStringValue(userInput.getOption(0).toString());
		//Träger
		tAssociation.getCellByPosition(2, 1).setStringValue(userInput.getOption(1).toString());
		
		//event data
		Table tEvent = odtDoc.getTableList().get(1);
		//Datum (von-bis)
		if(!(Boolean)userInput.getOption(6).getValue()){
			tEvent.getCellByPosition(1, 0).setStringValue(userInput.getOption(2).toString()+" - "+userInput.getOption(3).toString());
		}
		//PLZ Ort
		tEvent.getCellByPosition(3, 0).setStringValue(userInput.getOption(4).toString());
		//Land
		tEvent.getCellByPosition(5, 0).setStringValue(userInput.getOption(5).toString());
		
		//participants data
		Table tParticipants = odtDoc.getTableList().get(2);
		for(int i=0; i<participants.size(); i++){
			int row = i+1;
			NamiMitglied m = participants.get(i);
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
				if(!(Boolean)userInput.getOption(6).getValue()){
					try {					
						//compute age
						Date birthDate = sdfDB.parse(m.getGeburtsDatum());
						int diffInYearsStart = (int)Math.floor((((Date) userInput.getOption(2).getValue()).getTime()-birthDate.getTime()) / (1000 * 60 * 60 * 24 * 365.242));
						int diffInYearsEnd   = (int)Math.floor((((Date) userInput.getOption(3).getValue()).getTime()-birthDate.getTime()) / (1000 * 60 * 60 * 24 * 365.242));
						if(diffInYearsEnd>diffInYearsStart){
						//participant has his/her birthday at the event
							tParticipants.getCellByPosition(5, row).setStringValue(String.valueOf(diffInYearsStart)+"-"+String.valueOf(diffInYearsEnd));
						}else{
							tParticipants.getCellByPosition(5, row).setStringValue(String.valueOf(diffInYearsStart));
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
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
		userInput.addStringOption("Mitgliedsverband", "DPSG Diözesanverband Münster");	//0
		userInput.addStringOption("Träger", "BDKJ Stadtverband Dinslaken");				//1
		userInput.addDateOption("Anfangsdatum", new Date());							//2
		userInput.addDateOption("Enddatum", new Date());								//3
		userInput.addStringOption("PLZ Ort", "46535 Dinslaken");						//4
		userInput.addStringOption("Land", "Deutschland");								//5
		userInput.addBooleanOption("Datum freilassen", false);							//6
	}
}
