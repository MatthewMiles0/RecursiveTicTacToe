package tictacthree;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class MasterPanel extends Panel {
	private int currentTurn;
	private JButton newGameButton;
	
	private static Color p1Colour = new Color(0, 142, 170);
	private static Color p2Colour = new Color(213, 0, 50);
	
	public MasterPanel(int defaultRecursionLevel, int maxRecursionLevel) {
		super(0, defaultRecursionLevel, maxRecursionLevel);
		this.currentTurn = 1;
		setTurn(currentTurn);
	}
	
	@Override
	public Dimension getPreferredSize() {
		int min = this.getParent().getHeight() > this.getParent().getWidth() ? this.getParent().getWidth() : this.getParent().getHeight();
		return new Dimension(min, min);
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == newGameButton) {
			this.createNewGrid(defaultRecursion);
			this.setTurn(this.currentTurn);
		} else {
			actOnWinner();
			newTurn();
			
			if (this.getState() != 0) {
				this.removeAll();
				this.setLayout(new BorderLayout());
				this.setBackground(this.getState() == 1 ? p1Colour : p2Colour);
				
				JLabel winnerText = new JLabel("Player "+this.getState()+" wins!", SwingConstants.CENTER);
				winnerText.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 100));
				
				newGameButton = new JButton();
				newGameButton.setText("New Game");
				newGameButton.setForeground(Color.white);
				newGameButton.setBackground(this.getBackground().darker());
				newGameButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
				newGameButton.addActionListener(this);
				
				this.add(newGameButton, BorderLayout.SOUTH);
				this.add(winnerText, BorderLayout.CENTER);
			}
		}
		
	}
	
	/**
	 * swaps whose turn it is
	 */
	public void newTurn() {
		currentTurn = currentTurn == 1 ? 2 : 1;
		setTurn(currentTurn);
	}
}