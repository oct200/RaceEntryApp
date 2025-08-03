using System;
using System.Collections.Generic;

namespace model.ORMModel;

public partial class Cursa
{
    public int Id { get; set; }

    public int NumarParticipanti { get; set; }

    public int CapMotor { get; set; }

    public virtual ICollection<Participant> Participants { get; set; } = new List<Participant>();
}
