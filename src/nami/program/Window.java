package nami.program;

import javax.imageio.ImageIO;
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
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import nami.connector.Mitgliedstyp;
import nami.connector.exception.NamiLoginException;
import nami.connector.namitypes.NamiMitglied;
import nami.program.applicationForms.WriterAntragLand;
import nami.program.applicationForms.WriterAntragStadt_Dinslaken;
import nami.program.applicationForms.WriterNotfallliste;
import nami.program.subWindows.WindowChangelog;
import nami.program.subWindows.WindowHelp;
import nami.program.subWindows.WindowLicence;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;



/**
 * Programs GUI class
 * 
 * @author Tobias Miosczka
 *
 */
public class Window  implements  ActionListener, DocumentListener{

	private static final int VERSION_MAJOR = 2;
	private static final int VERSION_MINOR = 1;
	
	private JFrame 		frmNami;
	private JTextField 	tfFirstName,
						tfLastName,
						tfUsername;
	private JLabel      lbUser;
		
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
						mntmNotfallliste,
						mntmChangelog;
	
	private JPasswordField	pfPassword;
	
	private JList<NamiMitglied>			listFiltered,
										listParticipants;
	
	private DefaultListModel<NamiMitglied> 	dlmFiltered,
											dlmParticipants;
	
	private WindowHelp windowHelp = new WindowHelp();
	private WindowLicence windowLicence = new WindowLicence();
	private WindowChangelog windowChangelog = new WindowChangelog();
	
	private Program program;
	
	/**
	 * returns the GUIs JFrame
	 * 
	 * @return JFrame of the GUI
	 */
	public JFrame getFrame(){
		return frmNami;
	}
	
	/**
	 * returns the GUIs JProgressBar 
	 * 
	 * @return GUIs JProgressBar to display progress
	 */
	public JProgressBar getProgressBar(){
		return progressBar;
	}
	
	/**
	 * Constructor 
	 * 
	 * @param program 
	 * 				program
	 */
	public Window(Program program) {
		this.program = program;
		initialize();
	}

