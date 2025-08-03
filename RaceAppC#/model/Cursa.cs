namespace model
{
    public class Cursa : Entity<long>
    {
        public int NumarParticipanti {  get; set; }
        public int CapMotor {  get; set; }

        public Cursa(long id, int NumarParticipanti, int CapMotor) {
            this.id = id;
            this.NumarParticipanti = NumarParticipanti;
            this.CapMotor = CapMotor;
        }
    }
}
