using System;

namespace Parser
{
    class Program
    {
        static void Main(string[] args)
        {
            if (args.Length < 1)
            {
                Console.WriteLine("Missed file nam.");
            }
            string filename = args[0];
            FileParser parser = new FileParser();
            parser.Parse(filename);

        }
    }

}