	/**
	 * initializes the GUI
	 */
	private void initialize() {
		frmNami = new JFrame();
		frmNami.setResizable(false);
		frmNami.setTitle("Nami Antragshelfer "+String.valueOf(VERSION_MAJOR)+"."+String.valueOf(VERSION_MINOR));
		frmNami.setBounds(100, 100, 600, 650);
		frmNami.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmNami.getContentPane().setLayout(new BoxLayout(frmNami.getContentPane(), BoxLayout.X_AXIS));
				
		try {
			InputStream s = Thread.currentThread().getContextClassLoader().getResourceAsStream("resources/Lilie.gif");
			Image icon = ImageIO.read(s);
			frmNami.setIconImage(icon);
		} catch (IOException e) {
			System.out.println("Icon couldn't be loaded.");
			e.printStackTrace();
		}
		
		
		
		
		JMenuBar menuBar = new JMenuBar();
		frmNami.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Programm");
		menuBar.add(mnNewMenu);
		
		mntmExit = new JMenuItem("Beenden");
		mntmExit.addActionListener(this);
		mnNewMenu.add(mntmExit);
		
		JMenu mAntrag = new JMenu("Anträge");
		menuBar.add(mAntrag);
		
		mntmAntragStadt = new JMenuItem("Antrag an Stadt");
		mntmAntragStadt.addActionListener(this);
		mAntrag.add(mntmAntragStadt);
		
		mntmAntragLand = new JMenuItem("Antrag an Land");
		mAntrag.add(mntmAntragLand);
		mntmAntragLand.addActionListener(this);
		
		mntmNotfallliste = new JMenuItem("Notfallliste");
		mAntrag.add(mntmNotfallliste);
		mntmNotfallliste.addActionListener(this);
		
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
		pLogin.setBackground(UIManager.getColor("CheckBox.light"));
		pLogin.setBounds(10, 10, 180, 100);
		pOptions.add(pLogin);
		pLogin.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Login");
		lblNewLabel.setBounds(69, 0, 46, 25);
		pLogin.add(lblNewLabel);
		
		JPanel pLoginForm = new JPanel();
		pLoginForm.setBackground(UIManager.getColor("CheckBox.light"));
		pLoginForm.setBounds(0, 25, 180, 50);
		pLogin.add(pLoginForm);
		pLoginForm.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lUsername = new JLabel("Benutzername:");
		lUsername.setBackground(UIManager.getColor("CheckBox.light"));
		pLoginForm.add(lUsername);
		
		tfUsername = new JTextField();
		tfUsername.addActionListener(this);
		tfUsername.setColumns(10);
		pLoginForm.add(tfUsername);
		
		JLabel lPassword = new JLabel("Passwort:");
		lPassword.setBackground(UIManager.getColor("CheckBox.light"));
		pLoginForm.add(lPassword);
		
		pfPassword = new JPasswordField();
		pfPassword.addActionListener(this);
		pLoginForm.add(pfPassword);
		
		bLogin = new JButton("Login");
		bLogin.addActionListener(this);
		bLogin.setBounds(49, 77, 89, 23);
		pLogin.add(bLogin);
		
		JPanel pFilterOptions = new JPanel();
		pFilterOptions.setBorder(UIManager.getBorder("Menu.border"));
		pFilterOptions.setBackground(UIManager.getColor("CheckBox.light"));
		pFilterOptions.setBounds(10, 175, 180, 275);
		pOptions.add(pFilterOptions);
		pFilterOptions.setLayout(null);
		
		JPanel pStufe = new JPanel();
		pStufe.setBounds(0, 25, 180, 125);
		pFilterOptions.add(pStufe);
		pStufe.setLayout(new GridLayout(0, 1, 0, 0));
		
		cWoelflinge = new JCheckBox("Wölflinge");
		cWoelflinge.addActionListener(this);
		cWoelflinge.setBackground(UIManager.getColor("CheckBox.light"));
		cWoelflinge.setSelected(true);
		pStufe.add(cWoelflinge);
		
		cJungpfadfinder = new JCheckBox("Jungpfadfinder");
		cJungpfadfinder.addActionListener(this);
		cJungpfadfinder.setSelected(true);
		cJungpfadfinder.setBackground(UIManager.getColor("CheckBox.light"));
		pStufe.add(cJungpfadfinder);
		
		cPfadfinder = new JCheckBox("Pfadfinder");
		cPfadfinder.addActionListener(this);
		cPfadfinder.setSelected(true);
		cPfadfinder.setBackground(UIManager.getColor("CheckBox.light"));
		pStufe.add(cPfadfinder);
		
		cRover = new JCheckBox("Rover");
		cRover.addActionListener(this);
		cRover.setSelected(true);
		cRover.setBackground(UIManager.getColor("CheckBox.light"));
		pStufe.add(cRover);
		
		cAndere = new JCheckBox("Andere");
		cAndere.addActionListener(this);
		cAndere.setBackground(UIManager.getColor("CheckBox.light"));
		pStufe.add(cAndere);
		
		JPanel pName = new JPanel();
		pName.setBounds(0, 150, 180, 50);
		pFilterOptions.add(pName);
		pName.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lVorname = new JLabel("Vorname:");
		pName.add(lVorname);
		
		tfFirstName = new JTextField();
		pName.add(tfFirstName);
		tfFirstName.setColumns(10);
		tfFirstName.getDocument().addDocumentListener(this);
		
		JLabel lNachnahme = new JLabel("Nachnahme:");
		pName.add(lNachnahme);
		
		tfLastName = new JTextField();
		pName.add(tfLastName);
		tfLastName.setColumns(10);
		tfLastName.getDocument().addDocumentListener(this);
		
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
		lblFilter.setBounds(64, 0, 46, 25);
		pFilterOptions.add(lblFilter);
		
		JLabel lCopyRight = new JLabel("(c) Tobias Miosczka 2013 - 2015");
		lCopyRight.setFont(new Font("Arial", Font.PLAIN, 11));
		lCopyRight.setBounds(10, 576, 178, 14);
		pOptions.add(lCopyRight);
		
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setBounds(10, 114, 180, 14);
		pOptions.add(progressBar);
		
		lbUser = new JLabel();
		lbUser.setBounds(10, 128, 180, 14);
		pOptions.add(lbUser);
		
		JPanel pListFiltered = new JPanel();
		pAll.add(pListFiltered);
		pListFiltered.setLayout(null);
		
		JLabel lblMitglieder = new JLabel("Mitglieder");
		lblMitglieder.setBounds(0, 10, 200, 25);
		pListFiltered.add(lblMitglieder);
		
		bAdd = new JButton("Hinzufügen =>");
		bAdd.addActionListener(this);
		bAdd.setBounds(10, 566, 178, 23);
		pListFiltered.add(bAdd);
		
		listFiltered = new JList<NamiMitglied>();
		listFiltered.setBounds(0, 35, 200, 525);
		
		JScrollPane spListFiltered = new JScrollPane();
		spListFiltered.setBounds(0, 35, 200, 525);
		spListFiltered.setViewportView(listFiltered);
		pListFiltered.add(spListFiltered);
		
		JPanel panel_1 = new JPanel();
		pAll.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblTeilnehmer = new JLabel("Teilnehmer");
		lblTeilnehmer.setBounds(0, 10, 200, 25);
		panel_1.add(lblTeilnehmer);
		
		bRemove = new JButton("<= Entfernen");
		bRemove.addActionListener(this);
		bRemove.setBounds(10, 566, 178, 23);
		panel_1.add(bRemove);
		
		listParticipants = new JList<NamiMitglied>();
		listParticipants.setBounds(0, 35, 200, 525);
		
		JScrollPane spListParticipants = new JScrollPane();
		spListParticipants.setBounds(0, 35, 200, 525);
		spListParticipants.setViewportView(listParticipants);
		panel_1.add(spListParticipants);
		
		dlmFiltered = new DefaultListModel<NamiMitglied>();
		dlmParticipants = new DefaultListModel<NamiMitglied>();
		listFiltered.setModel(dlmFiltered);
		listParticipants.setModel(dlmParticipants);
	}
	
