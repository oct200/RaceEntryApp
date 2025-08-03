namespace model
{
    public class User : Entity<long>
    {
        public string Username {  get; set; }
        public string Parola { get; set; }

        public User(long id, string username, string parola)
        {
            this.id = id;
            this.Username = username;
            this.Parola = parola;
        }
    }
}
