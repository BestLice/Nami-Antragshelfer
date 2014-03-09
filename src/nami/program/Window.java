package nami.program;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JCheckBox;
import javax.swing.BoxLayout;
import javax.swing.JProgressBar;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import nami.connector.Mitgliedstyp;

import java.util.List;



public class Window implements KeyListener, ActionListener {

	private JFrame 		frmNami;
	private JTextField 	tfFirstName,
						tfLastName,
						tfUsername;
		
	private JCheckBox 	cWoelflinge,
						cJungpfadfinder,
						cPfadfinder,
						cRover,
						cAndere,
						cMitglied,
						cSchnuppermitglied,
						cNichtmitglied;
	
	private JProgressBar progressBar;
	
	private JButton 	bAdd,
						bLogin,
						bRemove;
	
	private JMenuItem 	mntmExit,
						mntmHelp,
						mntmLicence,
						mntmAntragStadt,
						mntmAntragLand,
						mntmChangelog;
	
	private JPasswordField	pfPassword;
	
	private JList<NamiMitgliedComperable>			listFiltered,
													listParticipants;
	
	private DefaultListModel<NamiMitgliedComperable> 	dlmFiltered,
														dlmParticipants;
	
	private WindowHelp windowHelp = new WindowHelp();
	private WindowLicence windowLicence = new WindowLicence();
	private WindowChangelog windowChangelog = new WindowChangelog();
	
	private Program program;
	
	public JFrame getFrame(){
		return frmNami;
	}
	
	public JProgressBar getProgressBar(){
		return progressBar;
	}
	
	public Window(Program program) {
		this.program = program;
		initialize();
	}

