package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class RobotCentric extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // Make sure your ID's match your configuration
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("backRightMotor");


        DcMotor downSlide = hardwareMap.get(DcMotor.class, "downSlide");
        DcMotor upSlide = hardwareMap.get(DcMotor.class, "upSlide");

        Servo rotateClawServo = hardwareMap.servo.get("rotateClawServo");
        Servo openClawServo = hardwareMap.servo.get("openClawServo");
        Servo rotateBucketServo = hardwareMap.servo.get("rotateBucketServo");



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

            if (gamepad1.atRest()) {
                downSlide.setPower(0);
                upSlide.setPower(0);
            }
            while (gamepad2.dpad_left) {
                downSlide.setPower(1);
            }
            while (gamepad2.dpad_right) {
                downSlide.setPower(-1);
            }
            while (gamepad2.dpad_up) {
                upSlide.setPower(1);
            }
            while (gamepad2.dpad_down) {
                upSlide.setPower(-1);
            }
            if (gamepad2.a) {
                rotateClawServo.setPosition(0);
            }
            if (gamepad2.b) {
                rotateClawServo.setPosition(0.6);
            }
            if (gamepad2.x) {
                openClawServo.setPosition(0.1);
            }
            if (gamepad2.y) {
                openClawServo.setPosition(0.3);
            }
            if (gamepad2.left_bumper) {
                rotateBucketServo.setPosition(0);
            }
            if (gamepad2.right_bumper) {
                rotateBucketServo.setPosition(0.5);
            }
        }
    }
}
