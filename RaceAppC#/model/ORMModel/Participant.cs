using System;
using System.Collections.Generic;

namespace model.ORMModel;

public partial class Participant
{
    public int Id { get; set; }

    public string Nume { get; set; } = null!;

    public int CapMotor { get; set; }

    public string? Echipa { get; set; }

    public string Cnp { get; set; } = null!;

    public virtual ICollection<Cursa> Cursas { get; set; } = new List<Cursa>();
}
