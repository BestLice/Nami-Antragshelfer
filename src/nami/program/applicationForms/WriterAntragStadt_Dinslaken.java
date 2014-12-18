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
		SimpleDateFormat sdfUserInput = new SimpleDateFormat("dd.MM.yyyy");		
		SimpleDateFormat sdfData = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");		
		
		Date start = null;
		Date end = null;
		try {
			start = sdfUserInput.parse(getOptionValue(1));
			end = sdfUserInput.parse(getOptionValue(2));
		} catch (ParseException e) {
			//wrong input
			e.printStackTrace();
		}
		//event data
		Table tEvent = odtDoc.getHeader().getTableList().get(0);
		//Maﬂnahme
		tEvent.getCellByPosition(0, 0).setStringValue(tEvent.getCellByPosition(0, 0).getStringValue()+getOptionValue(0));
		//Datum von bis
		tEvent.getCellByPosition(0, 1).setStringValue(tEvent.getCellByPosition(0, 1).getStringValue()+sdfUserInput.format(start)+" - "+sdfUserInput.format(end));
		
		tEvent.getCellByPosition(1, 1).setStringValue(tEvent.getCellByPosition(1, 1).getStringValue()+getOptionValue(3));
		
		//participants data
		Table tParticipants = odtDoc.getTableList().get(0);
		for(int i=0;i<participants.size();i++){
			int row = i+1;
			
			NamiMitglied m = participants.get(i).getNamiMitglied();
			if(m!=null){
				Date birthDate = null;
				try {
					birthDate = sdfData.parse(m.getGeburtsDatum());
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
				tParticipants.getCellByPosition(6, row).setStringValue(sdfUserInput.format(birthDate));
				//Alter
				//compute age
				int diffInYearsStart = (int)Math.floor((start.getTime()-birthDate.getTime()) / (1000 * 60 * 60 * 24 * 365.242));
				int diffInYearsEnd   = (int)Math.floor((end.getTime()  -birthDate.getTime()) / (1000 * 60 * 60 * 24 * 365.242));
				if(diffInYearsEnd>diffInYearsStart){
				//participant has his/her birthday at the event
					tParticipants.getCellByPosition(7, row).setStringValue(String.valueOf(diffInYearsStart)+"-"+String.valueOf(diffInYearsEnd));
				}else{
					tParticipants.getCellByPosition(7, row).setStringValue(String.valueOf(diffInYearsStart));
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
		addOption("Art der Maﬂnahme");
		addOption("Anfangsdatum");
		addOption("Enddatum");
		addOption("Ort");
	}

}
