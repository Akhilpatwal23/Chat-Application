package com.example.chatapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.auth.FirebaseAuth

private val NotificationCompat.MessagingStyle.Message.message: CharSequence?
    get() { return  message}
private val NotificationCompat.MessagingStyle.Message.senderId: String?
    get() { return senderId}

class MessageAdapter(val context: Context, val messageList: ArrayList<com.example.chatapp.Message>):
    RecyclerView.Adapter<ViewHolder>() {


    val ITEM_RECEVIE = 1
    val ITEM_SENT = 2
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder{
       if (viewType == 1){
           //inflate Receive
           val view: View = LayoutInflater.from(context).inflate(R.layout.receive,parent,false)
           return ReceiveViewHolder(view)
       }
       else{
           //inflate sent
           val view: View = LayoutInflater.from(context).inflate(R.layout.send,parent,false)
           return SentViewHolder(view)
       }
    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        val currentMessage = messageList[position]
        if(holder.javaClass== SentViewHolder::class.java) {

            //do the stuff for sent viewholder
            val viewHolder = holder as SentViewHolder
            holder.sentMessage.text = currentMessage.message   //message
        }
        else {
            //do stuff for receive view holder
            val viewHolder = holder as ReceiveViewHolder
            holder.receiveMessage.text = currentMessage.message  //message
        }
    }

    override fun getItemViewType(position: Int): Int {
       val currnetMessage = messageList[position]

        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currnetMessage.senderId)){
            return ITEM_SENT
        }
        else{
            return ITEM_RECEVIE
        }
    }
    override fun getItemCount(): Int {
          return messageList.size
    }

    class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val sentMessage = itemView.findViewById<TextView>(R.id.txt_sent_message)

    }

    class ReceiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val receiveMessage = itemView.findViewById<TextView>(R.id.txt_receive_message)
    }


}