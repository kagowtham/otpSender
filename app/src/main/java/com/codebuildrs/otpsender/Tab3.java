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

public class Tab3 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TouchImageView touchImageView=view.findViewById(R.id.image_tab);
        touchImageView.setImageResource(R.drawable.android_code);
        Button button=view.findViewById(R.id.button_copy);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text="\n" +
                        "\n" +
                        "\n" +
                        "            String encoded_message= \"otp+4563\";\n" +
                        "            String mainUrl=\"http://famousfoodnearby-mydb1234.193b.starter-ca-central-1.openshiftapps.com/Send?\";\n" +
                        "\n" +
                        "            StringBuilder sbPostData= new StringBuilder(mainUrl);\n" +
                        "            sbPostData.append(\"name=\");//TOKEN NAME\n" +
                        "            sbPostData.append(\"&msg=\"+encoded_message);//MESSAGE\n" +
                        "            sbPostData.append(\"&to=\"+\"999999996\");//TOKEN NAME\n" +
                        "            mainUrl = sbPostData.toString();\n" +
                        "            try\n" +
                        "            {\n" +
                        "                //prepare connection\n" +
                        "                myURL = new URL(mainUrl);\n" +
                        "                myURLConnection = myURL.openConnection();\n" +
                        "                myURLConnection.connect();\n" +
                        "                reader= new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));\n" +
                        "                //reading response\n" +
                        "                String response;\n" +
                        "                while ((response = reader.readLine()) != null)\n" +
                        "                    //print response\n" +
                        "                    Log.d(\"RESPONSE\", \"\"+response);\n" +
                        "                //finally close connection\n" +
                        "                reader.close();\n" +
                        "            }\n" +
                        "            catch (IOException e)\n" +
                        "            {\n" +
                        "                e.printStackTrace();\n" +
                        "            }";
                ClipboardManager clipboard = (ClipboardManager)getActivity().getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(ClipDescription.MIMETYPE_TEXT_PLAIN, text);
                clipboard.setPrimaryClip(clip);
            }
        });
    }
}
