package com.adam.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.Timer;
public class AnimatedPanel extends JPanel {

	private static final long serialVersionUID = -7564181037729291253L;
	private static final int PREF_W = 1200;
	private static final int PREF_H = 100;
	protected static long totalarrows=0;
	protected static long endarrows=SettingsWindow.songarrows;
	protected static long arrowsonscreen=endarrows;

	protected static long arrowsleft=endarrows-totalarrows;
	 public URL urla = AnimatedPanel.class.getResource("/resources/arrow_right.gif");
	  public URL urlb = AnimatedPanel.class.getResource("/resources/arrow_left.gif");
	 public URL urlc = AnimatedPanel.class.getResource("/resources/arrow_up.gif");
	  public URL urld = AnimatedPanel.class.getResource("/resources/arrow_down.gif");
	  private BufferedImage rightarrow;
	  private BufferedImage leftarrow;
	  private BufferedImage uparrow;
	  private BufferedImage downarrow;
	private static final int TIMER_DELAY = 6;
	static LinkedList<BufferedImage> images = new LinkedList<BufferedImage>();

	static LinkedList<Integer> xLocs = new LinkedList<Integer>();
	private LinkedList<Integer> arrows = new LinkedList<Integer>();
	private int moveSpeed = 3;
	private long totalTime = 0, nextNewTime = 1;
	private static Random r;
	protected static boolean remove;
	protected static javax.swing.event.EventListenerList listenerList = new javax.swing.event.EventListenerList();
	protected Timer t;
	public AnimatedPanel() throws IOException {
		r = new Random();
		buildImages();
	
		t = new Timer(TIMER_DELAY, new ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent e) {
				int index = 0;
				 arrowsleft=endarrows-totalarrows;
				
				
			 remove = false;
				totalTime += TIMER_DELAY;
				if (totalTime >= nextNewTime&&arrowsonscreen>0){
					
						addArrow();
					
				}
				else if(com.adam.game.AnimatedPanel.totalarrows==com.adam.game.AnimatedPanel.endarrows)		
		{
			
					com.adam.game.GameWindow.mainWindow.setIcon((Icon) com.adam.game.GameWindow.death);
					com.adam.game.GameWindow.frame.repaint();
					com.adam.game.GameWindow.scheduledExecutorService =
					Executors.newScheduledThreadPool(1);

					com.adam.game.GameWindow.scheduledFuture =
							com.adam.game.GameWindow.scheduledExecutorService.schedule(new Runnable() {
						public void run() {
							com.adam.game.GameWindow.mainWindow.setIcon((Icon) com.adam.game.GameWindow.dead);
						}
					},
					2,
					TimeUnit.SECONDS);

					com.adam.game.GameWindow.scheduledExecutorService.shutdown();
			
					com.adam.game.GameWindow.arrows.stopAnimation();
			new EndGame();
			t.stop();
			com.adam.game.GameWindow.frame.setVisible(false);
			com.adam.game.SettingsWindow.audioClip.stop();
		
		}
				for(int imgX : xLocs)
				{
					xLocs.set(index, imgX - moveSpeed);
					if (imgX - moveSpeed <= 0)
					{
						remove = true;
					}
					index++;
				}
				if (remove)
				{
					
					xLocs.remove(0);
					images.remove(0);
					arrows.remove(0);
					fireMissedArrowEvent();
					remove = false;
				}
				repaint();
			};
		});
		t.start();
	}
	private void buildImages() throws IOException
	  {
	
		 rightarrow=ImageIO.read(urla);
		     leftarrow = ImageIO.read(urlb);
		      uparrow = ImageIO.read(urlc);
		      downarrow = ImageIO.read(urld);
	  }
	private void addArrow() {
		int arrow = r.nextInt(4);
		
		nextNewTime = totalTime + r.nextInt(1000) + 500 + (500 / moveSpeed);
		
			arrowsleft=endarrows-totalarrows;
			
			
			if(arrowsonscreen>0){
				arrowsonscreen--;
		switch(arrow){
		case 0:
			images.add(rightarrow);
			arrows.add(arrow);
			xLocs.add(PREF_W);
			break;
		case 1:
			images.add(leftarrow);
			arrows.add(arrow);
			xLocs.add(PREF_W);
			break;
		case 2:
			images.add(downarrow);
			arrows.add(arrow);
			xLocs.add(PREF_W);
			break;
		case 3:
			images.add(uparrow);
			arrows.add(arrow);
			xLocs.add(PREF_W);
			break;
		}
			}
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(PREF_W, PREF_H);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.LIGHT_GRAY);
		
		
		int index = 0;
		for(BufferedImage image : images)
		{
			if (image != null) 
				g.drawImage(image, xLocs.get(index), 10, 65, 65, this);
			index++;
		}
	}	
	
	public boolean checkArrow(int arrow)
	{
		if (arrows.get(0) == arrow && isCentered())
		{
			xLocs.remove(0);
			images.remove(0);
			arrows.remove(0);
			
			return true;
		}
		return false;
	}
	
	private boolean isCentered()
	{
		return xLocs.get(0) < 80;
	}
	
	
	
	public void addMissedArrowListener(MissedArrowListener mal)
	{
		listenerList.add(MissedArrowListener.class, mal);
	}
	
	public void removeMissedArrowListener(MissedArrowListener mal)
	{
		
	}
	
	public void stopAnimation()
	{
		t.stop();
	}
	
	static void fireMissedArrowEvent()
	{
		for(MissedArrowListener listener : listenerList.getListeners(MissedArrowListener.class))
			listener.arrowMissed();

	}
	public static void main(String[] args) throws IOException
	{

		new GameWindow();

		
	}
}
