package com.example.rxhttp.request.body;

import com.example.rxhttp.request.interfaces.UploadProgressListener;

import java.io.IOException;

import androidx.annotation.NonNull;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ForwardingSink;
import okio.Okio;

/**
 * @author 53288
 * @description 用于上传的进度监听
 * @date 2021/5/18
 */
public class UpLoadRequestBody extends RequestBody  {

    private final RequestBody mRequestBody;
    private BufferedSource source = null;
    private int mCurrentLength;
    private UploadProgressListener mProgressListener;

    public UpLoadRequestBody(RequestBody mRequestBody, UploadProgressListener mProgressListener) {
        this.mRequestBody = mRequestBody;
        this.mProgressListener = mProgressListener;
    }


    @Override
    public MediaType contentType() {
        return mRequestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return mRequestBody.contentLength();
    }

    @Override
    public void writeTo(@NonNull BufferedSink sink) throws IOException {
        long contentLength = contentLength();
        if (mProgressListener != null) {
            mProgressListener.onAllProgress(contentLength);
        }

        ForwardingSink forwardingSink = new ForwardingSink(sink) {
            @Override
            public void write(@NonNull Buffer source, long byteCount) throws IOException {
                mCurrentLength += byteCount;
                if (mProgressListener != null) {
                    mProgressListener.onProgress(contentLength, mCurrentLength);
                }
                super.write(source, byteCount);
            }
        };
        BufferedSink bufferedSink = Okio.buffer(forwardingSink);
        if (mRequestBody != null) {
            mRequestBody.writeTo(bufferedSink);
        }
        bufferedSink.flush();
    }
}
