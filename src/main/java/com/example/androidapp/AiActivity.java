package com.example.androidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AiActivity extends AppCompatActivity {
RecyclerView recyclerView;
TextView welcommessagetextview;
EditText messageeditText;
ImageButton sendbutton;
List<Message> messageList;
MessageAdapter messageAdapter;

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai);
        messageList=new ArrayList<>();
        recyclerView=findViewById(R.id.recyclerview);
        sendbutton=findViewById(R.id.sendbtn);
        messageeditText=findViewById(R.id.messagetext);
        welcommessagetextview=findViewById(R.id.welcometxt);
        messageAdapter=new MessageAdapter(messageList);
        recyclerView.setAdapter(messageAdapter);
        LinearLayoutManager llm=new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        recyclerView.setLayoutManager(llm);

        sendbutton.setOnClickListener((v) -> {
            String question =messageeditText.getText().toString().trim();
            addToChat(question,Message.Sent_By_Me);
            messageeditText.setText("");
            callAPI(question);
            welcommessagetextview.setVisibility(View.GONE);

        });
    }
void addToChat(String message, String sentBy){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageList.add(new Message(message,sentBy));
                messageAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
            }
        });

}
void addResponse(String response){
        messageList.remove(messageList.size()-1);
        addToChat(response,Message.Sent_By_Bot);
}

    void callAPI(String question){
        messageList.add(new Message("Typing...",Message.Sent_By_Bot));
        JSONObject jsonBody=new JSONObject();
        try {
            jsonBody.put("model","text-davinci-003");
            jsonBody.put("prompt",question);
            jsonBody.put("max_tokens",4000);
            jsonBody.put("temperature",0);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        RequestBody body= RequestBody.create(jsonBody.toString(),JSON);
        Request request=new Request.Builder()
                .url("https://api.openai.com/v1/completions")
                .header("Authorization","Bearer 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")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                addResponse("Failed to load response due to"+e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    JSONObject jsonObject= null;
                    try {
                        jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray= jsonObject.getJSONArray("choices");
                        String result=jsonArray.getJSONObject(0).getString("text");
                        addResponse(result.trim());
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }else {
                    addResponse("Failed to load response due to"+response.body().toString());

                }
            }
        });
    }
}