import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class Launch {
	static JFrame guiFrame = new JFrame();

	static Color bg = new Color(1, 17, 51);
	static Font n1 = new Font("Nero", Font.PLAIN, 120);
	static Font n2 = new Font("Nero", Font.PLAIN, 20);
	static Font a1 = new Font("Agency FB", Font.BOLD, 30);
	static Font a2 = new Font("Agency FB", Font.BOLD, 20);
	static int vertices = 5;
	static MainMenu menuPane = new MainMenu();
	static StarplexProper properPane = new StarplexProper();
	static GraphPanel gPane;

	public static void main(String[] args) {

		// APP WINDOW
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame.setSize(800, 600);
		guiFrame.setVisible(true);
		guiFrame.setResizable(false);
		guiFrame.setBackground(bg);
		guiFrame.setLocationRelativeTo(null);
		guiFrame.setTitle("Welcome to Starplex!");

		showPanel(menuPane);
		// test();
	}

	public static void showPanel(JPanel panel) {
		guiFrame.add(panel);
		guiFrame.validate();
	}

	public static void removePanel(JPanel panel) {
		guiFrame.remove(panel);
		guiFrame.validate();
	}

	public static void removePanel(JLayeredPane panel) {
		guiFrame.remove(panel);
		guiFrame.validate();
	}

	public static void test() {
		int highlightCtr = 0;
		boolean highlightV[] = new boolean[20];

		ArrayList<Integer> list1 = new ArrayList<Integer>(Arrays.asList(1, 2, 4, 2, 5, 3));
		System.out.println(list1);

		for (int i = 0; highlightCtr != 5; i++) {
			if (highlightV[list1.get(i)] == false) {
				highlightV[list1.get(i)] = true;
				highlightCtr++;
			}

		}
	}
}
