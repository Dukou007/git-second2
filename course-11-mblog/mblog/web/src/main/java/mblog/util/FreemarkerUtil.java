package mblog.util;

import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.Map;  
  
/** 
 * 创建人： leon 
 * 创建时间： 2014年11月28日 上午10:07:51 
 * 类描述：Freemarker的工具类 
 */  
public class FreemarkerUtil {
      
    /** 
     * 通过指定的文件目录和文件名生成相应的文件 
     * @param fileDir 
     * @param fileName 
     */  
    public static Boolean printToFile(Template template,String fileDir,String fileName,Map<String,Object> params) {
        boolean done = false;  
        Writer writer = null;  
        try {  
            //判断多级目录是否存在，不存在则一级级创建  
            String[] paths = fileDir.split("\\\\");//注意：此处“\\”是错误的，必须要“\\\\”才能分割字符串  
            String dir = paths[0];  
            for (int i = 1; i < paths.length; i++) {  
                dir = dir + File.separator + paths[i];  
                File file=new File(dir.toString());  
                if (!file.exists()) {  
                    file.mkdir();  
                }  
            }  
            //创建输出流  
            File file = new File(fileDir +File.separator+ fileName);    
                 
            //设置生成的文件编码为UTF-8     
            //服务器不支持UTF-8格式HTML时候使用ANSI格式HTML文件，即系统默认编码     
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8"));  // 编码设置3  
            //writer = new FileWriter(fileDir +File.separator+ fileName);  
            //输出模板和数据模型都对应的文件  
            template.process(params, writer);
            done = true;  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (TemplateException e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if(writer!=null){  
                    writer.close();  
                }  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return done;  
    }
}  