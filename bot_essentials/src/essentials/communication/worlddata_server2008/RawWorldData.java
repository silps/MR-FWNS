package essentials.communication.worlddata_server2008;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import essentials.communication.WorldData;


@XmlRootElement(name="WorldData")
public class RawWorldData implements WorldData{

	public static RawWorldData createRawWorldDataFromXML( String aXMLData ){
		
	    FellowPlayer.IDCOUNTER = 0;
	    
		RawWorldData vWorldData = JAXB.unmarshal( new StringReader( aXMLData ), RawWorldData.class );
		
		return vWorldData;
		
	}
    
    @XmlElement(name="time")
	double mPlayTime;
	@XmlElement(name="playMode")
	PlayMode mPlayMode;
	@XmlElement(name="score")
	Score mScore;

	@XmlElement(name="agent_id")
	int mAgentId;
	@XmlElement(name="nickname")
	String mAgentNickname;
	@XmlElement(name="status")
	@XmlJavaTypeAdapter(StringToBooleanAdapter.class)
	Boolean mAgentStatus;

	@XmlElement(name="max_agent")
	int mMaxNumberOfAgents;

	@XmlElement(name="ball")
	BallPosition mBallPosition;

	@XmlElement(name="teamMate")
	List<FellowPlayer> mListOfTeamMates;
	@XmlElement(name="opponent")
	List<FellowPlayer> mListOfOpponents;

    @XmlElement(name="flag")
    List<ReferencePoint> mReferencePoints;
    
    @XmlTransient
    public double getPlayTime() {
        return mPlayTime;
    }
	@XmlTransient
    public PlayMode getPlayMode() {
        return mPlayMode;
    }
	@XmlTransient
    public Score getScore() {
        return mScore;
    }
	@XmlTransient
    public int getAgentId() {
        return mAgentId;
    }
	@XmlTransient
    public String getAgentNickname() {
        return mAgentNickname;
    }
	@XmlTransient
    public Boolean getAgentStatus() {
        return mAgentStatus;
    }
	@XmlTransient
    public int getMaxNumberOfAgents() {
        return mMaxNumberOfAgents;
    }
	@XmlTransient
    public BallPosition getBallPosition() {
        return mBallPosition;
    }
	@XmlTransient
    public List<FellowPlayer> getListOfTeamMates() {
        return mListOfTeamMates != null ? mListOfTeamMates : new ArrayList<FellowPlayer>();
    }
	@XmlTransient
    public List<FellowPlayer> getListOfOpponents() {
        return mListOfOpponents != null ? mListOfOpponents : new ArrayList<FellowPlayer>();
    }
	@XmlTransient
    public ReferencePoint getCenterLineBottom(){
        
        return getReferencePoint( ReferencePointName.CenterLineBottom );
        
    }
	@XmlTransient
    public ReferencePoint getYellowFieldCornerBottom(){
        
        return getReferencePoint( ReferencePointName.YellowFieldCornerBottom );
        
    }
	@XmlTransient
    public ReferencePoint getYellowPenaltyAreaFrontBottom(){
        
        return getReferencePoint( ReferencePointName.YellowPenaltyAreaFrontBottom );
        
    }
	@XmlTransient
    public ReferencePoint getYellowGoalCornerBottom(){
        
        return getReferencePoint( ReferencePointName.YellowGoalCornerBottom );
        
    }
	@XmlTransient
    public ReferencePoint getYellowGoalAreaFrontBottom(){
        
        return getReferencePoint( ReferencePointName.YellowGoalAreaFrontBottom );
        
    }
	@XmlTransient
    public ReferencePoint getBlueFieldCornerBottom(){
        
        return getReferencePoint( ReferencePointName.BlueFieldCornerBottom );
        
    }
	@XmlTransient
    public ReferencePoint getBluePenaltyAreaFrontBottom(){
        
        return getReferencePoint( ReferencePointName.BluePenaltyAreaFrontBottom );
        
    }
	@XmlTransient
    public ReferencePoint getBlueGoalCornerBottom(){
        
        return getReferencePoint( ReferencePointName.BlueGoalCornerBottom );
        
    }
	@XmlTransient
    public ReferencePoint getBlueGoalAreaFrontBottom(){
        
        return getReferencePoint( ReferencePointName.BlueGoalAreaFrontBottom );
        
    }
	@XmlTransient
    public ReferencePoint getFieldCenter(){
        
        return getReferencePoint( ReferencePointName.FieldCenter );
        
    }
	@XmlTransient
    public ReferencePoint getCenterLineTop(){
        
        return getReferencePoint( ReferencePointName.CenterLineTop );
        
    }
	@XmlTransient
    public ReferencePoint getYellowFieldCornerTop(){
        
        return getReferencePoint( ReferencePointName.YellowFieldCornerTop );
        
    }
	@XmlTransient
    public ReferencePoint getYellowPenaltyAreaFrontTop(){
        
        return getReferencePoint( ReferencePointName.YellowPenaltyAreaFrontTop );
        
    }
	@XmlTransient
    public ReferencePoint getYellowGoalCornerTop(){
        
        return getReferencePoint( ReferencePointName.YellowGoalCornerTop );
        
    }
	@XmlTransient
    public ReferencePoint getYellowGoalAreaFrontTop(){
        
        return getReferencePoint( ReferencePointName.YellowGoalAreaFrontTop );
        
    }
	@XmlTransient
    public ReferencePoint getBlueFieldCornerTop(){
        
        return getReferencePoint( ReferencePointName.BlueFieldCornerTop );
        
    }
	@XmlTransient
    public ReferencePoint getBluePenaltyAreaFrontTop(){
        
        return getReferencePoint( ReferencePointName.BluePenaltyAreaFrontTop );
        
    }
	@XmlTransient
    public ReferencePoint getBlueGoalCornerTop(){
        
        return getReferencePoint( ReferencePointName.BlueGoalCornerTop );
        
    }
	@XmlTransient
    public ReferencePoint getBlueGoalAreaFrontTop(){
        
        return getReferencePoint( ReferencePointName.BlueGoalAreaFrontTop );
        
    }

