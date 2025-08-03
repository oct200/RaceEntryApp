using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CursaRestClient;
class CursaRestClient
{
    private static readonly string BaseUrl = "http://localhost:8080/motociclete/curse";
    private static readonly HttpClient client = new HttpClient();

    static async Task Main()
    {
        while (true)
        {
            Console.WriteLine("\nChoose an option:\n1. Insert\n2. Update\n3. Delete\n4. List all\n5. Exit");
            string choice = Console.ReadLine();

            switch (choice)
            {
                case "1":
                    await Insert();
                    break;
                case "2":
                    await Update();
                    break;
                case "3":
                    await Delete();
                    break;
                case "4":
                    await ListAll();
                    break;
                case "5":
                    Console.WriteLine("Exiting...");
                    return;
                default:
                    Console.WriteLine("Invalid choice.");
                    break;
            }
        }
    }

    private static async Task Insert()
    {
        Console.Write("Number of participants: ");
        int numarParticipanti = int.Parse(Console.ReadLine());
        Console.Write("Engine capacity: ");
        int capMotor = int.Parse(Console.ReadLine());

        var json = $"{{\"numarParticipanti\":{numarParticipanti},\"capMotor\":{capMotor}}}";
        var content = new StringContent(json, Encoding.UTF8, "application/json");

        var response = await client.PostAsync(BaseUrl, content);

        if (response.IsSuccessStatusCode)
        {
            Console.WriteLine("Insert successful!");
            Console.WriteLine(await response.Content.ReadAsStringAsync());
        }
        else
        {
            Console.WriteLine($"Insert failed with code: {response.StatusCode}");
            Console.WriteLine(await response.Content.ReadAsStringAsync());
        }
    }

    private static async Task Update()
    {
        Console.Write("ID to update: ");
        string id = Console.ReadLine();

        Console.Write("New number of participants: ");
        int numarParticipanti = int.Parse(Console.ReadLine());
        Console.Write("New engine capacity: ");
        int capMotor = int.Parse(Console.ReadLine());

        var json = $"{{\"numarParticipanti\":{numarParticipanti},\"capMotor\":{capMotor}}}";
        var content = new StringContent(json, Encoding.UTF8, "application/json");

        var response = await client.PutAsync($"{BaseUrl}/{id}", content);

        if (response.IsSuccessStatusCode)
        {
            Console.WriteLine("Update successful!");
            Console.WriteLine(await response.Content.ReadAsStringAsync());
        }
        else
        {
            Console.WriteLine($"Update failed with code: {response.StatusCode}");
            Console.WriteLine(await response.Content.ReadAsStringAsync());
        }
    }

    private static async Task Delete()
    {
        Console.Write("ID to delete: ");
        string id = Console.ReadLine();

        var response = await client.DeleteAsync($"{BaseUrl}/{id}");

        if (response.IsSuccessStatusCode)
        {
            Console.WriteLine("Delete successful!");
            Console.WriteLine(await response.Content.ReadAsStringAsync());
        }
        else
        {
            Console.WriteLine($"Delete failed with code: {response.StatusCode}");
            Console.WriteLine(await response.Content.ReadAsStringAsync());
        }
    }

    private static async Task ListAll()
    {
        var response = await client.GetAsync(BaseUrl);

        if (response.IsSuccessStatusCode)
        {
            Console.WriteLine("List of Curse:");
            Console.WriteLine(await response.Content.ReadAsStringAsync());
        }
        else
        {
            Console.WriteLine($"Failed to list curse. Code: {response.StatusCode}");
            Console.WriteLine(await response.Content.ReadAsStringAsync());
        }
    }
}
