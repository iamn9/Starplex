import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class StarplexProper extends JPanel {
	RoundButton goBtn, checkBtn, abortBtn;
	JLabel bottomLbl, statusLbl;
	JTextField field1;

	public StarplexProper() {

		this.setBackground(Color.YELLOW);
		setLayout(null);

		bottomLbl = new JLabel("PLANAR?");
		bottomLbl.setBounds(10, 25, 100, 30);
		bottomLbl.setFont(Launch.a1);
		bottomLbl.setForeground(Launch.bg);

		statusLbl = new JLabel("==");
		statusLbl.setBounds(100, 30, 100, 20);
		statusLbl.setFont(Launch.a2);
		statusLbl.setForeground(Launch.bg);

		checkBtn = new RoundButton("Check", 70, 10, Launch.bg);
		checkBtn.setFont(new Font("Agency FB", Font.BOLD, 19));
		checkBtn.setBounds(610, 0, 70, 70);

		abortBtn = new RoundButton("Abort", 600, 300, Launch.bg);
		abortBtn.setFont(Launch.a2);
		abortBtn.setBounds(700, 0, 70, 70);

		/* Add elements to titlePanel */
		this.add(checkBtn);
		this.add(abortBtn);
		this.add(statusLbl);
		this.add(bottomLbl);

		/* Handlers */
		Handler handler = new Handler();
		checkBtn.addActionListener(handler);
		abortBtn.addActionListener(handler);

	}

	private class Handler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == checkBtn) {
				GraphPanel.refresh();
				if (GraphPanel.planarityCheck() == true)
					statusLbl.setText("YES");
				else
					statusLbl.setText("NO");

				for (int i = 1; i <= Launch.vertices; i++) {
					System.out.println("Vertex " + i + ": " + GraphPanel.degreeV[i - 1]);
				}
				System.out.println("k5 vertices: " + GraphPanel.k5);
				System.out.println("k33 vertices: " + GraphPanel.k33 + "\n");
				System.out.println("Smallest degree: " + GraphPanel.smlDegree);
				System.out.println("Index of Smallest degree: " + GraphPanel.indexOfsmlDegree);

			} else if (event.getActionCommand() == "Abort") {
				MainMenu m = new MainMenu();
				Launch.removePanel(MainMenu.jlPane);
				Launch.showPanel(m);

				Launch.vertices = 0;
				statusLbl.setText("");
			}
		}
	}
}