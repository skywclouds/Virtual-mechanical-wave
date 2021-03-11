package main;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;



public class Main extends JPanel{
	private static final long serialVersionUID = 1L;
	/**主窗体
	 * */
	static JFrame jf = new JFrame("机械波模拟");
	/**容器
	 * */
	static Container co = jf.getContentPane();
	static Main cc = new Main();
	/**时间
	 * */
	static double t = 0;
	/**控制周期
	 * */
	static double ω = 2.0;
	/**控制暂停/开始
	 * */
	static boolean have = false;
	/**用Ellipse2D数组储存图形
	 * */
	static Ellipse2D.Float[] shapes = new Ellipse2D.Float[100];
	
	/**main方法
	 * */
	public static void main(String[] args) {
		new Main().inFrame();//调用inFrame方法

	}
	/**初始化窗体方法
	 * */
	private void inFrame() {
		jf.setSize(1030, 570);
		co.add(cc);
		cc.setLayout(null);
		jf.setLocationRelativeTo(null);//设置窗体的位置
		jf.setResizable(false);
		jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		JButton jb1 = new JButton("start");//开始,暂停按钮
		JButton jb2 = new JButton("restart");//重新开始按钮
		jb1.addActionListener(new ActionListener() {//设置鼠标事件
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(jb1.getText()=="start") {
					jb1.setText("stop");//将按钮文字设置为stop
					have = true;//改变布尔值以进入循环
					System.out.println("have is changed into:"+have);
					
				}else {
					jb1.setText("start");//将按钮文字设置为start
					have = false;//改变布尔值以暂停循环
					System.out.println("have is changed into:"+have);
				}
			}
		});
		jb1.setBounds(125, 470, 100, 40);
		cc.add(jb1);
		
		jb2.addActionListener(new ActionListener() {//设置鼠标事件
			
			@Override
			public void actionPerformed(ActionEvent e) {
				jb1.setText("start");//将开始暂停的按钮文字设置为start
				have = false;//改变布尔值以暂停循环
				try {
					Thread.sleep(25);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				for(int i = 0;i<shapes.length;i++) {
					shapes[i] = new Ellipse2D.Float(10*i+2, 220, 10, 10);
					//为图形对象初始化坐标
				}
				t = 0;//令时间为0
				cc.repaint();//重绘	
				System.out.println("have is changed into:"+have);
			}
		});
		jb2.setBounds(300,470,100,40);
		cc.add(jb2);
		JLabel jl = new JLabel("speed : "+ω);
		JButton jb3 = new JButton("-");//控制周期的按钮
		jb3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(ω>1.0) {//ω的最小值为1.0
					reSetTime(ω, ω - 1);//防止因周期突变而导致坐标突变
					ω--;
					jl.setText("speed : "+ω);
				}
				
			}
		});
		jb3.setBounds(550, 475, 43, 30);
		cc.add(jb3);
		JButton jb4 = new JButton("+");//控制周期的按钮
		jb4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(ω<5.0) {//ω的最大值为5.0
					reSetTime(ω, ω + 1);//防止因周期突变而导致坐标突变
					ω++;
					jl.setText("speed : "+ω);
				}
				
			}
		});
		jb4.setBounds(775, 475, 43, 30);
		cc.add(jb4);
		jl.setBounds(650, 470 , 160, 40);
		cc.add(jl);

		for(int i = 0;i<shapes.length;i++) {
			shapes[i] = new Ellipse2D.Float(10*i+2, 220, 10, 10);
			//为图形对象初始化坐标
		}
		jf.setVisible(true);//使窗口可见
		new Thread(new Runnable() {//新建一个线程
			
			@Override
			public void run() {
				while(true) {//无限循环
					if(have) {//真循环
						try {
							Thread.sleep(20);
							t+=0.02;//使时间向前流动
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
						for(int i = shapes.length-1;i>0;i--) {
							//让后一个点的y坐标等于前一个点的y坐标，带动质点振动
							shapes[i].y = shapes[i-1].y;
						}
						cc.repaint();//重绘以更新画面
						//更新第一个质点的y坐标
						shapes[0].y = -80*((float) Math.sin(4*ω*t))+220;
					}else {//为防止循环次数次数过多时程序无响应，在不进入真循环时加一个事件间隔
						try {
							Thread.sleep(50);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		}).start();
	}
	/**覆盖paint方法以绘制图形
	 * */
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		for(int i = 0;i<shapes.length;i++) {
			if(i%3==0){//为防止计算次数过多而设置一个if语句
				double t2 = t-0.02*i;
				int t3 = (int) (100*Math.sin(t2));
				g2.setColor(new Color(t3+127, (int)(100* Math.cos(t2))+127, 127-t3));//设置颜色
			}
			g2.fill(shapes[i]);//绘制图形
		}
		
	}
	/**防止因周期突变而导致坐标突变的函数
	 * */
	public void reSetTime(double w1,double w2) {
		t = (w1 / w2) * t;//通过改变t的值来达到效果
	}

}
