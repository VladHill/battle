package battleship;

import java.io.*;
import java.util.Properties;


import print.color.Ansi.*;
import print.color.ColoredPrinter;
import print.color.ColoredPrinterWIN;



public class Battle {
    private static final String NEWLINE = System.lineSeparator();
    private static char space = ' ';
    private static char ship = '#';
    private static char fail = '•';
    private static char hit = 'X';
    private static int sizeOfField = 10;
    private static int maxCountOfBattleShip = 1;
    private static int maxCountOfCruiser = 2;
    private static int maxCountOfDestroyer = 3;
    private static int maxCountOfSubmarine = 4;

    private static final char[][] computerField = new char[sizeOfField][sizeOfField];
    private static final char[][] playerField = new char[sizeOfField][sizeOfField];
    private static final char[][] playerField2 = new char[sizeOfField][sizeOfField];


    public static char getShip() {
        return ship;
    }

    public static char getFail() {
        return fail;
    }

    public static int getSizeOfField() {
        return sizeOfField;
    }

    public static char[][] getComputerField() {
        return computerField;
    }

    public static char[][] getPlayerField() {
        return playerField;
    }
    public static String player1Name;
    public static String player2Name;


    public static void main(String[] args){

        loadProperties();



        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String answer;
            System.out.println("Enter your name?");
            player1Name = reader.readLine();
            System.out.println("Hello, " + player1Name + "!");





            while (true) {

                ComputersShips.clearAll();
                PlayersShips.clearAll();
                System.out.print(player1Name + ", will you arrange the ships yourself or automatically? [Y/n(auto)]: ");
                answer = reader.readLine();
                if ("y".equals(answer.toLowerCase()) || "".equals(answer)) {

                    manualPlacingPlayersShips(reader);
                } else

                    generatePlayers1Ships();

                generateComputersField();



                while (PlayersShips.countAliveShip != 0 && ComputersShips.countAliveShip != 0) {
                    playerFireAndCheck(reader);
                    if (ComputersShips.countAliveShip != 0) {
                        computerFireAndCheck();
                    }
                }

                System.out.print("Play again? [Y/n]: ");
                answer = reader.readLine();
                if (!("y".equals(answer.toLowerCase()) || "".equals(answer))) {
                    break;
                }
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void manualPlacingPlayersShips(BufferedReader reader) throws IOException {

        for (int i = 0; i < sizeOfField; i++) {
            for (int j = 0; j < sizeOfField; j++) {
                playerField[i][j] = 32;
            }
        }
        printPlayersField();
        manuallyCreatePlayersShip(reader, 4, maxCountOfBattleShip);             //create battleship
        manuallyCreatePlayersShip(reader, 3, maxCountOfCruiser);                //create Cruisers
        manuallyCreatePlayersShip(reader, 2, maxCountOfDestroyer);              //create Destroyers
        manuallyCreatePlayersShip(reader, 1, maxCountOfSubmarine);              //create Submarines
    }

    private static void generateComputersField() {

        for (int i = 0; i < sizeOfField; i++) {
            for (int j = 0; j < sizeOfField; j++) {
                computerField[i][j] = 32;
            }
        }
        createComputersShip(4, maxCountOfBattleShip);                            //generate battleship
        createComputersShip(3, maxCountOfCruiser);                               //generate Cruisers
        createComputersShip(2, maxCountOfDestroyer);                             //generate Destroyers
        createComputersShip(1, maxCountOfSubmarine);                             //generate Submarines
    }

    private static void generatePlayers1Ships() {

        for (int i = 0; i < sizeOfField; i++) {
            for (int j = 0; j < sizeOfField; j++) {
                playerField[i][j] = 32;
            }
        }
        createPlayersShip(4, maxCountOfBattleShip);                            //generate battleship
        createPlayersShip(3, maxCountOfCruiser);                               //generate Cruisers
        createPlayersShip(2, maxCountOfDestroyer);                             //generate Destroyers
        createPlayersShip(1, maxCountOfSubmarine);                             //generate Submarines
    }
    private static void generatePlayers2Ships() {

        for (int i = 0; i < sizeOfField; i++) {
            for (int j = 0; j < sizeOfField; j++) {
                playerField[i][j] = 32;
            }
        }
        createPlayersShip(4, maxCountOfBattleShip);                            //generate battleship
        createPlayersShip(3, maxCountOfCruiser);                               //generate Cruisers
        createPlayersShip(2, maxCountOfDestroyer);                             //generate Destroyers
        createPlayersShip(1, maxCountOfSubmarine);                             //generate Submarines
    }

    private static void manuallyCreatePlayersShip(BufferedReader reader, int countOfDecker, int maxCountOfShip) throws IOException {

        int countOfShip = countOfDecker == 1 ? PlayersShips.countOfSubmarine : countOfDecker == 2 ?
                PlayersShips.countOfDestroyer : countOfDecker == 3 ? PlayersShips.countOfCruiser : PlayersShips.countOfBattleShip;

        while (countOfShip < maxCountOfShip) {
            System.out.println(String.format("Arrange the ships %s!", countOfDecker == 1 ? "submarine" : countOfDecker == 2 ?
                    "destroyer" : countOfDecker == 3 ? "cruiser" : "battleship"));

            if (countOfDecker != 1) {
                System.out.println("Which way to turn the ships? (n for north, e - east, s - south, w - west)" + NEWLINE + "For example: d2 e");
            } else {
                System.out.println("Coordinates" + NEWLINE + "For example: d2");
            }

            String parameters = reader.readLine();

            if ("exit".equals(parameters.toLowerCase())) {
                System.exit(0);
            }

            String[] param =  parameters.split("[ ]+");
            String coordinates = param[0];
            int number;
            int letter;
            try {
                letter = Character.toLowerCase(coordinates.charAt(0)) - 97;                      // a=0, b=1, c=2, d=3, e=4,f=5, g=6, h=7, i=8, j=9 .....
                number = Integer.parseInt(coordinates.substring(1, coordinates.length())) - 1;   // 1 => 0
            } catch (NumberFormatException e) {
                System.out.println("Error! Error! Enter the coordinates again");
                manuallyCreatePlayersShip(reader, countOfDecker, maxCountOfShip);
                break;
            }

            if ((number < 0 || number > sizeOfField - 1 ) || (letter < 0 || letter > sizeOfField - 1)){
                System.out.println("From a to j or from 1 to 10! Enter the coordinates again");
                manuallyCreatePlayersShip(reader, countOfDecker, maxCountOfShip);
                break;
            }

            if (countOfDecker > 1) {

                if (param.length > 1) {
                    char direction = param[1].charAt(0);

                    if (direction != 'n' && direction != 'e' && direction != 's' && direction != 'w'){
                        System.out.println("Enter according to example, e - east, s - south, w - west");
                        manuallyCreatePlayersShip(reader, countOfDecker, maxCountOfShip);
                        break;
                    }

                    PlayersShips playersShip = new PlayersShips();
                    playersShip.setShip(direction, countOfDecker, number, letter);
                    printPlayersField();
                    countOfShip = countOfDecker == 2 ? PlayersShips.countOfDestroyer : countOfDecker == 3 ? PlayersShips.countOfCruiser : PlayersShips.countOfBattleShip;
                } else {
                    System.out.println("Forgot to write, enter again");
                    manuallyCreatePlayersShip(reader, countOfDecker, maxCountOfShip);
                    break;
                }

            } else {

                PlayersShips playersShip = new PlayersShips();
                playersShip.setShip(number, letter);
                printPlayersField();
                countOfShip = PlayersShips.countOfSubmarine;
            }
        }
    }

    private static void createPlayersShip(int countOfDecker, int maxCountOfShip) {

        int countOfShip = countOfDecker == 1 ? PlayersShips.countOfSubmarine : countOfDecker == 2 ?
                PlayersShips.countOfDestroyer : countOfDecker == 3 ? PlayersShips.countOfCruiser : PlayersShips.countOfBattleShip;

        while (countOfShip < maxCountOfShip) {

            int randomNumber = (int) (Math.random() * sizeOfField);
            int randomLetter = (int) (Math.random() * sizeOfField);

            if (countOfDecker > 1) {
                int randomDirection = (int) (Math.random()*4);
                char direction = randomDirection == 0 ? 'n': randomDirection == 1 ? 'e': randomDirection == 2 ? 's': 'w';

                PlayersShips ship = new PlayersShips();
                ship.setShip(direction, countOfDecker, randomNumber, randomLetter);
                countOfShip = countOfDecker == 2 ? PlayersShips.countOfDestroyer : countOfDecker == 3 ?
                        PlayersShips.countOfCruiser : PlayersShips.countOfBattleShip;

            } else {

                PlayersShips ship = new PlayersShips();
                ship.setShip(randomNumber, randomLetter);
                countOfShip = PlayersShips.countOfSubmarine;
            }
        }
    }

    private static void createComputersShip(int countOfDecker, int maxCountOfShip){

        int countOfShip = countOfDecker == 1 ? ComputersShips.countOfSubmarine : countOfDecker == 2 ?
                ComputersShips.countOfDestroyer : countOfDecker == 3 ? ComputersShips.countOfCruiser : ComputersShips.countOfBattleShip;

        while (countOfShip < maxCountOfShip) {

            int randomNumber = (int) (Math.random() * sizeOfField);                                    
            int randomLetter = (int) (Math.random() * sizeOfField);

            if (countOfDecker > 1) {
                int randomDirection = (int) (Math.random()*4);
                char direction = randomDirection == 0 ? 'n': randomDirection == 1 ? 'e': randomDirection == 2 ? 's': 'w';

                ComputersShips ship = new ComputersShips();
                ship.setShip(direction, countOfDecker, randomNumber, randomLetter);
                countOfShip = countOfDecker == 2 ? ComputersShips.countOfDestroyer : countOfDecker == 3 ?
                        ComputersShips.countOfCruiser : ComputersShips.countOfBattleShip;

            } else {

                ComputersShips ship = new ComputersShips();
                ship.setShip(randomNumber, randomLetter);
                countOfShip = ComputersShips.countOfSubmarine;
            }
        }
    }

    private static void computerFireAndCheck() throws InterruptedException {

        int fireNumber = (int) (Math.random() * sizeOfField);
        int fireLetter= (int) (Math.random() * sizeOfField);

        if (playerField[fireLetter][fireNumber] == space) {
            playerField[fireLetter][fireNumber] = fail;
            printTheField();
            System.out.println("Computer missed \n" + (char)(fireNumber + 97) + "" + (fireLetter + 1));
        } else if (playerField[fireLetter][fireNumber] == ship) {
            playerField[fireLetter][fireNumber] = hit;
            printTheField();
            System.out.println("Computer wounded \n" + (char)(fireNumber + 97) + "" + (fireLetter + 1) + "!");
/***/            Thread.sleep(2000);
            for(PlayersShips playersShip: PlayersShips.shipsList) {
                for (int j = 0; j < playersShip.coordinates.length; j++ ) {
                    int shipNumber = playersShip.coordinates[j].number;
                    int shipLetter = playersShip.coordinates[j].letter;
                    if (fireNumber == shipNumber && fireLetter == shipLetter) {
                        playersShip.countAliveDecker--;
                        if (playersShip.countAliveDecker == 0){
                            int ignore = playersShip.countOfDecker==1? PlayersShips.countOfSubmarine-- : playersShip.countOfDecker==2?
                                    PlayersShips.countOfDestroyer-- : playersShip.countOfDecker==3?
                                    PlayersShips.countOfCruiser-- : PlayersShips.countOfBattleShip--;

                            playersShip.areaAroundTheShip();
                            printTheField();
                            System.out.println("Killed");
                            PlayersShips.countAliveShip--;

                            if(PlayersShips.countAliveShip == 0) {
                                System.out.println("Lose");
                                return;
                            }
                        }
                    }
                }
            }
            computerFireAndCheck();

        } else if (playerField[fireLetter][fireNumber] == hit
                || playerField[fireLetter][fireNumber] == fail) {
            computerFireAndCheck();
        }
    }


    private static void playerFireAndCheck(BufferedReader reader) throws IOException
    {
        printTheField();
        int fireNumber;
        int fireLetter;
        while (true) {
            System.out.print("Fire: ");
            String fire = reader.readLine();


            if ("exit".equals(fire.toLowerCase())) {
                System.exit(0);
            }
            if (fire.length()>1) {
                fireLetter = Character.toLowerCase(fire.charAt(0)) - 97;
                fireNumber = Integer.parseInt(fire.substring(1, fire.length())) - 1;
                if ((fireNumber > -1 && fireNumber < sizeOfField )
                        && (fireLetter > -1 && fireLetter < sizeOfField)) {
                    break;
                } else System.out.println("Wrong coordinates");
            } else System.out.println("Wrong coordinates");
        }

        if (computerField[fireLetter][fireNumber] == space) {
            computerField[fireLetter][fireNumber] = fail;
            printTheField();

        } else if (computerField[fireLetter][fireNumber] == ship) {
            computerField[fireLetter][fireNumber] = hit;
            System.out.println("You wounded");
            for(ComputersShips computer: ComputersShips.shipsList) {
                for (int j = 0; j < computer.coordinates.length; j++ ) {
                    int shipNumber = computer.coordinates[j].number;
                    int shipLetter = computer.coordinates[j].letter;
                    if (fireNumber == shipNumber && fireLetter == shipLetter) {
                        computer.countAliveDecker--;
                        if (computer.countAliveDecker == 0){
                            int ignore = computer.countOfDecker==1? ComputersShips.countOfSubmarine-- : computer.countOfDecker==2?
                                    ComputersShips.countOfDestroyer-- : computer.countOfDecker==3?
                                    ComputersShips.countOfCruiser-- : ComputersShips.countOfBattleShip--;

                            computer.areaAroundTheShip();
                            System.out.println("You killed");
                            ComputersShips.countAliveShip--;

                            if(ComputersShips.countAliveShip == 0) {
                                System.out.println( player1Name + "You won!!");
                                printTheField();
                                return;
                            }
                        }
                    }
                }
            }
            System.out.println("Shoot again");
            playerFireAndCheck(reader);

        } else if (computerField[fireLetter][fireNumber] == hit
                || computerField[fireLetter][fireNumber] == fail) {
            System.out.println("Once again, you've already shot here");
            playerFireAndCheck(reader);
        }
    }

    private static void loadProperties(){
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream("config.properties")) {
            properties.load(input);
            space = properties.getProperty("space").charAt(0);
            ship = properties.getProperty("ship").charAt(0);
            fail = properties.getProperty("fail").charAt(0);
            hit = properties.getProperty("hit").charAt(0);
            sizeOfField = Integer.parseInt(properties.getProperty("sizeOfField"));
            maxCountOfBattleShip = Integer.parseInt(properties.getProperty("maxCountOfBattleShip"));
            maxCountOfCruiser = Integer.parseInt(properties.getProperty("maxCountOfCruiser"));
            maxCountOfDestroyer = Integer.parseInt(properties.getProperty("maxCountOfDestroyer"));
            maxCountOfSubmarine = Integer.parseInt(properties.getProperty("maxCountOfSubmarine"));
        } catch (IOException e) {
            System.out.println("Can't find config file, using default");
        }
    }

    private static void printPlayersField()
    {
        System.out.println("   1  2  3  4  5  6  7  8  9  10");
        for (int i = 0; i < sizeOfField; i++)
        {
            System.out.print(String.format("%2.2s", (char)(i + 65)));
            for (int j = 0; j < sizeOfField; j++)
            {
                System.out.print("[" + playerField[i][j] + "]");
            }
            System.out.println();
        }
    }

    private static void printTheField()
    {
        ColoredPrinter cp = new ColoredPrinter.Builder(1, false)
                .foreground(FColor.NONE).background(BColor.NONE).build();   //setting format

        System.out.println("              Player                                             Computer");
        System.out.println("    1  2  3  4  5  6  7  8  9  10                      1  2  3  4  5  6  7  8  9  10");
        for (int i = 0; i < sizeOfField; i++)
        {
            System.out.print(String.format("%2.2s ", (char)(i + 65)));
            for (int j = 0; j < sizeOfField; j++)
            {
                char cross = playerField[i][j];
                if (cross == Battle.hit) {
                    cp.print('[', Attribute.NONE, FColor.BLUE, BColor.BLUE);
                    cp.print(cross, Attribute.NONE, FColor.RED, BColor.BLUE);
                    cp.print(']', Attribute.NONE, FColor.BLUE, BColor.BLUE);
                } else if (cross == Battle.ship) {
                    cp.print('[', Attribute.NONE, FColor.BLUE, BColor.BLUE);
                    cp.print(cross, Attribute.NONE, FColor.BLUE, BColor.BLUE);
                    cp.print(']', Attribute.NONE, FColor.BLUE, BColor.BLUE);
                } else {
                    cp.print('[', Attribute.NONE, FColor.BLUE, BColor.WHITE);
                    cp.print(cross, Attribute.NONE, FColor.BLACK, BColor.WHITE);
                    cp.print(']', Attribute.NONE, FColor.BLUE, BColor.WHITE);
                }
                cp.clear();
            }

            String ship = i==0? "Battleship: " : i==1? "Cruiser: " : i==2? "Destroyer: " : i==3? "Submarine: " : " ";
            String countOfPlayersShips = String.valueOf(i==0? PlayersShips.countOfBattleShip : i==1? PlayersShips.countOfCruiser : i==2?
                    PlayersShips.countOfDestroyer : i==3? PlayersShips.countOfSubmarine : "");
            String playersShips = String.format("  %-12s%1s   ", ship, countOfPlayersShips);
            System.out.print(playersShips);
            System.out.print(String.format("%2.2s ", (char)(i + 65)));
            for (int j = 0; j < sizeOfField; j++)
            {
                char cross = computerField[i][j] == Battle.ship ? ' ': computerField[i][j];
                if (cross == Battle.hit) {
                    cp.print('[', Attribute.NONE, FColor.BLUE, BColor.BLUE);
                    cp.print(cross, Attribute.NONE, FColor.RED, BColor.BLUE);
                    cp.print(']', Attribute.NONE, FColor.BLUE, BColor.BLUE);
                } else {
                    cp.print('[', Attribute.NONE, FColor.BLUE, BColor.WHITE);
                    cp.print(cross, Attribute.NONE, FColor.BLACK, BColor.WHITE);
                    cp.print(']', Attribute.NONE, FColor.BLUE, BColor.WHITE);
                }
            }
            cp.clear();
            String countOfComputersShips = String.valueOf(i==0? ComputersShips.countOfBattleShip : i==1? ComputersShips.countOfCruiser : i==2?
                    ComputersShips.countOfDestroyer : i==3? ComputersShips.countOfSubmarine : "");
            String computersShips = String.format("  %-12s%1s", ship, countOfComputersShips);
            System.out.println(computersShips);
        }
    }

    private static void multigamep1()
    {


        /*try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String answer;
            while (true) {

                ComputersShips.clearAll();
                PlayersShips.clearAll();
                System.out.print("sam or auto [Y/n] ");
                answer = reader.readLine();
                if ("y".equals(answer.toLowerCase()) || "".equals(answer)) {

                    manualPlacingPlayersShips(reader);
                } else

                    generatePlayers1Ships();
                    //generateEnemyShips();
            }

        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }*/
    }
    /*private static void generateEnemyShips()
    {
        for (int i = 0; i < sizeOfField; i++) {
            for (int j = 0; j < sizeOfField; j++) {
                computerField[i][j] = 32;
            }
        }
        createPlayersShip(4, maxCountOfBattleShip);                            //generate battleship
        createPlayersShip(3, maxCountOfCruiser);                               //generate Cruisers
        createPlayersShip(2, maxCountOfDestroyer);                             //generate Destroyers
        createPlayersShip(1, maxCountOfSubmarine);                             //generate Submarines
    }*/
}
