package jp.ac.hal.ih14b_31.ih14b_31_dentaku4.dentaku;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.util.LangUtils;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * MainActivityをビューコントローラとしてあつかった感じになります。
 */
public class MainActivity extends Activity implements StateListenerInterface  {

    TextView mTextView;

    String mLeft = ""; //左辺用
    String mOp = ""; //演算子
    String mRight = ""; //右辺
    String mResult = "";

    //大事な大事な電卓オブジェクト
    private static Dentaku dentaku = null;
    //大事な大事なメディエーター
    private static StateMadiator stateMadiator = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView)findViewById(R.id.textView);
        //メディエーターをインスタンス化
        stateMadiator = new StateMadiator();
        //電卓をインスタンス化
        dentaku = new Dentaku();
        //メディエーターのリスナーに通知先を登録
        stateMadiator.setStateListener(this);
        stateMadiator.setEventTypeListener(dentaku);
    }

    public void buttonMethod(View button) {
        //まずは押されたボタンの種類を判定する

        //ボタンのテキスト取得
        Button btn = (Button) button;
        String text = btn.getText().toString();

        //メディエーターに値を送る
        stateMadiator.transportEventType(text);
        //結果を更新
        stateMadiator.updateState(dentaku.getState());
    }

    /**
     * 見た目変更
     */
    /**
     * 左辺が入力されたとき
     */
    @Override
    public void inputingLeft(){
        mTextView.setText(dentaku.getmLeft());
    }
    /**
     * + - / *が入力されたとき
     */
    @Override
    public void inputingOperand() {
        mTextView.setText(dentaku.getmLeft());
    }
    /**
     * 右辺入力されたとき
     */
    @Override
    public void inputingRight(){
        mTextView.setText(dentaku.getmRight());
    }
    /**
     * = が押されたとき
     */
    @Override
    public void outputNumber() {
        mTextView.setText(dentaku.getmLeft());
    }
    /**
     * MRおされたとき
     */
    @Override
    public void inputingMemoryRecall() {
        mTextView.setText(dentaku.getSubtotal());
    }

    /**
     * 暗号モード起動
     */
    public void startCodeaActivity(View view){
        Intent intent=new Intent(this,CodeaActivity.class);
        startActivityForResult(intent,0);
    }

    /**
     * 0divError
     */
    @Override
    public void onZeroDivError() {
        // 第3引数は、表示期間（LENGTH_SHORT、または、LENGTH_LONG）
        Toast.makeText(this, "テスト ACを押してください", Toast.LENGTH_LONG).show();
    }

    /***
     * でふぉ
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
