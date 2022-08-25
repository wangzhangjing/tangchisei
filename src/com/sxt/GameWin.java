package com.sxt;

import com.sxt.obj.BodyObj;
import com.sxt.obj.FoodObj;
import com.sxt.obj.HeadObj;
import com.sxt.utlis.GameUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;


public class GameWin extends JFrame {


    //游戏状态0未开始 1游戏中2 暂停 3失败4通过5失败后重启6下一关
    public static int state =0;
    //分数
    public  int score =0;
    //定义双缓存图片
    Image offScreenImage = null;
    //窗口宽高
    int winWidth =800;

    int winHeight =600;

    //蛇头对象
    HeadObj headObj=new HeadObj(GameUtils.rightImg,60,570,this);

    //蛇身的集合
    public List<BodyObj> bodyObjList =new ArrayList<>();

    public FoodObj foodObj=new FoodObj().getFood();

    public void launch(){
        //设置窗口是否可见
        this.setVisible(true);
        //设置窗口大小
        this.setSize(winWidth,winHeight);
        //设置窗口位置居中
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //设置窗口标题
        this.setTitle("贪吃蛇");

        bodyObjList.add(new BodyObj(GameUtils.bodyImg,30,570,this));
        bodyObjList.add(new BodyObj(GameUtils.bodyImg,0,570,this));

        //键盘事件
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_SPACE){
                    switch (state){
                        //未开始
                        case 0:
                            state = 1;
                            break;
                        case 1:
                            //游戏中
                            state = 2;
                            repaint();
                            break;
                        case 2:
                            //暂停
                            state = 1;
                            break;
                        case 3:
                            //失败后重新开始
                            state=5;
                            break;
                        case 4:
                            state=6;
                            break;
                        default:
                            break;
                    }
                }
            }
        });

        while (true){
            if (state==1){
                repaint();
            }
            //失败重启
            if (state==5){
                state=0;
                resetGame();
            }
            if (state==6 && GameUtils.level!=3){
                state=1;
                GameUtils.level++;
                resetGame();
            }

            try {
                Thread.sleep(300/GameUtils.level);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        //初始化双缓存图片
        if (offScreenImage==null){
            offScreenImage=this.createImage(winWidth,winHeight);
        }
        //获取图片对应的graphics对象
        Graphics gImage =offScreenImage.getGraphics();

        //灰色背景
        gImage.setColor(Color.gray);
        gImage.fillRect(0,0,winWidth,winHeight);
        //网格线
        gImage.setColor(Color.black);
        for (int i = 0; i <=20; i++) {
            //恒
            gImage.drawLine(0,i*30,600,i*30);
            //竖
            gImage.drawLine(i*30,0,i*30,600);

        }
        //绘制蛇身
        for (int i = bodyObjList.size() -1; i >=0; i--) {
              bodyObjList.get(i).pantSelf(gImage);
        }
        //绘制蛇头
        headObj.pantSelf(gImage);
        //食物绘制
        foodObj.pantSelf(gImage);
        //关卡绘制
        GameUtils.drawWord(gImage,"第"+GameUtils.level+"关",Color.orange,40,650,260);
        //分数绘制
        GameUtils.drawWord(gImage,score+"分",Color.blue,50,650,330);
        //绘制提示语句
        gImage.setColor(Color.green);
        prompt(gImage);
        //将双缓存图片绘制在窗口中
        g.drawImage(offScreenImage,0,0,null);
    }

    void prompt(Graphics g){
        //未开始
        if (state==0){
            g.fillRect(120,240,400,70);
            GameUtils.drawWord(g,"按下空格开始游戏",Color.orange,35,150,290);
        }
        //暂停
        if (state==2){
            g.fillRect(120,240,400,70);
            GameUtils.drawWord(g,"按下空格继续游戏",Color.orange,35,150,290);
        }
        //通过
        if (state==4){
            if (GameUtils.level==3){
                g.fillRect(120,240,400,70);
                GameUtils.drawWord(g,"恭喜你，游戏通关",Color.white,35,150,290);
            }else {
            g.fillRect(120,240,400,70);
            GameUtils.drawWord(g,"恭喜你，空格下一关",Color.white,35,150,290);
        }
        }
        //失败
        if (state==3){
            g.fillRect(120,240,400,70);
            GameUtils.drawWord(g,"失败,按空格重启",Color.red,35,150,290);
        }
    }
    //游戏重置
    void resetGame(){
        //关闭当前窗口
        this.dispose();
        //开启新窗口
        String[] args={};
        main(args);
    }

    public static void main(String[] args){
        GameWin gameWin=new GameWin();
        gameWin.launch();
    }
}
