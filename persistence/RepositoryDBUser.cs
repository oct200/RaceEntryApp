using log4net;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.Data.Sqlite;
using System.Data;
using log4net.Repository.Hierarchy;
using model;

namespace persistence
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
            logger.Info("Deleting user");

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
                            logger.Info("Delete user realizat cu succes");
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
            logger.Info("Retrieving user with username " + username);
            User user = null;
            using (IDbConnection conn = _dbUtils.getConnection())
            {
                try
                {
                    if (conn is SqliteConnection sqliteConn)
                    {
                        string query = "SELECT * FROM User WHERE username = @username";

                        using (var cmd = new SqliteCommand(query, sqliteConn))
                        {
                            cmd.Parameters.AddWithValue("@username", username);
                            using (var reader = cmd.ExecuteReader())
                            {
                                if (reader.Read())
                                {
                                    user = new User((long)reader.GetInt64(0), reader.GetString(1), reader.GetString(2));
                                }
                            }
                        }
                    }
                }
                catch (Exception ex)
                {
                    logger.Error("Eroare la getUserByUsername " + username + " : " + ex.Message);
                }
            }
            return user;
        }

        public User getUserByUsernameAndPassword(string username,string password)
        {
            logger.Info("Retrieving user with username " + username);
            User user = null;
            using (IDbConnection conn = _dbUtils.getConnection())
            {
                try
                {
                    if(conn is SqliteConnection sqliteConn)
                    {
                        string query = "SELECT * FROM User WHERE username = @username AND parola = @parola";

                        using(var cmd = new SqliteCommand(query, sqliteConn))
                        {
                            cmd.Parameters.AddWithValue("@username", username);
                            cmd.Parameters.AddWithValue ("@parola", password);
                            using (var reader = cmd.ExecuteReader())
                            {
                                if (reader.Read())
                                {
                                    user = new User((long)reader.GetInt64(0), reader.GetString(1), reader.GetString(2));
                                }
                            }
                        }
                    }
                }
                catch(Exception ex)
                {
                    logger.Error("Eroare la getUserByUsername " +  username + " : " + ex.Message);
                }
            }
            return user;
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
                        string query = "INSERT INTO User(username, parola) VALUES (@username, @parola) RETURNING id";

                        using (var cmd = new SqliteCommand(query, sqliteConn))
                        {
                            cmd.Parameters.AddWithValue("@username", user.Username);
                            cmd.Parameters.AddWithValue("@parola", user.Parola);

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
                    throw new Exception("eroare la crearea contului");
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
