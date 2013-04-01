package core;

import java.util.concurrent.atomic.AtomicLong;

import essentials.communication.Action;
import essentials.communication.action_server2008.Movement;
import essentials.core.ArtificialIntelligence;
import fwns_network.server_2008.NetworkCommunication;

public class ToServerManagement extends Thread{

	private boolean mManageMessagesFromServer = false;
    private AtomicLong mLastSendMessage = new AtomicLong( 0 );
    	
	@Override
	public void start(){
		
		this.startManagement();
		
	}
	
	public void startManagement() throws NullPointerException{
		
		if( Core.getInstance().getServerConnection() != null && !isAlive() ) {
		    
		    mManageMessagesFromServer = true;
			super.start();
			
		} else {
		    
			throw new NullPointerException( "NetworkCommunication cannot be NULL when starting ToServerManagement." ) ;
			
		}
		
	}
	
	public void stopManagement(){
	    
	    mManageMessagesFromServer = false;
        while(isAlive()){ 
            try {
                Thread.sleep( 10 );
            } catch ( InterruptedException e ) {
                e.printStackTrace();
            } 
        }
		
	}
	
	public void run(){
	    
	    Action vCurrentAction = null;
	    
		while( mManageMessagesFromServer ){
			
			try {
				
				Thread.sleep(66); // besser machen!
				
				if( Core.getInstance().getServerConnection() != null ) {
					
					if( Core.getInstance().getAI() != null ) {

					    vCurrentAction = Core.getInstance().getAI().getAction();
					    Core.getInstance().getServerConnection().sendDatagramm( vCurrentAction );
                        mLastSendMessage.set( System.currentTimeMillis() );
                        
						
					} else {

					    Core.getInstance().getServerConnection().sendDatagramm( (Action) Movement.NO_MOVEMENT );
						throw new NullPointerException( "Without actual AI only empty messages will be sent to the Server." ); // Change Exception 
												
					}
					
				} else {
					
					throw new NullPointerException( "NetworkCommunication cannot be NULL when running ToServerManagement." ) ;
					
				}
				
			} catch ( Exception e ) {
				
				// Logging einbauen besser!!!
			    e.printStackTrace();
				
			}
			
		}
		
	}
	
	public boolean isSendingMessages(){
	    
	    return (isAlive() && (System.currentTimeMillis() - mLastSendMessage.get() ) < 100);
	    
	}
	
}
