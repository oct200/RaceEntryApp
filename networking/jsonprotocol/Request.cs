using model.ORMModel;

namespace networking.jsonprotocol;

public class Request
{
    public RequestType Type { get; set; }
    public User User { get; set; }
    public User[] ListaUseri { get; set; }
    public string Echipa { get; set; }
    public int CapMotor { get; set; }
    public Participant Participant { get; set; }
    public Cursa Cursa { get; set; }

    public Request() { }


}