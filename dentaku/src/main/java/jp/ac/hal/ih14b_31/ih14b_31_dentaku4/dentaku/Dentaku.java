package jp.ac.hal.ih14b_31.ih14b_31_dentaku4.dentaku;

import android.util.Log;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by yoshimurasatoshi on 2014/09/04.
 */
public class Dentaku implements EventTypeListenerInterface  {

    //プロパティ群
    //電卓の状態を表す
    private StateString state = null;
    //左辺
    public String getmLeft() {
        return mLeft;
    }
    private String mLeft = null;
    //右辺
    public String getmRight() {
        return mRight;
    }
    private String  mRight = null;
    //オペランド
    private String mOperand = null;
    /**
     * メモ
     */
    public String getSubtotal() {
        return subtotal;
    }
    private String subtotal = null;
    //MAX
    private String MAX = "9999999999";
    //MIN
    private String MIN = "-999999999";


    /***
     * コンストラクタ
     * 初期化　最初は左辺入力状態
     * 左辺 0,
     * 右辺は空白
     * メモリは0
     */
    public Dentaku(){
        this.state = StateString.Input_Left;
        this.mLeft = "0";
        this.mRight = "";
        this.mOperand = "";
        this.subtotal = "0";
    }

    public StateString getState() {
        return state;
    }
    public void setState(StateString state) {
        this.state = state;
    }

    /*
    電卓の機能群
     */
    //数値が入力されたとき
    @Override
    public void onInputNumber( String text) {
        switch (this.state){
            //メモリ表示状態のときは左辺入力状態に
            case Memory_Recall:
                this.state = StateString.Input_Left;
            //左辺入力状態のとき
            case Input_Left:
                //ゼロのとき
                if(mLeft.equals("0")){
                    //空白に
                    mLeft = "";
                }
                mLeft = mLeft + text;
                break;
            //オペランド入力状態のとき
            case Input_Operand:
                //右辺に足す
                //ゼロのとき
                if(mRight.equals("0")){
                    //空白に
                    mRight = "";
                }
                this.mRight += text;
                this.state = StateString.Input_Right;
                break;
            //右辺入力状態のとき
            case Input_Right:
                //ゼロのとき
                if(mRight.equals("0")){
                    //空白に
                    mRight = "";
                }
                this.mRight += text;
                break;
            //結果表示状態のとき
            case Output_Number:
                //前の計算は忘れて新たに計算
                mLeft = text;
                mRight = "";
                this.state = StateString.Input_Left;
                break;
        }
    }

    /**
     * オペランドが押された時
     */
    @Override
    public void onInputOperand( String operand) {
        switch (this.state) {
            //左辺入力状態のとき
            case Input_Left:
                //まだ左辺が入力されていない場合or 0のときは左辺入力状態のまま
                if ( this.mLeft.equals("") || this.mLeft.equals("0") ) {
                    break;
                }
            //オペランド入力状態のとき
            case Input_Operand:
                //そのまま
                break;
            //右辺入力状態のとき
            case Input_Right:
                //まだ右辺が入力されていないもしくは0の場合,右辺入力状態のまま
                if (this.mRight.isEmpty() || mRight.equals("0") ) {
                    break;
                } else {
                    //右辺あった場合
                    //演算する
                    BigDecimal bLeft = this.calculate();
                    //計算結果を左辺に代入
                    this.mLeft = bLeft.toPlainString();
                    //右辺をクリア
                    this.mRight = "";
                }
                break;
            //結果表示状態のとき
            case Output_Number:
                //右辺をクリア
                this.mRight = "";
                break;
            //メモリ表示状態のとき何もしない
            case Memory_Recall:
                break;
        }
        //演算子を設定しなおす
        this.mOperand = operand;
        //オペランド入力状態に変更する
        this.state = StateString.Input_Operand;
    }