	/**
	 * displays login results
	 * 
	 * @param right
	 * 				true if username/password is correct
	 * 				false if username/password wrong
	 * @param user 
	 */
	public void showPassResult(boolean right, String user){
		if(right){
			tfUsername.setBackground(Color.GREEN);
			pfPassword.setBackground(Color.GREEN);
			progressBar.setString("");
			lbUser.setText("Angemeldet als "+user);
		}else{
			tfUsername.setBackground(Color.RED);
			pfPassword.setBackground(Color.RED);
			progressBar.setString("Falscher Name/Passwort.");
		}
	}
	
	/**
	 * updates the member and participants lists and sorts their elements
	 */
	public void updateLists(){
		List<NamiMitglied> member = program.getMember();
		List<NamiMitglied> participants = program.getParticipants();
		Collections.sort(member);
		Collections.sort(participants);
		//listFiltered
		boolean bWlf = cWoelflinge.isSelected();
		boolean bJng = cJungpfadfinder.isSelected();
		boolean bPfd = cPfadfinder.isSelected();
		boolean bRvr = cRover.isSelected();
		boolean bAnd = cAndere.isSelected();
		
		dlmFiltered.removeAllElements();
		for(NamiMitglied d : member){
			boolean bIsWlf = "Wölfling".		equals(d.getStufe());
			boolean bIsJng = "Jungpfadfinder".	equals(d.getStufe());
			boolean bIsPfd = "Pfadfinder".		equals(d.getStufe());
			boolean bIsRvr = "Rover".			equals(d.getStufe());
			//check stufe
			if(((bIsWlf&&bWlf)||
				(bIsJng&&bJng)||
				(bIsPfd&&bPfd)||
				(bIsRvr&&bRvr)||
				(!bIsWlf&&!bIsJng&&!bIsPfd&&!bIsRvr&&bAnd))
				){
				//check Aktiv
				if( (cMitglied.isSelected()			&&d.getMitgliedstyp()==Mitgliedstyp.MITGLIED)||
					(cSchnuppermitglied.isSelected()&&d.getMitgliedstyp()==Mitgliedstyp.SCHNUPPER_MITGLIED)||
					(cNichtmitglied.isSelected()	&&d.getMitgliedstyp()==Mitgliedstyp.NICHT_MITGLIED)){
					//check Name
					if((d.getVorname().toLowerCase().contains(tfFirstName.getText().toLowerCase()))&&
						d.getNachname().toLowerCase().contains(tfLastName.getText().toLowerCase())){
						dlmFiltered.addElement(d);
					}
				}
			}
		}
		//listParticipants
		dlmParticipants.removeAllElements();
		for(NamiMitglied d : participants){
			dlmParticipants.addElement(d);
		}
	}

	/** 
	 * Listener for all elements of the GUI
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if( source==cWoelflinge||
			source==cJungpfadfinder||
			source==cPfadfinder||
			source==cRover||
			source==cAndere||
			source==cMitglied||
			source==cSchnuppermitglied||
			source==cNichtmitglied){
			updateLists();
		}
		if(source==bAdd){
			for(NamiMitglied nm : listFiltered.getSelectedValuesList()){
				program.putMemberToParticipants(nm);
			}
			updateLists();
		}
		if(source==bRemove){
			for(NamiMitglied nm : listParticipants.getSelectedValuesList()){
				program.putParticipantToMember(nm);
			}
			updateLists();
		}
		if(source==bLogin||source==tfUsername||source==pfPassword){
			this.login();
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
			try {
				new WriterAntragLand(frmNami).run("Land_Ausgefüllt.odt", program.getParticipants());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		if(source==mntmAntragStadt){
			try {
				new WriterAntragStadt_Dinslaken(frmNami).run("Stadt_Augefüllt.odt", program.getParticipants());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		if(source==mntmNotfallliste){
			try {
				new WriterNotfallliste(frmNami).run("Notfallliste.odt", program.getParticipants());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	private void login() {
		try{
			program.login(tfUsername.getText(), String.copyValueOf(pfPassword.getPassword()));
		} catch (NamiLoginException e) {
			this.showPassResult(false, "");
		} catch (IOException e) {
			this.getProgressBar().setString("Keine Verbindung zur NaMi.");
			e.printStackTrace();
		}
		program.loadData(program);
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		updateLists();
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		updateLists();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		updateLists();
	}
}
