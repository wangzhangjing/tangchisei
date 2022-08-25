package com.sxt.obj;

import com.sxt.GameWin;
import com.sxt.utlis.GameUtils;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class HeadObj extends GameObj{
    //方向
    private String direction = "right";

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public HeadObj(Image img, int x, int y, GameWin frame) {
        super(img, x, y, frame);
        //键盘监听事件
        this.frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                changeDirection(e);
            }
        });
    }
    //控制方向wads
    public void changeDirection(KeyEvent e){
        switch (e.getKeyCode()){
            case KeyEvent.VK_A:
                if (!"right".equals(direction)){
                    direction="left";
                    img= GameUtils.leftImg;
                }
                break;
            case KeyEvent.VK_D:
                if (!"left".equals(direction)){
                    direction="right";
                    img= GameUtils.rightImg;
                }
                break;
            case KeyEvent.VK_W:
                if (!"down".equals(direction)){
                    direction="up";
                    img= GameUtils.upImg;
                }
                break;
            case KeyEvent.VK_S:
                if (!"up".equals(direction)){
                    direction="down";
                    img= GameUtils.downImg;
                }
                break;
            default:
                break;
        }
    }


    //蛇头移动
    public void move(){
        //蛇身移动
        List<BodyObj> bodyObjList = this.frame.bodyObjList;
        for (int i = bodyObjList.size()-1; i >=1 ; i--) {
            bodyObjList.get(i).x=bodyObjList.get(i-1).x;
            bodyObjList.get(i).y=bodyObjList.get(i-1).y;
            //蛇头与蛇身碰撞判断
            if (this.x==bodyObjList.get(i).x && this.y==bodyObjList.get(i).y){
                //失败
                GameWin.state=3;
            }

        }
        bodyObjList.get(0).x=this.x;
        bodyObjList.get(0).y=this.y;
        switch (direction){
            case "up":
                y -= height;
                break;
            case "down":
                y += height;
                break;
            case "left":
                x -= width;
                break;
            case "right":
                x += width;
            default:
                break;
        }
    }

    @Override
    public void pantSelf(Graphics g) {
        super.pantSelf(g);
        //蛇吃食物
        FoodObj food =this.frame.foodObj;
        Integer newX =null;
        Integer newY =null;
        if (this.x== food.x&&this.y== food.y){
            this.frame.foodObj =food.getFood();
            BodyObj lastBody =this.frame.bodyObjList.get(this.frame.bodyObjList.size()-1);
            newX =lastBody.x;
            newY =lastBody.y;
            this.frame.score++;
        }
        //通过判断
        if (this.frame.score>=5*GameUtils.level){
            //通关
            GameWin.state=4;
        }
        move();
        if (newX !=null&& newY!=null){
            this.frame.bodyObjList.add(new BodyObj(GameUtils.bodyImg,newX,newY,this.frame));
        }
        //越界处理
        if (x<0){
            x=570;
        }else if (x>570){
            x=0;
        }else if (y<30){
            y=570;
        }else if (y>570){
            y=30;
        }

    }
}
