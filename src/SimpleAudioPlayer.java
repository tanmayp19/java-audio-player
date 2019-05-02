import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class SimpleAudioPlayer {

	private JFrame frame;
	JFileChooser filechooser = new JFileChooser();	
	String audioFilePath;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SimpleAudioPlayer window = new SimpleAudioPlayer();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SimpleAudioPlayer() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 480, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel fplabel = new JLabel("No File Choosen");
		fplabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fplabel.setBounds(27, 52, 400, 34);
		frame.getContentPane().add(fplabel);
		
		JButton btnNewButton = new JButton("OPEN");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
                int returnValue = filechooser.showOpenDialog(null);
												
				if(returnValue == JFileChooser.APPROVE_OPTION) {
					
					audioFilePath = filechooser.getSelectedFile().getAbsolutePath();
					
					fplabel.setText(audioFilePath);
					//play(audioFilePath);
					
				}
				
			}

		});
		btnNewButton.setBounds(10, 114, 120, 35);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Play");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				play(audioFilePath);
			}

			void play(String audioFilePath) {
				File audioFile = new File(audioFilePath);
				
		        try {
		            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
		 
		            AudioFormat format = audioStream.getFormat();
		 
		            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
		 
		            SourceDataLine audioLine = (SourceDataLine) AudioSystem.getLine(info);
		 
		            audioLine.open(format);
		 
		            audioLine.start();
		            
		            int BUFFER_SIZE = 4096;
		            
		            byte[] bytesBuffer = new byte[BUFFER_SIZE];
		            int bytesRead = -1;
		 
		            while ((bytesRead = audioStream.read(bytesBuffer)) != -1) {
		                audioLine.write(bytesBuffer, 0, bytesRead);
		            }
		             
		            audioLine.drain();
		            audioLine.close();
		            audioStream.close();
		             
		            System.out.println("Playback completed.");
		             
		        } catch (UnsupportedAudioFileException ex) {
		            System.out.println("The specified audio file is not supported.");
		            ex.printStackTrace();
		        } catch (LineUnavailableException ex) {
		            System.out.println("Audio line for playing back is unavailable.");
		            ex.printStackTrace();
		        } catch (IOException ex) {
		            System.out.println("Error playing the audio file.");
		            ex.printStackTrace();
		        }
				
			}
		});
		btnNewButton_1.setBounds(178, 114, 120, 35);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Pause");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//audioLine.pause();
			}
		});
		btnNewButton_2.setBounds(334, 114, 120, 35);
		frame.getContentPane().add(btnNewButton_2);
		
		JLabel lblNewLabel = new JLabel("Playing File:-");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(24, 24, 81, 14);
		frame.getContentPane().add(lblNewLabel);
	}

}
