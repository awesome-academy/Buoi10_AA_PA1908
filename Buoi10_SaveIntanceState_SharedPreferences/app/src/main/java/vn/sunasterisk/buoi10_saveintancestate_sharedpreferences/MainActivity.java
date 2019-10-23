package vn.sunasterisk.buoi10_saveintancestate_sharedpreferences;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

    /**
     * - SaveInstanceState dùng để giữ lại dữ liệu đơn giản như String, int theo dạng
     * key-value trong trường trước hợp cấu hình ứng dụng thay đổi.
     * Vậy nhữn giữ liệu lớn hơn, phức tạp hơn thì chúng ta làm thế nào?
     * Chúng ta có thể dùng ViewModel, LocalStorage, ...
     * - Khi ta hủy ứng dụng và bật lại thì dữ liệu lưu bởi SaveInstanceState
     * không còn nữa. Có một số trường hợp người dùng muốn bật lại ứng dụng
     * và vẫn muốn lấy lại dữ liệu trước đó, ta có thể dùng SharedPreferences
     */

    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView mTextCount;
    private Button mButtonBlack;
    private Button mButtonRed;
    private Button mButtonBlue;
    private Button mButtonGreen;
    private Button mButtonCount;
    private Button mButtonReset;

    private int mCount;
    private int mColor;

    private SharedPreferences mSharedPreferences;
    private String sharedFile = "vn.sunasterisk.sharedpreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        registerListeners();

        Log.d(TAG, "onCreate: ");

        //returnState(savedInstanceState);

        getDataFromPreferences();
    }

    private void getDataFromPreferences() {
        mSharedPreferences = getSharedPreferences(sharedFile, MODE_PRIVATE);
        mCount = mSharedPreferences.getInt(Key.COUNT_KEY, 0);
        mColor = mSharedPreferences.getInt(Key.COLOR_KEY, mColor);

        mTextCount.setText(String.valueOf(mCount));
        mTextCount.setBackgroundColor(mColor);
    }

    /**
     * phương thức này lấy các giá trị của acitivity trước đó mà bị thay đổi cấu
     * hình, update lại giao diện
     *
     * @param savedInstanceState
     */
    private void returnState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mCount = savedInstanceState.getInt(Key.COUNT_KEY);
            mColor = savedInstanceState.getInt(Key.COLOR_KEY);

            mTextCount.setText(String.valueOf(mCount));
            mTextCount.setBackgroundColor(mColor);
        }
    }

    private void registerListeners() {
        mButtonBlack.setOnClickListener(this);
        mButtonRed.setOnClickListener(this);
        mButtonBlue.setOnClickListener(this);
        mButtonGreen.setOnClickListener(this);
        mButtonCount.setOnClickListener(this);
        mButtonReset.setOnClickListener(this);
    }

    private void initViews() {
        mTextCount = findViewById(R.id.text_count);
        mButtonBlack = findViewById(R.id.button_black);
        mButtonRed = findViewById(R.id.button_red);
        mButtonBlue = findViewById(R.id.button_blue);
        mButtonGreen = findViewById(R.id.button_green);
        mButtonCount = findViewById(R.id.button_count);
        mButtonReset = findViewById(R.id.button_reset);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_black:
            case R.id.button_red:
            case R.id.button_blue:
            case R.id.button_green:
                changeTextBackground(v);
                break;
            case R.id.button_count:
                count();
                break;
            case R.id.button_reset:
                reset();
                break;
            default:
                break;
        }
    }

    private void changeTextBackground(View v) {
        Drawable background = v.getBackground();
        mColor = ((ColorDrawable) background).getColor();
        mTextCount.setBackgroundColor(mColor);
    }

    private void count() {
        mCount++;
        mTextCount.setText(String.valueOf(mCount));
    }

    private void reset() {
        mCount = 0;
        mTextCount.setText(String.valueOf(mCount));

        mColor = getResources().getColor(R.color.color_background);
        mTextCount.setBackgroundColor(mColor);

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    /**
     * nếu cấu hình bị thay đổi: xoay màn hình, thay đổi ngôn ngữ, bàn phím thì
     * chúng ta có thể lưu những dữ liệu đơn giản như int, string vào đây
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.getInt(Key.COUNT_KEY, mCount);
        outState.getInt(Key.COLOR_KEY, mColor);
        Log.d(TAG, "onSaveInstanceState: ");
    }

    /**
     * Phương thức này có thể lấy ra dữ liệu của saveInstanceSate
     * @param savedInstanceState
     */
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(Key.COUNT_KEY, mCount);
        editor.putInt(Key.COLOR_KEY, mColor);

        // không đồng bộ
        editor.apply();
        // đồng bộ, trong một vài trường có thể chặn 1 số hoạt động, không khuyến
        //khích dùng
        //editor.commit();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }
}
