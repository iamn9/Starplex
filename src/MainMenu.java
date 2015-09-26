import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MainMenu extends JPanel {

	public static JLayeredPane jlPane = new JLayeredPane();
	RoundButton b1, b2, b3, b4;
	JLabel title, backgrnd;

	public MainMenu() {

		setLayout(null);
		JLabel backgrnd = new JLabel(new ImageIcon(getClass().getResource("bg.png")));
		backgrnd.setBounds(0, 0, 800, 600);

		/* JLabel TITLE */
		title = new JLabel("STARPLEX");
		title.setBounds(70, 30, 700, 200);
		title.setFont(Launch.n1);
		title.setForeground(Color.YELLOW);

		Handler handler = new Handler();

		b1 = new RoundButton("Enter", 110, 20, Launch.bg); // ENTER
		b1.setBounds(125, 200, 120, 120);
		b2 = new RoundButton("Help", 110, 20, Launch.bg); // INSTRUCTIONS
		b2.setBounds(275, 200, 120, 120);
		b3 = new RoundButton("Credits", 110, 18, Launch.bg); // CREDITS
		b3.setBounds(425, 200, 120, 120);
		b4 = new RoundButton("Exit", 110, 20, Launch.bg); // ENTER
		b4.setBounds(575, 200, 120, 120);

		/* Add elements to titlePanel */

		this.add(title);
		this.add(b1);
		this.add(b2);
		this.add(b3);
		this.add(b4);
		this.add(backgrnd);

		/* ActionListner */
		b1.addActionListener(handler);
		b2.addActionListener(handler);
		b3.addActionListener(handler);
		b4.addActionListener(handler);

	}

	public class Handler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			Launch.removePanel(Launch.menuPane);
			if (event.getSource() == b1) {
				hideMainMenu();
				Launch.guiFrame.setTitle("Starplex: Graph Planarity Analysis");
				Launch.guiFrame.add(jlPane, BorderLayout.CENTER);
				jlPane.setBounds(0, 0, 800, 600);
				int x = 1;
				do {
					String vertices = JOptionPane.showInputDialog(null, "Enter number of vertices:",
							"Generate vertices", JOptionPane.QUESTION_MESSAGE);
					if (vertices == null) {
						System.out.println("Cancel!");
						x = 1; // System.exit(0);
					} else if (vertices.equalsIgnoreCase("")) {
						System.out.println("No input!");
						x = 1;
					} else {
						try {
							Launch.vertices = Integer.parseInt(vertices);
							if (Launch.vertices <= 0) {
								System.out.println("Wrong input!");
							} else if (Launch.vertices > 20) {
								x = 1;
							} else {
								System.out.println("Right input");
								x = 0;
							}
						} catch (Exception error) {
							System.out.println("Must imput numbers onlys");
						}
					}
				} while (x == 1);

				GraphPanel gPane = new GraphPanel();
				Launch.properPane.setBounds(0, 500, 800, 100);
				gPane.setBounds(0, 0, 800, 500);
				jlPane.add(Launch.properPane, 1, 0);
				jlPane.add(gPane, 0, 0);
			} else if (event.getSource() == b2) {
				hideMainMenu();
				HelpPanel inst = new HelpPanel();
				Launch.showPanel(inst);

			} else if (event.getSource() == b3) {
				hideMainMenu();
				CreditsPanel cred = new CreditsPanel();
				Launch.showPanel(cred);
			} else if (event.getSource() == b4) {
				System.exit(0);
			}
		}

	}

	public void hideMainMenu() {
		this.setVisible(false);
	}
}