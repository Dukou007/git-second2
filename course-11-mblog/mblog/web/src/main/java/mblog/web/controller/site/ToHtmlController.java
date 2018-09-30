package mblog.web.controller.site;

import freemarker.template.Configuration;
import freemarker.template.Template;
import mblog.base.lang.Consts;
import mblog.modules.blog.data.PostVO;
import mblog.modules.blog.service.ChannelService;
import mblog.modules.blog.service.PostService;
import mblog.util.FreemarkerUtil;
import mblog.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ToHtmlController extends BaseController {

    @Autowired
    private PostService postService;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private Configuration configuration;

    @ResponseBody
    @RequestMapping("/view/tohtml/{id}")
    public boolean toHtml(@PathVariable Long id, HttpServletRequest req) throws IOException {
        Template template = configuration.getTemplate("/default/channel/view.ftl");

        String fileName = id + ".html";
        String htmlDir = "D:/git-second/course-11-mblog/mblog/web/src/main/resources/static/html";

        PostVO view = postService.get(id);
        Map<String, Object> params = new HashMap<>();
        params.put("view", view);
        params.put("channels", channelService.findAll(Consts.IGNORE));
        return FreemarkerUtil.printToFile(template, htmlDir, fileName, params);
    }
}
