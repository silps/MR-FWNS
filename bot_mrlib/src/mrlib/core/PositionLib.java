package mrlib.core;

import java.util.List;
import java.util.ArrayList;

import essentials.core.BotInformation;
import essentials.core.BotInformation.Teams;
import essentials.communication.worlddata_server2008.BallPosition;
import essentials.communication.worlddata_server2008.FellowPlayer;
import essentials.communication.worlddata_server2008.RawWorldData;
import essentials.communication.worlddata_server2008.ReferencePoint;

/**
 * Includes static functions to get or calculate positions on the playing field.
 * Functions tested using the included unit tests.
 * 
 * @author Hannes Eilers, Louis Jorswieck, Eike Petersen
 * @since 0.5
 * @version 1.0
 *
 */
public class PositionLib {
    /**
     * Gets {@link ReferencePoint} in middle of two other {@link ReferencePoint}.
     * @since 0.9
     * @param aRefPoint0    First {@link ReferencePoint}
     * @param aRefPoint1    Second {@link ReferencePoint}
     * @return {@link ReferencePoint} in middle between {@code aRefPoint0} and {@code aRerfPoint1}.
     */
    public static ReferencePoint getMiddleOfTwoReferencePoints(ReferencePoint aRefPoint0, ReferencePoint aRefPoint1) {
        //TODO: vieleicht noch mehr testen!
        //TODO: schoener machen
        //TODO: Funktionen auslagern
        // Merke Seitenhalbierende hat nix! mit Winkelhalbiernder zu tun! Geogebra nutzen...

        double a, b, wa, wb;
        if (aRefPoint0.getAngleToPoint() > aRefPoint1.getAngleToPoint()) {
            wa = aRefPoint0.getAngleToPoint();
            wb = aRefPoint1.getAngleToPoint();
            a = aRefPoint0.getDistanceToPoint();
            b = aRefPoint1.getDistanceToPoint();
        } else {
            wa =aRefPoint1.getAngleToPoint();
            wb = aRefPoint0.getAngleToPoint();
            b = aRefPoint0.getDistanceToPoint();
            a = aRefPoint1.getDistanceToPoint();
        }
        double c = Math.sqrt(a*a + b*b - 2 * a * b * Math.cos(Math.toRadians(Math.abs(wa - wb))));
        double sc = Math.sqrt((2 * (a*a + b*b)) - c*c) / 2;
        double gamma = Math.abs(Math.toDegrees(Math.acos((sc*sc + b*b - 0.25*c*c) / (2*sc*b))));

        if (wa < wb + 180) {
            gamma = wb + gamma;
        } else {
            gamma = wb - gamma;
        }
        if (gamma > 180) {
            gamma = gamma - 360;
        }
        if (gamma < -180) {
            gamma = gamma + 360;
        }

        return new ReferencePoint(sc, gamma, true);
    }

    /**
     * Returns the middle of enemies goal.
     * @param aWorldData             {@link RawWorldData}
     * @param aTeam                     Own {@link Teams} information
     * @return ReferencePoint       {@link ReferencePoint} of middle of enemies goal.
     */
    public static ReferencePoint getMiddleOfGoal(RawWorldData aWorldData, Teams aTeam) {
        ReferencePoint vGoalTop = null;
        ReferencePoint vGoalBottom = null;
        ReferencePoint rGoalMiddle = new ReferencePoint(0.0 , 0.0, true);

        // get opponents goal
        if (aTeam == Teams.Yellow) {
            vGoalTop = aWorldData.getBlueGoalCornerTop();
            vGoalBottom = aWorldData.getBlueGoalCornerBottom();
        } else {
            vGoalTop = aWorldData.getYellowGoalCornerTop();
            vGoalBottom = aWorldData.getYellowGoalCornerBottom();
        }

        rGoalMiddle = PositionLib.getMiddleOfTwoReferencePoints(vGoalTop, vGoalBottom);
        return rGoalMiddle;
    }

    /**
     * Returns the middle of own goal.
     * @param aWorldData           {@link RawWorldData}
     * @param aTeam                   Own {@link Teams} information
     * @return ReferencePoint     {@link ReferencePoint} of middle of own goal.
     */
    public static ReferencePoint getMiddleOfOwnGoal(RawWorldData aWorldData, Teams aTeam) {
        ReferencePoint vGoalTop = null;
        ReferencePoint vGoalBottom = null;
        ReferencePoint rOwnGoalMiddle = new ReferencePoint(0.0 , 0.0, true);

        // get own goal
        if (aTeam == Teams.Blue) {
            vGoalTop = aWorldData.getBlueGoalCornerTop();
            vGoalBottom = aWorldData.getBlueGoalCornerBottom();
        } else {
            vGoalTop = aWorldData.getYellowGoalCornerTop();
            vGoalBottom = aWorldData.getYellowGoalCornerBottom();
        }

        rOwnGoalMiddle = PositionLib.getMiddleOfTwoReferencePoints(vGoalTop, vGoalBottom);
        return rOwnGoalMiddle;
    }

