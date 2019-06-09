package vue.other;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

public class TexturePack {

	public final static int EMPTYIMG=100;
	private int titlePaneWidth;
	private int titlePx;
	private BufferedImage titleImg;
	private HashMap<Integer, WritableImage> memo;
	
	public TexturePack(String imagePath, int titlePaneWith, int titlePx) {
		try {
			System.out.println("TITLE IMG : " + imagePath);
			titleImg= ImageIO.read(this.getClass().getClassLoader().getResource(imagePath));
		} catch (IOException e) {
			throw new Error("ERROR WHILE READING IMAGE");
		}
		this.titlePaneWidth = titlePaneWith;
		this.titlePx = titlePx;
		this.memo =  new HashMap<Integer, WritableImage>();
	}
	
	public WritableImage getImg(int val) {
		if (val ==-1)
			val=1606;
		if (memo.get(val) == null) {
			memo.put(val, SwingFXUtils.toFXImage(titleImg.getSubimage(val%titlePaneWidth*titlePx, val/titlePaneWidth*titlePx, titlePx, titlePx), null));
		}
		return memo.get(val);
	}
}