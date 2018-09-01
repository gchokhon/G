/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AI;

import java.awt.image.BufferedImage;

/**
 *
 * @author g_cho
 */
public class trainer {
    public BufferedImage img;
    public int sizeX=0;
    public int sizeY=0;
    public trainer(BufferedImage im){
        img=im;
        this.sizeX=im.getWidth();
        this.sizeY=im.getHeight();
        
    }
    public int score(BufferedImage patern){
        int Px=patern.getWidth();
        int Py=patern.getHeight();
        int cox=this.sizeX/Px;
        int coy=this.sizeY/Py;
        int tx=cox;
        int[][] scores=new int[cox+1][coy+1];
        int Mx=0;
        int My=0;
        int ty=0;
 
        while (cox>0||coy>0){
        
        for(int y=0;y<Py;y++){
            for(int x=0;x<Px;x++){
              if(Mx<img.getWidth()&&My<img.getHeight()){
                int color1=img.getRGB(Mx, My);
                int color2=patern.getRGB(x, y);
                int a=color1>>24;
                int a2=color2>>24;
                int r=color1>>16;
                int r2=color2>>16;
                 int g=color1>>8;
                int g2=color2>>8;
                     int b=color1;
                int b2=color2;
                if(Math.abs(a-a2)<50)scores[cox][coy]++;
                if(Math.abs(r-r2)<50)scores[cox][coy]++;
                if(Math.abs(g-g2)<50)scores[cox][coy]++;
                if(Math.abs(b-b)<50)scores[cox][coy]++;
               
                 Mx++;
              }
            }
            My++;
            
        }
        
        cox--;
                 
        if(cox==0){
            if(coy!=0){
                cox=tx;
                Mx=0;
                ty=My;
                My=0;
                coy--;
            }
        }
        if(coy==0){
            if(cox!=0){
                My=ty;
                cox--;
            }
        }
        
        
        }

        int max=scores[0][0];
              
        for(int x=0;x<scores.length;x++)
            for(int y=0;y<scores[x].length;y++){
             
                if(max<scores[x][y])max=scores[x][y];
            }
        return max;
        
        
    }
    
    
}