    /**
     * Calculates if ball is in specific range around {@link ReferencePoint}.
     * @param ballPos         {@link BallPosition}
     * @param aRefPoint    {@link ReferencePoint}
     * @param range	          {@link Double} range
     * @return                      {@code true} if {@code ballPos} is in {@code range} around {@code aRefPoint}, {@code false} otherwise.
     */
    public static boolean isBallInRangeOfRefPoint(BallPosition ballPos, ReferencePoint aRefPoint, double range) {
        double a, b, wa, wb;
        if (ballPos.getAngleToBall() > aRefPoint.getAngleToPoint()) {
            wa = ballPos.getAngleToBall();
            a = ballPos.getDistanceToBall();
            wb = aRefPoint.getAngleToPoint();
            b = aRefPoint.getDistanceToPoint();
        } else {
            wb = ballPos.getAngleToBall();
            b = ballPos.getDistanceToBall();
            wa = aRefPoint.getAngleToPoint(); 
            a = aRefPoint.getDistanceToPoint();
        }

        double c = Math.sqrt(a*a + b*b - 2 * a * b * Math.cos(Math.toRadians(Math.abs(wa - wb))));
        if (c < range) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Calculates the distance between two {@link ReferencePoint}.
     * @param aRefPoint0        {@link ReferencePoint}
     * @param aRefPoint1        {@link ReferencePoint}
     * @return                            {@link Double} of distance between {@code aRefPoint0} and {@code aRefPoint1}.
     */
    public static double getDistanceBetweenTwoRefPoints(ReferencePoint aRefPoint0, ReferencePoint aRefPoint1) {
        double a, b, wa, wb;
        wa = aRefPoint0.getAngleToPoint();
        wb = aRefPoint1.getAngleToPoint();
        a = aRefPoint0.getDistanceToPoint();
        b = aRefPoint1.getDistanceToPoint();

        double c = Math.sqrt(a*a + b*b - 2 * a * b * Math.cos(Math.toRadians(Math.abs(wa - wb))));
       return c;
    }

    /**
     * Get's the {@link ReferencePoint} farest away from ball.
     * @param aWorldData          {@link RawWorldData}
     * @return ReferencePoint     {@link ReferencePoint} farest away from ball.
     */
    public static ReferencePoint getFurthestPointAwayFromBall(RawWorldData aWorldData) {
        double vBestAngle = 0;
        ReferencePoint vBestPoint = null;

        ArrayList<ReferencePoint> vEligiblePoints = new ArrayList<ReferencePoint>();
        vEligiblePoints.add(aWorldData.getFieldCenter());
        vEligiblePoints.add(aWorldData.getCenterLineTop());
        vEligiblePoints.add(aWorldData.getCenterLineBottom());
        vEligiblePoints.add(aWorldData.getBlueGoalAreaFrontTop());
        vEligiblePoints.add(aWorldData.getYellowGoalAreaFrontTop());
        vEligiblePoints.add(aWorldData.getBluePenaltyAreaFrontTop());
        vEligiblePoints.add(aWorldData.getBlueGoalAreaFrontBottom());
        vEligiblePoints.add(aWorldData.getYellowGoalAreaFrontBottom());
        vEligiblePoints.add(aWorldData.getYellowPenaltyAreaFrontTop());
        vEligiblePoints.add(aWorldData.getBluePenaltyAreaFrontBottom());
        vEligiblePoints.add(aWorldData.getYellowPenaltyAreaFrontBottom());

        double vPointToBallAngle = 0;
        for (ReferencePoint vPoint : vEligiblePoints) {
            vPointToBallAngle = Math.max(vPoint.getAngleToPoint(), aWorldData.getBallPosition().getAngleToBall()) - Math.min(vPoint.getAngleToPoint(), aWorldData.getBallPosition().getAngleToBall());

            if (vPointToBallAngle > 180) {
                vPointToBallAngle = 360 - vPointToBallAngle;
            }

            if (vPointToBallAngle > vBestAngle) {
                vBestPoint = vPoint;
                vBestAngle = vPointToBallAngle;
            }
        }

        return vBestPoint;
    }

    /**
     * Calculates angle between two reference points
     * @param aRefPoint0        {@link ReferencePoint}
     * @param aRefPoint1        {@link ReferencePoint}
     * @return                            {@link Double} agnle between {@code aRefPoint0} and {@code aRefPoint1}.
     */
    public static double getAngleBetweenTwoReferencePoints(ReferencePoint aRefPoint0, ReferencePoint aRefPoint1) {
        double wa, wb;
        if (aRefPoint0.getAngleToPoint() > aRefPoint1.getAngleToPoint()) {
            wb =aRefPoint1.getAngleToPoint();
            wa = aRefPoint0.getAngleToPoint();
        } else {
            wa =aRefPoint1.getAngleToPoint(); 
            wb = aRefPoint0.getAngleToPoint();
        }

        double gamma = Math.abs(wa - wb);
        if (gamma > 180) {
            gamma = 360 - gamma;
        }

        return gamma;
    }

    /**
     * Calculates if bot is inside quadrilateral of four {@link ReferencePoint}s.
     * @param aRefPoint0        {@link ReferencePoint}
     * @param aRefPoint1        {@link ReferencePoint}
     * @param aRefPoint2        {@link ReferencePoint}
     * @param aRefPoint3        {@link ReferencePoint}
     * @return                            {@code true} if bot is inside quadrilateral {@code aRefPoint0} to {@code aRefPoint3}, {@code false} otherwise.
     */
    public static boolean isBotInQuadrangle(ReferencePoint aRefPoint0, ReferencePoint aRefPoint1, ReferencePoint aRefPoint2, ReferencePoint aRefPoint3) {
        double angle1, angle2, angle3, angle4;
        angle1= PositionLib.getAngleBetweenTwoReferencePoints(aRefPoint0, aRefPoint1);
        angle2= PositionLib.getAngleBetweenTwoReferencePoints(aRefPoint1, aRefPoint2);
        angle3= PositionLib.getAngleBetweenTwoReferencePoints(aRefPoint2, aRefPoint3);
        angle4= PositionLib.getAngleBetweenTwoReferencePoints(aRefPoint3, aRefPoint0);

        if (angle1 + angle2 + angle3 + angle4 < 361 && angle1 + angle2 + angle3 + angle4 > 359) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Calculates if bot is inside soccer field.
     * @param aWorldData        {@link RawWorldData}
     * @return                             {@code true} if bot is inside soccer field, {@code false} othwerwise.
     */
    public static boolean isMeselfOutOfBounds(RawWorldData aWorldData) {
        return isBotInQuadrangle(aWorldData.getBlueFieldCornerTop(),
            aWorldData.getYellowFieldCornerTop(),
            aWorldData.getBlueFieldCornerBottom(),
            aWorldData.getYellowFieldCornerBottom());
    }

    /**
     * Calculates if ball is inside qaudrilateral of four {@link ReferencePoint}s.
     * ATTENTION: FUNCTION IS NOT WORKING! DON'T USE!
     * @param aRefPoint0        {@link ReferencePoint}
     * @param aRefPoint1        {@link ReferencePoint}
     * @param aRefPoint2        {@link ReferencePoint}
     * @param aRefPoint3        {@link ReferencePoint}
     * @param ballPos              {@link BallPosition}
     * @return                            {@code true} if {@code ballPos} is inside quadrilateral {@code aRefPoint0} to {@code aRefPoint3}, {@code false} otherwise.
     */
    public static boolean isBallInQuadrangle(ReferencePoint aRefPoint0, ReferencePoint aRefPoint1, ReferencePoint aRefPoint2, ReferencePoint aRefPoint3, BallPosition ballPos) {
        /*
         * TODO: Complete function to get if the Ball is in an area of 4 ReferencePoints
         */
        double smallestX = 0;
        @SuppressWarnings("unused")
        double secondsmallestX = 0;
        // double biggestX = 0;

        smallestX = aRefPoint0.getXOfPoint();
        if (aRefPoint1.getXOfPoint() < smallestX) {
            secondsmallestX = smallestX;
            smallestX = aRefPoint1.getXOfPoint();
        }
        if (aRefPoint2.getXOfPoint() < smallestX) {
            secondsmallestX = smallestX;
            smallestX = aRefPoint2.getXOfPoint();
        }
        if (aRefPoint3.getXOfPoint() < smallestX) {
            secondsmallestX = smallestX;
            smallestX = aRefPoint3.getXOfPoint();
        }

        return true;
    }

    /**
     * Calculate if agent is nearest team mate to a {@link ReferencePoint}.
     * @param vWorldState     {@link RawWorldData}
     * @param aRefPoint         {@link ReferencePoint}
     * @param aSelf	                {@link BotInformation}
     * @return                           {@code true} if bot is nearest team mate to {@code aRefPoint}.
     */
    public static boolean amINearestMateToPoint(RawWorldData vWorldState, ReferencePoint aRefPoint, BotInformation aSelf) {
        List<FellowPlayer> vTeamMates = vWorldState.getListOfTeamMates();
        vTeamMates.add(new FellowPlayer(aSelf.getVtId(),"me", true, 0, 0, 0));

        FellowPlayer closest_player = null;
        for (FellowPlayer p: vTeamMates) {
            double distNew = aRefPoint.sub(p).getDistanceToPoint();
            double distOld = aRefPoint.sub(closest_player).getDistanceToPoint();

            if (closest_player == null || distNew < distOld) {
                closest_player = p;
            }
        }
        // confirm that it is realy you
        if (closest_player.getId() == aSelf.getVtId()) {
            return true;
        }

        return false;
    }
}
