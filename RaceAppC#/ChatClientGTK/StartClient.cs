using System.Configuration;
using System.Reflection;
using chat.networking.jsonprotocol;
using services;
using log4net;
using log4net.Config;
using persistence;
using networking;
using networking.jsonprotocol;

namespace ChatClientGTK;

internal class StartClient
    
{
    private static int DEFAULT_PORT=55556;
    private static String DEFAULT_IP="127.0.0.1";
    private static readonly ILog log = LogManager.GetLogger(typeof(StartClient));
    public static void Main(string[] args)
    {
        
        //configurare jurnalizare folosind log4net
        var logRepository = LogManager.GetRepository(Assembly.GetEntryAssembly());
        XmlConfigurator.Configure(logRepository, new FileInfo("log4net.config"));
        log.Debug("Reading properties from app.config ...");
        int port = DEFAULT_PORT;
        String ip = DEFAULT_IP;
        String portS= ConfigurationManager.AppSettings["port"];
        if (portS == null)
        {
            log.DebugFormat("Port property not set. Using default value {0}",DEFAULT_PORT);
        }
        else
        {
            bool result = Int32.TryParse(portS, out port);
            if (!result)
            {
                log.DebugFormat("Port property not a number. Using default value {0}",DEFAULT_PORT);
                port = DEFAULT_PORT;
                log.DebugFormat("Portul {0}",port);
            }
        }
        String ipS=ConfigurationManager.AppSettings["ip"];
           
        if (ipS == null)
        {
            log.DebugFormat("Port property not set. Using default value {0}",DEFAULT_IP);
        }
            
        log.InfoFormat("Using  server on IP {0} and port {1}", ip, port);
        startApp(ip,port);
                        
    }

    static void startApp(string host, int port)
    {
        ChatServerJsonProxy service = new ChatServerJsonProxy(host,port);

        FormAutentificare fa = new FormAutentificare(service);
        fa.ShowDialog();
    }
}