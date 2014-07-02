package cn.dreampie.common.plugin.mail;

import com.jfinal.kit.PathKit;
import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by wangrenhui on 2014/7/2.
 */
public class MailerTemplate {
    private static Logger logger = LoggerFactory.getLogger(MailerTemplate.class);

    private static MailerTemplate mailerTemplate = new MailerTemplate();
    /**
     * 邮件模板的存放位置
     */
    private static final String TEMPLATE_PATH = "template";
    /**
     * 模板引擎配置
     */
    private static Configuration configuration;
    /**
     * 参数
     */
    private static Map<Object, Object> parameters;

    static {
        configuration = new Configuration();
//        ClassTemplateLoader ctl= new ClassTemplateLoader(MailerTemplate.class, TEMPLATE_PATH);
        try {
            configuration.setTemplateLoader(new FileTemplateLoader(new File(PathKit.getWebRootPath() + File.separator + TEMPLATE_PATH + File.separator)));
            logger.info("template dir:" + PathKit.getWebRootPath() + File.separator + TEMPLATE_PATH + File.separator);
        } catch (IOException e) {
            e.printStackTrace();
        }
        configuration.setEncoding(Locale.getDefault(), "UTF-8");
        configuration.setDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public static MailerTemplate me() {
        //初始化参数
        parameters = new HashMap<Object, Object>();
        return mailerTemplate;
    }

    public MailerTemplate set(String attr, Object value) {
        parameters.put(attr, value);
        return this;
    }

    public String getText(String templateFile) {
        try {
            Template template = configuration.getTemplate(templateFile);
            StringWriter stringWriter = new StringWriter();
            template.process(parameters, stringWriter);
            return stringWriter.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
