package exampleai.brain;




import java.util.List;

import mrlib.core.KickLib;
import mrlib.core.MoveLib;
import mrlib.core.PlayersLib;
import mrlib.core.PositionLib;

import essentials.communication.Action;
import essentials.communication.action_server2008.Movement;
import essentials.communication.worlddata_server2008.BallPosition;
import essentials.communication.worlddata_server2008.FellowPlayer;
import essentials.communication.worlddata_server2008.RawWorldData;
import essentials.communication.worlddata_server2008.ReferencePoint;
import essentials.core.ArtificialIntelligence;
import essentials.core.BotInformation;
import essentials.core.BotInformation.GamevalueNames;
import essentials.core.BotInformation.Teams;


// -bn 3 -tn "Northern Stars" -t blau -ids 3 -s 192.168.178.22:3310 -aiarc "${workspace_loc:FWNS_ExampleAI}/bin" -aicl "exampleai.brain.AI" -aiarg 0

public class DefensiveMidfielder extends Thread implements ArtificialIntelligence {
	
    BotInformation mSelf = null;
    RawWorldData mWorldState = null;
    Action mAction = null;
    
    boolean mNeedNewAction = true;    
    boolean mIsStarted = false;
    boolean mIsPaused = false;
    private boolean mRestart = false;
    
    @Override
    public void initializeAI( BotInformation aOneSelf ) {
        
        mSelf = aOneSelf; 
        mIsStarted = true;
        start();
        
    }

    @Override
    public void resumeAI() {
        
        mIsPaused = false;
        
    }
    
    @Override
    public void suspendAI() {
        
        mIsPaused = true;   
        
    }
    
    public void run(){
        
        RawWorldData vWorldState = null;
        Action vBotAction = null;
        
        while ( mIsStarted ){
            
            while( mIsPaused ){ try { this.wait( 10 ); } catch ( InterruptedException e ) { e.printStackTrace(); } }

            try {             
                if( mNeedNewAction && mWorldState != null  ){
                    synchronized ( this ) {
                        vWorldState = mWorldState;
                    }
                    
                    List<FellowPlayer> vOpponents = vWorldState.getListOfOpponents();
                    List<FellowPlayer> vTeamMates = vWorldState.getListOfTeamMates();
                    // --------------- START AI -------------------
                    
                    if( vWorldState.getBallPosition() != null ){
                    	System.out.println("DMF spieler");
                    	// get ball position
                    	
                    	BallPosition ballPos = vWorldState.getBallPosition();
                    	ReferencePoint DMF = getDMFposition(vWorldState, mSelf.getTeam());
                    	//Ist Gegner in der Nähe?
                    	
                    	if(PlayersLib.amINearestToBall(vWorldState, ballPos, mSelf)){
                    		mSelf.setAIClassname("exampleai.brain.DefensiveMidfielder");
                    		mRestart = true;
                    		mAction = (Action) Movement.NO_MOVEMENT;
                    	}
                    	
                    	if( ballPos.getDistanceToBall() < mSelf.getGamevalue( GamevalueNames.KickRange ) ){                 
                    		// kick
                    		FellowPlayer nearestMate = null;
                        	boolean enemyNear = false;
                        	for( FellowPlayer p : vOpponents){
                        		if(p.getDistanceToPlayer() < 75){
                        			enemyNear = true;
                        			for( FellowPlayer a : vTeamMates){
                        				if( nearestMate == null || a.getDistanceToPlayer() < nearestMate.getDistanceToPlayer()){
                        					nearestMate = a;
                        				}                    				
                        			break;
                        			}
                        			
                        		}
                        	}
                        	if(enemyNear == true){
                        		vBotAction = KickLib.kickTo(nearestMate);
                        		enemyNear = false;
                        	}else{
                        		ReferencePoint goalMid = PositionLib.getMiddleOfGoal( vWorldState, mSelf.getTeam() );
                        		vBotAction = KickLib.kickTo( goalMid );  
                    		}                  		
                    	} else if(PositionLib.isBallInRangeOfRefPoint(ballPos, DMF, 200)){
                    		// move to ball
                    		vBotAction = MoveLib.runTo( ballPos );
                    	} 
                    	else {
                    		if(DMF.getDistanceToPoint() > 10){
                    			vBotAction = MoveLib.runTo(DMF);
                    		}
                    		else{
                    			vBotAction = null;
                    		}
                    	}
                    	
                    }
                    
                    // ---------------- END AI --------------------
                    
                    synchronized ( this ) {
                        mAction = vBotAction;
                        mNeedNewAction = false;
                    }                  
                }
                Thread.sleep( 1 );                
            } catch ( Exception e ) {
                e.printStackTrace();
            }            
            
        }
        
    }

    
    @Override
    public synchronized Action getAction() {

        synchronized ( this ) {
            if( mAction != null)
                return mAction;
        }
        return (Action) Movement.NO_MOVEMENT;
        
    }

    @Override
    public void putWorldState(RawWorldData aWorldState) {

        synchronized ( this ) {
            mWorldState = aWorldState;
            mNeedNewAction = true;
        }
        
    }

    @Override
    public void disposeAI() {
        
        mIsStarted = false;
        mIsPaused = false;
        
    }
    
    @Override
    public boolean isRunning() {

        return mIsStarted && !mIsPaused;
        
    }

	@Override
	public boolean wantRestart() {
		// TODO Auto-generated method stub
		return mRestart;
	}

    @Override
    public void executeCommand( String arg0 ) {
        // TODO Auto-generated method stub
        
    }
    
    public static ReferencePoint getDMFposition( RawWorldData aWorldData, Teams aTeam ){
    	ReferencePoint PenaltyTop;
    	ReferencePoint PenaltyBottom;
    	ReferencePoint PenaltyMid;
    	
    	if ( aTeam == Teams.Blue){
    		PenaltyTop = aWorldData.getBluePenaltyAreaFrontTop();
    		PenaltyBottom = aWorldData.getBluePenaltyAreaFrontBottom();
    	}else
    	{
    		PenaltyTop = aWorldData.getYellowPenaltyAreaFrontTop();
    		PenaltyBottom = aWorldData.getYellowPenaltyAreaFrontBottom();
    	}
    	
    	PenaltyMid = PositionLib.getMiddleOfTwoReferencePoints(PenaltyTop, PenaltyBottom);
    	ReferencePoint DMFpoint = PositionLib.getMiddleOfTwoReferencePoints(PenaltyMid, aWorldData.getFieldCenter());
    	    	
    	return DMFpoint;
    }

}