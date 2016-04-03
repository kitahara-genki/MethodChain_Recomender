import java.applet.*;
import java.awt.*;
import java.net.*;
import java.io.*;
import java.lang.Math;
import java.util.*;
import java.awt.image.*;

public class tosoko extends Applet
{
  int x_ofs,y_ofs;
  int x_pos,y_pos,x_len,y_len;
  int old_x_pos,old_y_pos;
  int dir;
  int ban[][];
  int old_ban[][];
  int okiba[][];
  int gyaku_i[];
  int gyaku_j[];
  int num;
  int x_num;
  int y_num;
  int ini_x_pos,ini_y_pos;
  Image pzl_images[];
  
  boolean alldraw_flag,game_flag;

  Button button_start=new Button("START");
  Label label1=new Label("PUSH START !!");

  
  /** 倉庫番ゲームアプレット  */

  public void init() {
    setBackground(Color.black);
    label1.setBackground(Color.black);
    label1.setForeground(Color.white);
    x_num=Integer.parseInt(getParameter("x_number"));
    y_num=Integer.parseInt(getParameter("y_number"));
    x_ofs=Integer.parseInt(getParameter("x_offset"));
    y_ofs=Integer.parseInt(getParameter("y_offset"));
    x_len=Integer.parseInt(getParameter("x_width"));
    y_len=Integer.parseInt(getParameter("y_height"));
    String pref=getParameter("file");

    //x_ofs=50;
    //y_ofs=50;
    //x_len=20;
    //y_len=20;
    //x_num=20;
    //y_num=10;
    ban=new int[x_num][y_num];
    old_ban=new int[x_num][y_num];
    okiba=new int[x_num][y_num];
    gyaku_i=new int[100];
    gyaku_j=new int[100];        

      
    try {
    URL fsrc=getCodeBase();
    URL ffsrc=new URL(fsrc,pref);
    DataInputStream dis=null;
    dis=new DataInputStream(ffsrc.openStream());
    

    for(int i=0;i<x_num;i++) {
      for(int j=0;j<y_num;j++) {
        old_ban[i][j]=0;
        okiba[i][j]=0;  
      }
    }
    num=0;

    String ststr,ststr2;
    char ji;
    int ii,jj,kk;

    do {

      ststr=dis.readLine();
      ji=ststr.charAt(0);

      if (ji=='P') {
        ststr2=ststr.substring(1,3);
        ii=Integer.parseInt(ststr2);
        ststr2=ststr.substring(3,5);
        jj=Integer.parseInt(ststr2);
        ststr2=ststr.substring(5,6);
        kk=Integer.parseInt(ststr2);
        old_ban[ii][jj]=kk;
       
      }
      else if (ji=='G') {
        ststr2=ststr.substring(1,3);
        ii=Integer.parseInt(ststr2);
        ststr2=ststr.substring(3,5);
        jj=Integer.parseInt(ststr2);
        gyaku_i[num]=ii;
        gyaku_j[num]=jj;
        okiba[ii][jj]=3;
        num=num+1;
      }
      else if (ji=='M') {
        ststr2=ststr.substring(1,3);
        ii=Integer.parseInt(ststr2);
        ststr2=ststr.substring(3,5);
        jj=Integer.parseInt(ststr2);

        ini_x_pos=ii;
        ini_y_pos=jj;
      }
        
    } while(ji!='Q');    

    
    }
  catch (IOException e)
    {
    }

    
    pzl_images=new Image[7];

    pzl_images[0]=getImage(getCodeBase(),"hidari.jpg");//left
    pzl_images[4]=getImage(getCodeBase(),"migi.jpg");//right
    pzl_images[5]=getImage(getCodeBase(),"ue.jpg");//up
    pzl_images[6]=getImage(getCodeBase(),"sita.jpg");//down

    pzl_images[1]=getImage(getCodeBase(),"wall.jpg");//wall
    pzl_images[2]=getImage(getCodeBase(),"nimotu.jpg");//nimotu
    pzl_images[3]=getImage(getCodeBase(),"okiba.jpg");//okiba
    

    
    MediaTracker mt=new MediaTracker(this);
    for (int i=0;i<7;i++) {
      mt.addImage(pzl_images[i],i,x_len,y_len);
    }
    try {
      mt.waitForAll();
    }
    catch (InterruptedException e) {};

    add(button_start);
    add(label1);

  }  //init() の終わり

