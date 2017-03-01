package DeepPokemon

import java.net._
import java.io._
import javax.net.ssl._

import org.java_websocket.client._
import org.java_websocket.handshake.ServerHandshake
import scalaj.http._

class WSClient(uri: URI) extends WebSocketClient(uri) {
  val ctx = SSLContext.getInstance("TLS")
  ctx.init(null, null, null)
  setWebSocketFactory(new DefaultSSLWebSocketClientFactory(ctx))

  val parser = new ShowdownParser

  override def onMessage(message: String): Unit = {
    println(s"msg: '$message'")
    println(s"str: ${parser.parse(message)}")
  }

  override def onOpen(handshake: ServerHandshake): Unit = {
    println("opened connection")
  }

  override def onClose(code: Int, reason: String, remote: Boolean): Unit = {
    println(s"closed connection; code: '$code', " +
      s"reason: '$reason', remote: '$remote'")
  }

  override def onError(ex: Exception): Unit = {
    ex.printStackTrace()
  }
}

object WSClient extends App {
  val Array(websocket_address) = args
  println(websocket_address)

  val sock = new WSClient(new URI(websocket_address))

  sock.connectBlocking()

  val reader = new BufferedReader(new InputStreamReader(System.in))
  while (true) {
    val line = reader.readLine()
    if (line.equals("close")) {
      sock.close()
    }
  }
}
