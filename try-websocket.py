import sys

from twisted.python import log
from twisted.internet import reactor, ssl

from autobahn.twisted.websocket import WebSocketClientFactory, \
    WebSocketClientProtocol, \
    connectWS


class EchoClientProtocol(WebSocketClientProtocol):

    def sendHello(self):
        print("HEY")
    # self.sendMessage("/join asdf".encode('utf8'))

    def onOpen(self):
        print("WOW")
        self.sendHello()

    def onMessage(self, payload, isBinary):
        print("Text message received: {}".format(payload.decode('utf8')))
        reactor.callLater(1, self.sendHello)


if __name__ == '__main__':

    log.startLogging(sys.stdout)

    # parser = OptionParser()
    # parser.add_option("-u", "--url", dest="url", help="The WebSocket URL", default="wss://127.0.0.1:9000")
    # (options, args) = parser.parse_args()

    # create a WS server factory with our protocol
    ##
    factory = WebSocketClientFactory('wss://sim.smogon.com/showdown/websocket')
    factory.protocol = EchoClientProtocol

    # SSL client context: default
    ##
    if factory.isSecure:
        contextFactory = ssl.ClientContextFactory()
    else:
        contextFactory = None

    connectWS(factory, contextFactory)
    reactor.run()
