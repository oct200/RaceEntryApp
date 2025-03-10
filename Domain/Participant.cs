using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Motociclete.Domain
{
    public class Participant : Entity<long>
    {
        string nume {  get; set; }
        int capMotor {  get; set; }
        Echipa echipa { get; set; }
        string cnp {  get; set; }

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
