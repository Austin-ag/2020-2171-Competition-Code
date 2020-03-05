package frc.robot;

class Constants
{
    /*S T A R T  M O T O R  C O N S T A N T S*/

    public static final int frontLeftMotor = 6;

    public static final int backLeftMotor = 5;

    public static final int topLeftMotor = 4;

    public static final int frontRightMotor = 3;

    public static final int backRightMotor = 2;

    public static final int topRightMotor = 1;

    public static final int flywheelMaster = 7;

    public static final int flywheelSlave = 8;

    public static final int intake = 1;

    public static final int liftMaster = 0;

    public static final int liftSlave = 0;

    public static final int index = 0;

    /*E N D  M O T O R  C O N S T A N T S*/

    public static int PIDSlotID = 0;

    public static final int timeoutMS = 30;

    public static final int PIDLoopID = 0;

    /*D R I V E  T R A I N  C O N S T A N S T S*/

    public static final double velocKpDrive = 1;

    public static final double velocKiDrive = 0;

    public static final double velocKdDrive = 0;

    public static final double velocKfDriveleft = 1.405;

    public static final double velocKfDriveright = 1.271;

    /*F L Y W H E E L  C O N S T A N S T S*/

    public static final double velocKpFly = .015;

    public static final double velocKiFly = 0;

    public static final double velocKdFly = 0;

    public static final double velocKfFly = .049;//NEVER change this

    /*L I F T  C O N S T A N T S*/

    public static final double posKpLift = 0;

    public static final double posKiLift = 0;

    public static final double posKdLift = 0;

    public static final double posKfLift = .049;
}