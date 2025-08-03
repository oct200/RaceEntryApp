using log4net;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.Data.Sqlite;
using model;

namespace persistence
{
    public class InscriereDBRepository : IRepositoryInscriere
    {
        private static readonly ILog logger = LogManager.GetLogger(typeof(InscriereDBRepository));
        private readonly DBUtils _dbUtils;
        private readonly IRepositoryParticipant _pRepo;
        private readonly IRepositoryCursa _cRepo;

        public InscriereDBRepository(IDictionary<string, string> properties, IRepositoryParticipant pRepo, IRepositoryCursa cRepo)
        {
            _dbUtils = new DBUtils(properties);
            _pRepo = pRepo;
            _cRepo = cRepo;
            logger.Info("InscriereDBRepository initialized");
        }

        public Pair<long, long> Insert(Inscriere entity)
        {
            logger.Info("Inserting new Inscriere");

            using (IDbConnection conn = _dbUtils.getConnection())
            {
                try
                {
                    if (conn is SqliteConnection sqliteConn)
                    {
                        string query = "INSERT INTO Inscriere (cursa_id, participant_id) VALUES (@cursa_id, @participant_id)";

                        using (var cmd = new SqliteCommand(query, sqliteConn))
                        {
                            cmd.Parameters.AddWithValue("@cursa_id", entity.Cursa.id);
                            cmd.Parameters.AddWithValue("@participant_id", entity.Participant.id);

                            cmd.ExecuteNonQuery();
                            logger.Info("Inserted new Inscriere successfully");
                            return entity.id;
                        }
                    }
                }
                catch (Exception ex)
                {
                    logger.Error("Error inserting Inscriere: " + ex.Message);
                    throw new Exception(ex.Message);
                }
            }
            return null;
        }

        public void UpdateById(Pair<long, long> id, Inscriere entity)
        {
            // Not implemented
        }

        public void DeleteById(Pair<long, long> id)
        {
            // Not implemented
        }

        public Inscriere GetById(Pair<long, long> id)
        {
            // Not implemented
            return null;
        }

        public List<Inscriere> GetAll()
        {
            // Not implemented
            return new List<Inscriere>();
        }

        public List<Participant> GetParticipantiInscrisiByCursaId(long id)
        {
            logger.Info("Fetching Participants for CursaId: " + id);
            List<Participant> participants = new List<Participant>();

            using (IDbConnection conn = _dbUtils.getConnection())
            {
                try
                {
                    if (conn is SqliteConnection sqliteConn)
                    {
                        string query = "SELECT * FROM Inscriere WHERE cursa_id = @cursa_id";

                        using (var cmd = new SqliteCommand(query, sqliteConn))
                        {
                            cmd.Parameters.AddWithValue("@cursa_id", id);
                            using (var reader = cmd.ExecuteReader())
                            {
                                while (reader.Read())
                                {
                                    participants.Add(_pRepo.GetById(reader.GetInt64(reader.GetOrdinal("participant_id"))));
                                }
                            }
                        }
                        logger.Info("Participants fetched successfully");
                    }
                }
                catch (Exception ex)
                {
                    logger.Error("Error fetching Participants: " + ex.Message);
                }
            }
            return participants;
        }

        public List<Cursa> getCursePosibilePentruParticipant(Participant participant)
        {
            logger.Info("getCursePosibilePentruParticipant  " + participant.id);

            List<Cursa> curse = new List<Cursa>();

            using (IDbConnection conn = _dbUtils.getConnection())
            {
                try
                {
                    if (conn is SqliteConnection sqliteConn)
                    {
                        string query = "SELECT C.id, C.numarParticipanti, C.capMotor FROM Cursa C WHERE C.capMotor = @capMotor AND C.id NOT IN (SELECT cursa_id FROM Inscriere WHERE participant_id = @pid) ";

                        using (var cmd = new SqliteCommand(query, sqliteConn))
                        {
                            cmd.Parameters.AddWithValue("@capMotor", participant.CapMotor);
                            cmd.Parameters.AddWithValue("@pid", participant.id);
                            using (var reader = cmd.ExecuteReader())
                            {
                                while (reader.Read())
                                {
                                    curse.Add(new Cursa(reader.GetInt64(0),reader.GetInt32(1),reader.GetInt32(2)));
                                }
                            }
                        }
                        logger.Info("Participants fetched successfully");
                    }
                }
                catch (Exception ex)
                {
                    logger.Error("Error fetching getCursePosibilePentruParticipant  " + participant.id + " : " + ex.Message);
                }
            }
            return curse;

        }
    }

}
