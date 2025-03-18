using log4net;
using Motociclete.Domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.Data.Sqlite;
using System.Data.SQLite;
using System.Data;

namespace Motociclete.Repository
{
    public class RepositoryDBUser : IRepositoryUser
    {
        private static readonly ILog logger = LogManager.GetLogger(typeof(RepositoryDBUser));
        private readonly DBUtils _dbUtils;
        public RepositoryDBUser(IDictionary<string, string> properties)
        {
            _dbUtils = new DBUtils(properties);
            logger.Info("RepositoryDBUser initialized");
        }

        public void DeleteById(long id)
        {
            logger.Info("Deleting new user");

            using (IDbConnection conn = _dbUtils.getConnection())
            {
                try
                {
                    if (conn is SqliteConnection sqliteConn)
                    {
                        string query = "DELETE FROM User WHERE id = @id";

                        using (var cmd = new SqliteCommand(query, sqliteConn))
                        {
                            cmd.Parameters.AddWithValue("@id", id);

                            cmd.ExecuteNonQuery();
                            logger.Info("inserare user realizata cu succes");
                            return;
                        }
                    }
                }
                catch (Exception ex)
                {
                    logger.Error("eroare la delete user " + ex.Message);
                }
            }
        }
    

        public List<User> GetAll()
        {
            throw new NotImplementedException();
        }

        public User GetById(long id)
        {
            throw new NotImplementedException();
        }

        public User getUserByUsername(string username)
        {
            throw new NotImplementedException();
        }

        public long Insert(User user)
        {
            logger.Info("Inserting new user");

            using (IDbConnection conn = _dbUtils.getConnection())
            {
                try
                {
                    if (conn is SqliteConnection sqliteConn)
                    {
                        string query = "INSERT INTO User(username, parola) VALUES (@username, @parola)";

                        using (var cmd = new SqliteCommand(query, sqliteConn))
                        {
                            cmd.Parameters.AddWithValue("@username", user.username);
                            cmd.Parameters.AddWithValue("@parola", user.parola);

                            // Execute the insert command
                            long id = (long)cmd.ExecuteScalar();
                            logger.Info("inserare user realizata cu succes");
                            return id;
                        }
                    }
                }
                catch (Exception ex)
                {
                    logger.Error("eroare la insert user " + ex.Message); 
                }
            }
            return -1;
        }

        public void UpdateById(long id, User entity)
        {
            throw new NotImplementedException();
        }
    }
}
