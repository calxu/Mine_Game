import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class Game extends JFrame implements ActionListener
{
	JMenuBar bar;                    
	JMenu    Menu;                 
	JMenuItem ����,�м�,�߼�;            
	GameRegion gameRegion;     
	
	Game()                       //��ʼ������
	{
		//�˵������ʼ��
		bar=new JMenuBar();  
		this.setJMenuBar(bar);      
		Menu=new JMenu("��Ϸ");   
		bar.add(Menu); 
		����=new JMenuItem("����");
		�м�=new JMenuItem("�м�");
		�߼�=new JMenuItem("�߼�");
		Menu.add(����);
		Menu.add(�м�);
		Menu.add(�߼�);	                                    
		����.addActionListener(this);     
		�м�.addActionListener(this);
		�߼�.addActionListener(this);
		
		//�м�����ʼ��
		gameRegion=new GameRegion(5,5,3,1);  
		this.add(gameRegion,BorderLayout.CENTER);     
			
		//�����ʼ��
		this.setBounds(350,150,300,400);                              
		this.setVisible(true);                                     
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
		this.validate();                                              //ȷ�����������Ч�Ĳ���           
	}
	
	public void actionPerformed(ActionEvent e)  //�ؼ���������ִ�е��¼�
	{
		if(e.getSource()==����)                  
		{
			gameRegion.initGameRegion(5,5,3,1); 
			setBounds(350,150,300,400);
		}
		else if(e.getSource()==�м�)
		{
			gameRegion.initGameRegion(16,16,30,2);
			setBounds(350,150,360,480);
		}
		else if(e.getSource()==�߼�)
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
