package youareell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import controllers.IdController;
import controllers.MessageController;
import controllers.ServerController;
import controllers.TransactionController;

public class URLShell {
    private YouAreEll urll;
    private List<String> history;

    public URLShell() {
        this.urll = new YouAreEll(new TransactionController(
                new MessageController(ServerController.shared()),
                new IdController(ServerController.shared())));
        this.history = new ArrayList<>();
    }

    public static void prettyPrint(String output) {
        System.out.println("--------------------");
        System.out.println(output);
        System.out.println("--------------------");
    }

    public static void main(String[] args) throws java.io.IOException {
        new URLShell().run();
    }

    public void run() throws IOException {
        System.out.println("\n \nWelcome to Under-A-Rock! Type 'help' or '?' for available commands.");

        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        String commandLine;

        while (true) {
            System.out.print("🪨 Under-A-Rock > ");
            commandLine = console.readLine();

            if (commandLine.equals(""))
                continue;
            if (commandLine.equals("exit")) {
                System.out.println("\n*** Bye!\n");
                break;
            }

            history.add(commandLine);
            processCommand(commandLine);
        }
    }

    private void processCommand(String commandLine) {
        String[] commands = commandLine.split(" ");
        List<String> list = new ArrayList<>(Arrays.asList(commands));

        if (list.get(0).equals("ids")) {
            String results = urll.get_ids();
            URLShell.prettyPrint(results);
        } else if (list.get(0).equals("messages")) {
            String results = urll.get_messages();
            URLShell.prettyPrint(results);
        } else if (list.get(0).equals("postid")) {
            if (list.size() == 4) {
                String uid = list.get(1);
                String name = list.get(2);
                String github = list.get(3);
                String result = urll.postId(uid, name, github);
                System.out.println(result);
            } else {
                System.out.println("Usage: postid <uid> <name> <github>");
            }
        } else if (list.get(0).equals("getid")) {
            if (list.size() == 2) {
                String result = urll.getId(list.get(1));
                System.out.println(result);
            } else {
                System.out.println("Usage: getid <github>");
            }
        } else if (list.get(0).equals("putid")) {
            if (list.size() == 3) {
                String github = list.get(1);
                String newName = list.get(2);
                String result = urll.putId(github, newName);
                System.out.println(result);
            } else {
                System.out.println("Usage: putid <github> <newName>");
            }
        } else if (list.get(0).equals("postmessage")) {
            if (list.size() == 4) {
                String body = list.get(1);
                String from = list.get(2);
                String to = list.get(3);
                String result = urll.postMessage(body, from, to);
                System.out.println(result);
            } else {
                System.out.println("Usage: postmessage <body> <from> <to>");
            }
        } else if (list.get(0).equals("getmessages")) {
            if (list.size() == 2) {
                String result = urll.getMessagesForId(list.get(1));
                URLShell.prettyPrint(result);
            } else {
                System.out.println("Usage: getmessages <github>");
            }
        } else if (list.get(0).equals("deleteid")) {
            if (list.size() == 2) {
                String result = urll.deleteId(list.get(1));
                URLShell.prettyPrint(result);
            } else {
                System.out.println("Usage: deleteid <github>");
            }
        } else if (list.get(0).equals("getmessageseq")) {
            if (list.size() == 2) {
                String result = urll.getMessageForSequence(list.get(1));
                URLShell.prettyPrint(result);
            } else {
                System.out.println("Usage: getmessageseq <sequence>");
            }
        } else if (list.get(0).equals("getmessagesfromfriend")) {
            if (list.size() == 3) {
                String result = urll.getMessagesFromFriend(list.get(1), list.get(2));
                URLShell.prettyPrint(result);
            } else {
                System.out.println("Usage: getmessagesfromfriend <your_github> <friend_github>");
            }
        } else if (list.get(0).equals("help") || list.get(0).equals("?")) {
            displayHelpMenu();
        } else if (list.get(0).equals("history")) {
            displayHistory();
        } else if (list.get(0).equals("!!")) {
            executeLastCommand();
        } else if (list.get(0).startsWith("!")) {
            executeHistoryCommand(list.get(0));
        } else {
            System.out.println("Unknown command. Type 'help' or '?' for available commands.");
        }
    }

    private void displayHistory() {
        for (int i = 0; i < history.size(); i++) {
            System.out.println(i + " " + history.get(i));
        }
    }

    private void executeLastCommand() {
        if (history.size() > 1) {
            String lastCommand = history.get(history.size() - 2);
            System.out.println("Executing: " + lastCommand);
            processCommand(lastCommand);
        } else {
            System.out.println("No previous command in history");
        }
    }

    private void executeHistoryCommand(String command) {
        String numberStr = command.substring(1);
        try {
            int index = Integer.parseInt(numberStr);
            if (index >= 0 && index < history.size()) {
                String historyCommand = history.get(index);
                System.out.println("Executing: " + historyCommand);
                processCommand(historyCommand);
            } else {
                System.out.println("Invalid history index");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid history command");
        }
    }

    private void displayHelpMenu() {
        System.out.println("\n🌟 Welcome to the Under-A-Rock Command Center! 🌟");
        System.out.println("Here are the cosmic commands at your disposal:");
        System.out.println();
        System.out.println("🆔 id-tastic commands:");
        System.out.println("   ids                 - View all registered IDs");
        System.out.println("   postid <uid> <name> <github>  - Register a new ID");
        System.out.println("   getid <github>      - Get info for a specific ID");
        System.out.println("   putid <github> <newName>  - Update an ID's name");
        System.out.println("   deleteid <github>   - Delete an ID");
        System.out.println();
        System.out.println("💌 message-magic commands:");
        System.out.println("   messages            - View recent messages");
        System.out.println("   postmessage <body> <from> <to>  - Post a new message");
        System.out.println("   getmessageseq <sequence>  - Get a specific message by sequence number");
        System.out.println("   getmessagesfromfriend <your_github> <friend_github>  - Get messages from a friend");
        System.out.println();
        System.out.println("🕰️ time-travel commands:");
        System.out.println("   history             - View command history");
        System.out.println("   !!                  - Repeat the last command");
        System.out.println("   !<number>           - Repeat a specific command from history");
        System.out.println();
        System.out.println("🚪 exit                - Leave this awesome place");
        System.out.println();
        System.out.println("Remember, with great power comes great responsibility! 🦸‍");
    }
}