package com.example.base_ui.bigimageview.glide.cache;

import android.graphics.Paint;
import android.util.LruCache;

import com.bumptech.glide.load.Key;
import com.bumptech.glide.util.Util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * @author 53288
 * @description
 * @date 2021/6/7
 */
public class SafeKeyGenerator {
    private final LruCache<Key, String> loadIdToSafeHash = new LruCache<Key, String>(1000);

    public String getSafeKey(Key key) {
        String safeKey;
        synchronized (loadIdToSafeHash) {
            safeKey = loadIdToSafeHash.get(key);
        }
        if (safeKey == null) {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                key.updateDiskCacheKey(messageDigest);
                safeKey = Util.sha256BytesToHex(messageDigest.digest());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            synchronized (loadIdToSafeHash) {
                loadIdToSafeHash.put(key, safeKey);
            }
        }
        return safeKey;
    }
}
