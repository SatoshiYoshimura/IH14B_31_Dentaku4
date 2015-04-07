package jp.ac.hal.ih14b_31.ih14b_31_dentaku4.dentaku;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


public class CodeaActivity extends Activity {

    ImageView imageView;
    AnimationSet thunderAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codea);
        imageView = (ImageView)findViewById(R.id.imageView);
        adaptThunderAnimation();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.codea, menu);
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

    /**
     * メインに戻る
     */
    public void startMain(View view ){
        Intent intent = new Intent(this,MainActivity.class);
        startActivityForResult(intent,0);
    }

    /**
     *
     * @param button
     */
    public void buttonClick(View button){
        //ボタンのテキスト取得
        EditText inputCodea = (EditText)findViewById(R.id.inputCodea);
       String text = inputCodea.getText().toString();
        if(text.equals("thunder")){
            imageView.setImageResource(R.drawable.gra_anim_effect_thunder);
            imageView.startAnimation(thunderAnimation);
        }
    }

    /**
     * private
     */
    private void adaptThunderAnimation(){
        // 【1】インスタンスを生成
        this.thunderAnimation = new AnimationSet(true);

        // 【2】基本のアニメーションを生成
        AlphaAnimation alpha = new AlphaAnimation(0.9f, 0.2f);
//        RotateAnimation rotate = new RotateAnimation(0, 360, 50, 25);
        ScaleAnimation scale = new ScaleAnimation(0.1f, 1, 0.1f, 1);
        TranslateAnimation translate = new TranslateAnimation(50, 100, 200, 100);

        // 【3】生成したアニメーションを追加
        thunderAnimation.addAnimation(alpha);
//        thunderAnimation.addAnimation(rotate);
        thunderAnimation.addAnimation(scale);
        thunderAnimation.addAnimation(translate);

        // 【4】アニメーション時間を設定して動作開始
     //   set.setDuration(3000);
//        v.startAnimation(set);
    }
}

