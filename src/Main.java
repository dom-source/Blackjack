import java.util.Random;
import java.util.Scanner;

public class Main {

    private static int credit = 10000;

    private static boolean playAgain = true;
    static boolean stayOrDouble = false;

    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();

    /**
     * Generates a random Blackjack Card.
     * 1 is an Ace
     * 11-13 are Jack, Queen & King
     *
     * @return value of the card.
     */
    private static int generateCard() {
        int number = random.nextInt(13) + 1; // Generates a number from 1-13
        if (number > 10) {
            number = 10;
        }
        return number;
    }

    public static void main(String[] args) {
        print("Welcome to Blackjack");
        print("Rules:");
        print("Blackjack pays 3:2");
        print("Dealer must stand on soft 17");
        while (playAgain) {
            print("You currently have " + credit + "$");
            print("How much do you want to bet?");
            int bet = scanner.nextInt();
            while (bet > credit || bet < 0) {
                print("Error.");
                print("How much do you want to bet?");
                bet = scanner.nextInt();
            }

            int userHand = generateCard();
            int dealerHand = generateCard();
            if (userHand == 1) {
                userHand = 11;
            }
            if (dealerHand == 1) {
                dealerHand = 11;
            }
            print("The dealer gets a " + dealerHand);
            print("You get a " + userHand);
            int newCard = generateCard();
            if (newCard == 1) {
                newCard = 11;
            }
            dealerHand += newCard;
            newCard = generateCard();
            if (newCard == 1) {
                newCard = 11;
            }
            userHand += newCard;
            print("You get a " + newCard);

            if (userHand == 21) {
                print("Blackjack!");
                credit += bet * 0.5;
            } else {

                print("You now have a " + userHand);

                do {
                    print("What do you want to do? (stay/deal/double)");

                    switch (scanner.next()) {
                        case "deal" -> {
                            newCard = generateCard();
                            if (newCard == 1 && userHand + 11 <= 21) {
                                newCard = 11;
                            }
                            userHand += newCard;
                            print("You get a " + newCard);
                            print("You now have a " + userHand);
                        }
                        case "stay" -> {
                            print("You stay with the " + userHand);
                            stayOrDouble = true;
                        }
                        case "double" -> {
                            bet *= 2;
                            print("You doubled the bet");
                            newCard = generateCard();
                            if (newCard == 1 && userHand + 11 <= 21) {
                                newCard = 11;
                            }
                            userHand += newCard;
                            print("You get a " + newCard);
                            print("You now have a " + userHand);
                            stayOrDouble = true;
                        }
                    }
                } while (userHand < 21 && !stayOrDouble);
                stayOrDouble = false;
                if (userHand > 21) {
                    print("Bust!");
                    credit -= bet;
                } else if (dealerHand > userHand) {
                    print("The dealer has a " + dealerHand);
                    print("You lost");
                    credit -= bet;
                } else {
                    print("The dealer has a " + dealerHand);
                    while (dealerHand < 17 && dealerHand <= userHand) {
                        newCard = generateCard();
                        if (newCard == 1 && dealerHand + 11 <= 21) {
                            newCard = 11;
                        }
                        print("The dealer gets a " + newCard);
                        dealerHand += newCard;
                        print("The dealer now has a " + dealerHand);
                    }
                    if (dealerHand > 21) {
                        print("The dealer busted");
                        print("You won");
                        credit += bet;
                    } else if (dealerHand > userHand) {
                        print("The dealer won");
                        credit -= bet;
                    } else if (userHand > dealerHand) {
                        print("You won");
                        credit += bet;
                    } else {
                        print("Push!");
                    }
                }
            }
            print("Type 'yes' if you want to play again.");
            playAgain = scanner.next().equals("yes");
        }
    }

    /**
     * Prints the given String to the console.
     *
     * @param s String to print
     */
    private static void print(String s) {
        System.out.println(s);
    }

    /**
     * Returns the remaining Credit a player has.
     *
     * @return credit in US-Dollar
     */
    public int getCredit() {
        return credit;
    }
}
