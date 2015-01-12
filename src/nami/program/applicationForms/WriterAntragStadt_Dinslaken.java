package nami.program.applicationForms;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;

import nami.connector.namitypes.NamiMitglied;
import nami.program.NamiMitgliedComperable;

import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.table.Table;


public class WriterAntragStadt_Dinslaken extends WriterAntrag {

	public WriterAntragStadt_Dinslaken(JFrame owner) {
		super(owner);
	}

	@Override
	public void doTheMagic(List<NamiMitgliedComperable> participants, TextDocument odtDoc){	
		//collect data	
		SimpleDateFormat sdfOutput = new SimpleDateFormat("dd.MM.yyyy");
		
		//event data
		Table tEvent = odtDoc.getHeader().getTableList().get(0);
		//Maﬂnahme
		tEvent.getCellByPosition(0, 0).setStringValue(userInput.getOption(0).toString());
		//Datum von bis
		if(!(Boolean)userInput.getOption(4).getValue()){
			tEvent.getCellByPosition(0, 1).setStringValue(userInput.getOption(1)+" - "+userInput.getOption(2));
			tEvent.getCellByPosition(1, 1).setStringValue(userInput.getOption(3).toString());
		}
		
		//participants data
		Table tParticipants = odtDoc.getTableList().get(0);
		for(int i=0;i<participants.size();i++){
			int row = i+1;
			
			NamiMitglied m = participants.get(i).getNamiMitglied();
			if(m!=null){
				Date birthDate = null;
				try {
					birthDate = sdfDB.parse(m.getGeburtsDatum());
				} catch (ParseException e) {
					//should be dead code
					e.printStackTrace();
				}
				
				//Nr.
				//tParticipants.getCellByPosition(0, row).setStringValue(String.valueOf(row));
				//Nachnahme
				tParticipants.getCellByPosition(1, row).setStringValue(m.getNachname());
				//Vornahme
				tParticipants.getCellByPosition(2, row).setStringValue(m.getVorname());
				//Strasse
				tParticipants.getCellByPosition(3, row).setStringValue(m.getStraﬂe());
				//PLZ
				tParticipants.getCellByPosition(4, row).setStringValue(m.getPLZ());
				//Ort
				tParticipants.getCellByPosition(5, row).setStringValue(m.getOrt());
				//Geburtsdatum
				if(!(Boolean)userInput.getOption(4).getValue()){
					tParticipants.getCellByPosition(6, row).setStringValue(sdfOutput.format(birthDate));
					//Alter
					//compute age
					int diffInYearsStart = (int)Math.floor((((Date) userInput.getOption(1).getValue()).getTime()-birthDate.getTime()) / (1000 * 60 * 60 * 24 * 365.242));
					int diffInYearsEnd   = (int)Math.floor((((Date) userInput.getOption(2).getValue()).getTime()-birthDate.getTime()) / (1000 * 60 * 60 * 24 * 365.242));
					if(diffInYearsEnd>diffInYearsStart){
					//participant has his/her birthday at the event
						tParticipants.getCellByPosition(7, row).setStringValue(String.valueOf(diffInYearsStart)+"-"+String.valueOf(diffInYearsEnd));
					}else{
						tParticipants.getCellByPosition(7, row).setStringValue(String.valueOf(diffInYearsStart));
					}
				}
				if(i<participants.size()-1){
					//add row if element isnt the last
					tParticipants.appendRow();
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
		return "Stadt_Dinslaken_Blanco.odt";
	}

	@Override
	protected void initializeOptions() {
		// TODO Auto-generated method stub
		userInput.addStringOption("Art der Maﬂnahme", "");		//0
		userInput.addDateOption("Anfangsdatum", new Date());	//1
		userInput.addDateOption("Enddatum", new Date());		//2
		userInput.addStringOption("Ort", "");					//3
		userInput.addBooleanOption("Datum freilassen", false);	//4
	}

}
