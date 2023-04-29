/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.lukasz.rak.model;
import java.util.*;

/**
 *  The class used for handling the encryping and decrypting operations of the application.
 * @author Łukasz Rak
 * @version FINAL-5
 */
public class CipherModel {
    
/**
* The encryption key used for creating the 6x6 Polybius Square matrix.
*/
private String keyPolybiusSquare;
/**
* The encryption key used for numbering the columns for further message encryption.
*/
private String keyColumns;
    
/**
* The message used for encrypting or decrypting.
*/
private String message;
    
/**
* The array used for checking the position of a letter in the alphabet matrix.
*/
char matrixPos[]={'A','D','F','G','V','X'};

/**
* The matrix used for encrypting or decrypting.
*/    
final private char[][] polybiusSquare = new char[6][6];

/**
* The private method for removing spaces and making all the letters uppercase from the keys and message.
*/ 
private void modifyInputData()
{
    this.keyColumns = this.keyColumns.replaceAll("\\s", "");
    this.keyColumns = this.keyColumns.toUpperCase();
    
    this.keyPolybiusSquare = this.keyPolybiusSquare.replaceAll("\\s", ""); //in case the user enters lower case letters
    this.keyPolybiusSquare = this.keyPolybiusSquare.toUpperCase();
    
    this.message = this.message.replaceAll("\\s", "");
    this.message = this.message.toUpperCase();
}
/**
* Encrypts the message using a key and updates the message field to the store encrypted one.
     * @throws pl.polsl.lukasz.rak.model.IncorrectInput
*/  
public void encrypt() throws IncorrectInput{
    
    //INPUT DATA VALIDATION
    modifyInputData();   
    
     if(validateInputData(this.keyColumns) == false)
        throw new IncorrectInput("The input data is incorrect!");  
    if(validateInputData(this.keyPolybiusSquare) == false)
        throw new IncorrectInput("The input data is incorrect!");
    if(validateInputData(this.message) == false)
        throw new IncorrectInput("The input data is incorrect!");
    
    generatePolybiusSquare(); //generate the alphabet matrix
    

    char currentLetter;
    
    //do the transformation to the intermediate text
    String intermediateText = ""; //empty string
    
    for(int i = 0; i < message.length();i++) //for every letter of the message
    {
       currentLetter = message.charAt(i);
        //find the letter in the polybius matrix -> iterate through it
            
        for(int a = 0; a < polybiusSquare.length;a++)  
        {
            for(int b = 0; b < polybiusSquare[0].length;b++)
            {
                if(currentLetter == polybiusSquare[a][b])
                {
                    //append the encoded word to string
                    String encodedLetter;
                    encodedLetter = String.valueOf(matrixPos[a])+ matrixPos[b];          
                    intermediateText = intermediateText + encodedLetter;
                }
            }
        }
    }
    //after we get the intermediate text we need the column key to rearange the words
    //generate the second matrix used for encrypting the message
    
     int columns = this.keyColumns.length();
     //int rows = (int)Math.ceil(intermediateText.length()/this.keyColumns.length());
     
     int rows = 1;
     
     while((rows*columns) < intermediateText.length())
     {
         rows++;
     }
    // System.out.println("debug intermediate size: " + intermediateText.length()); //debug
     // System.out.println("matrix size: " + rows + columns); //debug
      
    int stringIndex = 0;
    char[][] encryptionMatrix = new char[rows][columns];
    
        for(int b = 0; b < rows; b++) 
        {
            for(int a = 0; a < columns; a++)
            {  
                if(stringIndex < intermediateText.length())
                 encryptionMatrix[b][a] =  intermediateText.charAt(stringIndex);
                
                 stringIndex++; 
                 
                // System.out.println("debug: " + stringIndex); //debug
            }
        }
        
        //now we read the letters (as columns) in the alphabetical order of the letters in the columns key
    String encryptionResult = "";
    
    
    int[] order = new int[this.keyColumns.length()];
    
    for(int counter = 0; counter < this.keyColumns.length();counter++)
    {
        order[counter] = ((int)this.keyColumns.charAt(counter)-48);
    }
   
    for(int b = 0; b < columns; b++) 
        {
             int columnIndex = columnsCopyingOrder(order);
            for(int a = 0; a < rows; a++)
            { 
               if(encryptionMatrix[a][columnIndex] != 0)
               encryptionResult += encryptionMatrix[a][columnIndex];
            }
            order[columnIndex] = 91; // Z is the last letter I use in ASCII - 90)
        }
    
    this.message = encryptionResult;
}

/**
* This method is used inside the decrypt method for finding the index of the smallest element.
* 
* @param array to find the index of smallest item in.
* @return index of the smallest item.
*/  
private int columnsCopyingOrder(int[] array){      
    int index = 0;
    for (int i = 1; i < array.length; i++) {
        if (array[i] < array[index]) 
            index = i; 
    }
 return index;  
}

/**
* Checks whether the input data is correct.
* @param toValidate string to validate
*/  

private boolean validateInputData(String toValidate)
{
    
    char[] toValidateArray = toValidate.toCharArray();
    for (char currentChar : toValidateArray) // use of for each loop
        {        

            if(Character.isDigit(currentChar))
            {
                if (!(currentChar >= '0' && currentChar <= '9'))
                {
                    System.out.println("failed! :(" + currentChar);
                    return false;                  
                }
            }
            else
            {
                
                if(!((currentChar >= 'A' && currentChar <= 'Z')))
                {                 
                   System.out.println("failed! :(" + currentChar);
                   return false;
                }
            }
        }  
    return true;
}

/**
* Checks whether the input message is correct.
* @param toValidate string to validate
*/  

private boolean validateInputEncryptedMessage(String toValidate)
{
    
    char[] toValidateArray = toValidate.toCharArray();
    for (char currentChar : toValidateArray) // use of for each loop
        {        
                if(!((currentChar >= 'A' && currentChar <= 'Z')))
                {                 
                   System.out.println("failed! :(" + currentChar);
                   return false;
                }
           
        }  
    return true;
}
/**
* Decrypts the message using a key and updates the message field to the store decrypted one.
     * @throws pl.polsl.lukasz.rak.model.IncorrectInput
*/    
public void decrypt()throws IncorrectInput{
    
    //INPUT DATA VALIDATION 
    
   
    modifyInputData();
      
    if(validateInputData(this.keyColumns) == false)
        throw new IncorrectInput("The input data is incorrect!");
    if(validateInputData(this.keyPolybiusSquare) == false)
        throw new IncorrectInput("The input data is incorrect!");
    if(validateInputEncryptedMessage(this.message) == false)
        throw new IncorrectInput("The input data is incorrect!"); 
        
     generatePolybiusSquare();
    //To decrypt the message first we undo the columnar  transposition.
    //After that we use the Polybius Square matrix to decode the message.
    
    //we read the whole message letter by letter
    //then we fill it back to the matrix using the correct column key (correct order)
    
    int columns = this.keyColumns.length();
    
    int rows = 1;
     
     while((rows*columns) < message.length())
     {
         rows++;
     }
     
    char[][] decryptionMatrix = new char[rows][columns];
    int[] order = new int[this.keyColumns.length()];
    int index = 0;
    char currentLetter;     
    
    if((columns*rows) != message.length())
    {
        for(int i = 0;i < ((columns*rows)-message.length());i++)
        {
            decryptionMatrix[(rows-1)][(columns-1-i)] = 'x'; //these places should remain empty for matrix to be correct
        }
    }
    
     for(int counter = 0; counter < this.keyColumns.length();counter++)
    {
        order[counter] = ((int)this.keyColumns.charAt(counter)-48); //addint the letters from the columns key to the array of order
    }
    
   
    for(int a = 0; a < columns; a++)
    {
        int columnIndex = columnsCopyingOrder(order);
        
        for(int b = 0; b < rows; b++)
        {   
             // I find the correct column and then fill it with cipher text one by one    
            
                if(decryptionMatrix[b][columnIndex] == 'x')
                {
                 break;
                }
                     
                if(!(index >= this.message.length()))
                {
                currentLetter = this.message.charAt(index);
                decryptionMatrix[b][columnIndex] =  currentLetter;
                }   
                         
                 index++;     
     
        }
        order[columnIndex] = 91; // Z is the last letter I use in ASCII - 90)  
    }
    
    //Now when we have the matrix with the reversed transposition we can
    //read it letter by letter and decode it with the alphabet matrix
    
    String intermediateText = "";
    
        for(int b = 0; b < rows; b++) 
        {
            for(int a = 0; a < columns; a++)
            {  
              intermediateText += decryptionMatrix[b][a];
            }
        }
        
    //Now we have the intermediateText so the last thing left is using the Polybius Square matrix
    
    String decodedText = "";
       
    //creating a hash map for decrypting the alphabet
    HashMap<String , Character> alphabetMatrix = new HashMap<>(); //use of collection
   
    for(int a = 0; a < polybiusSquare.length;a++)  
        {         
            for(int b = 0; b < polybiusSquare[0].length;b++)
            {             
                 String keyA = String.valueOf(matrixPos[a])+ matrixPos[b];
                 char value = polybiusSquare[a][b];
                 alphabetMatrix.put(keyA, value);
            }       
        }
    
    //Decrypting the message
    
     String keyForMap;
     
        for(int i = 0;i+1 < intermediateText.length();i = i+2)
        {
            keyForMap = String.valueOf(intermediateText.charAt(i))+intermediateText.charAt(i+1);
            
            if(alphabetMatrix.get(keyForMap) != null)
            decodedText = decodedText + alphabetMatrix.get(keyForMap);   
        }
        
        this.message = decodedText;
}   

/**
*Generates the PolybiusSquare matrix using the proper key.
*/    
private void generatePolybiusSquare(){
    
   //Containers for temporary storage of already used letters and numbers
   Vector<Character> usedLetters = new Vector<>(); //use of collection
   int row = 0;
   int index = 0;
   boolean detected = false;
   
    //A loop for inserting firstly the key to the matrix without repeating letter
    //and then inserting the rest of alphabet as well as numbers from 0-9
    //adding up to 36 spaces. (6x6 matrix)
  
    
    for(int i = 0; i < this.keyPolybiusSquare.length();i++) //for every letter of the key insert it into the matrix
    {  
       char currentLetter = keyPolybiusSquare.charAt(i);   
         
        if(index == 6) //we reach the limit of 6 elements in a row
       {
          row++;
          index = 0;
       }
        if (Character.isWhitespace(currentLetter)) //omit whitespace characters
          break;
               
       for(char letter : usedLetters) // for each letter check if it has been already inserted to the matrix <-- use of for each loop
       {          
            if(currentLetter ==  letter)
            {
                detected = true;
                break;
            }
       }
       
       if((!detected)) //ignoring whitespaces and repetitions of letters
       {
        usedLetters.add(currentLetter);
        this.polybiusSquare[row][index] = currentLetter;
        detected = false;
        index++;
       }  
       else
           detected = false;         
    }   
       
    for(int i = 0; i<26; i++) //fill with rest of the alphabet
    {
        
        int currentLetterASCII = 'A'+i;
        char currentLetter;  
        currentLetter = (char)currentLetterASCII;
        
    if(index == 6) //we reach the limit of 6 elements in a row
       {
          row++;
          index = 0;
       }
 
       for(char letter : usedLetters) // for each letter check if it has been already inserted to the matrix
       {          
            if(currentLetter ==  letter)
            { 
                detected = true;
                break;
            }
       }
       
       if((!detected)) //ignoring whitespaces and repetitions of letters
       {
        usedLetters.add(currentLetter);
        this.polybiusSquare[row][index] = currentLetter;
        detected = false;
        index++;
       }  
       else
           detected = false;
           
    }
    
    for(int i = 0; i<10; i++) //fill with the numbers from 0 to 9
    {
        
    if(index == 6) //we reach the limit of 6 elements in a row
       {
          row++;
          index = 0;
       }
 
       for(char letter : usedLetters) // for each letter check if it has been already inserted to the matrix
       {          
            if(i ==  (int)letter-48)
            {
                detected = true;
                break;
            }
       }
       
       if((!detected)) //ignoring whitespaces and repetitions of letters
       {
        usedLetters.add((char)(i));
        this.polybiusSquare[row][index] = (char)(i+48);
        detected = false;
        index++;
       }  
       else
           detected = false;   
    }   
}  

/**
* Gets the encryption used for creating a matrix.
*
* @return keyPolybiusSquare used for creating a Polybius Square.
*/
    
    public String getKeyPolybiusSquare() {
        return keyPolybiusSquare;
    }

/**
* Gets the key used for numbering the columns in the encryption algorithm.
*
* @return key used for numbering columns.
*/
    public String getKeyColumns() {
        return keyColumns;
    }
    
/**
* Gets the message.
*
* @return message used for encrypting or decrypting.
*/
    
    public String getMessage() {
        return message;
    }

/**
* Sets the encryption key for creating the Polybus Square matrix.
*
* @param keyPolybiusSquare used for creating the matrix for encryption.
*/  

    public void setKeyPolybiusSquare(String keyPolybiusSquare) {
        this.keyPolybiusSquare = keyPolybiusSquare;
    }
    
/**
* Sets the encryption key for columns.
*
* @param keyColumns used for numbering the columns (used for encrypting a message).
*/  
    public void setKeyColumns(String keyColumns) {
        this.keyColumns = keyColumns;
    }
    
/**
* Sets the message to encrypt/decrypt.
*
* @param message the message used for encrypting or decrypting.
*/
    public void setMessage(String message) {
        this.message = message;
    }

}
