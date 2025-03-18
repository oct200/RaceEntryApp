using log4net;
using Motociclete.Domain;
using Motociclete.Repository;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.Data.Sqlite;

namespace Motociclete.Teste
{
    public class Test
    {
        private static readonly ILog Log = LogManager.GetLogger(typeof(RepositoryDBUser));
        IDictionary<string, string> properties;
        public Test(IDictionary<string, string> properties)
        {
            this.properties = properties;
        }

        public void runTests()
        {
            Log.Info("incepe init repo");
            RepositoryDBUser ru = new RepositoryDBUser(properties);
            Log.Info("s-a facut repo");
            User user1 = new User(100,"userTest", "parola");
            long id = ru.Insert(user1);
            ru.DeleteById(id);
        }
        public bool CanConnectToDatabase(string connectionString)
        {
                // Create a connection to the database
                using (var connection = new SqliteConnection(connectionString))
                {
                    // Open the connection
                    connection.Open();

                    // If no exception is thrown, the connection is successful
                    Console.WriteLine("Successfully connected to the database!");
                    return true;
                }
           
        }

    }
}
