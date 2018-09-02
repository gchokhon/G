/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AI;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author g_cho
 */
public final class node {
    private BufferedImage checker;
    private BufferedImage item;
    public File file=new File("src/AI/storage.dat");
    public String filename=file.getAbsolutePath();
    private int[][]  division;
    public   ArrayList<trainer> testers=     new ArrayList();
    public ArrayList<patern> paterns=     new ArrayList();
    public node(){
        ArrayList files=readfilenames();
     
        for(int i=0;i<files.size();i++){
            trainer t=new trainer(readpic((String) files.get(i)));
            
            testers.add(t);
            
        }

          Random rand=new Random();

         // paterns=read();
         try {
        	 if(!read().isEmpty()) {
                 paterns= read();
        	 }
         }
         catch(NullPointerException e ) {
        	 paterns=new ArrayList();
         }

        if(!paterns.isEmpty()){
           for(int c=0;c<200;c++){
          patern p1=paterns.get(rand.nextInt(paterns.size()));
          p1.score=scorepatern(p1.getImage(),testers);
          patern p2=paterns.get(rand.nextInt(paterns.size()));
               p2.score=scorepatern(p2.getImage(),testers);
            patern min=min(paterns);
                 min.score=scorepatern(min.getImage(),testers);
            if(p1.score!=min.score&&p2.score!=min.score){
         patern Nimg= breed(p1,p2);
         Nimg.score=scorepatern(Nimg.getImage(),testers);
         if(Nimg.score>min.score)
         min=Nimg;  
         else{
             Random r=new Random();
             BufferedImage img=testers.get(r.nextInt(testers.size())).img;
             int cuty=r.nextInt(img.getHeight()-min.getImage().getHeight());
             int cutx=r.nextInt(img.getWidth()-min.getImage().getWidth());
             for(int y=cuty;y<min.getImage().getHeight();y++)
                 for(int x=cutx;x<min.getImage().getWidth();x++){
                     int pix=img.getRGB(x, y);
                     min.getImage().setRGB(x, y, pix);
                    min.score=scorepatern(min.getImage(),testers);
                 }
         }
            }
            else {
            
                 patern Nimg= new patern(p1.getImage().getWidth(),p2.getImage().getHeight());
         Nimg.score=scorepatern(Nimg.getImage(),testers);
         min=Nimg;  
                
            }
          
           }        
        }
        else {
              for(int i=0;i<200;i++){  
                patern Npat=new patern(25,25);
                Npat.score=scorepatern(Npat.getImage(),testers);
               paterns.add(Npat);
              }
            
        }
        patern best=paterns.get(0);
        for(int i=1;i<paterns.size();i++){
            if(best.score<paterns.get(i).score)best=paterns.get(i);
        }
        
        saveimage(buildnewimg(400,300));
       // Save(paterns);
      
        
        
        
        
    }
    public BufferedImage buildnewimg(int x,int y){
        BufferedImage img=new BufferedImage(x,y,BufferedImage.TYPE_INT_ARGB);
        Random r=new Random();    
        int patx=paterns.get(r.nextInt(paterns.size())).getImage().getWidth();
        int paty=paterns.get(r.nextInt(paterns.size())).getImage().getHeight();
      


        
        for(int Y=0;Y<y;Y+=paty){
            for(int X=0;X<x;X+=patx){
                Graphics2D g=img.createGraphics();
              
                BufferedImage im=paterns.get(r.nextInt(paterns.size())).getImage();
     
                g.drawImage(im ,X, Y, null);
                
                
                
               
                
            }
        }
        return img;
        
        
        
    }
        public patern findbest(int x,int y){
   
        int maxscore=0;
        int px=0;
        int py=0;
        patern max=null;

        for(int p=0;p<paterns.size();p++){
        for(int i=0;i<testers.size();i++){
             px=x;
             py=y;
                 int score=0;
            
            BufferedImage buf=paterns.get(i).getImage();
            for(int Y=0;Y<buf.getHeight();Y++){
                for(int X=0;X<buf.getWidth();X++){
                    int color=paterns.get(p).getImage().getRGB(X, Y);
                    int color2=testers.get(i).img.getRGB(px, py);
                    px++;
                    int a=color<<24;
                    int r=color<<16;
                    int g=color<<8;
                    int b=color;
                     int a2=color2<<24;
                    int r2=color2<<16;
                    int g2=color2<<8;
                    int b2=color2;
                   
                    if(a==a2)score++;
                    if(r==r2)score++;
                    if(g==g2)score++;
                    if(b==b2)score++;
                    
                }
                py++;
            }
            if(score>maxscore){
                maxscore=score;
                max=paterns.get(p);
            }
        }
        }
        
        return max;
        
    }
    public void checkcolordepth(BufferedImage img){
        int depth1=0;
        int depth2=0;
        for(int i=0;i<img.getHeight();i++)
        for(int y=0;y<img.getHeight();y++)
            for(int x=0;x<img.getWidth();x++){
               
                if(y>1){
                int RBG=img.getRGB(x, y);
                int a=RBG <<24;
                int r=RBG <<16;
                int g=RBG<<8;
                int b=RBG;
                  int RBG2=img.getRGB(x, y-1);
                int a2=RBG <<24;
                int r2=RBG <<16;
                int g2=RBG<<8;
                int b2=RBG;
                depth1=254-a+254-r+254-g+254-b;
                  depth2=254-a2+254-r2+254-g2+254-b2;
                 if(depth2<depth1){
                     img.setRGB(x, y, RBG2);
                     img.setRGB(x, y-1, RBG);
                    
                 }
                
                }
             
            }
      
      
        
    }
    public int scorepatern(BufferedImage pic,ArrayList tr){
       int arr[]=new int[tr.size()];
       for(int i=0;i<tr.size();i++){
           trainer s=(trainer)tr.get(i);
           arr[i]=s.score(pic);
       }
       int max=arr[0];
       for(int i=1;i<arr.length;i++){
           if(max<arr[i])max=arr[i];
       }
       return max;
    }
    public patern breed(patern p1,patern p2){
        int width=p1.getImage().getWidth();
        int height=p1.getImage().getHeight();
      
        Random rand=new Random();
        Boolean side=rand.nextBoolean();
        patern Nimg=new patern(width,height);
        if(side){
            int cut=rand.nextInt(width);
            for(int y=0;y<height;y++)
                for(int x=0;x<width-cut;x++){
                    int color=p1.getImage().getRGB(x, y);
                    Nimg.getImage().setRGB(x, y, color);
                }
                   for(int y=0;y<height;y++)
                for(int x=cut;x<width;x++){
                    int color=p2.getImage().getRGB(x, y);
                    Nimg.getImage().setRGB(x, y, color);
                }
        }
        else{
            int cut=rand.nextInt(height);
                    for(int y=0;y<height-cut;y++)
                for(int x=0;x<width;x++){
                    int color=p1.getImage().getRGB(x, y);
                    Nimg.getImage().setRGB(x, y, color);
                }
                       for(int y=cut;y<cut;y++)
                for(int x=0;x<width;x++){
                    int color=p2.getImage().getRGB(x, y);
                    Nimg.getImage().setRGB(x, y, color);
                }
            
            
        }
        return Nimg;
    }
    public patern min(ArrayList pat){
        patern min=(patern) pat.get(0);
        for(int i=0;i<pat.size();i++){
            patern temp=(patern) pat.get(i);
            if(min.score>temp.score)min=temp;
            
        }
        return min;
        
    }
    
