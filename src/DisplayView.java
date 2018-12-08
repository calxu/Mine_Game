import javax.swing.*;
import java.awt.*;

public class DisplayView extends JPanel      //是JPanel下的子类,包含了按钮、标签、布局方案三个对象以及6种行为
{//是雷区方块的外观显示类
	JLabel blockNameOrIcon;   //用来显示雷的名字或雷的图标,如果没有图片文件，则踩中地雷只能显示“雷”这个汉字
	JButton blockCover;       //用来遮挡blockNameOrIcon标签,因为标签上面有雷的图标提示
	CardLayout card;          //卡片式布局
	DisplayView()
	{
		//设置布局方案为卡片式布局
		card=new CardLayout();
		this.setLayout(card);  
		
		//创建blockNameOrIcon对象,并将其添加入此对象中
		blockNameOrIcon=new JLabel("",JLabel.CENTER);   //标签控件内容为空,居中对齐
		blockNameOrIcon.setHorizontalTextPosition(AbstractButton.CENTER);  //设置标签的文本相对其图像的水平位置
		blockNameOrIcon.setVerticalTextPosition(AbstractButton.CENTER);    //设置标签的文本相对其图像的垂直位置
		this.add("view",blockNameOrIcon);               //blockNameOrIcon这个标签的别名为view
		
		//创建blockCover对象,并将其添加入此对象中
		blockCover=new JButton();    
		this.add("cover",blockCover);                   //blockCover这个按钮的名字是cover,相当于别名
	}
	
	public void giveView(Block block)
	{//此函数作用：如果是雷，设置由blockCover遮挡的标签控件的名字和雷的图标;如果不是雷，并且将周围雷数大于1的数值显示出来，否则显示空白
		if(block.isMine())      //如果此块是雷
			blockNameOrIcon.setIcon(block.getMineIcon());//此标签的图标是雷的图标,也就是mine.gif
		else                  //如果此块不是雷
		{
			int n=block.getAroundMineNumber();  
			if(n>=1)
				blockNameOrIcon.setText(""+n);   
			else
				blockNameOrIcon.setText("");
		}
	}
	
	public void seeBlockNameOrIcon()
	{
		card.show(this,"view");           //blockNameOrIcon这个标签的别名为view
		validate();
	}
	
	public void seeBlockCover()
	{
		card.show(this,"cover");          //显示blockCover按钮，起遮挡作用
		validate();
	}
	
	public JButton getBlockCover()
	{
		return blockCover;
	}
}
