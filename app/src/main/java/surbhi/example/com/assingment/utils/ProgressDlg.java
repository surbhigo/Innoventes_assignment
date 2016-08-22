package surbhi.example.com.assingment.utils;
import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by S on 8/21/2016.
 */
public class ProgressDlg {
    private static ProgressDialog progressDialog;

    public static void showProgressDialog(Context context, String Message) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage(Message);
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }
    }



    // function to hide the loading dialog box
    public static void hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

}
