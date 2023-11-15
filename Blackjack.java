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
    // Initial greeting
    System.out.println("=== BLACKJACK ===");
    System.out.println("Type \"exit\" at any prompt to exit the game.\n");
    // Set up initial non global variables
    int choice;
    boolean gameLoop = true;
    boolean roundLoop;
    int playerTotal;
    int dealerTotal;
    
    // Main game loop start
    while (gameLoop) {
      roundLoop = true;
      // Generate first two cards for each player
      dealerCard1 = genCard();
      dealerCard2 = genCard();
      // Handles a case where two Aces counts as 22 by changing card value to 1
      if (addDealerCards(dealerCardCount) == 22) {
        dealerCard1.setValue(1);
      }
      playerCard1 = genCard();
      playerCard2 = genCard();
      if (addPlayerCards(playerCardCount) == 22) {
        playerCard1.setValue(1);
      }
      
      System.out.println("You have $" + money + ".");
      Thread.sleep(sleepTime);
      // Prompt for bet, returns -1 for exit
      bet = betPrompt(money);
      if (bet == -1) {
        System.exit(0);
      }
      
      Thread.sleep(sleepTime);
      System.out.println("\nDealer's hand: "+ dealerCard1.toString() + " " + dealerCard2.faceDown());
      System.out.println("Player's hand: " + playerCard1.toString() + " " + playerCard2.toString()+"\n");
      Thread.sleep(sleepTime);
      
      // Check if a player already has a Blackjack
      if (addPlayerCards(playerCardCount) == 21) {
        System.out.println("Blackjack!");
        Thread.sleep(sleepTime);
        System.out.println("Won $" + bet + ".");
        money = money + bet;
        roundLoop = false;
      }
      
      // Loop for each round
      while (roundLoop) {
        
        // Prompt for a choice
        choice = choicePrompt();
        Thread.sleep(sleepTime);
        
        // Choice 1 - Hit - Player draws a card
        if (choice == 1) {
          // Card drawn depends on cards currently in hand
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
          
          // Add up cards
          playerTotal = addPlayerCards(playerCardCount);
          Thread.sleep(sleepTime);
          
          // Check if player has a blackjack
          if (playerTotal == 21) {
            System.out.println("Blackjack!");
            Thread.sleep(sleepTime);
            System.out.println("Won $" + bet + ".");
            money = money + bet;
            roundLoop = false;
            
          // Check if above 21 - Bust
          } else if (playerTotal > 21) {
            System.out.println("Bust!");
            Thread.sleep(sleepTime);
            System.out.println("Lost $" + bet + ".");
            money = money - bet;
            roundLoop = false;
          }
          
        } // End of Hit
        
        // Choice 2 - Stand - Dealer's turn, then compare
        if (choice == 2) {
          System.out.println("\nStand!");
          Thread.sleep(sleepTime);
          
          System.out.println("\nDealer's hand: "+ dealerCard1.toString() + " " + dealerCard2.toString()+"\n");
          Thread.sleep(sleepTime);
          
          playerTotal = addPlayerCards(playerCardCount);
          dealerTotal = addDealerCards(dealerCardCount);
          
          // If the dealer has 16 or less cards, it will hit
          if (dealerTotal <= 16) {
            dealerTotal = dealerHit(playerTotal);
          }
          
          // If the dealer has less than 21 but more than 16, it will stand
          if (dealerTotal < 21) {
            System.out.println("Dealer Stand!");
            Thread.sleep(sleepTime);
            
            System.out.println("Player hand total: " + playerTotal);
            System.out.println("Dealer hand total: " + dealerTotal + "\n");
            Thread.sleep(sleepTime);
            
            // If player beats the dealer, win
            if (playerTotal > dealerTotal) {
              System.out.println("Win!");
              Thread.sleep(sleepTime);
              System.out.println("Won $" + bet + ".\n");
              money = money + bet;
              roundLoop = false;
            
            // If dealer beats the player, lose 
            } else if (playerTotal < dealerTotal) {
              System.out.println("Lose!");
              Thread.sleep(sleepTime);
              System.out.println("Lost $" + bet + ".\n");
              money = money - bet;
              roundLoop = false;
              
            // If the player and dealer have equal card totals, draw
            } else {
              System.out.println("Draw!");
              Thread.sleep(sleepTime);
              System.out.println("Did not win or lose anything.\n");
              roundLoop = false;
            }
            
          // If dealer gets a blackjack, they win
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
            
          // If dealer goes over 21, they bust
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
          
        } // End of Stand
        
        // Choice 3 - Double down - Hit once, then stand for double points
        if (choice == 3) {
          
          System.out.println("\nDouble down!");
          Thread.sleep(sleepTime);
          
          // Generate player's third card
          playerCard3 = genCard();
          playerCardCount++;
          
          System.out.println("Player's hand: " + playerCard1.toString() + " " + playerCard2.toString() + " " + playerCard3.toString() + "\n");
          Thread.sleep(sleepTime);
          System.out.println("Dealer's hand: "+ dealerCard1.toString() + " " + dealerCard2.toString());
          Thread.sleep(sleepTime);
          
          playerTotal = addPlayerCards(playerCardCount);
          dealerTotal = addDealerCards(dealerCardCount);
          
          // Check bust
          if (playerTotal > 21) {
            System.out.println("\nBust!");
            Thread.sleep(sleepTime);
            System.out.println("Lost $" + bet*2 + ".");
            money = money - bet*2;
            roundLoop = false;
            break;
          } else if (playerTotal == 21) {
            System.out.println("\nBlackjack!");
            Thread.sleep(sleepTime);
            System.out.println("Won $" + bet*2 + ".");
            money = money + bet*2;
            roundLoop = false;
            break;
          }
          // If the dealer has 16 or less cards, it will hit
          if (dealerTotal <= 16) {
            dealerTotal = dealerHit(playerTotal);
          }
          
          // If the dealer has more than 16 but less than 21, they will stand
          if (dealerTotal < 21) {
            System.out.println("Dealer Stand!");
            Thread.sleep(sleepTime);
            System.out.println("Player hand total: " + playerTotal);
            System.out.println("Dealer hand total: " + dealerTotal + "\n");
            Thread.sleep(sleepTime);
            
            // If player beats dealer, win
            if (playerTotal > dealerTotal) {
              System.out.println("Win!");
              Thread.sleep(sleepTime);
              System.out.println("Won $" + bet*2 + ".\n");
              money = money + bet*2;
              roundLoop = false;
              
            // If dealer beats player, lose
            } else if (playerTotal < dealerTotal) {
              System.out.println("Lose!");
              Thread.sleep(sleepTime);
              System.out.println("Lost $" + bet*2 + ".\n");
              money = money - bet*2;
              roundLoop = false;
              
            // If equal, draw
            } else {
              System.out.println("Draw!");
              Thread.sleep(sleepTime);
              System.out.println("Did not win or lose anything.\n");
              roundLoop = false;
            }
            
          // If dealer blackjack
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
            
          // Dealer bust
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
        } // End of Double Down
        
        // Exit choice
        if (choice == -1) {
          System.exit(0);
        }
      } // End of round loop
      
      // Reset for next round
      playerCardCount = 2;
      dealerCardCount = 2;
      Thread.sleep(sleepTime);
      
      // If 2/3 of deck has been used, regenerate table of used cards
      if (cardCount >= (deckSize * 0.666)) {
        System.out.println("Reshuffling the deck.\n");
        Thread.sleep(sleepTime);
        playedCards = clearCards();
      }
      
      // If player runs out of money, game over and exit
      if (money <= 0) {
        System.out.println("\nYou ran out of money!");
        Thread.sleep(sleepTime);
        System.out.println("Game over!");
        Thread.sleep(sleepTime);
        System.out.println("\nExiting...");
        gameLoop = false;
      }
      
    } // End of game loop
  } // End of main class

  // Playing card representation
  public static class Card {
    int suit, ID, value;
    
    // Constructor
    // Suit IDs listed in global variables above
    // ID determines the number or face value
    public Card(int suit, int ID) {
      this.suit = suit;
      this.ID = ID;
      
      // If Ace, value is set to 11
      if (ID == 1) {
        value = 11;
        
      // If ID is a face card, value is 10
      } else if (ID > 10) {
        value = 10;
        
      // Otherwise, value is ID
      } else {
        value = ID;
      }
    }
    
    // Returns suit
    public int getSuit() {
      return suit;
    }

    // Returns ID
    public int getID() {
      return ID;
    }
    
    // Returns value
    public int getValue() {
      return value;
    }
    
    // Sets value of a card
    public void setValue(int newValue) {
      value = newValue;
    }

    // Returns face down string
    public String faceDown() {
      return "[(?)]";
    }
    
    // Returns card as a string
    public String toString() {
      // If Ace
      if (ID == 1) {
        
        // Return string with suit ID
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
        
      // If face cards
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
        
      // If ID is 10
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
        
      // All other number values
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
  } // End Card class
  
  // Generates a new card that hasn't been played yet using card table
  static Card genCard() {
    
    // Error message if out of cards, exits program
    if (cardCount >= deckSize) {
      System.err.println("Out of cards.");
      System.exit(1);
    }
    
    // Generates random cards and checks until a valid one is found
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
    
    // Add played card to card table
    playedCards[suite][ID]++;
    cardCount++;
    
    // Return the new card
    return new Blackjack.Card(suite, ID);
  }
  
  // Returns true if a card has been played already, used in genCard
  static boolean cardPlayed(int suite, int ID) {
    // Allows 6 of each card to be played
    if (playedCards[suite][ID] == 6) {
      return true;
    }
    return false;
  }
  
  // For Debugging - Prints the full card table
  static void printCardTable() {
    for (int i = 0; i < 4; i++) {
      for (int j = 1; j < 14; j++) {
          System.out.print(playedCards[i][j] + ", ");
      }
      System.out.println();
    }
  }
  
  // For Debugging - Generates a card, prints it, then prints card table
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
  
  // Choice between hit, stand, double
  static int choicePrompt() {
    char choice = ' ';
    System.out.println("What would you like to do?");
    
    // If round 1, double down available, otherwise not available
    if (playerCardCount == 2) {
      System.out.print("H = Hit, S = Stand, D = Double: ");
    } else {
      System.out.print("H = Hit, S = Stand: ");
    }
    
    // Take input and check for exit
    String userInput = scan.nextLine();
    if (userInput.equalsIgnoreCase("exit")) {
      System.out.println("\nExiting...");
      return(-1);
    }
    
    // Try to parse input
    try {
      choice = Character.toLowerCase(userInput.charAt(0));
      
    // Catch exception, recursively call choicePrompt
    } catch (Exception noString) {
      System.out.println("Error: You must specify a choice.\n");
      return(choicePrompt());
    }
    
    // Check choice selection
    switch(choice) {
      
      // Hit
      case 'h': {
        return(1);
      }
      case '1': {
        return(1);
      }
      
      // Stand
      case 's': {
        return(2);
      }
      case '2': {
        return(2);
      }
      
      // Double down
      case 'd': {
        
        // Check if first round
        if (playerCardCount == 2) {
          
          // Check if player can afford to double down
          if (bet * 2 <= money) {
            return(3);
            
          // Print error if not enough money and rerun choice prompt
          } else {
            System.out.println("Error: Invalid choice, you do not have enough money.\n");
            return(choicePrompt());
          }
          
        // If not first turn, print error and rerun choice prompt
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
  } // End of choice prompt
  
  // Prompt for bet
  static int betPrompt(int money) {
    System.out.print("How much would you like to bet? ");
    String userInput = scan.nextLine();
    
    // Check for exit
    if (userInput.equalsIgnoreCase("exit")) {
      System.out.println("\nExiting...");
      return(-1);
    }
    
    // Try to parse input
    try {
      int betProposal = (Integer.parseInt(userInput));
      
      // Check if they can afford it
      if (betProposal > money) {
        System.out.println("You don't have that much money!\n");
        return(betPrompt(money));
        
      // Disallow 0 or less
      } else if (betProposal < 1) {
        System.out.println("You cannot bet that amount!\n");
        return(betPrompt(money));
        
      // Bet accepted
      } else {
        System.out.println("Bet $"+betProposal+".");
        return(betProposal);
      }
      
    // Rerun prompt if needed
    } catch (Exception parseError) {
      System.out.println("Error: Input must be an integer!\n");
      return(betPrompt(money));
    }
  } // End of betPrompt
  
  // Adds up cards for player
  static int addPlayerCards(int cardCount) {
    int temp;
    
    // Count depends on number of cards played
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
        // Return totaled cards
        return temp;
      }
      default:
        System.out.println("FATAL ERROR");
        return 0;
    }
  } // End of add player cards
  
  // Add cards for dealer
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
  } // End of add dealer cards
  
  // Dealer hit process
  static int dealerHit(int playerTotal) throws InterruptedException {
    System.out.println("Dealer Hit!");
    Thread.sleep(sleepTime);
    
    // Print hand
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
    
    // Count cards
    dealerCardCount++;
    int dealerTotal = addDealerCards(dealerCardCount);
    
    // If dealer total is greater than player total, return it because dealer wins
    if (dealerTotal > playerTotal) {
      return(dealerTotal);
    }
    
    // Hit if less than or equal to 16
    if (dealerTotal <= 16) {
      return(dealerHit(playerTotal));
    } else {
      return(dealerTotal);
    }
  } // End dealer hit class
}
