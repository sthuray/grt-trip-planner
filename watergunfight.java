import java.util.Scanner;

class Main {

  /*  instOutputter

      This method outputs and formats the instructions

      Paramenters: None

      Returns: Void
  
  */
  public static void instOutputter () {
    
    //Outputs and formats the instructions
    System.out.println("");
    System.out.println("Water Gun Fight - A 'Wild, Wild, West' hand-game variation");
    System.out.println("Choose to either shoot, pump your water gun, or defend. You will be playing against the computer.");
    System.out.println("Every 2 pump turns, your water gun gains a tier of pressure.");
    System.out.println("Every 4 defense turns, you find better cover and gain a tier of defense.");
    System.out.println("Pressure tiers decrease while you are defending and vice versa.");
    System.out.println("Pressure/defense tiers reset when the player takes a shot or when a new game starts.");
    System.out.println("Higher tiers always beat a lower tiers.");
    System.out.println("If a pressure tier shoots a defense tier of the same number, there is a 1/5 chance that the shot ricochets back at the shooter.");
    System.out.println("First player to successfully shoot their opponent wins.");
    System.out.println("");
    System.out.println("Shoot = 1");
    System.out.println("Pump = 2");
    System.out.println("Defend = 3");
  }

  /*  getGameAmnt

      The method determines the amount of games by using user input

      Paramenters: None

      Returns: int
  
  */
  public static int getGameAmnt () {
    
    //Initializes scanner
    Scanner keyedInput = new Scanner(System.in);

    //declares integer 
    int gameAmnt;

    //Prompts user for number of games they would like to play
    System.out.println("");
    System.out.println("Enter the amount of games (integer) you would like to play:");
    
    //Gets user's desired number of games
    gameAmnt = keyedInput.nextInt();

    //returns amount of games
    return gameAmnt;
  }

  /*  getUserChoice

      This method determines the user's choice by using user input

      Paramenters: double[]

      Returns: int
  
  */
  public static int getUserChoice (double[] amount) {
    
    //initializes scanner
    Scanner keyedInput = new Scanner(System.in);

    //declares variables
    int choice;             //user's integer choice
    boolean isPumped;       //if their water gun is pumped or not

    //Determines whether or not the user's water gun is pumped
    if (amount[0] > 0) {
        isPumped = true;
    } else {
        isPumped = false;
    }

    //Outputs prompt with formatting
    System.out.println("--------------------------------------------------");
    System.out.println("Enter your choice:");

    //gets user's choice
    choice = keyedInput.nextInt();

    //prompts user for choice again if they try to shoot when they haven't pumped their gun
    while (choice == 1 && isPumped == false) {
      System.out.println("");
      System.out.println("Your water gun is not pumped. Try again after pumping. Choose either pump(2) or defend(3).");
      choice = keyedInput.nextInt();
    }

    //if choice is not 1-3

    while (choice != 1 && choice != 2 && choice != 3) {
        System.out.println("");
        System.out.println("Please enter either 1(shoot), 2(pump), or 3(defend).");
        choice = keyedInput.nextInt();
    }

    //outputs your choice
    switch (choice) {
    //if you shoot
    case 1:
      System.out.println("--------------------------------------------------");
      System.out.println("YOU      - SHOOT");
      break;

    //if you pump
    case 2: 
      System.out.println("--------------------------------------------------");
      System.out.println("YOU      - PUMP");
      break;

    //if you defend
    case 3:
      System.out.println("--------------------------------------------------");
      System.out.println("YOU      - DEFEND");
      break;

    default:
      break;
    }

    //returns your choice 
    return choice;
  }

