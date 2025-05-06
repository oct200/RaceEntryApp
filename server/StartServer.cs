// See https://aka.ms/new-console-template for more information
using System.Configuration;
using System.Net.Sockets;
using System.Reflection;
using networking.jsonprotocol;
using networking;
using persistence;
using services;
using chat.server;
using services;
using log4net;
using log4net.Config;
using Microsoft.Data.Sqlite;

class StartServer
    {
        private static int DEFAULT_PORT=55556;
        private static String DEFAULT_IP="127.0.0.1";
        private static readonly ILog log = LogManager.GetLogger(typeof(StartServer));
        static void Main(string[] args)
        {
            var logRepository = LogManager.GetRepository(Assembly.GetEntryAssembly());
            XmlConfigurator.Configure(logRepository, new FileInfo("log4net.config"));
			
            log.Info("Starting chat server");
            log.Info("Reading properties from app.config ...");
            int port = DEFAULT_PORT;
            String ip = DEFAULT_IP;
            String portS= ConfigurationManager.AppSettings["port"];
            if (portS == null)
            {
                log.Debug("Port property not set. Using default value "+DEFAULT_PORT);
            }
            else
            {
                bool result = Int32.TryParse(portS, out port);
                if (!result)
                {
                    log.Debug("Port property not a number. Using default value "+DEFAULT_PORT);
                    port = DEFAULT_PORT;
                    log.Debug("Portul "+port);
                }
            }
            String ipS=ConfigurationManager.AppSettings["ip"];
           
            if (ipS == null)
            {
                log.Info("Port property not set. Using default value "+DEFAULT_IP);
            }
        string connectionString = GetConnectionStringByName("proiect_mppDB");

        var builder = new SqliteConnectionStringBuilder(connectionString);
        string dbRelativePath = builder.DataSource;
        string fullDbPath = Path.GetFullPath(dbRelativePath);

        log.InfoFormat("Resolved SQLite DB path: {0}", fullDbPath);

        log.InfoFormat("Configuration Settings for database {0}",GetConnectionStringByName("proiect_mppDB"));
            IDictionary<String, string> props = new SortedList<String, String>();
            props.Add("ConnectionString", GetConnectionStringByName("proiect_mppDB"));
            IService serviceImpl = getService(props);

         
            log.DebugFormat("Starting server on IP {0} and port {1}", ip, port);
			//SerialChatServer server = new SerialChatServer(ip,port, serviceImpl);
            JsonChatServer server = new JsonChatServer(ip,port, serviceImpl);
            server.Start();
            log.Debug("Server started ...");
            Console.ReadLine();
        }

        static IService getService(IDictionary<string, string> dbProperties)
        {
            IRepositoryCursa curse = new CursaDBRepository(dbProperties);
            IRepositoryParticipant participanti = new ParticipantDBRepository(dbProperties);
            IRepositoryInscriere inscrieri = new InscriereDBRepository(dbProperties, participanti, curse);
            IRepositoryUser useri = new RepositoryDBUser(dbProperties);

            return new ChatServerImpl(curse, inscrieri, participanti, useri);
        }



    static string GetConnectionStringByName(string name)
        {
            // Assume failure.
            string returnValue = null;

            // Look for the name in the connectionStrings section.
            ConnectionStringSettings settings =ConfigurationManager.ConnectionStrings[name];

            // If found, return the connection string.
            if (settings != null)
                returnValue = settings.ConnectionString;

            return returnValue;
        }
    }

   public class SerialChatServer: ConcurrentServer 
    {
        private IService server;
        private ChatClientJsonWorker worker;
        public SerialChatServer(string host, int port, IService server) : base(host, port)
            {
                this.server = server;
                Console.WriteLine("SerialChatServer...");
        }
        protected override Thread createWorker(TcpClient client)
        {
            worker = new ChatClientJsonWorker(server, client);
            return new Thread(new ThreadStart(worker.run));
        }
    }
    
   public class JsonChatServer: ConcurrentServer 
   {
       private IService server;
       private ChatClientJsonWorker worker;
       private static readonly ILog log = LogManager.GetLogger(typeof(JsonChatServer));
       public JsonChatServer(string host, int port, IService server) : base(host, port)
       {
           this.server = server;
           log.Debug("Creating JsonChatServer...");
       }
       protected override Thread createWorker(TcpClient client)
       {
           worker = new ChatClientJsonWorker(server, client);
           return new Thread(worker.run);
       }
   }
    