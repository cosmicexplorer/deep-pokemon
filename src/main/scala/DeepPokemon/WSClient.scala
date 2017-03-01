package DeepPokemon

import java.net._
import java.io._
import javax.net.ssl._

import org.java_websocket.WebSocketImpl
import org.java_websocket.client._
import org.java_websocket.drafts.Draft_17
import org.java_websocket.handshake.ServerHandshake
import org.json.JSONException

class WSClient(uri: URI) extends WebSocketClient(uri) {
  override def onMessage(message: String): Unit = {
    println(message)
    // JSONObject obj = new JSONObject(message);
    // String channel = obj.getString("channel");
  }

  override def onOpen(handshake: ServerHandshake): Unit = {
    println("opened connection")
  }

  override def onClose(code: Int, reason: String, remote: Boolean): Unit = {
    println(s"closed connection; code: '$code', reason: '$reason', remote: '$remote'")
  }

  override def onError(ex: Exception): Unit = {
    ex.printStackTrace()
  }
}

object WSClient extends App {
  val Array(websocket_address) = args
  println(websocket_address)

  val sock = new WSClient(new URI(websocket_address))

  val sslContext = SSLContext.getInstance("TLS")
  sslContext.init(null, null, null)

  sock.setWebSocketFactory(new DefaultSSLWebSocketClientFactory(sslContext))

  sock.connectBlocking()

  val reader = new BufferedReader(new InputStreamReader(System.in))
  while (true) {
    val line = reader.readLine()
    if (line.equals("close")) {
      sock.close()
    }
  }
}