  /*  getCompChoice

      This method determines the computer's choice by finding a random number

      Paramenters: double[]

      Returns: int
  
  */
  public static int getCompChoice (double[] amount) {
    
    //declares variables
    int choice;           //user's integer choie
    boolean isPumped;     //whether the water gun is pumped once

    //checks if the water gun has been pumped 
    if (amount[2] > 0) {
        isPumped = true;
    } else {
        isPumped = false;
    }

    //Picks a random number from 1-3 as the computer's choice
    choice = (int) Math.round(Math.random()*2+1);
    
    //if the computer tries to shoot and the gun isn't pumped, it will choose another choice
    while (choice == 1 && isPumped == false) {
      choice = (int) Math.round(Math.random()*2+1);
    }

    //outputs the choice of the computer
    switch (choice) {

    //if the computer shoots
    case 1:
      System.out.println("Computer - SHOOT");
      System.out.println("");
      break;
      
    //if the computer pumps 
    case 2:
      System.out.println("Computer - PUMP");
      System.out.println("");
      break;

    //if the computer defends  
    case 3:
      System.out.println("Computer - DEFEND");
      System.out.println("");
    }

    //returns the computer's choice
    return choice;
  }

  /*  getPumpDefAmount

      This method determines the user's/computer's pressure amount and defense amount. Saves the largest number of each amount as well.

      Paramenters: int, int, double[]

      Returns: double[]
  
  */
  public static double[] getPumpDefAmount (int userChoice, int compChoice, double[] amount) {

    //Declares the final value that pumping/defending increases/decreases
    final double PUMP_AMNT = 0.5;
    final double DEF_AMNT = 0.25;

    //The user's defense/pressure increases or decreases depending on their choice
    switch (userChoice) {

    //If user pumps
    case 2:

        //Increases user pressure
        amount[0] += PUMP_AMNT;

        //If user def is greater than 0
        if (amount[1] > 0) {
            amount[1] -= DEF_AMNT;
        }
        
        //Saves user total if it is the greatest reached
        if (amount[0] > amount[4]) {
            amount[4] = amount[0] / (amount[0] % 1);
        }
        break;

    //If user defends
    case 3:

        //Increases user def
        amount[1] +=  DEF_AMNT;

        //If user pressure is greater than 0
        if (amount[0] > 0) {
            amount[0] -= PUMP_AMNT;
        }

        //Saves user total if it's the greatest reached
        if (amount[1] > amount[5]) {
            amount[5] = amount[1] / (amount[1] % 1);
        }
        break;
        
    default:
        break;
    }

    switch (compChoice) {

    //if computer pumps
    case 2:

        //Increases computer pressure
        amount[2] += PUMP_AMNT;
        
        //If computer defense is greater than 0
        if (amount[3] > 0) {
            amount[3] -= DEF_AMNT;
        }

        //If it's the greatest comp pressure, saves the value
        if (amount[2] > amount[6]) {
            amount[6] = amount[2] / (amount[2] % 1);
        }
        break;

    //if computer defends
    case 3:

        //Increases computer def
        amount[3] +=  DEF_AMNT;

        //If computer pressure is greater than 0
        if (amount[2] > 0) {
            amount[2] -= PUMP_AMNT;
        }

        //Saves value if it is the greatest computer defense
        if (amount[3] > amount[7]) {
            amount[4] = amount[3] / (amount[3] % 1);
        }
        break;
        
    default:
        break;
    }

    //returns the amount of pressure
    return amount;
  }

  /*  getTier

      This method determines the tier of pressure/defense for the user/computer using the pressure/defense amounts. Also outputs it for the user. 

      Paramenters: double[]

      Returns: int[]
  
  */
  public static int[] getTier (double[] amount) {
    
    //tier[0] = pressure tier(user)
    //tier[1] = defense tier(user)
    //tier[2] = pressure tier(computer)
    //tier[3] = defense tier(computer)
    int [] tierTypes = new int[4];

    /*determine user pressure tier, then defense tier
     *outputs a message if the tier increases
     */
    if (amount[0] > 0) {
        tierTypes[0] = (int) amount[0] - ((int) amount[0] % 1);
        if (amount[0] % 1 == 0) {
            System.out.println("User pressure tier + 1");
        }
    } else if (amount[1] > 0) {
        tierTypes[1] = (int) amount[1] - ((int) amount[1] % 1);
        if (amount[1] % 1 == 0) {
            System.out.println("User defense tier + 1");
        }
    } else {
    }

    /*determines computer pressure tier, then defense tier
     outputs a message if the tier increases
    */
    if (amount[2] > 0) {
        tierTypes[2] = (int) amount[2] - ((int) amount[2] % 1);
        
        if (amount[2] % 1 == 0) {
            System.out.println("Computer pressure tier + 1");
        }
    } else if (amount[3] > 0) {
        tierTypes[3] = (int) amount[3] - ((int) amount[3] % 1);
        if (amount[3] % 1 == 0) {
            System.out.println("Computer defense tier + 1");
        }
    } else {
    }
    
    //Outputs the tier amount for the user
    System.out.println("");
    System.out.println("User pressure tier - " + tierTypes[0]);
    System.out.println("User defense tier - " + tierTypes[1]);
    System.out.println("Computer pressure tier - " + tierTypes[2]);
    System.out.println("Computer defense tier - " + tierTypes[3]);
    System.out.println("");

    //returns the tiers 
    return tierTypes;
  }

