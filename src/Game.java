import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class Game extends JFrame implements ActionListener
{
	JMenuBar bar;                    
	JMenu    Menu;                 
	JMenuItem 初级,中级,高级;            
	GameRegion gameRegion;     
	
	Game()                       //初始化操作
	{
		//菜单界面初始化
		bar=new JMenuBar();  
		this.setJMenuBar(bar);      
		Menu=new JMenu("游戏");   
		bar.add(Menu); 
		初级=new JMenuItem("初级");
		中级=new JMenuItem("中级");
		高级=new JMenuItem("高级");
		Menu.add(初级);
		Menu.add(中级);
		Menu.add(高级);	                                    
		初级.addActionListener(this);     
		中级.addActionListener(this);
		高级.addActionListener(this);
		
		//中间界面初始化
		gameRegion=new GameRegion(5,5,3,1);  
		this.add(gameRegion,BorderLayout.CENTER);     
			
		//窗体初始化
		this.setBounds(350,150,300,400);                              
		this.setVisible(true);                                     
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
		this.validate();                                              //确保组件具有有效的布局           
	}
	
	public void actionPerformed(ActionEvent e)  //控件被触发所执行的事件
	{
		if(e.getSource()==初级)                  
		{
			gameRegion.initGameRegion(5,5,3,1); 
			setBounds(350,150,300,400);
		}
		else if(e.getSource()==中级)
		{
			gameRegion.initGameRegion(16,16,30,2);
			setBounds(350,150,360,480);
		}
		else if(e.getSource()==高级)
		{
			gameRegion.initGameRegion(22,22,80,3);
			setBounds(100,100,450,600);
		}
		validate();
	}
	
	public static void main(String args[])
	{
		new Game();
	}
	
}
