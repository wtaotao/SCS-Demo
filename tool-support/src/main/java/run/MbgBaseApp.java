package run;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import lombok.extern.slf4j.Slf4j;

/**
 * 单表基础代码生成
 * 
 * 生成对象：Mapper、Entity、Example、Xml
 * 
 * @author xyb
 *
 */
@Slf4j
public class MbgBaseApp {
	
	/**
	 * 配置文件
	 */
	private static String configFile="src/main/resources/generatorBaseConfig.xml";
	
	/**
	 * 是否覆盖已经存在的文件
	 */
	private static boolean overwrite = true;
	
	public static void main(String[] args) throws Exception{
		log.info("开始使用 mybatis-generator");
		
		List<String> warnings = new ArrayList<String>();
		
		ConfigurationParser cp = new ConfigurationParser(warnings);
		
		log.info("配置文件信息: [" + configFile + "]");
		
		Configuration config = getConfiguration(configFile,cp);
		
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		myBatisGenerator.generate(null);

		log.info("生成结束.");
	}
    public static Configuration getConfiguration(String filePath,ConfigurationParser cp) throws Exception {
        InputStream in = null;
        try {
        	File file=new File(filePath);
        	if(file.exists()) {
        		in = new FileInputStream(file);
        	}else {
        		in = MbgBaseApp.class.getResourceAsStream(filePath);
        	}
        	if(in==null) {
        		String fileNewPath=MbgBaseApp.class.getResource("/").getPath()
        				+File.separator+filePath;
        		File fileNew=new File(fileNewPath);
            	if(fileNew.exists()) {
            		in = new FileInputStream(fileNew);
            	}else {
            		in = new FileInputStream(new File("").getCanonicalPath()+File.separator+filePath);
            	}
        	}
            return cp.parseConfiguration(in);
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

}
