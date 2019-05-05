/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cerdascermat;

import java.util.TimerTask;

/**
 *
 * @author Owner
 */
public class Timer {
   

public static void main(String[] args) {
  // run in a second
  final long timeInterval = 1000;
  Runnable runnable = new Runnable() {
  
  public void run() {
    while (true) {
      // ------- code for task to run
      System.out.println("Hello !!");
      // ------- ends here
      try {
       Thread.sleep(timeInterval);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      }
    }
  };
  
  Thread thread = new Thread(runnable);
  thread.start();
  }

    void scheduleAtFixedRate(TimerTask timerTask, int delay, int period) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void schedule(TimerTask task, int i, int i0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

