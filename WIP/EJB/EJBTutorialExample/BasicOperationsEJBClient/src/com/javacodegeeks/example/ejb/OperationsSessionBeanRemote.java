/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javacodegeeks.example.ejb;

import javax.ejb.Remote;

/**
 *
 * @author RadhaKrishna
 */
@Remote
public interface OperationsSessionBeanRemote {

    float add(float x, float y);

    float subtract(float x, float y);

    float mutliply(float x, float y);

    float divide(float x, float y);    
}