package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class RobotCentric extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // Make sure your ID's match your configuration
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("FrontLeft");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("BackLeft");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("FrontRight");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("BackRight");


        CRServo claw = hardwareMap.crservo.get("claw");
        Servo rotate = hardwareMap.get(Servo.class, "rotate");
        DcMotor rslide = hardwareMap.get(DcMotor.class, "RightSlide");
        rslide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rslide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        DcMotor lslide = hardwareMap.get(DcMotor.class, "LeftSlide");
        lslide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lslide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        // Reverse the right side motors. This may be wrong for your setup.
        // If your robot moves backwards when commanded to go forwards,
        // reverse the left side instead.
        // See the note about this earlier on this page.
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x;

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(backRightPower);

            if (gamepad2.a) {
                claw.setPower(-1);
            }
            if (gamepad2.b) {
                claw.setPower(1);
            }
            if (gamepad2.x) {
                rotate.setPosition(1);
            }
            if (gamepad2.y) {
                rotate.setPosition(0.2);
            }
            if (gamepad2.right_bumper) {
                rotate.setPosition(-1);
            }
            if (gamepad2.dpad_down) {
                rslide.setPower(1);
                lslide.setPower(-1);
            } else if (gamepad2.dpad_up) {
                rslide.setPower(-1);
                lslide.setPower(1);
            } else {
                rslide.setPower(0);
                lslide.setPower(0);
            }
        }
    }
}
