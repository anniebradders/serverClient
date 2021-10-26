package com.company;

import java.net.*;
import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class EnhancedEchoServer {
    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.err.println("Usage: java EchoServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);

        try (
                ServerSocket serverSocket =
                        new ServerSocket(Integer.parseInt(args[0]));
                Socket clientSocket = serverSocket.accept();
                PrintWriter out =
                        new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
        ) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                //System.out.println(inputLine);
                Scanner scan = new Scanner(inputLine);

                String say = "";
                String line = "";
                try {
                    say = scan.next();
                    line = scan.skip(" ").nextLine();
                }catch (NoSuchElementException e) {}
                    if (say.equals("say")) {
                        //out.println(line);
                        System.out.println(line);
                    }else if (say.equals("shout")){
                        System.out.println(line.toUpperCase());
                    }else if (say.equals("quit")){
                        break;
                    }
                    else {
                        System.out.println("ILLEGAL REQUEST");
                    }
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
