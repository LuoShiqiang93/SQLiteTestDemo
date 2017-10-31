package com.mark.sqlitetestdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.mark.sqlitetestdemo.been.User;
import com.mark.sqlitetestdemo.db.DemoDBManager;
import com.mark.sqlitetestdemo.utils.PreferenceManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by LuoShiQiang on 2017/10/31
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PreferenceManager.getInstance().setCurrentUserName("1");
    }

    public void saveData(View view) {
        User user = new User();
        user.setNick("张三");
        user.setAvatar("张三的头像");
        user.setUserName("1");
        User user1 = new User();
        user1.setUserName("2");
        user1.setAvatar("李四的头像");
        user1.setNick("李四");
        DemoDBManager.getInstance().saveContact(user);
        DemoDBManager.getInstance().saveContact(user1);
    }

    public void getData(View view) {
        Map<String, User> contactMap = DemoDBManager.getInstance().getContactMap();
        List<User> list = new ArrayList(contactMap.values());
        for (User user : list) {
            String nick = user.getNick();
            Log.e(TAG, "nick:" + nick);
        }
    }

    public void query(View view) {
        User user = DemoDBManager.getInstance().getContact("1");
        if (user == null) {
            Log.e(TAG, "user为null");
            return;
        }
        String avatar = user.getAvatar();
        String nick = user.getNick();
        String userName = user.getUserName();
        Log.e(TAG, "nick:" + nick);
        Log.e(TAG, "avatar:" + avatar);
        Log.e(TAG, "userName:" + userName);
    }

    public void delete(View view) {
        DemoDBManager.getInstance().deleteContact("3");
    }

    public void updata(View view) {
        User user = new User();
        user.setUserName("1");
        user.setAvatar("王五的头像");
        user.setNick("王五");
        DemoDBManager.getInstance().updataUser(user);
    }

    public void saveDatas(View view) {
        List<User> userList = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            User user = new User();
            user.setNick("赵" + i);
            user.setAvatar("赵" + i + "的头像");
            user.setUserName(String.valueOf(i));
            userList.add(user);
        }
        DemoDBManager.getInstance().saveContacList(userList);
    }

    public void clearSQL(View view) {
        DemoDBManager.getInstance().clearSQL();
    }
}
