import java.util.Random;
import java.util.Scanner;

public class Blackjack {

  // Global variables
  static int[][] playedCards = clearCards();
  final static int deckSize = 312;
  final static int sleepTime = 750;
  static int cardCount = 0;
  final static char spade = '♠';    // index 0
  final static char club = '♣';     // index 1
  final static char diamond = '♦';  // index 2
  final static char heart = '♥';    // index 3
  static Scanner scan = new Scanner(System.in);
  static Blackjack.Card dealerCard1 = null;
  static Blackjack.Card dealerCard2 = null;
  static Blackjack.Card dealerCard3 = null;
  static Blackjack.Card dealerCard4 = null;
  static Blackjack.Card dealerCard5 = null;
  static Blackjack.Card playerCard1 = null;
  static Blackjack.Card playerCard2 = null;
  static Blackjack.Card playerCard3 = null;
  static Blackjack.Card playerCard4 = null;
  static Blackjack.Card playerCard5 = null;
  static int playerCardCount = 2;
  static int dealerCardCount = 2;
  static int money = 100;
  static int bet = 0;
  
  public static void main(String[] args) throws InterruptedException {
    System.out.println("=== BLACKJACK ===");
    System.out.println("Type \"exit\" at any prompt to exit the game.\n");
    int choice;
    boolean gameLoop = true;
    boolean roundLoop;
    int playerTotal;
    int dealerTotal;
    while (gameLoop) {
      dealerCard1 = genCard();
      dealerCard2 = genCard();
      if (addDealerCards(dealerCardCount) == 22) {
        dealerCard1.setValue(1);
      }
      playerCard1 = genCard();
      playerCard2 = genCard();
      if (addPlayerCards(playerCardCount) == 22) {
        playerCard1.setValue(1);
      }
      roundLoop = true;
      System.out.println("You have $" + money + ".");
      Thread.sleep(sleepTime);
      bet = betPrompt(money);
      if (bet == -1) {
        System.exit(0);
      }
      Thread.sleep(sleepTime);
      System.out.println("\nDealer's hand: "+ dealerCard1.toString() + " " + dealerCard2.faceDown());
      System.out.println("Player's hand: " + playerCard1.toString() + " " + playerCard2.toString()+"\n");
      Thread.sleep(sleepTime);
      if (addPlayerCards(playerCardCount) == 21) {
        System.out.println("Blackjack!");
        Thread.sleep(sleepTime);
        System.out.println("Won $" + bet + ".");
        money = money + bet;
        roundLoop = false;
      }
      while (roundLoop) {
        choice = choicePrompt();
        Thread.sleep(sleepTime);
        // Hit
        if (choice == 1) {
          if (playerCardCount == 2) {
            playerCard3 = genCard();
            playerCardCount++;
            System.out.println("\nHit!");
            Thread.sleep(sleepTime);
            System.out.println("Player's hand: " + playerCard1.toString() + " " + playerCard2.toString() + " " + playerCard3.toString() + "\n");
          } else if (playerCardCount == 3) {
            playerCard4 = genCard();
            playerCardCount++;
            System.out.println("\nHit!");
            Thread.sleep(sleepTime);
            System.out.println("Player's hand: " + playerCard1.toString() + " " + playerCard2.toString() + " " + playerCard3.toString() +  " " + playerCard4.toString() + "\n");
          } else if (playerCardCount == 4) {
            playerCard5 = genCard();
            playerCardCount++;
            System.out.println("\nHit!");
            Thread.sleep(sleepTime);
            System.out.println("Player's hand: " + playerCard1.toString() + " " + playerCard2.toString() + " " + playerCard3.toString() +  " " + playerCard4.toString() +  " " + playerCard5.toString() + "\n");
          } else if (playerCardCount >= 5) {
            System.out.println("\nYou have too many cards!\n");
          }
          playerTotal = addPlayerCards(playerCardCount);
          Thread.sleep(sleepTime);
          if (playerTotal == 21) {
            System.out.println("Blackjack!");
            Thread.sleep(sleepTime);
            System.out.println("Won $" + bet + ".");
            money = money + bet;
            roundLoop = false;
          } else if (playerTotal > 21) {
            System.out.println("Bust!");
            Thread.sleep(sleepTime);
            System.out.println("Lost $" + bet + ".");
            money = money - bet;
            roundLoop = false;
          }
        }
        
        // Stand
        if (choice == 2) {
          System.out.println("\nStand!");
          Thread.sleep(sleepTime);
          System.out.println("\nDealer's hand: "+ dealerCard1.toString() + " " + dealerCard2.toString()+"\n");
          Thread.sleep(sleepTime);
          playerTotal = addPlayerCards(playerCardCount);
          dealerTotal = addDealerCards(dealerCardCount);
          if (dealerTotal <= 16) {
            dealerTotal = dealerHit(playerTotal);
          }
          if (dealerTotal < 21) {
            System.out.println("Dealer Stand!");
            Thread.sleep(sleepTime);
            System.out.println("Player hand total: " + playerTotal);
            System.out.println("Dealer hand total: " + dealerTotal + "\n");
            Thread.sleep(sleepTime);
            if (playerTotal > dealerTotal) {
              System.out.println("Win!");
              Thread.sleep(sleepTime);
              System.out.println("Won $" + bet + ".\n");
              money = money + bet;
              roundLoop = false;
            } else if (playerTotal < dealerTotal) {
              System.out.println("Lose!");
              Thread.sleep(sleepTime);
              System.out.println("Lost $" + bet + ".\n");
              money = money - bet;
              roundLoop = false;
            } else {
              System.out.println("Draw!");
              Thread.sleep(sleepTime);
              System.out.println("Did not win or lose anything.\n");
              roundLoop = false;
            }
          } else if (dealerTotal == 21) {
            System.out.println("Dealer Blackjack!");
            Thread.sleep(sleepTime);
            System.out.println("Player hand total: " + playerTotal);
            Thread.sleep(sleepTime);
            System.out.println("Dealer hand total: " + dealerTotal + "\n");
            Thread.sleep(sleepTime);
            System.out.println("Lose!");
            Thread.sleep(sleepTime);
            System.out.println("Lost $" + bet + ".\n");
            money = money - bet;
            roundLoop = false;
          } else if (dealerTotal > 21) {
            System.out.println("Dealer Bust!");
            Thread.sleep(sleepTime);
            System.out.println("Player hand total: " + playerTotal);
            Thread.sleep(sleepTime);
            System.out.println("Dealer hand total: " + dealerTotal + "\n");
            Thread.sleep(sleepTime);
            System.out.println("Win!");
            Thread.sleep(sleepTime);
            System.out.println("Won $" + bet + ".\n");
            money = money + bet;
            roundLoop = false;
          }
          
        }
        if (choice == 3) {
          System.out.println("\nDouble down!");
          Thread.sleep(sleepTime);
          playerCard3 = genCard();
          playerCardCount++;
          System.out.println("Player's hand: " + playerCard1.toString() + " " + playerCard2.toString() + " " + playerCard3.toString() + "\n");
          Thread.sleep(sleepTime);
          System.out.println("Dealer's hand: "+ dealerCard1.toString() + " " + dealerCard2.toString());
          Thread.sleep(sleepTime);
          playerTotal = addPlayerCards(playerCardCount);
          dealerTotal = addDealerCards(dealerCardCount);
          if (dealerTotal <= 16) {
            dealerTotal = dealerHit(playerTotal);
          }
          if (dealerTotal < 21) {
            System.out.println("Dealer Stand!");
            Thread.sleep(sleepTime);
            System.out.println("Player hand total: " + playerTotal);
            System.out.println("Dealer hand total: " + dealerTotal + "\n");
            Thread.sleep(sleepTime);
            if (playerTotal > dealerTotal) {
              System.out.println("Win!");
              Thread.sleep(sleepTime);
              System.out.println("Won $" + bet*2 + ".\n");
              money = money + bet*2;
              roundLoop = false;
            } else if (playerTotal < dealerTotal) {
              System.out.println("Lose!");
              Thread.sleep(sleepTime);
              System.out.println("Lost $" + bet*2 + ".\n");
              money = money - bet*2;
              roundLoop = false;
            } else {
              System.out.println("Draw!");
              Thread.sleep(sleepTime);
              System.out.println("Did not win or lose anything.\n");
              roundLoop = false;
            }
          } else if (dealerTotal == 21) {
            System.out.println("Dealer Blackjack!");
            Thread.sleep(sleepTime);
            System.out.println("Player hand total: " + playerTotal);
            Thread.sleep(sleepTime);
            System.out.println("Dealer hand total: " + dealerTotal + "\n");
            Thread.sleep(sleepTime);
            System.out.println("Lose!");
            Thread.sleep(sleepTime);
            System.out.println("Lost $" + bet*2 + ".\n");
            money = money - bet*2;
            roundLoop = false;
          } else if (dealerTotal > 21) {
            System.out.println("Dealer Bust!");
            Thread.sleep(sleepTime);
            System.out.println("Player hand total: " + playerTotal);
            Thread.sleep(sleepTime);
            System.out.println("Dealer hand total: " + dealerTotal + "\n");
            Thread.sleep(sleepTime);
            System.out.println("Win!");
            Thread.sleep(sleepTime);
            System.out.println("Won $" + bet*2 + ".\n");
            money = money + bet*2;
            roundLoop = false;
          }
        }
        if (choice == -1) {
          System.exit(0);
        }
      }
      playerCardCount = 2;
      dealerCardCount = 2;
      Thread.sleep(sleepTime);
      if (cardCount >= (deckSize * 0.666)) {
        System.out.println("Reshuffling the deck.\n");
        Thread.sleep(sleepTime);
        playedCards = clearCards();
      }
      if (money <= 0) {
        System.out.println("\nYou ran out of money!");
        Thread.sleep(sleepTime);
        System.out.println("Game over!");
        Thread.sleep(sleepTime);
        System.out.println("\nExiting...");
        gameLoop = false;
      }
    }
  }

