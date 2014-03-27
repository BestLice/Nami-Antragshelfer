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
		txtpnBenutzungbenutzernamemitgliedsnummer.setText("Changelog\r\n\r\n0.7 \tErste version\r\n1.0 \tAntrag Land hinzugef\u00FCgt\r\n\tAntrag Stadt hinzugef\u00FCgt\r\n1.1\tFormatierungsfehler behoben (utf-8)");
		contentPane.add(txtpnBenutzungbenutzernamemitgliedsnummer, BorderLayout.CENTER);
	}

}
