package com.cn.roguelike.util;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串操作类
 * 
 * @author Thinker
 */
public class LLStringHelper
{
	/** 单例 */
	private static final LLStringHelper instance=new LLStringHelper();
	/** 随机数对象 */
	private static final Random random=new Random();
	/** 数字与字母字典 */
	private static final char[] LETTER_AND_DIGIT=("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
	/** 数字与字母字典长度 */
	private static final int LETTER_AND_DIGIT_LENGTH=LETTER_AND_DIGIT.length;

	private LLStringHelper()
	{
	}

	/**
	 * 取得该类唯一实例
	 * 
	 * @return 该类唯实例
	 */
	public static LLStringHelper instance()
	{
		return instance;
	}

	/**
	 * 将对象转换为字符串
	 * 
	 * @param input
	 *            待转换对象
	 * @param defVal
	 *            对象转换为空字符串时的默认返回值
	 * @return 转换后的字符串
	 * @see #getString(String)
	 * @see #getString(String, String)
	 */
	public static String getString(Object input,String defVal)
	{
		return (input==null)?defVal:getString(input.toString(),defVal);
	}


	/**
	 * 转换字符串
	 * 
	 * @param input
	 *            待转换字符串
	 * @param defVal
	 *            默认转换值
	 * @return 转换后的字符串 <li>字符串为null或全部由空白字符组成的字符串时，返回defVal参数指定的值</li> <li>
	 *         其他情况，返回去掉字符串两端空白字符后的字符串</li>
	 */
	public static String getString(String input,String defVal)
	{
		return (isEmpty(input))?defVal:input.trim();
	}

	/**
	 * 生成固定长度的随机数
	 * 
	 * @param len
	 *            随机数长度
	 * @return 生成的随机数
	 */
	public static String getRandomString(final int len)
	{
		if(len<1) return "";
		StringBuilder sb=new StringBuilder(len);
		for(int i=0;i<len;i++)
		{
			sb.append(LETTER_AND_DIGIT[random.nextInt(LETTER_AND_DIGIT_LENGTH)]);
		}
		return sb.toString();
	}

	/**
	 * 对传入的字符串进行MD5加密
	 * 
	 * @param plainText
	 * @return
	 */
	public static String MD5(String plainText)
	{
		try
		{
			MessageDigest md=MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[]=md.digest();
			int i;
			StringBuffer buf=new StringBuffer("");
			for(int offset=0;offset<b.length;offset++)
			{
				i=b[offset];
				if(i<0) i+=256;
				if(i<16) buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			return buf.toString();
		}catch(NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static String trim(String str)
	{
		if(str==null)
		{
			str="";
		}else
		{
			str=str.trim();
		}
		if(str.length()==0)
		{
			return str;
		}

		if(str.charAt(0)=='"')
		{
			str=str.substring(1);
		}

		if(str.charAt(str.length()-1)=='"')
		{
			str=str.substring(0,str.length()-1);
		}

		return str;
	}

	public static String[] getStringList(String str)
	{
		str=trim(str);
		if(str.endsWith(","))
		{
			str=str.substring(0,str.length()-1);
		}
		String sep=",";
		if(str.indexOf(':')>=0)
		{
			sep=":";
		}
		return str.split(sep);
	}

	public static String[] getStringList(String str,String sep)
	{
		str=trim(str);
		return str.split(sep);
	}

	public static int[] getIntArray(String str,String sep)
	{
		String[] prop=getStringList(str,sep);
		List<Integer> tmp=new ArrayList<Integer>();
		for(int i=0;i<prop.length;i++)
		{
			try
			{
				int r=Integer.parseInt(prop[i]);
				tmp.add(r);
			}catch(Exception e)
			{
				throw new RuntimeException(e);
			}
		}
		int[] ints=new int[tmp.size()];
		for(int i=0;i<tmp.size();i++)
		{
			ints[i]=tmp.get(i);
		}
		return ints;
	}

	public static List<Integer> getIntList(String str,String sep)
	{
		List<Integer> tmp=new ArrayList<Integer>();
		if(str==null||str.trim().equals(""))
		{
			return tmp;
		}
		String[] prop=getStringList(str,sep);
		for(int i=0;i<prop.length;i++)
		{
			try
			{
				int r=Integer.parseInt(prop[i]);
				tmp.add(r);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return tmp;
	}

	public static String join(String[] strs,String sep)
	{
		StringBuffer buffer=new StringBuffer();
		buffer.append(strs[0]);
		for(int i=1;i<strs.length;i++)
		{
			buffer.append(sep).append(strs[i]);
		}
		return buffer.toString();
	}

	public static String join(List<Integer> ints,String sep)
	{
		StringBuffer sb=new StringBuffer();
		sb.append(ints.get(0));
		for(int i=1;i<ints.size();i++)
		{
			sb.append(sep).append(ints.get(i));
		}
		return sb.toString();
	}

	public static double[] getDoubleList(String str)
	{
		String[] prop=getStringList(str);
		double[] ds=new double[prop.length];
		for(int i=0;i<ds.length;i++)
		{
			ds[i]=Double.parseDouble(prop[i]);
		}
		return ds;
	}

	public static List<String> getListBySplit(String str,String split)
	{
		List<String> list=new ArrayList<String>();
		if(str==null||str.trim().equalsIgnoreCase("")) return null;
		String[] strs=str.split(split);
		for(String temp:strs)
		{
			if(temp!=null&&!temp.trim().equalsIgnoreCase(""))
			{
				list.add(temp.trim());
			}
		}
		return list;
	}

	public static int[] getIntList(String str)
	{
		String[] prop=getStringList(str);
		List<Integer> tmp=new ArrayList<Integer>();
		for(int i=0;i<prop.length;i++)
		{
			try
			{
				String sInt=prop[i].trim();
				if(sInt.length()<20)
				{
					int r=Integer.parseInt(prop[i].trim());
					tmp.add(r);
				}
			}catch(Exception e)
			{

			}
		}
		int[] ints=new int[tmp.size()];
		for(int i=0;i<tmp.size();i++)
		{
			ints[i]=tmp.get(i);
		}
		return ints;

	}

	public static String toWrapString(Object obj,String content)
	{
		if(obj==null)
		{
			return "null";
		}else
		{
			return obj.getClass().getName()+"@"+obj.hashCode()+"[\r\n"+content+"\r\n]";
		}
	}

	// 将1,2,3和{1,2,3}格式的字符串转化为JDK的bitset
	// 考虑了两边是否有{}，数字两边是否有空格，是否合法数字
	public static BitSet bitSetFromString(String str)
	{
		if(str==null)
		{
			return new BitSet();
		}
		if(str.startsWith("{"))
		{
			str=str.substring(1);
		}
		if(str.endsWith("}"))
		{
			str=str.substring(0,str.length()-1);
		}
		int[] ints=getIntList(str);
		BitSet bs=new BitSet();
		for(int i:ints)
		{
			bs.set(i);
		}
		return bs;
	}

	public static boolean hasExcludeChar(String str)
	{
		if(str!=null)
		{
			char[] chs=str.toCharArray();
			for(int i=0;i<chs.length;i++)
			{
				if(Character.getType(chs[i])==Character.PRIVATE_USE)
				{
					return true;
				}
			}
		}
		return false;
	}

	public static String replaceSql(String str)
	{
		if(str!=null)
		{
			return str.replaceAll("'","’").replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("\"","&quot;");
		}
		return "";
	}

	/**
	 * 判断两个字符串是否相等
	 * 
	 * @param s1
	 * @param s2
	 * @return true,字符串相等;false,字符串不相等
	 */
	public static boolean isEquals(String s1,String s2)
	{
		if(s1!=null)
		{
			return s1.equals(s2);
		}
		if(s2!=null)
		{
			return false;
		}
		// 两个字符串都是null
		return true;
	}

	/**
	 * 判断字符串是否时数字 包含负数和带小数点
	 * 
	 * @param text
	 * @return
	 */
	public static boolean isDigit(String text)
	{
		String reg="[-]*[\\d]+[\\.\\d+]*";
		Pattern pat=Pattern.compile(reg);
		Matcher mat=pat.matcher(text);
		return mat.matches();
	}

	/**
	 * 判断一句话是否是汉语
	 * 
	 * @param text
	 * @return
	 */
	public static boolean isChiness(String text)
	{
		String reg="[\\w]*[\\u4e00-\\u9fa5]+[\\w]*";
		Pattern pat=Pattern.compile(reg);
		Matcher mat=pat.matcher(text);
		boolean result=mat.matches();
		return result;
	}

	/**
	 * 判断单个字符是否是汉语
	 * 
	 * @param cha
	 * @return
	 */
	public static boolean isChineseChar(char cha)
	{
		String reg="[\\u4e00-\\u9fa5]";
		Pattern pat=Pattern.compile(reg);
		String text=Character.toString(cha);
		Matcher mat=pat.matcher(text);
		boolean result=mat.matches();
		return result;
	}

	/**
	 * 判断字符是否是字母(包括大小写)或者数字
	 * 
	 * @param cha
	 * @return
	 */
	public static boolean isLetterAndDigit(String cha)
	{
		String reg="[\\w]+";
		Pattern pat=Pattern.compile(reg);
		Matcher mat=pat.matcher(cha);
		boolean result=mat.matches();
		return result;
	}

	/**
	 * 返回字符串中汉字的数量
	 * 
	 * @param test
	 * @return
	 */
	public static int getChineseCount(String test)
	{
		int count=0;
		boolean tempResult=false;
		for(int i=0;i<test.length();i++)
		{
			char cha=test.charAt(i);
			tempResult=isChineseChar(cha);
			if(tempResult)
			{
				count++;
			}
		}
		return count;
	}

	/**
	 * 返回字符串中字母和数字的个数，其中字母包括大小写
	 * 
	 * @param text
	 * @return
	 */
	public static int getLetterAndDigitCount(String text)
	{
		int count=0;
		boolean tempResult=false;
		for(int i=0;i<text.length();i++)
		{
			tempResult=isLetterAndDigit(text);
			if(tempResult)
			{
				count++;
			}
		}
		return count;
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return true,字符串是空的;false,字符串不是空的
	 */
	public static boolean isEmpty(String str)
	{
		if(str==null||(str.trim().length()==0))
		{
			return true;
		}
		return false;
	}

	public static boolean isBlank(final CharSequence cs)
	{
		int strLen;
		if(cs==null||(strLen=cs.length())==0)
		{
			return true;
		}
		for(int i=0;i<strLen;i++)
		{
			if(Character.isWhitespace(cs.charAt(i))==false)
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * 将字符串首字母大写
	 * 
	 * @param s
	 * @return
	 */
	public static String upperCaseFirstCharOnly(String s)
	{
		if(s==null||s.length()<1)
		{
			return s;
		}
		return s.substring(0,1).toUpperCase()+s.substring(1).toLowerCase();
	}

	/**
	 * 清除源字符串左边的字符串
	 * 
	 * @param src
	 * @param s
	 * @return
	 * 
	 */
	public static String trimLeft(String src,String s)
	{
		if(src==null||src.isEmpty())
		{
			return "";
		}

		if(s==null||s.isEmpty())
		{
			return src;
		}

		if(src.equals(s))
		{
			return "";
		}

		while(src.startsWith(s))
		{
			src=src.substring(s.length());
		}

		return src;
	}

	/**
	 * 清除源字符串右边的字符串
	 * 
	 * @param src
	 * @param s
	 * @return
	 * 
	 */
	public static String trimRight(String src,String s)
	{
		if(src==null||src.isEmpty())
		{
			return "";
		}

		if(s==null||s.isEmpty())
		{
			return src;
		}

		if(src.equals(s))
		{
			return "";
		}

		while(src.endsWith(s))
		{
			src=src.substring(0,src.length()-s.length());
		}

		return src;
	}
}
