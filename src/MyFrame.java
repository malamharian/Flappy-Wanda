import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class MyFrame extends JFrame {
	
	public static int screenWidth = 1280;
	public static int screenHeight = 720;
	
	void initFrame()
	{
		setTitle("Flappy Wanda");
		setResizable(false);
		setSize(screenWidth, screenHeight);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		JOptionPane.showMessageDialog(null,
			"Welcome to Flappy Wanda\n"
			+ "Help Wanda to navigate across the sea without crashing into a pipe\n"
			+ "Press space or mouse click to jump\n"
			+ "Good luck!"
		);
		add(new GamePanel());
		setVisible(true);
	}
	
	public MyFrame() {
		
		initFrame();
		
	}
	
	public static void main(String[] args)
	{
		new MyFrame();
	}
}
