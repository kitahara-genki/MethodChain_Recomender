import java.applet.*;
import java.awt.*;
import java.lang.Math;
import java.util.*;

public class teto extends Applet implements Runnable {
  int pat,xza,yza,flag,pflag,kflag,dir,ten,nextpat,kpat,hten,kazu;
  int wait_time,wait_num;
//box:array[-20..210] of integer;
  int box[];
  boolean game_flag,yok_flag;
  public Thread clock=null;
 // Button button_start=new Button("START");
  Label label1=new Label("SCORE:  *******");
  Label label2=new Label("HIGH:  *******");
  Random r=new Random();


  public void init() {
    add(label1);
    add(label2);
    
    hten=Integer.parseInt(getParameter("high"));
    wait_time=Integer.parseInt(getParameter("wait"));

    //hten=100;
    //wait_time=150;

    wait_num=1500/wait_time+1;

    box=new int[245];
    //add(button_start);
  }//init end

  public void start() {
    game_flag=false;
    yok_flag=false;
    pflag=0;
    repaint();

    if (clock==null) {
      clock=new Thread(this);
      clock.start();
            
    }
  }
  public void stop() {
    if (clock!=null) {
      clock.stop();
      clock=null;
    }
  }
  public void run() {
      while (clock.isAlive()) {

        if ((game_flag) && (kazu==wait_num)) {
        //keys();//->dir
        //  float f;
        //  f=r.nextFloat();
        //  dir=Math.round(6*f);
        if (yok_flag) {
          dir=100;   
        }
        switch(dir) {
          case 100:
            pflag=5;
            repaint();
            yok_flag=false;

            break;
          case 5: 
            //houkou(xza,yza,kpat);//pflag=1
            pflag=1;
            repaint();

            break;
          case 6: 
            //sayuu1(0,kpat,xza,yza);//pflag=2 kflag=0
            pflag=2;
            kflag=0;
            repaint();

            break;
          case 4: 
            //sayuu1(2,kpat,xza,yza);//pflag=2 kflag=2
            pflag=2;
            kflag=2;
            repaint();

            break;
          case 0:
            while (flag==0) {
              //syori(xza,yza,kpat,flag);
              pflag=3;
              repaint();
            }
            //
            flag=0;
            break;
          case 1:
            //syori(xza,yza,kpat,flag);//pflag=3
            pflag=3;
            repaint();
            break;
        }
                
        dir=1;          
        if (flag!=0) {
          //linekesi();//->ten pflag=4
          pflag=4;
          repaint();
          if (box[21+20]!=0 || box[22+20]!=0 || box[23+20]!=0 || box[24+20]!=0 || box[25+20]!=0 || box[26+20]!=0 || box[27+20]!=0 || box[28+20]!=0 || box[29+20]!=0 || box[30+20]!=0) {
            game_flag=false;
            if (ten>hten) {
              label2.setText("RECORD!!");
              hten=ten;
            }
            else {
              label2.setText("GAME OVER");
            }

            if (clock!=null) {
              clock.stop();
              clock=null;
            }
           
            return;
          }
          xza=320;
          yza=40;
          flag=0;
          kpat=nextpat;
          pattern();//->pat
          nextpat=pat;
          //yokoku();//<-nextpat pflag=5
          //pflag=5;
          //repaint();
          yok_flag=true;
        }
                


        }// if end

        kazu=kazu+1;
        if (kazu>wait_num) {
          kazu=wait_num;
        }
        try {
          Thread.sleep(wait_time);
        } catch (InterruptedException e){}
  
      }
  }//run end


  public void update(Graphics g) {
    paint(g);
  }



