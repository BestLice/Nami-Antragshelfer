package nami.program;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nami.connector.NamiConnector;
import nami.connector.NamiServer;
import nami.connector.credentials.NamiCredentials;
import nami.connector.exception.NamiLoginException;
import nami.connector.namitypes.NamiMitglied;
import nami.program.NamiDataLoader.NamiDataLoaderHandler;

/**
 * Program
 * 
 * @author Tobias Miosczka
 *
 */
public class Program implements NamiDataLoaderHandler{
	
	private NamiConnector 	connector;
	private List<NamiMitglied> member, participants;
	private Window window;
	
	public static void main(String[] args) {		
		System.setProperty("file.encoding","UTF-8");
		java.lang.reflect.Field charset;
		try {
			charset = Charset.class.getDeclaredField("defaultCharset");
			charset.setAccessible(true);
			charset.set(null,null);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		@SuppressWarnings("unused")
		Program program = new Program();
	}
	
	/**
	 * Constructor for Program
	 */
	public Program(){
		window = new Window(this);
		window.getFrame().setVisible(true);
		member = new ArrayList<NamiMitglied>();
		participants = new ArrayList<NamiMitglied>();
	}
	
	/**
	 * procedure for logging into the nami database
	 * 
	 * @param user 
	 * 				valid username (membership number)
	 * @param pass 
	 * 				valid password
	 * @throws IOException 
	 * @throws NamiLoginException 
	 */
	public void login(String user, String pass) throws NamiLoginException, IOException{
		NamiCredentials credentials = new NamiCredentials(user, pass);
		connector = new NamiConnector(NamiServer.LIVESERVER, credentials);
		connector.namiLogin();
	}
	
	
	/**
	 * loads data from the nami database and displays it in the main frame
	 * program must be logged in first
	 * 
	 * @param progressOut
	 * 				JProgressbar to display the progress			
	 */
	public void loadData(final Program program){
		if(!connector.getIsAuthenticated()){
			return;
		}
		member.clear();
		participants.clear();
				
		NamiDataLoader dl = new NamiDataLoader(connector, this);
		dl.start();
	}
	
	
	/**
	 * Puts the element in the members list with the given index to the participants list
	 * 
	 * @param index
	 * 				index in member list
	 */
	public void putMemberToParticipants(int index){
		participants.add(member.get(index));
		member.remove(index);
	}
	
	/**
	 * Puts the the given element from the members list to the participants list
	 * 
	 * @param n
	 * 				object in member list
	 */
	public void putMemberToParticipants(NamiMitglied n){
		int index=member.indexOf(n);
		participants.add(member.get(index));
		member.remove(index);
	}
	
	/**
	 * Puts the element in the participants list with the given index to the member list
	 * 
	 * @param index
	 * 				index in participants list
	 */	
	public void putParticipantToMember(int index){
		member.add(participants.get(index));
		participants.remove(index);
	}
	
	/**
	 * Puts the the given element from the participants list to the members list
	 * 
	 * @param n
	 * 				object in member list
	 */
	public void putParticipantToMember(NamiMitglied n){
		int index=participants.indexOf(n);
		member.add(participants.get(index));
		participants.remove(index);
		Collections.sort(member);
	}
	
	/**
	 * returns the member list
	 * 
	 * @return
	 * 				member list
	 */
	public List<NamiMitglied> getMember(){
		return member;
	}
	
	/**
	 * returns the participants list
	 * 
	 * @return
	 * 				participants list
	 */	
	public List<NamiMitglied> getParticipants(){
		return participants;
	}

	@Override
	public void update(int percent, NamiMitglied e) {
		window.loaderUpdate(percent, "Lädt "+ e.getVorname() + " " + e.getNachname() + " " + percent + "%");
        member.add(e);	
	}

	@Override
	public void done(long time) {
		window.loaderUpdate(100, "Fertig nach " + time / 1000 + " Sekunden.");
	}
}
