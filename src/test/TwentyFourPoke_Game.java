package test;

//import contrib.ch.randelshofer.quaqua.colorchooser.CMYKChooser;

import java.awt.desktop.SystemSleepEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.applet.*;
import java.awt.*;
import java.io.File;
import javax.swing.*;
import java.awt.event.*;
import java.net.URL;
import  java.net.URI;
import java.net.MalformedURLException;
import java.util.Enumeration;
import javax.swing.JOptionPane;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.basic.BasicLookAndFeel;
import javax.swing.text.html.ImageView;

import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.painter.border.StandardBorderPainter;
import org.jvnet.substance.skin.SubstanceDustLookAndFeel;
import org.jvnet.substance.skin.SubstanceNebulaLookAndFeel;
import org.jvnet.substance.skin.SubstanceOfficeBlue2007LookAndFeel;
import org.jvnet.substance.skin.SubstanceOfficeSilver2007LookAndFeel;
import org.jvnet.substance.skin.SubstanceTwilightLookAndFeel;

public class  TwentyFourPoke_Game extends JFrame
{
	//定义按钮
    private JButton jbkey=new JButton("Solution");//solution按键
    private JButton jbnext=new JButton("Next Question");//next question按键，添加参数"next question"
    private JButton jbsubmit=new JButton("Submit");//submit按键,添加参数"submit"
    private JButton jbstart = new JButton("Start");
    
    private JLabel jlmessage=new JLabel("     Your answer:");

    private JTextField jtsolution=new JTextField();
    private JTextField jtexpression=new JTextField();

    public ImagePanel pp=new ImagePanel();

    //用来存image里的四个数字
    private static int[] card=new int[4];
    private double[] bcard=new double[4];

    //求四个数的和
    private double sum;
    private double[] temp1=new double[3];
    private double[] temp2=new double[2];
    //符号集
    private char[] sign={'+','-','*','/'};
    
    public static boolean[] test=new boolean[9000000];
    public  boolean judge=false;
    public static boolean evaluate=false;
    
    public JPanel pS,p1,p3;
    //游戏界面
    //"D:/24game/img/BGPicture.jpg"
    public TwentyFourPoke_Game()
    {
    	pS = new JPanel();
    	JLabel l=new JLabel();
    	Icon icon=new ImageIcon("D:/JAVA/BackUp/bg2.jpg"); //在此直接创建对象
    	l.setIcon(icon);
    	l.setBounds(100, -120, icon.getIconWidth(),icon.getIconHeight());
    	pS.add(l,new Integer(Integer.MIN_VALUE));
    	pS.setVisible(true);
    	//pS.setBackground(Color.RED);
    	pS.setLayout(null);
    	jbstart.setBounds(255,300,75,47);
    	pS.add(jbstart);
    	
        p1 = new JPanel(new GridLayout(1,3));
        p1.setVisible(false);
        //根据按钮大小改变图片大小
        jbnext.setSize(50, 35);
        jbnext.setHorizontalTextPosition(SwingConstants.CENTER);
        jbnext.setVerticalTextPosition(SwingConstants.BOTTOM);
        ImageIcon ii = new ImageIcon("next.png");
        Image temp=ii.getImage().getScaledInstance(jbnext.getWidth(),jbnext.getHeight(),ii.getImage().SCALE_DEFAULT);
        ii =new ImageIcon(temp);
        jbnext.setIcon(ii);
        
        //加入到JPanel p1中
        p1.add(jbnext);
        p1.add(jtsolution);
        
        /*
         *还需要调整solutions的大小
         */
        jbkey.setSize(35, 40);
        jbkey.setHorizontalTextPosition(SwingConstants.CENTER);
        jbkey.setVerticalTextPosition(SwingConstants.BOTTOM);
        ii = new ImageIcon("key.png");
        temp=ii.getImage().getScaledInstance(jbkey.getWidth(),jbkey.getHeight(),ii.getImage().SCALE_DEFAULT);
        ii =new ImageIcon(temp);
        jbkey.setIcon(ii);
        p1.add(jbkey);
        
        p3 = new JPanel(new GridLayout(1,3));//new GridLayout(1,3)
        p3.setVisible(false);
        
        jbsubmit.setSize(40,35);
        jbsubmit.setHorizontalTextPosition(SwingConstants.CENTER);
        jbsubmit.setVerticalTextPosition(SwingConstants.BOTTOM);
        ii = new ImageIcon("submit.png");
        temp=ii.getImage().getScaledInstance(jbsubmit.getWidth(),jbsubmit.getHeight(),ii.getImage().SCALE_DEFAULT);
        ii =new ImageIcon(temp);
        jbsubmit.setIcon(ii);
        
        p3.add(jlmessage);
        p3.add(jtexpression);
        p3.add(jbsubmit);
        
        
        //p1,p3组件布局管理
        //p1在最上方，p3在最下方
        add(p1,BorderLayout.NORTH);
        add(pp,BorderLayout.CENTER);
        add(p3,BorderLayout.SOUTH);
        add(pS);

        //添加按钮监听器
        ButtonListener listener=new ButtonListener();
        jbkey.addActionListener(listener);
        jbnext.addActionListener(listener);
        jbsubmit.addActionListener(listener);
        jbstart.addActionListener(listener);
    }
    /*
    public void setIcon(String file, JButton iconButton)
    {
    	ImageIcon icon=new ImageIcon(file);
    	Image temp=icon.getImage().getScaledInstance(iconButton.getWidth(),iconButton.getHeight(),icon.getImage().SCALE_DEFAULT);
    	icon=new ImageIcon(temp);
    	iconButton.setIcon(icon);
    }*/

