package tictacthree;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Panel extends JPanel implements ActionListener {
	private boolean isRecursive;
	private Panel[][] grid = new Panel[3][3];
	private Tile myTile;
	private int recursionLevel;
	private int maxRecursion;
	protected int defaultRecursion;
	
	List<ActionListener> listenerList = new ArrayList<ActionListener>();
	
	/**
	 * A recursive panel which can hold either a grid of panels or a tile which functions as a button, storing information about who pressed it.
	 * @param recursionLevel the recursion level of this panel
	 * @param defaultRecursion the max recursion level on board generation
	 * @param maxRecursion the maximum recursion level allowed by the game
	 */
	public Panel(int recursionLevel, int defaultRecursion, int maxRecursion) {
		this.maxRecursion = maxRecursion;
		this.defaultRecursion = defaultRecursion;
		this.recursionLevel = recursionLevel;
		if (recursionLevel >= defaultRecursion) {
			setAsTile(0);
		} else {
			createNewGrid(defaultRecursion);
		}
		//S/ystem.out.println();
	}
	
	/**
	 * Sets the panel to a tile which can be clicked to set the current state
	 * @param state the initial state for the panel
	 */
	public void setAsTile(int state) {
		this.removeAll();
		this.setBackground(Color.white);
		this.setLayout(new GridLayout());
		isRecursive = false;
		
		myTile = new Tile(state);
		myTile.addActionListener(this);
		this.add(myTile);
		this.setBorder(BorderFactory.createLineBorder(Color.white, 2));
		this.revalidate();
		this.repaint();
	}
	
	/**
	 * Resets the panel with a new grid
	 * @param goalRecursion how many levels deep of recursion should the board generate to
	 */
	public void createNewGrid(int goalRecursion) {
		this.removeAll();
		this.setBackground(Color.white);
		this.setLayout(new GridLayout());
		isRecursive = true;
		
		this.grid = new Panel[3][3];
		this.setLayout(new GridLayout(3, 3));
		this.setBorder(BorderFactory.createLineBorder(Color.white, 2));
		
		//Random r = new Random();
		
		for (int i = 0; i < 9; i++) {
			// randomise recursion level
			// goalRecursion = r.nextInt(maxRecursion);
			
			//S/ystem.out.println(goalRecursion);
			Panel p = new Panel(recursionLevel+1, goalRecursion, maxRecursion);
			grid[i % 3][(int) (i / 3)] = p;
			this.add(p);
			p.addActionListener(this);
		}
		
		this.revalidate();
		this.repaint();
	}
	
	private int checkWin() {
		//System.out.println(Arrays.deepToString(grid));
		
		// check vertical win
		for (int x = 0;x < grid[0].length;x++) {
			if (grid[0][x].getState() != 0
					&& grid[0][x].getState() == grid[1][x].getState()
					&& grid[1][x].getState() == grid[2][x].getState()) {
				return grid[0][x].getState();
			}
		}
		
		// check horizontal win
		for (int y = 0;y < grid.length;y++) {
			if (grid[y][0].getState() != 0
					&& grid[y][0].getState() == grid[y][1].getState()
					&& grid[y][1].getState() == grid[y][2].getState()) {
				return grid[y][0].getState();
			}
		}
		
		// check for [0][0] diagonal win
		if (grid[0][0].getState() != 0
				&& grid[0][0].getState() == grid[1][1].getState()
				&& grid[1][1].getState() == grid[2][2].getState()) {
			return grid[0][0].getState();
		}
		
		// check for [0][2] diagonal win
		if (grid[0][2].getState() != 0
				&& grid[0][2].getState() == grid[1][1].getState()
				&& grid[1][1].getState() == grid[2][0].getState()) {
			return grid[0][2].getState();
		}
		
		// check if grid is full (draw)
		for (int y = 0;y < grid.length;y++) {
			for (int x = 0;x < grid[0].length;x++) {
				if (grid[y][x].getState() == 0) {
					return 0; // keep playing
				}
			}
		}
		
		return 3; // draw
	}
	
	/**
	 * announces the current turn to this panel and all sub-panels
	 * @param turn whose turn it is
	 */
	public void setTurn(int turn) {
		if (isRecursive) {
			for (Panel[] panelRow : grid) {
				for (Panel p : panelRow) {
					p.setTurn(turn);
				}
			}
		} else {
			this.myTile.setTurn(turn);
		}
	}
	
	/**
	 * gets the state of the board
	 * @return 0: no winner decided, 1: player 1 wins this panel, 2: player 2..., 3: draw
	 */
	public int getState() {
		if (isRecursive) {
			int[] results = new int[9];
			for (int i = 0; i < 9; i++) { // get recursive states
				results[i] = grid[(int) (i / 3)][i % 3].getState();
			}
			
			// check columns
			for (int i = 0; i < 3; i++) {
				if (results[3*i] != 0 && results[3*i] == results[(3*i) + 1] && results[(3*i)] == results[(3*i) + 2]) {
					return results[3*i];
				}
			}
			
			// check rows
			for (int i = 0; i < 3; i++) {
				if (results[i] != 0 && results[i] == results[i+3] && results[i] == results[i+6]) {
					return results[i];
				}
			}
			
			// check diagonals
			if (results[0] != 0 && results[0] == results[4] && results[4] == results[8]) {
				return results[0];
			}
			
			if (results[2] != 0 && results[2] == results[4] && results[4] == results[6]) {
				return results[2];
			}
			
			// empty
			return 0;
			
		} else {
			return myTile.getState();
		}
	}
	
	protected void actOnWinner() {
		if (this.isRecursive) {
    		int winner = this.checkWin();
            
            if (winner == 1 || winner == 2) {
            	this.setAsTile(winner);
            } else if (winner == 3) {
            	if (this.recursionLevel >= this.maxRecursion-1) {
            		this.setAsTile(0);
            	} else {
                	this.createNewGrid(this.defaultRecursion+1);
            	}
            }
    	}
	}
	
	/**
	 * notifies listeners of an action event
	 * @param event the ActionEvent to be announced
	 */
	private void notifyListeners(ActionEvent event) {
    	//S/ystem.out.println("Passing on a click!");
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
    
    @Override
    public void actionPerformed(ActionEvent e) {
    	actOnWinner();
        
        notifyListeners(e);
    }
}
