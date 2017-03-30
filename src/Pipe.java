import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class Pipe extends Object {
	
	private int modelPixel[][];
	private BufferedImage scaled;
	private boolean scored;
	private int status;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public boolean isScored() {
		return scored;
	}
	public void setScored(boolean scored) {
		this.scored = scored;
	}
	
	public Pipe()
	{
		scored = false;
	}
	public Pipe(int x, int y, int width, int height, int status, BufferedImage img) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.status = status;
		scored = false;
		
		scaled = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D)scaled.createGraphics();
		g.drawImage(img, 0, 0, width, height, null);
		g.dispose();
	}
	
	public void render(int canvasPixel[])
	{	
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
				
				if( locx <= 0 || locx >= MyFrame.screenWidth || locy <= 0 || locy >= MyFrame.screenHeight ) continue;
				
				canvasPixel[pos] = modelPixel[i][j];
				
			}
		}
	}
	
	public void move(int pipeSpeed)
	{
		x -= pipeSpeed;
	}
}
