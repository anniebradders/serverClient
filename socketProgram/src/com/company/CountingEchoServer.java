package com.company;

import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.NoSuchElementException;

public class CountingEchoServer {
    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.err.println("Usage: java EnhancedEchoServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);

        try (
                ServerSocket serverSocket =
                        new ServerSocket(Integer.parseInt(args[0]));
        ) {
            // This extra while loop is solution to exercise 2, optional part 6.
            while (true) {
                try (
                        Socket clientSocket = serverSocket.accept();
                        PrintWriter out =
                                new PrintWriter(clientSocket.getOutputStream(), true);
                        BufferedReader in = new BufferedReader(
                                new InputStreamReader(clientSocket.getInputStream()));
                ) {
                    String inputLine;
                    int count = 0;
                    while ((inputLine = in.readLine()) != null) {
                        //System.out.println(inputLine);

                        // This loop body is solution to exercise 2, parts 1-4.
                        Scanner sc = new Scanner(inputLine);
                        String command = "";
                        String message = "";
                        try {
                            command = sc.next();
                            message = sc.skip(" ").nextLine();
                        } catch (NoSuchElementException e) {}
                        if (command.equals("say")) {
                            System.out.println(message);
                            count += 1;
                        } else if (command.equals("shout")) {
                            System.out.println(message.toUpperCase());
                            count += 1;
                        } else if (command.equals("quit")) {
                            count += 1;
                            System.out.println("The number of requests was: " + count);
                            break;
                        }else if (command.equals("count")){
                            System.out.println("The number of requests was: " + count);
                        }
                        else {
                            System.out.println("ILLEGAL REQUEST");
                            count += 1;
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Exception caught when trying to listen on port " + portNumber);
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen for a connection on port " + portNumber);
            System.out.println(e.getMessage());
        }
    }
}
