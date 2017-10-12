/**
 * File   : MyMessage.java
 * Authors: Nicolas Fuchs
 *          Jigé Pont
 * Date   : 25.09.2017
 * 
 * Description - a simple MyMessage for TCP
 *
 */
package sockets.tcp;

import java.io.Serializable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4361090429265319535L;

	private static int compteur = 0;
	private String msg;
	private int msgNum;
	
	private final Lock mutex = new ReentrantLock(true);
	
	public MyMessage(String msg) {
		mutex.lock();
		this.msgNum = compteur++;
		mutex.unlock();
		this.msg = msg;
	}
	
	public int getMsgNumber() {
		return this.msgNum;
	}
	
	public String getMsg() {
		return this.msg;
	}

}
