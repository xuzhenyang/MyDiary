package control;

public class TextProcess
{
	public static String writeProcess(String inputStr)
	{
		//txt�ĵ��Ļ��з���\r\n����JTextPane���з���\n����Ҫ�ô˷���ת��
		//д���ı�ʱ������'\r'
		StringBuffer stringBuf = new StringBuffer(inputStr);
		char [] stringArr = inputStr.toCharArray();
		//˫�±�i,j��ֹ��λ
		for(int i = 0, j = 0; i < stringArr.length; i++, j++)
		{
			if(stringArr[i] == '\n')
			{
				stringBuf.insert(j, '\r');
				j++;
			}
		}
		inputStr = stringBuf.toString();
		return inputStr;
	}
	
	public static String readProcess(String inputStr)
	{
		//��ȡʱ����ֱ����ÿ�к���ϻ��з� ������ʱ����Ҫ�˷���
		//��txt�ĵ���ȡʱ��ɾ��'\r'
		StringBuffer stringBuf = new StringBuffer(inputStr);
		char [] stringArr = inputStr.toCharArray();
		for(int i = 0, j = 0; i < stringArr.length; i++, j++)
		{
			if(stringArr[i] == '\r')
			{
				stringBuf.deleteCharAt(j);
				j--;
			}
		}
		inputStr = stringBuf.toString();
		return inputStr;
	}
}