    /**
     * = が押された時
     */
    @Override
    public void onInputEqual() {
        switch (this.state){
            //左辺入力状態のとき
            case Input_Left:
                //何もしない
                break;
            //オペランド入力状態のとき
            case Input_Operand:
                break;
            //右辺入力状態のとき
            case Input_Right:
                //演算する
                mLeft = this.calculate().toPlainString();
                //結果表示状態に移行
                this.state = StateString.Output_Number;
                break;
            //結果表示状態のとき
            case Output_Number:
                //演算する mOperandとmLeftは残ったままなので同じ演算がされる
                mLeft = this.calculate().toPlainString();
                break;
            //メモリ表示状態のとき何もしない
            case Memory_Recall:
                break;
        }
    }

    /**
     * Cが押されたとき
     */
    public void onInputClear(){
        switch (this.state){
            case Input_Left:
                this.mLeft = "0";
                break;
            case Input_Operand:
                this.mLeft = "0";
                this.state = StateString.Input_Left;
                break;
            case Input_Right:
                this.mRight = "0";
                break;
            //結果表示状態の場合はACと同じ
            case Output_Number:
                this.mLeft = "0";
                this.mRight = "0";
                this.mOperand = "";
                this.state = StateString.Input_Left;
                break;
            //メモリ表示状態のとき
            //何もしない
            case Memory_Recall:
                break;
        }
    }

    /**
     * ACが押されたとき
     */
    public void onInputAllClear(){
        this.mLeft = "0";
        this.mRight = "";
        this.mOperand = "";
        this.state = StateString.Input_Left;
    }

    /**
     * BSが押されたとき
     */
    @Override
    public void onInputBuckSpace() {
        switch (this.state){
            //左辺入力状態のときと結果出力状態のとき
            case Output_Number:
            case Input_Left:
                //なかったらなんもしない
                if(mLeft.isEmpty() || mLeft.equals("0")){
                    mLeft = "0";
                    break;
                }
                //左辺から1文字消す
                mLeft = mLeft.substring(0,mLeft.length() - 1 );
                //消した結果なくなったら、0にする
                if( mLeft.isEmpty() ){
                    mLeft = "0";
                    break;
                }
                break;
            //右辺入力状態のとき
            case Input_Right:
                //なかったらなんもしない
                if(mRight.isEmpty() || mRight.equals("0") ){
                    mRight = "0";
                    break;
                }
                //右辺から1文字消す
                mRight = mRight.substring(0,mRight.length() - 1 );
                //消した結果なくなったら、0にする
                if( mRight.isEmpty() ){
                    mRight = "0";
                    break;
                }
                break;
            //メモリ表示状態のとき
            //何もしない
            case Memory_Recall:
                break;
        }
    }

    /**
     * Dotが押されたとき
     */
    @Override
    public void onInputDot() {
        switch (this.state){
            //左辺に小数点つける
            case Output_Number:
                this.state = StateString.Input_Left;
                //右辺はクリア
                mRight = "";
            case Input_Left:
                //既に.があったら処理しない
                if(mLeft.indexOf(".") != -1){
                    break;
                }
                if(mLeft.isEmpty() || mLeft.equals("0")){
                    mLeft = "0.";
                }
                else{
                    mLeft += ".";
                }
                break;
            case Input_Operand:
                break;
            //右辺に小数点つける
            case Input_Right:
                //既に.があったら処理しない
                if(mRight.indexOf(".") != -1){
                    break;
                }
                if(mRight.isEmpty() || mRight.equals("0")){
                    mRight = "0.";
                }else{
                    mRight += ".";
                }
                break;
            //メモリ表示状態のとき
            //何もしない
            case Memory_Recall:
                break;
        }

    }

    /**
     * MCが押されたことを通知
     */
    public void onInputMemoryClear(){
        switch (this.state){
            case Input_Left:
                break;
            case Input_Operand:
                break;
            case Input_Right:
                break;
            case Output_Number:
                break;
        }
        //メモリクリア
        this.subtotal = "0";
    }

