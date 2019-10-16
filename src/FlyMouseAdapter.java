import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

/**
 * 鼠标适配器
 * @author Administrator
 *
 */
public class FlyMouseAdapter extends MouseAdapter {
	private ShootGame mainFrm;
	
	public FlyMouseAdapter(){}
	
	public FlyMouseAdapter(ShootGame jp){
		mainFrm=jp;
	}
	
	
	 @Override
     public void mouseMoved(MouseEvent e) { // 鼠标移动
         if (mainFrm.getState() == mainFrm.RUNNING) { // 运行状态下移动英雄机--随鼠标位置
             int x = e.getX();
             int y = e.getY();
             mainFrm.getHero().moveTo(x, y);
         }
     }
     @Override
     public void mouseEntered(MouseEvent e) { // 鼠标进入
         if (mainFrm.getState() == mainFrm.PAUSE) { // 暂停状态下运行
             mainFrm.setState(1);
         }
     }

     @Override
     public void mouseExited(MouseEvent e) { // 鼠标退出
         if (mainFrm.getState() != mainFrm.GAME_OVER&&mainFrm.getState()!=mainFrm.START) { // 游戏未结束，则设置其为暂停
             mainFrm.setState(2);
         }
     }

     @Override
     public void mouseClicked(MouseEvent e) { // 鼠标点击
         switch (mainFrm.getState()) {
         case 0:
             // 启动状态下运行
             mainFrm.setState(1);
             break;
         case 3: // 游戏结束，清理现场
             // 清空飞行物
             mainFrm.setFlyings(new FlyingObject[0]);
             // 清空子弹
             mainFrm.setBullets(new Bullet[0]);
             // 重新创建英雄机
             mainFrm.setHero(new Hero());
             // 清空成绩
             mainFrm.setScore(0);
             // 状态设置为启动
             mainFrm.setState(0);
             break;
         }
     }

}
