import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class RoundButton extends JButton {
	public RoundButton(String label, int radius, int ptsize, Color back) {
		super(label);
		Dimension size = new Dimension(radius, radius);
		setPreferredSize(size);

		// LABEL ON BUTTON
		setBackground(back);
		setFont(new Font("Nero", Font.BOLD, ptsize));
		setFocusPainted(false);

		// This allows us to paint a round background.
		setContentAreaFilled(false);
	}

	// Paint the round background and label.
	protected void paintComponent(Graphics g) {
		if (getModel().isArmed()) {
			// You might want to make the highlight color
			// a property of the RoundButton class.
			g.setColor(getBackground()); // ButtonFill when Pressed
			setForeground(Color.RED); // Font Color when Pressed
		} else {
			g.setColor(getBackground()); // defaultButtonFill
			setForeground(Color.YELLOW); // font color
		}
		g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);

		// This call will paint the label and the
		// focus rectangle.
		super.paintComponent(g);
	}

	// Paint the border of the button using a simple stroke.
	protected void paintBorder(Graphics g) {
		g.setColor(Color.YELLOW); // border color
		g.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
	}

	// Hit detection.
	Shape shape;

	public boolean contains(int x, int y) {
		// If the button has changed size,
		// make a new shape object.
		if (shape == null || !shape.getBounds().equals(getBounds())) {
			shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
		}
		return shape.contains(x, y);
	}
}