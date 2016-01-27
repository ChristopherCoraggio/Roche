import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class Document {
	
	private String sampleName;
	private String description;
	private int chargeNumber;
	private ArrayList<String> desiredTests;
	public static ImageIcon picture;
	private int problemID;
	private String submitterName;
	private String submitterContact;
	
	//Fix annotation of image
	
	public Document(){}
	
	public Document(String sampleName, String description, int chargeNumber, ArrayList<String> desiredTests,
			ImageIcon picture, String submitterName, String submitterContact) throws IOException{
		this.sampleName = sampleName;
		this.description = description;
		this.chargeNumber = chargeNumber;
		this.desiredTests = desiredTests;
		this.picture = picture;
		this.submitterName = submitterName;
		this.submitterContact = submitterContact;
		this.problemID = generateID();
	}
	
	public void makeLabel() throws java.io.IOException{
		FileWriter f = new FileWriter(new File(System.getProperty("user.home") + "//Desktop//Roche//blah.csv"));
		f.write("Sample Name, Charge Number, Submitter Name, Submitter Contact, Problem ID\n");
		f.write("" + sampleName + ", " + chargeNumber + ", " + submitterName + ", " + submitterContact + "\n");
		f.close();
		annotatePicture();
	}
	
	public String savePicture(String name) throws IOException{ //returns path of file made
		BufferedImage bi = new BufferedImage(
			    picture.getIconWidth(),
			    picture.getIconHeight(),
			    BufferedImage.TYPE_INT_RGB);
			java.awt.Graphics g = bi.createGraphics();
			// paint the picture to the BufferedImage
			picture.paintIcon(null, g, 0,0);
			g.dispose();
		ImageIO.write(bi, "jpg", new File(System.getProperty("user.home") + "\\Desktop\\Roche\\" + name + ".jpg"));
		return(System.getProperty("user.home") + "\\Desktop\\Roche\\" + name + ".jpg");
	}
	
	public void annotatePicture() throws IOException{
		int x, y; //start of square and text
		// http://research.cs.queensu.ca/~blostein/java.html
		JButton label = new JButton(this.toString(), picture);
		label.setVerticalAlignment(javax.swing.SwingConstants.TOP);
		label.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		String path = "";
		try {
			path = savePicture("test2");
		} catch (IOException e) {
			e.printStackTrace();
		}
        BufferedImage image = ImageIO.read(new File(path + "-modified.bmp"));
        x = image.getWidth() * 2/3;
        y = image.getHeight() * 2/3;
        Graphics g = image.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(x, y, image.getWidth() /3, image.getHeight() /3);
        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 12));
        drawTheString(g, this.toString(), image.getWidth() * 2 /3, image.getHeight() * 2 /3);
        g.dispose();
        
        ImageIO.write(image, "jpg", new File(path + "-modified.jpg"));
	}
	
	void drawTheString(Graphics g, String text, int x, int y) {
	    for (String line : text.split("\n"))
	        g.drawString(line, x, y += g.getFontMetrics().getHeight());
	}
	
	public int generateID() throws IOException{ //WILL - override this method :)
		int genID = 0;
		File h = new File(System.getProperty("user.home") + "/Desktop//Roche//willthiswork.csv");
		if (h.exists()){
			FileReader j = new FileReader(h);
			if (j.read() != -1){
				genID = j.read();	
			}
			j.close();
			genID += 1;
			System.out.println(genID);
		}
		
		FileWriter g = new FileWriter(h);
		g.write(Integer.toString(genID));
		g.close();
		return genID;
	}
	
	
	public String toString(){
		return( "\nSample Name:\t" + sampleName +
				"\nDescription:\t" + description +
				"\nCharge Number:\t" + chargeNumber +
				"\nDesired Tests:\t" + desiredTests +
				"\nSubmitter Name:\t" + submitterName +
				"\nSubmitter Contact:\t" + submitterContact +
				"\nID:\t" + problemID);
	}
}
