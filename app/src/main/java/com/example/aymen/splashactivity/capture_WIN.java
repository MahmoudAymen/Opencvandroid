package com.example.aymen.splashactivity;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;


public class capture_WIN extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener,View.OnTouchListener{
    private static final String TAG = "Capture_Win";
    private Bitmap BitmapResult1;
    private double M00;
    private double M01;
    private double M10;
    private Mat capture;
    private Scalar colorW = new Scalar(255.0D, 255.0D, 255.0D, 255.0D);
    private Mat contourMat;
    private List<MatOfPoint> contours;
    private lesionDetector lesionDetector;
   private Scalar mBlobColorHsv;
    private Mat mHierarchy;
    private CameraBridgeViewBase mOpenCvCameraView;
    private Mat mRgba;
    private Moments moments;
    private double posX;
    private double posY;
    private Mat resultsMat;
    private Bitmap txt1;
    private Mat txt1Mat;
    Button b1;
    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this)
    {
        public void onManagerConnected(int paramInt)
        {
            switch (paramInt)
            {
                default:
                    super.onManagerConnected(paramInt);
                    return;
                case 0:

            }
            Log.d("OCVSample::Activity", "OpenCV loaded successfully");
           capture_WIN.this.mOpenCvCameraView.enableView();
            capture_WIN.this.mOpenCvCameraView.setOnTouchListener(capture_WIN.this);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().addFlags(128);
        setContentView(R.layout.activity_capture__win);
        this.mOpenCvCameraView = (CameraBridgeViewBase)findViewById(R.id.surface_view);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        this.mOpenCvCameraView.setCvCameraViewListener(this);
        ArrayList<View> views = new ArrayList<View>();
        b1= (Button) findViewById(R.id.btnOK);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aymen();
            }
        });
        mOpenCvCameraView.addTouchables(views);


    }
    @Override
    protected void onPause() {
        super.onPause();
        if (this.mOpenCvCameraView != null) {
            this.mOpenCvCameraView.disableView();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.mOpenCvCameraView != null) {
            this.mOpenCvCameraView.disableView();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!OpenCVLoader.initDebug())
        {
            Log.d(TAG, "opencv not Loaded");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_11,this,mLoaderCallback);

        }
        else
        {
            Log.d(TAG, "opencv Loaded");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    @Override
    public void onCameraViewStarted(int paramInt1, int paramInt2) {
        Log.d(TAG, "op1");
        this.mRgba = new Mat(paramInt2, paramInt1, CvType.CV_8UC4);
        Log.d(TAG, "op2");
        this.mHierarchy = new Mat();
        Log.d(TAG, "op3");
        this.mBlobColorHsv = new Scalar(255.0D);
        Log.d(TAG, "op4");
        this.capture = new Mat();
        Log.d(TAG, "op5");
        this.txt1 = BitmapFactory.decodeResource(getResources(), 2130837537);
        Log.d(TAG, "op6");
        this.txt1Mat = new Mat();
        Log.d(TAG, "op7");
        Utils.bitmapToMat(this.txt1, this.txt1Mat);
        Log.d(TAG, "op8");
    }

    @Override
    public void onCameraViewStopped() {
        Log.d(TAG,"rani1");
        this.mRgba.release();
        Log.d(TAG, "rani2");
    }

    @Override
    public Mat onCameraFrame(Mat paramMat) {
        Log.d(TAG, "rani3");
        this.mRgba = paramMat;
        Log.d(TAG, "rani4");
        this.resultsMat = null;
        Log.d(TAG, "rani5");
        Mat localMat1 = new Mat();
        Log.d(TAG, "rani6");
        new Mat();
        Log.d(TAG, "rani7");
        new Scalar(0.0D, 0.0D, 0.0D, 0.0D);
        Log.d(TAG, "rani8");
        new Mat();
        Log.d(TAG, "rani9");
        Rect localRect1 = new Rect(paramMat.width() / 2 - 100, paramMat.height() / 2 - 100, 200, 200);
        Log.d(TAG, "rani10");
        Mat localMat2 = this.mRgba.submat(localRect1);
        Log.d(TAG, "rani11");
        Imgproc.cvtColor(localMat2, localMat1, 1);
        Log.d(TAG, "rani12");
        Imgproc.cvtColor(localMat1, localMat1, 7);
        Log.d(TAG, "rani13");
        Imgproc.adaptiveThreshold(localMat1, localMat1, 255.0D, 0, 0, 13, 4.0D);
        Log.d(TAG, "rani14");
        Core.bitwise_not(localMat1, localMat1);
        Log.d(TAG, "rani 15");
        this.contours = new ArrayList();
        Log.d(TAG, "rani 16");
        Imgproc.findContours(localMat1, this.contours, this.mHierarchy, 1, 1);
        Log.d(TAG, "rani 17");
        int i = 0;
        Log.d(TAG, "rani 18");
        Rect localRect2 = new Rect();
        Log.d(TAG, "rani19");
        new Rect();
        Log.d(TAG, "rani 20 ");
        int j = 0;
        Log.d(TAG, "rani 21");
        double d1 = 0.0D;
        Log.d(TAG, "rani 22");
        double d2 = 0.0D;
        Log.d(TAG, "rani 23");
        this.contourMat = new Mat(localMat1.size(), CvType.CV_8UC3, new Scalar(0.0D, 0.0D, 0.0D));
        Log.d(TAG, "rani 24147");
        for (int k = 0; ; k++) {
            Log.d(TAG, "rani 24");
            int m = this.contours.size();
            Log.d(TAG, "rani 25");
            if (k >= m) {
                Log.d(TAG, "rani 26");
                if (j != 0) {
                    Log.d(TAG, "rani 26");
                    Core.mean(localMat2.submat(localRect2).clone());
                    Log.d(TAG, "rani 27");
                    Core.rectangle(localMat2, localRect2.tl(), localRect2.br(), this.colorW);
                    Log.d(TAG, "rani 28");
                    Core.circle(localMat2, new Point(d1, d2), 1, this.colorW);
                    Log.d(TAG, "rani 29");
                }
                Log.d(TAG,"rani 30");
                Core.rectangle(this.mRgba, new Point(-100 + paramMat.width() / 2, -100 + paramMat.height() / 2), new Point(100 + paramMat.width() / 2, 100 + paramMat.height() / 2), this.colorW);
                Log.d(TAG, "rani 31");
                Core.circle(this.mRgba, new Point(paramMat.width() / 2, paramMat.height() / 2), 40, this.colorW, 1);
                Log.d(TAG, "rani 32");
                int i6 = this.mRgba.height() / 2 - this.txt1Mat.height() / 2;
                Log.d(TAG,"rani 33");
                Point localPoint1 = new Point(70, i6);
                Log.d(TAG,"rani 34");
                Rect localRect4 = new Rect(localPoint1, new Size(this.txt1Mat.width(), this.txt1Mat.height()));
                Log.d(TAG,"rani 35");
                this.txt1Mat.copyTo(this.mRgba.submat(localRect4));
                Log.d(TAG, "rani 36");
                if (this.resultsMat != null)
                {
                    Log.d(TAG,"rani 37");
                    Imgproc.resize(this.resultsMat, this.resultsMat, new Size(150.0D, 150.0D));
                    Log.d(TAG, "rani 38");
                    this.lesionDetector = new lesionDetector();
                    Log.d(TAG,"rani 39");
                    int i7 = 70 ;
                    Log.d(TAG,"rani 40");
                    int i8 = i6 + 30;
                    Log.d(TAG,"rani 41");
                    Point localPoint2 = new Point(i7, i8);
                    Log.d(TAG,"rani 42");
                    Point localPoint3 = new Point(365, i8 + 150);
                    Log.d(TAG,"rani 43");
                    Rect localRect5 = new Rect(localPoint2, localPoint3);
                    Log.d(TAG,"rani 44");
                    this.resultsMat.copyTo(this.mRgba.submat(localRect5));
                    Log.d(TAG, "rani 45");
                    Core.rectangle(this.mRgba, localPoint2, localPoint3, this.colorW);
                    Log.d(TAG, "rani 46");
                    new Point(45, i8 + 160);
                    Log.d(TAG,"rani 47");
                    new Point(105, 60 + (i8 + 160));
                    Log.d(TAG,"rani 48");
                }
                return this.mRgba;
            }
            Log.d(TAG,"rani 49");
            double d3 = Imgproc.contourArea((Mat)this.contours.get(k));
            Log.d(TAG,"rani 50");
            this.moments = Imgproc.moments((Mat)this.contours.get(k));
            Log.d(TAG,"rani 51");
            this.M00 = this.moments.get_m00();
            Log.d(TAG,"rani 52");
            this.M10 = this.moments.get_m10();
            Log.d(TAG,"rani 53");
            this.M01 = this.moments.get_m01();
            Log.d(TAG,"rani 54");
            this.posX = (int)(this.M10 / this.M00);
            Log.d(TAG,"rani 55");
            this.posY = (int)(this.M01 / this.M00);
            Log.d(TAG,"rani 56");
            int n = (int)Math.sqrt(Math.pow(this.posX - 100.0D, 2.0D) + Math.pow(this.posY - 100.0D, 2.0D));
            Log.d(TAG,"rani 57");
            if ((d3 <= i) || (n >= 20) || (d3 <= 70.0D)) {
                Log.d(TAG, "rani 58");
                continue;
            }
            Log.d(TAG,"rani 59");
            i = (int)d3;
            Log.d(TAG,"rani 60");
            int i1 = k;
            Log.d(TAG,"rani 61");
            localRect2 = Imgproc.boundingRect((MatOfPoint)this.contours.get(i1));
            Log.d(TAG,"rani 62");
            int i2 = (int)Math.max(localRect2.tl().x - 20, 1.0D);
            Log.d(TAG,"rani 63");
            int i3 = (int)Math.max(localRect2.tl().y - 20, 1.0D);
            Log.d(TAG,"rani 64");
            int i4 = (int)Math.min(localRect2.br().x + 20, -1 + localMat2.width());
            Log.d(TAG,"rani 65");
            int i5 = (int)Math.min(localRect2.br().y + 20, -1 + localMat2.height());
            Log.d(TAG,"rani 66");
            Rect localRect3 = new Rect(new Point(i2, i3), new Point(i4, i5));
            Log.d(TAG,"rani 67");
            this.resultsMat = localMat2.submat(localRect3).clone();
            Log.d(TAG,"rani 68");
            j = 1;
            d1 = this.posX;
            d2 = this.posY;
        }
        }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d(TAG,"rani bdit n5adem");
      /*  if (this.resultsMat != null)
        {
            Log.d(TAG,"rani bdit n5adem1");
            Intent localIntent = new Intent(getBaseContext(), MED_APP.class);
            Log.d(TAG,"rani bdit n5adem2");
            localIntent.setFlags(67108864);
            Log.d(TAG, "rani bdit n5adem3");
            Mat localMat = new Mat(new Size(150,150 ), this.resultsMat.type());
            Log.d(TAG,"rani bdit n5adem4");
            Imgproc.resize(this.resultsMat, localMat, localMat.size());
            Log.d(TAG, "rani bdit n5adem5");
            this.BitmapResult1 = Bitmap.createBitmap(localMat.width(), localMat.height(), Bitmap.Config.ARGB_8888);
            Log.d(TAG,"rani bdit n5adem6");
            Utils.matToBitmap(localMat, this.BitmapResult1);
            Log.d(TAG, "rani bdit n5adem7");
            ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
            Log.d(TAG,"rani bdit n5adem8");
            this.BitmapResult1.compress(Bitmap.CompressFormat.PNG, 50, localByteArrayOutputStream);
            Log.d(TAG, "rani bdit n5adem9");
            localIntent.putExtra("image1", localByteArrayOutputStream.toByteArray());
            Log.d(TAG, "rani bdit n5adem10");
            startActivity(localIntent);
            Log.d(TAG, "rani bdit n5adem11");
        }*/

        return false;
    }
    public void aymen()
    {
        if (this.resultsMat != null)
        {
            Log.d(TAG,"rani bdit n5adem1");
            Intent localIntent = new Intent(getBaseContext(), MED_APP.class);
            Log.d(TAG,"rani bdit n5adem2");
            localIntent.setFlags(67108864);
            Log.d(TAG, "rani bdit n5adem3");
            Mat localMat = new Mat(new Size(150,150 ), this.resultsMat.type());
            Log.d(TAG,"rani bdit n5adem4");
            Imgproc.resize(this.resultsMat, localMat, localMat.size());
            Log.d(TAG, "rani bdit n5adem5");
            this.BitmapResult1 = Bitmap.createBitmap(localMat.width(), localMat.height(), Bitmap.Config.ARGB_8888);
            Log.d(TAG,"rani bdit n5adem6");
            Utils.matToBitmap(localMat, this.BitmapResult1);
            Log.d(TAG, "rani bdit n5adem7");
            ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
            Log.d(TAG,"rani bdit n5adem8");
            this.BitmapResult1.compress(Bitmap.CompressFormat.PNG, 50, localByteArrayOutputStream);
            Log.d(TAG, "rani bdit n5adem9");
            localIntent.putExtra("image1", localByteArrayOutputStream.toByteArray());
            Log.d(TAG, "rani bdit n5adem10");
            startActivity(localIntent);
            Log.d(TAG, "rani bdit n5adem11");
        }

    }

}
