namespace model
{
    public class Participant : Entity<long>
    {
        public string Nume {  get; set; }
        public int CapMotor {  get; set; }
        public string Echipa { get; set; }
        public string Cnp {  get; set; }

        public Participant(long id, string Nume, int CapMotor, string Echipa, string Cnp)
        {
            this.id = id;
            this.Nume = Nume;
            this.CapMotor = CapMotor;
            this.Echipa = Echipa;
            this.Cnp = Cnp;
        }
    }
}
