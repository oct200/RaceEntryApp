using log4net;
using Motociclete.Domain;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.Data.Sqlite;

namespace Motociclete.Repository
{
    public class EchipaDBRepository : IRepositoryEchipa
    {
        private static readonly ILog logger = LogManager.GetLogger(typeof(EchipaDBRepository));
        private readonly DBUtils _dbUtils;

        public EchipaDBRepository(IDictionary<string, string> properties)
        {
            _dbUtils = new DBUtils(properties);
            logger.Info("EchipaDBRepository initialized");
        }

        public long Insert(Echipa entity)
        {
            logger.Info("Inserting new Echipa");

            using (IDbConnection conn = _dbUtils.getConnection())
            {
                try
                {
                    if (conn is SqliteConnection sqliteConn)
                    {
                        string query = "INSERT INTO Echipa (nume) VALUES (@nume); SELECT last_insert_rowid();";

                        using (var cmd = new SqliteCommand(query, sqliteConn))
                        {
                            cmd.Parameters.AddWithValue("@nume", entity.Nume);

                            long id = (long)cmd.ExecuteScalar();
                            logger.Info($"Inserted new Echipa with id {id}");
                            return id;
                        }
                    }
                }
                catch (Exception ex)
                {
                    logger.Error("Error inserting Echipa: " + ex.Message);
                }
            }
            return -1;
        }

        public void UpdateById(long id, Echipa entity)
        {
            throw new NotImplementedException();
        }

        public void DeleteById(long id)
        {
            throw new NotImplementedException();
        }

        public Echipa GetById(long id)
        {
            logger.Info($"Fetching Echipa with id {id}");

            using (IDbConnection conn = _dbUtils.getConnection())
            {
                try
                {
                    if (conn is SqliteConnection sqliteConn)
                    {
                        string query = "SELECT * FROM Echipa WHERE id = @id";

                        using (var cmd = new SqliteCommand(query, sqliteConn))
                        {
                            cmd.Parameters.AddWithValue("@id", id);
                            using (var reader = cmd.ExecuteReader())
                            {
                                if (reader.Read())
                                {
                                    return new Echipa(reader.GetInt64(0), reader.GetString(1));
                                }
                            }
                        }
                    }
                }
                catch (Exception ex)
                {
                    logger.Error("Error fetching Echipa: " + ex.Message);
                }
            }
            return null;
        }

        public List<Echipa> GetAll()
        {
            logger.Info("Fetching all Echipe");

            List<Echipa> echipe = new List<Echipa>();

            using (IDbConnection conn = _dbUtils.getConnection())
            {
                try
                {
                    if (conn is SqliteConnection sqliteConn)
                    {
                        string query = "SELECT * FROM Echipa";

                        using (var cmd = new SqliteCommand(query, sqliteConn))
                        {
                            using (var reader = cmd.ExecuteReader())
                            {
                                while (reader.Read())
                                {
                                    echipe.Add(new Echipa(reader.GetInt64(0), reader.GetString(1)));
                                }
                            }
                        }
                    }
                }
                catch (Exception ex)
                {
                    logger.Error("Error fetching all Echipe: " + ex.Message);
                }
            }
            return echipe;
        }
    }

}
