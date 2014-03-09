package nami.program;

import java.util.Date;
import java.util.List;

import nami.connector.namitypes.NamiMitglied;

import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.table.Table;


public class WriterAntragStadt extends WriterAntrag {
	
	public WriterAntragStadt(String input, String output, List<NamiMitgliedComperable> participants) throws Exception {
		super(input, output, participants);
	}

	@Override
	public void doTheMagic(List<NamiMitgliedComperable> participants, TextDocument odtDoc){		
		//participants data
		Table tParticipants = odtDoc.getTableList().get(0);
		for(int i=0;i<participants.size();i++){
			int row = i+1;
			
			NamiMitglied m = participants.get(i).getNamiMitglied();
			if(m!=null){
				//Nr.
				
				//Nachnahme
				tParticipants.getCellByPosition(1, row).setStringValue(m.getNachname());
				//Vornahme
				tParticipants.getCellByPosition(2, row).setStringValue(m.getVorname());
				//Strasse
				tParticipants.getCellByPosition(3, row).setStringValue(m.getStraße());
				//PLZ
				tParticipants.getCellByPosition(4, row).setStringValue(m.getPLZ());
				//Ort
				tParticipants.getCellByPosition(5, row).setStringValue(m.getOrt());
				//Geburtsdatum
				tParticipants.getCellByPosition(6, row).setStringValue(m.getAlterFormatiert());
				if(i<participants.size()-1){
					//add row if element isnt the last
					tParticipants.appendRow();
				}
			}
		}
	}

}
