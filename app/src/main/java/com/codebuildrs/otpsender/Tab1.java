package com.codebuildrs.otpsender;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by manoj on 29-03-2018.
 */

public class Tab1 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TouchImageView touchImageView=view.findViewById(R.id.image_tab);
        touchImageView.setImageResource(R.drawable.web_code);
        Button button=view.findViewById(R.id.button_copy);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text="<html>\n" +
                        " <title>otp send</title>\n" +
                        "   <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js\">\n" +
                        "   </script>\n" +
                        "   <script>\n" +
                        "     function func(){\n" +
                        "\t   var no=document.getElementById(\"no\").value; //PHONE NUMBER\n" +
                        "\t   var n=; // TOKEN NAME\n" +
                        "\t   var m=\"otp message\"; // MESSAGE\n" +
                        "\t   $.post( \"http://famousfoodnearby-mydb1234.193b.starter-ca-central-1.openshiftapps.com/Send\",\n" +
                        "\t   { name: n, to: no,msg: m});\n" +
                        "      \n" +
                        "\t }\n" +
                        "   </script>\n" +
                        "   <body>\n" +
                        "     <input id=\"no\"></input>\n" +
                        "\t <button onclick=\"func()\">send otp</button>\n" +
                        "   </body>\n" +
                        "</html>";
                ClipboardManager clipboard = (ClipboardManager)getActivity().getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(ClipDescription.MIMETYPE_TEXT_PLAIN, text);
                clipboard.setPrimaryClip(clip);
            }
        });
    }
}
