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
		
		//pNorth面板的设置
		pNorth=new JPanel();  
		/*1.将雷数文本框添加到pNorth面板中*/
		showMarkedMineCount=new JTextField(3);  
		showMarkedMineCount.setHorizontalAlignment(JTextField.CENTER);
		showMarkedMineCount.setFont(new Font("Arial",Font.BOLD,16));
		pNorth.add(showMarkedMineCount); 
		/*2.将重新开始按钮添加到pNorth面板中*/
		reStart=new JButton("重新开始"); 
		reStart.addActionListener(this); 
		pNorth.add(reStart);
		/*3.将计时器添加到pNorth面板中*/
		time=new Timer(1000,this);           //每隔1000毫秒计时一次
		showTime=new JTextField(3);  
		showTime.setHorizontalAlignment(JTextField.CENTER);  
		showTime.setFont(new Font("Arial",Font.BOLD,16));    
		pNorth.add(showTime);
		//pNorth面板布局
		add(pNorth,BorderLayout.NORTH);
		
		//pCenter面板设置
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
    	
    	//上方面板的初始化
    	markCount=mineCount;
    	showMarkedMineCount.setText(""+markCount); 
    	spendTime=0;
    	showTime.setText(""+spendTime);
    	
    	//pCenter面板的初始化
    	pCenter.removeAll();            
    	block=new Block[row][column]; 
    	for(int i=0;i<row;i++)      
    		for(int j=0;j<column;j++)   
    			block[i][j]=new Block();
    	PostMines post;
    	post=new PostMines();
    	post.postMinesForBlock(block,mineCount); 
    	displayView=new DisplayView[row][column];  
    	
    	
    	for(int i=0;i<row;i++)           //两层for循环的作用是为随机分配到雷的雷区按钮底下的标签提供雷的名字和雷的图标
    	{
    		for(int j=0;j<column;j++)
    		{
    			displayView[i][j]=new DisplayView();
    			displayView[i][j].getBlockCover().setBackground(new Color(255,255,255));  //设置雷区按扭的背景颜色为白色
    			displayView[i][j].giveView(block[i][j]);  //给block[i][j]方块对应的标签提供数字和图标,显示在BlockView JPanel面板中    			
    			displayView[i][j].seeBlockCover();      //显示按钮,遮挡标签下的地雷
    			displayView[i][j].getBlockCover().setEnabled(true);  //将BlockCover按钮设置为可用状态
    			displayView[i][j].getBlockCover().addActionListener(this);  //为用来遮挡blockNameOrIcon标签的按钮BlockCover添加事件监视器
    			displayView[i][j].getBlockCover().addMouseListener(this);   //为用来遮挡blockNameOrIcon标签的按钮BlockCover添加鼠标监听器;监听右键
    			pCenter.add(displayView[i][j]);           //将displayView[i][j]对象添加到pCenter面板中
    		}
    	}
    	validate();       //确保组件具有有效的布局
    }
    
    public void show(int m,int n)
    {
    	if(block[m][n].getAroundMineNumber()>0 && block[m][n].getIsOpen()==false)
    	{//如果选中并且不是雷的方块周围雷数大于0并且未曾被挖开（还没有失败）
    		displayView[m][n].seeBlockNameOrIcon();  
    		block[m][n].setIsOpen(true);           
    	}
    	else if(block[m][n].getIsOpen()==false)
    	{//如果选中并且不是雷的方块周围雷数等于0并且未曾被挖开
    		displayView[m][n].seeBlockNameOrIcon();  
    		block[m][n].setIsOpen(true);          
    		for(int k=Math.max(m-1, 0);k<=Math.min(m+1, row-1);k++)
    			for(int t=Math.max(n-1, 0);t<=Math.min(n+1, column-1);t++)
    				show(k,t);  //递归调用show方法,查看周围方块是否有雷
    	}
    }
    
    public void inquireWin()
    {
    	int number=0;
    	for(int i=0;i<row;i++)
    		for(int j=0;j<column;j++)
    			if(block[i][j].getIsOpen()==false)       //除了雷的方块没挖开，其他方法都挖开了
    				number++;
    	
    	if(number==mineCount)
    	{
    		time.stop();
    		JOptionPane.showConfirmDialog(this, "您已胜利!!","恭喜你！",JOptionPane.DEFAULT_OPTION);
    	}
    }

    public void actionPerformed(ActionEvent e)      //是否点击或时间到1000ms
    {
    	if(e.getSource()==time)
    	{
    		spendTime++;
    		showTime.setText(""+spendTime);
    	}
    	
    	if(e.getSource()!=reStart && e.getSource()!=time)    //如果事件源不是来自“重新开始”按钮或是time对象;说明来自普通雷区的按钮
    	{
    		time.start();
    		int m=-1,n=-1;                  //赋值为-1，就是让其有初值
    		
    		for(int i=0;i<row;i++)          //此两层for循环作用是获取被点击的雷区方块的下标(m,n)
    			for(int j=0;j<column;j++)
    				if(e.getSource()==displayView[i][j].getBlockCover())   
    				{
    					m=i;
    					n=j;
    					break;    //下标获取成功,所以break
    				}
    		
    		if(block[m][n].isMine())     //如果点击雷区的某一个按钮,其底下是雷:m,n就是雷区这一个被点击按钮的坐标
    		{
    			for(int i=0;i<row;i++)
    				for(int j=0;j<column;j++)  //这两层for循环作用是在用户踩中地雷的前提下将雷区所有雷都显示出来
    				{
    					displayView[i][j].getBlockCover().setEnabled(false);
    					if(block[i][j].isMine())   //如果该块有雷
    						displayView[i][j].seeBlockNameOrIcon();  //显示方块底下的标签控件的参数，也就是雷的名称和雷的图标
    				}
        		time.stop();  //判输
        		spendTime=0;  //计时器归零
        		markCount=mineCount; //标记数为雷的个数
    		}
        	else
        	{//否则此块不是雷
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
   
    public void mousePressed(MouseEvent e)        //监听鼠标右键
    {
    	JButton source=(JButton)e.getSource();
    	for(int i=0;i<row;i++)
    		for(int j=0;j<column;j++)
    			if(e.getModifiers()==InputEvent.BUTTON3_MASK && source==displayView[i][j].getBlockCover())
    			{//e.getModifiers()返回发生此动作事件期间按下的修改键;BUTTON3_MASK3是按下的鼠标右键
    				if(block[i][j].getIsMark())
    				{//如果该块已经标记
    					source.setIcon(null);          //source按钮上的图标为空
    					block[i][j].setIsMark(false);  //该块已标记，右键再点击，将该块设置为未标记，也就是去除标记的图标
    					markCount++;                   //标记雷数自增1
    					showMarkedMineCount.setText(""+markCount); //在文本框里显示标记的雷数
    				}
    				else
    				{
    					if(markCount>0)          
    					{
    						source.setIcon(mark);    //将按钮上显示标记的图标
    						block[i][j].setIsMark(true);  //该块设置已标记   
    						markCount--;                 //标记少了一个，可标记的雷数就少一个
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
