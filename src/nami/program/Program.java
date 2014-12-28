package nami.program;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.SwingUtilities;

import nami.connector.NamiConnector;
import nami.connector.NamiServer;
import nami.connector.credentials.NamiCredentials;
import nami.connector.exception.NamiLoginException;

/**
 * Program
 * 
 * @author Tobias Miosczka
 *
 */
public class Program{
	
	private NamiConnector 	con;
	private NamiCredentials 	credentials;
	private List<NamiMitgliedComperable> member, participants;
	private Window window;
	
	public static void main(String[] args) {		
		@SuppressWarnings("unused")
		Program program = new Program();
	}
	
	/**
	 * Constructor for Program
	 */
	public Program(){
		window = new Window(this);
		window.getFrame().setVisible(true);
		member = new ArrayList<NamiMitgliedComperable>();
		participants = new ArrayList<NamiMitgliedComperable>();
	}
	
	/**
	 * procedure for logging into the nami database
	 * 
	 * @param user 
	 * 				valid username (membership number)
	 * @param pass 
	 * 				valid password
	 */
	public void login(String user, String pass){
		credentials = new NamiCredentials(user, pass);
		con = new NamiConnector(NamiServer.LIVESERVER, credentials);
		try {
			con.namiLogin();
			window.showPassResult(true, user);
		} catch (NamiLoginException e) {
			window.showPassResult(false, user);
		} catch (IOException e) {
			window.getProgressBar().setString("Keine Verbindung zur Nami.");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * loads data from the nami database and displays it in the main frame
	 * program must be logged in first
	 * 
	 * @param progressOut
	 * 				JProgressbar to display the progress			
	 */
	public void loadData(final Program program){		
		if(!con.getIsAuthenticated()){
			return;
		}
		member.clear();
		participants.clear();
		
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
              DataLoader loader=new DataLoader(program);
              loader.execute();  
            }
        });		
		Collections.sort(member);
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
		Collections.sort(participants);
	}
	
	/**
	 * Puts the the given element from the members list to the participants list
	 * 
	 * @param n
	 * 				object in member list
	 */
	public void putMemberToParticipants(NamiMitgliedComperable n){
		int index=member.indexOf(n);
		participants.add(member.get(index));
		member.remove(index);
		Collections.sort(participants);
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
		Collections.sort(member);
	}
	
	/**
	 * Puts the the given element from the participants list to the members list
	 * 
	 * @param n
	 * 				object in member list
	 */
	public void putParticipantToMember(NamiMitgliedComperable n){
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
	public List<NamiMitgliedComperable> getMember(){
		return member;
	}
	
	/**
	 * returns the participants list
	 * 
	 * @return
	 * 				participants list
	 */	
	public List<NamiMitgliedComperable> getParticipants(){
		return participants;
	}
	
	public Window getWindow(){
		return window;
	}

	public NamiConnector getConnection() {
		return con;
	}
}
