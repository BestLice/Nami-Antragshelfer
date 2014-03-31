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
		setBounds(100, 100, 555, 255);
		contentPane = new JPanel();
		contentPane.setToolTipText("Changelog");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JTextPane txtpnBenutzungbenutzernamemitgliedsnummer = new JTextPane();
		txtpnBenutzungbenutzernamemitgliedsnummer.setBackground(UIManager.getColor("Panel.background"));
		txtpnBenutzungbenutzernamemitgliedsnummer.setEditable(false);
		txtpnBenutzungbenutzernamemitgliedsnummer.setText(
			  "Changelog                                   \r\n"
			+ "                                            \r\n"
			+ "0.7 \t Erste version                        \r\n"
			+ "1.0 \t Antrag Land hinzugef\u00FCgt         \r\n"
			+ "    \t Antrag Stadt hinzugef\u00FCgt        \r\n"
			+ "1.1 \t Formatierungsfehler behoben (utf-8)  \r\n"
			+ "1.3 \t Antragsvorlagen in .jar gespeichert  \r\n");
		contentPane.add(txtpnBenutzungbenutzernamemitgliedsnummer, BorderLayout.CENTER);
	}

}
