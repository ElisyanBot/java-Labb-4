package labb4v2;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class TollFeeCalculator {
  Scanner sc;
  String[] dateStrings;
  LocalDateTime[] dates;

  public TollFeeCalculator(String inputFile) {
      setDateString(inputFile);
      setDates(dateStrings);
      System.out.println("The total fee for the inputfile is " + getTotalFeeCost(dates));
  }

  public void setDateString(String inputFile){
    try {
    this.sc = new Scanner(new File(inputFile));
    this.dateStrings = sc.nextLine().split(", ");
    }  catch (IOException e) {
      System.err.println("Could not read file " + new File(inputFile).getAbsolutePath());
    }
  }

  public void setDates(String[] stringDateArr){
    this.dates = new LocalDateTime[stringDateArr.length]; // bugg (-1)
    for (int i = 0; i < dates.length; i++) {
      dates[i] = LocalDateTime.parse(dateStrings[i], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
  }

  public int getTotalFeeCost(LocalDateTime[] dates) {
    int totalFee = 0;
    LocalDateTime intervalStart = dates[0]; 

    for (LocalDateTime date : dates) {

      long diffInMinutes = intervalStart.until(date, ChronoUnit.MINUTES);

      if (diffInMinutes > 60) { // bugg
        System.err.println(date);
        totalFee += getTollFeePerPassing(date); //(bugg)?????
      }
      
      intervalStart = date; //bug
    }
    
    //test that return value is smaller then 60 
    return Math.min(totalFee, 60); // bug
  }

  //todo: skicka in tider texttider som testar lite olika varianter av denna.
  //todo: kolla så att det blir rätt räknat när man skickar in "lab4.txt"
  public static int getTollFeePerPassing(LocalDateTime date) {
    if (isTollFreeDate(date))
      return 0;
    int hour = date.getHour();
    int minute = date.getMinute();
    if (hour == 6 && minute >= 0 && minute <= 29)
      return 8;
    else if (hour == 6 && minute >= 30 && minute <= 59)
      return 13;
    else if (hour == 7 && minute >= 0 && minute <= 59)
      return 18;
    else if (hour == 8 && minute >= 0 && minute <= 29)
      return 13;
    else if (hour == 8 && minute >= 30 && minute <= 59) // tillaggd en halvtimme för att nästa logik inte ska ta
                                                        // 8:30-9:00, 9:30-10:00 osv.
      return 8;
    else if (hour >= 9 && hour <= 14 && minute >= 0 && minute <= 59)
      return 8;
    else if (hour == 15 && minute >= 0 && minute <= 29)
      return 13;
    else if (hour == 15 && minute >= 30 && minute <= 59) //samma problem som innan
      return 18;
    else if (hour == 16 && minute >= 0 && minute <= 59) 
      return 18;
    else if (hour == 17 && minute >= 0 && minute <= 59)
      return 13;
    else if (hour == 18 && minute >= 0 && minute <= 29)
      return 8;
    else
      return 0;
  }

  public static boolean isTollFreeDate(LocalDateTime date) {
    return date.getDayOfWeek().getValue() == 6 || date.getDayOfWeek().getValue() == 7
        || date.getMonth().getValue() == 7;
  }

  public static void main(String[] args) {
    new TollFeeCalculator("app/src/test/java/labb4v2/Lab4.txt");
  }
}
