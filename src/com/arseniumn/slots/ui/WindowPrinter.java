package com.arseniumn.slots.ui;
import com.arseniumn.slots.model.Symbol;

public class WindowPrinter {

	public void printAll(Symbol[][] slot_window,int totalbet,double reward, int coins) {
		System.out.println("||=========================================================================||");
	    System.out.println("||*********************** ==== *** ====  *** ==== *************************||");
	    System.out.println("||************************* // ***** // *****  // *************************||");
	    System.out.println("||************************ // ***** // *****  // **************************||    (((O)))");
	    System.out.println("||*************************************************************************||      //");
	    System.out.println("||*************************************************************************||     //");
	    System.out.println("||=========================================================================||    //");
	    System.out.println("||  "+slot_window[0][0].getName()+"  ||  "+slot_window[0][1].getName()+"  ||  "+slot_window[0][2].getName()+"  ||  "+slot_window[0][3].getName()+"  ||  "+slot_window[0][4].getName()+"  ||   //");
	    System.out.println("||-------------||-------------||-------------||-------------||-------------||  //");
	    System.out.println("||  "+slot_window[1][0].getName()+"  ||  "+slot_window[1][1].getName()+"  ||  "+slot_window[1][2].getName()+"  ||  "+slot_window[1][3].getName()+"  ||  "+slot_window[1][4].getName()+"  || //");
	    System.out.println("||-------------||-------------||-------------||-------------||-------------||//");
	    System.out.println("||  "+slot_window[2][0].getName()+"  ||  "+slot_window[2][1].getName()+"  ||  "+slot_window[2][2].getName()+"  ||  "+slot_window[2][3].getName()+"  ||  "+slot_window[2][4].getName()+"  ||/");
	    System.out.println("||=========================================================================||");
	    System.out.println("||=========================================================================||");
	    System.out.println("||  Remaining coins:"+coins+"          WIN:"+reward+"          Bet:"+totalbet+"               ||");
	    System.out.println("||=========================================================================||");
	    System.out.println("||=========================================================================||");
	    System.out.println("||=========================================================================||");

	}
	
}
