package essentials.communication.worlddata_server2008;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class FellowPlayer {
    
	@XmlElement(name="id")
	int mId;
	
	@XmlElement(name="nickname")
	String mNickname;
	
	@XmlElement(name="status")
    @XmlJavaTypeAdapter(StringToBooleanAdapter.class)
	Boolean mStatus;
	
	@XmlElement(name="dist")
	double mDistanceToPlayer;
	
	@XmlElement(name="angle")
	double mAngleToPlayer;
	
	@XmlElement(name="orientation")
	double mOrientation;
	
	public FellowPlayer() {
		
	}
	
    public FellowPlayer(int mId, String mNickname, Boolean mStatus,
			double mDistanceToPlayer, double mAngleToPlayer, double mOrientation) {
		
		this.mId = mId;
		this.mNickname = mNickname;
		this.mStatus = mStatus;
		this.mDistanceToPlayer = mDistanceToPlayer;
		this.mAngleToPlayer = mAngleToPlayer;
		this.mOrientation = mOrientation;
	}
    
	public int getId() {
        return mId;
    }
    public String getNickname() {
        return mNickname;
    }
    public Boolean getStatus() {
        return mStatus;
    }
    public double getDistanceToPlayer() {
        return mDistanceToPlayer;
    }
    public double getAngleToPlayer() {
        return mAngleToPlayer;
    }
    public double getOrientation() {
        return mOrientation;
    }
	
}