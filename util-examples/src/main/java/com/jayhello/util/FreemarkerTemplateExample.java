package com.jayhello.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Freemarker模板引擎示例
 */
public class FreemarkerTemplateExample {
    
    private Configuration cfg;
    
    public FreemarkerTemplateExample() {
        cfg = new Configuration(Configuration.VERSION_2_3_32);
        cfg.setClassForTemplateLoading(this.getClass(), "/templates");
        cfg.setDefaultEncoding("UTF-8");
    }
    
    /**
     * 使用字符串模板生成内容
     */
    public String generateFromString() throws IOException, TemplateException {
        String templateStr = "你好，${name}！欢迎来到${site}。";
        
        Template template = new Template("stringTemplate", templateStr, cfg);
        
        Map<String, Object> data = new HashMap<>();
        data.put("name", "张三");
        data.put("site", "Java每日实验");
        
        StringWriter writer = new StringWriter();
        template.process(data, writer);
        
        return writer.toString();
    }
    
    public static void main(String[] args) {
        FreemarkerTemplateExample example = new FreemarkerTemplateExample();
        
        try {
            String result = example.generateFromString();
            System.out.println("生成的内容: " + result);
        } catch (IOException | TemplateException e) {
            System.err.println("模板处理失败: " + e.getMessage());
        }
    }
}
