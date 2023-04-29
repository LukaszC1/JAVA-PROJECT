/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.lukasz.rak.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Entity class used by the database.
 * @author Łukasz Rak
 * @version FINAL-5
 */
@Entity
public class HistoryEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    
    /**Entity id.*/
    private Long id;
    
    /**Message for encryption/decryption.*/
    private String message;
    
    /**Key for creating alphabet matrix.*/
    private String keyMatrix;
    
    /**Key for creating encryption matrix.*/
    private String keyColumns;
    
    /**The result of algorithm.*/
    private String result;
    
    public String getMessage() {
        return message;
    }

    public String getKeyMatrix() {
        return keyMatrix;
    }

    public String getKeyColumns() {
        return keyColumns;
    }

    public String getResult() {
        return result;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setKeyMatrix(String keyMatrix) {
        this.keyMatrix = keyMatrix;
    }

    public void setKeyColumns(String keyColumns) {
        this.keyColumns = keyColumns;
    }

    public void setResult(String result) {
        this.result = result;
    }
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HistoryEntity)) {
            return false;
        }
        HistoryEntity other = (HistoryEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.polsl.lukasz.rak.entities.HistoryEntity[ id=" + id + " ]";
    }
    
}
