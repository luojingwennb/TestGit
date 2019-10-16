import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 飞机大战 首先对这个游戏分析,在屏幕上的物体都是飞行物,我们可以把建一个类,让其他飞行物继承这个类.游戏中应有英雄机(也就是自己控制的飞机)、敌人。
 * 而敌人应该分为打死给分的飞机（就是普通飞机），另一种就是打死有奖励的敌人。
 * 他们都应该是飞行物的子类，我们也可以为普通飞机和给奖励的敌人设一个接口让他们去实现接口，这样有利于以后的扩展，我在这里给的简化版的飞机大战，
 * 主要是为了让大家了解面向对象
 * @author Administrator
 *
 */
public class ShootGame extends JPanel {
   public static void main(String[] args) {
        JFrame frame = new JFrame("Fly");
        ShootGame game = new ShootGame(); // 面板对象
        frame.add(game); // 将面板添加到JFrame中
        frame.setSize(WIDTH, HEIGHT); // 设置大小
        frame.setAlwaysOnTop(true); // 设置其总在最上
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 默认关闭操作
        frame.setIconImage(new ImageIcon("images/icon.jpg").getImage()); // 设置窗体的图标
        frame.setLocationRelativeTo(null); // 设置窗体初始位置
        frame.setVisible(true); // 尽快调用paint
        game.action(); // 启动执行
    }
    public static final int WIDTH = 400; // 面板宽
    public static final int HEIGHT = 654; // 面板高
    /** 游戏的当前状态: START RUNNING PAUSE GAME_OVER */
    private int state; //游戏状态
    public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	public static final int START = 0; //游戏开始
	public static final int RUNNING = 1; //游戏中
	public static final int PAUSE = 2; //暂停
	public static final int GAME_OVER = 3; //游戏结束

    private int score = 0; // 得分
    public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	private Timer timer; // 定时器
    private int intervel = 1000/100; // 时间间隔(毫秒)
    //窗体中呈现的背景、功能按钮、飞行物
    public static BufferedImage background; //背景
    public static BufferedImage start; //开始
    public static BufferedImage airplane; //敌机
    public static BufferedImage bee; //蜜蜂
    public static BufferedImage bullet; //子弹
    public static BufferedImage hero0; //英雄0
    public static BufferedImage hero1;//英雄1
    public static BufferedImage pause; //暂停
    public static BufferedImage gameover; //游戏结束

    private FlyingObject[] flyings = {}; // 敌机数组
    public void setFlyings(FlyingObject[] flyings) {
		this.flyings = flyings;
	}

	public FlyingObject[] getFlyings() {
		return flyings;
	}
	private Bullet[] bullets = {}; // 子弹数组
    public Bullet[] getBullets() {
		return bullets;
	}

	public void setBullets(Bullet[] bullets) {
		this.bullets = bullets;
	}
	private Hero hero = new Hero(); // 英雄机

    public void setHero(Hero hero) {
		this.hero = hero;
	}

	public Hero getHero() {
		return hero;
	}