  // Playing card representation.
  public static class Card {
    int suit, ID, value;
    public Card(int suit, int ID) {
      this.suit = suit;
      this.ID = ID;
      if (ID == 1) {
        value = 11;
      } else if (ID > 10) {
        value = 10;
      } else {
        value = ID;
      }
    }
    
    public int getSuit() {
      return suit;
    }

    public int getID() {
      return ID;
    }
    
    public int getValue() {
      return value;
    }
    
    public void setValue(int newValue) {
      value = newValue;
    }

    public String faceDown() {
      return "[(?)]";
    }
    public String toString() {
      if (ID == 1) {
        switch(suit) {
          case 0:
            return "[A " + spade + "]";
          case 1:
            return "[A " + club + "]";
          case 2:
            return "[A " + diamond + "]";
          case 3:
            return "[A " + heart + "]";
        }
      } else if (ID == 11) {
        switch(suit) {
          case 0:
            return "[J " + spade + "]";
          case 1:
            return "[J " + club + "]";
          case 2:
            return "[J " + diamond + "]";
          case 3:
            return "[J " + heart + "]";
        }
      } else if (ID == 12) {
        switch(suit) {
          case 0:
            return "[Q " + spade + "]";
          case 1:
            return "[Q " + club + "]";
          case 2:
            return "[Q " + diamond + "]";
          case 3:
            return "[Q " + heart + "]";
        }
      } else if (ID == 13) {
        switch(suit) {
          case 0:
            return "[K " + spade + "]";
          case 1:
            return "[K " + club + "]";
          case 2:
            return "[K " + diamond + "]";
          case 3:
            return "[K " + heart + "]";
        } 
      } else if (ID == 10) {
        switch(suit) {
          case 0:
            return "[10" + spade + "]";
          case 1:
            return "[10" + club + "]";
          case 2:
            return "[10" + diamond + "]";
          case 3:
            return "[10" + heart + "]";
        } 
      } else {
        switch(suit) {
          case 0:
            return "[" + ID + " " + spade + "]";
          case 1:
            return "[" + ID + " " + club + "]";
          case 2:
            return "[" + ID + " " + diamond + "]";
          case 3:
            return "[" + ID + " " + heart + "]";
        }
      } 
      
      return null;
    }
    