  public void paint(Graphics g) {
    int n;

    switch(pflag) {
          case 1: 
            //houkou
            //pat->kpat
            

  n=(yza-40)/16*10+(xza-240)/16+1;
  switch(kpat) {
    case1  : 
      kpat=1;
      break;
    case 2 :
      if (yza>=72 && box[n+20]==0 && box[n-10+20]==0 && box[n-11+20]==0 && box[n-20+20]==0 && box[n-9+20]==0 && box[n-19+20]==0) {
      kpat=12;
      draw(g,xza,yza-16,16,0);draw(g,xza-16,yza,48,0);
      draw(g,xza-16,yza-16,32,10);draw(g,xza,yza-32,16,10);
      draw(g,xza,yza,16,10);
      //setcolor(0);moveto(xza,yza);lineto(xza+16,yza);lineto(xza+16,yza+16);
      //lineto(xza+32,yza+16);lineto(xza+32,yza-32);lineto(xza+16,yza-32);
      //lineto(xza+16,yza-16);lineto(xza,yza-16);lineto(xza,yza);
      //line(xza+16,yza-16,xza+16,yza);line(xza+16,yza-16,xza+32,yza-16);
      //line(xza+16,yza,xza+32,yza);
      }
      else {
        kpat=2;
      }
      break;
    case 12 :
      if (xza>=272 && box[n-1+20]==0 && box[n-11+20]==0 && box[n-12+20]==0 && box[n-10+20]==0 && box[n-21+20]==0 && box[n-22+20]==0) {
      kpat=22;
      draw(g,xza-16,yza-16,32,0);draw(g,xza,yza-32,16,0);
      draw(g,xza,yza,16,0);
      draw(g,xza-16,yza,16,10);draw(g,xza-32,yza-16,48,10);
      //setcolor(0);moveto(xza,yza);lineto(xza,yza-16);lineto(xza+16,yza-16);
      //lineto(xza+16,yza-32);lineto(xza-32,yza-32);lineto(xza-32,yza-16);
      //lineto(xza-16,yza-16);lineto(xza-16,yza);lineto(xza,yza);
      //line(xza-16,yza-16,xza,yza-16);line(xza-16,yza-32,xza-16,yza-16);
      //line(xza,yza-32,xza,yza-16);
      }
      else {
        kpat=12;
      }
      break;
    case 22 :
      if (yza<=328 && box[n+20]==0 && box[n-1+20]==0 && box[n-11+20]==0 && box[n+9+20]==0 && box[n-2+20]==0 && box[n+8+20]==0) {
      kpat=32;
      draw(g,xza-16,yza,16,0);draw(g,xza-32,yza-16,48,0);
      draw(g,xza-16,yza,32,10);draw(g,xza-16,yza-16,16,10);
      draw(g,xza-16,yza+16,16,10);
      //setcolor(0);moveto(xza,yza);lineto(xza,yza+16);lineto(xza-16,yza+16);
      //lineto(xza-16,yza+32);lineto(xza-32,yza+32);lineto(xza-32,yza-16);
      //lineto(xza-16,yza-16);lineto(xza-16,yza);lineto(xza,yza);
      //line(xza-32,yza,xza-16,yza);line(xza-32,yza+16,xza-16,yza+16);
      //line(xza-16,yza,xza-16,yza+16);
      }
      else {
        kpat=22;
      }
      break;
    case 32 :
      if (xza<=368 && box[n-1+20]==0 && box[n+20]==0 && box[n+1+20]==0 && box[n-10+20]==0 && box[n+10+20]==0 && box[n+11+20]==0) {
      kpat=2;
      draw(g,xza-16,yza,32,0);draw(g,xza-16,yza-16,16,0);
      draw(g,xza-16,yza+16,16,0);
      draw(g,xza,yza-16,16,10);draw(g,xza-16,yza,48,10);
      //setcolor(0);moveto(xza,yza);lineto(xza+16,yza);lineto(xza+16,yza+16);
      //lineto(xza+32,yza+16);lineto(xza+32,yza+32);lineto(xza-16,yza+32);
      //lineto(xza-16,yza+16);lineto(xza,yza+16);lineto(xza,yza);
      //line(xza,yza+16,xza+16,yza+16);line(xza,yza+16,xza,yza+32);
      //line(xza+16,yza+16,xza+16,yza+32);
      }
      else {
        kpat=32;
      }
      break;
    case 3 :
      if (yza<=328 && box[n-11+20]==0 && box[n-10+20]==0 && box[n+20]==0 && box[n+10+20]==0 && box[n+9+20]==0 && box[n+8+20]==0) {
      kpat=13;
      draw(g,xza,yza-16,16,0);draw(g,xza-32,yza,48,0);
      draw(g,xza-16,yza-16,32,11);draw(g,xza,yza,16,11);
      draw(g,xza,yza+16,16,11);
      //setcolor(0);moveto(xza,yza);lineto(xza+16,yza);lineto(xza+16,yza+32);
      //lineto(xza+32,yza+32);lineto(xza+32,yza-16);lineto(xza,yza-16);
      //lineto(xza,yza);line(xza+16,yza,xza+32,yza);
      //line(xza+16,yza+16,xza+32,yza+16);line(xza+16,yza-16,xza+16,yza);
      }
      else {
        kpat=3;
      }
      break;
    case 13 :
      if (xza<=368 && box[n-1+20]==0 && box[n-11+20]==0 && box[n-10+20]==0 && box[n-9+20]==0 && box[n+1+20]==0 && box[n+11+20]==0) {
      kpat=23;
      draw(g,xza-16,yza-16,32,0);draw(g,xza,yza,16,0);
      draw(g,xza,yza+16,16,0);
      draw(g,xza-16,yza,16,11);draw(g,xza-16,yza-16,48,11);
      //setcolor(0);moveto(xza,yza);lineto(xza-16,yza);lineto(xza-16,yza-32);
      //lineto(xza+32,yza-32);lineto(xza+32,yza-16);lineto(xza,yza-16);
      //lineto(xza,yza);line(xza-16,yza-16,xza,yza-16);
      //line(xza,yza-32,xza,yza-16);line(xza+16,yza-32,xza+16,yza-16);
      }
      else {
        kpat=13;
      }
      break;
    case 23 :
      if (yza>=72 && box[n+20]==0 && box[n-1+20]==0 && box[n-11+20]==0 && box[n-21+20]==0 && box[n-20+20]==0 && box[n-19+20]==0) {
      kpat=33;
      draw(g,xza-16,yza,16,0);draw(g,xza-16,yza-16,48,0);
      draw(g,xza-16,yza,32,11);draw(g,xza-16,yza-16,16,11);
      draw(g,xza-16,yza-32,16,11);
      //setcolor(0);moveto(xza,yza);lineto(xza,yza+16);lineto(xza-32,yza+16);
      //lineto(xza-32,yza-32);lineto(xza-16,yza-32);lineto(xza-16,yza);
      //lineto(xza,yza);line(xza-16,yza,xza-16,yza+16);
      //line(xza-32,yza,xza-16,yza);line(xza-32,yza-16,xza-16,yza-16);
      }
      else {
        kpat=23;
      }
      break;
    case 33 :
      if (xza>=272 && box[n-2+20]==0 && box[n-1+20]==0 && box[n+20]==0 && box[n-10+20]==0 && box[n-12+20]==0 && box[n-22+20]==0) {
      kpat=3;
      draw(g,xza-16,yza,32,0);draw(g,xza-16,yza-16,16,0);
      draw(g,xza-16,yza-32,16,0);
      draw(g,xza,yza-16,16,11);draw(g,xza-32,yza,48,11);
      //setcolor(0);moveto(xza,yza);lineto(xza+16,yza);lineto(xza+16,yza+32);
      //lineto(xza-32,yza+32);lineto(xza-32,yza+16);lineto(xza,yza+16);
      //lineto(xza,yza);line(xza,yza+16,xza+16,yza+16);
      //line(xza-16,yza+16,xza-16,yza+32);line(xza,yza+16,xza,yza+32);
      }
      else {
        kpat=33;
      }
      break;
    case 4 :
      if (yza>=72 && box[n+20]==0 && box[n-1+20]==0 && box[n-10+20]==0 && box[n-20+20]==0 && box[n-9+20]==0 && box[n-19+20]==0) {
      kpat=14;
      draw(g,xza-16,yza-16,16,0);draw(g,xza-16,yza,48,0);
      draw(g,xza-16,yza,32,12);draw(g,xza,yza-16,16,12);
      draw(g,xza,yza-32,16,12);
      //setcolor(0);moveto(xza,yza);lineto(xza+32,yza);lineto(xza+32,yza-48);
      //lineto(xza+16,yza-48);lineto(xza+16,yza-16);lineto(xza,yza-16);
      //lineto(xza,yza);line(xza+16,yza-16,xza+16,yza);
      //line(xza+16,yza-16,xza+32,yza-16);line(xza+16,yza-32,xza+32,yza-32);
      }
      else {
        kpat=4;
      }
      break;
    case 14 :
      if (xza>=272 && box[n+20]==0 && box[n-10+20]==0 && box[n-11+20]==0 && box[n-12+20]==0 && box[n-21+20]==0 && box[n-22+20]==0) {
      kpat=24;
      draw(g,xza-16,yza,32,0);draw(g,xza,yza-16,16,0);
      draw(g,xza,yza-32,16,0);
      draw(g,xza,yza,16,12);draw(g,xza-32,yza-16,48,12);
      //setcolor(0);moveto(xza,yza);lineto(xza-16,yza);lineto(xza-16,yza-16);
      //lineto(xza-48,yza-16);lineto(xza-48,yza-32);lineto(xza,yza-32);
      //lineto(xza,yza);line(xza-16,yza-16,xza,yza-16);
      //line(xza-32,yza-32,xza-32,yza-16);line(xza-16,yza-32,xza-16,yza-16);
      }
      else {
        kpat=14;
      }
      break;
    case 24 :
      if (yza<=328 && box[n+9+20]==0 && box[n-1+20]==0 && box[n-11+20]==0 && box[n-10+20]==0 && box[n-2+20]==0 && box[n+8+20]==0) {
      kpat=34;
      draw(g,xza,yza,16,0);draw(g,xza-32,yza-16,48,0);
      draw(g,xza-16,yza-16,32,12);draw(g,xza-16,yza,16,12);
      draw(g,xza-16,yza+16,16,12);
      //setcolor(0);moveto(xza,yza);lineto(xza-32,yza);lineto(xza-32,yza+48);
      //lineto(xza-16,yza+48);lineto(xza-16,yza+16);lineto(xza,yza+16);
      //lineto(xza,yza);line(xza-16,yza,xza-16,yza+16);
      //line(xza-32,yza+16,xza-16,yza+16);line(xza-32,yza+32,xza-16,yza+32);
      }
      else {
        kpat=24;
      }
      break;
    case 34 :
      if (xza<=368 && box[n-11+20]==0 && box[n-1+20]==0 && box[n+20]==0 && box[n+1+20]==0 && box[n+10+20]==0 && box[n+11+20]==0) {
      kpat=4;
      draw(g,xza-16,yza-16,32,0);draw(g,xza-16,yza,16,0);
      draw(g,xza-16,yza+16,16,0);
      draw(g,xza-16,yza-16,16,12);draw(g,xza-16,yza,48,12);
      //setcolor(0);moveto(xza,yza);lineto(xza+16,yza);lineto(xza+16,yza+16);
      //lineto(xza+48,yza+16);lineto(xza+48,yza+32);lineto(xza,yza+32);
      //lineto(xza,yza);line(xza,yza+16,xza+16,yza+16);
      //line(xza+16,yza+16,xza+16,yza+32);line(xza+32,yza+16,xza+32,yza+32);
      }
      else {
        kpat=34;
      }
      break;
    case 5 :
      if (yza>=72 && yza<=328 && box[n+10+20]==0 && box[n+20]==0 && box[n-10+20]==0 && box[n-20+20]==0 && box[n-9+20]==0 && box[n-19+20]==0 && box[n+9+20]==0 && box[n+8+20]==0) {
      kpat=15;
      draw(g,xza-32,yza,64,0);
      draw(g,xza,yza+16,16,13);draw(g,xza,yza,16,13);
      draw(g,xza,yza-16,16,13);draw(g,xza,yza-32,16,13);
      //setcolor(0);moveto(xza,yza);lineto(xza+16,yza);lineto(xza+16,yza-64);
      //lineto(xza,yza-64);lineto(xza,yza);
      //line(xza,yza-16,xza+16,yza-16);line(xza,yza-32,xza+16,yza-32);
      //line(xza,yza-48,xza+16,yza-48);
      }
      else {
        kpat=5;
      }
      break;
    case 15 :
      if (xza<=368 && xza>=272 && box[n-2+20]==0 && box[n-1+20]==0 && box[n+20]==0 && box[n+1+20]==0 && box[n-9+20]==0 && box[n-19+20]==0 && box[n+9+20]==0 && box[n+8+20]==0) {
      kpat=5;
      draw(g,xza,yza+16,16,0);draw(g,xza,yza,16,0);
      draw(g,xza,yza-16,16,0);draw(g,xza,yza-32,16,0);
      draw(g,xza-32,yza,64,13);
      //setcolor(0);moveto(xza,yza);lineto(xza+64,yza);lineto(xza+64,yza+16);
      //lineto(xza,yza+16);lineto(xza,yza);
      //line(xza+16,yza,xza+16,yza+16);line(xza+32,yza,xza+32,yza+16);
      //line(xza+48,yza,xza+48,yza+16);
      }
      else {
        kpat=15;
      }
      break;
    case 6 :
      if (yza>=72 && box[n-10+20]==0 && box[n-20+20]==0 && box[n-9+20]==0 && box[n+1+20]==0 && box[n+11+20]==0 && box[n-19+20]==0) {
      kpat=16;
      draw(g,xza,yza,32,0);draw(g,xza-16,yza+16,32,0);
      draw(g,xza,yza-16,32,14);draw(g,xza,yza-32,16,14);
      draw(g,xza+16,yza,16,14);
      //setcolor(0);moveto(xza,yza);lineto(xza+16,yza);lineto(xza+16,yza+16);
      //lineto(xza+32,yza+16);lineto(xza+32,yza-16);lineto(xza+16,yza-16);
      //lineto(xza+16,yza-32);lineto(xza,yza-32);lineto(xza,yza);
      //line(xza,yza-16,xza+16,yza-16);line(xza+16,yza,xza+32,yza);
      //line(xza+16,yza-16,xza+16,yza);
      }
      else {
        kpat=6;
      }
      break;
    case 16 :
      if (yza<=328 && xza>=256 && box[n+20]==0 && box[n+1+20]==0 && box[n+10+20]==0 && box[n+9+20]==0 && box[n+11+20]==0 && box[n-19+20]==0) {
      kpat=6;
      draw(g,xza,yza-16,32,0);draw(g,xza,yza-32,16,0);
      draw(g,xza+16,yza,16,0);
      draw(g,xza,yza,32,14);draw(g,xza-16,yza+16,32,14);
      //setcolor(0);moveto(xza,yza);lineto(xza+32,yza);lineto(xza+32,yza+16);
      //lineto(xza+16,yza+16);lineto(xza+16,yza+32);lineto(xza-16,yza+32);
      //lineto(xza-16,yza+16);lineto(xza,yza+16);lineto(xza,yza);
      //line(xza+16,yza,xza+16,yza+16);line(xza,yza+16,xza,yza+32);
      //line(xza,yza+16,xza+16,yza+16);
      }
      else {
        kpat=16;
      }
      break;
    case 7 :
      if (xza<=368 && yza>=56 && box[n+20]==0 && box[n+1+20]==0 && box[n+10+20]==0 && box[n-9+20]==0 && box[n+11+20]==0 && box[n+8+20]==0) {
      kpat=17;
      draw(g,xza-32,yza,32,0);draw(g,xza-16,yza+16,32,0);
      draw(g,xza,yza,32,15);draw(g,xza+16,yza-16,16,15);
      draw(g,xza,yza+16,16,15);
      //setcolor(0);moveto(xza,yza);lineto(xza+16,yza);lineto(xza+16,yza-16);
      //lineto(xza+32,yza-16);lineto(xza+32,yza+16);lineto(xza+16,yza+16);
      //lineto(xza+16,yza+32);lineto(xza,yza+32);lineto(xza,yza);
      //line(xza+16,yza,xza+32,yza);line(xza+16,yza,xza+16,yza+16);
      //line(xza,yza+16,xza+16,yza+16);
      }
      else {
        kpat=7;
      }
      break;
    case 17 :
      if (xza>=272 && box[n-1+20]==0 && box[n-2+20]==0 && box[n+9+20]==0 && box[n+10+20]==0 && box[n+11+20]==0 && box[n+8+20]==0) {
      kpat=7;
      draw(g,xza,yza,32,0);draw(g,xza+16,yza-16,16,0);
      draw(g,xza,yza+16,16,0);
      draw(g,xza-32,yza,32,15);draw(g,xza-16,yza+16,32,15);
      //setcolor(0);moveto(xza,yza);lineto(xza-32,yza);lineto(xza-32,yza+16);
      //lineto(xza-16,yza+16);lineto(xza-16,yza+32);lineto(xza+16,yza+32);
      //lineto(xza+16,yza+16);lineto(xza,yza+16);lineto(xza,yza);
      //line(xza-16,yza,xza-16,yza+16);line(xza-16,yza+16,xza,yza+16);
      //line(xza,yza+16,xza,yza+32);
      }
      else {
        kpat=17;
      }
      break;
  }
  //kpat=npat;

            break;
          case 2: 
            //sayuu1
            //pat ->kpat
            //k->kflag
            
            
  n=(yza-40)/16*10+(xza-240)/16+1;
  switch(kpat) {
    case 1 :
      if (box[n+1-kflag+20]==0 && box[n+2-kflag+20]==0 && box[n+11-kflag+20]==0 && box[n+12-kflag+20]==0) {
        draw(g,xza,yza,32,0);draw(g,xza,yza+16,32,0);
        switch(kflag) {
	  case 0 : 
            xza=xza+16;
            if (xza>368) {
              xza=368;
            }
            break;
	  case 2 :
            xza=xza-16;
            if (xza<240) {
              xza=240;
            }
            break;
        }
        draw(g,xza,yza,32,9);draw(g,xza,yza+16,32,9);
        //setcolor(0);moveto(xza,yza);lineto(xza+32,yza);lineto(xza+32,yza+32);
        //lineto(xza,yza+32);lineto(xza,yza);line(xza,yza+16,xza+32,yza+16);
        //line(xza+16,yza,xza+16,yza+32);
      }
      break;
    case 2 :
      if (box[n-10+1-kflag+20]==0 && box[n-1+1-kflag+20]==0 && box[n+1-kflag+20]==0 && box[n+1+1-kflag+20]==0) {
        draw(g,xza,yza-16,16,0);draw(g,xza-16,yza,48,0);
        switch(kflag) {
	  case 0 : 
            xza=xza+16;
            if (xza>368) {
              xza=368;
            }
            break;
	  case 2 : 
            xza=xza-16;
            if (xza<256) {
              xza=256;
            }
            break;
        }
        draw(g,xza,yza-16,16,10);draw(g,xza-16,yza,48,10);
        //setcolor(0);moveto(xza,yza);lineto(xza+16,yza);lineto(xza+16,yza+16);
        //lineto(xza+32,yza+16);lineto(xza+32,yza+32);lineto(xza-16,yza+32);
        //lineto(xza-16,yza+16);lineto(xza,yza+16);lineto(xza,yza);
        //line(xza,yza+16,xza+16,yza+16);line(xza,yza+16,xza,yza+32);
        //line(xza+16,yza+16,xza+16,yza+32);
      }
      break;
    case 3 :
      if (box[n-2+1-kflag+20]==0 && box[n-1+1-kflag+20]==0 && box[n+1-kflag+20]==0 && box[n-10+1-kflag+20]==0) {
        draw(g,xza,yza-16,16,0);draw(g,xza-32,yza,48,0);
        switch(kflag) {
	  case 0 :
            xza=xza+16;
            if (xza>384) {
              xza=384;
            }
            break;
	  case 2 :
            xza=xza-16;
            if (xza<272) {
              xza=272;
            }
            break;
        }
        draw(g,xza,yza-16,16,11);draw(g,xza-32,yza,48,11);
        //setcolor(0);moveto(xza,yza);lineto(xza+16,yza);lineto(xza+16,yza+32);
        //lineto(xza-32,yza+32);lineto(xza-32,yza+16);lineto(xza,yza+16);
        //lineto(xza,yza);line(xza,yza+16,xza+16,yza+16);
        //line(xza-16,yza+16,xza-16,yza+32);line(xza,yza+16,xza,yza+32);
      }
      break;
    case 4 :
      if (box[n-11+1-kflag+20]==0 && box[n-1+1-kflag+20]==0 && box[n+1-kflag+20]==0 && box[n+1+1-kflag+20]==0) {
        draw(g,xza-16,yza-16,16,0);draw(g,xza-16,yza,48,0);
        switch(kflag) {
	  case 0:
            xza=xza+16;
            if (xza>368) {
              xza=368;
            }
            break;
	  case 2:
            xza=xza-16;
            if (xza<256) {
              xza=256;
            } 
            break;
        }
        draw(g,xza-16,yza-16,16,12);draw(g,xza-16,yza,48,12);
        //setcolor(0);moveto(xza,yza);lineto(xza+16,yza);lineto(xza+16,yza+16);
        //lineto(xza+48,yza+16);lineto(xza+48,yza+32);lineto(xza,yza+32);
        //lineto(xza,yza);line(xza,yza+16,xza+16,yza+16);
        //line(xza+16,yza+16,xza+16,yza+32);line(xza+32,yza+16,xza+32,yza+32);
      }
      break;
    case 5 :
      if (box[n-2+1-kflag+20]==0 && box[n-1+1-kflag+20]==0 && box[n+1-kflag+20]==0 && box[n+1+1-kflag+20]==0) {
        draw(g,xza-32,yza,64,0);
        switch(kflag) {
	  case 0 : 
            xza=xza+16;
            if (xza>368) {
              xza=368;
            }
            break;
	  case 2 : 
            xza=xza-16;
            if (xza<272) {
              xza=272;
            }
            break;
        }
        draw(g,xza-32,yza,64,13);
        //setcolor(0);moveto(xza,yza);lineto(xza+64,yza);lineto(xza+64,yza+16);
        //lineto(xza,yza+16);lineto(xza,yza);line(xza+16,yza,xza+16,yza+16);
        //line(xza+32,yza,xza+32,yza+16);line(xza+48,yza,xza+48,yza+16);
      }
      break;
    case 6 :
      if (box[n+1-kflag+20]==0 && box[n+2-kflag+20]==0 && box[n+11-kflag+20]==0 && box[n+10-kflag+20]==0) {
        draw(g,xza,yza,32,0);draw(g,xza-16,yza+16,32,0);
        switch(kflag) {
	  case 0 : 
            xza=xza+16;
            if (xza>368) {
              xza=368;
            }
            break;
	  case 2 : 
            xza=xza-16;
            if (xza<256) {
              xza=256;
            }
            break;
        }
        draw(g,xza,yza,32,14);draw(g,xza-16,yza+16,32,14);
        //setcolor(0);moveto(xza,yza);lineto(xza+32,yza);lineto(xza+32,yza+16);
        //lineto(xza+16,yza+16);lineto(xza+16,yza+32);lineto(xza-16,yza+32);
        //lineto(xza-16,yza+16);lineto(xza,yza+16);lineto(xza,yza);
        //line(xza,yza+16,xza+16,yza+16);line(xza+16,yza,xza+16,yza+16);
        //line(xza,yza+16,xza,yza+32);
      }
      break;
    case 7 :
      if (box[n-kflag+20]==0 && box[n-1-kflag+20]==0 && box[n+10-kflag+20]==0 && box[n+11-kflag+20]==0) {
        draw(g,xza-32,yza,32,0);draw(g,xza-16,yza+16,32,0);
        switch(kflag) {
	  case 0 : 
            xza=xza+16;
            if (xza>384) {
              xza=384;
            }
            break;
	  case 2 : 
            xza=xza-16;
            if (xza<272) {
              xza=272;
            }
            break;
        }
        draw(g,xza-32,yza,32,15);draw(g,xza-16,yza+16,32,15);
        //setcolor(0);moveto(xza,yza);lineto(xza,yza+16);lineto(xza+16,yza+16);
        //lineto(xza+16,yza+32);lineto(xza-16,yza+32);lineto(xza-16,yza+16);
        //lineto(xza-32,yza+16);lineto(xza-32,yza);lineto(xza,yza);
        //line(xza-16,yza,xza-16,yza+16);line(xza-16,yza+16,xza,yza+16);
        //line(xza,yza+16,xza,yza+32);
      }
      break;
    case 12 :
      if (box[n+1-kflag+20]==0 && box[n-11+1-kflag+20]==0 && box[n-10+1-kflag+20]==0 && box[n-20+1-kflag+20]==0) {
        draw(g,xza-16,yza-16,32,0);draw(g,xza,yza-32,16,0);
        draw(g,xza,yza,16,0);
        switch(kflag) {
	  case 0 : 
            xza=xza+16;
            if (xza>384) {
              xza=384;
            }
            break;
	  case 2 :
            xza=xza-16;
            if (xza<256) {
              xza=256;
            }
            break;
        }
        draw(g,xza-16,yza-16,32,10);draw(g,xza,yza-32,16,10);
        draw(g,xza,yza,16,10);
        //setcolor(0);moveto(xza,yza);lineto(xza+16,yza);lineto(xza+16,yza+16);
        //lineto(xza+32,yza+16);lineto(xza+32,yza-32);lineto(xza+16,yza-32);
        //lineto(xza+16,yza-16);lineto(xza,yza-16);lineto(xza,yza);
        //line(xza+16,yza-16,xza+32,yza-16);line(xza+16,yza-16,xza+16,yza);
        //line(xza+16,yza,xza+32,yza);
      }
      break;
    case 22 :
      if (box[n-1+1-kflag+20]==0 && box[n-12+1-kflag+20]==0 && box[n-11+1-kflag+20]==0 && box[n-10+1-kflag+20]==0) {
        draw(g,xza-16,yza,16,0);draw(g,xza-32,yza-16,48,0);
        switch(kflag) {
	  case 0 : 
            xza=xza+16;
            if (xza>384) {
              xza=384;
            }
            break;
	  case 2 : 
            xza=xza-16;
            if (xza<272) {
              xza=272;
            }
            break;
        }
        draw(g,xza-16,yza,16,10);draw(g,xza-32,yza-16,48,10);
        //setcolor(0);moveto(xza,yza);lineto(xza,yza-16);lineto(xza+16,yza-16);
        //lineto(xza+16,yza-32);lineto(xza-32,yza-32);lineto(xza-32,yza-16);
        //lineto(xza-16,yza-16);lineto(xza-16,yza);lineto(xza,yza);
        //line(xza-16,yza-16,xza,yza-16);line(xza-16,yza-32,xza-16,yza-16);
        //line(xza,yza-32,xza,yza-16);
      }
      break;
    case 32 :
      if (box[n-1+1-kflag+20]==0 && box[n+1-kflag+20]==0 && box[n-11+1-kflag+20]==0 && box[n+9+1-kflag+20]==0) {
        draw(g,xza-16,yza,32,0);draw(g,xza-16,yza-16,16,0);
        draw(g,xza-16,yza+16,16,0);
        switch(kflag) {
	  case 0 : 
            xza=xza+16;
            if (xza>384) {
              xza=384;
            }
            break;
	  case 2 : 
            xza=xza-16;
            if (xza<256) {
              xza=256;
            }
            break;
        }
        draw(g,xza-16,yza,32,10);draw(g,xza-16,yza-16,16,10);
        draw(g,xza-16,yza+16,16,10);
        //setcolor(0);moveto(xza,yza);lineto(xza,yza+16);lineto(xza-16,yza+16);
        //lineto(xza-16,yza+32);lineto(xza-32,yza+32);lineto(xza-32,yza-16);
        //lineto(xza-16,yza-16);lineto(xza-16,yza);lineto(xza,yza);
        //line(xza-32,yza,xza-16,yza);line(xza-32,yza+16,xza-16,yza+16);
        //line(xza-16,yza,xza-16,yza+16);
      }
      break;
    case 13 :
      if (box[n-11+1-kflag+20]==0 && box[n-10+1-kflag+20]==0 && box[n+1-kflag+20]==0 && box[n+10+1-kflag+20]==0) {
        draw(g,xza-16,yza-16,32,0);draw(g,xza,yza,16,0);draw(g,xza,yza+16,16,0);
        switch(kflag) {
	  case 0 : 
            xza=xza+16;
            if (xza>384) {
              xza=384;
            }
            break;
	  case 2 : 
            xza=xza-16;
            if (xza<256) {
              xza=256;
            }
            break;
        }
        draw(g,xza-16,yza-16,32,11);draw(g,xza,yza,16,11);
        draw(g,xza,yza+16,16,11);
        //setcolor(0);moveto(xza,yza);lineto(xza,yza-16);lineto(xza+32,yza-16);
        //lineto(xza+32,yza+32);lineto(xza+16,yza+32);lineto(xza+16,yza);
        //lineto(xza,yza);line(xza+16,yza-16,xza+16,yza);
        //line(xza+16,yza,xza+32,yza);line(xza+16,yza+16,xza+32,yza+16);
      }
      break;
    case 23 :
      if (box[n-1+1-kflag+20]==0 && box[n-11+1-kflag+20]==0 && box[n-10+1-kflag+20]==0 && box[n-9+1-kflag+20]==0) {
        draw(g,xza-16,yza,16,0);draw(g,xza-16,yza-16,48,0);
        switch(kflag) {
	  case 0 : 
            xza=xza+16;
            if (xza>368) {
              xza=368;
            }
            break;
	  case 2 : 
            xza=xza-16;
            if (xza<256) {
              xza=256;
            }
            break;
        }
        draw(g,xza-16,yza,16,11);draw(g,xza-16,yza-16,48,11);
        //setcolor(0);moveto(xza,yza);lineto(xza-16,yza);lineto(xza-16,yza-32);
        //lineto(xza+32,yza-32);lineto(xza+32,yza-16);lineto(xza,yza-16);
        //lineto(xza,yza);line(xza-16,yza-16,xza,yza-16);
        //line(xza,yza-32,xza,yza-16);line(xza+16,yza-32,xza+16,yza-16);
      }
      break;
    case 33 :
      if (box[n+1-kflag+20]==0 && box[n-1+1-kflag+20]==0 && box[n-11+1-kflag+20]==0 && box[n-21+1-kflag+20]==0) {
        draw(g,xza-16,yza,32,0);draw(g,xza-16,yza-16,16,0);
        draw(g,xza-16,yza-32,16,0);
        switch(kflag) {
	  case 0 : 
            xza=xza+16;
            if (xza>384) {
              xza=384;
            }
            break;
	  case 2 : 
            xza=xza-16;
            if (xza<256) {
              xza=256;
            }
            break;
        }
        draw(g,xza-16,yza,32,11);draw(g,xza-16,yza-16,16,11);
        draw(g,xza-16,yza-32,16,11);
        //setcolor(0);moveto(xza,yza);lineto(xza,yza+16);lineto(xza-32,yza+16);
        //lineto(xza-32,yza-32);lineto(xza-16,yza-32);lineto(xza-16,yza);
        //lineto(xza,yza);line(xza-16,yza,xza-16,yza+16);
        //line(xza-32,yza,xza-16,yza);line(xza-32,yza-16,xza-16,yza-16);
      }
      break;
    case 14 :
      if (box[n-1+1-kflag+20]==0 && box[n+1-kflag+20]==0 && box[n-10+1-kflag+20]==0 && box[n-20+1-kflag+20]==0) {
        draw(g,xza-16,yza,32,0);draw(g,xza,yza-16,16,0);
        draw(g,xza,yza-32,16,0);
        switch(kflag) {
	  case 0 : 
            xza=xza+16;
            if (xza>384) {
              xza=384;
            } 
            break;
	  case 2 :
            xza=xza-16;
            if (xza<256) {
              xza=256;
            }
            break;
        }
        draw(g,xza-16,yza,32,12);draw(g,xza,yza-16,16,12);
        draw(g,xza,yza-32,16,12);
        //setcolor(0);moveto(xza,yza);lineto(xza+32,yza);lineto(xza+32,yza-48);
        //lineto(xza+16,yza-48);lineto(xza+16,yza-16);lineto(xza,yza-16);
        //lineto(xza,yza);line(xza+16,yza-16,xza+16,yza);
        //line(xza+16,yza-16,xza+32,yza-16);line(xza+16,yza-32,xza+32,yza-32);
      }
      break;
    case 24 :
      if (box[n+1-kflag+20]==0 && box[n-10+1-kflag+20]==0 && box[n-11+1-kflag+20]==0 && box[n-12+1-kflag+20]==0) {
        draw(g,xza,yza,16,0);draw(g,xza-32,yza-16,48,0);
        switch(kflag) {
	  case 0: 
            xza=xza+16;
            if (xza>384) {
              xza=384;
            }
            break;
	  case 2: 
            xza=xza-16;
            if (xza<272) {
              xza=272;
            }
            break;
        }
        draw(g,xza,yza,16,12);draw(g,xza-32,yza-16,48,12);
        //setcolor(0);moveto(xza,yza);lineto(xza,yza-32);lineto(xza-48,yza-32);
        //lineto(xza-48,yza-16);lineto(xza-16,yza-16);lineto(xza-16,yza);
        //lineto(xza,yza);line(xza-16,yza-16,xza,yza-16);
        //line(xza-32,yza-32,xza-32,yza-16);line(xza-16,yza-32,xza-16,yza-16);
      }
      break;
    case 34 :
      if (box[n+9+1-kflag+20]==0 && box[n-1+1-kflag+20]==0 && box[n-11+1-kflag+20]==0 && box[n-10+1-kflag+20]==0) {
        draw(g,xza-16,yza-16,32,0);draw(g,xza-16,yza,16,0);
        draw(g,xza-16,yza+16,16,0);
        switch(kflag) {
	  case 0: 
            xza=xza+16;
            if (xza>384) {
              xza=384;
            }
            break;
	  case 2: 
            xza=xza-16;
            if (xza<256) {
              xza=256;
            }
            break;
        }
        draw(g,xza-16,yza-16,32,12);draw(g,xza-16,yza,16,12);
        draw(g,xza-16,yza+16,16,12);
        //setcolor(0);moveto(xza,yza);lineto(xza,yza+16);lineto(xza-16,yza+16);
        //lineto(xza-16,yza+48);lineto(xza-32,yza+48);lineto(xza-32,yza);
        //lineto(xza,yza);line(xza-16,yza,xza-16,yza+16);
        //line(xza-32,yza+16,xza-16,yza+16);line(xza-32,yza+32,xza-16,yza+32);
      }
      break;
    case 15 :
      if (box[n+10+1-kflag+20]==0 && box[n+1-kflag+20]==0 && box[n-10+1-kflag+20]==0 && box[n-20+1-kflag+20]==0) {
        draw(g,xza,yza+16,16,0);draw(g,xza,yza,16,0);
        draw(g,xza,yza-16,16,0);draw(g,xza,yza-32,16,0);
        switch(kflag) {
	  case 0 : 
            xza=xza+16;
            if (xza>384) {
              xza=384;
            }
            break;
	  case 2 : 
            xza=xza-16;
            if (xza<240) {
              xza=240;
            }
            break;
        }
        draw(g,xza,yza+16,16,13);draw(g,xza,yza,16,13);
        draw(g,xza,yza-16,16,13);draw(g,xza,yza-32,16,13);
        //setcolor(0);moveto(xza,yza);lineto(xza+16,yza);lineto(xza+16,yza-64);
        //lineto(xza,yza-64);lineto(xza,yza);line(xza,yza-16,xza+16,yza-16);
        //line(xza,yza-32,xza+16,yza-32);line(xza,yza-48,xza+16,yza-48);
      }
      break;
    case 16 :
      if (box[n-9-kflag+20]==0 && box[n-8-kflag+20]==0 && box[n-19-kflag+20]==0 && box[n+2-kflag+20]==0) {
        draw(g,xza,yza-16,32,0);draw(g,xza,yza-32,16,0);
        draw(g,xza+16,yza,16,0);
        switch(kflag) {
	  case 0 : 
            xza=xza+16;
            if (xza>368) {
              xza=368;
            } 
            break;
	  case 2 : 
            xza=xza-16;
            if (xza<240) {
              xza=240;
            }
            break;
        }
        draw(g,xza,yza-16,32,14);draw(g,xza,yza-32,16,14);
        draw(g,xza+16,yza,16,14);
        //setcolor(0);moveto(xza,yza);lineto(xza+16,yza);lineto(xza+16,yza+16);
        //lineto(xza+32,yza+16);lineto(xza+32,yza-16);lineto(xza+16,yza-16);
        //lineto(xza+16,yza-32);lineto(xza,yza-32);lineto(xza,yza);
        //line(xza+16,yza,xza+32,yza);line(xza,yza-16,xza+16,yza-16);
        //line(xza+16,yza-16,xza+16,yza);
      }
      break;
    case 17 :
      if (box[n+1-kflag+20]==0 && box[n+2-kflag+20]==0 && box[n+11-kflag+20]==0 && box[n-8-kflag+20]==0) {
        draw(g,xza,yza,32,0);draw(g,xza,yza+16,16,0);
        draw(g,xza+16,yza-16,16,0);
        switch(kflag) {
	  case 0 : 
            xza=xza+16;
            if (xza>368) {
              xza=368;
            }
            break;
	  case 2 : 
            xza=xza-16;
            if (xza<240) {
              xza=240;
            }
            break;
        }
        draw(g,xza,yza,32,15);draw(g,xza,yza+16,16,15);
        draw(g,xza+16,yza-16,16,15);
        //setcolor(0);moveto(xza,yza);lineto(xza+16,yza);lineto(xza+16,yza-16);
        //lineto(xza+32,yza-16);lineto(xza+32,yza+16);lineto(xza+16,yza+16);
        //lineto(xza+16,yza+32);lineto(xza,yza+32);lineto(xza,yza);
        //line(xza+16,yza,xza+32,yza);line(xza+16,yza,xza+16,yza+16);
        //line(xza,yza+16,xza+16,yza+16);
      }
      break;
  }

            break;
          case 3: 
            //syori
            //pat ->kpat
            //frag ->flag
            
  
  n=(yza-40)/16*10+(xza-240)/16+1;
  switch(kpat) {
    case 1 :
      if (box[n+10+20]==0 && box[n+11+20]==0 && box[n+20+20]==0 && box[n+21+20]==0) {
        if (dir==0) {
          draw(g,xza,yza,32,0);draw(g,xza,yza+16,32,0);
          yza=yza+16;
        }
        else {
        draw(g,xza,yza,32,0);draw(g,xza,yza+16,32,0);
        yza=yza+16;
        draw(g,xza,yza,32,9);
        draw(g,xza,yza+16,32,9);
        //setcolor(0);line(xza+16,yza,xza+16,yza+32);
        //line(xza,yza+16,xza+32,yza+16);moveto(xza,yza);lineto(xza+32,yza);
        //lineto(xza+32,yza+32);lineto(xza,yza+32);lineto(xza,yza);
        }
      }
      else {
        if (dir==0) {
          flag=1;
          draw(g,xza,yza,32,9);draw(g,xza,yza+16,32,9);

        }
        else {
        flag=1;box[n+20]=9;box[n+1+20]=9;box[n+10+20]=9;box[n+11+20]=9;
        }
      }
      break;
    case 2 :
      if (box[n+9+20]==0 && box[n+10+20]==0 && box[n+11+20]==0 && box[n+20]==0) {
        if (dir==0) {
          draw(g,xza,yza-16,16,0);draw(g,xza-16,yza,48,0);
          yza=yza+16;
        }
        else {
        draw(g,xza,yza-16,16,0);draw(g,xza-16,yza,48,0);
        yza=yza+16;
        draw(g,xza,yza-16,16,10);draw(g,xza-16,yza,48,10);
        //setcolor(0);moveto(xza,yza);lineto(xza+16,yza);lineto(xza+16,yza+32);
        //lineto(xza,yza+32);lineto(xza,yza);moveto(xza-16,yza+16);
        //lineto(xza+32,yza+16);lineto(xza+32,yza+32);lineto(xza-16,yza+32);
        //lineto(xza-16,yza+16);
        }
      }
      else {
        if (dir==0) {
          flag=1;
          draw(g,xza,yza-16,16,10);draw(g,xza-16,yza,48,10);

        }
        else {
        flag=1;box[n-1+20]=10;box[n+20]=10;box[n+1+20]=10;box[n-10+20]=10;
        }
      }
      break;
    case 3 :
      if (box[n+8+20]==0 && box[n+9+20]==0 && box[n+10+20]==0 && box[n+20]==0) {
        if (dir==0) {
          draw(g,xza,yza-16,16,0);draw(g,xza-32,yza,48,0);
          yza=yza+16;
        }
        else {
        draw(g,xza,yza-16,16,0);draw(g,xza-32,yza,48,0);
        yza=yza+16;
        draw(g,xza,yza-16,16,11);draw(g,xza-32,yza,48,11);
        //setcolor(0);moveto(xza,yza);lineto(xza+16,yza);lineto(xza+16,yza+32);
        //lineto(xza-32,yza+32);lineto(xza-32,yza+16);lineto(xza,yza+16);
        //lineto(xza,yza);line(xza-16,yza+16,xza-16,yza+32);
        //line(xza,yza+16,xza,yza+32);line(xza,yza+16,xza+16,yza+16);
        }
      }
      else {
        if (dir==0) {
          flag=1;
          draw(g,xza,yza-16,16,11);draw(g,xza-32,yza,48,11);

        }
        else {
	flag=1;box[n-2+20]=11;box[n-1+20]=11;box[n+20]=11;box[n-10+20]=11;
        }
      }
      break;
    case 4 :
      if (box[n-1+20]==0 && box[n+9+20]==0 && box[n+10+20]==0 && box[n+11+20]==0) {
        if (dir==0) {
          draw(g,xza-16,yza-16,16,0);draw(g,xza-16,yza,48,0);
          yza=yza+16;
        }
        else {
        draw(g,xza-16,yza-16,16,0);draw(g,xza-16,yza,48,0);
        yza=yza+16;
        draw(g,xza-16,yza-16,16,12);draw(g,xza-16,yza,48,12);
        //setcolor(0);line(xza,yza+16,xza+16,yza+16);
        //line(xza+16,yza+16,xza+16,yza+32);
        //line(xza+32,yza+16,xza+32,yza+32);
        //moveto(xza,yza);lineto(xza+16,yza);lineto(xza+16,yza+16);
        //lineto(xza+48,yza+16);lineto(xza+48,yza+32);lineto(xza,yza+32);
        //lineto(xza,yza);
        }
      }
      else {
        if (dir==0) {
          flag=1;
          draw(g,xza-16,yza-16,16,12);draw(g,xza-16,yza,48,12);

        }
        else {
	flag=1;box[n-11+20]=12;box[n-1+20]=12;box[n+20]=12;box[n+1+20]=12;
        }
      }
      break;
    case 5 :
      if (box[n+8+20]==0 && box[n+9+20]==0 && box[n+10+20]==0 && box[n+11+20]==0) {
        if (dir==0) {
          draw(g,xza-32,yza,64,0);
          yza=yza+16;
        }
        else {
        draw(g,xza-32,yza,64,0);
        yza=yza+16;
        draw(g,xza-32,yza,64,13);
        //setcolor(0);line(xza+16,yza,xza+16,yza+16);
        //line(xza+32,yza,xza+32,yza+16);line(xza+48,yza,xza+48,yza+16);
        //moveto(xza,yza);lineto(xza+64,yza);lineto(xza+64,yza+16);
        //lineto(xza,yza+16);lineto(xza,yza);
        }
      }
      else {
        if (dir==0) {
          flag=1;
          draw(g,xza-32,yza,64,13);

        }
        else {
	flag=1;box[n-2+20]=13;box[n-1+20]=13;box[n+20]=13;box[n+1+20]=13;
        }
      }
      break;
    case 6 :
      if (box[n+10+20]==0 && box[n+11+20]==0 && box[n+20+20]==0 && box[n+19+20]==0) {
        if (dir==0) {
          draw(g,xza,yza,32,0);draw(g,xza-16,yza+16,32,0);
          yza=yza+16;
        }
        else {
        draw(g,xza,yza,32,0);draw(g,xza-16,yza+16,32,0);
        yza=yza+16;
        draw(g,xza,yza,32,14);draw(g,xza-16,yza+16,32,14);
        //setcolor(0);line(xza,yza+16,xza+16,yza+16);
        //line(xza+16,yza,xza+16,yza+16);line(xza,yza+16,xza,yza+32);
        //moveto(xza,yza);lineto(xza+32,yza);lineto(xza+32,yza+16);
        //lineto(xza+16,yza+16);lineto(xza+16,yza+32);lineto(xza-16,yza+32);
        //lineto(xza-16,yza+16);lineto(xza,yza+16);lineto(xza,yza);
        }
      }
      else {
        if (dir==0) {
          flag=1;
          draw(g,xza,yza,32,14);draw(g,xza-16,yza+16,32,14);

        }
        else {
	flag=1;box[n+20]=14;box[n+1+20]=14;box[n+10+20]=14;box[n+9+20]=14;
        }
      }
      break;
    case 7 :
      if (box[n+8+20]==0 && box[n+9+20]==0 && box[n+20+20]==0 && box[n+19+20]==0) {
        if (dir==0) {
          draw(g,xza-32,yza,32,0);draw(g,xza-16,yza+16,32,0);
          yza=yza+16;
        }
        else {
        draw(g,xza-32,yza,32,0);draw(g,xza-16,yza+16,32,0);
        yza=yza+16;
        draw(g,xza-32,yza,32,15);draw(g,xza-16,yza+16,32,15);
        //setcolor(0);line(xza-16,yza,xza-16,yza+16);
        //line(xza-16,yza+16,xza,yza+16);line(xza,yza+16,xza,yza+32);
        //moveto(xza,yza);lineto(xza,yza+16);lineto(xza+16,yza+16);
        //lineto(xza+16,yza+32);lineto(xza-16,yza+32);lineto(xza-16,yza+16);
        //lineto(xza-32,yza+16);lineto(xza-32,yza);lineto(xza,yza);
        }
      }
      else {
        if (dir==0) {
          flag=1;
          draw(g,xza-32,yza,32,15);draw(g,xza-16,yza+16,32,15);

        }
        else {
	flag=1;box[n-1+20]=15;box[n-2+20]=15;box[n+9+20]=15;box[n+10+20]=15;
        }
      }
      break;
    case 12 :
      if (box[n+10+20]==0 && box[n-1+20]==0 && box[n+20]==0 && box[n-10+20]==0) {
        if (dir==0) {
          draw(g,xza-16,yza-16,32,0);draw(g,xza,yza-32,16,0);
          draw(g,xza,yza,16,0);
          yza=yza+16;
        }
        else {
        draw(g,xza-16,yza-16,32,0);draw(g,xza,yza-32,16,0);
        draw(g,xza,yza,16,0);
        yza=yza+16;
        draw(g,xza-16,yza-16,32,10);draw(g,xza,yza-32,16,10);
        draw(g,xza,yza,16,10);
        //setcolor(0);line(xza+16,yza-16,xza+16,yza);
        //line(xza+16,yza-16,xza+32,yza-16);line(xza+16,yza,xza+32,yza);
        //moveto(xza,yza);lineto(xza+16,yza);lineto(xza+16,yza+16);
        //lineto(xza+32,yza+16);lineto(xza+32,yza-32);lineto(xza+16,yza-32);
        //lineto(xza+16,yza-16);lineto(xza,yza-16);lineto(xza,yza);
        }
      }
      else {
        if (dir==0) {
          flag=1;
          draw(g,xza-16,yza-16,32,10);draw(g,xza,yza-32,16,10);
          draw(g,xza,yza,16,10);

        }
        else {
	flag=1;box[n+20]=10;box[n-11+20]=10;box[n-10+20]=10;box[n-20+20]=10;
        }
      }
      break;
    case 22 :
      if (box[n+9+20]==0 && box[n+20]==0 && box[n-1+20]==0 && box[n-2+20]==0) {
        if (dir==0) {
          draw(g,xza-16,yza,16,0);draw(g,xza-32,yza-16,48,0);
          yza=yza+16;
        }
        else {
        draw(g,xza-16,yza,16,0);draw(g,xza-32,yza-16,48,0);
        yza=yza+16;
        draw(g,xza-16,yza,16,10);draw(g,xza-32,yza-16,48,10);
        //setcolor(0);line(xza-16,yza-16,xza,yza-16);
        //line(xza-16,yza-32,xza-16,yza-16);line(xza,yza-32,xza,yza-16);
        //moveto(xza,yza);lineto(xza,yza-16);lineto(xza+16,yza-16);
        //lineto(xza+16,yza-32);lineto(xza-32,yza-32);lineto(xza-32,yza-16);
        //lineto(xza-16,yza-16);lineto(xza-16,yza);lineto(xza,yza);
        }
      }
      else {
        if (dir==0) {
          flag=1;
          draw(g,xza-16,yza,16,10);draw(g,xza-32,yza-16,48,10);

        }
        else {
	flag=1;box[n-1+20]=10;box[n-10+20]=10;box[n-11+20]=10;box[n-12+20]=10;
        }
      }
      break;
    case 32 :
      if (box[n+10+20]==0 && box[n+19+20]==0 && box[n+9+20]==0 && box[n-1+20]==0) {
        if (dir==0) {
          draw(g,xza-16,yza,32,0);draw(g,xza-16,yza-16,16,0);
          draw(g,xza-16,yza+16,16,0);
          yza=yza+16;
        }
        else {
        draw(g,xza-16,yza,32,0);draw(g,xza-16,yza-16,16,0);
        draw(g,xza-16,yza+16,16,0);
        yza=yza+16;
        draw(g,xza-16,yza,32,10);draw(g,xza-16,yza-16,16,10);
        draw(g,xza-16,yza+16,16,10);
        //setcolor(0);line(xza-32,yza,xza-16,yza);
        //line(xza-32,yza+16,xza-16,yza+16);line(xza-16,yza,xza-16,yza+16);
        //moveto(xza,yza);lineto(xza,yza+16);lineto(xza-16,yza+16);
        //lineto(xza-16,yza+32);lineto(xza-32,yza+32);lineto(xza-32,yza-16);
        //lineto(xza-16,yza-16);lineto(xza-16,yza);lineto(xza,yza);
        }
      }
      else {
        if (dir==0) {
          flag=1;
          draw(g,xza-16,yza,32,10);draw(g,xza-16,yza-16,16,10);
          draw(g,xza-16,yza+16,16,10);

        }
        else {
	flag=1;box[n+20]=10;box[n-1+20]=10;box[n+9+20]=10;box[n-11+20]=10;
        }
      }
      break;
    case 13 :
      if (box[n-1+20]==0 && box[n+20]==0 && box[n+10+20]==0 && box[n+20+20]==0) {
        if (dir==0) {
          draw(g,xza-16,yza-16,32,0);draw(g,xza,yza,16,0);
          draw(g,xza,yza+16,16,0);
          yza=yza+16;
        }
        else {
        draw(g,xza-16,yza-16,32,0);draw(g,xza,yza,16,0);
        draw(g,xza,yza+16,16,0);
        yza=yza+16;
        draw(g,xza-16,yza-16,32,11);draw(g,xza,yza,16,11);
        draw(g,xza,yza+16,16,11);
        //setcolor(0);line(xza+16,yza-16,xza+16,yza);
        //line(xza+16,yza,xza+32,yza);line(xza+16,yza+16,xza+32,yza+16);
        //moveto(xza,yza);lineto(xza+16,yza);lineto(xza+16,yza+32);
        //lineto(xza+32,yza+32);lineto(xza+32,yza-16);lineto(xza,yza-16);
        //lineto(xza,yza);
        }
      }
      else {
        if (dir==0) {
          flag=1;
          draw(g,xza-16,yza-16,32,11);draw(g,xza,yza,16,11);
          draw(g,xza,yza+16,16,11);

        }
        else {
	flag=1;box[n-11+20]=11;box[n-10+20]=11;box[n+20]=11;box[n+10+20]=11;
        }
      }
      break;
    case 23 :
      if (box[n+9+20]==0 && box[n-1+20]==0 && box[n+20]==0 && box[n+1+20]==0) {
        if (dir==0) {
          draw(g,xza-16,yza,16,0);draw(g,xza-16,yza-16,48,0);
          yza=yza+16;
        }
        else {
        draw(g,xza-16,yza,16,0);draw(g,xza-16,yza-16,48,0);
        yza=yza+16;
        draw(g,xza-16,yza,16,11);draw(g,xza-16,yza-16,48,11);
        //setcolor(0);line(xza-16,yza-16,xza,yza-16);
        //line(xza,yza-32,xza,yza-16);line(xza+16,yza-32,xza+16,yza-16);
        //moveto(xza,yza);lineto(xza-16,yza);lineto(xza-16,yza-32);
        //lineto(xza+32,yza-32);lineto(xza+32,yza-16);lineto(xza,yza-16);
        //lineto(xza,yza);
        }
      }
      else {
        if (dir==0) {
          flag=1;
          draw(g,xza-16,yza,16,11);draw(g,xza-16,yza-16,48,11);

        }
        else {
	flag=1;box[n-1+20]=11;box[n-11+20]=11;box[n-10+20]=11;box[n-9+20]=11;
        }
      }
      break;
    case 33 :
      if (box[n+10+20]==0 && box[n+9+20]==0 && box[n-1+20]==0 && box[n-11+20]==0) {
        if (dir==0) {
          draw(g,xza-16,yza,32,0);draw(g,xza-16,yza-16,16,0);
          draw(g,xza-16,yza-32,16,0);
          yza=yza+16;
        }
        else {
        draw(g,xza-16,yza,32,0);draw(g,xza-16,yza-16,16,0);
        draw(g,xza-16,yza-32,16,0);
        yza=yza+16;
        draw(g,xza-16,yza,32,11);draw(g,xza-16,yza-16,16,11);
        draw(g,xza-16,yza-32,16,11);
        //setcolor(0);line(xza-32,yza-16,xza-16,yza-16);
        //line(xza-32,yza,xza-16,yza);line(xza-16,yza,xza-16,yza+16);
        //moveto(xza,yza);lineto(xza,yza+16);lineto(xza-32,yza+16);
        //lineto(xza-32,yza-32);lineto(xza-16,yza-32);lineto(xza-16,yza);
        //lineto(xza,yza);
        }
      }
      else {
        if (dir==0) {
          flag=1;
          draw(g,xza-16,yza,32,11);draw(g,xza-16,yza-16,16,11);
          draw(g,xza-16,yza-32,16,11);

        }
        else {
	flag=1;box[n+20]=11;box[n-1+20]=11;box[n-11+20]=11;box[n-21+20]=11;
        }
      }
      break;
    case 14 :
      if (box[n+9+20]==0 && box[n+10+20]==0 && box[n+20]==0 && box[n-10+20]==0) {
        if (dir==0) {
          draw(g,xza-16,yza,32,0);draw(g,xza,yza-16,16,0);
          draw(g,xza,yza-32,16,0);
          yza=yza+16;
        }
        else {
        draw(g,xza-16,yza,32,0);draw(g,xza,yza-16,16,0);
        draw(g,xza,yza-32,16,0);
        yza=yza+16;
        draw(g,xza-16,yza,32,12);draw(g,xza,yza-16,16,12);
        draw(g,xza,yza-32,16,12);
        //setcolor(0);line(xza+16,yza-16,xza+16,yza);
        //line(xza+16,yza-16,xza+32,yza-16);line(xza+16,yza-32,xza+32,yza-32);
        //moveto(xza,yza);lineto(xza,yza-16);lineto(xza+16,yza-16);
        //lineto(xza+16,yza-48);lineto(xza+32,yza-48);lineto(xza+32,yza);
        //lineto(xza,yza);
        }
      }
      else {
        if (dir==0) {
          flag=1;
          draw(g,xza-16,yza,32,12);draw(g,xza,yza-16,16,12);
          draw(g,xza,yza-32,16,12);

        }
        else {
	flag=1;box[n-1+20]=12;box[n+20]=12;box[n-10+20]=12;box[n-20+20]=12;
        }
      }
      break;
    case 24 :
      if (box[n+10+20]==0 && box[n+20]==0 && box[n-1+20]==0 && box[n-2+20]==0) {
        if (dir==0) {
          draw(g,xza,yza,16,0);draw(g,xza-32,yza-16,48,0);
          yza=yza+16;
        }
        else {
        draw(g,xza,yza,16,0);draw(g,xza-32,yza-16,48,0);
        yza=yza+16;
        draw(g,xza,yza,16,12);draw(g,xza-32,yza-16,48,12);
        //setcolor(0);line(xza-16,yza-16,xza,yza-16);
        //line(xza-32,yza-32,xza-32,yza-16);line(xza-16,yza-32,xza-16,yza-16);
        //moveto(xza,yza);lineto(xza,yza-32);lineto(xza-48,yza-32);
        //lineto(xza-48,yza-16);lineto(xza-16,yza-16);lineto(xza-16,yza);
        //lineto(xza,yza);
        }
      }
      else {
        if (dir==0) {
          flag=1;
          draw(g,xza,yza,16,12);draw(g,xza-32,yza-16,48,12);

        }
        else {
	flag=1;box[n+20]=12;box[n-10+20]=12;box[n-11+20]=12;box[n-12+20]=12;
        }
      }
      break;
    case 34 :
      if (box[n+19+20]==0 && box[n+9+20]==0 && box[n-1+20]==0 && box[n+20]==0) {
        if (dir==0) {
          draw(g,xza-16,yza-16,32,0);draw(g,xza-16,yza,16,0);
          draw(g,xza-16,yza+16,16,0);
          yza=yza+16;
        }
        else {
        draw(g,xza-16,yza-16,32,0);draw(g,xza-16,yza,16,0);
        draw(g,xza-16,yza+16,16,0);
        yza=yza+16;
        draw(g,xza-16,yza-16,32,12);draw(g,xza-16,yza,16,12);
        draw(g,xza-16,yza+16,16,12);
        //setcolor(0);line(xza-16,yza,xza-16,yza+16);
        //line(xza-32,yza+16,xza-16,yza+16);line(xza-32,yza+32,xza-16,yza+32);
        //moveto(xza,yza);lineto(xza-32,yza);lineto(xza-32,yza+48);
        //lineto(xza-16,yza+48);lineto(xza-16,yza+16);lineto(xza,yza+16);
        //lineto(xza,yza);
        }
      }
      else {
        if (dir==0) {
          flag=1;
          draw(g,xza-16,yza-16,32,12);draw(g,xza-16,yza,16,12);
          draw(g,xza-16,yza+16,16,12);

        }
        else {
	flag=1;box[n+9+20]=12;box[n-1+20]=12;box[n-11+20]=12;box[n-10+20]=12;
        }
      }
      break;
    case 15 :
      if (box[n+20+20]==0 && box[n+10+20]==0 && box[n+20]==0 && box[n-10+20]==0) {
        if (dir==0) {
          draw(g,xza,yza+16,16,0);draw(g,xza,yza,16,0);
          draw(g,xza,yza-16,16,0);draw(g,xza,yza-32,16,0);
          yza=yza+16;
        }
        else {
        draw(g,xza,yza+16,16,0);draw(g,xza,yza,16,0);
        draw(g,xza,yza-16,16,0);draw(g,xza,yza-32,16,0);
        yza=yza+16;
        draw(g,xza,yza+16,16,13);draw(g,xza,yza,16,13);
        draw(g,xza,yza-16,16,13);draw(g,xza,yza-32,16,13);
        //setcolor(0);moveto(xza,yza);lineto(xza,yza-64);lineto(xza+16,yza-64);
        //lineto(xza+16,yza);lineto(xza,yza);
        //line(xza,yza-16,xza+16,yza-16);line(xza,yza-32,xza+16,yza-32);
        //line(xza,yza-48,xza+16,yza-48);
        }
      }
      else {
        if (dir==0) {
          flag=1;
          draw(g,xza,yza+16,16,13);draw(g,xza,yza,16,13);
          draw(g,xza,yza-16,16,13);draw(g,xza,yza-32,16,13);

        }
        else {
	flag=1;box[n+10+20]=13;box[n+20]=13;box[n-10+20]=13;box[n-20+20]=13;
        }
      }
      break;
    case 16 :
      if (box[n+20]==0 && box[n+1+20]==0 && box[n+11+20]==0 && box[n-10+20]==0) {
        if (dir==0) {
          draw(g,xza,yza-16,32,0);draw(g,xza,yza-32,16,0);
          draw(g,xza+16,yza,16,0);
          yza=yza+16;
        }
        else {
        draw(g,xza,yza-16,32,0);draw(g,xza,yza-32,16,0);
        draw(g,xza+16,yza,16,0);
        yza=yza+16;
        draw(g,xza,yza-16,32,14);draw(g,xza,yza-32,16,14);
        draw(g,xza+16,yza,16,14);
        //setcolor(0);moveto(xza,yza);lineto(xza,yza-32);lineto(xza+16,yza-32);
        //lineto(xza+16,yza-16);lineto(xza+32,yza-16);lineto(xza+32,yza+16);
        //lineto(xza+16,yza+16);lineto(xza+16,yza);lineto(xza,yza);
        //line(xza,yza-16,xza+16,yza-16);line(xza+16,yza,xza+32,yza);
        //line(xza+16,yza-16,xza+16,yza);
        }
      }
      else {
        if (dir==0) {
          flag=1;
          draw(g,xza,yza-16,32,14);draw(g,xza,yza-32,16,14);
          draw(g,xza+16,yza,16,14);
 
        }
        else {
	flag=1;box[n-10+20]=14;box[n-9+20]=14;box[n-20+20]=14;box[n+1+20]=14;
        }
      }
      break;
    case 17 :
      if (box[n+10+20]==0 && box[n+20+20]==0 && box[n+11+20]==0 && box[n+1+20]==0) {
        if (dir==0) {
          draw(g,xza,yza,32,0);draw(g,xza+16,yza-16,16,0);
          draw(g,xza,yza+16,16,0);
          yza=yza+16;
        }
        else {
        draw(g,xza,yza,32,0);draw(g,xza+16,yza-16,16,0);
        draw(g,xza,yza+16,16,0);
        yza=yza+16;
        draw(g,xza,yza,32,15);draw(g,xza+16,yza-16,16,15);
        draw(g,xza,yza+16,16,15);
        //setcolor(0);
        //moveto(xza,yza);lineto(xza,yza+32);lineto(xza+16,yza+32);
        //lineto(xza+16,yza+16);lineto(xza+32,yza+16);lineto(xza+32,yza-16);
        //lineto(xza+16,yza-16);lineto(xza+16,yza);lineto(xza,yza);
        //line(xza+16,yza,xza+32,yza);line(xza+16,yza,xza+16,yza+16);
        //line(xza,yza+16,xza+16,yza+16);
        }
      }
      else {
        if (dir==0) {
          flag=1;
          draw(g,xza,yza,32,15);draw(g,xza+16,yza-16,16,15);
          draw(g,xza,yza+16,16,15);

        }
        else {
	flag=1;box[n+20]=15;box[n+1+20]=15;box[n-9+20]=15;box[n+10+20]=15;
        }
      }
      break;
  }

            break;
          case 4:
            //linekesi
            
            int nn,count,xx,yy,chip;


  chip=0;
  for (int i=1;i<5;i++) {
    nn=190;
    do {
      count=1;
      //
      //
      for (int j=1;j<11;j++)   {
        if (box[nn+j+20]==0) {
          count=0;
        }
      }
      nn=nn-10;
    } while (nn>=0 && count!=1);
    if (count==1) {
	nn=nn+10;
	for (int ii=nn+10;ii>=11;ii--) {
          box[ii+20]=box[ii-10+20];
        }
	for (int ii=nn+10;ii>=1;ii--)  {
	  if (box[ii+20]!=0) {
	    xx=240+((ii-1) % 10)*16;
	    yy=40+16*((ii-1) / 10);
	    draw(g,xx,yy,16,box[ii+20]);
	    //setcolor(0);moveto(xx,yy);lineto(xx+16,yy);lineto(xx+16,yy+16);
	    //lineto(xx,yy+16);lineto(xx,yy);
	  }
	  if (box[ii+20]==0) {
	    xx=240+((ii-1) % 10)*16;
	    yy=40+16*((ii-1) / 10);
	    draw(g,xx,yy,16,0);
	  }
	}
	chip=chip+1;
	ten=ten+10*chip;
        //gotoxy(3,3);write('SCORE : ',ten);
        label1.setText("SCORE:" + ten);
    }
  }

            break;
          case 5:
            //yokoku
            
            int x,y;

  //setcolor(15);
  //moveto(500,40);lineto(596,40);lineto(596,136);lineto(500,136);
  //lineto(500,40);draw(501,40,95,0);
  g.setColor(Color.black);
  g.fillRect(100,88,96,96);         


  switch(nextpat) {
    case 1 :
      x=132;y=120;
      draw(g,x,y,32,9);draw(g,x,y+16,32,9);
      break;
    case 2 :
      x=132;y=120;
      draw(g,x,y,16,10);draw(g,x-16,y+16,48,10);
      break;
    case 3 :
      x=148;y=120;
      draw(g,x,y,16,11);draw(g,x-32,y+16,48,11);
      break;
    case 4 :
      x=116;y=120;
      draw(g,x,y,16,12);draw(g,x,y+16,48,12);
      break;
    case 5 :
      x=116;y=120;
      draw(g,x,y,64,13);
      break;
    case 6 :
      x=132;y=120;
      draw(g,x,y,32,14);draw(g,x-16,y+16,32,14);
      break;
    case 7 :
      x=148;y=120;
      draw(g,x-32,y,32,15);draw(g,x-16,y+16,32,15);
      break;
  }

            break;
          case 0:
            g.setColor(Color.black);
            g.fillRect(240,88,161,273); 
            g.fillRect(100,88,96,96);                 
            break;
    }//switch end

  }//paint end

//  public boolean action(Event evt,Object arg) {
//    if (evt.target instanceof Button) {
//      if (arg.equals("START")) {
//        game_flag=false;
//        if (clock!=null) {
//          clock.stop();
//          clock=null;
//        }
//
//        if (clock==null) {
//          clock=new Thread(this);
//          clock.start();
//        }
//
//        pflag=0;
//        repaint();
//
//        ten=0;
//random
//        nextpat=1;
//        dir=1;
//        for (int i=0;i<221;i++) {
//          box[i]=0;
//        }
//        for (int i=221;i<231;i++) {
//          box[i]=1;
//        }
        //
        //
        
//        xza=320;
//        yza=40;
//        flag=0;
//        kpat=nextpat;
//        pattern();//->pat
//        nextpat=pat;
//       yokoku();//<-nextpat
//        game_flag=true;

//      }//START end
//      return true;
//    }
//    return false;
//  }

