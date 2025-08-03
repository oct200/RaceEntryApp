namespace model
{
    public class Inscriere : Entity<Pair<long, long>>
    {
        public Participant Participant {  get; set; }
        public Cursa Cursa { get; set; }

        public Inscriere(Participant Participant, Cursa Cursa)
        {
            this.id = new Pair<long, long>(Participant.id, Cursa.id);
            this.Participant = Participant;
            this.Cursa = Cursa;
        }
    }
}
