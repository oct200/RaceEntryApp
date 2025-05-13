
using model.ORMModel;

namespace networking.jsonprotocol;

public class Response
{
    public ResponseType Type { get; set; }
    public string ErrorMessage { get; set; }
    public User User { get; set; }
    public List<string> Echipe { get; set; }
    public List<int> CapacitatiMotor { get; set; }
    public List<Participant> Participanti { get; set; }
    public List<Cursa> Curse { get; set; }
    public Response()
    {
    }
}