    public BufferedImage readpic(String name){
        File t=new File("src/nodes/Images/trainpics");
        String ps=t.getAbsolutePath();
        ps=ps+"/"+name;
        BufferedImage pic=null;
        try {
            pic=ImageIO.read(new File(ps));
        } catch (IOException ex) {
            Logger.getLogger(node.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pic;
    }
    
    public ArrayList readfilenames(){
        ArrayList results=new ArrayList();
        File p=new File("src/nodes/Images/trainpics/");
        String pas=p.getAbsolutePath();
        File[] files = new File(pas).listFiles();
//If this pathname does not denote a directory, then listFiles() returns null. 

for (File f : files) {
    if (f.isFile()) {
        results.add(f.getName());
    }
}
return results;
        
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
    
    /**
     *
     * @param s
     */
    public void Save(ArrayList s){
        try {
            int size=s.size();
            FileOutputStream f =new FileOutputStream(this.filename);
            
        ObjectOutputStream os = new ObjectOutputStream(f);
        
            os.writeInt(size);
            for(int i=0;i<size;i++){
                patern n=(patern) s.get(i);
                BufferedImage img=n.getImage();
                int score=n.score;
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                ImageIO.write(img,"jpg", buffer);
                os.writeInt(buffer.size());
                  buffer.writeTo(os);
                 os.writeInt(score);
                  
                
                
             
            }
            os.close();
            f.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(node.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(node.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
      public ArrayList read(){
   
       ArrayList<patern> temp=null;
          
        try {
            File f=new File(this.filename);
            if(f.exists()){
              FileInputStream file=new FileInputStream(f);
            ObjectInputStream is=new ObjectInputStream(file);
            int count=is.readInt();
            temp=new ArrayList(count);
            for(int i=0;i<count;i++){
             int size=is.readInt();
            patern p=new patern(1,1);
            byte[] buffer=new byte[size];
            is.readFully(buffer);
            p.setImage(ImageIO.read(new ByteArrayInputStream(buffer)));
            p.score=is.readInt();
            int sc=scorepatern(p.getImage(),this.testers);
            if(sc<p.score) {
            	patern m=new patern(p.getImage().getWidth(),p.getImage().getHeight());
            	m.generateRandom();
            	m.score=scorepatern(p.getImage(),this.testers);
            	p=m;
            }
            temp.add(p);
            }
            
            
            
          file.close();
            is.close();
            }
            else return null;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(node.class.getName()).log(Level.SEVERE, null, ex);
         
        } catch (IOException ex) {
            Logger.getLogger(node.class.getName()).log(Level.SEVERE, null, ex);
        }
        return temp;
        
    }
    
    
    
    
    
    
}
