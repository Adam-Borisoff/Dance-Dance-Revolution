 package com.adam.game;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
public class SettingsWindow extends JFrame {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private String[] songs = { "Trololol", "Hampster dance"};
	private JComboBox<String> c = new JComboBox<String>();
	private String[] Duration = { "One minute", "Two minutes", "Three minutes"};
	private JComboBox<String> d = new JComboBox<String>();
	private JLabel songchoice = new JLabel("Song:   ");
	private JLabel durationchoice = new JLabel("Duration: ");
	private JButton submit = new JButton("Play  ");
	private JButton instructions = new JButton("Instructions");
	public float arrows;
	protected static URL urla = AnimatedPanel.class.getResource("/resources/Troll.wav");
	protected static URL urlb = AnimatedPanel.class.getResource("/resources/Hampster.wav");
	protected static int songarrows;
	protected static AudioInputStream audioStream;
protected static Clip audioClip;
protected static AudioFormat format;
protected static DataLine.Info info; 
	 static void play(URL audioFilePath) throws UnsupportedAudioFileException, IOException, LineUnavailableException{	
		audioStream = AudioSystem.getAudioInputStream(audioFilePath);
        format = audioStream.getFormat();
        info = new DataLine.Info(Clip.class, format);
        	audioClip = (Clip) AudioSystem.getLine(info);
	            audioClip.open(audioStream);
	            audioClip.start();  
	            audioClip.loop(Clip.LOOP_CONTINUOUSLY);
	    }
	 public SettingsWindow(){
		 setVisible(true);
		 setTitle("Dance Dance Revolution-Main window");
		 for (int i = 0; i < 2; i++)
		      c.addItem(songs[i]);
		
		 for (int i = 0; i < 3; i++)
		      d.addItem(Duration[i]);
		  submit.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		    		  if(c.getSelectedItem().equals(songs[0])){
		    			  try {
							play(urla);
							
						} catch (UnsupportedAudioFileException | IOException| LineUnavailableException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
		    		  }
		    			if(c.getSelectedItem().equals(songs[1])){
		    			
		    				try{
		    					play(urlb);
		    				} catch (UnsupportedAudioFileException | IOException| LineUnavailableException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
		    			}
		    		
		    		  if(d.getSelectedIndex()==0){
		    			  songarrows=1;
		    		  }
		    		  if(d.getSelectedIndex()==1){
		    			  songarrows=56;
		    		  }
		    		  if(d.getSelectedIndex()==2){
		    			  songarrows=84;
		    		  }
		    		  try {
		    			 
						new GameWindow();
					} catch (IOException e1) {
						
						e1.printStackTrace();
					}
		    		  setVisible(false);
				
		      }
		    });
		  instructions.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		    		 instructionsWindow();
				
		      }
		    });
		 
		 Container cp = getContentPane();
		    cp.setLayout(new FlowLayout());
		cp.add(songchoice);
		    cp.add(c);
		    cp.add(durationchoice);
		cp.add(d);
		cp.add(instructions);
		cp.add(submit);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(210,300);
	setResizable(false);
		    }
		public static void instructionsWindow(){
		
			JFrame instructions = new JFrame("INSTRUCTIONS");
			Container content = instructions.getContentPane();
			content.setLayout(new GridLayout(10, 10));
			
			Label label = new Label("------------------------INSTRUCTIONS------------------------");
			label.setFont(new Font("Serif", Font.PLAIN, 20));
			Label label2 = new Label("When an arrow gets in the red box in the bottom left hand corner");
			label2.setFont(new Font("Serif", Font.PLAIN, 15));
			Label label3 = new Label("Press the arrow on your keyboard that corresponds with the arrow in the box");
			label3.setFont(new Font("Serif", Font.PLAIN, 15));
			Label label4 = new Label("Pressing arrows before an arrow is in the box will deduct you 5 points");
			label4.setFont(new Font("Serif", Font.PLAIN, 15));
			Label label5 = new Label("A miss will deduct you 5 points");
			label5.setFont(new Font("Serif", Font.PLAIN, 15));
			Label label6 = new Label("A hit will grant you 5 points");
			label6.setFont(new Font("Serif", Font.PLAIN, 15));
			
			content.add(label);
			content.add(label2);
			content.add(label3);
			content.add(label4);
			content.add(label5);
			content.add(label6);

			instructions.setSize(500, 200);
			instructions.setResizable(false);
			instructions.setVisible(true);
		}
	
}