  //
  public void start() {
    game_flag=false;
    
  }
  
  //

  public void update(Graphics g) {
    paint(g);  
  }

  public void paint(Graphics g) {
    int ii,jj;

    if (game_flag) {
      if (alldraw_flag) {
        //cls
        g.clearRect(0,0,size().width,size().height);

        //
        ii=x_ofs+x_pos*x_len;
        jj=y_ofs+y_pos*y_len;
        g.drawImage(pzl_images[6],ii,jj,x_len,y_len,this);        
        for(int i=0;i<x_num;i++) {
          for(int j=0;j<y_num;j++) {
            if (ban[i][j]==1) { //wall
              ii=x_ofs+i*x_len;
              jj=y_ofs+j*y_len;
              g.drawImage(pzl_images[1],ii,jj,x_len,y_len,this);
 
            }
            if (ban[i][j]==2) { //nimotu
              ii=x_ofs+i*x_len;
              jj=y_ofs+j*y_len;
              g.drawImage(pzl_images[2],ii,jj,x_len,y_len,this);
 
            }

            if (okiba[i][j]==3) { //okiba
              ii=x_ofs+i*x_len;
              jj=y_ofs+j*y_len;
              g.drawImage(pzl_images[3],ii,jj,x_len,y_len,this);
 
            }

          }
        }

      }
      else {
      
        old_x_pos=x_pos;
        old_y_pos=y_pos;
    
        switch(dir) {
        case 4 :
          if (ban[x_pos-1][y_pos]==0) {
            x_pos=x_pos-1;
            ii=x_ofs+x_pos*x_len;
            jj=y_ofs+y_pos*y_len;
            g.drawImage(pzl_images[0],ii,jj,x_len,y_len,this);
            ii=x_ofs+old_x_pos*x_len;
            jj=y_ofs+old_y_pos*y_len;
            if (okiba[old_x_pos][old_y_pos]==3) {
              g.drawImage(pzl_images[3],ii,jj,x_len,y_len,this);
            }
            else {
              g.clearRect(ii,jj,x_len,y_len);
            }

          }
          else if (ban[x_pos-1][y_pos]==2) {
            if (ban[x_pos-2][y_pos]==0) {
              ban[x_pos-2][y_pos]=2;
              ban[x_pos-1][y_pos]=0;
              ii=x_ofs+(x_pos-2)*x_len;
              jj=y_ofs+y_pos*y_len;
              g.drawImage(pzl_images[2],ii,jj,x_len,y_len,this);
              //
              x_pos=x_pos-1;
              ii=x_ofs+x_pos*x_len;
              jj=y_ofs+y_pos*y_len;
              g.drawImage(pzl_images[0],ii,jj,x_len,y_len,this);
              ii=x_ofs+old_x_pos*x_len;
              jj=y_ofs+old_y_pos*y_len;
              if (okiba[old_x_pos][old_y_pos]==3) {
                g.drawImage(pzl_images[3],ii,jj,x_len,y_len,this);
              }
              else {
                g.clearRect(ii,jj,x_len,y_len);
              }
              if (hantei()) {
                game_flag=false;
                label1.setText("GOOD JOB !");
              }
            }
            else {
              ii=x_ofs+x_pos*x_len;
              jj=y_ofs+y_pos*y_len;
              g.drawImage(pzl_images[0],ii,jj,x_len,y_len,this);
            }
          }
          else {
            ii=x_ofs+x_pos*x_len;
            jj=y_ofs+y_pos*y_len;
            g.drawImage(pzl_images[0],ii,jj,x_len,y_len,this);
          }
          break;
        case 6 :
          if (ban[x_pos+1][y_pos]==0) {
            x_pos=x_pos+1;
            ii=x_ofs+x_pos*x_len;
            jj=y_ofs+y_pos*y_len;
            g.drawImage(pzl_images[4],ii,jj,x_len,y_len,this);
            ii=x_ofs+old_x_pos*x_len;
            jj=y_ofs+old_y_pos*y_len;
            if (okiba[old_x_pos][old_y_pos]==3) {
              g.drawImage(pzl_images[3],ii,jj,x_len,y_len,this);
            }
            else {
              g.clearRect(ii,jj,x_len,y_len);
            }


          }
          else if (ban[x_pos+1][y_pos]==2) {
            if (ban[x_pos+2][y_pos]==0) {
              ban[x_pos+2][y_pos]=2;
              ban[x_pos+1][y_pos]=0;
              ii=x_ofs+(x_pos+2)*x_len;
              jj=y_ofs+y_pos*y_len;
              g.drawImage(pzl_images[2],ii,jj,x_len,y_len,this);
              //
              x_pos=x_pos+1;
              ii=x_ofs+x_pos*x_len;
              jj=y_ofs+y_pos*y_len;
              g.drawImage(pzl_images[4],ii,jj,x_len,y_len,this);
              ii=x_ofs+old_x_pos*x_len;
              jj=y_ofs+old_y_pos*y_len;
              if (okiba[old_x_pos][old_y_pos]==3) {
                g.drawImage(pzl_images[3],ii,jj,x_len,y_len,this);
              }
              else {
                g.clearRect(ii,jj,x_len,y_len);
              }
              if (hantei()) {
                game_flag=false;
                label1.setText("GOOD JOB !");

              }

            }
            else {
              ii=x_ofs+x_pos*x_len;
              jj=y_ofs+y_pos*y_len;
              g.drawImage(pzl_images[4],ii,jj,x_len,y_len,this);
            }            
          }
          else {
            ii=x_ofs+x_pos*x_len;
            jj=y_ofs+y_pos*y_len;
            g.drawImage(pzl_images[4],ii,jj,x_len,y_len,this);
          }
          break;
        case 8 :
          if (ban[x_pos][y_pos-1]==0) {
            y_pos=y_pos-1;
            ii=x_ofs+x_pos*x_len;
            jj=y_ofs+y_pos*y_len;
            g.drawImage(pzl_images[5],ii,jj,x_len,y_len,this);
            ii=x_ofs+old_x_pos*x_len;
            jj=y_ofs+old_y_pos*y_len;
            if (okiba[old_x_pos][old_y_pos]==3) {
              g.drawImage(pzl_images[3],ii,jj,x_len,y_len,this);
            }
            else {
              g.clearRect(ii,jj,x_len,y_len);
            }


          }
          else if (ban[x_pos][y_pos-1]==2) {
            if (ban[x_pos][y_pos-2]==0) {
              ban[x_pos][y_pos-2]=2;
              ban[x_pos][y_pos-1]=0;
              ii=x_ofs+x_pos*x_len;
              jj=y_ofs+(y_pos-2)*y_len;
              g.drawImage(pzl_images[2],ii,jj,x_len,y_len,this);
              //
              y_pos=y_pos-1;
              ii=x_ofs+x_pos*x_len;
              jj=y_ofs+y_pos*y_len;
              g.drawImage(pzl_images[5],ii,jj,x_len,y_len,this);
              ii=x_ofs+old_x_pos*x_len;
              jj=y_ofs+old_y_pos*y_len;
              if (okiba[old_x_pos][old_y_pos]==3) {
                g.drawImage(pzl_images[3],ii,jj,x_len,y_len,this);
              }
              else {
                g.clearRect(ii,jj,x_len,y_len);
              }
              if (hantei()) {
                game_flag=false;
                label1.setText("GOOD JOB !");

              }

            }
            else {
              ii=x_ofs+x_pos*x_len;
              jj=y_ofs+y_pos*y_len;
              g.drawImage(pzl_images[5],ii,jj,x_len,y_len,this);
            }
          }
          else {
            ii=x_ofs+x_pos*x_len;
            jj=y_ofs+y_pos*y_len;
            g.drawImage(pzl_images[5],ii,jj,x_len,y_len,this);
          }
          break;
        case 2 :
          if (ban[x_pos][y_pos+1]==0) {
            y_pos=y_pos+1;
            ii=x_ofs+x_pos*x_len;
            jj=y_ofs+y_pos*y_len;
            g.drawImage(pzl_images[6],ii,jj,x_len,y_len,this);
            ii=x_ofs+old_x_pos*x_len;
            jj=y_ofs+old_y_pos*y_len;
            if (okiba[old_x_pos][old_y_pos]==3) {
              g.drawImage(pzl_images[3],ii,jj,x_len,y_len,this);
            }
            else {
              g.clearRect(ii,jj,x_len,y_len);
            }


          }
          else if (ban[x_pos][y_pos+1]==2) {
            if (ban[x_pos][y_pos+2]==0) {
              ban[x_pos][y_pos+2]=2;
              ban[x_pos][y_pos+1]=0;
              ii=x_ofs+x_pos*x_len;
              jj=y_ofs+(y_pos+2)*y_len;
              g.drawImage(pzl_images[2],ii,jj,x_len,y_len,this);
              //
              y_pos=y_pos+1;
              ii=x_ofs+x_pos*x_len;
              jj=y_ofs+y_pos*y_len;
              g.drawImage(pzl_images[6],ii,jj,x_len,y_len,this);
              ii=x_ofs+old_x_pos*x_len;
              jj=y_ofs+old_y_pos*y_len;
              if (okiba[old_x_pos][old_y_pos]==3) {
                g.drawImage(pzl_images[3],ii,jj,x_len,y_len,this);
              }
              else {
                g.clearRect(ii,jj,x_len,y_len);
              }
              if (hantei()) {
                game_flag=false;
                label1.setText("GOOD JOB !");

              }

            }
            else {
              ii=x_ofs+x_pos*x_len;
              jj=y_ofs+y_pos*y_len;
              g.drawImage(pzl_images[6],ii,jj,x_len,y_len,this);
            }
          }
          else {
            ii=x_ofs+x_pos*x_len;
            jj=y_ofs+y_pos*y_len;
            g.drawImage(pzl_images[6],ii,jj,x_len,y_len,this);
          }
          break;
   
        }

        
      }

    }
  }  //paint() の終わり
  


