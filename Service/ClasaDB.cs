using Microsoft.Data.Sqlite;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.SQLite;
using System.IO;


namespace Motociclete.Service
{
    public class ClasaDB
    {
        public static void createDataBase()
        {
            string dbFilePath = "mydatabase.db"; // SQLite database file path
            string connectionString = $"Data Source={dbFilePath};Version=3;";

            // Establish SQLite connection
            using (SQLiteConnection connection = new SQLiteConnection(connectionString))
            {
                connection.Open();
                Console.WriteLine("Connected to SQLite database!");

                // SQL script to create tables
                string createTablesQuery = @"
                CREATE TABLE IF NOT EXISTS User (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    username TEXT NOT NULL UNIQUE,
                    parola TEXT NOT NULL
                );

                CREATE TABLE IF NOT EXISTS Echipa (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nume TEXT NOT NULL UNIQUE
                );

                CREATE TABLE IF NOT EXISTS Cursa (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    numarParticipanti INTEGER NOT NULL,
                    capMotor INTEGER NOT NULL
                );

                CREATE TABLE IF NOT EXISTS Participant (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nume TEXT NOT NULL,
                    capMotor INTEGER NOT NULL,
                    echipa_id INTEGER,
                    cnp TEXT NOT NULL UNIQUE,
                    FOREIGN KEY (echipa_id) REFERENCES Echipa(id) ON DELETE SET NULL
                );

                CREATE TABLE IF NOT EXISTS Inscriere (
                    cursa_id INTEGER NOT NULL,
                    participant_id INTEGER NOT NULL,
                    PRIMARY KEY (cursa_id, participant_id),
                    FOREIGN KEY (cursa_id) REFERENCES Cursa(id) ON DELETE CASCADE,
                    FOREIGN KEY (participant_id) REFERENCES Participant(id) ON DELETE CASCADE
                );
            ";

                // Execute the SQL script to create tables
                using (SQLiteCommand command = new SQLiteCommand(createTablesQuery, connection))
                {
                    command.ExecuteNonQuery();
                    Console.WriteLine("Tables created successfully!");
                }
                Console.WriteLine(connectionString);
                connection.Close();
            }
        }
    }
}
