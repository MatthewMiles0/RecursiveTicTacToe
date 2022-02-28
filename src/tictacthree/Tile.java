package tictacthree;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Tile extends JPanel implements MouseListener {
	List<ActionListener> listenerList = new ArrayList<ActionListener>();
	private int value;
	private int currentTurn;
	
	private static Color blankColour = new Color(193, 198, 200);
	private static Color p1Colour = new Color(0, 142, 170);
	private static Color p2Colour = new Color(213, 0, 50);
	
	/**
	 * A tile which functions as a button, holding information about who has won the tile
	 * @param state the initial state for the tile
	 */
	public Tile(int state) {
		this.value = state;
		this.addMouseListener(this);
		this.currentTurn = 0;
		this.updateBackground();
		this.setVisible(true);
		this.repaint();
	}
	
	/**
	 * returns the state of the tile
	 * @return the player who has selected the tile. 0: none, 1: player 1, 2: player 2
	 */
	public int getState() {
		return value;
	}
	
	/**
	 * set the state of the tile
	 * @param state the state to be set
	 */
	public void setState(int state) {
		this.value = state;
		this.repaint();
		this.revalidate();
	}
	
	/**
	 * tells the tile whose turn it is so that it is filled with the correct colour when selected
	 * @param turn whose turn it is
	 */
	public void setTurn(int turn) {
		this.currentTurn = turn;
	}
	
	private void updateBackground() {
		this.setBackground(this.value == 0 ? blankColour : this.value == 1 ? p1Colour : p2Colour);
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(this.getParent().getWidth(), this.getParent().getHeight());
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		this.setBackground(this.getBackground().darker());
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		this.updateBackground();
	}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		//S/ystem.out.println("I've been clicked! "+this.value);
		if (this.value == 0)
			fillPanel();
	}
	
	private void fillPanel() {
		//S/ystem.out.println(currentTurn);
		if (currentTurn == 0) {
			return;
		}
		
    	//S/ystem.out.println("I have been clicked!");
    	this.setBackground(this.currentTurn == 1 ? p1Colour : p2Colour);
    	
    	this.value = currentTurn;
        ActionEvent event = new ActionEvent((Object) this, ActionEvent.ACTION_PERFORMED, "new turn");
        for (ActionListener action : listenerList) {
            action.actionPerformed(event);
        }
    }
    
	/**
	 * adds an action listener
	 * @param al the ActionListener object to be called
	 */
    public void addActionListener(ActionListener al) {
        listenerList.add(al);
    }

    /**
	 * removes an action listener from the listener list
	 * @param al the ActionListener object to be removed
	 */
    public void removeActionListener(ActionListener al) {
        listenerList.remove(al);
    }
}
