package nami.program;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import nami.connector.NamiConnector;
import nami.connector.NamiServer;
import nami.connector.credentials.NamiCredentials;
import nami.connector.exception.NamiLoginException;

public class Program{
	
	private NamiConnector 	con;
	private NamiCredentials 	credentials;
	private List<NamiMitgliedComperable> member, participants;
	private Window window;
	
	public Program(){
		window = new Window(this);
		window.getFrame().setVisible(true);
		member = new ArrayList<NamiMitgliedComperable>();
		participants = new ArrayList<NamiMitgliedComperable>();
	}
	
	public void login(String user, String pass){
		credentials = new NamiCredentials(user, pass);
		con = new NamiConnector(NamiServer.LIVESERVER, credentials);
		try {
			con.namiLogin();
			window.showPassResult(true);
		} catch (NamiLoginException e) {
			window.showPassResult(false);
			e.printStackTrace();
		} catch (IOException e) {
			window.getProgressBar().setString("Keine Verbindung zur Nami.");
			e.printStackTrace();
		}
	}
	
	public void loadData(final JProgressBar progressOut, JFrame frame){		
		if(!con.getIsAuthenticated()){
			return;
		}
		member.clear();
		participants.clear();
		
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
              DataLoader loader=new DataLoader(con, member, window);
              loader.execute();  
            }
        });		
		Collections.sort(member);
	}
	
	public void putMemberToParticipants(int index){
		participants.add(member.get(index));
		member.remove(index);
		Collections.sort(participants);
	}
	
	public void putMemberToParticipants(NamiMitgliedComperable n){
		int index=member.indexOf(n);
		participants.add(member.get(index));
		member.remove(index);
		Collections.sort(participants);
	}
	
	public void putParticipantToMember(int index){
		member.add(participants.get(index));
		participants.remove(index);
		Collections.sort(member);
	}
	
	public void putParticipantToMember(NamiMitgliedComperable n){
		int index=participants.indexOf(n);
		member.add(participants.get(index));
		participants.remove(index);
		Collections.sort(member);
	}
	
	public List<NamiMitgliedComperable> getMember(){
		return member;
	}
	
	public List<NamiMitgliedComperable> getParticipants(){
		return participants;
	}
}