  //draw
 public void draw(Graphics g,int x,int y,int l,int nn) {
    //setcolor(nn);
    if (nn==0) {
      g.setColor(Color.black);
    }
    else if (nn==9) {
      g.setColor(Color.blue);
    }
    else if (nn==10) {
      g.setColor(Color.cyan);
    }
    else if (nn==11) {
      g.setColor(Color.green);
    }
    else if (nn==12) {
      g.setColor(Color.magenta);
    }
    else if (nn==13) {
      g.setColor(Color.pink);
    }
    else if (nn==14) {
      g.setColor(Color.red);
    }
    else {
      g.setColor(Color.yellow);
    }
    for (int i=1;i<16;i++) {
      if (y>=88) {
        g.drawLine(x,y+i,x+l-1,y+i);
      }
    }
    g.setColor(Color.black);
    if (y>=88) {
      for (int i=0;i*16<l;i++) {
        g.drawLine(x+16*i,y,x+16*i,y+16);
      }
    }
 }

  //draw end



  //pattern
  public void pattern() {
    int typ;
    float f1;

    f1=r.nextFloat();
    typ=Math.round(f1*7)+1;
    //typ=random(7)+1;
    switch (typ) {
      case 1 : 
        pat=1;
        break;
      case 2 : 
        pat=2;
        break;
      case 3 : 
        pat=3;
        break;
      case 4 : 
        pat=4;
        break;
      case 5 : 
        pat=5;
        break;
      case 6 : 
        pat=6;
        break;
      case 7 : 
        pat=7;
        break;
      case 8 : 
        pat=1;
        break;
    }
  }

