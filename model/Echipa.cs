namespace model
{
    public class Echipa : Entity<long>
    {
        public string Nume {  get; set; }
        public Echipa(long id, string Nume) {
            this.id = id;
            this.Nume = Nume;
        }
    }
}
