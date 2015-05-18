package nami.program.subWindows;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

/**
 * Window to display the change log
 * 
 * @author Tobias Miosczka
 */
public class WindowChangelog extends JFrame {
	
	private static final long serialVersionUID = 51999655259994672L;
	private JPanel contentPane;
	
	/**
	 * Constructor.
	 * Creates the frame.
	 */
	public WindowChangelog() {
		setType(Type.UTILITY);
		setResizable(false);
		setTitle("Hilfe");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 400, 600);
		contentPane = new JPanel();
		contentPane.setToolTipText("Changelog");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JTextPane txtpnBenutzungbenutzernamemitgliedsnummer = new JTextPane();
		txtpnBenutzungbenutzernamemitgliedsnummer.setBackground(UIManager.getColor("Panel.background"));
		txtpnBenutzungbenutzernamemitgliedsnummer.setEditable(false);
		txtpnBenutzungbenutzernamemitgliedsnummer.setText(
			  "Changelog                                            \r\n"
			+ "                                                     \r\n"
			+ "0.7 \t Erste version                                 \r\n"
			+ "1.0 \t Antrag Land hinzugefügt                       \r\n"
			+ "\t Antrag Stadt hinzugefügt                          \r\n"
			+ "1.1 \t Formatierungsfehler behoben (utf-8).          \r\n"
			+ "1.3 \t Antragsvorlagen in .jar gespeichert.          \r\n"
			+ "1.4 \t Antrag an die Stadt Dinslaken aktualisiert.   \r\n"
			+ "\t Unbenutzte externe Libraries entfernt.            \r\n"
			+ "\t ->Größe der .jar um 4/5 reduziert!                \r\n"
			+ "\t Benutzereingaben zu jedem Antrag hinzugefügt.     \r\n"
			+ "1.5 \t Bug durch Nami-Version 2.1 wurde behoben.     \r\n"
			+ "\t Fehler bei Benutzereingaben und Filter behoben.   \r\n"
			+ "1.6 \t Option hinzugefügt um das Datum               \r\n"
			+ "\t eines Antrags freizulassen.                       \r\n"
			+ "\t Speicherort eines Antrags kann nun selbst bestimmt\r\n"
			+ "\t werden.                                           \r\n"
			+ "\t                                                   \r\n"
			+ "2.0 \t Funktion zur Erstellung von Notfalllisten     \r\n"
			+ "\t hinzugefügt.                                      \r\n"
			+ "\t Formatierungsfehler beseitigt.                    \r\n"
			+ "\t Bugfixes.                                         \r\n"
			+ "\t                                                   \r\n"
			+ "Bekannte Fehler:                                     \r\n"
			+ "\t Das Anfangs- und Enddatum wird bei dem Antrag     \r\n"
			+ "\t für das Land nicht übernommen.                    \r\n");
		contentPane.add(txtpnBenutzungbenutzernamemitgliedsnummer, BorderLayout.CENTER);
	}

}
