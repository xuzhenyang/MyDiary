package control;

public class TextProcess
{
	public static String writeProcess(String inputStr)
	{
		//txt文档的换行符是\r\n，而JTextPane换行符是\n，需要用此方法转换
		//写入文本时，增加'\r'
		StringBuffer stringBuf = new StringBuffer(inputStr);
		char [] stringArr = inputStr.toCharArray();
		//双下标i,j防止错位
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
		//读取时可以直接在每行后加上换行符 所以暂时不需要此方法
		//从txt文档读取时，删除'\r'
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

