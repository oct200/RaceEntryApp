package Motociclete;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class CursaRestClient {
    private static final String BASE_URL = "http://localhost:8080/motociclete/curse";
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        while (true) {
            System.out.println("\nChoose an option:\n1. Insert\n2. Update\n3. Delete\n4. List all\n5. Exit");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> insert();
                case "2" -> update();
                case "3" -> delete();
                case "4" -> listAll();
                case "5" -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void insert() throws IOException {
        System.out.print("Number of participants: ");
        int numarParticipanti = Integer.parseInt(scanner.nextLine());
        System.out.print("Engine capacity: ");
        int capMotor = Integer.parseInt(scanner.nextLine());

        String json = String.format("{\"numarParticipanti\":%d,\"capMotor\":%d}", numarParticipanti, capMotor);
        HttpURLConnection conn = createConnection(BASE_URL, "POST");
        writeRequest(conn, json);
        int responseCode = conn.getResponseCode();

        if (responseCode == 200 || responseCode == 201) {
            System.out.println("Insert successful!");
            System.out.println(readResponse(conn));
        } else {
            System.out.println("Insert failed with code: " + responseCode);
            System.out.println(readError(conn));
        }
        conn.disconnect();
    }

    private static void update() throws IOException {
        System.out.print("ID to update: ");
        String id = scanner.nextLine();

        System.out.print("New number of participants: ");
        int numarParticipanti = Integer.parseInt(scanner.nextLine());
        System.out.print("New engine capacity: ");
        int capMotor = Integer.parseInt(scanner.nextLine());

        String json = String.format("{\"numarParticipanti\":%d,\"capMotor\":%d}", numarParticipanti, capMotor);
        HttpURLConnection conn = createConnection(BASE_URL + "/" + id, "PUT");
        writeRequest(conn, json);
        int responseCode = conn.getResponseCode();

        if (responseCode == 200) {
            System.out.println("Update successful!");
            System.out.println(readResponse(conn));
        } else {
            System.out.println("Update failed with code: " + responseCode);
            System.out.println(readError(conn));
        }
        conn.disconnect();
    }

    private static void delete() throws IOException {
        System.out.print("ID to delete: ");
        String id = scanner.nextLine();

        HttpURLConnection conn = createConnection(BASE_URL + "/" + id, "DELETE");
        int responseCode = conn.getResponseCode();

        if (responseCode == 200) {
            System.out.println("Delete successful!");
            System.out.println(readResponse(conn));
        } else {
            System.out.println("Delete failed with code: " + responseCode);
            System.out.println(readError(conn));
        }
        conn.disconnect();
    }

    private static void listAll() throws IOException {
        HttpURLConnection conn = createConnection(BASE_URL, "GET");
        int responseCode = conn.getResponseCode();

        if (responseCode == 200) {
            System.out.println("List of Curse:");
            System.out.println(readResponse(conn));
        } else {
            System.out.println("Failed to list curse. Code: " + responseCode);
            System.out.println(readError(conn));
        }
        conn.disconnect();
    }

    private static HttpURLConnection createConnection(String urlString, String method) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(method);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(method.equals("POST") || method.equals("PUT"));
        return conn;
    }

    private static void writeRequest(HttpURLConnection conn, String json) throws IOException {
        try (OutputStream os = conn.getOutputStream()) {
            os.write(json.getBytes());
            os.flush();
        }
    }

    private static String readResponse(HttpURLConnection conn) throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String input;
            while ((input = in.readLine()) != null) {
                response.append(input).append("\n");
            }
            return response.toString();
        }
    }

    private static String readError(HttpURLConnection conn) throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getErrorStream()))) {
            StringBuilder error = new StringBuilder();
            String input;
            while ((input = in.readLine()) != null) {
                error.append(input).append("\n");
            }
            return error.toString();
        }
    }
}