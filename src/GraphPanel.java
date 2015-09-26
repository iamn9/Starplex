import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GraphPanel extends JPanel {

	private int radius = 50, clickCtr = 0;
	public static int k33 = 0, k5 = 0, sumOfDegrees = 0, numOfEdges = 0, smlDegree = 19, indexOfsmlDegree;

	private String text = "A", towhere;
	private List<Ellipse2D> nodes;
	private Ellipse2D dragged;
	private Point offset;

	private static boolean[][] edges;
	private Ellipse2D[] click = new Ellipse2D[3];
	public static int degreeV[];
	private static boolean isolatedVertex = false, noDegLessThanFive = false, finishK5;

	private static int crawlerCtr = 0, crawlerMemory[] = new int[20];

	public GraphPanel() {
		setBackground(Launch.bg);

		nodes = new ArrayList<>(Launch.vertices);
		edges = new boolean[Launch.vertices][Launch.vertices];
		degreeV = new int[Launch.vertices];
		// generates vertices
		for (int i = 1; i <= Launch.vertices; i++)
			nodes.add(new Ellipse2D.Float(
					(float) (400 + 200 * (Math.cos(Math.toRadians(i * (360 / Launch.vertices)))) - (radius / 2)),
					(float) (250 + 200 * (Math.sin(Math.toRadians(i * (360 / Launch.vertices)))) - (radius / 2)),
					radius, radius));

		// vertices or nodes click and release
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				for (Ellipse2D node : nodes) {
					if (node.contains(e.getPoint())) {
						dragged = node;
						// Adjust for the different between the top/left corner of the
						// node and the point it was clicked...
						offset = new Point(node.getBounds().x - e.getX(), node.getBounds().y - e.getY());
						// Highlight the clicked node
						repaint();

						// triple click to ask connection
						click[clickCtr % 3] = node;
						clickCtr++;

						if (click[0] == click[1] && click[1] == click[2]) {
							do {
								int x = 1;
								do {
									towhere = JOptionPane.showInputDialog(null, "Connect to vertex:", "Link Vertex",
											JOptionPane.QUESTION_MESSAGE);
									if (towhere.equalsIgnoreCase("")) {
										System.out.println("No input!");
										x = 1;
									} else {
										try {
											int n = Integer.parseInt(towhere);
											if (n <= 0) {
												System.out.println("Wrong input!");
											} else if (n > Launch.vertices) {
												x = 1;
											} else {
												System.out.println("Right input");
												x = 0;
											}
										} catch (Exception error) {
											System.out.println("Must input numbers only");
										}
									}
								} while (x == 1);
								if ((nodes.indexOf(click[0]) != (Integer.valueOf(towhere) - 1))) {
									edges[nodes.indexOf(click[0])][Integer.valueOf(towhere) - 1] = true;
									edges[Integer.valueOf(towhere) - 1][nodes.indexOf(click[0])] = true;
									click[0] = null;
									click[1] = null;
									click[2] = null;
									break;
								}
							} while (true);
						}
						repaint();
						break;
					}
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// Erase the "click" highlight
				if (dragged != null) {
					repaint();
				}
				dragged = null;
				offset = null;
			}
		});

		// Move's the vertices
		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if (dragged != null && offset != null) {
					// Adjust the position of the drag point to allow for the
					// click point offset
					Point to = e.getPoint();
					to.x += offset.x;
					to.y += offset.y;

					// Modify the position of the node...
					Rectangle bounds = dragged.getBounds();
					bounds.setLocation(to);
					dragged.setFrame(bounds);

					repaint();
				}

			}
		});

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();

		// Draw the connecting lines first
		// This ensures that the lines are under the nodes...
		for (int k = 0; k < Launch.vertices; k++) {
			Point from = nodes.get(k).getBounds().getLocation();
			g2d.setColor(Color.WHITE);
			// Point from = nodeA.getBounds().getLocation();
			from.x += radius / 2;
			from.y += radius / 2;
			for (Ellipse2D nodeB : nodes) {
				if (edges[k][nodes.indexOf(nodeB)] == true) {
					Point to = nodeB.getBounds().getLocation();
					to.x += radius / 2;
					to.y += radius / 2;
					g2d.setStroke(new BasicStroke(5));
					g2d.draw(new Line2D.Float(from, to));
				}
			}
		}

		// Draw the nodes...
		int k = 1;
		for (Ellipse2D node : nodes) {

			g2d.setColor(Color.YELLOW);
			g2d.fill(node);
			if (node == dragged) {
				g2d.setColor(Color.BLUE);
				g2d.draw(node);
			}

			g2d.setColor(Color.BLUE);
			FontMetrics fm = g.getFontMetrics();
			int textWidth = fm.stringWidth(text);
			int x = node.getBounds().x;
			int y = node.getBounds().y;
			int width = node.getBounds().width;
			int height = node.getBounds().height;
			g.drawString(Integer.toString(k), x + ((width - textWidth)) / 2,
					y + ((height - fm.getHeight()) / 2) + fm.getAscent());
			k++;
		}
		g2d.dispose();
	}

	public static boolean planarityCheck() {

		/*** DEFAULT PLANAR ***/
		if (Launch.vertices <= 4) {
			return true;
		}

		/* degree counter */
		for (int i = 0; i < Launch.vertices; i++) {
			for (int j = 0; j < Launch.vertices; j++) {
				if (edges[i][j] == true) {
					degreeV[i]++;
					sumOfDegrees++; // changes inserted in braces
				}
			}
			if (degreeV[i] == 0)
				isolatedVertex = true; // isolated vertex check
			if (degreeV[i] < 5)
				noDegLessThanFive = true;
			else
				noDegLessThanFive = false;

		}

		/******* Handshake Theorem - edges counter ******/
		numOfEdges = sumOfDegrees / 2;

		/******* Euler's Theorem : Corollaries *****/
		if (numOfEdges <= (3 * Launch.vertices) - 6) // corollary 1
			return true;
		if (isolatedVertex == false && noDegLessThanFive == true) // corollary 2
			return true;

		/*** theorem 2: Kuratowski Theorem k5 and k33 are not planar ******/
		for (int i = 0; i < Launch.vertices; i++) {
			if (degreeV[i] >= 4) {
				k5++;
				k33++;
				/* identify's the smallest degree greater than or equal to 4 */
				if (smlDegree > degreeV[i]) {
					System.out.println(smlDegree + " is greater than " + degreeV[i]);
					smlDegree = degreeV[i];
					indexOfsmlDegree = i;
				}
			}
			if (degreeV[i] == 3) {
				k33++;
			}
		}
//		 if (k33>=6){
//		 k33Check();
//		 }
//		 if (k5>=5){
//		 while (finishK5!=true)
//		 k5Crawler(indexOfsmlDegree);
//		 }

		return false;
	}

	public static void k5Crawler(int store) {
		int j = 0;
		for (short i = 0; i < Launch.vertices; i++) {
			if (store == i)
				continue;
			if (edges[store][i] == true) {
				if (i == crawlerMemory[j++]) {
					System.out.println("scan: " + i);
					continue;
				} else {
					crawlerMemory[crawlerCtr++] = store;
					indexOfsmlDegree = i;
					return;
				}

			}
		}
		finishK5 = true;
	}

	public static boolean k33Check() {
		System.out.println("You are in K33");
		return false;
	}

	public static void refresh() {
		k5 = 0;
		k33 = 0;
		for (int i = 0; i < Launch.vertices; i++) {
			degreeV[i] = 0;
			edges[i].equals(false);
		}
		smlDegree = 19;
		indexOfsmlDegree = 0;

	}
}