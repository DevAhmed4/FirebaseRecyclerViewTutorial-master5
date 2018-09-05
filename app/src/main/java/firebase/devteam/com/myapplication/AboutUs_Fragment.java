package firebase.devteam.com.myapplication;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AboutUs_Fragment extends Fragment {
    public AboutUs_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_about_us_, container, false);
        CardView  cardView = (CardView)v.findViewById(R.id.facebookid);
        CardView  ycardView = (CardView)v.findViewById(R.id.yid);
        CardView  tcardView = (CardView)v.findViewById(R.id.twitterid);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserintent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/waziftyy/"));
                startActivity(browserintent);
            }
        });
        ycardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserintent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/channel/UCoq8CDv46n7VIwlHReVOGlQ?view_as=subscriber"));
                startActivity(browserintent);
            }
        });
        tcardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserintent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.Twitter.com/con2nect/"));
                startActivity(browserintent);
            }
        });

        return v;
    }



}
