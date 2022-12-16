/*
 * Copyright 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.glass.qrcodescannersample;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;

import com.example.glass.ui.GlassGestureDetector.Gesture;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

/**
 * This activity scans a QR code and shows the result.
 */
public class MainActivity extends BaseActivity {

  private static final int REQUEST_CODE = 105;
  private TextView resultLabel;
  private TextView scanResult;
  private int flag = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    resultLabel = findViewById(R.id.resultLabel);
    scanResult = findViewById(R.id.scanResult);
  }

  /**
   * Shows the detected {@link String} if the QR code was successfully read.
   */
  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
      if (data != null) {
        final String qrData = data.getStringExtra(CameraActivity.QR_SCAN_RESULT);
        resultLabel.setVisibility(View.VISIBLE);
        scanResult.setVisibility(View.VISIBLE);
        scanResult.setText(qrData);
      }
    }
  }
public void makeQRCode(){
    if(flag==1){
      return;
    }
    int width = 150;
    int height=150;
      QRCodeWriter writer = new QRCodeWriter();
      String url = null;
      url = "aibver.com/!!!/C:/Users/user/Desktop/test/sofa.jpg";
      MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
  try{
    BitMatrix bitMatrix = multiFormatWriter.encode(url, BarcodeFormat.QR_CODE,width,height);
    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
    MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "test1", "test1-description");
  flag=1;
  }catch (Exception e){
      e.printStackTrace();
    }


}
  /**
   * Hides previously shown QR code string and starts scanning QR Code on {@link Gesture#TAP}
   * gesture. Finishes application on {@link Gesture#SWIPE_DOWN} gesture.
   */
  @Override
  public boolean onGesture(Gesture gesture) {
    switch (gesture) {
      case TAP:
//        Intent(this, CameraActivity.class) = this에서 CameraActivity.class를 호출
        startActivityForResult(new Intent(this, CameraActivity.class), REQUEST_CODE);
        resultLabel.setVisibility(View.GONE);
        scanResult.setVisibility(View.GONE);
        return true;
      case SWIPE_DOWN:
        finish();
        return true;
      case SWIPE_UP:
        makeQRCode();
      default:
        return false;
    }
  }
}
