package ir.blog.mrcoder.darsadyar;

import android.app.Activity;
import android.os.Bundle;

public class AboutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

//    public void webClicked(View v) {
//        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://mrcoder.blog.ir"));
//        startActivity(browserIntent);
//    }
}
