package com.sxt.obj;

import com.sxt.GameWin;
import com.sxt.utlis.GameUtils;

import java.awt.*;
import java.util.Random;

public class FoodObj extends GameObj{
    //随机函数
    Random r =new Random();

    public FoodObj() {
        super();
    }

    public FoodObj(Image img, int x, int y, GameWin frame) {
        super(img, x, y, frame);
    }

    //获取食物
    public FoodObj getFood(){
        return new FoodObj(GameUtils.foodImg,r.nextInt(20)*30,(r.nextInt(19)+1)*30,this.frame);
    }

    @Override
    public void pantSelf(Graphics g) {
        super.pantSelf(g);
    }


}