    class ButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
        	//如果玩家点击查看答案键
            if(e.getSource() == jbkey)
            {
                for(int i = 0;i < 4;i++)
                {
                    bcard[i] = (double)card[i] % 13;
                    if(card[i] % 13 == 0)
                        bcard[i] = 13;
                }
                //开始寻找答案
                showAnswer();
            }
            //如果玩家点击next键,进入下一题
            if(e.getSource() == jbnext)
            {
                pp.showPicture();
                for(int i = 0;i < 4;i++)
                {
                    bcard[i] = (double)card[i] % 13;
                    if(card[i] % 13 == 0)
                        bcard[i] = 13;
                }
                findAnswer();
                System.out.println(judge);
            }
            //玩家提交自己的答案
            else if(e.getSource() == jbsubmit)
            {
                String expression = jtexpression.getText();
                
                //判断答案是不是24 这里有小bug
                if(judge== true) {
                	int result = evaluateExpression(expression);
                	System.out.println(result);

                		if(result == 24)
                		{
                			JOptionPane.showMessageDialog(null,"    Accepted");
                		}
                		else
                		{
                			JOptionPane.showMessageDialog(null,"  Wrong Answer!","FeedBack",JOptionPane.ERROR_MESSAGE);
                		}
                }
                	else if( judge == false){
                		if(expression.compareTo("No Solution") == 0)
                			JOptionPane.showMessageDialog(null,"    Accepted");
                		else
                			JOptionPane.showMessageDialog(null,"  Wrong Answer!","FeedBack",JOptionPane.ERROR_MESSAGE);
                	}
            }
            else if(e.getSource() == jbstart) {
            	System.out.println(123123123);
            	remove(pS);
            	add(p1,BorderLayout.NORTH);
                add(pp,BorderLayout.CENTER);
                add(p3,BorderLayout.SOUTH);
            	p1.setVisible(true);
            	p3.setVisible(true);
            	//pp.setVisible(true);
            }
        }
    }

    // calculate计算
    public static double calcute(double a,double b,char c)
    {
        if(c=='+')
            return a+b;
        else if(c=='-')
            return  a-b;
        else if(c=='*')
            return a*b;
        else if(c=='/'&& b!=0)
            return a/b;
        else
            return -1;
    }

    //遍历所有情况
    public  void showAnswer()
    {
    	judge=false;
        for(int i=0;i<4;i++)
        //第一次放置的符号
        {
            for(int j=0;j<4;j++)
            //第二次放置的符号
            {
                for(int k=0;k<4;k++)
                //第三次放置的符号
                {
                    for(int m=0;m<3;m++)
                    //首先计算的两个相邻数字，共有3种情况，相当于括号的作用
                    {
                        if(bcard[m+1]==0 && sign[i]=='/') break;
                        temp1[m]=calcute(bcard[m],bcard[m+1],sign[i]);
                        temp1[(m+1)%3]=bcard[(m+2)%4];
                        temp1[(m+2)%3]=bcard[(m+3)%4];
                        //先确定首先计算的两个数字，计算完成相当于剩下三个数，按顺序储存在temp数组中
                        for(int n=0;n<2;n++)
                        //三个数字选出先计算的两个相邻数字，两种情况，相当于第二个括号
                        {
                            if(temp1[n+1]==0 && sign[j]=='/') break;
                            temp2[n]=calcute(temp1[n],temp1[n+1],sign[j]);
                            temp2[(n+1)%2]=temp1[(n+2)%3];
                            //先确定首先计算的两个数字，计算完成相当于剩下两个数，按顺序储存在temp数组中
                            if(temp2[1]==0 && sign[k]=='/') break;
                            sum=calcute(temp2[0],temp2[1],sign[k]);
                            //计算和
                            if(sum==24)
                            //若和为24
                            {
                                judge=true;
                                //判断符为1，表示已求得解
                                if(m==0 && n==0)
                                {
                                    String sss ="  (("+(int)bcard[0]+sign[i]+(int)bcard[1]+")"+sign[j]+(int)bcard[2]+")"+sign[k]+(int)bcard[3]+"="+(int)sum;
                                    jtsolution.setText(sss);
                                    System.out.println("output!");
                                    return ;
                                }
                                else if(m==0 && n==1)
                                {
                                    String sss ="  ("+(int)bcard[0]+sign[i]+(int)bcard[1]+")"+sign[k]+"("+(int)bcard[2]+sign[j]+(int)bcard[3]+")="+(int)sum;
                                    jtsolution.setText(sss);
                                    System.out.println("output!");
                                    return ;
                                }
                                else if(m==1 && n==0)
                                {
                                    String sss ="  ("+(int)bcard[0]+sign[j]+"("+(int)bcard[1]+sign[i]+(int)bcard[2]+"))"+sign[k]+(int)bcard[3]+"="+(int)sum;
                                    jtsolution.setText(sss);
                                    System.out.println("output!");
                                    return ;
                                }
                                else if(m==1 && n ==1) {
                                	String sss =(int)bcard[0]+sign[j]+"(("+(int)bcard[1]+sign[i]+(int)bcard[2]+")"+sign[k]+(int)bcard[3]+")"+"="+(int)sum;
                                    jtsolution.setText(sss);
                                    System.out.println("output!");
                                    System.out.println(bcard[0]);
                                    System.out.println(sign[j]);
                                }
                                else if(m==2 && n==0) 
                                {
                                    String sss ="  ("+(int)bcard[0]+sign[j]+(int)bcard[1]+")"+sign[k]+"("+(int)bcard[2]+sign[i]+(int)bcard[3]+")="+(int)sum;
                                    jtsolution.setText(sss);
                                    System.out.println("output!");
                                    return ;
                                }
                                else if(m==2 && n==1)
                                {
                                    String sss =(int)bcard[0]+sign[k]+"("+(int)bcard[1]+sign[j]+"("+(int)bcard[2]+sign[i]+(int)bcard[3]+"))="+(int)sum;
                                    jtsolution.setText(sss);
                                    System.out.println(bcard[0]);
                                    System.out.println(sign[k]);
                                    return ;
                                }
                                //m=0,1,2 n=0,1表示六种括号放置可能，并按照这六种可能输出相应的格式的计算式

                            }
                        }
                    }
                }
            }
        }
        //找不到答案
        if(judge==false)
            jtsolution.setText("       No Solution!");
            //pp.showPicture();
        //如果没有找到结果，符号位为0
    }
    
  //遍历所有情况
    public  void findAnswer()
    {
        //boolean judge = false;
    	judge=false;
        for(int i=0;i<4;i++)
        //第一次放置的符号
        {
            for(int j=0;j<4;j++)
            //第二次放置的符号
            {
                for(int k=0;k<4;k++)
                //第三次放置的符号
                {
                    for(int m=0;m<3;m++)
                    //首先计算的两个相邻数字，共有3种情况，相当于括号的作用
                    {
                        if(bcard[m+1]==0 && sign[i]=='/') break;
                        temp1[m]=calcute(bcard[m],bcard[m+1],sign[i]);
                        temp1[(m+1)%3]=bcard[(m+2)%4];
                        temp1[(m+2)%3]=bcard[(m+3)%4];
                        //先确定首先计算的两个数字，计算完成相当于剩下三个数，按顺序储存在temp数组中
                        for(int n=0;n<2;n++)
                        //三个数字选出先计算的两个相邻数字，两种情况，相当于第二个括号
                        {
                            if(temp1[n+1]==0 && sign[j]=='/') break;
                            temp2[n]=calcute(temp1[n],temp1[n+1],sign[j]);
                            temp2[(n+1)%2]=temp1[(n+2)%3];
                            //先确定首先计算的两个数字，计算完成相当于剩下两个数，按顺序储存在temp数组中
                            if(temp2[1]==0 && sign[k]=='/') break;
                            sum=calcute(temp2[0],temp2[1],sign[k]);
                            //计算和
                            if(sum==24)
                            	judge=true;
                        }
                    }
                }
            }
        }
        //找不到答案
            //pp.showPicture();
        //如果没有找到结果，符号位为0
    }


    public static int evaluateExpression(String expression)
    {
    	String judge="24";
    	int i=0, j=0, x;
    	int cnt=0;//用来对出现的数字进行计数
    	char[] c = expression.toCharArray();
    	for( i=0;i<14;i++)
    		test[i]=false;
    	
    	//遍历表达式将出现的4个数,设置为true
    	//card中出现的数字设置为true
        for (i = 0; i < c.length; i++)
        {
            j = i;
            x = 0;
            if (c[j] >= '0' && c[j] <= '9') {
                while ( j<c.length && c[j] >= '0' && c[j] <= '9' ) {
                    x = x * 10 + c[j] - '0';
                    j++;
                }
                cnt++;
                test[x] = true;
                i = j;
            }
        }
        //24不算正确情况
    	if(judge.compareTo(expression) == 0)
    		return -1;
    	else if(cnt > 4)
    		return -1;
    	else {
    		// numberStack用来储存数字
    		java.util.Stack<Integer> numberStack = new java.util.Stack<Integer>();

    		// operatorStack用来储存符号
    		java.util.Stack<Character> operatorStack = new java.util.Stack<Character>();

    		// Extract operands and operators
    		java.util.StringTokenizer tokens = new java.util.StringTokenizer(expression, "()+-/*", true);
    		
    		// Phase 1: Scan tokens
    		while (tokens.hasMoreTokens())
    		{
    			String token = tokens.nextToken().trim(); // Extract a token
    			// 如果是空格Blank space
    			if (token.length() == 0) 
    				continue; // Back to the while loop to extract the next token
    			else if (token.charAt(0) == '+' || token.charAt(0) == '-')
    			{
    				// Process all +, -, *, / in the top of the operator stack
    				while (!operatorStack.isEmpty() &&(operatorStack.peek().equals('+') ||operatorStack.peek().equals('-') || operatorStack.peek().equals('*') ||
    						operatorStack.peek().equals('/')))
    				{
    					processAnOperator(numberStack, operatorStack);
    				}
    				// Push the + or - operator into the operator stack
    				operatorStack.push(new Character(token.charAt(0)));
    			}
    			else if (token.charAt(0) == '*' || token.charAt(0) == '/')
    			{
    				// Process all *, / in the top of the operator stack
    				while (!operatorStack.isEmpty() && (operatorStack.peek().equals('*') || operatorStack.peek().equals('/')))
    				{
    					processAnOperator(numberStack, operatorStack);
    				}

    				// Push the * or / operator into the operator stack
    				operatorStack.push(new Character(token.charAt(0)));
    			}
    			else if (token.trim().charAt(0) == '(')
    			{
    				operatorStack.push(new Character('(')); // Push '(' to stack
    			}
    			else if (token.trim().charAt(0) == ')')
    			{
    				// Process all the operators in the stack until seeing '('
    				while (!operatorStack.peek().equals('('))
    				{
    					processAnOperator(numberStack, operatorStack);
    				}
    				operatorStack.pop(); // Pop the '(' symbol from the stack
    			}
    			else
    			{
    				// An operand scanned
    				// 把算出来的答案加入到栈中
    				//System.out.println(Integer.valueOf(token));
    				test[Integer.valueOf(token)]=true;
    				System.out.println(Integer.valueOf(token));
    				numberStack.push( Integer.valueOf(token));
    			}
    		}

    		// Phase 2: process all the remaining operators in the stack
    		//表达式遍历完成了,将栈中剩余的数字计算出来
    		while (!operatorStack.isEmpty())
    		{
    			processAnOperator(numberStack, operatorStack);
    		}
    		
    		// Return the result
    		//检查4个数到底是不是card中的4个数
    		for( i=0;i<4;i++) {
    			int tmp=card[i]%13;
    			//如果出现和card中的数字不匹配的情况
    			if(tmp==0)
    				tmp=13;
    			if(test[tmp] == false) {
    				System.out.println(111111111);
    				evaluate=false;
    				return -1;
    			}
    		}
    		evaluate=true;
    		return ((Integer)(numberStack.pop())).intValue();
    	}
    }

    public static void processAnOperator(java.util.Stack<Integer> operandStack,java.util.Stack<Character> operatorStack)
    {
        if (operatorStack.peek().equals('+'))
        {
            operatorStack.pop();
            //弹出栈顶的两个数字
            int op1 = ((Integer)(operandStack.pop())).intValue();
            int op2 = ((Integer)(operandStack.pop())).intValue();
            //把算好的数字的和压回去
            operandStack.push(Integer.valueOf(op2 + op1));
        }
        else if (operatorStack.peek().equals('-'))
        {
            operatorStack.pop();
          //弹出栈顶的两个数字
            int op1 = ((Integer)(operandStack.pop())).intValue();
            int op2 = ((Integer)(operandStack.pop())).intValue();
          //把算好的数字的差压回去
            operandStack.push(new Integer(op2 - op1));//这里是op2-op1
        }
        else if (operatorStack.peek().equals('*'))
        {
            operatorStack.pop();
            int op1 = ((Integer)(operandStack.pop())).intValue();
            int op2 = ((Integer)(operandStack.pop())).intValue();
            operandStack.push(new Integer(op2 * op1));
        }
        else if (operatorStack.peek().equals('/'))
        {
            operatorStack.pop();
            int op1 = ((Integer)(operandStack.pop())).intValue();
            int op2 = ((Integer)(operandStack.pop())).intValue();
            operandStack.push(new Integer(op2 / op1));
        }
    }

    class ImagePanel extends JPanel
    {
        public void showPicture()
        {
            int i;
            //随机生成四个数字
            for(i = 0;i < 4;i++)
            {
                //随机产生一个[1,52]的数字
                card[i] = (int)(1 + Math.random() * 52);
            }
            repaint();
        }
        
        //4张图片画出来
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            int i;
            int w = getWidth() / 4;
            int h = getHeight();
            int x = 0;
            int y = 0;
            for(i = 0;i < 4;i++)
            {
                ImageIcon imageIcon=new ImageIcon("images\\" + card[i] + ".png");
                //备份是image\\
                Image image=imageIcon.getImage();
                //if(image == null)
                    //System.out.println("! ");
                if(image!=null)
                {
                    g.drawImage(image,x,y,w,h,this);
                }
                x+=w;
            }
        }
    }
    
    //播放音乐
    public static  void music()
    {
        try{
            URL address;
            URI uri;
            File f=new File("D:\\24game\\bgm.wav");
            uri=f.toURI();
            address=uri.toURL();
			AudioClip clip;
            clip = Applet.newAudioClip(address);
            clip.loop();
            System.out.println("ready to play!");
        }catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
    }

    //设置全局字体
    public static void initGobalFont(Font font) {
        FontUIResource fontResource = new FontUIResource(font);
        for(Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements();) {
            Object key=keys.nextElement();
            Object value=UIManager.get(key);
            if(value instanceof FontUIResource) {
                System.out.println(key);
                UIManager.put(key, fontResource);
            }
        }
    }

    public static void main(String[] args)
    {
    	
        //将界面的字体初始化
    	initGobalFont(new Font("YaHei Consolas Hybrid", Font.PLAIN, 18));
        //不同风格的界面初始化		
		
		try {
			String lookAndFeel ="com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
            UIManager.setLookAndFeel(lookAndFeel);
        } catch (Exception e)
		{
        	System.out.println("can't change your UIStyle");
		}
    	// 装载可选择的主题  
    	/*
    	java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				////////////////////更换皮肤代码/////////////////
				JFrame.setDefaultLookAndFeelDecorated(true);
		        JDialog.setDefaultLookAndFeelDecorated(true);
		        try
		        {
		            //UIManager.setLookAndFeel(UIManager
		                    //.getCrossPlatformLookAndFeelClassName());
		            UIManager.setLookAndFeel("org.jvnet.substance.skin.SubstanceOfficeBlue2007LookAndFeel");
		            //SubstanceLookAndFeel.setCurrentBorderPainter(new StandardBorderPainter());
		        }
		        catch (Exception e)
		        {
		            e.printStackTrace();
		        }
				
		        new TwentyFourPoke_Game().setVisible(true);
			}
		});*/
		music();
        //进入游戏登录界面
        TwentyFourPoke_Game frame = new TwentyFourPoke_Game();
        
        
        //倒计时180s
        Timer t=new Timer();
        int cnt=180;
        Mytimer mt=new Mytimer(t, cnt);
        t.scheduleAtFixedRate(mt,0,1000);
        
        frame.setTitle("24 Poker Game");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(620,400);
        frame.setVisible(true);
        
    }
}
//倒计时
class  Mytimer extends TimerTask
{
    private  Timer timer;
    private  int countDown;

    Mytimer(Timer timer, int time)
    {
        this.countDown =time;
        this.timer=timer;
    }
    public  void  run()
    {
        //System.out.println(this.countDown);
        this.countDown--;
        if(countDown<0)
            this.timer.cancel();
    }
}


