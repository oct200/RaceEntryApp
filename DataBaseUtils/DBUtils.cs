using log4net;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.Data.Sqlite;
using Motociclete.DataBaseUtils;

namespace Motociclete.Repository
{
    public class DBUtils
    {
        private static readonly ILog Log = LogManager.GetLogger(typeof(DBUtils));
        IDictionary<string, string> dbProperties;
        private static IDbConnection _instance;

        public DBUtils(IDictionary<string, string> dbProperties){
            this.dbProperties = dbProperties;
        }

        private IDbConnection getNewConnection()
        {
            Log.Info("Opening connection");
            IDbConnection connection = null;
            try
            {
                connection = ConnectionFactory.getInstance().createConnection(dbProperties);
                if (connection == null)
                {
                    throw new Exception("connection is null");
                }

            }
            catch (Exception ex)
            {
                Log.Error(ex);
            }

            Log.Info("Connection opened");
            return connection;
        }

        public IDbConnection getConnection()
        {
            Log.Info("Accessing connection");
            try
            {
                if (_instance == null || _instance.State == ConnectionState.Closed)
                {
                    _instance = getNewConnection();
                    _instance.Open();
                }
            }
            catch (Exception ex)
            {
                Log.Error(ex);
            }

            Log.Info("Connection accessed");
            return _instance;
        }
    }
}