  public boolean hantei() {
    for (int i=0;i<num;i++) {
      if (ban[gyaku_i[i]][gyaku_j[i]]!=2) {
        return(false);
      }
    }
    return(true);
  }
 
 
  public boolean action(Event evt,Object arg) {
    if (evt.target instanceof Button) {
      if (arg.equals("START")) {
        game_flag=true;
        alldraw_flag=true;
        //
        for(int i=0;i<x_num;i++) {
          for(int j=0;j<y_num;j++) {
            ban[i][j]=old_ban[i][j];  
          }
        }
        x_pos=ini_x_pos;
        y_pos=ini_y_pos;
        //
        label1.setText("GAME ON !");
        repaint();
        return true;
      }
    }
    return false;
  }
   
  //key
  public boolean keyUp(Event evt,int code) {
    switch (code) {
      case 'k':
        dir=2;
        alldraw_flag=false;
        repaint();
        break;
      case 'K':
        dir=2;
        alldraw_flag=false;
        repaint();
        break;
      case 'j':
        dir=4;
        alldraw_flag=false;
        repaint();
        break;
      case 'J':
        dir=4;
        alldraw_flag=false;
        repaint();
        break;
      case 'l':
        dir=6;
        alldraw_flag=false;
        repaint();
        break;
      case 'L':
        dir=6;
        alldraw_flag=false;
        repaint();
        break;
      case 'i':
        dir=8;
        alldraw_flag=false;
        repaint();
        break;
      case 'I':
        dir=8;
        alldraw_flag=false;
        repaint();
        break;
    }
    
    return true;
  }
  //key end


}








