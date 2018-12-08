import javax.swing.*;

public class Block
{//是一个类，封装了方块对象的5个特片以及10种行为
	int aroundMineNumber;  //周围雷的数目
	ImageIcon mineIcon;    //雷的图标
	boolean isMine=false;  //是否是雷
	boolean isMark=false;  //是否被标记，是用右键标记的
	boolean isOpen=false;  //是否被挖开
	
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
