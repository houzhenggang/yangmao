package com.yangmao.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.dbcp.BasicDataSource;


public class insertDB {
	private static BasicDataSource dataSource=new BasicDataSource();
	
    public static void main( String[] args ) throws SQLException
    {
    	List<String> fileNames=FileViewer.getListFiles("D:/sending", "txt", true);
    	//init data Source    	
    	dataSource.setUrl("jdbc:mysql://123.206.64.32:3306/yangmao?useUnicode=true&amp;characterEncoding=UTF-8&amp;autoReconnect=true&amp;autoReconnectForPools=true&amp;zeroDateTimeBehavior=convertToNull");
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUsername("root");
		dataSource.setPassword("$$$xu&&&li");
		dataSource.setValidationQuery("SELECT  1");
		dataSource.setTestOnBorrow(true);
		dataSource.setInitialSize(10);
		dataSource.setMaxActive(100);
		dataSource.setMaxIdle(20);
		dataSource.setMinIdle(5);
		
		System.out.println("processing files:"+fileNames);
		processFiles(fileNames);
		

		
    }
    
    private static void processFiles(List<String> fileNames) {
		for(String filename:fileNames){
			Connection conn=null;
			PreparedStatement pStatement1 = null;
			PreparedStatement pStatement = null;
			try {
					//Date now=new Date();
					conn = dataSource.getConnection();	
					Date lastYear = new Date();
	        		Calendar calendar = Calendar.getInstance();
	        		calendar.setTime(lastYear);		
	        		calendar.add(Calendar.YEAR, -1);
	        		lastYear = calendar.getTime();	
	        		
	        		java.sql.Date date=new java.sql.Date(lastYear.getTime());
	        		Timestamp ts=new Timestamp(lastYear.getTime());
				
		            FileInputStream fis=new FileInputStream(filename);
		            InputStreamReader isr=new InputStreamReader(fis, "UTF-8");
		            BufferedReader br = new BufferedReader(isr);
		            //简写如下
		            //BufferedReader br = new BufferedReader(new InputStreamReader(
		            //        new FileInputStream("E:/phsftp/evdokey/evdokey_201103221556.txt"), "UTF-8"));
		            String line="";
		            String[] arrs=null;
		            int i=0;
		            String email=null;
		            String sql1=null;
		            String sql2=null;
		            ResultSet rs=null;
		            while ((line=br.readLine())!=null) {
		            	i++;
		            	
		                email=line.trim();
		                System.out.println("processing line:"+i+" email:"+email);
		                if(i%100000==0){
		                	System.gc();
		                }
		                		                
		                //验证地址是否正确
		                if(!isNameAdressFormat(email)){
		                	System.out.println("invalid emial address");
		                	continue;
		                }
		                
		                //查看是否该email已经存在
		                sql1="select * from yangmao_email where email=?";
		    			//System.out.println("sql is:"+sql);
		    			pStatement1 = conn.prepareStatement(sql1);
		    			pStatement1.setString(1, email);
		    			rs=pStatement1.executeQuery();
		    			if (rs.next()) {
		    				System.out.println("found existing email");
		    				continue;	    				
						}
		                
		                //insert into tb
		                sql2="insert into yangmao_email(email,last_email_time) values(?,?)";
		                pStatement = conn.prepareStatement(sql2);
		                pStatement.setString(1, email);
		                pStatement.setTimestamp(2, ts);	    
		                //pStatement.setDate(2, date);	                
		    			pStatement.execute();
		                
		            }
		            br.close();
		            isr.close();
		            fis.close();			
			}catch (Exception e) {
	            e.printStackTrace();
	        }finally{
				try {
					if (pStatement != null){
						pStatement.close();}	
					if (pStatement1 != null){
						pStatement1.close();}
					if(conn!=null){
						conn.close();} 
					}catch (SQLException e) {
						System.out.println("fail to close db connection");
					}			
			}
			//;deleteFile(filename);
		}
		
	}

    public static boolean deleteFile(String fileName) {  
    	  File file = new File(fileName);  
    	  // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除  
    	  if (file.exists() && file.isFile()) {  
    	   if (file.delete()) {  
    	    System.out.println("删除单个文件" + fileName + "成功！");  
    	    return true;  
    	   } else {  
    	    System.out.println("删除单个文件" + fileName + "失败！");  
    	    return false;  
    	   }  
    	  } else {  
    	   System.out.println("删除单个文件失败：" + fileName + "不存在！");  
    	   return false;  
    	  }  
    	 }  
    
	private static boolean isNameAdressFormat(String email){  
        boolean isExist = false;  
       
        Pattern p = Pattern.compile(".+@(\\w+.)+[a-z]{2,3}");  
        Matcher m = p.matcher(email);  
        boolean b = m.matches();  
        if(b) {  
            //System.out.println("有效邮件地址");  
            isExist=true;  
        } else {  
            System.out.println("无效邮件地址:"+email);  
        }  
        return isExist;  
    }  
    
}
