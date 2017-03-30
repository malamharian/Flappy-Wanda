import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Bird extends Object{
	
	private int size;
	private int jumpFactor;
	private double vDown;
	private int angle;
	private int turnSpeed;
	private BufferedImage model;
	private BufferedImage scaled;
	private int modelPixel[][];
	
	public BufferedImage getModel() {
		return model;
	}
	public void setModel(BufferedImage model) {
		this.model = model;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public double getvDown() {
		return vDown;
	}
	public void setvDown(double vDown) {
		this.vDown = vDown;
	}
	
	public int getJumpFactor() {
		return jumpFactor;
	}
	public void setJumpFactor(int jumpFactor) {
		this.jumpFactor = jumpFactor;
	}
	
	public void jump()
	{
		if(y > size)
		{
			vDown = jumpFactor;
			angle = -50;
		}
	}
	
	public Bird()
	{
		x = 100;
		y = 0;
		size = 50;
		width = height = size;
		vDown = 1;
		turnSpeed = 3;
		jumpFactor = -12;
		try {
			model = ImageIO.read(new File("assets/images/wanda.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public boolean render(int canvasPixel[])
	{
		boolean collide = false;
		scaled = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D)scaled.createGraphics();
		g.rotate(Math.toRadians(angle), size/2, size/2);
		g.drawImage(model, 0, 0, size, size, null);
		g.dispose();
		
		try{
			modelPixel = Converter.convertTo2DUsingGetRGB(scaled);
		}catch(Exception e)
		{
			
		}
		
		for(int i = 0; i<modelPixel.length; i++)
		{
			for(int j = 0; j<modelPixel[i].length; j++)
			{
				int locx = j+x;
				int locy = i+y;
				int pos= MyFrame.screenWidth*locy + locx;
				
				if(modelPixel[i][j] == 0) continue;
				if( locx <= 0 || locx >= MyFrame.screenWidth || locy <= 0 || locy >= MyFrame.screenHeight ) continue;
				
				if(canvasPixel[pos] != 0) collide = true;
				canvasPixel[pos] = modelPixel[i][j];
				
			}
		}
		
		if(angle < 90)
			angle+= turnSpeed;
		
		return collide;
	}
}
