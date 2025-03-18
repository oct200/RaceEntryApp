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
    public class ParticipantDBRepository : IRepositoryParticipant
    {
        private static readonly ILog logger = LogManager.GetLogger(typeof(ParticipantDBRepository));
        private readonly DBUtils _dbUtils;
        private readonly EchipaDBRepository _echipaDBRepository;

        public ParticipantDBRepository(IDictionary<string, string> properties, EchipaDBRepository echipaDBRepository)
        {
            _dbUtils = new DBUtils(properties);
            _echipaDBRepository = echipaDBRepository;
            logger.Info("ParticipantDBRepository initialized");
        }

        public long Insert(Participant entity)
        {
            logger.Info("Inserting new Participant");

            using (IDbConnection conn = _dbUtils.getConnection())
            {
                try
                {
                    if (conn is SqliteConnection sqliteConn)
                    {
                        string query = "INSERT INTO Participant (nume, capMotor, echipa, cnp) VALUES (@nume, @capMotor, @echipa, @cnp); SELECT last_insert_rowid();";

                        using (var cmd = new SqliteCommand(query, sqliteConn))
                        {
                            cmd.Parameters.AddWithValue("@nume", entity.Nume);
                            cmd.Parameters.AddWithValue("@capMotor", entity.CapMotor);
                            cmd.Parameters.AddWithValue("@echipa", entity.Echipa.id);
                            cmd.Parameters.AddWithValue("@cnp", entity.Cnp);

                            long id = (long)cmd.ExecuteScalar();
                            logger.Info($"Inserted new Participant with id {id}");
                            return id;
                        }
                    }
                }
                catch (Exception ex)
                {
                    logger.Error("Error inserting Participant: " + ex.Message);
                }
            }
            return -1;
        }

        public void UpdateById(long id, Participant entity)
        {
            throw new NotImplementedException();
        }

        public void DeleteById(long id)
        {
            logger.Info($"Deleting Participant with id {id}");

            using (IDbConnection conn = _dbUtils.getConnection())
            {
                try
                {
                    if (conn is SqliteConnection sqliteConn)
                    {
                        string query = "DELETE FROM Participant WHERE id = @id";

                        using (var cmd = new SqliteCommand(query, sqliteConn))
                        {
                            cmd.Parameters.AddWithValue("@id", id);
                            cmd.ExecuteNonQuery();
                            logger.Info("Participant deleted successfully");
                        }
                    }
                }
                catch (Exception ex)
                {
                    logger.Error("Error deleting Participant: " + ex.Message);
                }
            }
        }

        public Participant GetById(long id)
        {
            logger.Info($"Fetching Participant with id {id}");

            using (IDbConnection conn = _dbUtils.getConnection())
            {
                try
                {
                    if (conn is SqliteConnection sqliteConn)
                    {
                        string query = "SELECT * FROM Participant WHERE id = @id";

                        using (var cmd = new SqliteCommand(query, sqliteConn))
                        {
                            cmd.Parameters.AddWithValue("@id", id);
                            using (var reader = cmd.ExecuteReader())
                            {
                                if (reader.Read())
                                {
                                    var echipa = _echipaDBRepository.GetById(reader.GetInt64(3));
                                    return new Participant(reader.GetInt64(0), reader.GetString(1), reader.GetInt32(2), echipa, reader.GetString(4));
                                }
                            }
                        }
                    }
                }
                catch (Exception ex)
                {
                    logger.Error("Error fetching Participant: " + ex.Message);
                }
            }
            return null;
        }

        public List<Participant> GetAll()
        {
            logger.Info("Fetching all Participants");

            List<Participant> participants = new List<Participant>();

            using (IDbConnection conn = _dbUtils.getConnection())
            {
                try
                {
                    if (conn is SqliteConnection sqliteConn)
                    {
                        string query = "SELECT * FROM Participant";

                        using (var cmd = new SqliteCommand(query, sqliteConn))
                        {
                            using (var reader = cmd.ExecuteReader())
                            {
                                while (reader.Read())
                                {
                                    var echipa = _echipaDBRepository.GetById(reader.GetInt64(3));
                                    participants.Add(new Participant(reader.GetInt64(0), reader.GetString(1), reader.GetInt32(2), echipa, reader.GetString(4)));
                                }
                            }
                        }
                    }
                }
                catch (Exception ex)
                {
                    logger.Error("Error fetching all Participants: " + ex.Message);
                }
            }
            return participants;
        }
    }

}
