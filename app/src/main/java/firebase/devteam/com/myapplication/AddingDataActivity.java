package firebase.devteam.com.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.text.DateFormat;

public class AddingDataActivity extends AppCompatActivity {
    EditText Title,Desc,ImageURL,SiteURL;
    Button button;
    DatabaseReference mDatabase,mDatabase2;
    String mTitle ,mDesc,mImageURL,mSiteURL;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    int highScore;
    String publishDate;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTitle = Title.getText().toString();
                mDesc = Desc.getText().toString();
                mImageURL = ImageURL.getText().toString();
                mSiteURL = SiteURL.getText().toString();

                //check that user has entered a valid inputs in title and description EditTexts
                if(!TextUtils.isEmpty(mTitle) && !TextUtils.isEmpty(mDesc) &&!TextUtils.isEmpty(mImageURL) &&!TextUtils.isEmpty(mSiteURL)) {
                    writeNewUser(mTitle, mDesc, mImageURL, mSiteURL,publishDate);
                    editor.putInt(getString(R.string.saved_high_score_key), --highScore);
                    editor.commit();
                    mDatabase2.setValue(highScore);
                    Toast.makeText(AddingDataActivity.this, "Done", Toast.LENGTH_SHORT).show();
                    Title.setText("");
                    Desc.setText("");
                    ImageURL.setText("");
                    SiteURL.setText("");
                }else{
                    Toast.makeText(AddingDataActivity.this, "Some Fields are required!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    private void init() {
        Title = (EditText)findViewById(R.id.Title);
        Desc = (EditText)findViewById(R.id.Desc);
        ImageURL = (EditText)findViewById(R.id.ImageUrl);
        SiteURL = (EditText)findViewById(R.id.URL);
        button = (Button)findViewById(R.id.AddDataButton);

        preferences = getSharedPreferences("Child", Context.MODE_PRIVATE);
        editor = preferences.edit();
        int defaultValue = getResources().getInteger(R.integer.saved_high_score_default_key);
        highScore = preferences.getInt(getString(R.string.saved_high_score_key), defaultValue);



        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formater = new SimpleDateFormat("HH:mm");

        String date = DateFormat.getDateInstance(DateFormat.YEAR_FIELD).format(calendar.getTime());
        String time  = formater.format(calendar.getTime());
        publishDate = " " + date + " at " + time;

        mDatabase = FirebaseDatabase.getInstance().getReference().child("News");
        mDatabase2 = FirebaseDatabase.getInstance().getReference().child("NewsNumber").child("number");
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                highScore = dataSnapshot.getValue(Integer.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase2.addValueEventListener(listener);

    }
    private void writeNewUser(String _title,String _desc,String _imageUrl,String _siteURL,String _publishDate) {


        String childeIndexNumber  = String.valueOf(highScore);
        mDatabase.child(childeIndexNumber).child("title").setValue(_title);
        mDatabase.child(childeIndexNumber).child("desc").setValue(_desc);
        mDatabase.child(childeIndexNumber).child("image").setValue(_imageUrl);
        mDatabase.child(childeIndexNumber).child("publishDate").setValue(publishDate);
        mDatabase.child(childeIndexNumber).child("url").setValue(_siteURL);

    }
}
