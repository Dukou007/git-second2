import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/websocket/{user}")  
public class MyServerEndpoint {  
      
    private static final Logger sysLogger = Logger.getLogger("sysLog");

    //把上线的用户都存起来
    private static List<Session> userSessions = new ArrayList<>();
      
    @OnOpen  
    public void open(Session session,  @PathParam(value = "user")String user) {  

        sysLogger.info("open ------ WebSocket opened from sessionId " + session.getId());
        try {

            userSessions.add(session);

			session.getBasicRemote().sendText("已经为你打开websocket连接~~");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }  
      
    @OnMessage  
    public void inMessage(Session session, String message) throws IOException {
        sysLogger.info("received ***** WebSocket Received from sessionId " + session.getId() + ": " + message);

        //给当前用户发送信息
        //session.getBasicRemote().sendText(message);

        //给所有用户发送信息
        for (Session sess : userSessions) {
            if(sess.isOpen()){
                sess.getBasicRemote().sendText(message);
            }
        }
    }  
      
    @OnClose  
    public void end(Session session) {
        sysLogger.info("closed XXXXX WebSocket closed from sessionId " + session.getId());
    }  
      
  
} 
