package inc.appscode0.actionbound;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.zxing.Result;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.logging.Logger;

import cn.pedant.SweetAlert.SweetAlertDialog;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static inc.appscode0.actionbound.ChooseStage_FinishBound.previous;
import static inc.appscode0.actionbound.ChooseStage_FinishBound.qrcode;

public class QrScanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    String what="";
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == android.R.id.home) {
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        // Programmatically initialize the scanner view



        getSupportActionBar().setTitle("Bar code Scan");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        try
        {
            mScannerView = new ZXingScannerView(this);
            setContentView(mScannerView);


        } catch (Exception ex) {
//            Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT).show();
        }

        try
        {


        }catch (Exception ex)
        {

        }



    }

    @Override
    public void onResume() {
        super.onResume();
        // Register ourselves as a handler for scan results.
        mScannerView.setResultHandler(this);
        // Start camera on resume
        mScannerView.startCamera();

        //  new IntentIntegrator(this).initiateScan();
    }

    @Override
    public void onPause() {
        super.onPause();
        // Stop camera on pause
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {

        previous=previous+1;



        Toast.makeText(this, String.valueOf(what), Toast.LENGTH_SHORT).show();
        if(qrcode==1)
        {
            final AlertDialog.Builder alert =  new AlertDialog.Builder(getApplicationContext());
            alert.setMessage(String.valueOf(rawResult.getText()));
            alert.setTitle("Hurray!");
            alert.setCancelable(false);
            alert.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    qrcode=0;
                    startActivity(new Intent(QrScanner.this, ChooseStage_FinishBound.class));
                }
            });
//            alert.show();
        }
        else {
            Toast.makeText(this, "heeey", Toast.LENGTH_SHORT).show();
        }



    }
}