	private void initialize() {
		frmNami = new JFrame();
		frmNami.setResizable(false);
		frmNami.setTitle("Nami Antragshelfer 1.2");
		frmNami.setBounds(100, 100, 600, 650);
		frmNami.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmNami.getContentPane().setLayout(new BoxLayout(frmNami.getContentPane(), BoxLayout.X_AXIS));
		
		JMenuBar menuBar = new JMenuBar();
		frmNami.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Programm");
		menuBar.add(mnNewMenu);
		
		mntmExit = new JMenuItem("Beenden");
		mntmExit.addActionListener(this);
		mnNewMenu.add(mntmExit);
		
		JMenu mAntrag = new JMenu("Antr\u00E4ge");
		menuBar.add(mAntrag);
		
		mntmAntragStadt = new JMenuItem("Antrag an Stadt");
		mntmAntragStadt.addActionListener(this);
		mAntrag.add(mntmAntragStadt);
		
		mntmAntragLand = new JMenuItem("Antrag an Land");
		mAntrag.add(mntmAntragLand);
		mntmAntragLand.addActionListener(this);
		
		JMenu mHelp = new JMenu("Hilfe");
		menuBar.add(mHelp);
		
		mntmHelp = new JMenuItem("Hilfe");
		mntmHelp.addActionListener(this);
		mHelp.add(mntmHelp);
		
		mntmLicence = new JMenuItem("Lizenz");
		mntmLicence.addActionListener(this);
		mHelp.add(mntmLicence);
		
		mntmChangelog = new JMenuItem("Changelog");
		mntmChangelog.addActionListener(this);
		mHelp.add(mntmChangelog);
		
		
		JPanel pAll = new JPanel();
		frmNami.getContentPane().add(pAll);
		pAll.setLayout(new GridLayout(1, 3, 0, 0));
		
		JPanel pOptions = new JPanel();
		pAll.add(pOptions);
		pOptions.setLayout(null);
		
		JPanel pLogin = new JPanel();
		pLogin.setBounds(10, 10, 180, 100);
		pOptions.add(pLogin);
		pLogin.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Login");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(69, 0, 46, 25);
		pLogin.add(lblNewLabel);
		
		JPanel pLoginForm = new JPanel();
		pLoginForm.setBounds(0, 25, 180, 50);
		pLogin.add(pLoginForm);
		pLoginForm.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lUsername = new JLabel("Benutzername:");
		lUsername.setFont(new Font("Tahoma", Font.PLAIN, 11));
		pLoginForm.add(lUsername);
		
		tfUsername = new JTextField();
		tfUsername.addActionListener(this);
		tfUsername.setColumns(10);
		pLoginForm.add(tfUsername);
		
		JLabel lPassword = new JLabel("Passwort:");
		lPassword.setFont(new Font("Tahoma", Font.PLAIN, 11));
		pLoginForm.add(lPassword);
		
		pfPassword = new JPasswordField();
		pfPassword.addActionListener(this);
		pLoginForm.add(pfPassword);
		
		bLogin = new JButton("Login");
		bLogin.addActionListener(this);
		bLogin.setBounds(49, 77, 89, 23);
		pLogin.add(bLogin);
		
		JPanel pFilterOptions = new JPanel();
		pFilterOptions.setBounds(10, 175, 180, 275);
		pOptions.add(pFilterOptions);
		pFilterOptions.setLayout(null);
		
		JPanel pStufe = new JPanel();
		pStufe.setBounds(0, 25, 180, 125);
		pFilterOptions.add(pStufe);
		pStufe.setLayout(new GridLayout(0, 1, 0, 0));
		
		cWoelflinge = new JCheckBox("W\u00F6lflinge");
		cWoelflinge.addActionListener(this);
		cWoelflinge.setSelected(true);
		pStufe.add(cWoelflinge);
		
		cJungpfadfinder = new JCheckBox("Jungpfadfinder");
		cJungpfadfinder.addActionListener(this);
		cJungpfadfinder.setSelected(true);
		pStufe.add(cJungpfadfinder);
		
		cPfadfinder = new JCheckBox("Pfadfinder");
		cPfadfinder.addActionListener(this);
		cPfadfinder.setSelected(true);
		pStufe.add(cPfadfinder);
		
		cRover = new JCheckBox("Rover");
		cRover.addActionListener(this);
		cRover.setSelected(true);
		pStufe.add(cRover);
		
		cAndere = new JCheckBox("Andere");
		cAndere.addActionListener(this);
		pStufe.add(cAndere);
		
		JPanel pName = new JPanel();
		pName.setBounds(0, 150, 180, 50);
		pFilterOptions.add(pName);
		pName.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lVorname = new JLabel("Vorname:");
		lVorname.setFont(new Font("Tahoma", Font.PLAIN, 11));
		pName.add(lVorname);
		
		tfFirstName = new JTextField();
		tfFirstName.addKeyListener(this);
		pName.add(tfFirstName);
		tfFirstName.setColumns(10);
		
		JLabel lNachnahme = new JLabel("Nachnahme:");
		lNachnahme.setFont(new Font("Tahoma", Font.PLAIN, 11));
		pName.add(lNachnahme);
		
		tfLastName = new JTextField();
		tfLastName.addKeyListener(this);

		pName.add(tfLastName);
		tfLastName.setColumns(10);
		
		JPanel pAktiv = new JPanel();
		pAktiv.setBounds(0, 200, 180, 75);
		pFilterOptions.add(pAktiv);
		pAktiv.setLayout(new GridLayout(0, 1, 0, 0));
		
		cMitglied = new JCheckBox("Mitglieder");
		cMitglied.addActionListener(this);
		cMitglied.setSelected(true);
		pAktiv.add(cMitglied);
		
		cSchnuppermitglied = new JCheckBox("Schnuppermitglieder");
		cSchnuppermitglied.addActionListener(this);
		cSchnuppermitglied.setSelected(true);
		pAktiv.add(cSchnuppermitglied);
		
		cNichtmitglied = new JCheckBox("Nichtmitglieder");
		cNichtmitglied.addActionListener(this);
		pAktiv.add(cNichtmitglied);
		
		JLabel lblFilter = new JLabel("Filter");
		lblFilter.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblFilter.setBounds(64, 0, 46, 25);
		pFilterOptions.add(lblFilter);
		
		JLabel lCopyRight = new JLabel("(c) Tobias Miosczka 2014");
		lCopyRight.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lCopyRight.setBounds(10, 576, 178, 14);
		pOptions.add(lCopyRight);
		
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setBounds(10, 114, 180, 14);
		pOptions.add(progressBar);
		
		JPanel pListFiltered = new JPanel();
		pAll.add(pListFiltered);
		pListFiltered.setLayout(null);
		
		JLabel lblMitglieder = new JLabel("Mitglieder");
		lblMitglieder.setHorizontalAlignment(SwingConstants.CENTER);
		lblMitglieder.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblMitglieder.setBounds(0, 10, 200, 25);
		pListFiltered.add(lblMitglieder);
		
		bAdd = new JButton("Hinzuf\u00FCgen =>");
		bAdd.addActionListener(this);
		bAdd.setBounds(10, 566, 178, 23);
		pListFiltered.add(bAdd);
		
		listFiltered = new JList<NamiMitgliedComperable>();
		listFiltered.setBounds(0, 35, 200, 525);
		
		JScrollPane spListFiltered = new JScrollPane();
		spListFiltered.setBounds(0, 35, 200, 525);
		spListFiltered.setViewportView(listFiltered);
		pListFiltered.add(spListFiltered);
		
		JPanel panel_1 = new JPanel();
		pAll.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblTeilnehmer = new JLabel("Teilnehmer");
		lblTeilnehmer.setHorizontalAlignment(SwingConstants.CENTER);
		lblTeilnehmer.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTeilnehmer.setBounds(0, 10, 200, 25);
		panel_1.add(lblTeilnehmer);
		
		bRemove = new JButton("<= Entfernen");
		bRemove.addActionListener(this);
		bRemove.setBounds(10, 566, 178, 23);
		panel_1.add(bRemove);
		
		listParticipants = new JList<NamiMitgliedComperable>();
		listParticipants.setBounds(0, 35, 200, 525);
		
		JScrollPane spListParticipants = new JScrollPane();
		spListParticipants.setBounds(0, 35, 200, 525);
		spListParticipants.setViewportView(listParticipants);
		panel_1.add(spListParticipants);
		
		dlmFiltered = new DefaultListModel<NamiMitgliedComperable>();
		dlmParticipants = new DefaultListModel<NamiMitgliedComperable>();
		listFiltered.setModel(dlmFiltered);
		listParticipants.setModel(dlmParticipants);
	}
	
