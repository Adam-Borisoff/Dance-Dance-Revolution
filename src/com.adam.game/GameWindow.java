package com.adam.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameWindow
  extends JPanel
  implements MissedArrowListener
{
  public URL url = GameWindow.class.getResource("/resources/forward.gif");
  public URL url1 = GameWindow.class.getResource("/resources/backward.gif");
  public URL url2 = GameWindow.class.getResource("/resources/left.gif");
  public URL url3 = GameWindow.class.getResource("/resources/right.gif");
  public URL url4 = GameWindow.class.getResource("/resources/ready.gif");
  public URL url5 = GameWindow.class.getResource("/resources/basicmiss.gif");
  public URL url6 = GameWindow.class.getResource("/resources/critmiss.gif");
  public URL url7 = GameWindow.class.getResource("/resources/death.gif");
  public URL url8 = GameWindow.class.getResource("/resources/dead.gif");
  public static URL url9 = GameWindow.class.getResource("/resources/background..jpg");
  private static final long serialVersionUID = 8121004801717571097L;
  static JLabel mainWindow;
  private static JLabel arrowBox;
  private static ImageIcon forward;
  private static ImageIcon backward;
  private static ImageIcon left;
  private static ImageIcon right;
  private static ImageIcon ready;
  private static ImageIcon basicmiss;
  private static ImageIcon critmiss;
 
  static ImageIcon death;
  static ImageIcon dead;
  protected static JFrame frame;
  protected static ScheduledFuture<?> scheduledFuture;
  protected static ScheduledFuture<?> scheduledFutureArrowMover;
  protected static ScheduledExecutorService scheduledExecutorService;
  protected static ScheduledExecutorService scheduledExecutorServiceArrowMover;
  protected static AnimatedPanel arrows;
  public static int x = 1200;
  protected static long score = 0;
 
  public GameWindow()
    throws IOException
  {
   buildImages();
    
    
    setSize(500, 300);
    mainWindow = new JLabel(ready);

    arrows = new AnimatedPanel();
    arrows.addMissedArrowListener(this);
    arrows.setSize(1200, 100);
    arrows.setLayout(new FlowLayout(0));
    arrowBox = new JLabel("");
    arrowBox.setBounds(0, 0, 80, 80);
    
    arrowBox.setPreferredSize(new Dimension(80, 80));
    arrowBox.setBorder(BorderFactory.createLineBorder(Color.red));
    arrows.add(arrowBox);
    setSize(1200, 800);
    setLayout(new BorderLayout());
    add(mainWindow, "Center");
    add(arrows, "South");
   
    frame = new JFrame("Dance Dance Revolution");
    frame.add(this);
    frame.addKeyListener(new KeyListener()
    {
      public void keyPressed(KeyEvent e)
      {
        GameWindow.processKey(e.getKeyCode());
      }
      
      public void keyReleased(KeyEvent e) {}
      
      public void keyTyped(KeyEvent e) {}
    });
    frame.setSize(1200, 800);
    frame.setDefaultCloseOperation(3);
    frame.setVisible(true);
    frame.setResizable(false);
  }
  
  private void buildImages()
  {
    forward = new ImageIcon(url);
    backward = new ImageIcon(url1);
    left = new ImageIcon(url2);
    right = new ImageIcon(url3);
    ready = new ImageIcon(url4);
    basicmiss = new ImageIcon(url5);
    critmiss = new ImageIcon(url6);
    death = new ImageIcon(url7);
    dead = new ImageIcon(url8);

  }
  
  
  
  public static void processKey(int key)
  {
    try
    {
      scheduledFuture.cancel(true);
    }
    catch (Exception localException) {}
    switch (key)
    {
    case 37: 
      if (arrows.checkArrow(1)) {
        handleHit(left);
      } else {
        handleMiss(basicmiss);
      }
      break;
    case 38: 
      if (arrows.checkArrow(3)) {
        handleHit(forward);
      } else {
        handleMiss(basicmiss);
      }
      break;
    case 39: 
      if (arrows.checkArrow(0)) {
        handleHit(right);
      } else {
        handleMiss(basicmiss);
      }
      break;
    case 40: 
      if (arrows.checkArrow(2)) {
        handleHit(backward);
      } else {
        handleMiss(basicmiss);
      }
      break;
    }
  }
  
  private static void handleMiss(ImageIcon missType)
  {
    if (AnimatedPanel.totalarrows!= AnimatedPanel.endarrows)
    {
      if (missType == critmiss) {
        AnimatedPanel.totalarrows+= 1;
      }
      score -= 5L;
      
      mainWindow.setIcon(missType);
      frame.repaint();
      scheduledExecutorService = 
        Executors.newScheduledThreadPool(1);
      
      scheduledFuture = 
        scheduledExecutorService.schedule(new Runnable()
        {
          public void run()
          {
            GameWindow.mainWindow.setIcon(GameWindow.ready);
          }
        }, 1L, 
        TimeUnit.SECONDS);
      
      scheduledExecutorService.shutdown();
    }
  }
  
  private static void handleHit(ImageIcon moveType)
  {
    if (AnimatedPanel.totalarrows != AnimatedPanel.endarrows)
    {
      score += 5L;
      AnimatedPanel.totalarrows += 1;
      
      mainWindow.setIcon(moveType);
    
	
      frame.repaint();
      scheduledExecutorService = 
        Executors.newScheduledThreadPool(1);
      
      scheduledFuture = 
        scheduledExecutorService.schedule(new Runnable()
        {
          public void run()
          {
            GameWindow.mainWindow.setIcon(GameWindow.ready);
          }
        }, 1L, 
        TimeUnit.SECONDS);
      
      scheduledExecutorService.shutdown();
    }
  }
  
  public void arrowMissed()
  {
    try
    {
      scheduledFuture.cancel(true);
    }
    catch (Exception localException) {}
    handleMiss(critmiss);
  }
}
