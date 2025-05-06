using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.Data.Sqlite;
using log4net;
using model;

namespace persistence
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

        public List<int> getCapacitati()
        {
            logger.Info("fetching all team names");
            List<int> capacitati = new List<int>();

            using (IDbConnection connection = _dbUtils.getConnection())
            {
                try
                {
                    if (connection is SqliteConnection sqliteConn)
                    {
                        string query = "SELECT DISTINCT capMotor FROM Cursa";

                        using (var cmd = new SqliteCommand(query, sqliteConn))
                        {
                            using (var reader = cmd.ExecuteReader())
                            {
                                while (reader.Read())
                                    capacitati.Add(reader.GetInt32(0));
                            }
                        }
                        return capacitati;
                    }
                }
                catch (Exception e)
                {
                    logger.Error("eroare la getEchipe " + e.Message);
                    throw new Exception("eroare la listarea echipelor");
                }
            }
            return capacitati;
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
                        string query = "INSERT INTO Cursa (numarParticipanti, capMotor) VALUES (@numarParticipanti, @capMotor); SELECT last_insert_rowid();";

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
                        string query = "SELECT * FROM Cursa WHERE id = @id";

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

        public void increaseNr(long id)
        {
            logger.Info("incerasing nr of participanti for " + id);

            using (IDbConnection conn = _dbUtils.getConnection())
            {
                try
                {
                    if (conn is SqliteConnection sqliteConn)
                    {
                        string query = "UPDATE Cursa SET numarParticipanti = numarParticipanti + 1 WHERE id = @id";

                        using (var cmd = new SqliteCommand(query, sqliteConn))
                        {
                            cmd.Parameters.AddWithValue("@id", id);
                            cmd.ExecuteNonQuery();
                        }
                    }
                }
                catch (Exception ex)
                {
                    logger.Error("Error incerasing nr of participanti for " + id + " : " + ex.Message);
                }
            }
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
                        string query = "SELECT * FROM Cursa";

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

        public List<Cursa> getCurseByCap(int cap)
        {
            logger.Info("Fetching all Curse with motor capacity " + cap);

            List<Cursa> curse = new List<Cursa>();

            using (IDbConnection conn = _dbUtils.getConnection())
            {
                try
                {
                    if (conn is SqliteConnection sqliteConn)
                    {
                        string query = "SELECT * FROM Cursa WHERE capMotor = @cap";

                        using (var cmd = new SqliteCommand(query, sqliteConn))
                        {
                            cmd.Parameters.AddWithValue("@cap", cap);
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