  /*  winChecker

      This method determines whether or not the move results in a win, tie or loss. It outputs a message and saves the value for each circumstance.

      Paramenters: int, int, int[], int[]

      Returns: int[]
  
  */
  public static int[] winChecker (int userChoice, int compChoice, int[] tiers, int[] scoreVal) {

    //declares variables
    int ricochet = 3;             //number used to determine if ricochet happens
    int randNum;                  //random number

    /*outputs a message depending on whether you win, draw, or tie
    also saves the result
    */
    //if the user shoots
    if (userChoice == 1) {

        //if the computer shoots
        if (compChoice == 1) {

            //if the user has a greater pressure tier, wins increase
            if (tiers[0] > tiers[2]) {

                scoreVal[0]++;

                System.out.println("Your superior water pressure burst through the enemy's puny squirt of water!");
                System.out.println("WIN");

            //if they have the same pressure tier, draws increase
            } else if (tiers[0] == tiers[2]) {

                scoreVal[1]++;

                System.out.println("You and your enemy's powerful streams of water could not surpass one another.");
                System.out.println("DRAW");

            //if the computer has a greater pressure tier, losses increase    
            } else {

                scoreVal[2]++;

                System.out.println("Your pathetic water pressure was burst through by the enemy's shot.");
                System.out.println("LOSS");
            }
        
        //if the computer pumps, wins increase
        } else if (compChoice == 2) {

            scoreVal[0]++;
            
            System.out.println("You caught your opponent while they weren't paying attention!");
            System.out.println("WIN");

        //if the computer defends
        } else if (compChoice == 3) {

            //if the computer has a weaker defense tier than the user's pressure tier, wins increase
            if (tiers[0] > tiers[3]) {

                scoreVal[0]++;

                System.out.println("Your superior water pressure burst through the enemy's puny defenses!");
                System.out.println("WIN");

            //if the computer has the same defense tier as the user's pressure tier
            } else if (tiers[0] == tiers[3]) {

                //finds a random number between 1 and 5
                randNum = (int) Math.round(Math.random()*4+1);

                //if that number is the ricochet number, losses increase (shot ricochets)
                if (randNum == ricochet) {

                    scoreVal[2]++;

                    System.out.println("Your shot was deflected right back at you by your enemy's defense!");
                    System.out.println("LOSS");
                
                //Otherwise, draws increase
                } else {

                    scoreVal[1]++;

                    System.out.println("Your shot was defended by the enemy successfully.");
                    System.out.println("DRAW");
                }
            //draws increase otherwise
            } else {

                scoreVal[1]++;

                System.out.println("Your pathetic water pressure didn't even manage to make a dent in the enemy's defenses.");
                System.out.println("DRAW");
            }
        }
    //if the user pumps
    } else if (userChoice == 2) {

        //if the computer shoots, losses increase
        if (compChoice == 1) {

            scoreVal[2]++;

            System.out.println("Your opponent caught you while you weren't paying attention!");
            System.out.println("LOSE");
        
        //otherwise draws increase
        } else {

            scoreVal[1]++;

            System.out.println("Neither player has made a move toward the other.");
            System.out.println("DRAW");
        }
    
    //if the user defends
    } else if (userChoice == 3) {

        //if the computer shoots
        if (compChoice == 1) {

            //if the computer's pressure tier is higher than the user's defense tier, losses increase
            if (tiers[2] > tiers[1]) {

                scoreVal[2]++;

                System.out.println("Your enemy's superior water pressure burst through your  puny defenses!");
                System.out.println("LOSE");
            
            //if the tier is the same as the computer's tier
            } else if (tiers[2] == tiers[1]) {

                //determines a random number from 1 to 5
                randNum = (int) Math.round(Math.random()*4+1);

                //if that number is the ricochet number, wins increase
                if (randNum == ricochet) {

                    scoreVal[0]++;

                    System.out.println("The enemy's shot was deflected right back at them by your defense!");
                    System.out.println("WIN");

                //otherwise draws increase
                } else {

                    scoreVal[1]++;

                    System.out.println("The enemy's shot was defended by you successfully.");
                    System.out.println("DRAW");
                }
            
            //otherwise, draws increase
            } else { 
                
                scoreVal[1]++;

                System.out.println("Your enemy's pathetic water pressure didn't even manage to make a dent in your defenses.");
                System.out.println("DRAW");
            }
        
        //otherwise, draws increase
        } else {

            scoreVal[1]++;

            System.out.println("Neither player has made a move toward the other.");
            System.out.println("DRAW");
        }
    }

    //returns the number of wins, losses, and draws
    return scoreVal;
  }

