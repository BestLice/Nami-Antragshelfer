package nami.program;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import nami.connector.Geschlecht;
import nami.connector.namitypes.NamiMitglied;

import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.table.Table;

import com.ibm.icu.text.DateFormat;

public class WriterAntragLand extends WriterAntrag{
	
	
	public static final int MAX_PARTICIPANTS_PER_PAGE = 15;

	public WriterAntragLand(String input, String output, List<NamiMitgliedComperable> participants) throws Exception {
		super(input, output, participants);
	}

	@Override
	public void doTheMagic(List<NamiMitgliedComperable> participants, TextDocument odtDoc){

		//Data participants
		Table tParticipants = odtDoc.getTableList().get(2);
		for(int i=0;i<MAX_PARTICIPANTS_PER_PAGE;i++){
			int row = i+1;
			if(i==participants.size()){
				return;
			}
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
				DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.GERMAN);
				Date bd = null;
				try {
					bd = df.parse(m.getAlterFormatiert());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
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
				//*
				
			}
		}
	}
}
