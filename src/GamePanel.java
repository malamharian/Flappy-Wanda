import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class GamePanel extends JPanel implements MouseListener, KeyListener{
	
	public void initGame()
	{
		canvas = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
		canvasPixel = ((DataBufferInt)canvas.getRaster().getDataBuffer()).getData();
		requestFocus();
		loop = true;
		gravity = 0.8;
		levelIncrement = 10;
		FPS = 60;
		interval = 1000/FPS;
		scoreFontSize = 50;
		try{
			pipeImg[0] = ImageIO.read(new File("assets/images/UpperCoral.png"));
		}catch(Exception e){}
		try{
			pipeImg[1] = ImageIO.read(new File("assets/images/LowerCoral.png"));
		}catch(Exception e){}
	}
	
	public void resetGame()
	{
		lose = false;
		level = 0;
		score = 0;
		intervalLimit = 80;
		pipeInterval = intervalLimit;
		pipeSpeed = 7;
		bird = new Bird();
		background = new Background(screenWidth, screenHeight);
		pipes.clear();
	}
	
	BufferedImage canvas;
	int[] canvasPixel;
	
	private int level;
	private int score;
	private boolean loop;
	private boolean lose;
	
	private int intervalLimit;
	private int pipeInterval;
	private double gravity;
	private int levelIncrement;
	private int pipeSpeed;
	private int FPS;
	private int interval;
	private int scoreFontSize;
	
	private int screenWidth = MyFrame.screenWidth;
	private int screenHeight = MyFrame.screenHeight;
	
	private Bird bird;
	private Background background;
	private Vector<Pipe> pipes = new Vector<Pipe>();
	private BufferedImage pipeImg[] = new BufferedImage[2];
	
	private Thread gameLoop = new Thread(new Runnable() {
		
		@Override
		public void run() {
			while(loop)
			{
				if(pipeInterval >= intervalLimit)
				{
					pipeInterval = 0;
					addPipe();
				}
				pipeInterval++;
				repaint();
				deletePipe();
				checkLose();
				checkScore();
				bird.setvDown(bird.getvDown()+gravity);
				bird.setY(bird.getY()+(int)bird.getvDown());
				try {
					Thread.sleep(interval);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
	});
	
	
	
	public void checkScore()
	{
		if(pipes.size()>1)
		{
			if(pipes.elementAt(1).getX() < bird.getX()+bird.getSize() && !pipes.elementAt(0).isScored())
			{
				score++;
				pipes.elementAt(0).setScored(true);
				
				if(score % levelIncrement == 0 && score != 0)
				{
					if(level * levelIncrement != score)
					{
						level ++;
						pipeSpeed += 1;
						intervalLimit -= 4;
						background.setPanSpeed(pipeSpeed-3);
					}
				}
			}
		}
	}
	
	public void checkLose()
	{
		if( bird.getY() > screenHeight)
		{
			lose = true;
		}
		if(lose)
		{
			if(JOptionPane.showConfirmDialog(null, "Your Score: " + score + "\nPlay again?", "Game Over", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
				resetGame();
			else
				System.exit(0);
		}
	}
	
	public void deletePipe()
	{
		
		for(int i = 0; i<pipes.size(); i++)
		{
			if(pipes.elementAt(i).getX() <= 0-pipes.elementAt(i).getWidth())
			{
				pipes.removeElementAt(i);
			}
		}
	}
	
	public void drawPipe()
	{
		for(Pipe VPipe : pipes)
		{
			VPipe.render(canvasPixel);
			VPipe.move(pipeSpeed);
		}
	}
	
	public void addPipe()
	{
		int rand = (int)(Math.random()*5)+1;
		int pipeWidth = 100;
		int pipeHeight = (6-rand)*(screenHeight/8);
		int pipeX = screenWidth;
		int pipeY = screenHeight-pipeHeight;
		
		pipes.add(new Pipe(pipeX,0,pipeWidth,(rand*screenHeight/8), 0, pipeImg[0]));
		pipes.add(new Pipe(pipeX, pipeY, pipeWidth, pipeHeight, 1, pipeImg[1]));	
		
	}
	
	public void drawScore(Graphics2D g2d)
	{
		g2d.setColor(Color.black);
		g2d.setFont(new Font("Arial",Font.PLAIN, scoreFontSize));
		g2d.drawString("Score: " + score, screenWidth/2, 100);
		g2d.drawString("Level: " + level, screenWidth/2, 100+scoreFontSize);
	}
	
	public void clearCanvas()
	{
		for(int i = 0; i<canvasPixel.length; i++)
			canvasPixel[i] = 0;
	}
	
	public void doDrawing(Graphics g)
	{
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		background.render(g2d);
		clearCanvas();
		drawPipe();
		if(bird.render(canvasPixel))
			lose = true;
		
		g2d.drawImage(canvas,0,0,null);
		drawScore(g2d);
		g2d.dispose();
	}
	
	public void init()
	{
		initGame();
		resetGame();
		gameLoop.start();
		addMouseListener(this);
		addKeyListener(this);
		setFocusable(true);
		setVisible(true);
	}
	
	public GamePanel()
	{
		init();
	}
	
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
	}
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		doDrawing(g);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		bird.jump();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE)
			bird.jump();
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