    public boolean greaterThan(Card comparisonCard) {
      if (this.getValue() > comparisonCard.getValue()) {
        return true;
      }
      return false;
    }
    
    public boolean equals(Card comparisonCard) {
      if (this.getValue() == comparisonCard.getValue()) {
        return true;
      }
      return false;
    }
    
    public boolean lessThan(Card comparisonCard) {
      if (this.getValue() < comparisonCard.getValue()) {
        return true;
      }
      return false;
    }
    
    public boolean greaterThan(int comparisonCard) {
      if (this.getValue() > comparisonCard) {
        return true;
      }
      return false;
    }
    
    public boolean equals(int comparisonCard) {
      if (this.getValue() == comparisonCard) {
        return true;
      }
      return false;
    }
    
    public boolean lessThan(int comparisonCard) {
      if (this.getValue() < comparisonCard) {
        return true;
      }
      return false;
    }
    
  } // End Card class
  
  // UNUSED
  public static class Hand {
    int cards = 2;
    Card card1, card2;
    public Hand(Card card1, Card card2) {
      this.card1 = card1;
      this.card2 = card2;
    }
  }
  
  // Generates a new card that hasn't been played yet.
  static Card genCard() {
    if (cardCount >= deckSize) {
      System.err.println("Out of cards.");
      System.exit(1);
    }
    Random rand = new Random();
    boolean findingCard = true;
    int suite = 0, ID = 0;
    while (findingCard) {
      suite = rand.nextInt(4);
      ID = rand.nextInt(13)+1;
      if (!cardPlayed(suite, ID)) {
        findingCard = false;
      }
    }
    playedCards[suite][ID]++;
    cardCount++;
    return new Blackjack.Card(suite, ID);
  }
  
