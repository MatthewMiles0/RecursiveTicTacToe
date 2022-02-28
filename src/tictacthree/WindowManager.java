package tictacthree;

import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class WindowManager extends JFrame {
	private Panel masterPanel;
	private int maxR;
	private int defaultR;
	
	/**
	 * initialises a JFrame window to hold the tictactoe game
	 * @param defaultRecursionLevel the initial maximum level of recursion for the game
	 * @param maxRecursionLevel the maximum possible level of recursion for the game
	 */
	public WindowManager(int defaultRecursionLevel, int maxRecursionLevel) {
		super();
		this.defaultR = defaultRecursionLevel;
		this.maxR = maxRecursionLevel;
		this.setLayout(new GridBagLayout());
		this.setSize(new Dimension(800, 835));
		this.setMinimumSize(new Dimension(800, 835));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		masterPanel = new MasterPanel(this.defaultR, this.maxR);
		this.setTitle("TicTacToeToeToe...");
		this.getContentPane().add(masterPanel);
		this.setVisible(true);
		this.revalidate();
		this.repaint();

		//S/ystem.out.println(this.getWidth());
	}
}