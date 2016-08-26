package com.yangmao.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Character.UnicodeBlock;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil extends StringUtils {

    public static final char UNDERLINE='_';
    public static final String SecurityAlgorithm = "SHA-1";
    /**
     * 是否为汉字 汉字范围为 19968 - 40869
     *
     * @param src
     * @return
     */
    public static boolean isChineseCharacter(String src) {
        boolean ret = true;
        if (src == null || src.trim().length() == 0) {
            return ret;
        }

        char[] charArr = src.toCharArray();
        for (int i = 0; i < charArr.length; i++) {
            char ch = charArr[i];
            if (ch < 19968 || ch > 40869) {
                ret = false;
                break;
            }
        }
        return ret;
    }

    /**
     * 获取字符长度：汉、日、韩文字符长度为2，ASCII码等字符长度为1
     *
     * @param c
     *            字符
     * @return 字符长度
     */
    @SuppressWarnings("unused")
    private static int getSpecialCharLength(char c) {
        if (isLetter(c)) {
            return 1;
        } else {
            return 2;
        }
    }

    /**
     * 判断一个字符是Ascill字符还是其它字符（如汉，日，韩文字符）
     *
     * char c, 需要判断的字符
     * @return boolean, 返回true,Ascill字符
     */
    private static boolean isLetter(char c) {
        int k = 0x80;
        return c / k == 0;
    }

    /**
     * 去除回车换行符
     *
     * @param src
     * @return
     */
    public static String deleteReturn(String src) {
        if (src == null || src.trim().length() == 0) {
            return "";
        }
        src = src.replaceAll("\r", " ");
        src = src.replaceAll("\n", " ");
        src = src.replaceAll("\t", " ");
        return src;
    }

    /**
     * 替换特殊字符
     *
     * @param str
     * @return
     */
    public static String replaceCharacter(String str) {
        if (str == null || str.trim().length() == 0) {
            return "";
        }
        str = str.replaceAll("\r", "");
        str = str.replaceAll("\n", "");
        str = str.replaceAll("\t", "");
        return str;
    }

    public static boolean containChineseChar(String str) {
        if (str == null || str.trim().length() == 0) {
            return false;
        }
        char[] charArr = str.toCharArray();
        for (int i = 0; i < charArr.length; i++) {
            if (!isLetter(charArr[i])) {
                return true;
            }
        }
        return false;
    }




    /**
     * 过滤掉字符串中的特殊字符
     *
     * @param str
     * @return
     */
    public static String specialCharacterfilter(String str) {
        if (str == null) {
            return null;
        }
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\\\\\\ ]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    public static String filterSpecialChar4Xss(String xssStr) {
        if (xssStr == null) {
            return null;
        }

        xssStr = xssStr.replaceAll("[''\\[\\]<>/“\\\\\\ ]", "");
        return xssStr;
    }

    /**
     * 校验手机号是否正确
     *
     * @param str
     * @return
     */
    public static boolean isPhoneNumber(String str) {
        if (str == null) {
            return false;
        }
        String regEx = "^1[0-9]{10}$";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static boolean isInteger(String str){
        return str.matches("^\\d*$");
    }

    /**
     * 获取字符串里的第一个字符
     */
    public static String getFirstCharacter(String value) {
        return value.substring(0, 1);
    }

    // 身份证正则表达式[15位或18位]
    private static String idNumber = "(^[1-9]\\d{13}[0-9a-zA-Z]$)|(^[1-9]\\d{16}[0-9a-zA-Z]$)";
    private static int getDaysInMonth(int year, int month) {

        int days = 0;
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                days = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                days = 30;
                break;
            case 2:
                if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
                    days = 29;
                } else { // 不是闰年
                    days = 28;
                }
                break;
            default:
                break;
        }

        return days; // 返回一个月份的天数
    }

    /**
     * 验证身份证的合法性
     *
     * @param idCardNumber
     * @return boolean
     */
    public static boolean isIDCard(String idCardNumber) {

        boolean flag = true; // 验证身份证的合法性的标志位
        if (!idCardNumber.matches(idNumber)) { // 获取解密后的IdNumber并进行合法性校验
            flag = false; // 身份证格式不合法
        } else { // 身份证格式合法, 判断出生日期
            String year = "", month = "", day = "";
            int intYear = 0, intMonth = 0, intDay = 0, days = 0;

            if ((15 == idCardNumber.length())) {
                year = idCardNumber.substring(6, 8);
                month = idCardNumber.substring(8, 10);
                day = idCardNumber.substring(10, 12);
                intYear = Integer.parseInt(year) + 1900;
                intMonth = Integer.parseInt(month);
                intDay = Integer.parseInt(day);
                days = getDaysInMonth(intYear, intMonth);
                if ((intMonth > 12) || (intDay > days)) {
                    flag = false; // 输入的出生日期不正确
                }
            } else { // 18 == decryptIdNumber.length()
                Calendar cal = Calendar.getInstance();
                int nowYear = cal.get(Calendar.YEAR);
                year = idCardNumber.substring(6, 10);
                month = idCardNumber.substring(10, 12);
                day = idCardNumber.substring(12, 14);
                intYear = Integer.parseInt(year);
                intMonth = Integer.parseInt(month);
                intDay = Integer.parseInt(day);
                days = getDaysInMonth(intYear, intMonth);
                if ((intYear > nowYear) || (intMonth > 12) || (intDay > days)) {
                    flag = false; // 输入的出生日期不正确
                }
            }
        }

        return flag; // 返回验证身份证的合法性的标志位
    }

    public  static String joinString(char joinChar,String... strs)
	{
	    if (strs.length==0)
	        return StringUtils.EMPTY;
	    String joinStr =StringUtils.EMPTY;
	    for(String str :strs)
	    {
	        if (StringUtils.isNotEmpty(str))
	            joinStr+=str+joinChar;
	    }
	    if  (StringUtils.isNotEmpty(joinStr))
	        joinStr = StringUtil.trimEnd(joinStr, joinChar);
	    return joinStr;
	}

	public static String utf8ToUnicode(String inStr) {
        char[] myBuffer = inStr.toCharArray();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < inStr.length(); i++) {
            UnicodeBlock ub = UnicodeBlock.of(myBuffer[i]);
            if (ub == UnicodeBlock.BASIC_LATIN) {
                // 英文及数字等
                sb.append(myBuffer[i]);
            } else if (ub == UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
                // 全角半角字符
                int j = (int) myBuffer[i] - 65248;
                sb.append((char) j);
            } else {
                // 汉字
                short s = (short) myBuffer[i];
                String hexS = Integer.toHexString(s);
                String unicode = "\\u" + hexS;
                sb.append(unicode.toLowerCase());
            }
        }
        return sb.toString();
    }

    public static String unicodeToUtf8(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len;) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
        }
        return outBuffer.toString();
    }

    public static String trimEnd(String str,char c)
    {
        String trimStr =str;
        if (StringUtils.isEmpty(str))
            return StringUtils.EMPTY;
        char[] charList = str.toCharArray();
        if (charList[charList.length-1]==c)
            trimStr =str.substring(0, charList.length-1);
        return trimStr;
    }

    /**
     * 判断是否空
     * @param input
     * @return
     */
    public static boolean isNullOrEmpty(String input) {
        if (input == null)
            return true;
        if (input.equals(""))
            return true;
        return false;
    }
    
    /**
     * SH1加密
     */
    public static byte[] SHA1(String decript) {
		try {
			MessageDigest digest = MessageDigest.getInstance(SecurityAlgorithm);
			digest.update(decript.getBytes());
			byte[] messageDigest = digest.digest();
			return messageDigest;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String SHA1HexString(String decript) {
		byte[] bytes = SHA1(decript);
		if(bytes == null) {
			return null;
		}
		StringBuffer hexString = new StringBuffer();
        // 字节数组转换为 十六进制 数
        for (int i = 0; i < bytes.length; i++) {
            String shaHex = Integer.toHexString(bytes[i] & 0xFF);
            if (shaHex.length() < 2) {
                hexString.append(0);
            }
            hexString.append(shaHex);
        }
        return hexString.toString();
	}
	
	/**
	 * 
	 */
	public static void getWritter(HttpServletResponse response,String json) throws IOException{
        //以下代码从JSON.java中拷过来的
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out;
        out = response.getWriter();
        out.print(json);
        out.flush();
        out.close();
	}
	
}
