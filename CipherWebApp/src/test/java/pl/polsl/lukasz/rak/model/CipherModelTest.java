/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.lukasz.rak.model;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * The test class checking all the methods from the model class.
 * @author Łukasz Rak
 * @version FINAL-5
 */
public class CipherModelTest {
    
    /**
    * Declaration of the model class for testing.
    */
    CipherModel testModel = new CipherModel();
    
    /**
     * Test of encrypt method, of class CipherModel with correct data.
     * @throws IncorrectInput
     */
    
    @Test
    public void testEncrypt() throws IncorrectInput {
        
        testModel.setKeyColumns("privacy");
        testModel.setKeyPolybiusSquare("147regiment");
        testModel.setMessage("attack at 1200 am");
        testModel.encrypt();
        assertEquals("DXXVGDADDAAXDVDXVFGVGFADDVVD",testModel.getMessage()); //testing the result of encryption which is put into the message field   
        
        testModel.setKeyColumns("matrix");
        testModel.setKeyPolybiusSquare("keyhole");
        testModel.setMessage("message");
        testModel.encrypt();
        assertEquals("FDDGDFGADAADDX",testModel.getMessage()); //testing the result of encryption which is put into the message field   
    }
   /**
     * Test of decrypt method, of class CipherModel with correct data.
     * @throws IncorrectInput
     */
    @Test
    public void testDecrypt() throws IncorrectInput {
        testModel.setKeyColumns("privacy");
        testModel.setKeyPolybiusSquare("147regiment");
        testModel.setMessage("DXXVGDADDAAXDVDXVFGVGFADDVVD");
        testModel.decrypt();
        assertEquals("ATTACKAT1200AM",testModel.getMessage());
        
        testModel.setKeyColumns("matrix");
        testModel.setKeyPolybiusSquare("keyhole");
        testModel.setMessage("FDDGDFGADAADDX");
        testModel.decrypt();
        assertEquals("MESSAGE",testModel.getMessage()); //testing the result of encryption which is put into the message field   
    }
    
     /**
     * Test of exception used in encrypt method (incorrect input data).
     */
    @Test
    public void testExceptionEncrypt(){
     try{
             testModel.setKeyColumns("p;';rivaacy");   
             testModel.setKeyPolybiusSquare("147regiment");
             testModel.setMessage("attack at 1200 am");         
             testModel.encrypt();           
            
             testModel.setKeyColumns("private");   
             testModel.setKeyPolybiusSquare("regi\\m\\ent");
             testModel.setMessage("attack at 1200 am");         
             testModel.encrypt();
             
             testModel.setKeyColumns("private");   
             testModel.setKeyPolybiusSquare("regiment");
             testModel.setMessage("attack at[ 12[00[ am");         
             testModel.encrypt();
             
            fail("An exception should be thrown when the input data contains letters outside of the English alphabet");
            
        }catch(IncorrectInput e){  
             }
    }
    
    /**
     * Test of exception used in decrypt method (incorrect input data).
     */
    @Test
    public void testExceptionDecrypt(){ //the exception used in both decrypt and encrypt is the same
     try{
             testModel.setKeyColumns("privacy");   
             testModel.setKeyPolybiusSquare("147regiment");
             testModel.setMessage("D;XXVGDA;''.");         
             testModel.encrypt();
             
            fail("An exception should be thrown when the input data contains letters outside of the English alphabet");
            
            }catch(IncorrectInput e){
            }
    }
    
    /**
     * Parameterized test of encryption algorithm.
     * @throws IncorrectInput
     * @param keyColumns for numering the columns for encryption matrix
     * @param keyMatrix for creating the alphabet
     * @param message to be decrypted
     * @param expected result of the algorithm
     */
    @ParameterizedTest
    @CsvSource({
    "column,message,i am waiting for you,DGGAGGAADDVADFFFAVFGGVVDVAFGGVFA",
    "rows,legacy,long message for the user,AFDDGDGFDDAAFFGAAFGAGGGFDDFFAGGDFAAGADGDGA",
    "secretcode,eyes,we meet today,AGADAAXFDFFAGAAAFGGAFV"})
    public void encryptionParamTest(String keyColumns, String keyMatrix,String message,String expected) throws IncorrectInput {
       
        testModel.setKeyColumns(keyColumns);
        testModel.setKeyPolybiusSquare(keyMatrix);
        testModel.setMessage(message);
        testModel.encrypt(); // this method puts the result in message field
        assertEquals(expected,testModel.getMessage());
    }
    
     /**
     * Parameterized test of decryption algorithm.
     * @throws IncorrectInput
     * @param keyColumns for numering the columns for encryption matrix
     * @param keyMatrix for creating the alphabet
     * @param message to be decrypted
     * @param expected result of the algorithm
     */
    @ParameterizedTest
    @CsvSource({
    "column,message,DGGAGGAADDVADFFFAVFGGVVDVAFGGVFA,IAMWAITINGFORYOU",
    "rows,legacy,AFDDGDGFDDAAFFGAAFGAGGGFDDFFAGGDFAAGADGDGA,LONGMESSAGEFORTHEUSER",
    "secretcode,eyes,AGADAAXFDFFAGAAAFGGAFV,WEMEETTODAY"})
    public void decryptionParamTest(String keyColumns, String keyMatrix,String message,String expected) throws IncorrectInput {
        
        testModel.setKeyColumns(keyColumns);
        testModel.setKeyPolybiusSquare(keyMatrix);
        testModel.setMessage(message);
        testModel.decrypt(); // this method puts the result in message field
        assertEquals(expected,testModel.getMessage());
    }
 
}