    @XmlTransient
    public List<ReferencePoint> getReferencePoints() {
        return mReferencePoints;
    }
	
    public ReferencePoint getReferencePoint( ReferencePointName aName ) {
        
        if( mReferencePoints.get( aName.getPosition() ).getPointName() == aName ){
            
            return mReferencePoints.get( aName.getPosition() );
            
        }
        
        for( ReferencePoint vPoint : mReferencePoints ){
            
            if( vPoint.getPointName() == aName ){
                
                return vPoint;
                
            }
            
        }
        
        return null;
        
    }
    
    public String toXMLString(){
        
        StringWriter vXMLDataStream = new StringWriter();
        JAXB.marshal( this, vXMLDataStream );
        
        String vXMLDataString = vXMLDataStream.toString();
        vXMLDataString = vXMLDataString.substring( vXMLDataString.indexOf('>') + 2 );
        
        return vXMLDataString;
        
    }
    
    @Override
    public String toString() {
        return "RawWorldData [mPlayTime=" + mPlayTime + ", mPlayMode=" + mPlayMode + ", mScore=" + mScore
                + ", mAgentId=" + mAgentId + ", mAgentNickname=" + mAgentNickname + ", mAgentStatus=" + mAgentStatus
                + ", mMaxNumberOfAgents=" + mMaxNumberOfAgents + ", mBallPosition=" + mBallPosition
                + ", mListOfTeamMates=" + mListOfTeamMates + ", mListOfOpponents=" + mListOfOpponents
                + ", mReferencePoints=" + mReferencePoints + "]";
    }
    
    
	
}
/*
<rawWorldData>
    <time>0</time>
    <agent_id>20</agent_id>
    <nickname>TestBot</nickname>
    <status>found</status>
    <max_agent>22</max_agent>
	<playMode>kick off</playMode>
    <score>
        <yellow>0</yellow>
        <blue>0</blue>
    </score>
    <ball>
        <dist>264.6</dist>
        <angle>-8.03823</angle>
    </ball>
    <teamMate>
        <id>0</id>
        <nickname>TestBot</nickname>
        <status>found</status>
        <dist>200.0</dist>
        <angle>-90.0</angle>
        <orientation>0.0</orientation>
    </teamMate>
    <teamMate>
        <id>1</id>
        <nickname>TestBot</nickname>
        <status>found</status>
        <dist>200.0</dist>
        <angle>-90.0</angle>
        <orientation>0.0</orientation>
    </teamMate>
    <teamMate>
        <id>2</id>
        <nickname>TestBot</nickname>
        <status>found</status>
        <dist>200.0</dist>
        <angle>-90.0</angle>
        <orientation>0.0</orientation>
    </teamMate>
    <teamMate>
        <id>3</id>
        <nickname>TestBot</nickname>
        <status>found</status>
        <dist>200.0</dist>
        <angle>-90.0</angle>
        <orientation>0.0</orientation>
    </teamMate>
    <teamMate>
        <id>4</id>
        <nickname>TestBot</nickname>
        <status>found</status>
        <dist>200.0</dist>
        <angle>-90.0</angle>
        <orientation>0.0</orientation>
    </teamMate>
    <teamMate>
        <id>5</id>
        <nickname>TestBot</nickname>
        <status>found</status>
        <dist>200.0</dist>
        <angle>-90.0</angle>
        <orientation>0.0</orientation>
    </teamMate>
    <teamMate>
        <id>6</id>
        <nickname>TestBot</nickname>
        <status>found</status>
        <dist>200.0</dist>
        <angle>-90.0</angle>
        <orientation>0.0</orientation>
    </teamMate>
    <teamMate>
        <id>7</id>
        <nickname>TestBot</nickname>
        <status>found</status>
        <dist>200.0</dist>
        <angle>-90.0</angle>
        <orientation>0.0</orientation>
    </teamMate>
    <teamMate>
        <id>18</id>
        <nickname>TestBot</nickname>
        <status>found</status>
        <dist>0.0</dist>
        <angle>0.0</angle>
        <orientation>0.0</orientation>
    </teamMate>
    <teamMate>
        <id>19</id>
        <nickname>TestBot</nickname>
        <status>found</status>
        <dist>0.0</dist>
        <angle>0.0</angle>
        <orientation>0.0</orientation>
    </teamMate>
    <opponent>
        <id>8</id>
        <nickname>TestBot</nickname>
        <status>found</status>
        <dist>200.0</dist>
        <angle>-90.0</angle>
        <orientation>0.0</orientation>
    </opponent>
    <opponent>
        <id>9</id>
        <nickname>TestBot</nickname>
        <status>found</status>
        <dist>200.0</dist>
        <angle>-90.0</angle>
        <orientation>0.0</orientation>
    </opponent>
    <opponent>
        <id>10</id>
        <nickname>TestBot</nickname>
        <status>found</status>
        <dist>200.0</dist>
        <angle>-90.0</angle>
        <orientation>0.0</orientation>
    </opponent>
    <opponent>
        <id>11</id>
        <nickname>TestBot</nickname>
        <status>found</status>
        <dist>200.0</dist>
        <angle>-90.0</angle>
        <orientation>0.0</orientation>
    </opponent>
    <opponent>
        <id>12</id>
        <nickname>TestBot</nickname>
        <status>found</status>
        <dist>200.0</dist>
        <angle>-90.0</angle>
        <orientation>0.0</orientation>
    </opponent>
    <opponent>
        <id>13</id>
        <nickname>TestBot</nickname>
        <status>found</status>
        <dist>200.0</dist>
        <angle>-90.0</angle>
        <orientation>0.0</orientation>
    </opponent>
    <opponent>
        <id>14</id>
        <nickname>TestBot</nickname>
        <status>found</status>
        <dist>200.0</dist>
        <angle>-90.0</angle>
        <orientation>0.0</orientation>
    </opponent>
    <opponent>
        <id>15</id>
        <nickname>TestBot</nickname>
        <status>found</status>
        <dist>200.0</dist>
        <angle>-90.0</angle>
        <orientation>0.0</orientation>
    </opponent>
    <opponent>
        <id>16</id>
        <nickname>TestBot</nickname>
        <status>found</status>
        <dist>200.0</dist>
        <angle>-90.0</angle>
        <orientation>0.0</orientation>
    </opponent>
    <opponent>
        <id>17</id>
        <nickname>TestBot</nickname>
        <status>found</status>
        <dist>509.902</dist>
        <angle>-11.3099</angle>
        <orientation>0.0</orientation>
    </opponent>
    <flag>
        <id>bottom_center</id>
        <dist>335.932</dist>
        <angle>38.4734</angle>
    </flag>
    <flag>
        <id>bottom_left_corner</id>
        <dist>245.082</dist>
        <angle>121.485</angle>
    </flag>
    <flag>
        <id>bottom_left_goal</id>
        <dist>132.563</dist>
        <angle>73.3422</angle>
    </flag>
    <flag>
        <id>bottom_left_pole</id>
        <dist>135.351</dist>
        <angle>161.03</angle>
    </flag>
    <flag>
        <id>bottom_left_small_area</id>
        <dist>73.6003</dist>
        <angle>143.286</angle>
    </flag>
    <flag>
        <id>bottom_right_corner</id>
        <dist>685.631</dist>
        <angle>17.7479</angle>
    </flag>
    <flag>
        <id>bottom_right_goal</id>
        <dist>503.287</dist>
        <angle>14.6161</angle>
    </flag>
    <flag>
        <id>bottom_right_pole</id>
        <dist>654.481</dist>
        <angle>3.85484</angle>
    </flag>
    <flag>
        <id>bottom_right_small_area</id>
        <dist>585.655</dist>
        <angle>4.30866</angle>
    </flag>
    <flag>
        <id>middle_center</id>
        <dist>265.452</dist>
        <angle>-7.79433</angle>
    </flag>
    <flag>
        <id>top_center</id>
        <dist>383.419</dist>
        <angle>-46.6909</angle>
    </flag>
    <flag>
        <id>top_left_corner</id>
        <dist>306.961</dist>
        <angle>-114.645</angle>
    </flag>
    <flag>
        <id>top_left_goal</id>
        <dist>203.578</dist>
        <angle>-79.242</angle>
    </flag>
    <flag>
        <id>top_left_pole</id>
        <dist>171.406</dist>
        <angle>-138.311</angle>
    </flag>
    <flag>
        <id>top_left_small_area</id>
        <dist>128.363</dist>
        <angle>-117.364</angle>
    </flag>
    <flag>
        <id>top_right_corner</id>
        <dist>710.106</dist>
        <angle>-23.135</angle>
    </flag>
    <flag>
        <id>top_right_goal</id>
        <dist>526.468</dist>
        <angle>-22.3269</angle>
    </flag>
    <flag>
        <id>top_right_pole</id>
        <dist>662.876</dist>
        <angle>-9.90283</angle>
    </flag>
    <flag>
        <id>top_right_small_area</id>
        <dist>595.023</dist>
        <angle>-11.0456</angle>
    </flag>
</rawWorldData>
*/