	static { // 静态代码块，初始化图片资源
        try {
            background = ImageIO.read(ShootGame.class
                    .getResource("img/bg.jpg")); //img/background.png
            start = ImageIO.read(ShootGame.class.getResource("img/start.png"));
            airplane = ImageIO
                    .read(ShootGame.class.getResource("img/airplane.png"));
            bee = ImageIO.read(ShootGame.class.getResource("img/bee.png"));
            bullet = ImageIO.read(ShootGame.class.getResource("img/bullet.png"));
            hero0 = ImageIO.read(ShootGame.class.getResource("img/hero0.png"));
            hero1 = ImageIO.read(ShootGame.class.getResource("img/hero1.png"));
            pause = ImageIO.read(ShootGame.class.getResource("img/pause.png"));
            gameover = ImageIO
                    .read(ShootGame.class.getResource("img/gameover.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 画 */
    @Override
    public void paint(Graphics g) {
        g.drawImage(background, 0, 0, null); // 画背景图
        paintHero(g); // 画英雄机
        paintBullets(g); // 画子弹
        paintFlyingObjects(g); // 画飞行物
        paintScore(g); // 画分数
        paintState(g); // 画游戏状态
    }

    /** 画英雄机 */
    public void paintHero(Graphics g) {
        g.drawImage(hero.getImage(), hero.getX(), hero.getY(), null);
    }

    /** 画子弹 */
    public void paintBullets(Graphics g) {
        for (int i = 0; i < bullets.length; i++) {
            Bullet b = bullets[i];
            g.drawImage(b.getImage(), b.getX() - b.getWidth() / 2, b.getY(),
                    null);
        }
    }

    /** 画飞行物 */
    public void paintFlyingObjects(Graphics g) {
        for (int i = 0; i < flyings.length; i++) {
            FlyingObject f = flyings[i];
            g.drawImage(f.getImage(), f.getX(), f.getY(), null);
        }
    }

    /** 画分数 */
    public void paintScore(Graphics g) {
        int x = 10; // x坐标
        int y = 25; // y坐标
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 14); // 字体
        g.setColor(new Color(0xFF0000));//0x3A3B3B
        g.setFont(font); // 设置字体
        g.drawString("成绩:" + score, x, y); // 画分数
        y += 20; // y坐标增20
        g.drawString("生命值:" + hero.getLife(), x, y); // 画命
    }

    /** 画游戏状态 */
    public void paintState(Graphics g) {
        switch (state) {
        case START: // 启动状态
            g.drawImage(start, 0, 0, null);
            break;
        case PAUSE: // 暂停状态
            g.drawImage(pause, 0, 0, null);
            break;
        case GAME_OVER: // 游戏终止状态
            g.drawImage(gameover, 0, 0, null);
            break;
        }
    }


    /** 启动执行代码 */
    public void action() {
        // 鼠标监听事件

    	FlyMouseAdapter flyAdapter=new FlyMouseAdapter(this);
        this.addMouseListener(flyAdapter); // 处理鼠标点击操作
        this.addMouseMotionListener(flyAdapter); // 处理鼠标滑动操作

        timer = new Timer(); // 主流程控制
        FlyGameTimerTask flyTask=new FlyGameTimerTask(this);//定时器任务
        timer.schedule(flyTask, intervel, intervel);
    }

    int flyEnteredIndex = 0; // 飞行物入场计数

    /** 飞行物入场 */
    public void enterAction() {
        flyEnteredIndex++;
        if (flyEnteredIndex % 40 == 0) { // 400毫秒生成一个飞行物--10*40
            FlyingObject obj = nextOne(); // 随机生成一个飞行物
            flyings = Arrays.copyOf(flyings, flyings.length + 1);//数组扩容 
            flyings[flyings.length - 1] = obj;//往数组后面追加飞行物
        }
    }

    /** 走一步 控制所有飞行物的移动 */
    public void stepAction() {
        for (int i = 0; i < flyings.length; i++) { // 飞行物走一步
            FlyingObject f = flyings[i];
            f.step();
        }

        for (int i = 0; i < bullets.length; i++) { // 子弹走一步
            Bullet b = bullets[i];
            b.step();
        }
        hero.step(); // 英雄机走一步
    }

    /** 飞行物走一步 */
//    public void flyingStepAction() {
//        for (int i = 0; i < flyings.length; i++) {
//            FlyingObject f = flyings[i];
//            f.step();
//        }
//    }

    int shootIndex = 0; // 射击计数

    /** 射击 */
    public void shootAction() {
        shootIndex++;
        if (shootIndex % 30 == 0) { // 300毫秒发一颗
            Bullet[] bs = hero.shoot(); // 英雄打出子弹
            bullets = Arrays.copyOf(bullets, bullets.length + bs.length); // 扩容
            System.arraycopy(bs, 0, bullets, bullets.length - bs.length,
                    bs.length); // 追加数组
        }
    }

    /** 子弹与飞行物碰撞检测 */
    public void bangAction() {
        for (int i = 0; i < bullets.length; i++) { // 遍历所有子弹
            Bullet b = bullets[i];
            bang(b); // 子弹和飞行物之间的碰撞检查
        }
    }

    /** 删除越界飞行物及子弹 */
    public void outOfBoundsAction() {
        int index = 0; // 索引
        FlyingObject[] flyingLives = new FlyingObject[flyings.length]; // 活着的飞行物
        for (int i = 0; i < flyings.length; i++) {
            FlyingObject f = flyings[i];
            if (!f.outOfBounds()) {
                flyingLives[index++] = f; // 不越界的留着
            }
        }
        flyings = Arrays.copyOf(flyingLives, index); // 将不越界的飞行物都留着

        index = 0; // 索引重置为0
        Bullet[] bulletLives = new Bullet[bullets.length];
        for (int i = 0; i < bullets.length; i++) {
            Bullet b = bullets[i];
            if (!b.outOfBounds()) {
                bulletLives[index++] = b;
            }
        }
        bullets = Arrays.copyOf(bulletLives, index); // 将不越界的子弹留着
    }

    /** 检查游戏结束 */
    public void checkGameOverAction() {
        if (isGameOver()) {
            state = GAME_OVER; // 改变状态
        }
    }

    /** 检查游戏是否结束 */
    public boolean isGameOver() {

        for (int i = 0; i < flyings.length; i++) {
            int index = -1;
            FlyingObject obj = flyings[i];
            if (hero.hit(obj)) { // 检查英雄机与飞行物是否碰撞
                hero.subtractLife(); // 减命
                hero.setDoubleFire(0); // 双倍火力解除
                index = i; // 记录碰上的飞行物索引
            }
            if (index != -1) {
                FlyingObject t = flyings[index];
                flyings[index] = flyings[flyings.length - 1];
                flyings[flyings.length - 1] = t; // 碰上的与最后一个飞行物交换

                flyings = Arrays.copyOf(flyings, flyings.length - 1); // 删除碰上的飞行物
            }
        }

        return hero.getLife() <= 0;
    }

    /** 子弹和飞行物之间的碰撞检查 */
    public void bang(Bullet bullet) {
        int index = -1; // 击中的飞行物索引
        for (int i = 0; i < flyings.length; i++) {
            FlyingObject obj = flyings[i];
            if (obj.shootBy(bullet)) { // 判断是否击中
                index = i; // 记录被击中的飞行物的索引
                break;
            }
        }
        if (index != -1) { // 有击中的飞行物
            FlyingObject one = flyings[index]; // 记录被击中的飞行物
            FlyingObject temp = flyings[index]; // 被击中的飞行物与最后一个飞行物交换
            flyings[index] = flyings[flyings.length - 1];
            flyings[flyings.length - 1] = temp;
            flyings = Arrays.copyOf(flyings, flyings.length - 1); // 删除最后一个飞行物(即被击中的)
            // 检查one的类型(敌人加分，奖励获取)
            if (one instanceof Enemy) { // 检查类型，是敌人，则加分
                Enemy e = (Enemy) one; // 强制类型转换
                score += e.getScore(); // 加分
            } else if (one instanceof Award) { // 若为奖励，设置奖励
                Award a = (Award) one;
                int type = a.getType(); // 获取奖励类型
                switch (type) {
                case Award.DOUBLE_FIRE:
                    hero.addDoubleFire(); // 设置双倍火力
                    break;
                case Award.LIFE:
                    hero.addLife(); // 设置加命
                    break;
                }
            }
        }
    }

    /**
     * 随机生成飞行物
     * 
     * @return 飞行物对象
     */
	public static FlyingObject nextOne() {
		Random random = new Random();
		int type = random.nextInt(20); // [0,20)
		if (type == 0) {
			return new Bee();
		} else {
			return new Airplane();
		}
	}

}