using System;
using System.Collections.Generic;

namespace model.ORMModel;

public partial class User
{
    public int Id { get; set; }

    public string Username { get; set; } = null!;

    public string Parola { get; set; } = null!;
}
