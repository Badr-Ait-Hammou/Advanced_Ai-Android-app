package com.example.androidapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {
    List<Message> messageList;
    public MessageAdapter(List<Message> messageList) {
        this.messageList=messageList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View chatView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatitem,null);
       MyViewHolder myViewHolder=new MyViewHolder(chatView);
       return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    Message message=messageList.get(position);
    if(message.getSentBy().equals(Message.Sent_By_Me)){
        holder.leftChatView.setVisibility(View.GONE);
        holder.rightChatView.setVisibility(View.VISIBLE);
        holder.rightTextView.setText(message.getMessage());
    }else {
        holder.leftChatView.setVisibility(View.VISIBLE);
        holder.rightChatView.setVisibility(View.GONE);
        holder.leftTextView.setText(message.getMessage());
    }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        LinearLayout leftChatView,rightChatView;
        TextView leftTextView,rightTextView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            leftChatView=itemView.findViewById(R.id.leftchatview);
            rightChatView=itemView.findViewById(R.id.rightchatview);
            leftTextView=itemView.findViewById(R.id.leftchattextview);
            rightTextView=itemView.findViewById(R.id.rightchattextview);
        }
    }

}
