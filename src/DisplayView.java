import javax.swing.*;
import java.awt.*;

public class DisplayView extends JPanel      //��JPanel�µ�����,�����˰�ť����ǩ�����ַ������������Լ�6����Ϊ
{//����������������ʾ��
	JLabel blockNameOrIcon;   //������ʾ�׵����ֻ��׵�ͼ��,���û��ͼƬ�ļ�������е���ֻ����ʾ���ס��������
	JButton blockCover;       //�����ڵ�blockNameOrIcon��ǩ,��Ϊ��ǩ�������׵�ͼ����ʾ
	CardLayout card;          //��Ƭʽ����
	DisplayView()
	{
		//���ò��ַ���Ϊ��Ƭʽ����
		card=new CardLayout();
		this.setLayout(card);  
		
		//����blockNameOrIcon����,�����������˶�����
		blockNameOrIcon=new JLabel("",JLabel.CENTER);   //��ǩ�ؼ�����Ϊ��,���ж���
		blockNameOrIcon.setHorizontalTextPosition(AbstractButton.CENTER);  //���ñ�ǩ���ı������ͼ���ˮƽλ��
		blockNameOrIcon.setVerticalTextPosition(AbstractButton.CENTER);    //���ñ�ǩ���ı������ͼ��Ĵ�ֱλ��
		this.add("view",blockNameOrIcon);               //blockNameOrIcon�����ǩ�ı���Ϊview
		
		//����blockCover����,�����������˶�����
		blockCover=new JButton();    
		this.add("cover",blockCover);                   //blockCover�����ť��������cover,�൱�ڱ���
	}
	
	public void giveView(Block block)
	{//�˺������ã�������ף�������blockCover�ڵ��ı�ǩ�ؼ������ֺ��׵�ͼ��;��������ף����ҽ���Χ��������1����ֵ��ʾ������������ʾ�հ�
		if(block.isMine())      //����˿�����
			blockNameOrIcon.setIcon(block.getMineIcon());//�˱�ǩ��ͼ�����׵�ͼ��,Ҳ����mine.gif
		else                  //����˿鲻����
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
		card.show(this,"view");           //blockNameOrIcon�����ǩ�ı���Ϊview
		validate();
	}
	
	public void seeBlockCover()
	{
		card.show(this,"cover");          //��ʾblockCover��ť�����ڵ�����
		validate();
	}
	
	public JButton getBlockCover()
	{
		return blockCover;
	}
}