  // Returns true if a card has been played already.
  static boolean cardPlayed(int suite, int ID) {
    if (playedCards[suite][ID] == 6) {
      return true;
    }
    return false;
  }
  
  // For Debug
  static void printCardTable() {
    for (int i = 0; i < 4; i++) {
      for (int j = 1; j < 14; j++) {
          System.out.print(playedCards[i][j] + ", ");
      }
      System.out.println();
    }
  }
  
  static void debugCardPrint(int value) {
    for (int i = 0; i < value; i++) {
      System.out.println(genCard().toString());
    }
    printCardTable();
  }
  
  // Generates a new clear card array
  static int[][] clearCards() {
    int[][] cleared = new int[4][14];
    for (int i = 0; i < 4; i++) {
      for (int j = 1; j < 14; j++) {
          cleared[i][j] = 0;
      }
    }
    return cleared;
  }
  
  static int choicePrompt() {
    char choice = ' ';
    System.out.println("What would you like to do?");
    if (playerCardCount == 2) {
      System.out.print("H = Hit, S = Stand, D = Double: ");
    } else {
      System.out.print("H = Hit, S = Stand: ");
    }
    String userInput = scan.nextLine();
    if (userInput.equalsIgnoreCase("exit")) {
      System.out.println("\nExiting...");
      return(-1);
    }
    try {
      choice = Character.toLowerCase(userInput.charAt(0));
    } catch (Exception noString) {
      System.out.println("Error: You must specify a choice.\n");
      return(choicePrompt());
    }
    switch(choice) {
      case 'h': {
        return(1);
      }
      case '1': {
        return(1);
      }
      case 's': {
        return(2);
      }
      case '2': {
        return(2);
      }
      case 'd': {
        if (playerCardCount == 2) {
          if (bet * 2 <= money) {
            return(3);
          } else {
            System.out.println("Error: Invalid choice, you do not have enough money.\n");
            return(choicePrompt());
          }
          
        } else {
          System.out.println("Error: Invalid choice, can only double down on first turn.\n");
          return(choicePrompt());
        }
      }
      case '3': {
        if (playerCardCount == 2) {
          if (bet * 2 <= money) {
            return(3);
          } else {
            System.out.println("Error: Invalid choice, you do not have enough money.\n");
            return(choicePrompt());
          }
          
        } else {
          System.out.println("Error: Invalid choice, can only double down on first turn.\n");
          return(choicePrompt());
        }
      }
      default: {
        System.out.println("Error: Invalid choice \"" + userInput + "\".\n");
        return(choicePrompt());
      }
    }
  }
  
  static int betPrompt(int money) {
    System.out.print("How much would you like to bet? ");
    String userInput = scan.nextLine();
    if (userInput.equalsIgnoreCase("exit")) {
      System.out.println("\nExiting...");
      return(-1);
    }
    try {
      int betProposal = (Integer.parseInt(userInput));
      if (betProposal > money) {
        System.out.println("You don't have that much money!\n");
        return(betPrompt(money));
      } else if (betProposal < 1) {
        System.out.println("You cannot bet that amount!\n");
        return(betPrompt(money));
      } else {
        System.out.println("Bet $"+betProposal+".");
        return(betProposal);
      }
    } catch (Exception parseError) {
      System.out.println("Error: Input must be an integer!\n");
      return(betPrompt(money));
    }
  }
  
  // Unused
  static int addPlayerCardsOld(int cardCount) {
    switch(cardCount) {
      case 1:
        return playerCard1.getValue();
      case 2:
        return playerCard1.getValue() + playerCard2.getValue();
      case 3:
        return playerCard1.getValue() + playerCard2.getValue() + playerCard3.getValue();
      case 4:
        return playerCard1.getValue() + playerCard2.getValue() + playerCard3.getValue() + playerCard4.getValue();
      case 5:
        return playerCard1.getValue() + playerCard2.getValue() + playerCard3.getValue() + playerCard4.getValue() + playerCard5.getValue();
      default:
        return 0;
    }
  }
  
