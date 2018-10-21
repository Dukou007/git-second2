> 需求

编写一个迷你tomcat

> 需求分析

tomcat是一个专门处理servlet的web容器。

一般容器处理流程是这样的，客户端发起请求，web容器接受请求，处理请求，然后把得到结果返回给客户端。

所以提取要点如下：
* 请求端口监控
* 把请求的信息封装起来
* 请求处理
* 把响应结果封装起来

因此至少需要以下几个类
* Request 封装请求
* Servlet 请求处理的Servlet
* Response 封装响应结果
* MyTomcat 启动监控和处理服务

以上可以说我们已经设计出了一个超级简单的tomcat，但是这个tomcat有点局限性，只有一个servert，
也就是说，这个tomcat只能处理一个url，为了让服务器能够处理多个servlet，我们必须抽象封装servlet。
请求url与servelt是一一对应关系，所以，我们可以通过一个map把这些关系在启动过程中就初始化加载进来，
因此我们可以封装一个ServletMapping类用于封装映射关系，
然后初始化的话可以放在一个config类中统一管理（真实的tomcat是直接在web.xml配置映射关系）
因此又多了两个类
* ServletMapping 封装url与Servlet的对应关系
* ServletMappingConfig 配置项目的Servlet与Url关系，用于初始化

然后，现在可以通过url找到对应的servlet了，每个Servrt的结构应该一直，因此抽象一个MyServlet类，固定处理的方法是service（）；
后面添加业务处理的时候都要继承这个MyServlet抽象类。比如我要加一个HelloWorldServlet，对应url是"/hello"，
我就需要显示MyServlet抽象类，编写业务逻辑，然后在ServletMappingConfig中配置映射关系。

现在好了，我编写了很多url对应的servelt，如果找不到url，我应该抛出404错误，这个我们先跳过。
当我获取到url对应的servelt名称之后，现在又有一个问题来了，我如何去运行这个servlet的service（）业务代码呢？
同学们可能说直接new HelloWorldServlet（）.service（）不就行了么，这里涉及到一个问题，因为后面业务越来越多，需要的servelt也越来越多，
所以不可能全部通过一个个去判断然后new对象，这时候我们自然想到可以通过反射方式获取servlet实例，然后调用service（）；
所以在tomcat的处理请求阶段，我们可以通过反射方式去调用业务逻辑。

至此。需求分析完毕。

具体代码请看具体项目。

>原作者：张丰哲 https://www.jianshu.com/p/dce1ee01fb90