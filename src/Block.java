import javax.swing.*;

public class Block
{//��һ���࣬��װ�˷�������5����Ƭ�Լ�10����Ϊ
	int aroundMineNumber;  //��Χ�׵���Ŀ
	ImageIcon mineIcon;    //�׵�ͼ��
	boolean isMine=false;  //�Ƿ�����
	boolean isMark=false;  //�Ƿ񱻱�ǣ������Ҽ���ǵ�
	boolean isOpen=false;  //�Ƿ��ڿ�
	
	public void setAroundMineNumber(int n)
	{
		aroundMineNumber=n;
	}
	public int getAroundMineNumber()
	{
		return aroundMineNumber;
	}
	
	public void setIsMine(boolean b)
	{
		isMine=b;
	}
	public boolean isMine()
	{
		return isMine;
	}
	
	public void setMineIcon(ImageIcon icon)
	{
		mineIcon=icon;
	}
	public ImageIcon getMineIcon()
	{
		return mineIcon;
	}
	
	public void setIsOpen(boolean p)
	{
		isOpen=p;
	}
	public boolean getIsOpen()
	{
		return isOpen;
	}
	
	public void setIsMark(boolean m)
	{
		isMark=m;
	}
	public boolean getIsMark()
	{
		return isMark;
	}
}
