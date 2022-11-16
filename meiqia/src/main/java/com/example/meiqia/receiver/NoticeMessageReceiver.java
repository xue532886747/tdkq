package com.example.meiqia.receiver;

import android.util.Log;

import com.meiqia.meiqiasdk.controller.MessageReceiver;
import com.meiqia.meiqiasdk.model.Agent;
import com.meiqia.meiqiasdk.model.BaseMessage;

/**
 * @author 53288
 * @description
 * @date 2021/6/21
 */
public class NoticeMessageReceiver extends MessageReceiver {
    private static final String TAG = "NoticeMessageReceiver1";

    @Override
    public void receiveNewMsg(BaseMessage message) {
        Log.d(TAG, "receiveNewMsg " + "baseMessage = " + message.getContent());
    }

    @Override
    public void recallMessage(long id, String nickname) {
        Log.d(TAG, "recallMessage " + " id = " + id + ", nickname = " + nickname);
    }

    @Override
    public void changeTitleToInputting() {
        Log.d(TAG, "changeTitleToInputting  ");
    }

    @Override
    public void addDirectAgentMessageTip(String agentNickname) {
        Log.d(TAG, "addDirectAgentMessageTip = " + agentNickname);
    }

    @Override
    public void setCurrentAgent(Agent agent) {
        Log.d(TAG, "setCurrentAgent = " + agent.getNickname());
    }

    @Override
    public void inviteEvaluation() {
        Log.d(TAG, "inviteEvaluation = ");
    }

    @Override
    public void setNewConversationId(String newConversationId) {
        Log.d(TAG, "setNewConversationId = " + newConversationId);
    }

    @Override
    public void updateAgentOnlineOfflineStatus() {
        Log.d(TAG, "updateAgentOnlineOfflineStatus = ");
    }

    @Override
    public void blackAdd() {
        Log.d(TAG, "blackAdd = ");
    }

    @Override
    public void blackDel() {
        Log.d(TAG, "blackDel = ");
    }

    @Override
    public void removeQueue() {
        Log.d(TAG, "removeQueue = ");
    }

    @Override
    public void queueingInitConv() {
        Log.d(TAG, "queueingInitConv = ");
    }

    @Override
    public void socketOpen() {
        Log.d(TAG, "socketOpen ");
    }
}
