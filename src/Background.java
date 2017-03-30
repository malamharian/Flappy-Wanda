import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;


public class Background extends Object {

	private BufferedImage image;
	private int panSpeed;
	
	public Background(int width, int height) {
		
		
		panSpeed = 4;
		this.width = width;
		this.height = height;
		x = 0;
		y = 0;
		try{
			image = ImageIO.read(new File("assets/images/background.png"));
		}catch(Exception e)
		{
			
		}
	}
	
	public void render(Graphics2D g2d)
	{
		g2d.drawImage(image, x, y, width, height,null);
		g2d.drawImage(image, x+width, y, width, height,null);
		
		x -= panSpeed;
		
		if(x<= -width)
		{
			x= 0;
		}
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public int getPanSpeed() {
		return panSpeed;
	}

	public void setPanSpeed(int panSpeed) {
		this.panSpeed = panSpeed;
	}
}
