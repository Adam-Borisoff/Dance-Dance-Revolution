package com.adam.game;

import java.awt.Container;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class EndGame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	


	private JLabel score = new JLabel("Score: " + com.adam.game.GameWindow.score);
	public EndGame(){
		setTitle("Game Over!");
		
		  Container cp = getContentPane();
		    cp.setLayout(new FlowLayout());
	
		    cp.add(score);
		
		cp.add(score);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(200,300);
		 setVisible(true);
		 setResizable(false);

	}
	
}
	
	
