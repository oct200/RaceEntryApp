using log4net;
using log4net.Config;
using Motociclete.Service;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Motociclete
{
    internal static class Program
    {
        private static readonly ILog log = LogManager.GetLogger(typeof(Program));
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            var logRepository = LogManager.GetRepository(Assembly.GetEntryAssembly());
            XmlConfigurator.Configure(logRepository, new FileInfo("log4net.config"));

            log.Info("Hello logging world!");

            ConnectionStringSettings connString = ConfigurationManager.ConnectionStrings["proiect_mppDB"];
            IDictionary<string, string> dbProperties = new SortedList<string, string>();
            dbProperties.Add("ConnectionString", connString.ToString());
            Console.WriteLine(dbProperties["ConnectionString"]);

            Teste.Test t = new Teste.Test(dbProperties);
            t.runTests();

            /*Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new Form1());*/
        }
    }
}
