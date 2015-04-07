package jp.ac.hal.ih14b_31.ih14b_31_dentaku4.dentaku;

import android.util.Log;

import org.apache.commons.lang3.math.NumberUtils;


/**
 * Created by yoshimurasatoshi on 2014/09/04.
 * 状態とイベント通知のクラス
 * メディエータというかリスナー？
 */
public class StateMadiator {

    private StateListenerInterface stateListener = null;
    private EventTypeListenerInterface eventTypeListener = null;

    //オペランドはまとめておくと便利
    static final String[] MOPERANDARRAY = { "+", "-", "*", "/" };

    /**
     * ユーザの行動で行われるイベントを電卓に通知,
     * 第1引数:押したボタンの文字
     */
    public void transportEventType (String text){
        if (this.eventTypeListener != null) {
            //数値のとき
            if (NumberUtils.isNumber(text)) {
                eventTypeListener.onInputNumber(text);
            }
            //=の時
            else if (text.equals("=")) {
                eventTypeListener.onInputEqual();
            }
            // + - * / 演算子の時
            else if(this.isCulcOperand(text)){
                eventTypeListener.onInputOperand(text);
            }
            //Cの時
            else if(text.equals("C")){
                eventTypeListener.onInputClear();
            }
            //BSのとき
            else if(text.equals("BS")){
                eventTypeListener.onInputBuckSpace();
            }
            // .のとき
            else if(text.equals(".")){
                eventTypeListener.onInputDot();
            }
            //ACの時
            else if( text.equals("AC") ){
                eventTypeListener.onInputAllClear();
            }
            //MCのとき
            else if( text.equals("MC") ){
                eventTypeListener.onInputMemoryClear();
            }
            //M+もしくはM-のとき
            else if( text.equals("M+") || text.equals("M-") ){
                eventTypeListener.onInputMemoryOperand( text );
            }
            //MRのとき
            else if( text.equals("MR") ){
                eventTypeListener.onInputMemoryRecall();
            }

        }
    }

    /**
     *  電卓の状態によってその状態を見た目に反映通知 updateにする
     *
     */
    public void updateState( StateString stateString ) {
        if (this.stateListener != null) {
            switch (stateString) {
                //左辺入力状態だったとき
                case Input_Left:
                    stateListener.inputingLeft();
                    break;
                //右辺入力状態だったとき
                case Input_Right:
                    stateListener.inputingRight();
                    break;
                //結果出力だったとき
                case Output_Number:
                    stateListener.outputNumber();
                    break;
                //オペランド入力だったとき
                case Input_Operand:
                    stateListener.inputingOperand();
                    break;
                //メモリリコール状態だったとき
                case Memory_Recall:
                    stateListener.inputingMemoryRecall();
                    break;
                case ZERO_DIVIDE_ERROR:
                    stateListener.onZeroDivError();
                    break;
            }
        }
    }

    /**
     * EventTypeリスナーを追加する
     *
     * @param listener
     */
    public void setEventTypeListener(EventTypeListenerInterface listener) {
        this.eventTypeListener = listener;
    }
    /**
     * EventTypeリスナーを削除する
     */
    public void removeEventTypeListener() {
        this.eventTypeListener = null;
    }
    /**
     * Stateリスナーを追加する
     *
     * @param listener
     */
    public void setStateListener(StateListenerInterface listener) {
        this.stateListener = listener;
    }
    /**
     * Stateリスナーを削除する
     */
    public void removeStateListener() {
        this.stateListener = null;
    }

    /**
     * private
     */
    /**
     * 引数が + - / * であったらtrue 違えばfalse
     * @return
     */
    private Boolean isCulcOperand(String checStr){
        for (String operand: MOPERANDARRAY){
            if(operand.equals(checStr)){
                return true;
            }
        }
        return false;
    }

}
