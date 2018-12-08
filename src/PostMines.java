import java.util.*;
import javax.swing.*;

public class PostMines 
{
	ImageIcon mineIcon;
	PostMines()
	{
		mineIcon=new ImageIcon("mine.gif");
	}
	
	public void postMinesForBlock(Block block[][],int mineCount)
	{
		int row=block.length;
		int column=block[0].length;
		LinkedList<Block> list=new LinkedList<Block>();  //LinkedList为通用链表，它支持枚举数并实现ICollection接口
		
		for(int i=0;i<row;i++)                           //将block块串成链
			for(int j=0;j<column;j++)
				list.add(block[i][j]);  
		
		while(mineCount>0)       //以中级为例,while循环是将40个雷随机分布到256个方块中
		{
			int size=list.size();//返回链表节点的个数,这里是256
			int randomIndex=(int)(Math.random()*size);  //Math.random():产生一个[0,1)之间的随机数
			//以中级为例,randomIndex是[0~256)之间的随机整数
			Block b=list.get(randomIndex);  //list返回索引为randomIndex的节点,此节点为随机分配的地雷
			b.setIsMine(true);              //设置此节点为雷
			b.setMineIcon(mineIcon);        //设置此节点的图片为mine.gif
			b.setIsOpen(false);             //该方块对象具有被挖开的成员变量isOpen为false,也就是说不能被挖开,否则就是踩中地雷了
			b.setIsMark(false);             //该方块对象具有被标记的成员变量isMark为false,也就是说程序不能标记地雷,需要用户自己右键标记
			list.remove(randomIndex);       //list删除索引值为randomIndex的节点,防止出现重复的雷块
			mineCount--;                    //分配下一个地雷
		}
		
		for(int i=0;i<row;i++)
			for(int j=0;j<column;j++)
				if(block[i][j].isMine()==false)   //如果不是雷,则要计算其周围雷的个数
				{
					int mineNumber=0;    //该方块周围雷数初始值为0
					for(int k=Math.max(i-1, 0);k<=Math.min(i+1, row-1);k++)  //以第一行第一列为例,k=0;k<=1;k++,这里k是行数
						for(int t=Math.max(j-1, 0);t<=Math.min(j+1, column-1);t++)  //t=0;t<=1;t++,这里t是列数
							if(block[k][t].isMine())
								mineNumber++;  
					block[i][j].setIsOpen(false);   //因为不是雷,程序不能自动挖开此地雷,需要用户自己挖
					block[i][j].setIsMark(false);   //程序不能自动标记
					block[i][j].setAroundMineNumber(mineNumber);  //将方块周围雷的数量的成员变量赋值为此块周围雷的个数
				}
		
	}
	
}
