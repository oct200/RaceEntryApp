using Microsoft.Extensions.Logging;
using Motociclete.Domain;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.Data.Sqlite;
using log4net;

namespace Motociclete.Repository
{
    public class CursaDBRepository : IRepositoryCursa
    {
        private static readonly ILog logger = LogManager.GetLogger(typeof(CursaDBRepository));
        private readonly DBUtils _dbUtils;

        public CursaDBRepository(IDictionary<string, string> properties)
        {
            _dbUtils = new DBUtils(properties);
            logger.Info("RepositoryDBUser initialized");
        }

        public long Insert(Cursa entity)
        {
            logger.Info("Inserting new Cursa");

            using (IDbConnection conn = _dbUtils.getConnection())
            {
                try
                {
                    if (conn is SqliteConnection sqliteConn)
                    {
                        string query = "INSERT INTO Curse (numarParticipanti, capMotor) VALUES (@numarParticipanti, @capMotor); SELECT last_insert_rowid();";

                        using (var cmd = new SqliteCommand(query, sqliteConn))
                        {
                            cmd.Parameters.AddWithValue("@numarParticipanti", entity.NumarParticipanti);
                            cmd.Parameters.AddWithValue("@capMotor", entity.CapMotor);

                            long id = (long)cmd.ExecuteScalar();
                            logger.Info($"Inserted new Cursa with id {id}");
                            return id;
                        }
                    }
                }
                catch (Exception ex)
                {
                    logger.Error("Error inserting Cursa: " + ex.Message);
                }
            }
            return -1;
        }


        public void UpdateById(long id, Cursa entity)
        {
            throw new NotImplementedException();
        }

        public void DeleteById(long id)
        {
            throw new NotImplementedException();
        }

        public Cursa GetById(long id)
        {
            logger.Info($"Fetching Cursa with id {id}");

            using (IDbConnection conn = _dbUtils.getConnection())
            {
                try
                {
                    if (conn is SqliteConnection sqliteConn)
                    {
                        string query = "SELECT * FROM Curse WHERE id = @id";

                        using (var cmd = new SqliteCommand(query, sqliteConn))
                        {
                            cmd.Parameters.AddWithValue("@id", id);
                            using (var reader = cmd.ExecuteReader())
                            {
                                if (reader.Read())
                                {
                                    return new Cursa(reader.GetInt64(0), reader.GetInt32(1), reader.GetInt32(2));
                                }
                            }
                        }
                    }
                }
                catch (Exception ex)
                {
                    logger.Error("Error fetching Cursa: " + ex.Message);
                }
            }
            return null;
        }

        public List<Cursa> GetAll()
        {
            logger.Info("Fetching all Curse");

            List<Cursa> curse = new List<Cursa>();

            using (IDbConnection conn = _dbUtils.getConnection())
            {
                try
                {
                    if (conn is SqliteConnection sqliteConn)
                    {
                        string query = "SELECT * FROM Curse";

                        using (var cmd = new SqliteCommand(query, sqliteConn))
                        {
                            using (var reader = cmd.ExecuteReader())
                            {
                                while (reader.Read())
                                {
                                    curse.Add(new Cursa(reader.GetInt64(0), reader.GetInt32(1), reader.GetInt32(2)));
                                }
                            }
                        }
                    }
                }
                catch (Exception ex)
                {
                    logger.Error("Error fetching all Curse: " + ex.Message);
                }
            }
            return curse;
        }

    }

}
