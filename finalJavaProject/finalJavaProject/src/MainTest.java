import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import javafx.application.Application;

import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.assertTrue;


class MainTest {
	 private volatile boolean success = false;
	@Test
    public void testMain() {
        Thread thread = new Thread() { // Wrapper thread.
            @Override
            public void run() {
                try {
                    Application.launch(GameMain.class); // Run JavaFX application.
                    success = true;
                } catch(Throwable throble) {
                    if(throble.getCause() != null && throble.getCause().getClass().equals(InterruptedException.class)) {
                        success = true;
                        return;
                    }
                    Logger.getLogger(GameMain.class.getName()).log(Level.SEVERE, null, throble);
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
        try {
            Thread.sleep(2000);  
        } catch(InterruptedException exception) {
            
        }
        thread.interrupt();
        try {
            thread.join(1); 
        } catch(InterruptedException exception) {
        	
        }
        assertTrue(success);
    }
}