	public void showPassResult(boolean right){
		if(right){
			tfUsername.setBackground(Color.GREEN);
			pfPassword.setBackground(Color.GREEN);
		}else{
			tfUsername.setBackground(Color.RED);
			pfPassword.setBackground(Color.RED);
			progressBar.setString("Falscher Name/Passwort.");
		}
	}
	
	public void updateLists(){
		List<NamiMitgliedComperable> member = program.getMember();
		List<NamiMitgliedComperable> participants = program.getParticipants();
		//listFiltered
		boolean bWlf = cWoelflinge.isSelected();
		boolean bJng = cJungpfadfinder.isSelected();
		boolean bPfd = cPfadfinder.isSelected();
		boolean bRvr = cRover.isSelected();
		boolean bAnd = cAndere.isSelected();
		
		dlmFiltered.removeAllElements();
		for(NamiMitgliedComperable d : member){
			boolean bIsWlf = "Wölfling".		equals(d.getNamiMitglied().getStufe());
			boolean bIsJng = "Jungpfadfinder".	equals(d.getNamiMitglied().getStufe());
			boolean bIsPfd = "Pfadfinder".		equals(d.getNamiMitglied().getStufe());
			boolean bIsRvr = "Rover".			equals(d.getNamiMitglied().getStufe());
			//check stufe
			if( (bIsWlf&&bWlf)||
				(bIsJng&&bJng)||
				(bIsPfd&&bPfd)||
				(bIsRvr&&bRvr)||
				(!bIsWlf&&!bIsJng&&!bIsPfd&&!bIsRvr&&bAnd)){
				//check Aktiv
				if( (cMitglied.isSelected()			&&d.getNamiMitglied().getMitgliedstyp()==Mitgliedstyp.MITGLIED)||
					(cSchnuppermitglied.isSelected()&&d.getNamiMitglied().getMitgliedstyp()==Mitgliedstyp.SCHNUPPER_MITGLIED)||
					(cNichtmitglied.isSelected()	&&d.getNamiMitglied().getMitgliedstyp()==Mitgliedstyp.NICHT_MITGLIED)){
					//check Name
					if((d.getNamiMitglied().getVorname().contains(tfFirstName.getText()))&&
						d.getNamiMitglied().getNachname().contains(tfLastName.getText())){
						dlmFiltered.addElement(d);
					}
				}
			}
		}
		//listParticipants
		dlmParticipants.removeAllElements();
		for(NamiMitgliedComperable d : participants){
			dlmParticipants.addElement(d);
		}
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source==cWoelflinge||source==cJungpfadfinder||source==cPfadfinder||source==cRover||source==cMitglied||source==cSchnuppermitglied||source==cNichtmitglied){
			updateLists();
		}
		if(source==bAdd){
			for(NamiMitgliedComperable nm : listFiltered.getSelectedValuesList()){
				program.putMemberToParticipants(nm);
			}
			updateLists();
		}
		if(source==bRemove){
			for(NamiMitgliedComperable nm : listParticipants.getSelectedValuesList()){
				program.putParticipantToMember(nm);
			}
			updateLists();
		}
		if(source==bLogin||source==tfUsername||source==pfPassword){
			String user = tfUsername.getText();
			String pass = String.copyValueOf(pfPassword.getPassword());
			program.login(user, pass);
			program.loadData(progressBar, frmNami);
		}
		if(source==mntmExit){
			System.exit(0);
		}
		if(source==mntmHelp){
			windowHelp.setVisible(true);
		}
		if(source==mntmLicence){
			windowLicence.setVisible(true);
		}
		if(source==mntmChangelog){
			windowChangelog.setVisible(true);
		}
		if(source==mntmAntragLand){
			doAntragLand();
		}
		
