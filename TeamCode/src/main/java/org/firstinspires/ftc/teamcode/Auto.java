package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.commandftc.RobotUniversal;
import org.firstinspires.ftc.teamcode.lib.PIDController;
import org.firstinspires.ftc.teamcode.subsystems.DriveTrainSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.StorageSubSystem;
import org.firstinspires.ftc.teamcode.subsystems.VisionSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.VuforiaSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.WobellSubsystem;

@Autonomous(name = "Auto")
public class Auto extends LinearOpMode {
    protected DriveTrainSubsystem driveTrain;
    protected ShooterSubsystem shooter;
    protected IntakeSubsystem intake;
    protected StorageSubSystem storage;
    protected WobellSubsystem wobellSubsystem;
    protected VisionSubsystem vision;
    protected VuforiaSubsystem vuforia;

    private long state;

    @Override
    public void runOpMode() {
        state = 0;
        RobotUniversal.telemetry = telemetry;
        RobotUniversal.opMode = this;
        RobotUniversal.hardwareMap = hardwareMap;

        driveTrain = new DriveTrainSubsystem();
        shooter = new ShooterSubsystem();
        intake = new IntakeSubsystem();
        storage = new StorageSubSystem();
        wobellSubsystem = new WobellSubsystem();
//        vision = new VisionSubsystem();
//        vuforia = new VuforiaSubsystem();

        wobellSubsystem.close();
        shooter.setLift(0.20);

        driveTrain.set_for_autonomous();
        storage.set_for_autonomous();

        telemetry.addData("time", this::getRuntime);
        telemetry.addData("state", () -> state);
//        telemetry.addData("Target", vuforia::Visible_Target);
//        telemetry.addData("lift", shooter::getLift);
//        telemetry.addData("shooter(velocity)", shooter::getLeftVelocity);
        telemetry.addData("Storage", storage::getEncoder);
//        telemetry.addData("RL", driveTrain::getRearLeftEncoder);
//        telemetry.addData("RR", driveTrain::getRearRightEncoder);
//        telemetry.addData("FL", driveTrain::getFrontLeftEncoder);
//        telemetry.addData("FR", driveTrain::getFrontRightEncoder);
        telemetry.update();

        waitForStart();

        shooter.setPower(0.55);
        driveTrain.setPower(0.7);
        driveForward(-2000);
        sleep(200);
//        spin(-60);
        wait_for_shooter(2500);
        driveTrain.setPower(1);
        driveLeft(20);
        shoot_ring();
        driveLeft(-45);
        shoot_ring();
        driveLeft(-45);
        shoot_ring();
        shooter.setPower(0);
//        while (opModeIsActive()) {
//            telemetry.update();
//        }
        wobell_C();
    }

    private void driveForward(double mm) {
        state++;
        driveTrain.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        driveTrain.driveForwardDistance(mm);
        while (driveTrain.isBusy()) {
            telemetry.update();
        }
    }

    private void spin(double spin) {
        state++;
        driveTrain.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        driveTrain.spinLeftDistance(spin);
        while (driveTrain.isBusy() && opModeIsActive())
            telemetry.update();
    }

    private void driveLeft(double mm) {
        state++;
        driveTrain.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        driveTrain.driveLeftDistance(mm);
        while (driveTrain.isBusy() && opModeIsActive())
            telemetry.update();
    }

    private void driveDiagonalLeft(double mm) {
        state++;
        driveTrain.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        driveTrain.driveDiagonalLeft(mm);
        while (driveTrain.isBusy() && opModeIsActive())
            telemetry.update();
    }

    private void driveDiagonalRight(double mm) {
        state++;
        driveTrain.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        driveTrain.driveDiagonalRight(mm);
        while (driveTrain.isBusy() && opModeIsActive())
            telemetry.update();
    }

    private void shoot_ring() {
        state++;
        storage.index_distance(105);
        while (storage.isBusy() && opModeIsActive())
            telemetry.update();
    }

    private void wait_for_shooter(double velocity) {
        state++;
        while (shooter.getLeftVelocity() <= velocity && opModeIsActive())
            telemetry.update();
        sleep(1000);
    }

    private void wobell_A() {
        spin(-610);
        driveForward(-1300);
        wobellSubsystem.setLift(-1);
        sleep(1300);
        wobellSubsystem.open();
        sleep(100);
        wobellSubsystem.setLift(1);
        sleep(1000);
        wobellSubsystem.setLift(0);
    }

    private void wobell_B() {
        spin(-300);
        driveForward(-1000);
        wobellSubsystem.setLift(-1);
        sleep(1300);
        wobellSubsystem.open();
        sleep(100);
        wobellSubsystem.setLift(1);
        sleep(1000);
        wobellSubsystem.setLift(0);
    }

    private void wobell_C() {
        spin(-320);
        driveForward(-2100);
        wobellSubsystem.setLift(-1);
        sleep(1300);
        wobellSubsystem.open();
        sleep(100);
        wobellSubsystem.setLift(1);
        sleep(1000);
        wobellSubsystem.setLift(0);
    }
}
