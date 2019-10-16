

import java.util.TimerTask;

public class FlyGameTimerTask extends TimerTask {
	private ShootGame mainFrm;
	public FlyGameTimerTask(ShootGame jp){
		mainFrm=jp;
	}
	
	@Override
    public void run() {
        if (mainFrm.getState() == mainFrm.RUNNING) { // 运行状态
        	mainFrm.enterAction(); // 飞行物入场
        	mainFrm.stepAction(); // 走一步
        	mainFrm.shootAction(); // 英雄机射击
        	mainFrm.bangAction(); // 子弹打飞行物
        	mainFrm.outOfBoundsAction(); // 删除越界飞行物及子弹
        	mainFrm.checkGameOverAction(); // 检查游戏结束
        }
        mainFrm.repaint(); // 重绘，调用paint()方法
    }

}
