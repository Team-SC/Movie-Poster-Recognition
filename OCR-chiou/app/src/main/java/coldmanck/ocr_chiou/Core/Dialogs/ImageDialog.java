package coldmanck.ocr_chiou.Core.Dialogs;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import coldmanck.ocr_chiou.R;

/**
 * Created by Fadi on 5/11/2014.
 */
public class ImageDialog extends DialogFragment {

    private Bitmap bmp;

    private String title;

    public ImageDialog(){}

    public static ImageDialog New(){
        return new ImageDialog();
    }

    public ImageDialog addBitmap(Bitmap bmp) {
        if (bmp != null)
            this.bmp = bmp;
        return this;
    }

    public ImageDialog addTitle(String title) {
        if (title != null)
            this.title = title;
        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.image_dialog, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.image_dialog_imageView);
        TextView textView = (TextView) view.findViewById(R.id.image_dialog_textView);

        if (bmp != null)
            imageView.setImageBitmap(bmp);

        if(title!=null)
            textView.setText(this.title);

        return view;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        bmp.recycle();
        bmp = null;
        System.gc();
        super.onDismiss(dialog);
    }
}