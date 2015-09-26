import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class HelpPanel extends JPanel {
	RoundButton b1;

	public HelpPanel() {
		/* titlePanel */
		setLayout(null);

		JLabel backgrnd = new JLabel(new ImageIcon(getClass().getResource("ins.png")));
		backgrnd.setBounds(0, 0, 800, 600);

		b1 = new RoundButton("Back", 100, 300, Launch.bg);
		b1.setFont(Launch.a2);
		b1.setBounds(20, 480, 70, 70);
		b1.addActionListener((ActionEvent event) -> {
			MainMenu m = new MainMenu();
			Launch.removePanel(this);
			Launch.showPanel(m);

		});

		add(b1);
		add(backgrnd);

	}
}
