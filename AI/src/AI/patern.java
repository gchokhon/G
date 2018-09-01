/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AI;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author g_cho
 */
public class patern implements Serializable   {
   
    private int curpos;
    private int curline;
    private int x;
    private int y;
    public int score;
    private BufferedImage img;
    public patern(int x,int y){
        this.x=x;
        this.y=y;
        img=new BufferedImage(x,y, BufferedImage.TYPE_INT_ARGB);
        curpos=0;
        curline=0;
        score=0;
        generateRandom();
              
    }
    private static final long serialVersionUID = 1L;

    public void addpix(int x){
        if(this.x>this.curpos){
            img.setRGB(curpos, curline, x);
            this.curpos++;
        }
    }
    public void changeline(){
        if(this.curline<this.y)curline++;
    }
    public void scorself(BufferedImage p){
        
    }
    public void generateRandom(){
//image dimension
int width = this.x;

int height = this.y;
//create buffered image object img
BufferedImage im = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
//file object

for(int y = 0; y < height; y++){
       for(int x = 0; x < width; x++){
         int a = (int)(Math.random()*256); //alpha
         int r = (int)(Math.random()*256); //red
         int g = (int)(Math.random()*256); //green
         int b = (int)(Math.random()*256); //blue
 
         int p = (a<<24) | (r<<16) | (g<<8) | b; //pixel
 
         im.setRGB(x, y, p);
       }
}


    this.img=im;
         
    }
    public BufferedImage getImage(){
        return this.img;
    }
    public void setImage(BufferedImage im){
        this.img=im;
    }
        public void saveimage(){
        File f = new File("src/nodes/Images/C.jpg");
        String fs=f.getAbsolutePath();
        f=new File(fs);
        try {
            ImageIO.write(img, "jpg", f);
        } catch (IOException ex) {
            Logger.getLogger(node.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        public void saveimage(BufferedImage img){
        File f = new File("src/nodes/Images/C.jpg");
        String fs=f.getAbsolutePath();
        f=new File(fs);
        try {
            ImageIO.write(img, "jpg", f);
        } catch (IOException ex) {
            Logger.getLogger(node.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        	@Override
	public String toString() {
		return "img:" + img + "\nscore: " + score;
	}
        
    
    
}
