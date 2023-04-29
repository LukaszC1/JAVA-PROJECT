/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.lukasz.rak.model;

/**
 * Additional class for exceptions thrown then the input data is invalid.
 * @author Łukasz Rak
 * @version FINAL-5
 */
public class IncorrectInput extends Exception {

    /**
     * IncorrectInput class constructor taking error message string as parameter.
     * @param error string with message.
     */
    public IncorrectInput(String error)
    {
        super(error);
    }
}
