/* ******************************************************
   Author: Okeke Nnadozie
   Date: 30/11/2014
   ID: 120584075
   About: program is a music quiz which can be played by multiple players. it asks each question in rounds, sequentially, player after player, before moving to the next round. It records their scores for each round and displays the sum of their scores for all rounds when the program is over.

   I/O implementation: Aim: if the program is terminated when it has gotten the number of players and usernames. it starts over from round one, and keeps starting over unless the players complete the game.
 
   *********************************************************************** */

import javax.swing.*;
import java.util.Random;
import java.io.*;



class minip8
{

   public static void main (String[] param)throws IOException
   {
	welcome();

	check_for_files();

	int h = check_for_players();
	final int quizlength = 4;

	String[] questions = new String [quizlength]; questions[0] = "Not used";
	String[] answers = new String [quizlength]; answers[0] = "Not used";
	String[] usernames = new String [h+1]; usernames[0] = "Not used";
	//JOptionPane.showMessageDialog(null, "Ive gotten here" + usernames[0]);
	 
	questions = array_of_questions(quizlength);
	answers = array_of_answers(quizlength);
	usernames = check_for_usernames(h); 

	int[] scores = askquestions(h, usernames, questions, answers);
	finalmessage(scores, usernames); 

	//print("about to wipe text files");
	wipe_or_create_textfile("howmany.txt","0");
	wipe_or_create_textfile("usernames.txt","k");
	 

	System.exit(0);

   }//end main





/* *****************************************************************************************************************
   method 1: check_for_files checks if there are any text files containing information about player numbers or names
   ***************************************************************************************************************** */
   public static void check_for_files() throws IOException
   {
	File players = new File("howmany.txt");

	if (players.exists() && !players.isDirectory()) 
	{ }
	
	else 
	{ wipe_or_create_textfile("howmany.txt", "0");}

	 
	File scores = new File("usernames.txt");

	if (scores.exists() && !scores.isDirectory()) 
	{ }
	
	else 
	{ wipe_or_create_textfile("usernames.txt", "k");}

	return;

   }





/* *********************************************************************************************************
   method 3: check_for_players checks existing textfiles to see if there is info about the number of players
   ********************************************************************************************************* */
   public static int check_for_players()throws IOException
   {
	BufferedReader instream = new BufferedReader (new FileReader("howmany.txt"));
	int firstvalue = Integer.parseInt(instream.readLine());
	instream.close();
	int h;
	
	if(firstvalue > 0) {h = firstvalue;}

	else {h = howmany();}
	
	PrintWriter outstream = new PrintWriter(new FileWriter("howmany.txt"));
	outstream.println(h);
	outstream.close();

	return h;

   }//end check_for_players





/* ********************************************************************************************************
   method 4: check_for_usernames checks existing textfiles to see if there is info about the names of players
   ******************************************************************************************************** */
   public static String[] check_for_usernames(int h)throws IOException
   {
	BufferedReader instream = new BufferedReader (new FileReader("usernames.txt"));
	//JOptionPane.showMessageDialog(null, "Im in check for usernames");

	String[] usernames = new String[h+1];
	usernames[0] = instream.readLine();
	//print("in username 0 is " + usernames[0]);
	
	if(usernames[0].equals("not used"))
	{ //print("im doing this");
  	  for(int i = 1; i < usernames.length; i++)
	  {usernames[i] = instream.readLine();}
	}

	else
	{/*print("im in the seconf if");*/instream.close();  usernames = username(h);}

	return usernames;

   }//end check_for_players






/* **********************************************************************************************************************
   method 2: wipe_or_createtextfile is used to create text files depending on the values provided. at the end of this 
             program, it is supplied values that essentially wipe any existing textfiles in preparation for starting the 
             program again.
   ********************************************************************************************************************* */
   public static void wipe_or_create_textfile(String filename, String initial_value)throws IOException
   {
	PrintWriter outstream = new PrintWriter(new FileWriter(filename));
	outstream.println(initial_value);
	//System.out.println(initial_value);
	outstream.close();
	return;
   
   }//end wipe_textfile	





/* *********************************************************
   method 5: welcome prints a welsome message to the screen
   ******************************************************** */
   public static void welcome()
   {
	String welcomemssg;
	welcomemssg =               "MM   MM      PPPPP\n";
	welcomemssg = welcomemssg + "M   M   M      P        P\n";
	welcomemssg = welcomemssg + "M         M      PPPPP\n";
	welcomemssg = welcomemssg + "M         M      P   \n";
	welcomemssg = welcomemssg + "M         M      P           \n\n";
	welcomemssg = welcomemssg + "Rules of Game \n";
	welcomemssg = welcomemssg + "============================================\n";
	welcomemssg = welcomemssg + "* Choose the correct answers to get a chance at rolling a dice\n\n";
	welcomemssg = welcomemssg + "* A diceroll from 1 to 5 gives you 3 points, a 6 gives you 6 points\n\n";
	welcomemssg = welcomemssg + "* Press OK to begin or continue this music quiz";
	JOptionPane.showMessageDialog(null,welcomemssg);

	return; 

   }//end welcomequestion





/* ***********************************************
   method 6: username gets the players user names
   *********************************************** */
   public static String[] username(int h) throws IOException
   {
	PrintWriter outstream = new PrintWriter(new FileWriter("usernames.txt"));

	String[] user_name = new String[h+1]; user_name[0]= "not used";
	outstream.println(user_name[0]);   

	for(int i = 1; i <= h; i++)  //This loop asks all the players for their names 
	{ 
	  user_name[i] = input("Player " + i + "  what is your name?");
	  outstream.println(user_name[i]); //the names are being stored in a textfile incase program is closed
	}
	
	outstream.close();

	return user_name;

   }//end usernames 





/* ****************************************************************************************************************
   method 7: finalmessage displays each players total score. <<<<<<<<<<<<<<<<work on this to have it display all at 
              once!!//done
   **************************************************************************************************************** */
   public static void finalmessage(int[] finalscore,String[] user_name)
   {
	String finalmessage = "Final scores\n";
	finalmessage = finalmessage + "=============================\n";
	
	sort(finalscore, user_name);
	for (int i = 1; i < finalscore.length; i++)
	{
	  finalmessage = finalmessage + user_name[i] + " you scored a total of " + finalscore[i] + " points.\n";
	  //print(finalmessage);
	}

	print(finalmessage);

	return;

   }//end finalmessage





/* ***********************************************************************************************
   method 8: sort sorts out the players scores from lowest to highest and return the sorted scores
   ************************************************************************************************ */
   static void sort(int[] score, String[] playername)
   {
	boolean sorted = false;
	while (!sorted)
	{
	  sorted = true;
	  
	  for(int i = 1; i < score.length - 1; i++)
	  {

	  if(score[i] > score[i+1])
	  {
	    String temp1; 
	    int tmp = score[i+1];
	    temp1 = playername[i+1];
	    score[i+1] = score[i];
	    playername[i+1] = playername[i];
	    score[i] = tmp;
	    playername[i] = temp1;
	    sorted = false;
	  }

	  }//end for loop

	}//end while loop

	return;
   
   }//end sort 





/* *****************************************************************************************************
   method 9: askquestions controls how the questions are asked in each round using a loop within a loop.  
   ***************************************************************************************************** */
   public static int[] askquestions(int h, String[] user_name, String[] question, String[] answer)
   {
	int[] score = new int[h+1]; score[0] = 0;
	String[] user_input = new String[h+1]; user_input[0] = "not used";
	
	int[] round_score = new int[h+1]; score[0] = 0;
 	int round = 1;

	while (round < question.length)
	{

	  for(int i = 1; i <= h; i++)  		  
	  {
	    user_input[i] = confirm(input("Round " + round + ": " + user_name[i] + " your turn\n\n" + question[round]),round,user_name[i],question[round]);
	
	    boolean result = check (user_input[i], answer[round]); 

	    round_score[i] = response(result, answer[round], user_name[i]);
	    score[i] = score[i] + response(result, answer[round], user_name[i]);	      
	  }
	  
	  display_roundscores(user_input, h,round,answer[round],round_score, user_name);
	  round = round + 1;
	}

	return score;
 
   }//end askquestions





/* **********************************************************************************************************************
   method 10: display_roundscores lists all the players scores after each round of play. these scores are not sorted :(, 
              only the final ones are sorted and a different method deals with those.
   ********************************************************************************************************************* */
   public static void display_roundscores(String[] userinput, int h, int round, String answer, int[] roundscore, String[] name) //<<<<<<<<include diceroll results by using if statements later, i'm tired :(
   {
	String message = "The answer to round " + round + " was: " + answer + "\n\n";
	message = message + "================================================\n";
	//sort(roundscore, name);

	for(int i = 1; i <= h; i++)
	{ 
	  if(userinput[i].equalsIgnoreCase(answer))
	  { message = message + name[i] + " you correctly chose " + userinput[i] + " . Your dice roll gave you a score of " + roundscore[i] + " points\n\n";
	  }

	  else if(!userinput[i].equalsIgnoreCase(answer))
	  { message = message + name[i] + " you wrongly chose " + userinput[i] + " . You missed your chance to roll the dice and got a score of " + roundscore[i] + " points\n\n";
	  }
	}
	
	print(message);
	return;

   }//end display_roundscores
 





/* **************************************************************************************
   method 11: response simply works out each players scores.
              /* before it used to say what the correct answer was and tell the player his/her score and their diceroll. 
                 but that part was blued out in favour of in favor of the scores being displayed by display_roundscores
   ************************************************************************************** */
   public static int response(boolean result, String answer, String name)
   {
	int score = 0;
	int diceroll;

	if (result == true)
	{
	  diceroll = randomgen();
	  score = score + score_calc(score, diceroll);
	  //print("The correct answer was " + answer + "\n\nCongratulations " + name + " you got it right! So you get " + score + " points." );
	}
 
	else if (result == false)
	{ 
	 // print("The correct answer was " + answer + "\n\nSorry " + name + " you got it wrong! So you get " + score + " points." );
	  score = score;
	}

	return score;

   }//end response





/* *********************************************
   method 12: print prints messages to the screen
   ********************************************* */
   public static void print(String message)
   {
	JOptionPane.showMessageDialog(null,message);

	return;

   }//end print





/* ***************************************************************************************************************
   method 13: printint prints integer messages to the screen <<<<I don't think I actually ever needed this method
   *************************************************************************************************************** */
   public static void printint(int message)
   {
	JOptionPane.showMessageDialog(null,message);

	return;

   }//end printint





/* *****************************************************************
   method 14: check confirms if the user's answer is correct or wrong. 
   ***************************************************************** */
   public static boolean check(String user_input, String questions_answer)
   {
	boolean validation = false;
	//String answer1 = question();

	if(user_input.equalsIgnoreCase(questions_answer))
	{validation = true;}

	else
	{}

	return validation;

   }//end check





/* *********************************************************
   method 15: input is short form of JOptio....InputDIalog...
   ********************************************************* */
   public static String input(String message)
   {
	String input = JOptionPane.showInputDialog(message);

	return input;

   }//end optionsinput





/* **********************************************************
   method 16: randomgen generates a random numbers from 1 to 6
   ********************************************************** */
   public static int randomgen()
   {
	Random dice = new Random();              
	int dicethrow = dice.nextInt(6)+1;       

	return dicethrow;

   }//end randomgen





/* ***************************************************************************************************************
   method 17: validate_input checks if the user's input can be parsed. if yes, it parses it and returns the parsed value.
              If no, it requests for a new input , and does so until that input can be parsed.
   **************************************************************************************************************** */
   public static int validate_input(String input)
   {
	boolean check = isNumeric(input);
	int result = 0;
	int ifwronginput = 0;

	if(check == true)
	{ 
	   result = Integer.parseInt(input);
	}

	else if (check == false)
	{
	  JOptionPane.showMessageDialog(null,"wrong input");
	  ifwronginput= validate_input(input("How many players will there be?"));
	  result = ifwronginput;
	}

	if(result < 1 )
	{
	  JOptionPane.showMessageDialog(null,"wrong input");
	  ifwronginput= validate_input(input("How many players will there be?"));
	  result = ifwronginput;
	}

	else
	{}

	return result;

   }//end validate_input





/* ********************************************************************************
   method 18: howmany finds out how many people will be taking the quiz at a time
   ****************************************************************************** */	 
   public static int howmany()
   {
	int player_number = validate_input(input("How many players will there be?"));
	return player_number;

   }//end howmany





/* ********************************************************************************
   method 19: isNumeric checks if a string of values can be changed into an integer
   ******************************************************************************** */
   public static boolean isNumeric(String str)  
   {  
	try  
	{  
	double d = Double.parseDouble(str);  
	}  

	catch(NumberFormatException nfe)  
	{  

	return false;  
	}  

	return true;  

   }//end isNumeric





/* ******************************************************************************************************
   method 20: score_calc calculates a player's new score depending on the current score and the dice roll
   ****************************************************************************************************** */
   public static int score_calc(int score, int diceroll)
   {
	if (diceroll >=1 & diceroll <=5)
	{score = score + 3;}

	else if (diceroll == 6)
	{score = score + 6;}

	return score;	
	  
   }//end score_calc





/* ***************************************************************************************************
   method 21: array_of_questios contains all the questions this program uses. more can be added easily
   **************************************************************************************************** */
   public static String[] array_of_questions(int number_of_questions)
   {
	String[] questions = new String [number_of_questions];
	questions[0] = "Not used";

	questions[1] = "How much does listening to music while working out improve physical performance?\n\n";
	questions[1] = questions[1] + "a) Not by much\nb) By a little margin\nc) Music helps tremendously\nd) It doesnt help at all";

	questions[2] = "How much did Warner Music collect in royalties in the year 2001?\n\n";
	questions[2] = questions[2] + "a) $2million\nb) $4million dollars\nc) $8 million\nd) $3.5 million";

	questions[3] = "Which of the Beatles could read music?\n\n";
	questions[3] = questions[3] + "a) None of them\nb) Bambble and schabgy\nc) Dangledoo, loely and dumby\nd) All of them";

	return questions;

   }//end array_of_questions





/* *************************************************************************************************************
   method 23: array_of_answers contains all the answers to all questions asked. more answers can be added easily
   ************************************************************************************************************* */
   public static String[] array_of_answers(int number_of_answers)
   {
	String[] answers = new String [number_of_answers];
	answers[0] = "Not used";
	answers[1] = "c";
	answers[2] = "d";
	answers[3] = "a";

	return answers;

   }//end array_of_questions





/* *********************************************************************************************************************
   method 24: confirm checks to see that the input for a players answer is one of the letters, a, b, c, or d. if it isnt 
              it asks for the question again, and does so until the player chooses one of the options a, b, c or d
   ********************************************************************************************************************* */
   public static String confirm( String userinput, int round, String name, String question)
   {
	if (!userinput.equalsIgnoreCase("a")&!userinput.equalsIgnoreCase("b")&!userinput.equalsIgnoreCase("c")&!userinput.equalsIgnoreCase("d"))
	{ 
	  print("Wrong Input. Only A, B, C, D are allowed");
	  userinput = confirm(input("Round" + round + ": " + name + " your turn\n\n" + question),round,name,question);
	}

	else
	{}

	return userinput;

   }//end confirm


}//end class minip8
