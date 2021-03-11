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
	/**������
	 * */
	static JFrame jf = new JFrame("��е��ģ��");
	/**����
	 * */
	static Container co = jf.getContentPane();
	static Main cc = new Main();
	/**ʱ��
	 * */
	static double t = 0;
	/**��������
	 * */
	static double �� = 2.0;
	/**������ͣ/��ʼ
	 * */
	static boolean have = false;
	/**��Ellipse2D���鴢��ͼ��
	 * */
	static Ellipse2D.Float[] shapes = new Ellipse2D.Float[100];
	
	/**main����
	 * */
	public static void main(String[] args) {
		new Main().inFrame();//����inFrame����

	}
	/**��ʼ�����巽��
	 * */
	private void inFrame() {
		jf.setSize(1030, 570);
		co.add(cc);
		cc.setLayout(null);
		jf.setLocationRelativeTo(null);//���ô����λ��
		jf.setResizable(false);
		jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		JButton jb1 = new JButton("start");//��ʼ,��ͣ��ť
		JButton jb2 = new JButton("restart");//���¿�ʼ��ť
		jb1.addActionListener(new ActionListener() {//��������¼�
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(jb1.getText()=="start") {
					jb1.setText("stop");//����ť��������Ϊstop
					have = true;//�ı䲼��ֵ�Խ���ѭ��
					System.out.println("have is changed into:"+have);
					
				}else {
					jb1.setText("start");//����ť��������Ϊstart
					have = false;//�ı䲼��ֵ����ͣѭ��
					System.out.println("have is changed into:"+have);
				}
			}
		});
		jb1.setBounds(125, 470, 100, 40);
		cc.add(jb1);
		
		jb2.addActionListener(new ActionListener() {//��������¼�
			
			@Override
			public void actionPerformed(ActionEvent e) {
				jb1.setText("start");//����ʼ��ͣ�İ�ť��������Ϊstart
				have = false;//�ı䲼��ֵ����ͣѭ��
				try {
					Thread.sleep(25);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				for(int i = 0;i<shapes.length;i++) {
					shapes[i] = new Ellipse2D.Float(10*i+2, 220, 10, 10);
					//Ϊͼ�ζ����ʼ������
				}
				t = 0;//��ʱ��Ϊ0
				cc.repaint();//�ػ�	
				System.out.println("have is changed into:"+have);
			}
		});
		jb2.setBounds(300,470,100,40);
		cc.add(jb2);
		JLabel jl = new JLabel("speed : "+��);
		JButton jb3 = new JButton("-");//�������ڵİ�ť
		jb3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(��>1.0) {//�ص���СֵΪ1.0
					reSetTime(��, �� - 1);//��ֹ������ͻ�����������ͻ��
					��--;
					jl.setText("speed : "+��);
				}
				
			}
		});
		jb3.setBounds(550, 475, 43, 30);
		cc.add(jb3);
		JButton jb4 = new JButton("+");//�������ڵİ�ť
		jb4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(��<5.0) {//�ص����ֵΪ5.0
					reSetTime(��, �� + 1);//��ֹ������ͻ�����������ͻ��
					��++;
					jl.setText("speed : "+��);
				}
				
			}
		});
		jb4.setBounds(775, 475, 43, 30);
		cc.add(jb4);
		jl.setBounds(650, 470 , 160, 40);
		cc.add(jl);

		for(int i = 0;i<shapes.length;i++) {
			shapes[i] = new Ellipse2D.Float(10*i+2, 220, 10, 10);
			//Ϊͼ�ζ����ʼ������
		}
		jf.setVisible(true);//ʹ���ڿɼ�
		new Thread(new Runnable() {//�½�һ���߳�
			
			@Override
			public void run() {
				while(true) {//����ѭ��
					if(have) {//��ѭ��
						try {
							Thread.sleep(20);
							t+=0.02;//ʹʱ����ǰ����
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
						for(int i = shapes.length-1;i>0;i--) {
							//�ú�һ�����y�������ǰһ�����y���꣬�����ʵ���
							shapes[i].y = shapes[i-1].y;
						}
						cc.repaint();//�ػ��Ը��»���
						//���µ�һ���ʵ��y����
						shapes[0].y = -80*((float) Math.sin(4*��*t))+220;
					}else {//Ϊ��ֹѭ��������������ʱ��������Ӧ���ڲ�������ѭ��ʱ��һ���¼����
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
	/**����paint�����Ի���ͼ��
	 * */
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		for(int i = 0;i<shapes.length;i++) {
			if(i%3==0){//Ϊ��ֹ����������������һ��if���
				double t2 = t-0.02*i;
				int t3 = (int) (100*Math.sin(t2));
				g2.setColor(new Color(t3+127, (int)(100* Math.cos(t2))+127, 127-t3));//������ɫ
			}
			g2.fill(shapes[i]);//����ͼ��
		}
		
	}
	/**��ֹ������ͻ�����������ͻ��ĺ���
	 * */
	public void reSetTime(double w1,double w2) {
		t = (w1 / w2) * t;//ͨ���ı�t��ֵ���ﵽЧ��
	}

}
