package net.onefree.aiphone.ui.activity;

import net.onefree.aiphone.R;
import net.onefree.aiphone.utils.SystemUtils;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class QrCodeResultActivity extends BaseActivity {

    private ImageView mResultImage;
    private TextView mResultText;
    private String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_result);

        Bundle extras = getIntent().getExtras();

        mResultImage = (ImageView) findViewById(R.id.result_image);
        mResultText = (TextView) findViewById(R.id.result_text);

        if (null != extras) {
            int width = extras.getInt("width");
            int height = extras.getInt("height");

//            LayoutParams lps = new LayoutParams(width, height);
//            lps.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
//            lps.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
//            lps.rightMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
//
//            mResultImage.setLayoutParams(lps);

            result = extras.getString("result");
            mResultText.setText(result);

//            Bitmap barcode = null;
//            byte[] compressedBitmap = extras.getByteArray(DecodeThread.BARCODE_BITMAP);
//            if (compressedBitmap != null) {
//                barcode = BitmapFactory.decodeByteArray(compressedBitmap, 0, compressedBitmap.length, null);
//                // Mutable copy:
//                barcode = barcode.copy(Bitmap.Config.RGB_565, true);
//            }
//
//            mResultImage.setImageBitmap(barcode);
        }
    }

    public void initView() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setTitle("二维码扫描结果");
        actionBar.setSplitBackgroundDrawable(getResources().getDrawable(R.color.activity_bg_color_a));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_qrcode_result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_copy:
                SystemUtils.copy(result, this);
                Toast.makeText(this, "复制到剪贴版", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, result);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "分享二维码内容"));
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
