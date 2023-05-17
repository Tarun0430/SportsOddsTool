import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Math;
import java.util.InputMismatchException;

public class Odds {

  public static void main(String[] args) {
    ArrayList<String> oddsList = new ArrayList<>();
    boolean addAnotherProp = true;
    while (addAnotherProp) {
      Scanner scanner = new Scanner(System.in);
      // Asks for players name and stores it
      System.out.println("Enter Player's Name:");
      String playerName = scanner.nextLine();
      System.out.println();
      // Asks for prop type and stores it
      System.out.println("Enter Prop Type:");
      String propType = scanner.nextLine();
      System.out.println();
      // Asks for what the line is at and stores it
      System.out.println("What is the line at?");
      double propLine = scanner.nextDouble();
      System.out.println();
      // Asks for the over odds and stores it
      System.out.println("Enter Odds For the Over:");
      int overOdds = 0;
      try {
        overOdds = scanner.nextInt();
      } catch (InputMismatchException e) {
        String overInput = scanner.next();
        if (overInput.equalsIgnoreCase("Even")) {
          overOdds = 100;
        } else {
          System.out.println("Enter valid input");
        }
      }
      System.out.println();
      // Asks for the under odds and stores it
      System.out.println("Enter odds for the under:");
      int underOdds = 0;
      try {
        underOdds = scanner.nextInt();
      } catch (InputMismatchException e) {
        String underInput = scanner.next();
        if (underInput.equalsIgnoreCase("Even")) {
          underOdds = 100;
        } else {
          System.out.println("Enter valid input");
        }
      }
      // Declares strings for the over and under (for return statement)
      String odds1Type = "over";
      String odds2Type = "under";
      // Declare variables for the different oddsType (for return statement)
      String higherOddsType = "";
      String lowerOddsType = "";
      if (overOdds < underOdds) {
        higherOddsType = odds1Type;
        lowerOddsType = odds2Type;
      } else if (underOdds < overOdds) {
        higherOddsType = odds2Type;
        lowerOddsType = odds1Type;
      } else if (underOdds == overOdds) {
        higherOddsType = odds2Type;
        lowerOddsType = odds1Type;
      }
      // Gets the percent value for the odds
      double odds1Percent = 0.0;
      if (overOdds < 0) {
        odds1Percent = NegativeOddsPercent(overOdds);
        // Test System.out.println(odds1Percent);
      } else if (overOdds > 0) {
        odds1Percent = PositiveOddsPercent(overOdds);
        // Test System.out.println(odds1Percent);
      }
      double odds2Percent = 0.0;
      if (underOdds < 0) {
        odds2Percent = NegativeOddsPercent(underOdds);
      } else if (underOdds > 0) {
        odds2Percent = PositiveOddsPercent(underOdds);
        // Test System.out.println(odds2Percent);
      }
      // Gets the true high odds without the house juice
      double trueHighOdds = truePercent(odds1Percent, odds2Percent);
      // Change this
      double trueLowOdds = 100 - trueHighOdds;
      trueHighOdds = Math.round(trueHighOdds * 100.0) / 100.0;
      trueLowOdds = Math.round(trueLowOdds * 100.0) / 100.0;
      System.out.println();
      // Prints out the higher odds first
      System.out.println("There is a " + trueHighOdds + "% chance for " + playerName + " going "
          + higherOddsType + " " + propLine + " " + propType);
      // Creates a resultString that will be used for ArrayList if user wants
      String resultString = "There is a " + trueHighOdds + "% chance for " + playerName + " going "
          + higherOddsType + " " + propLine + " " + propType;
      System.out.println();
      // Prints out the lower odds second
      System.out.println("There is a " + trueLowOdds + "% chance for " + playerName + " going "
          + lowerOddsType + " " + propLine + " " + propType);
      scanner.nextLine();
      System.out.println();
      // Asks if user wants to save prop (in array list)
      System.out.println("Would you like to save this prop? (Yes/No)");
      String propSaveResponse = scanner.nextLine();
      if (propSaveResponse.equalsIgnoreCase("yes")) {
        oddsList.add(resultString);
        if (oddsList.size() > 1) {
          oddsList = sortedList(oddsList);
        }
      }
      System.out.println();
      // Asks user if they want to see props (ArraList)
      System.out.println("Do you want to see your saved props? (Yes/No)");
      String propViewResponse = scanner.nextLine();
      if (propViewResponse.equalsIgnoreCase("yes")) {
        for (int i = 0; i < oddsList.size(); i++) {
          System.out.println(oddsList.get(i));
          System.out.println();
        }
      }
      System.out.println();
      // Asks the user if they want to go through the process again for another prop
      System.out.println("Do you want to add another prop? (Yes/No)");
      String propAddResponse = scanner.nextLine();
      addAnotherProp = propAddResponse.equalsIgnoreCase("yes");

    }
  }

  public static double NegativeOddsPercent(int negNumber) {
    double negValue = Math.abs((double) negNumber) / (100 + Math.abs((double) negNumber));
    return negValue;
  }

  public static double PositiveOddsPercent(int posNumber) {
    double posValue = 100 / (100 + (double) posNumber);
    return posValue;
  }



  public static double truePercent(double lowerPercent, double higherPercent) {
    double truehigherPercent = 0.0;
    if (lowerPercent < higherPercent) {
      truehigherPercent = higherPercent / (higherPercent + lowerPercent);
      truehigherPercent = truehigherPercent * 100;
      return truehigherPercent;
    } else {
      truehigherPercent = lowerPercent / (higherPercent + lowerPercent);
      truehigherPercent = truehigherPercent * 100;
      return truehigherPercent;
    }

  }

  public static ArrayList<String> sortedList(ArrayList<String> resultList) {
    int n = resultList.size();
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        String result1 = resultList.get(j);
        String result2 = resultList.get(j + 1);
        double percentage1 = Double.parseDouble(result1.split(" ")[3].replace("%", ""));;
        double percentage2 = Double.parseDouble(result2.split(" ")[3].replace("%", ""));
        if (percentage1 < percentage2) {
          resultList.set(j, result2);
          resultList.set(j + 1, result1);
        }
      }
    }
    return resultList;
  }


}

// Step 1 (Completed). Gives you the percent values of odds.


// Step 2. (Completed) Gives you the odds without the house juice. - Completed
// 1. Asks for the odds for the over and unders on the prop.
// 2. Converts both of them to percents
// 3. Gives the true odds for both.
// trueOverPercent = percentOver/(percentOver + percentUnder);
// trueUnderPercent = percentUnder/(percentOver + percentUnder);


// Step 3. (Completed) Asks for player name, asks for player prop type (options are given, user picks 1)
// asks for odds, stores odds in an array list, returns player name, prop type, and percentage value
// in the order of highest percent value. You can keep adding as many players as you want


// Step 4. Incorporates everything in step 2, except it also asks for the user to give their
// confidence percentage. //Need to figure out a way to incorporate that and give a new percentage


// Step 5. Make this a downloadable application
