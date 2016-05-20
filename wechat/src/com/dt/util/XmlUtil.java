package com.dt.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import com.dt.exception.WeixinException;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
/**
 * 
 * 类名称：XmlUtil   
 * 类描述：   xml与javabean转换工具
 * 创建人：luoj  
 * 创建时间：2015年7月17日 下午3:44:17
 */
public class XmlUtil{
    /**
     * java 转换成xml
     * @Title: toXml 
     * @Description: TODO 
     * @param obj 对象实例
     * @return String xml字符串
     */
    public static String toXml(Object obj){
        XStream xstream=new XStream();
//      XStream xstream=new XStream(new DomDriver()); //直接用jaxp dom来解释
//      XStream xstream=new XStream(new DomDriver("utf-8")); //指定编码解析器,直接用jaxp dom来解释
        ////如果没有这句，xml中的根元素会是<包.类名>；或者说：注解根本就没生效，所以的元素名就是类的属性
        xstream.processAnnotations(obj.getClass()); //通过注解方式的，一定要有这句话
        return xstream.toXML(obj);
    }
     
    /**
     *  将传入xml文本转换成Java对象
     * @Title: toBean 
     * @Description: TODO 
     * @param xmlStr
     * @param cls  xml对应的class类
     * @return T   xml对应的class类的实例对象
     * 
     * 调用的方法实例：PersonBean person=XmlUtil.toBean(xmlStr, PersonBean.class);
     */
    public static <T> T  toBean(String xmlStr,Class<T> cls){
        //注意：不是new Xstream(); 否则报错：java.lang.NoClassDefFoundError: org/xmlpull/v1/XmlPullParserFactory
        XStream xstream=new XStream(new DomDriver());
        xstream.processAnnotations(cls);
        T obj=(T)xstream.fromXML(xmlStr);
        return obj;         
    } 

   /**
     * 写到xml文件中去
     * @Title: writeXMLFile 
     * @Description: TODO 
     * @param obj 对象
     * @param absPath 绝对路径
     * @param fileName  文件名
     * @return boolean
     */
   
    public static boolean toXMLFile(Object obj, String absPath, String fileName ){
        String strXml = toXml(obj);
        String filePath = absPath + fileName;
        File file = new File(filePath);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
            	e.printStackTrace();
                return false ;
            }
        }// end if 
        OutputStream ous = null ;
        try {
            ous = new FileOutputStream(file);
            ous.write(strXml.getBytes());
            ous.flush();
        } catch (Exception e1) {
            e1.printStackTrace();
            return false;
        }finally{
            if(ous != null )
                try {
                    ous.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return true ;
    }
     
    /**
     * 从xml文件读取报文
     * @Title: toBeanFromFile 
     * @Description: TODO 
     * @param absPath 绝对路径
     * @param fileName 文件名
     * @param cls
     * @throws Exception 
     * @return T
     */
    public static <T> T  toBeanFromFile(String absPath, String fileName,Class<T> cls) throws Exception{
        String filePath = absPath +fileName;
        InputStream ins = null ;
        try {
            ins = new FileInputStream(new File(filePath ));
        } catch (Exception e) {
            throw new Exception("读{"+ filePath +"}文件失败！", e);
        }
         
        XStream xstream=new XStream(new DomDriver("utf-8"));
        xstream.processAnnotations(cls);
        T obj =null;
        try {
            obj = (T)xstream.fromXML(ins);
        } catch (Exception e) {
            throw new WeixinException("解析{"+ filePath +"}文件失败！");
        }
        if(ins != null)
            ins.close();
        return obj;         
    } 
     
}