		if(source==mntmAntragStadt){
			doAntragStadt();
		}
	}

	@Override
	public void keyPressed(KeyEvent e){
		updateLists();
	}

	@Override
	public void keyReleased(KeyEvent e){
		updateLists();
	}

	@Override
	public void keyTyped(KeyEvent e){
		updateLists();
	}
	
	private void doAntragStadt(){
		try {
			@SuppressWarnings("unused")
			WriterAntragStadt w = new WriterAntragStadt("Stadt_Blanco.odt", "Stadt_Augefüllt_Alle.odt", program.getParticipants());				
		} catch (Exception e1) {
			//datei noch geöffnet
			progressBar.setString("Datei ist noch geöffnet.");
			e1.printStackTrace();
		}
	}
	
	private void doAntragLand(){
		int pages = (int) Math.ceil((double)(program.getParticipants().size())/(double)(WriterAntragLand.MAX_PARTICIPANTS_PER_PAGE));
		if(pages==0){
			//nothing to do in here, just return
			return;
		}
		try {
			for(int i=0;i<pages;i++){
				int from=i*WriterAntragLand.MAX_PARTICIPANTS_PER_PAGE;
				int to=((i+1)*WriterAntragLand.MAX_PARTICIPANTS_PER_PAGE);
				List<NamiMitgliedComperable> sublist;
				if(program.getParticipants().size()>to){
					sublist = program.getParticipants().subList(from, to);
				}else{
					sublist = program.getParticipants().subList(from, program.getParticipants().size());
				}
				@SuppressWarnings("unused")
				WriterAntragLand w = new WriterAntragLand("Land_Blanco.odt", "Land_Augefüllt_"+String.valueOf(i+1)+".odt", sublist);
			}				
		} catch (Exception e1) {
			//datei noch geöffnet
			progressBar.setString("Datei ist noch geöffnet.");
			e1.printStackTrace();
		}
	}
}