  //pattern end

  public boolean keyUp(Event evt,int code) {
    dir=1;
    switch (code) {
      case 'k':
        dir=5;
        
        break;
      case 'K':
        dir=5;
        
        break;
      case 'j':
        dir=4;
        
        break;
      case 'J':
        dir=4;
        
        break;
      case 'l':
        dir=6;
        
        break;
      case 'L':
        dir=6;
        
        break;
      case ' ':
        dir=0;
        break;
    }
    return true;
  }
  //public boolean keyUp(Event evt,int code) {
  //  dir=1;
  //  return true;
  //}

  public boolean mouseDown(Event evt,int x,int y) {
        game_flag=false;
        if (clock!=null) {
          clock.stop();
          clock=null;
        }
        pflag=0;
        repaint();

        if (clock==null) {
          clock=new Thread(this);
          clock.start();
        }

        
        ten=0;
        label1.setText("SCORE:" + ten);
        label2.setText("HIGH:" + hten);

        pattern();//->pat
        nextpat=pat;

        dir=1;
        for (int i=0;i<221;i++) {
          box[i]=0;
        }
        for (int i=221;i<241;i++) {
          box[i]=1;
        }
        //
        //
        
        xza=320;
        yza=40;
        flag=0;
        kpat=nextpat;
        pattern();//->pat
        nextpat=pat;
        //yokoku();//<-nextpat
        yok_flag=true;
        game_flag=true;
        kazu=0;

        return true;
  }


}//teto end



