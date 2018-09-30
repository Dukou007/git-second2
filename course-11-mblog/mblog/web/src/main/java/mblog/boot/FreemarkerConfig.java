package mblog.boot;

import freemarker.template.Configuration;
import freemarker.template.TemplateModelException;
import mblog.shiro.tags.ShiroTags;
import mblog.template.directive.*;
import mblog.template.method.TimeAgoMethod;
import mblog.web.menu.MenusDirective;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by langhsu on 2017/11/14.
 */
@Component
public class FreemarkerConfig {

    /**
     * Configuration 是一个存放应用级别（application level）公共配置信息，
     * 以及模版（Template）可使用的全局共享变量的一个对象。
     * 同时它还负责模版（Template）实例的创建以及缓存
     */
    @Autowired
    private Configuration configuration;
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 共享变量是那些定义给所有模版（Template）使用的变量。
     * 可以通过configuration对象的setSharedVariable 方法来添加共享变量。
     * @throws TemplateModelException
     */
    @PostConstruct
    public void setSharedVariable() throws TemplateModelException {
        configuration.setSharedVariable("author_contents", applicationContext.getBean(AuthorContentsDirective.class));
        configuration.setSharedVariable("channel", applicationContext.getBean(ChannelDirective.class));
        configuration.setSharedVariable("contents", applicationContext.getBean(ContentsDirective.class));
        configuration.setSharedVariable("num", applicationContext.getBean(NumberDirective.class));
        configuration.setSharedVariable("resource", applicationContext.getBean(ResourceDirective.class));
        configuration.setSharedVariable("menus", applicationContext.getBean(MenusDirective.class));
        configuration.setSharedVariable("banner", applicationContext.getBean(BannerDirective.class));

        configuration.setSharedVariable("timeAgo", new TimeAgoMethod());
        configuration.setSharedVariable("shiro", new ShiroTags());
    }
}
