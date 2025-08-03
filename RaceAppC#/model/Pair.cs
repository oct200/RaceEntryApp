namespace model
{
    public class Pair<T, U>
    {
        public T First { get; }
        public U Second { get; }

        public Pair(T First, U Second)
        {
            this.First = First;
            this.Second = Second;
        }

        public override string ToString()
        {
            return $"({First}, {Second})";
        }
    }

}
