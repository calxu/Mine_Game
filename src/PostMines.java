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
		LinkedList<Block> list=new LinkedList<Block>();  //LinkedListΪͨ��������֧��ö������ʵ��ICollection�ӿ�
		
		for(int i=0;i<row;i++)                           //��block�鴮����
			for(int j=0;j<column;j++)
				list.add(block[i][j]);  
		
		while(mineCount>0)       //���м�Ϊ��,whileѭ���ǽ�40��������ֲ���256��������
		{
			int size=list.size();//��������ڵ�ĸ���,������256
			int randomIndex=(int)(Math.random()*size);  //Math.random():����һ��[0,1)֮��������
			//���м�Ϊ��,randomIndex��[0~256)֮����������
			Block b=list.get(randomIndex);  //list��������ΪrandomIndex�Ľڵ�,�˽ڵ�Ϊ�������ĵ���
			b.setIsMine(true);              //���ô˽ڵ�Ϊ��
			b.setMineIcon(mineIcon);        //���ô˽ڵ��ͼƬΪmine.gif
			b.setIsOpen(false);             //�÷��������б��ڿ��ĳ�Ա����isOpenΪfalse,Ҳ����˵���ܱ��ڿ�,������ǲ��е�����
			b.setIsMark(false);             //�÷��������б���ǵĳ�Ա����isMarkΪfalse,Ҳ����˵�����ܱ�ǵ���,��Ҫ�û��Լ��Ҽ����
			list.remove(randomIndex);       //listɾ������ֵΪrandomIndex�Ľڵ�,��ֹ�����ظ����׿�
			mineCount--;                    //������һ������
		}
		
		for(int i=0;i<row;i++)
			for(int j=0;j<column;j++)
				if(block[i][j].isMine()==false)   //���������,��Ҫ��������Χ�׵ĸ���
				{
					int mineNumber=0;    //�÷�����Χ������ʼֵΪ0
					for(int k=Math.max(i-1, 0);k<=Math.min(i+1, row-1);k++)  //�Ե�һ�е�һ��Ϊ��,k=0;k<=1;k++,����k������
						for(int t=Math.max(j-1, 0);t<=Math.min(j+1, column-1);t++)  //t=0;t<=1;t++,����t������
							if(block[k][t].isMine())
								mineNumber++;  
					block[i][j].setIsOpen(false);   //��Ϊ������,�������Զ��ڿ��˵���,��Ҫ�û��Լ���
					block[i][j].setIsMark(false);   //�������Զ����
					block[i][j].setAroundMineNumber(mineNumber);  //��������Χ�׵������ĳ�Ա������ֵΪ�˿���Χ�׵ĸ���
				}
		
	}
	
}