    /**
     * メモリー　＋　かメモリー -が押された事を通知
     */
    public void onInputMemoryOperand( String memoryOperand ){
        switch (this.state){
            //左辺入力時は何もしない
            case Input_Left:
                break;
            //オペランド入力時も何もしない
            case Input_Operand:
                break;
            //右辺入力時にメモリに小計を足すor引く
            case Input_Right:
                this.memoryCalculate(memoryOperand);
                break;
            //結果表示時も何もしない
            case Output_Number:
                break;
            //メモリ表示状態のとき
            //何もしない
            case Memory_Recall:
                break;
        }
    }

    /**
     * MRが押されたこと通知
     */
    public void onInputMemoryRecall(){
        //数値はクリアして
        mLeft = "0";
        mRight = "0";
        this.state = StateString.Memory_Recall;
    }


    /*
    private
     */
    /**
     * 自分の持つ演算子を使って演算
     * @return
     */
    private BigDecimal calculate(){
        //演算の為に数値化
        BigDecimal bLeft = new BigDecimal(mLeft);
        BigDecimal bRight = null;
        if (!mRight.isEmpty()) {
            bRight = new BigDecimal(mRight);
        }else{
            //mRightが無かったら計算せず返す
            return bLeft;
        }
        //自分の演算子文字列に応じた演算
        //switchの為にcharに変換
        char cOperand = mOperand.charAt(0);
        switch (cOperand){
            case '+':
                bLeft = bLeft.add(bRight);
                break;
            case '-':
                bLeft = bLeft.subtract(bRight);
                break;
            case '*':
                bLeft = bLeft.multiply(bRight);
                break;
            case '/':
                //0Divは0として返す //0を割ろうとしたら0で返す
                if( bRight.compareTo( BigDecimal.ZERO ) == 0  || bLeft.compareTo( BigDecimal.ZERO ) == 0 ){
                    this.state = StateString.ZERO_DIVIDE_ERROR;
                    return new BigDecimal("0");
                }
                //小数点8位以下は四捨五入
                bLeft = bLeft.divide(bRight,7,BigDecimal.ROUND_HALF_UP);
                break;
        }
        //最大値,最小値 超えていたらとどめておく
        BigDecimal bMax = new BigDecimal(MAX);
        BigDecimal bMin = new BigDecimal(MIN);
        if(bLeft.compareTo(bMax) == 1){
            bLeft = bMax;
        }else if(bLeft.compareTo(bMin) == -1){
            bLeft = bMin;
        }
        //結果が0の時はトリムしない
        if(bLeft.compareTo(new BigDecimal("0")) == 0){
            return bLeft;
        }

        return  trim(bLeft);
    }

    /**
     * M+orM-の計算
     * 引数 M+orM-
     * */
    private void memoryCalculate( String memoryOperad ){
        //自分の持つsubtotalに計算結果を足す
        BigDecimal bSubtotal = new BigDecimal(this.subtotal);
        BigDecimal bLeft = this.calculate();

        if(memoryOperad.equals("M+")){
            bSubtotal = bSubtotal.add(bLeft);
        }else if(memoryOperad.equals("M-")){
            bSubtotal = bSubtotal.subtract(bLeft);
        }else{
            Log.w("Dentaku.memoryCalculate","引数の値がおかしい");
            return;
        }
        this.subtotal = bSubtotal.toPlainString();
    }



    /**
     * 小数点以下に0が続いた場合表示しない
     * @param n トリミングしたい数値
     * @return トリミング後の数値
     */
    static BigDecimal trim(BigDecimal n) {
        try {
            while (true) {
                n = n.setScale( n.scale() -1 );
            }
        } catch ( ArithmeticException e ) {
            ///小数点以下が無い場合は表示しない
            if(n.toPlainString().indexOf(".") == -1){
                n = n.setScale(n.scale() + -n.scale() );
            }
            //ていうかフォーラムあったから書いたけど例外で抜けるってどうなん？
        }
        return n;
    }

}
