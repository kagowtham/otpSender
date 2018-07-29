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

public class Tab2 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TouchImageView touchImageView=view.findViewById(R.id.image_tab);
        touchImageView.setImageResource(R.drawable.c_sharp_code);
        Button button=view.findViewById(R.id.button_copy);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text="var no=9999999990; //PHONE NUMBER\n" +
                        "var n= ; // TOKEN NAME\n" +
                        "var m=\"otp message\"; // MESSAGE\n" +
                        "\n" +
                        "var values = new Dictionary<string, string>\n" +
                        "{\n" +
                        "   { \"name\", n},\n" +
                        "   { \"no\",no},\n" +
                        "   { \"msg\",m}\n" +
                        "};\n" +
                        "\n" +
                        "var content = new FormUrlEncodedContent(values);\n" +
                        "\n" +
                        "var response = await client.PostAsync(\"http://famousfoodnearby-mydb1234.193b.starter-ca-central-1.openshiftapps.com/Send\", content);\n" +
                        "\n" +
                        "var responseString = await response.Content.ReadAsStringAsync();";
                ClipboardManager clipboard = (ClipboardManager)getActivity().getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(ClipDescription.MIMETYPE_TEXT_PLAIN, text);
                clipboard.setPrimaryClip(clip);
            }
        });
    }
}
