/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AI;

import java.util.ArrayList;


/**
 *
 * @author g_cho
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        for(int i=0;i<500;i++){
        node start=new node();
      
        ArrayList t=start.paterns;
        for(int d=0;d<t.size();d++){
           patern ttt=(patern) t.get(d);
           ttt.score=start.scorepatern(ttt.getImage(), start.testers);
           t.set(d, ttt);
          
        }
        
        
        start.Save(t);
        }
        


      
      
       
        // TODO code application logic here
    }
    
}