  /*  resultOutputter

      This method outputs the results for all of the games. 

      Paramenters: int[], double[]

      Returns: void
  
  */
  public static void resultOutputter (int[] score, double[] amount) {
    
    //determines win percentage
    double winPerc = 100 * ((double) score[0] / ((double) score[0] + (double) score[2]));

    //declares integers for the highest tiers reached
    int userPresTier = (int) amount[4];
    int userDefTier = (int) amount[5];
    int compPresTier = (int) amount[6];
    int compDefTier = (int) amount[7];

    //Outputs results with formatting
    System.out.println("--------------------------------------------------");
    System.out.println("");
    System.out.println("Wins: " + score[0]);
    System.out.println("Draws: " + score[1]);
    System.out.println("Losses: " + score[2]);
    System.out.println("");
    System.out.println("Win Percentage: " + winPerc + "%");
    System.out.println("");
    System.out.println("Your greatest pressure tier: " + userPresTier);
    System.out.println("Your greatest defense tier: " + userDefTier);
    System.out.println("");
    System.out.println("The enemy's greatest pressure tier: " + compPresTier);
    System.out.println("The enemy's greatest defense tier: " + compDefTier);
  }

  
  public static void main(String[] args) {
      
      //declares variables and arrays
      double [] pumpDefAmnt = new double[8];    //values for pressure and defense
      int [] score = new int[3];                //win, lose, tie values
      int [] tiers = new int[4];                //tiers
      int userChoice;                           //user's choice
      int compChoice;                           //computer's choice
      int numOfGames;                           //number of games
      int beforeWin;                            //before winning/losing, game number

      instOutputter();

      //determines number of games
      numOfGames = getGameAmnt();

      //this will run while the number of games doesn't equal the user's desired amount
      while (score[0] + score[2] != numOfGames) {

          //determines game number
          beforeWin = score[0] + score[2];

          //get's user's choice
          userChoice = getUserChoice(pumpDefAmnt);

          //get's computer's choice
          compChoice = getCompChoice(pumpDefAmnt);

          //get's the pressure amount and defense amount for each player
          pumpDefAmnt = getPumpDefAmount(userChoice, compChoice, pumpDefAmnt);

          //gets the tier of each player
          tiers = getTier(pumpDefAmnt);

          //checks if the user won and gets the results
          score = winChecker(userChoice, compChoice, tiers, score);
          
          //resets pressure and defense if it is a new game
          if (score[0] + score[2] > beforeWin) {
              pumpDefAmnt[0] = 0;
              pumpDefAmnt[1] = 0;
              pumpDefAmnt[2] = 0;
              pumpDefAmnt[3] = 0;

              //outputs the game number if it is under/equal to the # of games
              if ((score[0] + score[2] + 1) <= numOfGames) {
                  System.out.println("");
                  System.out.println("");
                  System.out.println("GAME " + (score[0] + score[2] + 1));
                  System.out.println("");
              }
          }
      }  

      //outputs results
      resultOutputter(score, pumpDefAmnt);

      //outputs a farewell message
      System.out.println("");
      System.out.println("Farewell!");
  }
}