//{$i pattern.lib}
//{$i syori.lib}
//{$i houkou.lib}
//{$i keys.lib}
//{$i sayuu1.lib}
//{$i linekesi.lib}
//{$i yokoku.lib}
  
//      randomize;
//      cleardevice;
  //ten:=0;nextpat:=1;
//      setcolor(15);
//      for i:=1 to 5 do begin
//      moveto(240-2*i,40-2*i);lineto(240-2*i,360+2*i);
//      lineto(400+2*i,360+2*i);
//  lineto(400+2*i,40-2*i);end;
  //for i:=-20 to 200 do box[i]:=0;
  //for i:=201 to 210 do box[i]:=1;
  //repeat
  //xza:=320;yza:=40;flag:=0;
  //kpat:=nextpat;
  //pattern(pat);nextpat:=pat;yokoku(nextpat);

  //while flag=0 do begin
  //delay(500);keys(dir);
  //case dir of
  //  5: houkou(xza,yza,kpat);
  //  6: sayuu1(0,kpat,xza,yza);
  //  4: sayuu1(2,kpat,xza,yza);
  //end;
  //syori(xza,yza,kpat,flag);
  //if dir=0 then begin
  //  while frag=0 do syori(xza,yza,kpat,flag);
  //end;
  //end;
  //linekesi(ten);
  //until (box[1]=1) or (box[2]=1) or (box[3]=1) or (box[4]=1) or (box[5]=1)
  //or (box[6]=1) or (box[7]=1) or (box[8]=1) or (box[9]=1) or (box[10]=1);

  