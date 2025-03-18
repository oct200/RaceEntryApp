using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Motociclete.Domain
{
    public class Participant : Entity<long>
    {
        public string nume {  get; set; }
        public int capMotor {  get; set; }
        public Echipa echipa { get; set; }
        public string cnp {  get; set; }

        public Participant(long id, string nume, int capMotor, Echipa echipa, string cnp)
        {
            this.id = id;
            this.nume = nume;
            this.capMotor = capMotor;
            this.echipa = echipa;
            this.cnp = cnp;
        }
    }
}
