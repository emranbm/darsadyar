package ir.blog.mrcoder.darsadyar;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class AboutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    public void webClicked(View v) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://mrcoder.blog.ir"));
        startActivity(browserIntent);
    }
}
