import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameRegion extends JPanel implements ActionListener,MouseListener
{
	JPanel pCenter,pNorth; 
	JTextField showMarkedMineCount,showTime;  
	JButton reStart; 
	int row,column,mineCount,markCount,grade,spendTime=0; 
	DisplayView[][] displayView; 	
	Block[][] block;                                 
	ImageIcon mark,mine;                            
	Timer time;                               
	public GameRegion(int row,int column,int mineCount,int grade)
	{	
		mine=new ImageIcon("mine.gif"); 
		mark=new ImageIcon("mark.gif"); 
		this.setLayout(new BorderLayout()); 
		
		//pNorth��������
		pNorth=new JPanel();  
		/*1.�������ı�����ӵ�pNorth�����*/
		showMarkedMineCount=new JTextField(3);  
		showMarkedMineCount.setHorizontalAlignment(JTextField.CENTER);
		showMarkedMineCount.setFont(new Font("Arial",Font.BOLD,16));
		pNorth.add(showMarkedMineCount); 
		/*2.�����¿�ʼ��ť��ӵ�pNorth�����*/
		reStart=new JButton("���¿�ʼ"); 
		reStart.addActionListener(this); 
		pNorth.add(reStart);
		/*3.����ʱ����ӵ�pNorth�����*/
		time=new Timer(1000,this);           //ÿ��1000�����ʱһ��
		showTime=new JTextField(3);  
		showTime.setHorizontalAlignment(JTextField.CENTER);  
		showTime.setFont(new Font("Arial",Font.BOLD,16));    
		pNorth.add(showTime);
		//pNorth��岼��
		add(pNorth,BorderLayout.NORTH);
		
		//pCenter�������
		pCenter=new JPanel();            	            
		initGameRegion(row,column,mineCount,grade); 
		add(pCenter,BorderLayout.CENTER); 
	}
	
    public void initGameRegion(int row,int column,int mineCount,int grade)
    { 
    	pCenter.setLayout(new GridLayout(row,column));
    	this.row=row;      
    	this.column=column;
    	this.mineCount=mineCount;
    	this.grade=grade;
    	
    	//�Ϸ����ĳ�ʼ��
    	markCount=mineCount;
    	showMarkedMineCount.setText(""+markCount); 
    	spendTime=0;
    	showTime.setText(""+spendTime);
    	
    	//pCenter���ĳ�ʼ��
    	pCenter.removeAll();            
    	block=new Block[row][column]; 
    	for(int i=0;i<row;i++)      
    		for(int j=0;j<column;j++)   
    			block[i][j]=new Block();
    	PostMines post;
    	post=new PostMines();
    	post.postMinesForBlock(block,mineCount); 
    	displayView=new DisplayView[row][column];  
    	
    	
    	for(int i=0;i<row;i++)           //����forѭ����������Ϊ������䵽�׵�������ť���µı�ǩ�ṩ�׵����ֺ��׵�ͼ��
    	{
    		for(int j=0;j<column;j++)
    		{
    			displayView[i][j]=new DisplayView();
    			displayView[i][j].getBlockCover().setBackground(new Color(255,255,255));  //����������Ť�ı�����ɫΪ��ɫ
    			displayView[i][j].giveView(block[i][j]);  //��block[i][j]�����Ӧ�ı�ǩ�ṩ���ֺ�ͼ��,��ʾ��BlockView JPanel�����    			
    			displayView[i][j].seeBlockCover();      //��ʾ��ť,�ڵ���ǩ�µĵ���
    			displayView[i][j].getBlockCover().setEnabled(true);  //��BlockCover��ť����Ϊ����״̬
    			displayView[i][j].getBlockCover().addActionListener(this);  //Ϊ�����ڵ�blockNameOrIcon��ǩ�İ�ťBlockCover����¼�������
    			displayView[i][j].getBlockCover().addMouseListener(this);   //Ϊ�����ڵ�blockNameOrIcon��ǩ�İ�ťBlockCover�����������;�����Ҽ�
    			pCenter.add(displayView[i][j]);           //��displayView[i][j]������ӵ�pCenter�����
    		}
    	}
    	validate();       //ȷ�����������Ч�Ĳ���
    }
    
    public void show(int m,int n)
    {
    	if(block[m][n].getAroundMineNumber()>0 && block[m][n].getIsOpen()==false)
    	{//���ѡ�в��Ҳ����׵ķ�����Χ��������0����δ�����ڿ�����û��ʧ�ܣ�
    		displayView[m][n].seeBlockNameOrIcon();  
    		block[m][n].setIsOpen(true);           
    	}
    	else if(block[m][n].getIsOpen()==false)
    	{//���ѡ�в��Ҳ����׵ķ�����Χ��������0����δ�����ڿ�
    		displayView[m][n].seeBlockNameOrIcon();  
    		block[m][n].setIsOpen(true);          
    		for(int k=Math.max(m-1, 0);k<=Math.min(m+1, row-1);k++)
    			for(int t=Math.max(n-1, 0);t<=Math.min(n+1, column-1);t++)
    				show(k,t);  //�ݹ����show����,�鿴��Χ�����Ƿ�����
    	}
    }
    
    public void inquireWin()
    {
    	int number=0;
    	for(int i=0;i<row;i++)
    		for(int j=0;j<column;j++)
    			if(block[i][j].getIsOpen()==false)       //�����׵ķ���û�ڿ��������������ڿ���
    				number++;
    	
    	if(number==mineCount)
    	{
    		time.stop();
    		JOptionPane.showConfirmDialog(this, "����ʤ��!!","��ϲ�㣡",JOptionPane.DEFAULT_OPTION);
    	}
    }

    public void actionPerformed(ActionEvent e)      //�Ƿ�����ʱ�䵽1000ms
    {
    	if(e.getSource()==time)
    	{
    		spendTime++;
    		showTime.setText(""+spendTime);
    	}
    	
    	if(e.getSource()!=reStart && e.getSource()!=time)    //����¼�Դ�������ԡ����¿�ʼ����ť����time����;˵��������ͨ�����İ�ť
    	{
    		time.start();
    		int m=-1,n=-1;                  //��ֵΪ-1�����������г�ֵ
    		
    		for(int i=0;i<row;i++)          //������forѭ�������ǻ�ȡ�����������������±�(m,n)
    			for(int j=0;j<column;j++)
    				if(e.getSource()==displayView[i][j].getBlockCover())   
    				{
    					m=i;
    					n=j;
    					break;    //�±��ȡ�ɹ�,����break
    				}
    		
    		if(block[m][n].isMine())     //������������ĳһ����ť,���������:m,n����������һ���������ť������
    		{
    			for(int i=0;i<row;i++)
    				for(int j=0;j<column;j++)  //������forѭ�����������û����е��׵�ǰ���½����������׶���ʾ����
    				{
    					displayView[i][j].getBlockCover().setEnabled(false);
    					if(block[i][j].isMine())   //����ÿ�����
    						displayView[i][j].seeBlockNameOrIcon();  //��ʾ������µı�ǩ�ؼ��Ĳ�����Ҳ�����׵����ƺ��׵�ͼ��
    				}
        		time.stop();  //����
        		spendTime=0;  //��ʱ������
        		markCount=mineCount; //�����Ϊ�׵ĸ���
    		}
        	else
        	{//����˿鲻����
        		show(m,n);   
        	}
    	}
    	else if(e.getSource()==reStart)
    	{
    		time.stop();
    		initGameRegion(row,column,mineCount,grade);
    	}
    
    	inquireWin();
    }
   
    public void mousePressed(MouseEvent e)        //��������Ҽ�
    {
    	JButton source=(JButton)e.getSource();
    	for(int i=0;i<row;i++)
    		for(int j=0;j<column;j++)
    			if(e.getModifiers()==InputEvent.BUTTON3_MASK && source==displayView[i][j].getBlockCover())
    			{//e.getModifiers()���ط����˶����¼��ڼ䰴�µ��޸ļ�;BUTTON3_MASK3�ǰ��µ�����Ҽ�
    				if(block[i][j].getIsMark())
    				{//����ÿ��Ѿ����
    					source.setIcon(null);          //source��ť�ϵ�ͼ��Ϊ��
    					block[i][j].setIsMark(false);  //�ÿ��ѱ�ǣ��Ҽ��ٵ�������ÿ�����Ϊδ��ǣ�Ҳ����ȥ����ǵ�ͼ��
    					markCount++;                   //�����������1
    					showMarkedMineCount.setText(""+markCount); //���ı�������ʾ��ǵ�����
    				}
    				else
    				{
    					if(markCount>0)          
    					{
    						source.setIcon(mark);    //����ť����ʾ��ǵ�ͼ��
    						block[i][j].setIsMark(true);  //�ÿ������ѱ��   
    						markCount--;                 //�������һ�����ɱ�ǵ���������һ��
    					}
    					showMarkedMineCount.setText(""+markCount);
    				}
    				break;
    			}

    }
    

   
    
    
    
    
    
    
    
    
    public void mouseReleased(MouseEvent e)
    {}
    public void mouseEntered(MouseEvent e)
    {}
    public void mouseExited(MouseEvent e)
    {}
    public void mouseClicked(MouseEvent e)
    {}
}
