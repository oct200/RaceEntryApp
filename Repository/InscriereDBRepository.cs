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
    public class InscriereDBRepository : IRepositoryInscriere
    {
        private static readonly ILog logger = LogManager.GetLogger(typeof(InscriereDBRepository));
        private readonly DBUtils _dbUtils;
        private readonly ParticipantDBRepository _pRepo;
        private readonly EchipaDBRepository _eRepo;

        public InscriereDBRepository(IDictionary<string, string> properties, ParticipantDBRepository pRepo, EchipaDBRepository eRepo)
        {
            _dbUtils = new DBUtils(properties);
            _pRepo = pRepo;
            _eRepo = eRepo;
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
    }

}