  static int addPlayerCards(int cardCount) {
    int temp;
    switch(cardCount) {
      case 1:
        return playerCard1.getValue();
      case 2: {
        temp = playerCard1.getValue() + playerCard2.getValue();
        if (temp > 21) {
          if (playerCard1.getID() == 1 || playerCard2.getID() == 1) {
            temp = temp - 10;
          }
        }
        return temp;
      }
      case 3: {
        temp = playerCard1.getValue() + playerCard2.getValue() + playerCard3.getValue();
        if (temp > 21) {
          if (playerCard1.getID() == 1 || playerCard2.getID() == 1 || playerCard3.getID() == 1) {
            temp = temp - 10;
          }
        }
        return temp;
      }
      case 4: {
        temp = playerCard1.getValue() + playerCard2.getValue() + playerCard3.getValue() + playerCard4.getValue();
        if (temp > 21) {
          if (playerCard1.getID() == 1 || playerCard2.getID() == 1 || playerCard3.getID() == 1 || playerCard4.getID() == 1) {
            temp = temp - 10;
          }
        }
        return temp;
      }
      case 5: {
        temp = playerCard1.getValue() + playerCard2.getValue() + playerCard3.getValue() + playerCard4.getValue() + playerCard5.getValue();
        if (temp > 21) {
          if (playerCard1.getID() == 1 || playerCard2.getID() == 1 || playerCard3.getID() == 1 || playerCard4.getID() == 1 || playerCard5.getID() == 1) {
            temp = temp - 10;
          }
        }
        return temp;
      }
      default:
        System.out.println("FATAL ERROR");
        return 0;
    }
  }
  
  static int addDealerCards(int cardCount) {
    int temp;
    switch(cardCount) {
      case 1:
        return dealerCard1.getValue();
      case 2: {
        temp = dealerCard1.getValue() + dealerCard2.getValue();
        if (temp > 21) {
          if (dealerCard1.getID() == 1 || dealerCard2.getID() == 1) {
            temp = temp - 10;
          }
        }
        return temp;
      }
      case 3: {
        temp = dealerCard1.getValue() + dealerCard2.getValue() + dealerCard3.getValue();
        if (temp > 21) {
          if (dealerCard1.getID() == 1 || dealerCard2.getID() == 1 || dealerCard3.getID() == 1) {
            temp = temp - 10;
          }
        }
        return temp;
      }
      case 4: {
        temp = dealerCard1.getValue() + dealerCard2.getValue() + dealerCard3.getValue() + dealerCard4.getValue();
        if (temp > 21) {
          if (dealerCard1.getID() == 1 || dealerCard2.getID() == 1 || dealerCard3.getID() == 1 || dealerCard4.getID() == 1) {
            temp = temp - 10;
          }
        }
        return temp;
      }
      case 5: {
        temp = dealerCard1.getValue() + dealerCard2.getValue() + dealerCard3.getValue() + dealerCard4.getValue() + dealerCard5.getValue();
        if (temp > 21) {
          if (dealerCard1.getID() == 1 || dealerCard2.getID() == 1 || dealerCard3.getID() == 1 || dealerCard4.getID() == 1 || dealerCard5.getID() == 1) {
            temp = temp - 10;
          }
        }
        return temp;
      }
      default:
        System.out.println("FATAL ERROR");
        return 0;
    }
  }
  
  static int dealerHit(int playerTotal) throws InterruptedException {
    System.out.println("Dealer Hit!");
    Thread.sleep(sleepTime);
    if (dealerCardCount == 2) {
      dealerCard3 = genCard();
      System.out.println("Dealer's hand: "+ dealerCard1.toString() + " " + dealerCard2.toString() + " " + dealerCard3.toString());
    } else if (dealerCardCount == 3) {
      dealerCard4 = genCard();
      System.out.println("Dealer's hand: "+ dealerCard1.toString() + " " + dealerCard2.toString() + " " + dealerCard3.toString() + " " + dealerCard4.toString()); 
    } else if (dealerCardCount == 4) {
      dealerCard5 = genCard();
      System.out.println("Dealer's hand: "+ dealerCard1.toString() + " " + dealerCard2.toString() + " " + dealerCard3.toString() + " " + dealerCard4.toString() + " " + dealerCard5.toString());
    } else if (dealerCardCount == 5) {
      System.out.println("Dealer has too many cards.");
      return addDealerCards(dealerCardCount);
    }
    System.out.println("");
    Thread.sleep(sleepTime);
    dealerCardCount++;
    int dealerTotal = addDealerCards(dealerCardCount);
    if (dealerTotal > playerTotal) {
      return(dealerTotal);
    }
    if (dealerTotal <= 16) {
      return(dealerHit(playerTotal));
    } else {
      return(dealerTotal);
    }
  }
}
