/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tda;

import piece.User;

/**
 *
 * @author i7
 * @param <E>
 */
public interface ListIterator <E>  {
    boolean limit();

    E next();

    E previous();

}
